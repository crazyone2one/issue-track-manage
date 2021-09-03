package cn.master.track.util;

import cn.master.track.enums.ExcelColumn;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Created by 11's papa on 2021/08/31
 * @version 1.0.0
 */
@Slf4j
public class ExcelUtils {
    private final static String EXCEL2003 = "xls";
    private final static String EXCEL2007 = "xlsx";

    private static <T> void handleField(T t, String value, Field field) {
        Class<?> type = field.getType();
        try {
            if (Objects.equals(type, void.class) || StringUtils.isBlank(value)) {
                return;
            }
            if (Objects.equals(type, Object.class)) {
                field.set(t, value);
            } else if (Objects.isNull(type.getSuperclass()) || Objects.equals(type.getSuperclass(), Number.class)) {
                if (Objects.equals(type, int.class) || Objects.equals(type, Integer.class)) {
                    field.set(t, NumberUtils.toInt(value));
                }
            } else if (Objects.equals(type, boolean.class)) {
                field.set(t, BooleanUtils.toBoolean(value));
            } else if (Objects.equals(type, Date.class)) {
                field.set(t, value);
            } else if (Objects.equals(type, String.class)) {
                field.set(t, value);
            } else {
                Constructor<?> constructor = type.getConstructor(String.class);
                field.set(t, constructor.newInstance(value));
            }
        } catch (IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入excel文件
     *
     * @param cls
     * @param file
     * @return java.util.List<T>
     */
    public static <T> List<T> readExcel(Class<T> cls, MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (Objects.isNull(fileName)) {
            return new LinkedList<>();
        }
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            log.error("上传文件格式不正确");
        }
//        String filePath = "src/main/resources/temp";
//        File temp = new File(filePath);
//        if (!temp.exists()) {
//            temp.mkdirs();
//        }

        List<T> dataList = new ArrayList<>();
        Workbook workbook = null;
        try {
            InputStream inputStream = file.getInputStream();
            if (fileName.endsWith(EXCEL2003)) {
                workbook = new HSSFWorkbook(inputStream);
            }
            if (fileName.endsWith(EXCEL2007)) {
                workbook = new XSSFWorkbook(inputStream);
            }
            if (Objects.nonNull(workbook)) {
                Map<String, List<Field>> classMap = new LinkedHashMap<>();
                List<Field> fields = Stream.of(cls.getDeclaredFields()).collect(Collectors.toList());
                fields.forEach(field -> {
                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                    if (Objects.nonNull(annotation)) {
                        final String value = annotation.value();
                        if (StringUtils.isBlank(value)) {
                            return;
                        }
                        if (!classMap.containsKey(value)) {
                            classMap.put(value, new LinkedList<>());
                        }
                        field.setAccessible(true);
                        classMap.get(value).add(field);
                    }
                });
                //索引-->columns
                Map<Integer, List<Field>> reflectionMap = new LinkedHashMap<>();
                //默认读取第一个sheet
                Sheet sheet = workbook.getSheetAt(0);
                boolean firstRow = true;
                for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    //首行  提取注解
                    if (firstRow) {
                        getTitle(row, reflectionMap, classMap);
                        firstRow = false;
                    } else {
                        //忽略空白行
                        if (row == null) {
                            continue;
                        }
                        try {
                            T t = cls.newInstance();
                            //判断是否为空白行
                            boolean allBlank = true;
                            allBlank = isAllBlank(reflectionMap, row, t, allBlank);
                            if (!allBlank) {
                                dataList.add(t);
                            } else {
                                log.warn(String.format("row:%s is blank ignore!", i));
                            }
                        } catch (Exception e) {
                            log.error(String.format("parse row:%s exception!", i), e);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(workbook)) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    log.error("parse excel exception!", e);
                }
            }
        }
        return dataList;
    }

    private static <T> boolean isAllBlank(Map<Integer, List<Field>> reflectionMap, Row row, T t, boolean allBlank) {
        for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
            if (reflectionMap.containsKey(j)) {
                Cell cell = row.getCell(j);
                String cellValue = getCellValue(cell);
                if (StringUtils.isNotBlank(cellValue)) {
                    allBlank = false;
                }
                List<Field> fieldList = reflectionMap.get(j);
                fieldList.forEach(
                        x -> {
                            try {
                                handleField(t, cellValue, x);
                            } catch (Exception e) {
                                log.error(String.format("reflect field:%s value:%s exception!", x.getName(), cellValue), e);
                            }
                        }
                );
            }
        }
        return allBlank;
    }

    private static void getTitle(Row row, Map<Integer, List<Field>> reflectionMap, Map<String, List<Field>> classMap) {
        for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
            Cell cell = row.getCell(j);
            String cellValue = getCellValue(cell);
            if (classMap.containsKey(cellValue)) {
                reflectionMap.put(j, classMap.get(cellValue));
            }
        }
    }

    private static String getCellValue(Cell cell) {
        if (Objects.isNull(cell)) {
            return "";
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return DateUtil.getJavaDate(cell.getNumericCellValue()).toString();
            } else {
                return BigDecimal.valueOf(cell.getNumericCellValue()).toString();
            }
        } else if (cell.getCellType() == CellType.STRING) {
            return StringUtils.trimToEmpty(cell.getStringCellValue());
        } else if (cell.getCellType() == CellType.FORMULA) {
            return StringUtils.trimToEmpty(cell.getCellFormula());
        } else if (cell.getCellType() == CellType.BLANK) {
            return "";
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.ERROR) {
            return "ERROR";
        } else {
            return cell.toString().trim();
        }
    }

    /**
     * 导出excel
     *
     * @param fileName 文件名称
     * @param response HttpServletResponse
     * @param dataList dataList
     * @param cls      cls
     */
    public static <T> void writeExcel(String fileName, HttpServletResponse response, List<T> dataList, Class<T> cls) {
        Field[] fields = cls.getDeclaredFields();
        final List<Field> fieldList = Arrays.stream(fields)
                .filter(field -> {
                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                    if (Objects.nonNull(annotation) && annotation.col() > 0) {
                        field.setAccessible(true);
                        return true;
                    }
                    return false;
                }).sorted(Comparator.comparing(field -> {
                    int col = 0;
                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                    if (Objects.nonNull(annotation)) {
                        col = annotation.col();
                    }
                    return col;
                })).collect(Collectors.toList());
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Sheet1");
        AtomicInteger ai = new AtomicInteger();
        {
            Row row = sheet.createRow(ai.getAndIncrement());
            AtomicInteger aj = new AtomicInteger();
            //写入头部
            fieldList.forEach(field -> {
                ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                String columnName = "";
                if (Objects.nonNull(annotation)) {
                    columnName = annotation.value();
                }
                Cell cell = row.createCell(aj.getAndIncrement());
                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
//                cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
//                cellStyle.setAlignment(CellStyle.ALIGN_CENTER);

//                Font font = wb.createFont();
//                font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
//                cellStyle.setFont(font);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(columnName);
            });
        }
        if (CollectionUtils.isNotEmpty(dataList)) {
            dataList.forEach(t -> {
                final Row row = sheet.createRow(ai.getAndIncrement());
                AtomicInteger aj = new AtomicInteger();
                fieldList.forEach(field -> {
                    Object value = "";
                    try {
                        value = field.get(t);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    Cell cell = row.createCell(aj.getAndIncrement());
                    if (Objects.nonNull(value)) {
                        cell.setCellValue(value.toString());
                    }
                });
            });
        }
        //冻结窗格
        wb.getSheet("Sheet1").createFreezePane(0, 1, 0, 1);
        //浏览器下载excel
        buildExcelDocument(fileName, wb, response);
        //生成excel文件
//        buildExcelFile(".\\" + fileName, wb);
    }

    private static void buildExcelFile(String path, Workbook wb) {
        File file = new File(path);
        file.deleteOnExit();
        try {
            wb.write(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void buildExcelDocument(String fileName, Workbook wb, HttpServletResponse response) {
        try {
            response.setContentType("application/msexcel");
            response.setHeader("Content-disposition", "attachment;filename=" +
                    URLEncoder.encode(fileName, "utf-8") + "." + EXCEL2007);
            response.flushBuffer();
            wb.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

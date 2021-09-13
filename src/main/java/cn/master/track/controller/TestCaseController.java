package cn.master.track.controller;


import cn.master.track.config.Constants;
import cn.master.track.entity.IssueProject;
import cn.master.track.entity.TestCase;
import cn.master.track.service.IssueModuleService;
import cn.master.track.service.IssueProjectService;
import cn.master.track.service.TestCaseService;
import cn.master.track.util.ExcelUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-27
 */
@Controller
@RequestMapping("/case")
public class TestCaseController {

    private final TestCaseService caseService;
    private final IssueProjectService projectService;
    private final IssueModuleService moduleService;

    @Autowired
    public TestCaseController(TestCaseService caseService, IssueProjectService projectService, IssueModuleService moduleService) {
        this.caseService = caseService;
        this.projectService = projectService;
        this.moduleService = moduleService;
    }

    /**
     * 测试用例首页
     *
     * @param model Model
     * @return java.lang.String
     */
    @GetMapping("/summary")
    public String caseSummary(Model model) {
        final List<Map<String, Object>> caseInfoMap = caseService.caseInfoMap();
        model.addAttribute("caseInfo", caseInfoMap);
        model.addAttribute("proMap", projectService.projectsMap());
        model.addAttribute("monthList", Constants.MONTH_LIST);
        return "testCase/summary";
    }

    /**
     * 测试用例列表
     *
     * @param testCase
     * @param model
     * @param pn
     * @param pc
     * @return java.lang.String
     */
    @GetMapping("/list")
    public String caseList(@ModelAttribute @Validated TestCase testCase, Model model, String proId,
                           @RequestParam(value = "pn", defaultValue = "1") Integer pn,
                           @RequestParam(value = "pc", defaultValue = "10") Integer pc) {
        if (Objects.nonNull(proId)) {
            testCase.setCaseProjectId(proId);
        }
        final Page<TestCase> testCasePage = caseService.searchCase(testCase, pn, pc);
        final List<TestCase> records = testCasePage.getRecords();
        List<TestCase> recordList = new LinkedList<>();
        for (TestCase record : records) {
            final IssueProject projectById = projectService.getProjectById(record.getCaseProjectId());
            record.setCaseProjectId(projectById.getProjectName());
            record.setCaseModuleId(moduleService.getModuleById(record.getCaseModuleId()).getModuleName());
            recordList.add(record);
        }
        model.addAttribute("records", recordList);
        model.addAttribute("casePageList", testCasePage);
        model.addAttribute("proMap", projectService.projectsMap());
        model.addAttribute("monthList", Constants.MONTH_LIST);
        return "testCase/case_list";
    }

    @GetMapping("/search")
    public String searchCase(Model model) {
        return "redirect:/case/list";
    }

    @RequestMapping("/addCase")
    public String addCase(@ModelAttribute @Validated TestCase testCase) {
        caseService.addCase(testCase);
        return "redirect:/case/list";
    }

    @RequestMapping("/editCase")
    public String editCase(@ModelAttribute @Validated TestCase testCase) {
        caseService.upgradeCaseInfo(testCase);
        return "redirect:/case/list";
    }

    @RequestMapping("/export")
    public void exportCase(HttpServletResponse response, @ModelAttribute @Validated TestCase testCase) {
        final List<TestCase> results = caseService.caseList4export(testCase);
        long t1 = System.currentTimeMillis();
        ExcelUtils.writeExcel("测试用例" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")), response, results, TestCase.class);
        long t2 = System.currentTimeMillis();
        System.out.printf("write over! cost:%sms%n", (t2 - t1));
    }

    @PostMapping(value = "/upload")
    public String importCase(@RequestParam("file") MultipartFile file) {
        caseService.insertTestCaseByExcel(file);
        return "redirect:/case/list";
    }
}


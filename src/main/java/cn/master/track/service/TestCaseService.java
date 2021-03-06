package cn.master.track.service;

import cn.master.track.entity.TestCase;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-27
 */
public interface TestCaseService extends IService<TestCase> {

    /**
     * 查询
     *
     * @param testCase  testCase
     * @param pageIndex pageIndex
     * @param pageCount pageCount
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.master.track.entity.TestCase>
     */
    Page<TestCase> searchCase(TestCase testCase, Integer pageIndex, Integer pageCount);

    /**
     * 导出数据查询
     *
     * @param testCase TestCase
     * @return java.util.List<cn.master.track.entity.TestCase>
     */
    List<TestCase> caseList4export(TestCase testCase);

    /**
     * 添加测试用例
     *
     * @param testCase testCase
     */
    void addCase(TestCase testCase);

    /**
     * 更新case信息
     *
     * @param testCase TestCase
     */
    void upgradeCaseInfo(TestCase testCase);

    /**
     * 测试用例根据执行状态统计
     *
     * @param projectId 项目名
     * @param moduleId  模块名称
     * @return java.util.Map<java.lang.String, java.lang.Integer>
     */
    Map<String, Integer> caseStatusMap(String projectId, String moduleId);

    /**
     * 查询测试用例的项目名称，module数量，测试用例数量
     *
     * @return java.util.Map<java.lang.String, java.lang.String>
     */
    List<Map<String, Object>> caseInfoMap();

    /**
     * 导入数据
     *
     * @param file file
     */
    void insertTestCaseByExcel(MultipartFile file);

    Map<String, Map<String, Integer>> caseStatusMap();
}

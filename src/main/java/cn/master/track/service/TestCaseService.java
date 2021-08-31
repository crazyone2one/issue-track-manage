package cn.master.track.service;

import cn.master.track.entity.TestCase;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @param testCase
     * @param pageIndex
     * @param pageCount
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.master.track.entity.TestCase>
     */
    Page<TestCase> searchCase(TestCase testCase, Integer pageIndex, Integer pageCount);

    /**
     * 数据跳转查询
     *
     * @param caseProjectName 项目名
     * @param caseSuite       模块名称
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.master.track.entity.TestCase>
     */
    Page<TestCase> search4Redirection(String caseProjectName, String caseSuite);

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
     * 查询
     *
     * @param projectName 项目名
     * @param moduleName  模块名称
     * @return cn.master.track.entity.TestCase
     */
    TestCase findTestCaseInfo(String projectName, String moduleName);

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
}

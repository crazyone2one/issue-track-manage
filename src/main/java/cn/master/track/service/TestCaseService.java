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
     * @param testCase testCase
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.master.track.entity.TestCase>
     */
    Page<TestCase> searchCase(TestCase testCase, Integer pageIndex, Integer pageCount);

    /**
     * 数据跳转查询
     *
     * @param caseProjectName
     * @param caseSuite
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
     * 查询测试用例的项目名称，module数量，测试用例数量
     *
     * @return java.util.Map<java.lang.String, java.lang.String>
     */
    List<Map<String, Object>> caseInfoMap();
}

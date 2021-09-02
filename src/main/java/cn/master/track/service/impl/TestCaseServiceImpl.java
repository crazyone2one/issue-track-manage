package cn.master.track.service.impl;

import cn.master.track.entity.IssueProject;
import cn.master.track.entity.TestCase;
import cn.master.track.mapper.CommonMapper;
import cn.master.track.mapper.TestCaseMapper;
import cn.master.track.service.IssueProjectService;
import cn.master.track.service.TestCaseService;
import cn.master.track.util.ExcelUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-27
 */
@Service
public class TestCaseServiceImpl extends ServiceImpl<TestCaseMapper, TestCase> implements TestCaseService {
    private final IssueProjectService projectService;
    private final CommonMapper commonMapper;

    @Autowired
    public TestCaseServiceImpl(IssueProjectService projectService, CommonMapper commonMapper) {
        this.projectService = projectService;
        this.commonMapper = commonMapper;
    }

    @Override
    public Page<TestCase> searchCase(TestCase testCase, Integer pageIndex, Integer pageCount) {
        QueryWrapper<TestCase> wrapper = new QueryWrapper<>();
        return baseMapper.selectPage(new Page<>(pageIndex, pageCount), wrapper);
    }

    @Override
    public List<TestCase> caseList4export(TestCase testCase) {
        List<TestCase> caseList = new LinkedList<>();
        QueryWrapper<TestCase> wrapper = new QueryWrapper<>();
        baseMapper.selectList(wrapper).forEach(testCase1 -> {
            final IssueProject project = projectService.getProjectById(testCase1.getCaseProjectId(), testCase1.getCaseSuiteId());
            testCase1.setCaseProjectId(project.getProjectName());
            testCase1.setCaseSuiteId(project.getModuleName());
            caseList.add(testCase1);
        });
        return caseList;
    }

    @Override
    public Page<TestCase> search4Redirection(String caseProjectName, String caseSuite) {
        QueryWrapper<TestCase> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(StringUtils.isNotBlank(caseProjectName),
                TestCase::getCaseProjectId, projectService.listProjectsId(caseProjectName));
        wrapper.lambda().like(StringUtils.isNotBlank(caseSuite), TestCase::getCaseSuiteId, caseSuite);
        return baseMapper.selectPage(new Page<>(1, 10), wrapper);
    }

    @Override
    public void addCase(TestCase testCase) {
        final IssueProject issueProject = projectService.addProject(testCase.getCaseProjectId(), testCase.getCaseSuiteId());
        testCase.setCaseProjectId(issueProject.getProjectId());
        testCase.setCaseSuiteId(issueProject.getModuleId());
        testCase.setCreateDate(new Date());
        baseMapper.insert(testCase);
    }

    @Override
    public void upgradeCaseInfo(TestCase testCase) {
        final TestCase caseInfo = findTestCaseInfo(testCase.getCaseProjectId(), testCase.getCaseSuiteId());
        testCase.setCaseSuiteId(caseInfo.getCaseSuiteId());
        testCase.setCaseProjectId(caseInfo.getCaseProjectId());
        testCase.setUpdateDate(new Date());
        baseMapper.updateById(testCase);
    }

    @Override
    public TestCase findTestCaseInfo(String projectName, String moduleName) {
        final IssueProject project = projectService.getProject(projectName, moduleName);
        return baseMapper.selectOne(new QueryWrapper<TestCase>().lambda()
                .eq(StringUtils.isNotBlank(projectName), TestCase::getCaseProjectId, project.getProjectId())
                .eq(StringUtils.isNotBlank(moduleName), TestCase::getCaseSuiteId, project.getModuleId()));
    }

    @Override
    public Map<String, Integer> caseStatusMap(String projectId, String moduleId) {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("total", baseMapper.selectCount(new QueryWrapper<TestCase>().lambda()
                .eq(TestCase::getCaseProjectId, projectId)
                .eq(TestCase::getCaseSuiteId, moduleId)));
        map.put("execute", baseMapper.selectCount(new QueryWrapper<TestCase>().lambda()
                .eq(TestCase::getCaseRun, "1")
                .eq(TestCase::getCaseProjectId, projectId)
                .eq(TestCase::getCaseSuiteId, moduleId)));
        return map;
    }

    @Override
    public List<Map<String, Object>> caseInfoMap() {
        String sql = "SELECT t2.project_name project, COUNT(DISTINCT(case_suite_id)) sc,COUNT(id) cc from test_case t1 " +
                "LEFT JOIN issue_project t2 on t2.project_id = t1.case_project_id GROUP BY t1.case_project_id;";
        return commonMapper.findMapBySql(sql);
    }

    @Override
    public void insertTestCaseByExcel(MultipartFile file) {
        final List<TestCase> caseList = ExcelUtils.readExcel(TestCase.class, file);
        caseList.forEach(this::addCase);
    }
}

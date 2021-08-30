package cn.master.track.service.impl;

import cn.master.track.entity.TestCase;
import cn.master.track.mapper.CommonMapper;
import cn.master.track.mapper.TestCaseMapper;
import cn.master.track.service.IssueProjectService;
import cn.master.track.service.TestCaseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public Page<TestCase> search4Redirection(String caseProjectName, String caseSuite) {
        QueryWrapper<TestCase> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(StringUtils.isNotBlank(caseProjectName),
                TestCase::getCaseProjectId, projectService.listProjectsId(caseProjectName));
        wrapper.lambda().like(StringUtils.isNotBlank(caseSuite), TestCase::getCaseSuiteId, caseSuite);
        return baseMapper.selectPage(new Page<>(1, 10), wrapper);
    }

    @Override
    public void addCase(TestCase testCase) {
        final String projectNameId = projectService.addProjectByName(testCase.getCaseProjectId());
        testCase.setCaseProjectId(projectNameId);
        testCase.setCreateDate(new Date());
        baseMapper.insert(testCase);
    }

    @Override
    public List<Map<String, Object>> caseInfoMap() {
        String sql = "SELECT t2.project_name project, COUNT(DISTINCT(case_suite_id)) sc,COUNT(id) cc from test_case t1 " +
                "LEFT JOIN issue_project t2 on t2.project_id = t1.case_project_id GROUP BY t1.case_project_id;";
        return commonMapper.findMapBySql(sql);
    }
}

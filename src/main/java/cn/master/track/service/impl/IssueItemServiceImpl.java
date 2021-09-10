package cn.master.track.service.impl;

import cn.master.track.entity.IssueItem;
import cn.master.track.entity.IssueProject;
import cn.master.track.entity.IssueSummary;
import cn.master.track.mapper.CommonMapper;
import cn.master.track.mapper.IssueItemMapper;
import cn.master.track.service.IssueItemService;
import cn.master.track.service.IssueProjectService;
import cn.master.track.service.IssueSummaryService;
import cn.master.track.service.SummaryItemRefService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-04
 */
@Slf4j
@Service
public class IssueItemServiceImpl extends ServiceImpl<IssueItemMapper, IssueItem> implements IssueItemService {

    private final IssueSummaryService summaryService;
    private final SummaryItemRefService refService;
    private final IssueProjectService projectService;
    private final CommonMapper commonMapper;

    @Autowired
    public IssueItemServiceImpl(IssueSummaryService summaryService,
                                SummaryItemRefService refService,
                                IssueProjectService projectService,
                                CommonMapper commonMapper) {
        this.summaryService = summaryService;
        this.refService = refService;
        this.projectService = projectService;
        this.commonMapper = commonMapper;
    }

    @Override
    public Page<IssueItem> pageItems(IssueItem issueItem, Integer pageIndex, Integer pageCount) {
        final QueryWrapper<IssueItem> wrapper = getWrapper(issueItem);
        return baseMapper.selectPage(new Page<>(pageIndex, pageCount), wrapper);
    }

    private QueryWrapper<IssueItem> getWrapper(IssueItem issueItem) {
        final QueryWrapper<IssueItem> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(issueItem.getProjectCode())) {
            List<String> issueIds = new ArrayList<>();
            fuzzyQueryByProjectName(issueItem.getProjectCode()).forEach(temp -> issueIds.add(temp.getProjectCode()));
            wrapper.lambda().in(IssueItem::getProjectCode, issueIds);
        }
        if (StringUtils.isNotEmpty(issueItem.getSeverity())) {
            wrapper.lambda().eq(IssueItem::getSeverity, issueItem.getSeverity());
        }
        if (StringUtils.isNotEmpty(issueItem.getStatus())) {
            wrapper.lambda().eq(IssueItem::getStatus, issueItem.getStatus());
        }
        wrapper.lambda().eq(StringUtils.isNotBlank(issueItem.getIssueDate()), IssueItem::getIssueDate, issueItem.getIssueDate());
        wrapper.orderByDesc("project_code");
        return wrapper;
    }

    @Override
    public List<IssueItem> issueItems(IssueItem issueItem) {
        final QueryWrapper<IssueItem> wrapper = getWrapper(issueItem);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public void saveIssueItem(IssueItem item) {
        final IssueProject issueProject = projectService.addProject(item.getProjectCode(), item.getModule());
        item.setProjectCode(issueProject.getProjectCode());
        item.setModule(issueProject.getModuleId());
        baseMapper.insert(item);
        // 保存任务汇总数据
        final String summaryId = summaryService.addIssueSummary(item);
        // 保存关系数据
        refService.addReference(item, summaryService.findSummaryById(summaryId));
    }

    @Override
    public IssueItem getIssueById(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public List<IssueItem> fuzzyQueryByProjectName(String projectName) {
        String sql = "SELECT project_code FROM issue_project WHERE project_name LIKE '%" + projectName + "%'";
        return baseMapper.selectList(new QueryWrapper<IssueItem>().lambda()
                .in(IssueItem::getProjectCode, commonMapper.findListBySql(sql)));
    }

    @Override
    public Page<IssueSummary> searchSummary(IssueSummary summary, Integer pageIndex, Integer pageCount) {
        final QueryWrapper<IssueSummary> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(summary.getProjectId())) {
            List<String> issueIds = new ArrayList<>();
            fuzzyQueryByProjectName(summary.getProjectId()).forEach(temp -> issueIds.add(temp.getProjectCode()));
            wrapper.lambda().in(IssueSummary::getProjectId, issueIds).orderByDesc(IssueSummary::getProjectId);
        }
        if (StringUtils.isNotEmpty(summary.getJobStatus())) {
            wrapper.lambda().eq(IssueSummary::getJobStatus, summary.getJobStatus());
        }
        wrapper.lambda().eq(StringUtils.isNotBlank(summary.getIssueDate()), IssueSummary::getIssueDate, summary.getIssueDate());
        wrapper.lambda().groupBy(IssueSummary::getProjectId);
        return summaryService.searchSummaryPage(new Page<>(pageIndex, pageCount), wrapper);
    }

    @Override
    public List<IssueSummary> summaryList(IssueSummary summary) {
        final QueryWrapper<IssueSummary> wrapper = new QueryWrapper<>();
        List<String> issueIds = new ArrayList<>();
        if (StringUtils.isNotEmpty(summary.getProjectId())) {
            fuzzyQueryByProjectName(summary.getProjectId()).forEach(temp -> issueIds.add(temp.getProjectCode()));
            wrapper.lambda().in(IssueSummary::getProjectId, issueIds);
        }
        if (StringUtils.isNotEmpty(summary.getJobStatus())) {
            wrapper.lambda().eq(IssueSummary::getJobStatus, summary.getJobStatus());
        }
        wrapper.lambda().eq(StringUtils.isNotBlank(summary.getIssueDate()), IssueSummary::getIssueDate, summary.getIssueDate());
        wrapper.groupBy("project_id", "issue_date");
        wrapper.lambda().orderByDesc(IssueSummary::getProjectId);
        return summaryService.listSummary(wrapper);
    }

    @Override
    public void modifyIssue(IssueItem issueItem) {
        issueItem.setProjectCode(projectService.addProject(issueItem.getProjectCode(), issueItem.getModule()).getProjectCode());
        final IssueItem issueItem1 = baseMapper.selectById(issueItem.getId());
        issueItem1.setCreateDate(issueItem.getCreateDate());
        issueItem.setUpdateDate(new Date());
        QueryWrapper<IssueSummary> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(IssueSummary::getProjectId, issueItem.getProjectCode())
                .eq(IssueSummary::getIssueDate, issueItem.getIssueDate());
        final IssueSummary tempSummary = summaryService.findIssueSummary(wrapper);
        baseMapper.updateById(issueItem);
        // 问题单的状态发生变化时更新ref数据
        if (!Objects.equals(issueItem1.getSeverity(), issueItem.getSeverity())) {
            refService.updateReference(issueItem, tempSummary);
        }
    }
}

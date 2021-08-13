package cn.master.track.service.impl;

import cn.master.track.entity.IssueItem;
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

import java.util.*;

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
        final QueryWrapper<IssueItem> wrapper = new QueryWrapper<>();
        List<String> issueIds = new ArrayList<>();
        if (StringUtils.isNotEmpty(issueItem.getProjectId())) {
            fuzzyQueryByProjectName(issueItem.getProjectId()).forEach(temp -> issueIds.add(temp.getProjectId()));
            wrapper.lambda().in(IssueItem::getProjectId, issueIds);
        }
        return baseMapper.selectPage(new Page<>(pageIndex, pageCount), wrapper);
    }

    @Override
    public List<IssueItem> issueItems(IssueItem issueItem) {
        final QueryWrapper<IssueItem> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(issueItem.getProjectId())) {
            List<String> issueIds = new ArrayList<>();
            fuzzyQueryByProjectName(issueItem.getProjectId()).forEach(temp -> issueIds.add(temp.getProjectId()));
            wrapper.lambda().in(IssueItem::getProjectId, issueIds).orderByDesc(IssueItem::getProjectId);
        }
        return baseMapper.selectList(wrapper);
    }

    @Override
    public void saveIssueItem(IssueItem item) {
        final String projectNameId = projectService.addProjectByName(item.getProjectId());
        item.setProjectId(projectNameId);
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
    public Map<String, String> searchIssueMaps(String level, String status, String data, boolean review) {
        QueryWrapper<IssueItem> wrapper = new QueryWrapper<>();
        wrapper.select("project_id,COUNT(severity) count")
                .eq("issue_date", data);
        if (review) {
            wrapper.eq("severity_update", level).eq("status_update", status);
        } else {
            wrapper.eq("severity", level).eq("status", status);
        }
        wrapper.groupBy("project_id").orderByDesc("project_id");
        final List<Map<String, Object>> searchResults = baseMapper.selectMaps(wrapper);
        Map<String, String> countMap1 = new LinkedHashMap<>();
        for (Map<String, Object> searchResult : searchResults) {
            countMap1.put(searchResult.get("project_id").toString(), searchResult.get("count").toString());
        }
        return countMap1;
    }

    @Override
    public List<IssueItem> fuzzyQueryByProjectName(String projectName) {
        String sql = "SELECT id FROM issue_project WHERE project_name LIKE '%" + projectName + "%'";
        return baseMapper.selectList(new QueryWrapper<IssueItem>().lambda()
                .in(IssueItem::getProjectId, commonMapper.findListBySql(sql)));
    }

    @Override
    public Page<IssueSummary> searchSummary(IssueSummary summary, Integer pageIndex, Integer pageCount) {
        final QueryWrapper<IssueSummary> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(summary.getProjectName())) {
            List<String> issueIds = new ArrayList<>();
            fuzzyQueryByProjectName(summary.getProjectName()).forEach(temp -> issueIds.add(temp.getProjectId()));
            wrapper.lambda().in(IssueSummary::getProjectName, issueIds).orderByDesc(IssueSummary::getProjectName);
        }
        if (StringUtils.isNotEmpty(summary.getJobStatus())) {
            wrapper.lambda().eq(IssueSummary::getJobStatus, summary.getJobStatus());
        }
        wrapper.lambda().groupBy(IssueSummary::getProjectName);
        return summaryService.searchSummaryPage(new Page<>(pageIndex, pageCount), wrapper);
    }

    @Override
    public List<IssueSummary> summaryList(IssueSummary summary) {
        final QueryWrapper<IssueSummary> wrapper = new QueryWrapper<>();
        List<String> issueIds = new ArrayList<>();
        if (StringUtils.isNotEmpty(summary.getProjectName())) {
            fuzzyQueryByProjectName(summary.getProjectName()).forEach(temp -> issueIds.add(temp.getProjectId()));
            wrapper.lambda().in(IssueSummary::getProjectName, issueIds).orderByDesc(IssueSummary::getProjectName);
        }
        if (StringUtils.isNotEmpty(summary.getJobStatus())) {
            wrapper.lambda().eq(IssueSummary::getJobStatus, summary.getJobStatus());
        }
        wrapper.lambda().groupBy(IssueSummary::getProjectName);
        return summaryService.listSummary(wrapper);
    }

    @Override
    public void modifyIssue(IssueItem issueItem) {
        issueItem.setProjectId(projectService.getProjectByName(issueItem.getProjectId()).getId());
        issueItem.setUpdateDate(new Date());
        baseMapper.update(issueItem, new QueryWrapper<IssueItem>().lambda().eq(IssueItem::getId, issueItem.getId()));
    }
}

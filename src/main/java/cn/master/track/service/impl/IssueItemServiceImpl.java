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
import org.apache.commons.collections4.MapUtils;
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
    public Page<IssueItem> pageItems(Map<String, Object> params, Integer pageIndex, Integer pageCount) {
        final QueryWrapper<IssueItem> wrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(params)) {
            List<String> issueIds = new ArrayList<>();
            fuzzyQueryByProjectName(params.get("projectName").toString()).forEach(temp -> issueIds.add(temp.getProjectId()));
            wrapper.lambda().in(IssueItem::getProjectId, issueIds).orderByDesc(IssueItem::getProjectId);
        }
        return baseMapper.selectPage(new Page<>(pageIndex, pageCount), wrapper);
    }

    @Override
    public List<IssueItem> issueItems(Map<String, Object> params) {
        final QueryWrapper<IssueItem> wrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(params)) {
            List<String> issueIds = new ArrayList<>();
            fuzzyQueryByProjectName(params.get("projectName").toString()).forEach(temp -> issueIds.add(temp.getProjectId()));
            wrapper.lambda().in(IssueItem::getProjectId, issueIds).orderByDesc(IssueItem::getProjectId);
        }
        return baseMapper.selectList(wrapper);
    }

    @Override
    public void addIssueItem(Map<String, Object> params) {
        // 保存项目
        final String projectNameId = projectService.addProjectByName(params.get("projectName").toString());
        final IssueItem.IssueItemBuilder builder = IssueItem.builder();
        builder
//                .id(UuidUtils.generate())
                .projectId(projectNameId)
                .module(params.get("module").toString())
                .functionDesc(params.get("function").toString())
                .titleDesc(params.get("title").toString())
                .severity(params.get("severity").toString())
                .owner(params.get("owner").toString())
                .status(params.get("status").toString())
                .issueDate(params.get("monthDate").toString())
                .remark(params.get("remark").toString())
                .createDate(new Date());
        final IssueItem item = builder.build();
        // 保存issue
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
    public Page<IssueSummary> searchSummary(Map<String, Object> params, Integer pageIndex, Integer pageCount) {
        final QueryWrapper<IssueSummary> wrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(params)) {
            List<String> issueIds = new ArrayList<>();
            fuzzyQueryByProjectName(params.get("projectName").toString()).forEach(temp -> issueIds.add(temp.getProjectId()));
            wrapper.lambda().in(IssueSummary::getProjectName, issueIds).orderByDesc(IssueSummary::getProjectName);
        }
        wrapper.lambda().groupBy(IssueSummary::getProjectName);
        return summaryService.searchSummaryPage(new Page<>(pageIndex, pageCount), wrapper);
    }

    @Override
    public List<IssueSummary> summaryList(Map<String, Object> params) {
        final QueryWrapper<IssueSummary> wrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(params)) {
            List<String> issueIds = new ArrayList<>();
            fuzzyQueryByProjectName(params.get("projectName").toString()).forEach(temp -> issueIds.add(temp.getProjectId()));
            wrapper.lambda().in(IssueSummary::getProjectName, issueIds).orderByDesc(IssueSummary::getProjectName);
        }
        wrapper.lambda().groupBy(IssueSummary::getProjectName);
        return summaryService.listSummary(wrapper);
    }

    @Override
    public void modifyIssue(Map<String, Object> params) {
        final String tempId = params.get("id").toString();
        final IssueItem.IssueItemBuilder builder = IssueItem.builder();
        builder.id(tempId)
                .projectId(projectService.getProjectByName(params.get("project_id").toString()).getId())
                .module(params.get("module").toString())
                .functionDesc(params.get("function_desc").toString())
                .titleDesc(params.get("title_desc").toString())
                .severityUpdate(params.get("severity").toString())
                .owner(params.get("owner").toString())
                .statusUpdate(params.get("status").toString())
                .remark(params.get("remark").toString())
                .issueDate(params.get("issueDate").toString())
                .updateDate(new Date());
        baseMapper.updateById(builder.build());
    }
}

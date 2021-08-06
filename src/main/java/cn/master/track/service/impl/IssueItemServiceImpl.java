package cn.master.track.service.impl;

import cn.master.track.entity.IssueItem;
import cn.master.track.entity.IssueSummary;
import cn.master.track.mapper.IssueItemMapper;
import cn.master.track.service.IssueItemService;
import cn.master.track.service.IssueSummaryService;
import cn.master.track.service.SummaryItemRefService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.MapUtils;
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
@Service
public class IssueItemServiceImpl extends ServiceImpl<IssueItemMapper, IssueItem> implements IssueItemService {

    private final IssueSummaryService summaryService;
    private final SummaryItemRefService refService;

    @Autowired
    public IssueItemServiceImpl(IssueSummaryService summaryService, SummaryItemRefService refService) {
        this.summaryService = summaryService;
        this.refService = refService;
    }

    @Override
    public Page<IssueItem> pageItems(Integer pageIndex, Integer pageCount) {
        return baseMapper.selectPage(new Page<>(pageIndex, pageCount),
                new QueryWrapper<IssueItem>().lambda());
    }

    @Override
    public void addIssueItem(Map<String, Object> params) {
        final IssueItem.IssueItemBuilder builder = IssueItem.builder();
        builder
//                .id(UuidUtils.generate())
                .projectName(params.get("projectName").toString())
                .module(params.get("module").toString())
                .functionDesc(params.get("function").toString())
                .titleDesc(params.get("title").toString())
                .severity(params.get("severity").toString())
                .owner(params.get("owner").toString())
                .status(params.get("status").toString())
                .data(params.get("date").toString())
                .remark(params.get("remark").toString())
                .createDate(new Date());
        final IssueItem item = builder.build();
        // 保存issue
        baseMapper.insert(item);
        // 保存任务汇总数据
        summaryService.addIssueSummary(item);
        // 保存关系数据
        refService.addReference(item, summaryService.findIssueByProjectId(item.getId()));
    }

    @Override
    public Map<String, Object> searchIssueMaps(String status, String data) {
        Map<String, Object> result = new LinkedHashMap<>();
        // 轻微
        final Integer count1 = baseMapper.selectCount(new QueryWrapper<IssueItem>().lambda()
                .eq(IssueItem::getSeverity, "1")
                .eq(StringUtils.isNotBlank(status), IssueItem::getStatus, status)
                .eq(StringUtils.isNotBlank(data), IssueItem::getData, data));
        final Integer count2 = baseMapper.selectCount(new QueryWrapper<IssueItem>().lambda()
                .eq(IssueItem::getSeverity, "2")
                .eq(StringUtils.isNotBlank(status), IssueItem::getStatus, status)
                .eq(StringUtils.isNotBlank(data), IssueItem::getData, data));
        final Integer count3 = baseMapper.selectCount(new QueryWrapper<IssueItem>().lambda()
                .eq(IssueItem::getSeverity, "3")
                .eq(StringUtils.isNotBlank(status), IssueItem::getStatus, status)
                .eq(StringUtils.isNotBlank(data), IssueItem::getData, data));
        final Integer count4 = baseMapper.selectCount(new QueryWrapper<IssueItem>().lambda()
                .eq(IssueItem::getSeverity, "4")
                .eq(StringUtils.isNotBlank(status), IssueItem::getStatus, status)
                .eq(StringUtils.isNotBlank(data), IssueItem::getData, data));
        result.put("level1", count1);
        result.put("level2", count2);
        result.put("level3", count3);
        result.put("level4", count4);
        result.put("count", count1 + count2 + count3 + count4);
        return result;
    }

    @Override
    public Map<String, IssueItem> issuesMap() {
        Map<String, IssueItem> result = new LinkedHashMap<>();
        baseMapper.selectList(new QueryWrapper<IssueItem>().lambda()).forEach(temp->{
            result.put(temp.getId(), temp);
        });
        return result;
    }

    @Override
    public List<IssueItem> fuzzyQueryByProjectName(String projectName) {
        return baseMapper.selectList(new QueryWrapper<IssueItem>().lambda().like(IssueItem::getProjectName, projectName));
    }

    @Override
    public Page<IssueSummary> searchSummary(Map<String, Object> params, Integer pageIndex, Integer pageCount) {
        final QueryWrapper<IssueSummary> wrapper = new QueryWrapper<>();
        if (MapUtils.isNotEmpty(params)) {
            List<String> issueIds = new ArrayList<>();
            fuzzyQueryByProjectName(params.get("projectName").toString()).forEach(temp -> issueIds.add(temp.getId()));
            wrapper.lambda().in(IssueSummary::getProjectName, issueIds);
        }
        return summaryService.searchSummaryPage(new Page<>(pageIndex, pageCount), wrapper);
    }
}

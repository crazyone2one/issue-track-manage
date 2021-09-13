package cn.master.track.service.impl;

import cn.master.track.config.Constants;
import cn.master.track.entity.IssueItem;
import cn.master.track.entity.IssueSummary;
import cn.master.track.entity.SummaryItemRef;
import cn.master.track.mapper.SummaryItemRefMapper;
import cn.master.track.service.SummaryItemRefService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-04
 */
@Service
public class SummaryItemRefServiceImpl extends ServiceImpl<SummaryItemRefMapper, SummaryItemRef> implements SummaryItemRefService {

    @Override
    public SummaryItemRef findSummaryItemRef(IssueItem issueItem, IssueSummary summary) {
        return baseMapper.selectOne(new QueryWrapper<SummaryItemRef>().lambda()
                .eq(SummaryItemRef::getIssueDate, issueItem.getIssueDate())
                .eq(SummaryItemRef::getItemId, issueItem.getProjectCode())
                .eq(SummaryItemRef::getSummaryId, summary.getId())
        );
    }

    @Override
    public void addReference(IssueItem item, IssueSummary summary) {
        if (StringUtils.isNotEmpty(item.getSeverity())) {
            final SummaryItemRef itemRef = findSummaryItemRef(item, summary);
            if (Objects.nonNull(itemRef)) {
                if (Constants.BUG_LEVEL_1.equals(item.getSeverity())) {
                    itemRef.setCreateBugSlight(itemRef.getCreateBugSlight() + 1);
                }
                if (Constants.BUG_LEVEL_2.equals(item.getSeverity())) {
                    itemRef.setCreateBugOrdinary(itemRef.getCreateBugOrdinary() + 1);
                }
                if (Constants.BUG_LEVEL_3.equals(item.getSeverity())) {
                    itemRef.setCreateBugSeverity(itemRef.getCreateBugSeverity() + 1);
                }
                if (Constants.BUG_LEVEL_4.equals(item.getSeverity())) {
                    itemRef.setCreateBugDeadly(itemRef.getCreateBugDeadly() + 1);
                }
                baseMapper.updateById(itemRef);
            } else {
                final SummaryItemRef.SummaryItemRefBuilder builder = SummaryItemRef.builder();
                builder.itemId(item.getProjectCode()).summaryId(summary.getId()).issueDate(item.getIssueDate());
                if (Constants.BUG_LEVEL_1.equals(item.getSeverity())) {
                    builder.createBugSlight(1);
                }
                if (Constants.BUG_LEVEL_2.equals(item.getSeverity())) {
                    builder.createBugOrdinary(1);
                }
                if (Constants.BUG_LEVEL_3.equals(item.getSeverity())) {
                    builder.createBugSeverity(1);
                }
                if (Constants.BUG_LEVEL_4.equals(item.getSeverity())) {
                    builder.createBugDeadly(1);
                }
                baseMapper.insert(builder.build());
            }
        }
    }

    @Override
    public void updateReference(IssueItem item, IssueSummary summary) {
        final SummaryItemRef itemRef = findSummaryItemRef(item, summary);
        if (Objects.nonNull(itemRef)) {
            if (Constants.BUG_LEVEL_1.equals(item.getSeverity())) {
                itemRef.setReviewBugSlight(itemRef.getReviewBugSlight() + 1);
            }
            if (Constants.BUG_LEVEL_2.equals(item.getSeverity())) {
                itemRef.setReviewBugOrdinary(itemRef.getReviewBugOrdinary() + 1);
            }
            if (Constants.BUG_LEVEL_3.equals(item.getSeverity())) {
                itemRef.setReviewBugSeverity(itemRef.getReviewBugSeverity() + 1);
            }
            if (Constants.BUG_LEVEL_4.equals(item.getSeverity())) {
                itemRef.setReviewBugDeadly(itemRef.getReviewBugDeadly() + 1);
            }
            baseMapper.updateById(itemRef);
        }
    }

    @Override
    public Map<String, SummaryItemRef> refsMap() {
        Map<String, SummaryItemRef> result = new LinkedHashMap<>();
        baseMapper.selectList(new QueryWrapper<>()).forEach(r->{
            result.put(r.getSummaryId(), r);
        });
        return result;
    }
}

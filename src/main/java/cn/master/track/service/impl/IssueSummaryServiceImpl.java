package cn.master.track.service.impl;

import cn.master.track.entity.IssueItem;
import cn.master.track.entity.IssueSummary;
import cn.master.track.mapper.IssueSummaryMapper;
import cn.master.track.service.IssueSummaryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
public class IssueSummaryServiceImpl extends ServiceImpl<IssueSummaryMapper, IssueSummary> implements IssueSummaryService {

    @Override
    public String addIssueSummary(IssueItem item) {
        final IssueSummary issueSummary = findIssueSummary(item);
        final IssueSummary.IssueSummaryBuilder builder = IssueSummary.builder();
        if (Objects.isNull(issueSummary)) {
            final IssueSummary summary = builder.projectId(item.getProjectId())
                    .createCaseCount(0)
                    .executeCaseCount(0)
                    .deliveryStatus("0")
                    .issueDate(item.getIssueDate())
                    .createDate(new Date())
                    .build();
            baseMapper.insert(summary);
            return summary.getSummaryId();
        } else {
            builder.projectId(item.getProjectId()).issueDate(item.getIssueDate()).updateDate(new Date());
            baseMapper.updateById(builder.build());
            return issueSummary.getSummaryId();
        }
    }

    @Override
    public IssueSummary findIssueSummary(IssueItem issueItem) {
        return baseMapper.selectOne(new QueryWrapper<IssueSummary>().lambda()
                .eq(IssueSummary::getProjectId, issueItem.getProjectId())
                .eq(IssueSummary::getIssueDate,issueItem.getIssueDate()));
    }

    @Override
    public IssueSummary findIssueSummary(QueryWrapper<IssueSummary> wrapper) {
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public IssueSummary findSummaryById(String id) {
        return baseMapper.selectOne(new QueryWrapper<IssueSummary>().lambda()
                .eq(IssueSummary::getSummaryId, id));
    }

    @Override
    public Page<IssueSummary> searchSummaryPage(Page<IssueSummary> page, QueryWrapper<IssueSummary> wrapper) {
        log.debug("分页查询汇总数据");
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public List<IssueSummary> listSummary(QueryWrapper<IssueSummary> wrapper) {
        log.debug("查询汇总数据");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public void modifySummary(IssueSummary summary) {
        summary.setProjectId(findSummaryById(summary.getSummaryId()).getProjectId());
        summary.setUpdateDate(new Date());
        baseMapper.update(summary, new QueryWrapper<IssueSummary>().lambda().eq(IssueSummary::getSummaryId, summary.getSummaryId()));
    }

}

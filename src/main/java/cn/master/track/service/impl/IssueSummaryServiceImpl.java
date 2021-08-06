package cn.master.track.service.impl;

import cn.master.track.entity.IssueItem;
import cn.master.track.entity.IssueSummary;
import cn.master.track.mapper.IssueSummaryMapper;
import cn.master.track.service.IssueSummaryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-04
 */
@Service
public class IssueSummaryServiceImpl extends ServiceImpl<IssueSummaryMapper, IssueSummary> implements IssueSummaryService {

    @Override
    public void addIssueSummary(IssueItem item) {
        final IssueSummary summary = IssueSummary.builder()
//                .id(UuidUtils.generate())
                .projectName(item.getId())
                .createCaseCount("0")
                .executeCaseCount("0")
                .bugDoc("0")
                .reportDoc("0")
                .hasDoc("0")
                .deliveryStatus("0")
                .createDate(new Date())
                .build();
        baseMapper.insert(summary);
    }

    @Override
    public IssueSummary findIssueByProjectId(String id) {
        return baseMapper.selectOne(new QueryWrapper<IssueSummary>().lambda()
                .eq(IssueSummary::getProjectName, id));
    }

    @Override
    public IssueSummary findSummaryById(String id) {
        return baseMapper.selectOne(new QueryWrapper<IssueSummary>().lambda()
                .eq(IssueSummary::getId, id));
    }

    @Override
    public Page<IssueSummary> pageSummary(Integer pageIndex, Integer pageCount) {
        return baseMapper.selectPage(new Page<>(pageIndex, pageCount), new QueryWrapper<IssueSummary>().lambda());
    }

    @Override
    public Page<IssueSummary> searchSummaryPage(Page<IssueSummary> page, QueryWrapper<IssueSummary> wrapper) {
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public void modifySummary(Map<String, Object> params) {
        final String tempId = params.get("id").toString();
        final IssueSummary.IssueSummaryBuilder builder = IssueSummary.builder();
        builder.id(tempId)
                .projectName(findSummaryById(tempId).getProjectName())
                .jobDesc(params.get("jobDesc").toString())
                .createCaseCount(params.get("createCase").toString())
                .executeCaseCount(params.get("executeCase").toString())
                .bugDoc(params.get("bugDoc").toString())
                .reportDoc(params.get("reportDoc").toString())
                .hasDoc(params.get("hasDoc").toString())
                .jobStatus(params.get("jobStatus").toString())
                .deliveryStatus(params.get("deliveryStatus").toString())
                .owner(params.get("owner").toString())
                .remark(params.get("remark").toString())
                .updateDate(new Date());
        baseMapper.update(builder.build(), new QueryWrapper<IssueSummary>().lambda().eq(IssueSummary::getId, tempId));
    }
}

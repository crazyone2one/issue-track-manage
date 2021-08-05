package cn.master.track.service.impl;

import cn.master.track.entity.IssueItem;
import cn.master.track.entity.IssueSummary;
import cn.master.track.entity.SummaryItemRef;
import cn.master.track.mapper.SummaryItemRefMapper;
import cn.master.track.service.SummaryItemRefService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-04
 */
@Service
public class SummaryItemRefServiceImpl extends ServiceImpl<SummaryItemRefMapper, SummaryItemRef> implements SummaryItemRefService {

    @Override
    public void addReference(IssueItem item, IssueSummary summary) {
        baseMapper.insert(SummaryItemRef.builder()
//                .id(UuidUtils.generate())
                .itemId(item.getId()).summaryId(summary.getId()).build());
    }
}

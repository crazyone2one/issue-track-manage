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
                .createDate(new Date())
                .build();
        baseMapper.insert(summary);
    }

    @Override
    public IssueSummary findIssueById(String id) {
        return baseMapper.selectOne(new QueryWrapper<IssueSummary>().lambda()
                .eq(IssueSummary::getProjectName, id));
    }

    @Override
    public Page<IssueSummary> pageSummary(Integer pageIndex, Integer pageCount) {
        return baseMapper.selectPage(new Page<>(pageIndex, pageCount), new QueryWrapper<IssueSummary>().lambda());
    }
}

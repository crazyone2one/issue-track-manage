package cn.master.track.service;

import cn.master.track.entity.IssueItem;
import cn.master.track.entity.IssueSummary;
import cn.master.track.entity.SummaryItemRef;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-04
 */
public interface SummaryItemRefService extends IService<SummaryItemRef> {

    void addReference(IssueItem item, IssueSummary summary);
}

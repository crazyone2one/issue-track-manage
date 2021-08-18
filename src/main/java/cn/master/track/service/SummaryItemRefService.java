package cn.master.track.service;

import cn.master.track.entity.IssueItem;
import cn.master.track.entity.IssueSummary;
import cn.master.track.entity.SummaryItemRef;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-04
 */
public interface SummaryItemRefService extends IService<SummaryItemRef> {

    /**
     * 查询
     *
     * @param issueItem 问题单
     * @param summary   问题汇总
     * @return cn.master.track.entity.SummaryItemRef
     */
    SummaryItemRef findSummaryItemRef(IssueItem issueItem, IssueSummary summary);

    /**
     * 保存关系
     *
     * @param item    问题单
     * @param summary 问题汇总
     */
    void addReference(IssueItem item, IssueSummary summary);

    /**
     * 更新数关系
     *
     * @param item    问题单
     * @param summary 问题汇总
     */
    void updateReference(IssueItem item, IssueSummary summary);

    /**
     * 关系表
     *
     * @return java.util.Map<java.lang.String, cn.master.track.entity.SummaryItemRef>
     */
    Map<String, SummaryItemRef> refsMap();
}

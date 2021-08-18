package cn.master.track.service;

import cn.master.track.entity.IssueItem;
import cn.master.track.entity.IssueSummary;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-04
 */
public interface IssueSummaryService extends IService<IssueSummary> {
    /**
     * 添加问题单时自动创建任务汇总数据，返回汇总数据主键id
     *
     * @param item 问题单
     * @return java.lang.String
     */
    String addIssueSummary(IssueItem item);

    IssueSummary findIssueSummary(IssueItem issueItem);

    IssueSummary findIssueSummary(QueryWrapper<IssueSummary> wrapper);

    /**
     * 通过主键id查询任务汇总数据
     *
     * @param id 任务汇总主键id
     * @return cn.master.track.entity.IssueSummary
     */
    IssueSummary findSummaryById(String id);

    /**
     * 分页查询任务汇总数据
     *
     * @param page    分页参数
     * @param wrapper 查询条件
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.master.track.entity.IssueSummary>
     */
    Page<IssueSummary> searchSummaryPage(Page<IssueSummary> page, QueryWrapper<IssueSummary> wrapper);

    /**
     * 查询任务汇总数据
     *
     * @param wrapper 查询条件
     * @return java.util.List<cn.master.track.entity.IssueSummary>
     */
    List<IssueSummary> listSummary(QueryWrapper<IssueSummary> wrapper);

    /**
     * 更新汇总数据
     *
     * @param summary IssueSummary
     */
    void modifySummary(IssueSummary summary);

}

package cn.master.track.service;

import cn.master.track.entity.IssueItem;
import cn.master.track.entity.IssueSummary;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public interface IssueSummaryService extends IService<IssueSummary> {
    /**
     * 添加问题单时自动创建任务汇总数据
     *
     * @param item 问题单
     */
    void addIssueSummary(IssueItem item);

    /**
     * 通过问题单的主键id查询任务汇总数据
     *
     * @param id 问题单主键id
     * @return cn.master.track.entity.IssueSummary
     */
    IssueSummary findIssueByProjectId(String id);

    /**
     * 通过主键id查询任务汇总数据
     *
     * @param id 任务汇总主键id
     * @return cn.master.track.entity.IssueSummary
     */
    IssueSummary findSummaryById(String id);

    Page<IssueSummary> pageSummary(Integer pageIndex, Integer pageCount);

    /**
     * 查询任务汇总数据
     *
     * @param page
     * @param wrapper
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.master.track.entity.IssueSummary>
     */
    Page<IssueSummary> searchSummaryPage(Page<IssueSummary> page, QueryWrapper<IssueSummary> wrapper);

    /**
     * 修改任务汇总数据
     *
     * @param params
     */
    void modifySummary(Map<String, Object> params);
}

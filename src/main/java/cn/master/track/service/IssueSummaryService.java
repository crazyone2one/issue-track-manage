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
     * 添加问题单时自动创建任务汇总数据，返回汇总数据主键id
     *
     * @param item 问题单
     * @return java.lang.String
     */
    String addIssueSummary(IssueItem item);

    /**
     * 通过主键id查询任务汇总数据
     *
     * @param id 任务汇总主键id
     * @return cn.master.track.entity.IssueSummary
     */
    IssueSummary findSummaryById(String id);

    /**
     * 查询任务汇总数据
     *
     * @param page 分页参数
     * @param wrapper 查询条件
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.master.track.entity.IssueSummary>
     */
    Page<IssueSummary> searchSummaryPage(Page<IssueSummary> page, QueryWrapper<IssueSummary> wrapper);

    /**
     * 修改任务汇总数据
     *
     * @param params 前端传来的参数
     */
    void modifySummary(Map<String, Object> params);

    /**
     * 统计总数
     *
     * @param m1
     * @param m2
     * @param m3
     * @param m4
     * @return java.util.Map<java.lang.String, java.lang.String>
     */
    Map<String, String> totalCount(Map<String, String> m1, Map<String, String> m2, Map<String, String> m3, Map<String, String> m4);
}

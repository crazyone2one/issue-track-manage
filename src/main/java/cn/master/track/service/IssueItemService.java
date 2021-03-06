package cn.master.track.service;

import cn.master.track.entity.IssueItem;
import cn.master.track.entity.IssueSummary;
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
public interface IssueItemService extends IService<IssueItem> {
    /**
     * 分页查询
     *
     * @param issueItem IssueItem
     * @param pageIndex
     * @param pageCount
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.master.track.entity.IssueItem>
     */
    Page<IssueItem> pageItems(IssueItem issueItem, Integer pageIndex, Integer pageCount);

    /**
     * 查询数据
     *
     * @param issueItem IssueItem
     * @return java.util.List<cn.master.track.entity.IssueItem>
     */
    List<IssueItem> issueItems(IssueItem issueItem);

    /**
     * 保存issue
     *
     * @param item IssueItem
     */
    void saveIssueItem(IssueItem item);

    /**
     * 查询issue
     *
     * @param id 主键id
     * @return cn.master.track.entity.IssueItem
     */
    IssueItem getIssueById(String id);

    /**
     * 按照项目名称查询
     *
     * @param projectName 项目名称
     * @return java.util.List<cn.master.track.entity.IssueItem>
     */
    List<IssueItem> fuzzyQueryByProjectName(String projectName);

    /**
     * 分页查询汇总数据
     *
     * @param summary   IssueSummary
     * @param pageIndex
     * @param pageCount
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.master.track.entity.IssueSummary>
     */
    Page<IssueSummary> searchSummary(IssueSummary summary, Integer pageIndex, Integer pageCount);

    /**
     * 查询汇总数据
     *
     * @param summary IssueSummary
     * @return java.util.List<cn.master.track.entity.IssueSummary>
     */
    List<IssueSummary> summaryList(IssueSummary summary);

    /**
     * 更新issue
     *
     * @param issueItem IssueItem
     */
    void modifyIssue(IssueItem issueItem);
}

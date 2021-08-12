package cn.master.track.service;

import cn.master.track.entity.IssueItem;
import cn.master.track.entity.IssueSummary;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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
     *
     * @param params
     * @param pageIndex
     * @param pageCount
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.master.track.entity.IssueItem>
     */
    Page<IssueItem> pageItems(Map<String, Object> params, Integer pageIndex, Integer pageCount);

    /**
     * 添加issue数据
     *
     * @param params
     */
    void addIssueItem(Map<String, Object> params);

    IssueItem getIssueById(String id);

    /**
     * 查询issue
     *
     *
     * @param level 严重程度
     * @param status 状态
     * @param data 月份
     * @param review
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    Map<String, String> searchIssueMaps(String level, String status, String data, boolean review);

    /**
     * 按照项目名称查询
     *
     * @param projectName 项目名称
     * @return java.util.List<cn.master.track.entity.IssueItem>
     */
    List<IssueItem> fuzzyQueryByProjectName(String projectName);

    /**
     * 任务汇总查询
     *
     * @param params
     * @param pageIndex
     * @param pageCount
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.master.track.entity.IssueSummary>
     */
    Page<IssueSummary> searchSummary(Map<String, Object> params, Integer pageIndex, Integer pageCount);

    /**
     * 更新issue
     *
     * @param params 参数
     */
    void modifyIssue(Map<String, Object> params);
}

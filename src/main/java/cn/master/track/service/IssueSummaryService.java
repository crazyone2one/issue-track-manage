package cn.master.track.service;

import cn.master.track.entity.IssueItem;
import cn.master.track.entity.IssueSummary;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-04
 */
public interface IssueSummaryService extends IService<IssueSummary> {

    void addIssueSummary(IssueItem item);

    IssueSummary findIssueById(String id);

    Page<IssueSummary> pageSummary(Integer pageIndex, Integer pageCount);
}

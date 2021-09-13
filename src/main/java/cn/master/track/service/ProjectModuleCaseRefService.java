package cn.master.track.service;

import cn.master.track.entity.IssueModule;
import cn.master.track.entity.IssueProject;
import cn.master.track.entity.ProjectModuleCaseRef;
import cn.master.track.entity.TestCase;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-27
 */
public interface ProjectModuleCaseRefService extends IService<ProjectModuleCaseRef> {

    void appendRef(IssueProject project, IssueModule module, TestCase testCase);
}

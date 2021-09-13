package cn.master.track.service;

import cn.master.track.entity.IssueModule;
import cn.master.track.entity.IssueProject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 11's papa
 * @since 2021-09-11
 */
public interface IssueModuleService extends IService<IssueModule> {

    /**
     * 添加模块数据
     *
     * @param project    项目
     * @param moduleName 模块名称
     * @return cn.master.track.entity.IssueModule
     */
    IssueModule addModule(IssueProject project, String moduleName);

    /**
     * 验证模块数据是否存在
     *
     * @param issueProject 项目
     * @param moduleName   模块名称
     * @return cn.master.track.entity.IssueModule
     */
    IssueModule checkModule(IssueProject issueProject, String moduleName);

    IssueModule getModuleByName(String moduleName);

    IssueModule getModuleById(String moduleId);
}

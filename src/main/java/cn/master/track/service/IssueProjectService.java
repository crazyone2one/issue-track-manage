package cn.master.track.service;

import cn.master.track.entity.IssueProject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 问题单对应的项目表 服务类
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-10
 */
public interface IssueProjectService extends IService<IssueProject> {

    /**
     * 创建项目数据
     *
     * @param name 项目名称
     * @return java.lang.String
     */
    String addProjectByName(String name);

    /**
     * 查询项目
     *
     * @param name 项目名称
     * @return cn.master.track.entity.IssueProject
     */
    IssueProject getProjectByName(String name);

    IssueProject getProjectById(String id);

    /**
     * 项目信息map
     *
     * @return java.util.Map<java.lang.String, cn.master.track.entity.IssueProject>
     */
    Map<String, IssueProject> projectsMap();
}

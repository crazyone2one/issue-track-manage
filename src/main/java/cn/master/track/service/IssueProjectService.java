package cn.master.track.service;

import cn.master.track.entity.IssueProject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
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
     * 添加项目信息。
     * 先根据条件查询，如果查询到直接返回查询到的结果。如果没有查询到相对应的数据，则添加然后返回新添加的数据
     *
     * @param projectName 项目名称
     * @param moduleName  模块名称
     * @return java.lang.String
     */
    IssueProject addProject(String projectName, String moduleName);

    /**
     * 查询项目
     *
     * @param name 项目名称
     * @return cn.master.track.entity.IssueProject
     */
    IssueProject getProjectByName(String name);

    /**
     * 查询项目
     *
     * @param projectName 项目名称
     * @return cn.master.track.entity.IssueProject
     */
    IssueProject checkProject(String projectName);

    /**
     * 查询项目 by id
     *
     * @param projectId 项目名称id
     * @param moduleId  模块名称id
     * @return cn.master.track.entity.IssueProject
     */
    IssueProject getProjectById(String projectId, String moduleId);

    /**
     * 查询项目
     *
     * @param name 项目名称
     * @return java.util.List<cn.master.track.entity.IssueProject>
     */
    List<IssueProject> listProject(String name);

    /**
     * 查询项目id
     *
     * @param name 项目名称
     * @return java.util.List<java.lang.String>
     */
    List<String> listProjectsId(String name);

    /**
     * 通过id查询project信息
     *
     * @param id 主键id
     * @return cn.master.track.entity.IssueProject
     */
    IssueProject getProjectById(String id);

    /**
     * 项目信息map
     *
     * @return java.util.Map<java.lang.String, cn.master.track.entity.IssueProject>
     */
    Map<String, IssueProject> projectsMap();

    boolean checkProjectByNameAndModule(String projectName, String moduleName);
}

package cn.master.track.service.impl;

import cn.master.track.entity.IssueModule;
import cn.master.track.entity.IssueProject;
import cn.master.track.mapper.IssueProjectMapper;
import cn.master.track.service.IssueModuleService;
import cn.master.track.service.IssueProjectService;
import cn.master.track.util.UuidUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 问题单对应的项目表 服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-10
 */
@Service
public class IssueProjectServiceImpl extends ServiceImpl<IssueProjectMapper, IssueProject> implements IssueProjectService {

    private final IssueModuleService moduleService;

    @Autowired
    public IssueProjectServiceImpl(IssueModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @Override
    public IssueProject addProject(String projectName, String moduleName) {
        final IssueProject project = checkProject(projectName);
        /*1. 判断根据名称是否可查询到相关的项目*/
        if (Objects.nonNull(project)) {
            final IssueModule issueModule = moduleService.checkModule(project, moduleName);
            if (Objects.isNull(issueModule)) {
                /*2 判断项目下是存在模块数据,不存在模块数据则创建*/
                moduleService.addModule(project, moduleName);
                return checkProject(projectName);
            }
            return project;
        }
        /*未查询到对应的项目数据,新创建*/
        final IssueProject.IssueProjectBuilder builder = IssueProject.builder();
        builder.projectName(projectName.trim());
        builder.projectCode(UuidUtils.generate()).createData(new Date());
        baseMapper.insert(builder.build());
        final IssueProject issueProject = checkProject(projectName);
        moduleService.addModule(issueProject, moduleName);
        return issueProject;
    }

    @Override
    public IssueProject getProjectByName(String name) {
        return baseMapper.selectOne(new QueryWrapper<IssueProject>().lambda()
                .eq(IssueProject::getProjectName, name));
    }

    @Override
    public IssueProject checkProject(String projectName) {
        return baseMapper.selectOne(new QueryWrapper<IssueProject>().lambda()
                .eq(StringUtils.isNotBlank(projectName), IssueProject::getProjectName, projectName.trim()));
    }

    @Override
    public IssueProject getProjectById(String projectId, String moduleId) {
        QueryWrapper<IssueProject> wrapper = new QueryWrapper<>();
        wrapper.select(" * ")
                .eq(StringUtils.isNotBlank(projectId), "project_code", projectId.trim())
                .eq(StringUtils.isNotBlank(moduleId), "module_id", moduleId.trim());
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public List<IssueProject> listProject(String name) {
        QueryWrapper<IssueProject> wrapper = new QueryWrapper<>();
        wrapper.select("DISTINCT(project_code)").like(StringUtils.isNotBlank(name), "project_name", name);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<String> listProjectsId(String name) {
        List<String> tempList = new ArrayList<>();
        listProject(name).forEach(temp -> tempList.add(temp.getProjectCode()));
        return tempList;
    }

    @Override
    public IssueProject getProjectById(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public Map<String, IssueProject> projectsMap() {
        Map<String, IssueProject> map = new LinkedHashMap<>();
        baseMapper.selectList(new QueryWrapper<>()).forEach(temp -> map.put(temp.getId(), temp));
        return map;
    }

    @Override
    public boolean checkProjectByNameAndModule(String projectName, String moduleName) {
        boolean flag = false;
        final IssueProject project = getProjectByName(projectName);
        if (Objects.nonNull(project)) {
            final IssueModule issueModule = moduleService.checkModule(project, moduleName);
            flag = !Objects.isNull(issueModule);
        }
        return flag;
    }

}

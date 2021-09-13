package cn.master.track.service.impl;

import cn.master.track.entity.IssueModule;
import cn.master.track.entity.IssueProject;
import cn.master.track.mapper.IssueModuleMapper;
import cn.master.track.service.IssueModuleService;
import cn.master.track.util.UuidUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2021-09-11
 */
@Service
public class IssueModuleServiceImpl extends ServiceImpl<IssueModuleMapper, IssueModule> implements IssueModuleService {

    @Override
    public IssueModule addModule(IssueProject project, String moduleName) {
        final IssueModule module = IssueModule.builder()
                .moduleName(moduleName).moduleCode(UuidUtils.generate())
                .projectId(project.getId())
                .createDate(new Date()).build();
        baseMapper.insert(module);
        return getModuleByName(moduleName);
    }

    @Override
    public IssueModule checkModule(IssueProject issueProject, String moduleName) {
        return baseMapper.selectOne(new QueryWrapper<IssueModule>().lambda()
                .eq(IssueModule::getProjectId,issueProject.getId())
                .eq(IssueModule::getModuleName,moduleName));
    }

    @Override
    public IssueModule getModuleByName(String moduleName) {
        return baseMapper.selectOne(new QueryWrapper<IssueModule>().lambda().eq(IssueModule::getModuleName, moduleName));
    }

    @Override
    public IssueModule getModuleById(String moduleId) {
        return baseMapper.selectById(moduleId);
    }
}

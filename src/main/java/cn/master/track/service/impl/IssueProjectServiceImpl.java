package cn.master.track.service.impl;

import cn.master.track.entity.IssueProject;
import cn.master.track.mapper.IssueProjectMapper;
import cn.master.track.service.IssueProjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public String addProjectByName(String name) {
        final IssueProject project = baseMapper.selectOne(new QueryWrapper<IssueProject>().lambda()
                .eq(IssueProject::getProjectName, name.trim()));
        if (Objects.nonNull(project)) {
            return project.getProjectId();
        }
        final IssueProject.IssueProjectBuilder builder = IssueProject.builder();
        builder.projectName(name.trim()).projectCode("").createData(new Date());
        baseMapper.insert(builder.build());
        return baseMapper.selectOne(new QueryWrapper<IssueProject>().lambda()
                .eq(IssueProject::getProjectName, name.trim())).getProjectId();
    }

    @Override
    public IssueProject getProjectByName(String name) {
        return baseMapper.selectOne(new QueryWrapper<IssueProject>().lambda()
                .eq(IssueProject::getProjectName, name.trim()));
    }

    @Override
    public IssueProject getProjectById(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public Map<String, IssueProject> projectsMap() {
        Map<String, IssueProject> map = new LinkedHashMap<>();
        baseMapper.selectList(new QueryWrapper<>()).forEach(temp -> {
            map.put(temp.getProjectId(), temp);
        });
        return map;
    }
}

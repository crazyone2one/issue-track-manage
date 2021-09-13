package cn.master.track.service.impl;

import cn.master.track.entity.IssueModule;
import cn.master.track.entity.IssueProject;
import cn.master.track.entity.ProjectModuleCaseRef;
import cn.master.track.entity.TestCase;
import cn.master.track.mapper.ProjectModuleCaseRefMapper;
import cn.master.track.service.ProjectModuleCaseRefService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-27
 */
@Service
public class ProjectModuleCaseRefServiceImpl extends ServiceImpl<ProjectModuleCaseRefMapper, ProjectModuleCaseRef> implements ProjectModuleCaseRefService {

    @Override
    public void appendRef(IssueProject project, IssueModule module, TestCase testCase) {
        baseMapper.insert(ProjectModuleCaseRef.builder().projectId(project.getId()).moduleId(module.getId()).caseId(testCase.getId()).build());
    }
}

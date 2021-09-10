package cn.master.track.service.impl;

import cn.master.track.entity.TestCaseSteps;
import cn.master.track.mapper.TestCaseStepsMapper;
import cn.master.track.service.TestCaseStepsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2021-09-09
 */
@Service
public class TestCaseStepsServiceImpl extends ServiceImpl<TestCaseStepsMapper, TestCaseSteps> implements TestCaseStepsService {

    @Override
    public void addCaseSteps(TestCaseSteps caseSteps) {
        baseMapper.insert(caseSteps);
    }
}

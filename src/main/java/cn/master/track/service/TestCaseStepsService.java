package cn.master.track.service;

import cn.master.track.entity.TestCaseSteps;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 11's papa
 * @since 2021-09-09
 */
public interface TestCaseStepsService extends IService<TestCaseSteps> {

    void addCaseSteps(TestCaseSteps caseSteps);
}

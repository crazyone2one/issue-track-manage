package cn.master.track.mapper;

import cn.master.track.entity.IssueModule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 11's papa
 * @since 2021-09-11
 */
@Mapper
public interface IssueModuleMapper extends BaseMapper<IssueModule> {
    /**
     * 查询项目关联的模块数据
     *
     * @param projectCode 项目code
     * @return java.util.List<cn.master.track.entity.IssueModule>
     */
    @Select("select * from issue_module where project_id=#{projectCode}")
    List<IssueModule> listModulesOnProject(String projectCode);
}

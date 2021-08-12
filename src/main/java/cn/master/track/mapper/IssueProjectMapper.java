package cn.master.track.mapper;

import cn.master.track.entity.IssueProject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 问题单对应的项目表 Mapper 接口
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-10
 */
@Mapper
public interface IssueProjectMapper extends BaseMapper<IssueProject> {

}

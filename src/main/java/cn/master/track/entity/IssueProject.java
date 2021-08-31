package cn.master.track.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 问题单对应的项目表
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("issue_project")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssueProject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "project_id", type = IdType.ASSIGN_UUID)
    private String projectId;

    /**
     * 项目名称
     */
    @TableField("project_name")
    private String projectName;

    /**
     * 项目code
     */
    @TableField("project_code")
    private String projectCode;
    /**
     * 模块名称
     */
    @TableField("module_name")
    private String moduleName;
    /**
     * 模块名称id
     */
    @TableField(value = "module_id")
    private String moduleId;

    /**
     * 创建时间
     */
    @TableField("create_data")
    private Date createData;


}

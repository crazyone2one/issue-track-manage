package cn.master.track.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 11's papa
 * @since 2021-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("issue_module")
@Builder
@AllArgsConstructor
public class IssueModule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 模块名称
     */
    @TableField("module_name")
    private String moduleName;

    /**
     * 模块id
     */
    @TableField("module_code")
    private String moduleCode;

    /**
     * 项目id
     */
    @TableField("project_id")
    private String projectId;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date createDate;


}

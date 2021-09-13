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

/**
 * <p>
 *
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("project_module_case_ref")
@Builder
@AllArgsConstructor
public class ProjectModuleCaseRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("project_id")
    private String projectId;

    @TableField("case_id")
    private String caseId;

    @TableField("module_id")
    private String moduleId;

}

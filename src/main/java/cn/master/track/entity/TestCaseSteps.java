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
 * @since 2021-09-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_case_steps")
@Builder
@AllArgsConstructor
public class TestCaseSteps implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 测试用例id
     */
    @TableField("case_id")
    private String caseId;

    /**
     * 排序
     */
    @TableField("case_order")
    private Integer caseOrder;

    /**
     * 步骤
     */
    @TableField("case_step")
    private String caseStep;

    /**
     * 预期结果
     */
    @TableField("case_step_result")
    private String caseStepResult;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date createDate;

    /**
     * 修改时间
     */
    @TableField("update_date")
    private Date updateDate;


}

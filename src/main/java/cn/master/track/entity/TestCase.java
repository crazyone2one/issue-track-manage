package cn.master.track.entity;

import cn.master.track.enums.ExcelColumn;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

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
@TableName("test_case")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestCase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 项目id
     */
    @TableField("case_project_id")
    @ExcelColumn(value = "项目名称", col = 1)
    private String caseProjectId;

    /**
     * 用例集(模块)id
     */
    @TableField("case_module_id")
    @ExcelColumn(value = "用例集名称", col = 2)
    private String caseModuleId;

    /**
     * 测试用例标题
     */
    @TableField("case_title")
    @ExcelColumn(value = "测试用例标题", col = 3)
    private String caseTitle;

    /**
     * 摘要
     */
    @TableField("case_summary")
    @ExcelColumn(value = "摘要", col = 4)
    private String caseSummary;

    /**
     * 前提条件
     */
    @TableField("case_precondition")
    @ExcelColumn(value = "前提条件", col = 5)
    private String casePrecondition;

    /**
     * 测试步骤
     */
    @TableField("case_steps")
    @ExcelColumn(value = "测试步骤", col = 6)
    private String caseSteps;

    /**
     * 预期结果
     */
    @TableField("case_expected_results")
    @ExcelColumn(value = "预期结果", col = 7)
    private String caseExpectedResults;

    /**
     * 是否执行
     */
    @TableField("case_run")
    private Integer caseRun;
    /**
     * 是否执行
     */
    @TableField("issue_date")
    private String issueDate;

    /**
     * 任务时间,同问题单表中字段一致
     */
    @TableField("create_date")
    private Date createDate;

    /**
     * 修改时间
     */
    @TableField("update_date")
    private Date updateDate;


}

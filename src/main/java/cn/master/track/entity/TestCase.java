package cn.master.track.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
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
 * @since 2021-08-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_case")
@AllArgsConstructor
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
    private String caseProjectId;

    /**
     * 用例集名称id
     */
    @TableField("case_suite_id")
    private String caseSuiteId;

    /**
     * 测试用例标题
     */
    @TableField("case_title")
    private String caseTitle;

    /**
     * 摘要
     */
    @TableField("case_summary")
    private String caseSummary;

    /**
     * 前提条件
     */
    @TableField("case_precondition")
    private String casePrecondition;

    /**
     * 测试步骤
     */
    @TableField("case_steps")
    private String caseSteps;

    /**
     * 预期结果
     */
    @TableField("case_expected_results")
    private String caseExpectedResults;

    /**
     * 预期结果
     */
    @TableField("case_run")
    private Integer caseRun;

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

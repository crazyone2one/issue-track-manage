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
 * @since 2021-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("issue_summary")
@Builder
@AllArgsConstructor
public class IssueSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 项目名称
     */
    @TableField("project_name")
    private String projectName;

    /**
     * 任务描述
     */
    @TableField("job_desc")
    private String jobDesc;

    /**
     * 编写用例数量
     */
    @TableField("create_case_count")
    private Integer createCaseCount;

    /**
     * 执行测试用例数量
     */
    @TableField("execute_case_count")
    private Integer executeCaseCount;

    /**
     * bug文档
     */
    @TableField("bug_doc")
    private String bugDoc;

    /**
     * 测试报告
     */
    @TableField("report_doc")
    private String reportDoc;

    /**
     * 需求文档
     */
    @TableField("has_doc")
    private String hasDoc;

    /**
     * 完成状态
     */
    @TableField("job_status")
    private String jobStatus;

    /**
     * 交付状态
     */
    @TableField("delivery_status")
    private String deliveryStatus;

    /**
     * 负责人
     */
    @TableField("owner")
    private String owner;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    @TableField("issue_date")
    private String issueDate;
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

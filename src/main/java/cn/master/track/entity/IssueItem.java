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
 * 问题单表
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("issue_item")
@Builder
@AllArgsConstructor
public class IssueItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 项目名称
     */
    @TableField("project_code")
    private String projectCode;

    /**
     * 所属模块
     */
    @TableField("module_code")
    private String moduleCode;

    /**
     * 功能点
     */
    @TableField("function_desc")
    private String functionDesc;

    /**
     * 标题
     */
    @TableField("title_desc")
    private String titleDesc;

    /**
     * 严重程度
     */
    @TableField("severity")
    private String severity;

    /**
     * 测试人员
     */
    @TableField("tester")
    private String tester;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 月份
     */
    @TableField("issue_date")
    private String issueDate;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

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

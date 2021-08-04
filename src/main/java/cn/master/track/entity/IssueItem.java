package cn.master.track.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("issue_item")
public class IssueItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("id")
    private String id;

    /**
     * 项目名称
     */
    @TableField("project_name")
    private String projectName;

    /**
     * 所属模块
     */
    @TableField("module")
    private String module;

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
    @TableField("owner")
    private String owner;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 月份
     */
    @TableField("data")
    private String data;

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

package cn.master.track.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2021-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("summary_item_ref")
@Builder
public class SummaryItemRef implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * summary表主键id
     */
    @TableField("summary_id")
    private String summaryId;

    /**
     * item表主键id
     */
    @TableField("item_id")
    private String itemId;
    /**
     * 问题单时间
     */
    @TableField("issue_date")
    private String issueDate;
    /**
     * 新建轻微级别问题单数量
     */
    @TableField("create_bug_slight")
    private Integer createBugSlight;
    /**
     * 新建一般级别问题单数量
     */
    @TableField("create_bug_ordinary")
    private Integer createBugOrdinary;
    /**
     * 新建致命级别问题单数量
     */
    @TableField("create_bug_deadly")
    private Integer createBugDeadly;
    /**
     * 新建严重级别问题单数量
     */
    @TableField("create_bug_severity")
    private Integer createBugSeverity;
    /**
     * 回测轻微级别问题单数量
     */
    @TableField("review_bug_slight")
    private Integer reviewBugSlight;
    /**
     * 回测一般级别问题单数量
     */
    @TableField("review_bug_ordinary")
    private Integer reviewBugOrdinary;
    /**
     * 回测严重级别问题单数量
     */
    @TableField("review_bug_severity")
    private Integer reviewBugSeverity;
    /**
     * 回测致命级别问题单数量
     */
    @TableField("review_bug_deadly")
    private Integer reviewBugDeadly;
}

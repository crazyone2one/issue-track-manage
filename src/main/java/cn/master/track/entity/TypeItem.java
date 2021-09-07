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
 * @since 2021-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("type_item")
@Builder
@AllArgsConstructor
public class TypeItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 字典编码
     */
    @TableField("type_code")
    private String typeCode;

    /**
     * 字典名称
     */
    @TableField("type_name")
    private String typeName;

    /**
     * 字典组ID
     */
    @TableField("type_group")
    private String typeGroup;

    /**
     * group name
     */
    @TableField("type_group_name")
    private String typeGroupName;
    /**
     * 删除标记
     */
    @TableField("delete_flag")
    private String deleteFlag;
}

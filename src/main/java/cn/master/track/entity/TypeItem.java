package cn.master.track.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("type_item")
public class TypeItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
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
    @TableField("type_group_id")
    private String typeGroupId;


}

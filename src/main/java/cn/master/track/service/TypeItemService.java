package cn.master.track.service;

import cn.master.track.entity.TypeItem;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-04
 */
public interface TypeItemService extends IService<TypeItem> {

    /**
     * 添加数据字典
     *
     * @param item TypeItem
     * @return int
     */
    int addType(TypeItem item);

    int updateType(TypeItem item);

    int deleteType(TypeItem item);
}

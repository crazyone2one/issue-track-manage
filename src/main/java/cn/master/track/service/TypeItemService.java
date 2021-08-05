package cn.master.track.service;

import cn.master.track.entity.TypeItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-04
 */
public interface TypeItemService extends IService<TypeItem> {

    void addType(Map<String, String> param);
}

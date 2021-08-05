package cn.master.track.service.impl;

import cn.master.track.entity.TypeItem;
import cn.master.track.mapper.TypeItemMapper;
import cn.master.track.service.TypeItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-04
 */
@Service
public class TypeItemServiceImpl extends ServiceImpl<TypeItemMapper, TypeItem> implements TypeItemService {

    @Override
    public void addType(Map<String, String> param) {
        baseMapper.insert(TypeItem.builder().typeCode(param.get("type_code"))
                .typeName(param.get("type_name")).typeGroup(param.get("type_group")).build());
    }
}

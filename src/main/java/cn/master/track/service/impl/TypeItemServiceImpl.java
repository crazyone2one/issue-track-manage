package cn.master.track.service.impl;

import cn.master.track.config.Constants;
import cn.master.track.entity.TypeItem;
import cn.master.track.mapper.TypeItemMapper;
import cn.master.track.service.CommonService;
import cn.master.track.service.TypeItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-04
 */
@Service
public class TypeItemServiceImpl extends ServiceImpl<TypeItemMapper, TypeItem> implements TypeItemService {
    private final CommonService commonService;

    @Autowired
    public TypeItemServiceImpl(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public int addType(TypeItem item) {
        final int insert = baseMapper.insert(TypeItem.builder().typeCode(item.getTypeCode())
                .typeName(item.getTypeName())
                .typeGroup(item.getTypeGroup())
                .typeGroupName(Constants.typeMap.get(item.getTypeGroup()))
                .build());
        commonService.refreshTypeGroup();
        commonService.refreshTypeMap();
        return insert;
    }

    @Override
    public int updateType(TypeItem item) {
        final TypeItem typeItem = baseMapper.selectById(item.getId());
        if (Objects.nonNull(typeItem)) {
            typeItem.setTypeCode(item.getTypeCode());
            typeItem.setTypeName(item.getTypeName());
//            typeItem.setTypeGroup(item.getTypeGroup());
//            typeItem.setTypeGroupName(item.getTypeGroupName());
        }
        final int i = baseMapper.updateById(typeItem);
        commonService.refreshTypeGroup();
        commonService.refreshTypeMap();
        return i;
    }

    @Override
    public int deleteType(TypeItem item) {
        final TypeItem typeItem = baseMapper.selectById(item.getId());
        if (Objects.nonNull(typeItem)) {
            typeItem.setDeleteFlag("1");
        }
        final int i = baseMapper.updateById(typeItem);
        commonService.refreshTypeGroup();
        commonService.refreshTypeMap();
        return i;
    }

}

package cn.master.track.service.impl;

import cn.master.track.config.Constants;
import cn.master.track.entity.TypeItem;
import cn.master.track.mapper.TypeItemMapper;
import cn.master.track.service.CommonService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Created by 11's papa on 2021/08/04
 * @version 1.0.0
 */
@Service
public class CommonServiceImpl implements CommonService {


    private final TypeItemMapper typeItemMapper;

    @Autowired
    public CommonServiceImpl(TypeItemMapper typeItemMapper) {
        this.typeItemMapper = typeItemMapper;
    }

    @Override
    @PostConstruct
    public void initTypeGroup() {
        final List<TypeItem> typeItems = typeItemMapper.selectList(new QueryWrapper<TypeItem>().lambda());
        List<TypeItem> temp1 = new LinkedList<>();
        List<TypeItem> temp2 = new LinkedList<>();
        List<TypeItem> temp3 = new LinkedList<>();
        List<TypeItem> temp4 = new LinkedList<>();
        for (TypeItem typeItem : typeItems) {
            if (Constants.SEVERITY_LEVEL.equals(typeItem.getTypeGroup())) {
                temp1.add(typeItem);
            }
            if (Constants.OWNER_LIST.equals(typeItem.getTypeGroup())) {
                temp2.add(typeItem);
            }
            if (Constants.ISSUE_STATUS.equals(typeItem.getTypeGroup())) {
                temp3.add(typeItem);
            }
            if (Constants.JOB_STATUS.equals(typeItem.getTypeGroup())) {
                temp4.add(typeItem);
            }
        }
        Constants.allTypes.put("severity_level", temp1);
        Constants.allTypes.put("owner_list", temp2);
        Constants.allTypes.put("issue_status", temp3);
        Constants.allTypes.put("job_status", temp4);
    }
}

package cn.master.track.service.impl;

import cn.master.track.config.Constants;
import cn.master.track.entity.IssueItem;
import cn.master.track.entity.IssueSummary;
import cn.master.track.mapper.IssueSummaryMapper;
import cn.master.track.service.IssueSummaryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-04
 */
@Slf4j
@Service
public class IssueSummaryServiceImpl extends ServiceImpl<IssueSummaryMapper, IssueSummary> implements IssueSummaryService {

    @Override
    public String addIssueSummary(IssueItem item) {
        final IssueSummary summary = IssueSummary.builder()
//                .id(UuidUtils.generate())
                .projectName(item.getProjectId())
                .createCaseCount(0)
                .executeCaseCount(0)
                .bugDoc("0")
                .reportDoc("0")
                .hasDoc("0")
                .deliveryStatus("0")
                .issueDate(item.getIssueDate())
                .createDate(new Date())
                .build();
        baseMapper.insert(summary);
        return summary.getId();
    }

    @Override
    public IssueSummary findSummaryById(String id) {
        return baseMapper.selectOne(new QueryWrapper<IssueSummary>().lambda()
                .eq(IssueSummary::getId, id));
    }

    @Override
    public Page<IssueSummary> searchSummaryPage(Page<IssueSummary> page, QueryWrapper<IssueSummary> wrapper) {
        log.debug("分页查询汇总数据");
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public List<IssueSummary> listSummary(QueryWrapper<IssueSummary> wrapper) {
        log.debug("查询汇总数据");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public void modifySummary(IssueSummary summary) {
        summary.setProjectName(findSummaryById(summary.getId()).getProjectName());
        summary.setUpdateDate(new Date());
        baseMapper.update(summary, new QueryWrapper<IssueSummary>().lambda().eq(IssueSummary::getId, summary.getId()));
    }

    @Override
    public Map<String, String> totalCount(Map<String, String> m1, Map<String, String> m2, Map<String, String> m3, Map<String, String> m4) {
        Map<String, String> result = new LinkedHashMap<>();
        for (String tempId : Constants.PROJECT_ID_LIST) {
            int level1 = 0;
            int level2 = 0;
            int level3 = 0;
            int level4 = 0;
            if (m1.containsKey(tempId)) {
                level1 += Integer.parseInt(m1.get(tempId));
            }
            if (m2.containsKey(tempId)) {
                level2 += Integer.parseInt(m2.get(tempId));
            }
            if (m3.containsKey(tempId)) {
                level3 += Integer.parseInt(m3.get(tempId));
            }
            if (m4.containsKey(tempId)) {
                level4 += Integer.parseInt(m4.get(tempId));
            }
            result.put(tempId, String.valueOf(level1 + level2 + level3 + level4));
        }
        return result;
    }
}

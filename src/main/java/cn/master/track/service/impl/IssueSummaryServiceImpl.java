package cn.master.track.service.impl;

import cn.master.track.config.Constants;
import cn.master.track.entity.IssueItem;
import cn.master.track.entity.IssueSummary;
import cn.master.track.mapper.IssueSummaryMapper;
import cn.master.track.service.IssueSummaryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-04
 */
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
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public void modifySummary(Map<String, Object> params) {
        final String tempId = params.get("id").toString();
        final IssueSummary.IssueSummaryBuilder builder = IssueSummary.builder();
        builder.id(tempId)
                .projectName(findSummaryById(tempId).getProjectName())
                .jobDesc(params.get("jobDesc").toString())
                .createCaseCount(Integer.parseInt(params.get("createCase").toString()))
                .executeCaseCount(Integer.parseInt(params.get("executeCase").toString()))
                .bugDoc(params.get("bugDoc").toString())
                .reportDoc(params.get("reportDoc").toString())
                .hasDoc(params.get("hasDoc").toString())
                .jobStatus(params.get("jobStatus").toString())
                .deliveryStatus(params.get("deliveryStatus").toString())
                .owner(params.get("owner").toString())
                .remark(params.get("remark").toString())
                .updateDate(new Date());
        baseMapper.update(builder.build(), new QueryWrapper<IssueSummary>().lambda().eq(IssueSummary::getId, tempId));
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

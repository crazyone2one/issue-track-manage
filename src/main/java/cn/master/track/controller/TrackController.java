package cn.master.track.controller;

import cn.master.track.mapper.CommonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Created by 11's papa on 2021/08/11
 * @version 1.0.0
 */
@Slf4j
@Controller
public class TrackController {
    private final CommonMapper commonMapper;

    @Autowired
    public TrackController(CommonMapper commonMapper) {
        this.commonMapper = commonMapper;
    }

    @GetMapping("/")
    public String index(Model model) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        String xAxisSql = "SELECT GROUP_CONCAT(date ORDER BY date) data from (SELECT DATE_FORMAT( DATE_SUB(NOW(),INTERVAL ac - 1 MONTH),'%Y-%m' ) AS date \n" +
                "FROM ( SELECT @ai /*'*/ := /*'*/@ai + 1 AS ac FROM ( SELECT 1  UNION SELECT  2   UNION SELECT 3 ) ac1, (SELECT 1  UNION SELECT 2  UNION SELECT 3  UNION SELECT 4 ) ac2,(SELECT @ai /*'*/ := /*'*/ 0 )xc0) a ORDER BY date ) t1";
        final List<Map<String, Object>> xAxData = commonMapper.findMapBySql(xAxisSql);
        List<Map<String, Object>> xAxi = getSeries(xAxData);
        model.addAttribute("xAxisList", mapper.writeValueAsString(xAxi));
        String countQuery = "SELECT GROUP_CONCAT(count ORDER BY date) data  from (SELECT t1.date,IFNULL( issueCount.c, 0 ) count FROM ( SELECT DATE_FORMAT( DATE_SUB( NOW(), INTERVAL ac - 1 MONTH ), '%Y-%m' ) AS date FROM (SELECT @ai /*'*/ := /*'*/ @ai + 1 AS ac FROM ( SELECT 1 UNION SELECT 2 UNION SELECT 3 ) ac1, ( SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 ) ac2,( SELECT @ai /*'*/ := /*'*/ 0 ) xc0 ) a ORDER BY date )\n" +
                "t1 LEFT JOIN ( SELECT issue_date, COUNT( issue_id ) c FROM issue_item {whereSQL} GROUP BY issue_date ) issueCount ON issueCount.issue_date = t1.date ORDER BY t1.date) t2";
        // 新增
        final List<Map<String, Object>> newCount = commonMapper.findMapBySql(countQuery.replace("{whereSQL}","where status='1'"));
        model.addAttribute("newSeries", mapper.writeValueAsString(getSeries(newCount)));
        // 回测，暂止统计回测通过的数据
        final List<Map<String, Object>> reviewCount = commonMapper.findMapBySql(countQuery.replace("{whereSQL}", "where status_update='4'"));
        model.addAttribute("reviewSeries", mapper.writeValueAsString(getSeries(reviewCount)));
        return "index";
    }

    private List<Map<String, Object>> getSeries(List<Map<String, Object>> xAxData) {
        List<Map<String, Object>> xAxi = new ArrayList<>(xAxData);
        for (Map<String, Object> objectMap : xAxi) {
            String dateString = objectMap.get("data").toString();
            objectMap.put("data", dateString.split(","));
        }
        return xAxi;
    }
}

package cn.master.track.controller;


import cn.master.track.config.Constants;
import cn.master.track.service.IssueItemService;
import cn.master.track.service.IssueSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-04
 */
@Controller
@RequestMapping("/summary")
public class IssueSummaryController {

    private final IssueItemService itemService;
    private final IssueSummaryService summaryService;

    @Autowired
    public IssueSummaryController(IssueItemService itemService, IssueSummaryService summaryService) {
        this.itemService = itemService;
        this.summaryService = summaryService;
    }

    /**
     * 列表数据
     *
     * @param params 页面上的搜素条件
     * @param model
     * @param pn
     * @param pc
     * @return java.lang.String
     */
    @RequestMapping(value = "/list")
    public String listSummary(@RequestParam Map<String, Object> params,
                              Model model,
                              @RequestParam(value = "pn", defaultValue = "1") Integer pn,
                              @RequestParam(value = "pc", defaultValue = "10") Integer pc) {
        model.addAttribute("jobStatusList", Constants.allTypes.get("job_status"));
        model.addAttribute("summaryList", itemService.searchSummary(params, pn, pc));
        model.addAttribute("issueMap", itemService.issuesMap());
        model.addAttribute("addIssueLevel", itemService.searchIssueMaps("1", "2021-08"));
        model.addAttribute("modifyIssueLevel", itemService.searchIssueMaps("4", "2021-08"));
        return "summary/index";
    }

    @GetMapping("/goModify")
    public String goModify(String id, Model model) {
        model.addAttribute("summaryVo", summaryService.findSummaryById(id));
        model.addAttribute("issueMap", itemService.issuesMap());
        model.addAttribute("addIssueLevel", itemService.searchIssueMaps("1", "2021-08"));
        model.addAttribute("modifyIssueLevel", itemService.searchIssueMaps("4", "2021-08"));
        return "summary/modify";
    }

    @RequestMapping("/modifySummary")
    public String modifySummary(@RequestParam Map<String, Object> params) {
        summaryService.modifySummary(params);
        return "redirect:/summary/list";
    }
}


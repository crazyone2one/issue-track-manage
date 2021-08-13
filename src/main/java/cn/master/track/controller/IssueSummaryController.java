package cn.master.track.controller;


import cn.master.track.config.Constants;
import cn.master.track.entity.IssueSummary;
import cn.master.track.service.IssueItemService;
import cn.master.track.service.IssueProjectService;
import cn.master.track.service.IssueSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private final IssueProjectService projectService;

    @Autowired
    public IssueSummaryController(IssueItemService itemService, IssueSummaryService summaryService, IssueProjectService projectService) {
        this.itemService = itemService;
        this.summaryService = summaryService;
        this.projectService = projectService;
    }

    /**
     * 列表数据
     *
     * @param summary 页面上的搜素条件
     * @param model
     * @param pn
     * @param pc
     * @return java.lang.String
     */
    @RequestMapping(value = "/list")
    public String listSummary(@ModelAttribute @Validated IssueSummary summary,
                              Model model,
                              @RequestParam(value = "pn", defaultValue = "1") Integer pn,
                              @RequestParam(value = "pc", defaultValue = "15") Integer pc) {
        model.addAttribute("jobStatusList", Constants.allTypes.get("job_status"));
        model.addAttribute("summaryPage", itemService.searchSummary(summary, pn, pc));
        model.addAttribute("summaryList", itemService.summaryList(summary));
        model.addAttribute("proMap", projectService.projectsMap());
        Map<String, String> level1Map = itemService.searchIssueMaps("1", "1", "2021-08", false);
        final Map<String, String> level2Map = itemService.searchIssueMaps("2", "1", "2021-08", false);
        final Map<String, String> level3Map = itemService.searchIssueMaps("3", "1", "2021-08", false);
        final Map<String, String> level4Map = itemService.searchIssueMaps("4", "1", "2021-08", false);
        final Map<String, String> totalCount = summaryService.totalCount(level1Map, level2Map, level3Map, level4Map);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("level1", level1Map);
        model.addAttribute("level2", level2Map);
        model.addAttribute("level3", level3Map);
        model.addAttribute("level4", level4Map);
        final Map<String, String> modifyLevel1 = itemService.searchIssueMaps("1", "4", "2021-08", true);
        final Map<String, String> modifyLevel2 = itemService.searchIssueMaps("2", "4", "2021-08", true);
        final Map<String, String> modifyLevel3 = itemService.searchIssueMaps("3", "4", "2021-08", true);
        final Map<String, String> modifyLevel4 = itemService.searchIssueMaps("4", "4", "2021-08", true);
        final Map<String, String> modifyTotal = summaryService.totalCount(modifyLevel1, modifyLevel2, modifyLevel3, modifyLevel4);
        model.addAttribute("modifyLevel1", modifyLevel1);
        model.addAttribute("modifyLevel2", modifyLevel2);
        model.addAttribute("modifyLevel3", modifyLevel3);
        model.addAttribute("modifyLevel4", modifyLevel4);
        model.addAttribute("modifyTotal", modifyTotal);
        return "summary/summaryList";
    }

    @GetMapping("/goModify")
    public String goModify(String id, Model model) {
        model.addAttribute("jobStatusList", Constants.allTypes.get("job_status"));
        model.addAttribute("ownerList", Constants.allTypes.get("owner_list"));
        model.addAttribute("summaryVo", summaryService.findSummaryById(id));
        model.addAttribute("proMap", projectService.projectsMap());
        return "summary/modify";
    }

    @RequestMapping("/modifySummary")
    public String modifySummary(@ModelAttribute @Validated IssueSummary summary) {
        summaryService.modifySummary(summary);
        return "redirect:/summary/list";
    }
}


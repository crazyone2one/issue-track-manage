package cn.master.track.controller;


import cn.master.track.config.Constants;
import cn.master.track.entity.IssueSummary;
import cn.master.track.service.IssueItemService;
import cn.master.track.service.IssueProjectService;
import cn.master.track.service.IssueSummaryService;
import cn.master.track.service.SummaryItemRefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private final SummaryItemRefService refService;

    @Autowired
    public IssueSummaryController(IssueItemService itemService, IssueSummaryService summaryService, IssueProjectService projectService, SummaryItemRefService refService) {
        this.itemService = itemService;
        this.summaryService = summaryService;
        this.projectService = projectService;
        this.refService = refService;
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
        model.addAttribute("ownerList", Constants.allTypes.get("owner_list"));
        model.addAttribute("refMap", refService.refsMap());
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


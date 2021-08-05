package cn.master.track.controller;


import cn.master.track.service.IssueItemService;
import cn.master.track.service.IssueSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    private final IssueSummaryService summaryService;
    private final IssueItemService itemService;

    @Autowired
    public IssueSummaryController(IssueSummaryService summaryService, IssueItemService itemService) {
        this.summaryService = summaryService;
        this.itemService = itemService;
    }

    /**
     * 页面列表数据
     *
     * @param model
     * @param pn
     * @param pc
     * @return java.lang.String
     */
    @GetMapping("/list")
    public String summaryListPage(Model model, @RequestParam(value = "pn", defaultValue = "1") Integer pn,
                                  @RequestParam(value = "pc", defaultValue = "10") Integer pc) {
        model.addAttribute("summaryList", summaryService.pageSummary(pn, pc));
        model.addAttribute("issueMap", itemService.issuesMap());
        model.addAttribute("addIssueLevel", itemService.searchIssueMaps("1", "2021-08"));
        model.addAttribute("modifyIssueLevel", itemService.searchIssueMaps("4", "2021-08"));
        return "summary/index";
    }
}


package cn.master.track.controller;


import cn.master.track.config.Constants;
import cn.master.track.service.IssueItemService;
import cn.master.track.service.IssueProjectService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Controller
@RequestMapping("/items")
public class IssueItemController {

    private final IssueItemService itemService;
    private final IssueProjectService projectService;

    @Autowired
    public IssueItemController(IssueItemService itemService, IssueProjectService projectService) {
        this.itemService = itemService;
        this.projectService = projectService;
    }

    @GetMapping("/list")
    public String itemList(@RequestParam Map<String, Object> params,Model model,
                           @RequestParam(value = "pn", defaultValue = "1") Integer pn,
                           @RequestParam(value = "pc", defaultValue = "15") Integer pc) {
        model.addAttribute("issueStatusList", Constants.allTypes.get("issue_status"));
        model.addAttribute("issueListPage", itemService.pageItems(params, pn, pc));
        model.addAttribute("issueList", itemService.issueItems(params));
        model.addAttribute("proMap", projectService.projectsMap());
        return "issue/issueList";
    }

    @GetMapping("/demo")
    public String demo() {
        return "";
    }


    @GetMapping("/goAdd")
    public String goAdd(Model model) {
        model.addAttribute("ownerList", Constants.allTypes.get("owner_list"));
        model.addAttribute("severityList", Constants.allTypes.get("severity_level"));
        model.addAttribute("statusList", Constants.allTypes.get("issue_status"));
        model.addAttribute("monthList", Constants.MONTH_LIST);
        return "issue/addIssue";
    }

    @RequestMapping(value = "/addIssue")
    public String addIssue(@RequestParam Map<String, Object> params,
                           Model model,
                           @RequestParam(value = "pn", defaultValue = "1") Integer start,
                           @RequestParam(value = "pc", defaultValue = "15") Integer length) {
        itemService.addIssueItem(params);
        model.addAttribute("issueList", itemService.pageItems(params, start, length));
        return "redirect:/items/list";
    }

    @GetMapping("/goModify")
    public String goModify(String id, Model model) {
        model.addAttribute("issueVo", itemService.getIssueById(id));
        model.addAttribute("proVo", projectService.getProjectById(itemService.getIssueById(id).getProjectId()));
        model.addAttribute("severityList", Constants.allTypes.get("severity_level"));
        model.addAttribute("statusList", Constants.allTypes.get("issue_status"));
        model.addAttribute("ownerList", Constants.allTypes.get("owner_list"));
        return "issue/modifyIssue";
    }

    @RequestMapping("/modifyIssue")
    public String modifyIssue(@RequestParam Map<String, Object> params) {
        itemService.modifyIssue(params);
        return "redirect:/items/list";
    }
}


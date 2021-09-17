package cn.master.track.controller;


import cn.master.track.config.Constants;
import cn.master.track.entity.IssueItem;
import cn.master.track.entity.IssueProject;
import cn.master.track.service.IssueItemService;
import cn.master.track.service.IssueModuleService;
import cn.master.track.service.IssueProjectService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;

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
    private final IssueModuleService moduleService;

    @Autowired
    public IssueItemController(IssueItemService itemService, IssueProjectService projectService, IssueModuleService moduleService) {
        this.itemService = itemService;
        this.projectService = projectService;
        this.moduleService = moduleService;
    }

    @GetMapping("/list")
    public String itemList(@ModelAttribute @Validated IssueItem issueItem,Model model,
                           @RequestParam(value = "pn", defaultValue = "1") Integer pn,
                           @RequestParam(value = "pc", defaultValue = "10") Integer pc) {
        final Page<IssueItem> issueItemPage = itemService.pageItems(issueItem, pn, pc);
        final List<IssueItem> records = issueItemPage.getRecords();
        List<IssueItem> recordList = new LinkedList<>();
        for (IssueItem record : records) {
            final IssueProject projectById = projectService.getProjectById(record.getProjectCode());
            record.setProjectCode(projectById.getProjectName());
            record.setModuleCode(moduleService.getModuleById(record.getModuleCode()).getModuleName());
            recordList.add(record);
        }
        model.addAttribute("issueStatusList", Constants.allTypes.get("issue_status"));
        model.addAttribute("severityList", Constants.allTypes.get("severity_level"));
        model.addAttribute("ownerList", Constants.allTypes.get("owner_list"));
        model.addAttribute("issueListPage", issueItemPage);
        model.addAttribute("records", recordList);
        model.addAttribute("proMap", projectService.projectsMap());
        model.addAttribute("statusList", Constants.allTypes.get("issue_status"));
        model.addAttribute("monthList", Constants.MONTH_LIST);
        return "issue/issueList";
    }

    @GetMapping("/demo")
    public String demo() {
        int x = 10 / 0;
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
    public String addIssue(@ModelAttribute @Validated IssueItem issueItem) {
        itemService.saveIssueItem(issueItem);
        return "redirect:/items/list";
    }

    @GetMapping("/goModify")
    public String goModify(String id, Model model) {
        model.addAttribute("issueVo", itemService.getIssueById(id));
        model.addAttribute("proVo", projectService.getProjectById(itemService.getIssueById(id).getProjectCode()));
        model.addAttribute("severityList", Constants.allTypes.get("severity_level"));
        model.addAttribute("statusList", Constants.allTypes.get("issue_status"));
        model.addAttribute("ownerList", Constants.allTypes.get("owner_list"));
        return "issue/modifyIssue";
    }

    @RequestMapping("/modifyIssue")
    public String modifyIssue(@ModelAttribute @Validated IssueItem issueItem) {
        itemService.modifyIssue(issueItem);
        return "redirect:/items/list";
    }
}


package cn.master.track.controller;


import cn.master.track.config.Constants;
import cn.master.track.service.CommonService;
import cn.master.track.service.IssueItemService;
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
    private final CommonService commonService;

    @Autowired
    public IssueItemController(IssueItemService itemService, CommonService commonService) {
        this.itemService = itemService;
        this.commonService = commonService;
    }

    @GetMapping("/list")
    public String itemList(Model model,
                           @RequestParam(value = "pn", defaultValue = "1") Integer pn,
                           @RequestParam(value = "pc", defaultValue = "10") Integer pc) {
        model.addAttribute("issueList", itemService.pageItems(pn, pc));
        return "item/index";
    }

    @GetMapping("/goAdd")
    public String goAdd(Model model) {
        commonService.initTypeGroup();
        model.addAttribute("ownerList", Constants.allTypes.get("owner_list"));
        model.addAttribute("severityList", Constants.allTypes.get("severity_level"));
        model.addAttribute("statusList", Constants.allTypes.get("issue_status"));
        model.addAttribute("monthList", Constants.monthList);
        return "item/addIssue";
    }

    @RequestMapping(value = "/addIssue")
    public String addIssue(@RequestParam Map<String, Object> params,
                           Model model,
                           @RequestParam(value = "pn", defaultValue = "1") Integer start,
                           @RequestParam(value = "pc", defaultValue = "10") Integer length) {
        itemService.addIssueItem(params);
        model.addAttribute("issueList", itemService.pageItems(start, length));
        return "item/index";
    }

}


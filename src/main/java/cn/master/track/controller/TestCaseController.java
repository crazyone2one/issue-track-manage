package cn.master.track.controller;


import cn.master.track.entity.TestCase;
import cn.master.track.service.IssueProjectService;
import cn.master.track.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-27
 */
@Controller
@RequestMapping("/case")
public class TestCaseController {

    private final TestCaseService caseService;
    private final IssueProjectService projectService;

    @Autowired
    public TestCaseController(TestCaseService caseService, IssueProjectService projectService) {
        this.caseService = caseService;
        this.projectService = projectService;
    }

    @GetMapping("/summary")
    public String caseSummary(Model model) {
        final List<Map<String, Object>> caseInfoMap = caseService.caseInfoMap();
        model.addAttribute("caseInfo", caseInfoMap);
        return "testCase/summary";
    }

    @GetMapping("/list")
    public String caseList(@ModelAttribute @Validated TestCase testCase, Model model,
                           @RequestParam(value = "pn", defaultValue = "1") Integer pn,
                           @RequestParam(value = "pc", defaultValue = "10") Integer pc) {
        model.addAttribute("casePageList", caseService.searchCase(testCase, pn, pc));
        model.addAttribute("proMap", projectService.projectsMap());
        return "testCase/case_list";
    }

    @GetMapping("/search")
    public String searchCase(Model model) {
        return "redirect:/case/list";
    }

    @RequestMapping("/addCase")
    public String addCase(@ModelAttribute @Validated TestCase testCase) {
        caseService.addCase(testCase);
        return "redirect:/case/list";
    }

    @RequestMapping("/editCase")
    public String editCase(@ModelAttribute @Validated TestCase testCase) {
        caseService.upgradeCaseInfo(testCase);
        return "redirect:/case/list";
    }

    @GetMapping("/redirection2CaseList")
    public String redirection2CaseList(String caseProjectName, String caseSuite, Model model) {
        model.addAttribute("casePageList", caseService.search4Redirection(caseProjectName, caseSuite));
        model.addAttribute("proMap", projectService.projectsMap());
        return "testCase/case_list";
    }
}


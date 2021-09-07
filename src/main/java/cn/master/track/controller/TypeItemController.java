package cn.master.track.controller;


import cn.master.track.config.Constants;
import cn.master.track.entity.TypeItem;
import cn.master.track.service.TypeItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 11's papa
 * @since 2021-08-04
 */
@Controller
@RequestMapping("/type")
public class TypeItemController {
    private final TypeItemService itemService;

    @Autowired
    public TypeItemController(TypeItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/list")
    public String itemList(Model model) {
        model.addAttribute("types", Constants.allTypes);
        model.addAttribute("typeMap", Constants.typeMap);
        return "settings/setting";
    }

    @RequestMapping("/addType")
    public String addCase(@ModelAttribute @Validated TypeItem testCase) {
        itemService.addType(testCase);
        return "redirect:/type/list";
    }

    @RequestMapping("/editItem")
    public String editItem(@ModelAttribute @Validated TypeItem testCase) {
        itemService.updateType(testCase);
        return "redirect:/type/list";
    }

    @RequestMapping("/deleteItem")
    public String deleteItem(@ModelAttribute @Validated TypeItem testCase) {
        itemService.deleteType(testCase);
        return "redirect:/type/list";
    }
}


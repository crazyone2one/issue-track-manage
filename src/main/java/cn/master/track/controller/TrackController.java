package cn.master.track.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Created by 11's papa on 2021/08/11
 * @version 1.0.0
 */
@Controller
public class TrackController {
    @GetMapping("/")
    public String demo() {
        return "index";
    }
}

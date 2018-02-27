package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 31364 on 2018/2/27.
 * description: 页面跳转的Controller
 */
@Controller
public class PageController {

    @RequestMapping(value = "/")
    public String showIndex() {
        return "index";
    }

    @RequestMapping(value = "/{page}")
    public String showPage(@PathVariable("page") String page) {
        return page;
    }
}

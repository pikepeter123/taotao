package com.taotao.search.controller;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by 31364 on 2018/3/3.
 * description:
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Value(value = "${ITEM_ROWS}")
    private Integer ITEM_ROWS;

    @RequestMapping(value = "/search")
    public String search(String q, @RequestParam(defaultValue = "1") Integer page, Model model) throws Exception {
        q = new String(q.getBytes("ISO-8859-1"), "UTF-8");
        SearchResult result = searchService.search(q, page, ITEM_ROWS);
        model.addAttribute("query", q);
        model.addAttribute("totalPages", result.getPageCount());
        model.addAttribute("itemList", result.getItemList());
        model.addAttribute("page", page);
        return "search";
    }
}

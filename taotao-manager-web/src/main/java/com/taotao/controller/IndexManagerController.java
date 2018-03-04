package com.taotao.controller;

import com.taotao.common.pojo.TaoTaoResult;
import com.taotao.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 31364 on 2018/3/3.
 * description:
 */
@Controller
@RequestMapping(value = "/index")
public class IndexManagerController {

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping(value = "/import")
    @ResponseBody
    public TaoTaoResult indexImport() throws Exception {
        return searchItemService.importAllItemToIndex();
    }
}

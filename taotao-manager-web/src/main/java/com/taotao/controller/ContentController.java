package com.taotao.controller;

import com.taotao.common.pojo.TaoTaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 31364 on 2018/3/1.
 * description: 内容的controller
 */
@Controller
@RequestMapping(value = "/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 添加内容的方法
     *
     * @param tbContent 待添加的内容
     * @return 操作结果
     */
    @RequestMapping(value = "/save")
    public TaoTaoResult addContent(TbContent tbContent) {
        return contentService.addContent(tbContent);
    }
}

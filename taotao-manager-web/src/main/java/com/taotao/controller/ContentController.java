package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaoTaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @ResponseBody
    public TaoTaoResult addContent(TbContent tbContent) {
        return contentService.addContent(tbContent);
    }

    @RequestMapping(value = "/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
        return contentService.getContentList(categoryId, page, rows);
    }

    @RequestMapping(value = "/edit")
    @ResponseBody
    public TaoTaoResult editContent(TbContent tbContent) {
        return contentService.editContent(tbContent);
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public TaoTaoResult deleteContent(TbContent tbContent) {
        return contentService.deleteContent(tbContent);
    }
}

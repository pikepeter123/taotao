package com.taotao.controller;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaoTaoResult;
import com.taotao.pojo.TbContentCategory;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by 31364 on 2018/2/28.
 * description:
 */
@Controller
@RequestMapping(value = "/content/category")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        return contentCategoryService.getContentCategoryList(parentId);
    }

    @RequestMapping(value = "/create")
    @ResponseBody
    public TaoTaoResult createContentCategory(TbContentCategory tbContentCategory) {
        return contentCategoryService.createContentCategory(tbContentCategory);
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public TaoTaoResult updateContentCategory(TbContentCategory tbContentCategory) {
        return contentCategoryService.updateContentCategory(tbContentCategory);
    }

    @RequestMapping(value = "/delete")
    public TaoTaoResult deleteContentCategory(TbContentCategory tbContentCategory) {
        return contentCategoryService.deleteContentCategory(tbContentCategory);
    }

}

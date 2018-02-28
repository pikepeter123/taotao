package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaoTaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 31364 on 2018/2/27.
 * description: 商品展示的前端Controller
 */
@Controller
@RequestMapping(value = "/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        EasyUIDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public TaoTaoResult saveItem(TbItem tbItem, @RequestParam(value = "desc") String description) {
        return itemService.addItem(tbItem, description);
    }

    /**
     * 删除商品（未测试）
     *
     * @param tbItem 待删除的商品
     * @return 操作的结果
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public TaoTaoResult deleteItem(TbItem tbItem) {
        return itemService.deleteItem(tbItem);
    }

    /**
     * 更新商品
     *
     * @param tbItem 待更新的商品
     * @return 更新结果
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public TaoTaoResult updateItem(TbItem tbItem) {
        return itemService.updateItem(tbItem);
    }

    /**
     * 下架商品
     *
     * @param ids 待下架商品id的数组
     * @return 操作结果
     */
    @RequestMapping(value = "/instock")
    @ResponseBody
    public TaoTaoResult instockItem(Long[] ids) {
        return itemService.instockItems(ids);
    }

    /**
     * 上架商品
     *
     * @param ids 待上架商品id的数组
     * @return 操作结果
     */
    @RequestMapping(value = "/reshelf")
    @ResponseBody
    public TaoTaoResult reshelfItem(Long[] ids) {
        return itemService.reshelfItems(ids);
    }
}

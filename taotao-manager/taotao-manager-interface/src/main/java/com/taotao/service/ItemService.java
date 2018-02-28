package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaoTaoResult;
import com.taotao.pojo.TbItem;

/**
 * Created by 31364 on 2018/2/27.
 * description:
 */
public interface ItemService {

    EasyUIDataGridResult getItemList(Integer page, Integer rows);

    TaoTaoResult addItem(TbItem item, String description);
}

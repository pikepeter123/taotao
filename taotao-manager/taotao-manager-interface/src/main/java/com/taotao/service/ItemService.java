package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;

/**
 * Created by 31364 on 2018/2/27.
 * description:
 */
public interface ItemService {

    EasyUIDataGridResult getItemList(Integer page, Integer rows);
}

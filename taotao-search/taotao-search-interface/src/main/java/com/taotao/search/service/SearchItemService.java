package com.taotao.search.service;

import com.taotao.common.pojo.TaoTaoResult;

/**
 * Created by 31364 on 2018/3/3.
 * description:
 */
public interface SearchItemService {

    TaoTaoResult importAllItemToIndex() throws Exception;
}

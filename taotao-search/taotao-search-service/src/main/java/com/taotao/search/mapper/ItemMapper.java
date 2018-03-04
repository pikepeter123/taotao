package com.taotao.search.mapper;

import com.taotao.common.pojo.SearchItem;

import java.util.List;

/**
 * Created by 31364 on 2018/3/3.
 * description:
 */
public interface ItemMapper {

    List<SearchItem> getItemList();

    SearchItem getItemById(Long id);
}

package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * Created by 31364 on 2018/2/27.
 * description:
 */
public interface ItemCatService {

    List<EasyUITreeNode> getItemCatList(Long parentId);
}

package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaoTaoResult;
import com.taotao.pojo.TbContentCategory;

import java.util.List;

/**
 * Created by 31364 on 2018/2/28.
 * description:
 */
public interface ContentCategoryService {

    List<EasyUITreeNode> getContentCategoryList(Long parentId);

    TaoTaoResult createContentCategory(TbContentCategory tbContentCategory);

    TaoTaoResult updateContentCategory(TbContentCategory tbContentCategory);

    TaoTaoResult deleteContentCategory(TbContentCategory tbContentCategory);
}

package com.taotao.service;

import com.taotao.common.pojo.TaoTaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * Created by 31364 on 2018/3/1.
 * description:
 */
public interface ContentService {

    TaoTaoResult addContent(TbContent tbContent);

    List<TbContent> getContentList(Long cid);
}

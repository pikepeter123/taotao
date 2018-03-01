package com.taotao.service.impl;

import com.taotao.common.pojo.TaoTaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 31364 on 2018/3/1.
 * description: 内容的业务层实现类
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper tbContentMapper;

    /**
     * 添加内容的方法（未测试）
     *
     * @param tbContent 待添加的内容
     * @return 操作结果
     */
    @Override
    public TaoTaoResult addContent(TbContent tbContent) {
//        补全属性
        Date date = new Date();
        tbContent.setCreated(date);
        tbContent.setUpdated(date);
        tbContentMapper.insert(tbContent);
        return TaoTaoResult.ok(tbContent);
    }

    @Override
    public List<TbContent> getContentList(Long cid) {
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> list = tbContentMapper.selectByExample(example);
        return list;
    }
}

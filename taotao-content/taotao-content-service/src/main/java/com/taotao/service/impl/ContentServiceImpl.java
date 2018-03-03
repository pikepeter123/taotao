package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaoTaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.content.jedis.JedisClient;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private JedisClient jedisClient;

    @Value(value = "${CONTENT_KEY}")
    private String CONTENT_KEY;

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
        System.out.println(tbContent);
        tbContentMapper.insert(tbContent);
//        清空redis中categoryId对应的缓存
        jedisClient.hdel(CONTENT_KEY, tbContent.getCategoryId() + "");
        return TaoTaoResult.ok(tbContent);
    }

    /**
     * 根据类别Id查询内容
     *
     * @param cid 类别id
     * @return 内容的集合
     */
    @Override
    public List<TbContent> getContentList(Long cid) {
//        先查询缓存
        try {
            String jsonString = jedisClient.hget(CONTENT_KEY, cid + "");
//            判断是否命中缓存
            if (StringUtils.isNoneBlank(jsonString)) {
                List<TbContent> jsonList = JsonUtils.jsonToList(jsonString, TbContent.class);
                return jsonList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> list = tbContentMapper.selectByExample(example);
//        将查询结果放进缓存中
        try {
            jedisClient.hset(CONTENT_KEY, cid + "", JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
//        构造分页对象
        PageHelper.startPage(page, rows);
        TbContentExample example = new TbContentExample();
//        构造查询条件
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
//        执行查询
        List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
//        构造分页结果信息
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
//        构造返回结果对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
//        设置属性
        result.setRows(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public TaoTaoResult editContent(TbContent tbContent) {
//        需要先查询一下创建时间
        TbContent persistentContent = tbContentMapper.selectByPrimaryKey(tbContent.getId());
//        设置创建时间
        tbContent.setCreated(persistentContent.getCreated());
//        设置更新时间
        tbContent.setUpdated(new Date());
//        执行跟新操作
        tbContentMapper.updateByPrimaryKeyWithBLOBs(tbContent);
        return TaoTaoResult.ok();
    }

    @Override
    public TaoTaoResult deleteContent(TbContent tbContent) {
//        执行删除
        tbContentMapper.deleteByPrimaryKey(tbContent.getId());
//        返回操作结果
        return TaoTaoResult.ok();
    }
}

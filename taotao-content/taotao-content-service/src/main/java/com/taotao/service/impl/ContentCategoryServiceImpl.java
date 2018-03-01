package com.taotao.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaoTaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 31364 on 2018/2/28.
 * description: 内容分类业务层实现类
 */
@Service
@SuppressWarnings(value = "all")
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    /**
     * 查询所有内容分类的方法
     *
     * @param parentId 父节点的Id
     * @return 操作结果
     */
    @Override
    public List<EasyUITreeNode> getContentCategoryList(Long parentId) {
//        取查询参数id，parentId
        TbContentCategoryExample example = new TbContentCategoryExample();
//        设置查询条件
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
//        执行查询
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (TbContentCategory tbContentCategory : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
//            添加到列表
            resultList.add(node);
        }
        return resultList;
    }

    /**
     * 创建新的内容分类的方法
     *
     * @param tbContentCategory 待创建的内容分类
     * @return 操作结果
     */
    @Override
    public TaoTaoResult createContentCategory(TbContentCategory tbContentCategory) {
//        补全类目属性
        Date date = new Date();
//        设置创建时间
        tbContentCategory.setCreated(date);
//        设置更改时间
        tbContentCategory.setUpdated(date);
//        设置状态
        tbContentCategory.setStatus(1);
//        设置是否是父节点
        tbContentCategory.setIsParent(false);
//        设置sort_order
        tbContentCategory.setSortOrder(1);
//        对父节点处理
        TbContentCategory persistentParentContentCategory = contentCategoryMapper.selectByPrimaryKey(tbContentCategory.getParentId());
        if (!persistentParentContentCategory.getIsParent()) {
//            判断如果父节点id的是否为父节点属性为false，修改成true
            persistentParentContentCategory.setIsParent(true);
//            修改更新时间
            persistentParentContentCategory.setUpdated(date);
//            更新数据库中父节点的记录
            contentCategoryMapper.updateByPrimaryKey(persistentParentContentCategory);
        }
//        向数据库中插入数据
        contentCategoryMapper.insert(tbContentCategory);
        return TaoTaoResult.ok(tbContentCategory);
    }

    /**
     * 更新内容分类的方法
     *
     * @param tbContentCategory 待更新的内容分类
     * @return 操作结果
     */
    @Override
    public TaoTaoResult updateContentCategory(TbContentCategory tbContentCategory) {
        TbContentCategory persistentContentCategory = contentCategoryMapper.selectByPrimaryKey(tbContentCategory.getId());
//        设置更新时间
        persistentContentCategory.setUpdated(new Date());
//        更新内容分类的名字
        persistentContentCategory.setName(tbContentCategory.getName());
        contentCategoryMapper.updateByPrimaryKey(persistentContentCategory);
        return TaoTaoResult.ok(persistentContentCategory);
    }

    /**
     * 删除内容分类节点
     *
     * @param tbContentCategory 待删除的节点
     * @return 操作结果
     */
    @Override
    public TaoTaoResult deleteContentCategory(TbContentCategory tbContentCategory) {
//        先查出来他的父节点
        TbContentCategory persistentContentCategory = contentCategoryMapper.selectByPrimaryKey(tbContentCategory.getId());
//        判断待删除节点的父节点下面有没有其他子节点，如果没有，将父节点的isParent属性设置为false，如果有，直接删除待删除的节点
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(persistentContentCategory.getParentId());
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        if (list.size() == 1) {
//            没有其他节点了
//            更新父节点的isParent属性值
            TbContentCategory parentContentCategory = contentCategoryMapper.selectByPrimaryKey(persistentContentCategory.getParentId());
            parentContentCategory.setIsParent(false);
            parentContentCategory.setUpdated(new Date());
//            更新数据库
            contentCategoryMapper.updateByPrimaryKey(parentContentCategory);
        }

//        删除待删除节点时，需要判断该节点是否是父节点，是父节点就递归删除他下面所有节点，如果不是，直接删除
        queryAndRecursionDelete(tbContentCategory);
//        删除自己
        contentCategoryMapper.deleteByPrimaryKey(tbContentCategory.getId());
//        构造操作结果对象
        TaoTaoResult result = new TaoTaoResult();
        result.setStatus(200);
        return result;
    }

    private void queryAndRecursionDelete(TbContentCategory tbContentCategory) {
        TbContentCategoryExample example = new TbContentCategoryExample();
//        构造条件查询对象
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(tbContentCategory.getId());
//        执行查询
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        for (TbContentCategory temp : list) {
            contentCategoryMapper.deleteByPrimaryKey(temp.getId());
            queryAndRecursionDelete(temp);
        }
    }
}

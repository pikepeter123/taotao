package com.taotao.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 31364 on 2018/2/27.
 * description: 商品类目业务层实现类
 */
@Service
@SuppressWarnings("all")
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    /**
     * 根据父节点的id查询子节点的列表
     *
     * @param parentId 父节点的ID
     * @return 子节点的集合
     */
    @Override
    public List<EasyUITreeNode> getItemCatList(Long parentId) {
//        根据父节点id查询子节点列表
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
//        条件是父节点是前台传过来的节点的id
        criteria.andParentIdEqualTo(parentId);
//        执行查询
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
//        转换成EasyUITreeNode列表
        ArrayList<EasyUITreeNode> resultList = new ArrayList<>();
        for (TbItemCat tbItemCat : list) {
//            构造前台节点对象
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
//            判断是否有子节点
            node.setState(tbItemCat.getIsParent() ? "closed" : "open");
            resultList.add(node);
        }
        return resultList;
    }
}

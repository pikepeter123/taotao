package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaoTaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 31364 on 2018/2/27.
 * description: 商品模块业务层实现类
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    /**
     * 分页查询所有数据的方法
     *
     * @param page 页码
     * @param rows 每页展示的数据
     * @return EasyUIDataGrid表格数据
     */
    @Override
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
//        设置分页信息
        PageHelper.startPage(page, rows);
//        执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
//        取分页信息
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
//        创建返回结果对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    /**
     * 添加商品的业务层方法
     *
     * @param item        需要添加的商品
     * @param description 商品的描述
     * @return 添加结果
     */
    @Override
    public TaoTaoResult addItem(TbItem item, String description) {
//        生成商品id
        long itemId = IDUtils.genItemId();
//        补全TbItem对象的属性
        item.setId(itemId);
//        商品状态1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
//        向商品表中插入数据
        itemMapper.insert(item);
//        创建一个TbItemDesc对象
        TbItemDesc itemDesc = new TbItemDesc();
//        补全TbItemDesc的属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(description);
        return null;
    }

    @Override
    public TaoTaoResult deleteItem(TbItem tbItem) {
        itemMapper.deleteByPrimaryKey(tbItem.getId());
        TaoTaoResult result = new TaoTaoResult();
        result.setStatus(200);
        return result;
    }

    /**
     * 更新商品的业务层方法
     *
     * @param tbItem 待更新的商品
     * @return 更新结果
     */
    @Override
    public TaoTaoResult updateItem(TbItem tbItem) {
//        首先根据商品的id查出数据库中的记录
        TbItem persistentTbItem = itemMapper.selectByPrimaryKey(tbItem.getId());
//        根据persistentTbItem补全tbItem的属性
//        补全商品状态
        if (tbItem.getStatus() == null) {
            tbItem.setStatus(persistentTbItem.getStatus());
        }
//        补全商品的创建日期
        tbItem.setCreated(persistentTbItem.getCreated());
//        设置更新日期
        tbItem.setUpdated(new Date());
//        执行更新
        itemMapper.updateByPrimaryKey(tbItem);
//        构造操作结果对象
        TaoTaoResult result = new TaoTaoResult();
//        设置更新成功的状态码
        result.setStatus(200);
        return result;
    }

    @Override
    public TaoTaoResult instockItems(Long[] ids) {
        for (Long id : ids) {
//            根据id查询持久态对象
            TbItem persistentItem = itemMapper.selectByPrimaryKey(id);
//            更新状态为2下架
            persistentItem.setStatus((byte) 2);
//            更新操作事件updateDate
            persistentItem.setUpdated(new Date());
            itemMapper.updateByPrimaryKey(persistentItem);
        }
        TaoTaoResult result = new TaoTaoResult();
        result.setStatus(200);
        return result;
    }

    @Override
    public TaoTaoResult reshelfItems(Long[] ids) {
        for (Long id : ids) {
//            根据id查询持久态对象
            TbItem persistentItem = itemMapper.selectByPrimaryKey(id);
//            更新状态为2上架
            persistentItem.setStatus((byte) 1);
//            更新操作事件updateDate
            persistentItem.setUpdated(new Date());
            itemMapper.updateByPrimaryKey(persistentItem);
        }
        TaoTaoResult result = new TaoTaoResult();
        result.setStatus(200);
        return result;
    }

}

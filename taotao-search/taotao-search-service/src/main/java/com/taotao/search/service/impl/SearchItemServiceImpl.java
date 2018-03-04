package com.taotao.search.service.impl;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.TaoTaoResult;
import com.taotao.search.dao.ItemSearchDao;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 31364 on 2018/3/3.
 * description:
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private SolrServer solrServer;

    @Autowired
    private ItemMapper itemMapper;

    /**
     * 导入商品数据到索引库
     *
     * @return 操作结果
     */
    @Override
    public TaoTaoResult importAllItemToIndex() throws Exception {
        List<SearchItem> list = itemMapper.getItemList();
        for (SearchItem item : list) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", item.getId());
            document.addField("item_title", item.getTitle());
            document.addField("item_sell_point", item.getSell_point());
            document.addField("item_price", item.getPrice());
            document.addField("item_image", item.getImage());
            document.addField("item_category_name", item.getCategory_name());
            document.addField("item_desc", item.getItem_desc());

//            添加文档
            solrServer.add(document);
        }
        solrServer.commit();
        return TaoTaoResult.ok();
    }
}

package com.taotao.search.dao;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 31364 on 2018/3/3.
 * description: 商品搜索的dao
 */
@Repository
public class ItemSearchDao {

    @Autowired
    private SolrServer solrServer;

    public SearchResult search(SolrQuery query) throws Exception {
//        根据query查询索引库
        QueryResponse response = solrServer.query(query);
//        取查询结果
        SolrDocumentList documents = response.getResults();
//        构造返回结果对象
        SearchResult result = new SearchResult();
//        查询结果总记录数
        result.setRecordCount(documents.getNumFound());
//        取结果集
        List<SearchItem> itemList = new ArrayList<>();
        for (SolrDocument document : documents) {
            SearchItem searchItem = new SearchItem();
            searchItem.setId(Long.parseLong(document.get("id").toString()));
            searchItem.setCategory_name((String) document.get("item_category_name"));
            searchItem.setImage((String) document.get("item_image"));
            searchItem.setPrice((Long) document.get("item_price"));
//            取高亮显示
            String itemTitle;
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list = highlighting.get(document.get("id")).get("item_title");
            if (list != null && list.size() > 0) {
                itemTitle = list.get(0);
            } else {
                itemTitle = (String) document.get("item_title");
            }
            searchItem.setTitle(itemTitle);
//            添加到商品列表
            itemList.add(searchItem);
        }
        result.setItemList(itemList);
//        返回结果
        return result;
    }
}

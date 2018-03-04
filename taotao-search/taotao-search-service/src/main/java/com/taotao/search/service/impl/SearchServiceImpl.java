package com.taotao.search.service.impl;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.dao.ItemSearchDao;
import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 31364 on 2018/3/4.
 * description:
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ItemSearchDao itemSearchDao;

    @Override
    public SearchResult search(String keyWord, Integer page, Integer rows) throws Exception {
//        构造查询条件
        SolrQuery query = new SolrQuery();
//        设置查询条件
        query.setQuery(keyWord);
//        设置分页条件
        if (page < 1)
            page = 1;
        query.setStart(page - 1);
        query.setRows(rows);
//        设置默认搜索域
        query.set("df", "item_title");
//        开启高亮
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color: red\">");
        query.setHighlightSimplePost("</em>");
        SearchResult result = itemSearchDao.search(query);
        Long recordCount = result.getRecordCount();
        Long pageCount = recordCount / rows;
        if (recordCount / rows > 0) {
            pageCount++;
        }
        result.setPageCount(pageCount);
        return result;
    }
}

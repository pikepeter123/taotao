package com.taotao.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by 31364 on 2018/3/3.
 * description:
 */
public class TestSolrj {

    /**
     * 添加文档
     *
     * @throws Exception 抛出异常
     */
    @Test
    public void test1() throws Exception {
//        创建一个solrserver对象
        HttpSolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr");
//        创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
//        想文档中添加域
        document.addField("id", "test01");
        document.addField("item_title", "测试商品");
        document.addField("item_price", 200);
//        将文档写入索引库
        solrServer.add(document);
//        提交
        solrServer.commit();
    }

    @Test
    public void test2() throws Exception {
        HttpSolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr");
//        创建一个solrQuery对象
        SolrQuery query = new SolrQuery();
        query.setQuery("三星");
        query.setStart(0);
        query.setRows(30);
        query.set("df", "item_title");
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");
//        执行查询
        QueryResponse response = solrServer.query(query);
//        取查询结果
        SolrDocumentList documents = response.getResults();
//        查询结果的总记录数
        System.out.println("查询结果的总记录数:" + documents.getNumFound());
        for (SolrDocument document : documents) {
            System.out.println(document.get("id"));
            String itemName = null;
//            取高亮显示
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list = highlighting.get(document.get("id")).get("item_title");
            if(list != null && list.size() > 0) {
                itemName = list.get(0);
            } else {
                itemName = (String) document.get("item_title");
            }
            System.out.println(itemName);
            System.out.println(document.get("item_sell_point"));
            System.out.println(document.get("item_price"));
            System.out.println(document.get("item_category_name"));
        }
    }
}

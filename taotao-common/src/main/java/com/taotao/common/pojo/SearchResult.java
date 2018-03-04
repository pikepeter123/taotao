package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 31364 on 2018/3/3.
 * description:
 */
public class SearchResult implements Serializable {

    private List<SearchItem> itemList;
    private Long recordCount;
    private Long pageCount;

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "itemList=" + itemList +
                ", recordCount=" + recordCount +
                ", pageCount=" + pageCount +
                '}';
    }
}

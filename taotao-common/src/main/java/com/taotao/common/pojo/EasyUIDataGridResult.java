package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 31364 on 2018/2/27.
 * description:
 */
public class EasyUIDataGridResult implements Serializable {

    private Long total;
    private List rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}

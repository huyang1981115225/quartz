package com.yusys.quartz.query;

import java.io.Serializable;

/**
 * Created by huyang on 2019/10/28.
 */
public class BaseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    private int page;

    private int limit;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

}

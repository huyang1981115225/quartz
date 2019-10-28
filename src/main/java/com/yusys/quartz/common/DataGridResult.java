package com.yusys.quartz.common;

import java.util.List;

/**
 * 数据返回响应体
 * Created by huyang on 2019/10/28.
 */
public class DataGridResult {

    private int code;

    private String message;

    private long count;

    private List<?> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}

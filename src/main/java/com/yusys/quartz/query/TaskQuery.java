package com.yusys.quartz.query;

/**
 * Created by huyang on 2019/10/28.
 */
public class TaskQuery extends BaseQuery {

    private static final long serialVersionUID = 1L;

    // 任务描述
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TaskQuery{" +
                "description='" + description + '\'' +
                '}';
    }
}

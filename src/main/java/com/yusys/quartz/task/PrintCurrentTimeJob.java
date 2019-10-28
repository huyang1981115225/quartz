package com.yusys.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by huyang on 2019/10/28.
 */
@Component
public class PrintCurrentTimeJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("现在时间是： "+new Date());
    }

    /**
     * 获取包名加类名
     * @param args
     */
    public static void main(String[] args) {
        PrintCurrentTimeJob job = new PrintCurrentTimeJob();
        System.out.println(job.getClass().getName());
    }
}

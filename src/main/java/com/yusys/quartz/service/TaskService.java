package com.yusys.quartz.service;

import com.yusys.quartz.common.DataGridResult;
import com.yusys.quartz.entity.TaskEntity;
import com.yusys.quartz.query.TaskQuery;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * Created by huyang on 2019/10/28.
 */
public interface TaskService {

    /**
     * 启动时初始化任务
     */
    void initSchedule() throws SchedulerException;

    /**
     * 根据id获取任务
     */
    TaskEntity get(Long id);

    /**
     * 查询任务列表
     */
    DataGridResult list(TaskQuery query);

    /**
     * 新增任务
     */
    int save(TaskEntity taskScheduleJob);

    /**
     * 修改任务
     */
    int update(TaskEntity taskScheduleJob);

    /**
     * 删除任务
     */
    int remove(Long id);

    /**
     * 批量删除任务
     */
    int removeBatch(Long[] ids);

    /**
     * 修改任务状态 停止0----》运行时1
     */
    void changeStatus(Long jobId, String jobStatus) throws SchedulerException;

    /**
     * 更新cron表达式
     */
    void updateCron(Long jobId,String cronExpression) throws SchedulerException;

    /**
     * 立即执行任务
     */
    void run(TaskEntity scheduleJob) throws SchedulerException;

    /**
     * 暂停任务
     */
    void pauseJob(TaskEntity scheduleJob) throws SchedulerException;

    /**
     * 恢复任务
     */
    void  resumeJob(TaskEntity scheduleJob) throws SchedulerException;

    /**
     * 获取所有计划中的任务列表
     */
    List<TaskEntity> getAllJob()throws SchedulerException;

    /**
     * 所有正在运行的job
     */
    List<TaskEntity> getRunningJob()throws SchedulerException;
}

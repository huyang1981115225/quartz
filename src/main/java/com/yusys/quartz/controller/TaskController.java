package com.yusys.quartz.controller;

import com.yusys.quartz.common.DataGridResult;
import com.yusys.quartz.entity.TaskEntity;
import com.yusys.quartz.enums.JobStatusEnum;
import com.yusys.quartz.query.TaskQuery;
import com.yusys.quartz.service.TaskService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huyang on 2019/10/28.
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * 查询任务列表
     */
    @RequestMapping("/list")
    public DataGridResult queryTasks(@RequestBody TaskQuery query) {
        System.err.println(query);
        DataGridResult result = taskService.list(query);
        return result;
    }

    /**
     * 新增任务
     */
    @RequestMapping("/add")
    public String addTask(@RequestBody TaskEntity task) {
        System.err.println(task);
        if (taskService.save(task) > 0) {
            return "Add task success";
        }
        return "Add task fail";
    }

    /**
     * 修改任务
     */
    @PostMapping("/edit")
    @ResponseBody
    public String edit(TaskEntity task) {
        TaskEntity taskServer = taskService.get(task.getId());
        if (JobStatusEnum.RUNNING.getCode().equals(taskServer.getJobStatus())) {
            throw new RuntimeException("修改之前请先停止任务！");
        }
        taskService.update(task);
        return "edit task success";
    }

    /**
     * 修改任务执行状态
     */
    @RequestMapping("/changeStatus/{id}")
    public String changeStatus(@PathVariable("id") Long id, Boolean jobStatus) {
        String status = jobStatus == true ? JobStatusEnum.RUNNING.getCode() : JobStatusEnum.STOP.getCode();
        try {
            taskService.changeStatus(id, status);
            return "change status success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "change status fail";
    }

    /**
     * 修改任务cron表达式
     */
    @RequestMapping("/updateJobCron/{id}")
    public String updateJobCron(@PathVariable("id") Long id,String cronExpression) {
        try {
            taskService.updateCron(id,cronExpression);
            return "updateJobCron success";
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "updateJobCron fail";
    }

    /**
     * 删除任务
     */
    @RequestMapping("/remove/{id}")
    public String remove(@PathVariable("id") Long id) {
        TaskEntity taskServer = taskService.get(id);
        if (JobStatusEnum.RUNNING.getCode().equals(taskServer.getJobStatus())) {
            throw new RuntimeException("删除前请先停止任务！");
        }
        if (taskService.remove(id) > 0) {
            return "remove task success";
        }
        return "remove task fail！";
    }

    /**
     * 批量删除任务
     */
    @RequestMapping("/removeBatch")
    public String removeBatch(@RequestParam("ids[]") Long[] ids) {
        for (Long id : ids) {
            TaskEntity taskServer = taskService.get(id);
            if (JobStatusEnum.RUNNING.getCode().equals(taskServer.getJobStatus())) {
                throw new RuntimeException("删除前请先停止任务！");
            }
        }
        taskService.removeBatch(ids);
        return "remove task success";
    }

    /**
     * 立即运行任务
     */
    @RequestMapping("/run/{id}")
    public String runTask(@PathVariable("id") Long id) {
        TaskEntity task = taskService.get(id);
        try {
            if (JobStatusEnum.STOP.getCode().equals(task.getJobStatus())) {
                throw new RuntimeException("想要立即执行请先开启任务");
            }
            taskService.run(task);
            return "run task success";
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "run task fail";
    }

    /**
     * 暂停任务
     */
    @RequestMapping("/pause/{id}")
    public String pauseTask(@PathVariable("id") Long id) {
        TaskEntity task = taskService.get(id);
        try {
            if (JobStatusEnum.STOP.getCode().equals(task.getJobStatus())) {
                throw new RuntimeException("想要暂停请先开启任务");
            }
            taskService.pauseJob(task);
            return "pause task success";
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "pause task fail";
    }

    /**
     * 恢复任务
     */
    @RequestMapping("/resume/{id}")
    public String resumeTask(@PathVariable("id") Long id) {
        TaskEntity task = taskService.get(id);
        try {
            if (JobStatusEnum.STOP.getCode().equals(task.getJobStatus())) {
                throw new RuntimeException("想要恢复请先开启任务");
            }
            taskService.resumeJob(task);
            return "resume task success";
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "resume task fail";
    }

    /**
     * 获取所有计划中的任务列表
     */
    @RequestMapping("/allJobList")
    public List<TaskEntity> getAllJob() {
        List<TaskEntity> result = new ArrayList<>();
        try{
            result = taskService.getAllJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取所有正在运行的job
     */
    @RequestMapping("/runningJobList")
    public List<TaskEntity> getRunningJob() {
        List<TaskEntity> result = new ArrayList<>();
        try{
          result = taskService.getRunningJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return result;
    }
}

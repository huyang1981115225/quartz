package com.yusys.quartz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yusys.quartz.common.DataGridResult;
import com.yusys.quartz.entity.TaskEntity;
import com.yusys.quartz.enums.JobStatusEnum;
import com.yusys.quartz.mapper.TaskMapper;
import com.yusys.quartz.query.TaskQuery;
import com.yusys.quartz.service.TaskService;
import com.yusys.quartz.utils.QuartzManager;
import com.yusys.quartz.vo.TaskVO;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by huyang on 2019/10/28.
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    QuartzManager quartzManager;

    @Override
    public TaskEntity get(Long id) {
        return taskMapper.get(id);
    }

    @Override
    public DataGridResult list(TaskQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<TaskVO> list = taskMapper.listTaskVoByDesc(query.getDescription());
        //取记录总条数
        PageInfo<TaskVO> pageInfo = new PageInfo<TaskVO>(list);
        long total = pageInfo.getTotal();
        //创建一个返回值对象
        DataGridResult result = new DataGridResult();
        result.setData(list);
        result.setCount(total);
        return result;
    }

    @Override
    public int save(TaskEntity task) {
        // TODO 新增的时候是Stop
        task.setJobStatus(JobStatusEnum.STOP.getCode());
        task.setCreateUser("huyang");
        task.setCreateTime(new Date());
        task.setUpdateUser("huyang");
        task.setUpdateTime(new Date());
        return taskMapper.save(task);
    }

    @Override
    public int update(TaskEntity task) {
        return taskMapper.update(task);
    }

    @Override
    public int remove(Long id) {
        try {
            TaskEntity task = get(id);
            quartzManager.deleteJob(task);
            return taskMapper.remove(id);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public int removeBatch(Long[] ids) {
        for (Long id : ids) {
            try {
                TaskEntity task = get(id);
                quartzManager.deleteJob(task);
            } catch (SchedulerException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return taskMapper.removeBatch(ids);
    }

    @Override
    public void initSchedule() throws SchedulerException {
        // 这里获取任务信息数据
        List<TaskEntity> jobList = taskMapper.list();
        System.out.println(jobList);
        for (TaskEntity task : jobList) {
            if (JobStatusEnum.RUNNING.getCode().equals(task.getJobStatus())) {
                quartzManager.addJob(task);
            }
        }
    }

    @Override
    public void changeStatus(Long jobId, String jobStatus) throws SchedulerException {
        TaskEntity task = get(jobId);
        if (task == null) {
            return;
        }
        if (JobStatusEnum.STOP.getCode().equals(jobStatus)) {
            quartzManager.deleteJob(task);
            task.setJobStatus(JobStatusEnum.STOP.getCode());
        } else {
            task.setJobStatus(JobStatusEnum.RUNNING.getCode());
            quartzManager.addJob(task);
        }
        update(task);
    }

    @Override
    public void updateCron(Long jobId,String cronExpression) throws SchedulerException {
        TaskEntity task = get(jobId);
        if (task == null) {
            return;
        }
        if (JobStatusEnum.RUNNING.getCode().equals(task.getJobStatus())) {
            task.setCronExpression(cronExpression);
            quartzManager.updateJobCron(task);
        }
        update(task);
    }

    @Override
    public void run(TaskEntity task) throws SchedulerException {
        quartzManager.runJobNow(task);
    }

    @Override
    public void pauseJob(TaskEntity scheduleJob) throws SchedulerException {
        quartzManager.pauseJob(scheduleJob);
    }

    @Override
    public void resumeJob(TaskEntity scheduleJob) throws SchedulerException {
        quartzManager.resumeJob(scheduleJob);
    }

    @Override
    public List<TaskEntity> getAllJob() throws SchedulerException {
        return quartzManager.getAllJob();
    }

    @Override
    public List<TaskEntity> getRunningJob() throws SchedulerException {
        return quartzManager.getRunningJob();
    }
}

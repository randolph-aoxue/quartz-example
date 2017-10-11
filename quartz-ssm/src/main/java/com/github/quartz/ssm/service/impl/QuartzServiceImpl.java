package com.github.quartz.ssm.service.impl;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.quartz.ssm.service.QuartzService;

@Service("quartzService")
public class QuartzServiceImpl implements QuartzService {

    @Autowired
    private Scheduler quartzScheduler;

    @Override
    public void addJob(String jobName, String jobGroup, String triggerName, String triggerGroup,
            Class<? extends Job> jobClass, String cronExpression) {
        try {
            // 创建一项作业
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();
            // 创建一个触发器
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroup)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();

            // 告诉调度器使用该触发器来安排作业
            quartzScheduler.scheduleJob(jobDetail, trigger);

            // 启动
            if (!quartzScheduler.isShutdown()) {
                quartzScheduler.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean modifyJob(String oldJobName, String oldJobGroup, String oldTriggerName, String oldTriggerGroup,
            String jobName, String jobGroup, String triggerName, String triggerGroup, String cronExpression) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(oldTriggerName, oldTriggerGroup);
            
            CronTrigger trigger = (CronTrigger) quartzScheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return false;
            }

            JobKey jobKey = JobKey.jobKey(oldJobName, oldJobGroup);

            JobDetail job = quartzScheduler.getJobDetail(jobKey);
            Class<? extends Job> jobClass = job.getJobClass();
            // 停止触发器
            quartzScheduler.pauseTrigger(triggerKey);
            // 移除触发器
            quartzScheduler.unscheduleJob(triggerKey);
            // 删除任务
            quartzScheduler.deleteJob(jobKey);

            addJob(jobName, jobGroup, triggerName, triggerGroup, jobClass, cronExpression);

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void modifyJobTime(String triggerName, String triggerGroupName, String cronExpression) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) quartzScheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cronExpression)) {
                // 修改时间
                trigger.getTriggerBuilder().withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
                // 重启触发器
                quartzScheduler.resumeTrigger(triggerKey);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeJob(String jobName, String jobGroup, String triggerName, String triggerGroup) {
        try {
            // 停止触发器
            quartzScheduler.pauseTrigger(TriggerKey.triggerKey(triggerName, triggerGroup));
            // 移除触发器
            quartzScheduler.unscheduleJob(TriggerKey.triggerKey(triggerName, triggerGroup));
            // 删除任务
            quartzScheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startSchedule() {
        try {
            quartzScheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void shutdownSchedule() {
        try {
            if (!quartzScheduler.isShutdown()) {
                quartzScheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void pauseJob(String jobName, String jobGroup) {
        try {
            quartzScheduler.pauseJob(JobKey.jobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void resumeJob(String jobName, String jobGroup) {
        try {
            quartzScheduler.resumeJob(JobKey.jobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}

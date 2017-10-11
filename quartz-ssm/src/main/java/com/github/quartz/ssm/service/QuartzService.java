package com.github.quartz.ssm.service;

import org.quartz.Job;

public interface QuartzService {

    /**
     * addJob(方法描述：添加一个定时任务) <br />
     * (方法适用条件描述： – 可选)
     * 
     * @param jobName
     *            作业名称
     * @param jobGroup
     *            作业组名称
     * @param triggerName
     *            触发器名称
     * @param triggerGroup
     *            触发器组名称
     * @param jobClass
     *            定时任务的class
     * @param cronExpression
     *            时间表达式
     */
    void addJob(String jobName, String jobGroup, String triggerName, String triggerGroup, Class<? extends Job> jobClass,
            String cronExpression);

    /**
     * 
     * @param oldJobName
     *            原作业名称
     * @param oldJobGroup
     *            原作业组s
     * @param oldTriggerName
     *            原 trigger name
     * @param oldTriggerGroup
     *            原 trigger group
     * @param jobName
     * @param jobGroup
     * @param triggerName
     * @param triggerGroup
     * @param cronExpression
     */
    boolean modifyJob(String oldJobName, String oldJobGroup, String oldTriggerName, String oldTriggerGroup,
            String jobName, String jobGroup, String triggerName, String triggerGroup, String cronExpression);

    /**
     * 修改触发器调度时间
     * 
     * @param triggerName
     *            触发器名称
     * @param triggerGroup
     *            触发器组名称
     * @param cronExpression
     *            cron表达式
     */
    void modifyJobTime(String triggerName, String triggerGroup, String cronExpression);

    /**
     * 暂停指定的任务
     * 
     * @param jobName
     *            任务名称
     * @param jobGroup
     *            任务组名称
     * @return
     */
    void pauseJob(String jobName, String jobGroup);

    /**
     * 恢复指定的任务
     * 
     * @param jobName
     *            任务名称
     * @param jobGroup
     *            任务组名称
     * @return
     */
    void resumeJob(String jobName, String jobGroup);

    /**
     * 删除指定组任务
     * 
     * @param jobName
     *            作业名称
     * @param jobGroup
     *            作业组名称
     * @param triggerName
     *            触发器名称
     * @param triggerGroup
     *            触发器组名称
     */
    void removeJob(String jobName, String jobGroup, String triggerName, String triggerGroup);

    /**
     * 开始所有定时任务。启动调度器
     */
    void startSchedule();

    /**
     * 关闭调度器
     */
    void shutdownSchedule();
}

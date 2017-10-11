package quartz.example01;

import org.apache.log4j.PropertyConfigurator;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.TriggerBuilder;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class SimpleExample {

    public void run() throws Exception {
        PropertyConfigurator.configure("E:/workspace_test/quartz-example/src/main/resources/log4j.properties");

        Logger log = LoggerFactory.getLogger(SimpleExample.class);

        log.info("------- Initializing ----------------------");

        // 生成任务调度器
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.info("------- Initialization Complete -----------");

        // 计算下次触发时间
        Date runTime = DateBuilder.evenMinuteDate(new Date());

        log.info("------- Scheduling Job  -------------------");

        // 定义HelloJob 
        JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("job1", "group1").build();

        // 定义触发器并设置Job执行时间
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();

        // 设置任务调度
        sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + runTime);

        // 开始任务调度
        sched.start();

        log.info("------- Started Scheduler -----------------");

        log.info("------- Waiting 65 seconds... -------------");
        try {
            Thread.sleep(65L * 1000L);
        } catch (Exception e) {
        }

        // 停止任务调度
        log.info("------- Shutting Down ---------------------");
        sched.shutdown(true);
        log.info("------- Shutdown Complete -----------------");
    }

    public static void main(String[] args) throws Exception {
        SimpleExample example = new SimpleExample();
        example.run();
    }

}

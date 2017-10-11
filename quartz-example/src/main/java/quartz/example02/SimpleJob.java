package quartz.example02;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

public class SimpleJob implements Job {

    private static Logger _log = LoggerFactory.getLogger(SimpleJob.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {

        // 打印job name 和 执行时间
        JobKey jobKey = context.getJobDetail().getKey();
        _log.info("SimpleJob says: " + jobKey + " executing at " + new Date());
    }

}

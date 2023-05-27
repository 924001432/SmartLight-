package com.example.light.config;

import com.example.light.job.IdeaJob;
import com.example.light.job.TimingJob;
import com.example.light.common.SpringUtils;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MyScheduler {


    public void schedulerJob() throws SchedulerException {
        ApplicationContext annotationContext = SpringUtils.getApplicationContext();
        StdScheduler stdScheduler = (StdScheduler) annotationContext.getBean("mySchedulerFactoryBean");//获得上面创建的bean
        Scheduler myScheduler =stdScheduler;
        startScheduler1(myScheduler);
        startScheduler2(myScheduler);
        myScheduler.start();
    }

    /**
     *  IdeaJob 定时任务，每月一号执行
     */
    public void startScheduler1(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(IdeaJob.class).withIdentity("job1", "jobGroup1").build();

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 1 1 * ?");//每月1号凌晨1点执行一次：`0 0 1 1 * ?`
//        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");//测试，每分钟的10秒钟执行一次

        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1")
                .withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, trigger);
    }
    /**
     *  TimingJob 校时任务，每天凌晨执行
     */
    public void startScheduler2(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(TimingJob.class).withIdentity("job2", "jobGroup2").build();

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 0 * * ?");//每天0点执行一次：`0 0 0 * * ?`
//        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");//测试，每分钟的10秒钟执行一次

        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "triggerGroup2")
                .withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, trigger);

    }

}
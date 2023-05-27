package com.example.light.job;


import com.example.light.common.SpringUtils;
import com.example.light.service.AlarmService;
import com.example.light.service.NewsProducerService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TimingJob implements Job {

    @Autowired
    private NewsProducerService newsProducerService;

    public TimingJob() {
    }

    /**
     * 有点难理解，spring注入相关的知识
     */
//    @Autowired   //这里不能直接注入，因为@Autowired注入是Spring的注入，要求注入对象与被注入对象都是在SpringIOC容器中存在，
//    public QuartzDemoJob(QuartzDemoService quartzDemoService) {
//        this.quartzDemoService = quartzDemoService;
//    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        ApplicationContext applicationContext = SpringUtils.getApplicationContext();
        NewsProducerService newsProducerService = applicationContext.getBean(NewsProducerService.class);

        Date current_time = new Date();
        String strTime = dateToStr(current_time, "HH:mm:ss");

        System.out.println("Time: " + strTime);

        /*
            TODO:校时模块，时间数据格式处理，获取每一位值转16进制，字符串转
         */
        //协议
        byte[] time = {0x05,0x00,0x00,0x00,0x23};

        //获取当前时分秒
        byte Hours = (byte)Integer.parseInt(strTime.substring(0, 2));
        byte Minutes = (byte)Integer.parseInt(strTime.substring(3, 5));
        byte Seconds = (byte)Integer.parseInt(strTime.substring(6));
        //赋值
        time[1] = Hours;
        time[2] = Minutes;
        time[3] = Seconds;
        //发布消息
        newsProducerService.publishBytes(time);


    }

    public static String dateToStr(Date date, String strFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(strFormat);
        String str = sf.format(date);
        return str;
    }

    public static java.sql.Timestamp strToSqlDate(String strDate, String dateFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = sf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Timestamp dateSQL = new java.sql.Timestamp(date.getTime());
        return dateSQL;
    }


}
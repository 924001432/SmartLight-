//package com.example.light.job;
//
//
//import com.example.light.common.SpringUtils;
//import com.example.light.entity.Idea;
//import com.example.light.service.IdeaService;
//import com.example.light.service.NewsProducerService;
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Component;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@Component
//public class TestHeartJob implements Job {
//    /**
//     * 有点难理解，spring注入相关的知识
//     */
////    @Autowired   //这里不能直接注入，因为@Autowired注入是Spring的注入，要求注入对象与被注入对象都是在SpringIOC容器中存在，
////    public QuartzDemoJob(QuartzDemoService quartzDemoService) {
////        this.quartzDemoService = quartzDemoService;
////    }
//
//    @Override
//    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//
//        ApplicationContext applicationContext = SpringUtils.getApplicationContext();
//        //获取上下文
//        NewsProducerService newsProducerService = applicationContext.getBean(NewsProducerService.class);
//        IdeaService ideaService = applicationContext.getBean(IdeaService.class);
//
//        Date current_time = new Date();
//        String strDate = dateToStr(current_time, "yyyy-MM-dd HH:mm:ss");
//
//
//        System.out.println("Date: " + strDate);
//        时间数据格式处理，获取每一位值转16进制
////        /*
//         *   自动执行定时计划，获取当前月份，匹配数据库记录，数据处理，构造协议，下发命令
//         */
//        //获取当前月份计划的数据库记录
//
//        //数据处理，得到时间段信息，打包数据
//        byte[] payload={0x58,0x44,0x01,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x01,0x00,0x55,0x01,0x02,0x23};
//
//        newsProducerService.testPublish(payload);
//
//
//    }
//
//    public static String dateToStr(Date date, String strFormat) {
//        SimpleDateFormat sf = new SimpleDateFormat(strFormat);
//        String str = sf.format(date);
//        return str;
//    }
//
//    public static java.sql.Timestamp strToSqlDate(String strDate, String dateFormat) {
//        SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
//        Date date = null;
//        try {
//            date = sf.parse(strDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        java.sql.Timestamp dateSQL = new java.sql.Timestamp(date.getTime());
//        return dateSQL;
//    }
//
//
//}
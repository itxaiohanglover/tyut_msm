package com.tyut.msm.config;

import com.tyut.msm.service.StaService;
import com.tyut.msm.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author xh
 * @Date 2022/1/18
 * 定时任务
 */
@Component
public class ScheduledTask {

    @Autowired
    private StaService staService;

    /**
     * 每5分钟从redis中统计访问量并添加到数据库中
     */
//    @Scheduled(fixedRate = 50000)
//    public void task1() {
//        System.out.println("5分钟执行了redis与mysql同步");
//    }


    /**
     * 每天凌晨0点执行定时,统计昨天的数据
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void task2() {
        //获取上一天的日期
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        staService.createStatisticsByDay(day);
    }



}

package com.tyut.msm.service;

import com.tyut.msm.entity.Sta;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 小航
 * @since 2022-01-17
 */
public interface StaService extends IService<Sta> {

    // 根据时间查询统计量
    Sta countViews(String day);

    // 前端定时重写统计浏览量
    void updateByNum(Integer viewNum);

    // 统计上一天的数据
    void createStatisticsByDay(String day);

    // 每次访问增加访问量
    void addView();

    // 增加访问量和浏览量
    void addAll();
}

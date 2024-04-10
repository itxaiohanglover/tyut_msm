package com.tyut.msm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tyut.msm.entity.Sta;
import com.tyut.msm.mapper.StaMapper;
import com.tyut.msm.service.StaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyut.msm.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小航
 * @since 2022-01-17
 */
@Service
public class StaServiceImpl extends ServiceImpl<StaMapper, Sta> implements StaService {

    @Autowired
    private StaService staService;

    // 根据时间查询统计量
    @Override
    public Sta countViews(String day) {
        QueryWrapper<Sta> wrapper = new QueryWrapper<>();
        wrapper.like("gmt_modified", day);
        List<Sta> stas = baseMapper.selectList(wrapper);
        if(stas.size() == 0) {
            //获取上一天的日期
            String dayLst = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
            staService.createStatisticsByDay(dayLst);
            stas = baseMapper.selectList(wrapper);
        }
        return stas.get(0);
    }

    // 前端定时重写统计浏览量
    @Override
    public void updateByNum(Integer viewNum) {
        Sta sta = new Sta();
        sta.setViewNum(viewNum);
        sta.setId("1");
        baseMapper.updateById(sta);
    }

    // 统计上一天的数据
    @Override
    public void createStatisticsByDay(String day) {
        QueryWrapper<Sta> wrapper = new QueryWrapper<>();
        wrapper.like("gmt_modified", day);
        List<Sta> stas = baseMapper.selectList(wrapper);
        Sta sta = new Sta();
        sta.setViewNum(stas.get(0).getViewNum());
        sta.setTotalView(stas.get(0).getTotalView());
        sta.setMsmNum(stas.get(0).getMsmNum());
        sta.setTotalMsm(stas.get(0).getTotalMsm());
        // 插入当天新的一列
        baseMapper.insert(sta);
    }
    // 每次访问增加访问量
    @Override
    public void addView() {
        QueryWrapper<Sta> wrapper = new QueryWrapper<>();
        wrapper.like("gmt_modified", DateUtil.formatDate(new Date()));
        List<Sta> stas = baseMapper.selectList(wrapper);
        Sta sta = stas.get(0);
        sta.setViewNum(sta.getViewNum() + 1);
        sta.setTotalView(sta.getTotalView() + 1);
        baseMapper.updateById(sta);
    }

    // 增加访问量和浏览量
    @Override
    public void addAll() {
        QueryWrapper<Sta> wrapper = new QueryWrapper<>();
        wrapper.like("gmt_modified", DateUtil.formatDate(new Date()));
        List<Sta> stas = baseMapper.selectList(wrapper);
        Sta sta = stas.get(0);
        sta.setViewNum(sta.getViewNum() + 1);
        sta.setTotalView(sta.getTotalView() + 1);
        sta.setMsmNum(sta.getMsmNum() + 1);
        sta.setTotalMsm(sta.getTotalMsm() + 1);
        baseMapper.updateById(sta);
    }

}

package com.tyut.msm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tyut.msm.entity.Msm;
import com.tyut.msm.entity.query.MsmQuery;
import com.tyut.msm.entity.subject.OneSubject;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 小航
 * @since 2022-01-16
 */
public interface MsmService extends IService<Msm> {

    // 查询已审核的留言
    List<Msm> getAcMsM(Integer num);

    // 查询最新的8条留言
    List<Msm> getMsM();

    // 统计留言总数
    Integer countMsm();

    // 获取随机留言
    List<Msm> getgetRandomMsm();

    // 添加留言
    void saveMsm(Msm msm);

    // 根据id删除留言
    void delMsgById(String id);

    // 分页查询留言、多条件查询
    void pageQuery(Page<Msm> pageParam, MsmQuery msmQuery);

    // 根据id查询留言
    Msm getMsgById(String id);

    // 统计数据
    Map<String, Object> getChartData(String type);

    // 统计个人留言数
    Integer countMsmByDay(String day);

    // 获取全部留言（树形）
    List<OneSubject> getOneTwoSubject();
}

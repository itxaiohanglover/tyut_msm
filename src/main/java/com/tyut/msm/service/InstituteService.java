package com.tyut.msm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tyut.msm.entity.Institute;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 小航
 * @since 2022-01-16
 */
public interface InstituteService extends IService<Institute> {

    // 学院排行榜
    List<Institute> getTop();

    // 根据id更新留言数
    void updateCount(String iId);

    // 根据学院名称查询ID
    String getIdByName(String name);

    // 根据名称判断是否存在学院
    boolean isExistByName(String iname);

    // 根据id查询学院名称
    String getNameById(String iId);
}

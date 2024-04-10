package com.tyut.msm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tyut.msm.entity.ClassInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 小航
 * @since 2022-01-16
 */
public interface ClassInfoService extends IService<ClassInfo> {

    // 根据学院id查询班级
    List<ClassInfo> getClassInfoByIId(String IId);

    // 班级排行榜
    List<ClassInfo> getTop();

    // 根据留言更新留言数
    void updateCount(String iId);

    // 根据学院名称查询专业
    List<ClassInfo> getClassByName(String iName);

    // 专业班级一键删除
    void removeByName(String name);

    // 根据名称查询ID
    String getIdByName(String cname);

    // 根据id查询班级
    String getNameById(String cId);
}

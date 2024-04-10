package com.tyut.msm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyut.msm.entity.Msm;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 小航
 * @since 2022-01-16
 */
public interface MsmMapper extends BaseMapper<Msm> {

    // 获取随机留言
    List<Msm> getgetRandomMsm();

    // 统计个人留言数
    Integer selectMsmCount(String day);

    // 查询审核通过的留言
    List<Msm> selectAcNum();
}

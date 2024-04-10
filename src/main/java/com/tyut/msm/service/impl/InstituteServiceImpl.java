package com.tyut.msm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyut.msm.entity.ClassInfo;
import com.tyut.msm.entity.Institute;
import com.tyut.msm.mapper.InstituteMapper;
import com.tyut.msm.service.InstituteService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小航
 * @since 2022-01-16
 */
@Service
public class InstituteServiceImpl extends ServiceImpl<InstituteMapper, Institute> implements InstituteService {

    // 学院排行榜
    @Override
    public List<Institute> getTop() {
        QueryWrapper<Institute> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("i_count");
        wrapper.last("limit 10");
        return baseMapper.selectList(wrapper);
    }

    // 根据留言更新留言数
    @Override
    public void updateCount(String iId) {
        Institute institute = baseMapper.selectById(iId);
        institute.setICount(institute.getICount() + 1);
        baseMapper.updateById(institute);
    }

    // 根据名称查询ID
    @Override
    public String getIdByName(String name) {
        QueryWrapper<Institute> wrapper = new QueryWrapper<>();
        wrapper.eq("i_name", name);
        List<Institute> institutes = baseMapper.selectList(wrapper);
        if(institutes.size() <= 0){
            return "";
        }
        return institutes.get(0).getId();
    }

    // 根据名称判断是否存在学院
    @Override
    public boolean isExistByName(String iname) {
        QueryWrapper<Institute> wrapper = new QueryWrapper<>();
        wrapper.eq("i_name", iname);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0){
            return true;
        }
        return false;
    }

    // 根据学院id查询学院名称
    @Override
    public String getNameById(String iId) {
        QueryWrapper<Institute> wrapper = new QueryWrapper<>();
        wrapper.like("id", iId);
        List<Institute> institutes = baseMapper.selectList(wrapper);
        return institutes.get(0).getIName();
    }
}

package com.tyut.msm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyut.msm.entity.ClassInfo;
import com.tyut.msm.entity.Institute;
import com.tyut.msm.mapper.ClassInfoMapper;
import com.tyut.msm.service.ClassInfoService;
import com.tyut.msm.service.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ClassInfoServiceImpl extends ServiceImpl<ClassInfoMapper, ClassInfo> implements ClassInfoService {

    @Autowired
    private InstituteService instituteService;

    // 根据学院id查询班级
    @Override
    public List<ClassInfo> getClassInfoByIId(String IId) {
        QueryWrapper<ClassInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("i_id", IId);
        List<ClassInfo> classInfos = baseMapper.selectList(wrapper);
        return classInfos;
    }

    // 班级排行榜
    @Override
    public List<ClassInfo> getTop() {
        QueryWrapper<ClassInfo> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("c_count");
        wrapper.last("limit 10");
        return baseMapper.selectList(wrapper);
    }

    // 根据留言更新留言数
    @Override
    public void updateCount(String iId) {
        ClassInfo classInfo = baseMapper.selectById(iId);
        classInfo.setCCount(classInfo.getCCount() + 1);
        baseMapper.updateById(classInfo);
    }

    // 根据学院名称查询专业
    @Override
    public List<ClassInfo> getClassByName(String iName) {
        QueryWrapper<ClassInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("i_id", instituteService.getIdByName(iName));
        return baseMapper.selectList(wrapper);
    }

    // 专业班级一键删除
    @Override
    public void removeByName(String name) {
        QueryWrapper<ClassInfo> wrapper = new QueryWrapper<>();
        wrapper.like("c_name", name);
        baseMapper.delete(wrapper);
    }

    // 根据名称查询ID
    @Override
    public String getIdByName(String cname) {
        QueryWrapper<ClassInfo> wrapper = new QueryWrapper<>();
        wrapper.like("c_name", cname);
        List<ClassInfo> classInfos = baseMapper.selectList(wrapper);
        if(classInfos.size() <= 0){
            return "";
        }
        return classInfos.get(0).getId();
    }

    // 根据id查询名称
    @Override
    public String getNameById(String cId) {
        QueryWrapper<ClassInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("id", cId);
        List<ClassInfo> classInfos = baseMapper.selectList(wrapper);
        return classInfos.get(0).getCName();
    }

}

package com.tyut.msm.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyut.msm.entity.ClassInfo;
import com.tyut.msm.entity.Institute;
import com.tyut.msm.entity.Msm;
import com.tyut.msm.entity.Sta;
import com.tyut.msm.entity.query.MsmQuery;
import com.tyut.msm.entity.subject.OneSubject;
import com.tyut.msm.entity.subject.TwoSubject;
import com.tyut.msm.mapper.MsmMapper;
import com.tyut.msm.service.ClassInfoService;
import com.tyut.msm.service.InstituteService;
import com.tyut.msm.service.MsmService;
import com.tyut.msm.service.StaService;
import org.apache.ibatis.annotations.One;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小航
 * @since 2022-01-16
 */
@Service
public class MsmServiceImpl extends ServiceImpl<MsmMapper, Msm> implements MsmService {

    @Autowired
    private InstituteService instituteService;

    @Autowired
    private ClassInfoService classInfoService;

    @Autowired
    private StaService staService;

    // 查询已审核的留言,1代表审核通过
    @Override
    public List<Msm> getAcMsM(Integer num) {
        QueryWrapper<Msm> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        List<Msm> msms = baseMapper.selectList(wrapper);
        Collections.shuffle(msms);
        if(msms.size() < 5){
            return msms;
        }else{
            return msms.subList(0, 5);
        }
    }

    // 查询最新的8条弹幕
    @Override
    public List<Msm> getMsM() {
        QueryWrapper<Msm> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<Msm> msms = baseMapper.selectList(wrapper);
        return msms;
    }

    // 统计留言总数
    @Override
    public Integer countMsm() {
        QueryWrapper<Msm> wrapper = new QueryWrapper<>();
        wrapper.select("id");
        Integer integer = baseMapper.selectCount(wrapper);
        return integer;
    }

    // 获取随机留言
    @Override
    public List<Msm> getgetRandomMsm() {
        return baseMapper.getgetRandomMsm();
    }

    // 添加留言
    @Override
    public void saveMsm(Msm msm) {
        QueryWrapper<Msm> wrapper = new QueryWrapper<>();
        wrapper.eq("id", msm.getId());
        Integer count = baseMapper.selectCount(wrapper);
        System.out.println(count);
        if(count > 0) {
            baseMapper.updateById(msm);
        }else {
            baseMapper.insert(msm);
        }
        if(!StringUtils.isEmpty(msm.getCId())) {
            classInfoService.updateCount(msm.getCId());
        }
        if(!StringUtils.isEmpty(msm.getIId())) {
            instituteService.updateCount(msm.getIId());
        }
    }

    // 根据id删除留言
    @Override
    public void delMsgById(String id) {
        baseMapper.deleteById(id);
    }

    // 分页查询留言、多条件查询
    @Override
    public void pageQuery(Page<Msm> pageParam, MsmQuery msmQuery) {
        QueryWrapper<Msm> queryWrapper = new QueryWrapper<>();
        if (msmQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String name = msmQuery.getName();
        String cId = msmQuery.getCId();
        String iId = msmQuery.getCId();
        String begin = msmQuery.getBegin();
        String end = msmQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }

        if (!StringUtils.isEmpty(cId) ) {
            queryWrapper.eq("c_id", cId);
        }

        if (!StringUtils.isEmpty(iId) ) {
            queryWrapper.eq("i_id", iId);
        }

        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }
        baseMapper.selectPage(pageParam, queryWrapper);
    }

    // 根据id查询留言
    @Override
    public Msm getMsgById(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public Map<String, Object> getChartData(String type) {

        Map<String, Object> map = new HashMap<>();
        List<String> xList = new ArrayList<String>();
        List<Integer> yList = new ArrayList<Integer>();
        map.put("xList", yList);
        map.put("yList", xList);
        SimpleDateFormat formatdate= new SimpleDateFormat("yyyy-MM-dd");
        switch (type) {
            case "i_num":
                List<Institute> institutes =  instituteService.list(null);
                for (Institute institute : institutes) {
                    xList.add(institute.getIName());
                }
                for (Institute institute : institutes) {
                    yList.add(institute.getICount());
                }
                break;
            case "c_num":
                List<ClassInfo> classInfos = classInfoService.list(null);
                for (ClassInfo classInfo : classInfos) {
                    xList.add(classInfo.getCName());
                }
                for (ClassInfo classInfo : classInfos) {
                    yList.add(classInfo.getCCount());
                }
                break;
            case "view":
                QueryWrapper<Sta> wrapper = new QueryWrapper<>();
                wrapper.orderByAsc("gmt_modified");
                List<Sta> stas =  staService.list(wrapper);
                for (Sta sta : stas) {
                    xList.add(formatdate.format(sta.getGmtModified()));
                }
                for (Sta sta : stas) {
                    yList.add(sta.getViewNum());
                }
                break;
            case "msm":
                List<Sta> stass =  staService.list(null);
                for (Sta sta : stass) {
                    xList.add(formatdate.format(sta.getGmtModified()));
                }
                for (Sta sta : stass) {
                    yList.add(sta.getMsmNum());
                }
                break;
            default:
                break;
        }
        return map;
    }


    // 统计个人留言数
    @Override
    public Integer countMsmByDay(String day) {
        return baseMapper.selectMsmCount(day);
    }

    // 获取全部留言（树形）
    @Override
    public List<OneSubject> getOneTwoSubject() {
        // 一级分类集合
        List<OneSubject> oneSubjects = new ArrayList<>();
        List<Institute> oneSubjectLists = instituteService.list(null);
        for(Institute oneSubjectList : oneSubjectLists) {
            // 一级分类
            OneSubject oneSubject = new OneSubject();
            oneSubject.setId(oneSubjectList.getId());
            oneSubject.setTitle(oneSubjectList.getIName());
            // 二级分类集合
            List<TwoSubject> twoSubjects = new ArrayList<>();
            QueryWrapper<ClassInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("i_id", oneSubjectList.getId());
            List<ClassInfo> twoSubjectLists = classInfoService.list(wrapper);
            for (ClassInfo twoSubjectList : twoSubjectLists) {
                // 二级分类
                TwoSubject twoSubject = new TwoSubject();
                twoSubject.setId(twoSubjectList.getId());
                twoSubject.setTitle(twoSubjectList.getCName());
                twoSubjects.add(twoSubject);
            }
            oneSubject.setChildren(twoSubjects);
            oneSubjects.add(oneSubject);
        }
        return oneSubjects;
    }
}

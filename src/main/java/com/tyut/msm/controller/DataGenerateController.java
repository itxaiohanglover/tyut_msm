package com.tyut.msm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tyut.msm.entity.ClassInfo;
import com.tyut.msm.entity.Institute;
import com.tyut.msm.entity.Msm;
import com.tyut.msm.entity.query.MsmQuery;
import com.tyut.msm.entity.query.ObjQuery;
import com.tyut.msm.entity.query.PageQuery;
import com.tyut.msm.service.ClassInfoService;
import com.tyut.msm.service.InstituteService;
import com.tyut.msm.service.MsmService;
import com.tyut.msm.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xh
 * @Date 2022/1/17
 */
@RestController
@RequestMapping("/data/")
@CrossOrigin
public class DataGenerateController {

    @Autowired
    private MsmService msmService;
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private InstituteService instituteService;

    // 学院班级生成
    @PostMapping("createInfo")
    public R createInfo(@RequestBody() ObjQuery objQuery) {
        if(StringUtils.isEmpty(objQuery.getIname()) || StringUtils.isEmpty(objQuery.getCname()) || objQuery.getCnum() == null || objQuery.getCnum() <= 0) {
            return R.error();
        }
        // TODO 判断redis，如果有查询数据，则清除
        // 如果不存在再去创建
        if(!instituteService.isExistByName(objQuery.getIname())) { // 根据名称判断是否存在学院
            Institute institute = new Institute();
            institute.setIName(objQuery.getIname());
            institute.setICount(0);
            instituteService.saveOrUpdate(institute);
        }
        for(int i = 1; i <= objQuery.getCnum(); i ++) {
            ClassInfo classInfo = new ClassInfo();
            classInfo.setCName(objQuery.getCname() + String.format("%02d", i) + "班");
            classInfo.setIId(instituteService.getIdByName(objQuery.getIname()));
            classInfo.setCCount(0);
            classInfoService.saveOrUpdate(classInfo);
        }
        return R.ok();
    }

    // 分页查询,学院班级
    @PostMapping("/{page}/{limit}")
    public R getPageList(
            @PathVariable Long page, @PathVariable Long limit) {
        long total = 0;
        // 查到所有的学院
        List<Institute> institutes = instituteService.list(null);
        // TODO 判断redis里面是否有数据
        List<PageQuery> pageQuerys = new ArrayList<>();
        for (Institute institute : institutes) {
            // 整合专业
            // 根据学院名称查询专业
            List<ClassInfo> classInfos = classInfoService.getClassByName(institute.getIName());
            // 词频统计
            HashMap<String, Integer> map = new HashMap<>();
            for(ClassInfo classInfo : classInfos) {
                String classname = classInfo.getCName();
                classname = classname.substring(0, classname.length() - 3);
                map.put(classname, map.getOrDefault(classname, 0) + 1);
            }
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                PageQuery pageQuery = new PageQuery();
                // 整合学院
                pageQuery.setIname(institute.getIName());
                // 整合班级
                pageQuery.setCname(entry.getKey());
                pageQuery.setCnum(entry.getValue());
                pageQuerys.add(pageQuery);
                total ++;
            }
        }
        List<PageQuery> records = pageQuerys;
        // TODO 放入redis，再此查询先到redis里面查询，班级个数一般不会发生变化的
        int start = (int)((page - 1) * limit);
        int end = Math.min(pageQuerys.size(), (int)(page * limit));
        records = records.subList(start, end);
        return  R.ok().data("total", total).data("rows", records);
    }


    // 专业班级一键删除
    @GetMapping("del/{name}")
    public R delClassByName(@PathVariable String name) {
        classInfoService.removeByName(name);
        return R.ok();
    }
}

package com.tyut.msm.controller;


import com.tyut.msm.entity.ClassInfo;
import com.tyut.msm.service.ClassInfoService;
import com.tyut.msm.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小航
 * @since 2022-01-16
 */
@RestController
@RequestMapping("/classInfo/")
@CrossOrigin
public class ClassInfoController {

    @Autowired
    private ClassInfoService classInfoService;

    // 根据学院id查询班级
    @GetMapping("getClassInfoByIId")
    public R getClassInfoByIId(String IId) {
        List<ClassInfo> classInfos = classInfoService.getClassInfoByIId(IId);
        return R.ok().data("classInfos", classInfos);
    }

    // 班级排行榜
    @GetMapping("getTop")
    public R getTop() {
        List<ClassInfo> iTopList = classInfoService.getTop();
        return R.ok().data("iToplist", iTopList);
    }
}


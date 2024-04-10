package com.tyut.msm.controller;


import com.tyut.msm.entity.Institute;
import com.tyut.msm.service.InstituteService;
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
@RequestMapping("/institute/")
@CrossOrigin
public class InstituteController {

    @Autowired
    private InstituteService instituteService;

    // 学院排行榜
    @GetMapping("getTop")
    public R getTop() {
        List<Institute> iTopList = instituteService.getTop();
        return R.ok().data("iToplist", iTopList);
    }

    // 全部学院
    @GetMapping("getAll")
    public R getAll() {
        List<Institute> iList = instituteService.list(null);
        return R.ok().data("iList", iList);
    }

}


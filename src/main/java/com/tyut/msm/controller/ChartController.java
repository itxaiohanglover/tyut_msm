package com.tyut.msm.controller;

import com.tyut.msm.service.MsmService;
import com.tyut.msm.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author xh
 * @Date 2022/1/17
 */
@RestController
@RequestMapping("/show-chart/")
@CrossOrigin
public class ChartController {

    @Autowired
    private MsmService msmService;

    // 生成图表数据
    @GetMapping("{type}")
    public R showChart(@PathVariable String type){
        Map<String, Object> map = msmService.getChartData(type);
        return R.ok().data(map);
    }
}

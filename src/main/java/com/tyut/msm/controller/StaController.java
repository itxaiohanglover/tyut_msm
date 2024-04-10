package com.tyut.msm.controller;


import com.tyut.msm.entity.Sta;
import com.tyut.msm.service.StaService;
import com.tyut.msm.utils.DateUtil;
import com.tyut.msm.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小航
 * @since 2022-01-17
 */
@RestController
@RequestMapping("/sta/")
@CrossOrigin
public class StaController {

    @Autowired
    private StaService staService;

    // 统计
    @GetMapping("countViews")
    public R countViews() {
        String day = DateUtil.formatDate(new Date());
        Sta stas = staService.countViews(day);
        return R.ok()
                .data("viewNum", stas.getTotalView())
                .data("msmNum", stas.getTotalMsm())
                .data("dateTime", stas.getGmtModified())
                .data("lastView", stas.getViewNum())
                .data("lastMsm", stas.getMsmNum());
    }


}


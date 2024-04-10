package com.tyut.msm.controller;

import com.tyut.msm.utils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @author xh
 * @Date 2022/1/16
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class LoginController {
    @PostMapping("/login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("/info")
    public R info(){
        return R.ok()
                .data("roles","[admin]")
                .data("name","admin")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    @GetMapping("/logout")
    public R logout(){
        return R.ok();
    }



}

package com.tyut.msm.entity.query;

import lombok.Data;

import java.util.Date;

/**
 * @author xh
 * @Date 2022/1/22
 */
@Data
public class MsmShow {
    private static final long serialVersionUID = 1L;

    private String name;
    // 班级
    private String cname;
    // 学院
    private String iname;

    private String content;

    private Date date;

    // 用户图片路径
    private String path;
}

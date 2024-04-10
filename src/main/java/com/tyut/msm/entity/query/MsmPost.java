package com.tyut.msm.entity.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xh
 * @Date 2022/1/22
 */
@Data
public class MsmPost {
    private static final long serialVersionUID = 1L;

    private String name = "晨晨曦曦";
    // 班级
    private String cname;
    // 学院
    private String iname;

    private String content;
}

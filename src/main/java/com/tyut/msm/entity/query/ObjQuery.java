package com.tyut.msm.entity.query;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xh
 * 一键生成专业、班级用
 * @Date 2022/1/17
 */
@Data
public class ObjQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    // 学院名称
    private String iname;

    // 专业名称
    private String cname;

    // 班级个数
    private Integer cnum;

}

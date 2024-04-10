package com.tyut.msm.entity.query;

import lombok.Data;

/**
 * @author xh
 * @Date 2022/1/22
 */
@Data
public class MsmAdmin {
    private static final long serialVersionUID = 1L;
    private String id;

    private String name;
    // 班级
    private String cname;
    // 学院
    private String iname;

    private String content;

    private Integer status;

    private String path;
}

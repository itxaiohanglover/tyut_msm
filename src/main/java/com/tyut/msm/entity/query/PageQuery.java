package com.tyut.msm.entity.query;

import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xh
 * @Date 2022/1/17
 */
@Data
public class PageQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    // 学院名称
    private String iname;

    // 专业名称
    private String cname;

    // 班级个数
    private Integer cnum;


}

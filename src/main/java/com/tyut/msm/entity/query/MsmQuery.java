package com.tyut.msm.entity.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xh
 * @Date 2022/1/16
 */
@Data
public class MsmQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "留言人,模糊查询")
    private String name;
    // 班级id
    private String cId;
    // 学院id
    private String iId;

    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    private String end;
}

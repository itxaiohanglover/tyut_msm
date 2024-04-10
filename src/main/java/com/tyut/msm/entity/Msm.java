package com.tyut.msm.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author 小航
 * @since 2022-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Msm对象", description="")
public class Msm implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "留言Id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "姓名")
    private String name = "晨晨曦曦";

    @ApiModelProperty(value = "留言内容")
    private String content;

    @ApiModelProperty(value = "班级ID")
    @JsonProperty(value = "cId")
    private String cId;

    @ApiModelProperty(value = "学院ID")
    @JsonProperty(value = "iId")
    private String iId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "审核状态")
    private Integer status = 1;

    @ApiModelProperty(value = "用户图片路径")
    private String path;
}

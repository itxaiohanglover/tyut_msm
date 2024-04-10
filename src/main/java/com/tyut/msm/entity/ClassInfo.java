package com.tyut.msm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@ApiModel(value="ClassInfo对象", description="")
public class ClassInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "班级ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "班级名称")
    private String cName;

    @ApiModelProperty(value = "班级留言数")
    private Integer cCount;

    @ApiModelProperty(value = "学院ID")
    @JsonProperty(value = "iId")
    private String iId;


}

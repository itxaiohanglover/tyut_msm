package com.tyut.msm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 小航
 * @since 2022-01-16
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsmException extends RuntimeException{

    private Integer code; // 状态码

    private String msg; // 异常信息
}

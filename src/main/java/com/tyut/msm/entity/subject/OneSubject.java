package com.tyut.msm.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xh
 * 一级分类
 * @Date 2022/1/20
 */
@Data
public class OneSubject {
    private String id;
    private String title;
    private List<TwoSubject> children = new ArrayList<>();
}

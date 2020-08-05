package com.example.butterknife_comple;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * className: bindView
 * description:j
 * author：lix
 * email：lixuan_1@163.com
 * date: 2020/8/5 16:27
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface BindView {
    int value();
}

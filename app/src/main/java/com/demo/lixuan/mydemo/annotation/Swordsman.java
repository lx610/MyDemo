package com.demo.lixuan.mydemo.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Swordsman {
    String name() default "张无忌";
    int age();
}

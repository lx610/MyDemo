package com.demo.lixuan.mydemo.annotion;


import android.app.Activity;
import android.content.Intent;

import org.junit.Test;

import java.io.Serializable;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * className: LoginTest
 * description:java类作用描述
 * author：lix
 * email：lixuan_1@163.com
 * date: 2020/7/30 16:41
 */
public class GiveFieldTest {

    @FruitName(value = "水果")
    String appleName;

    @FruitColor(fruitColor= FruitColor.Color.RED)
    private String appleColor;


    public void setAppleColor(String appleColor) {
        this.appleColor = appleColor;
    }
    public String getAppleColor() {
        return appleColor;
    }


    public void setAppleName(String appleName) {
        this.appleName = appleName;
    }
    public String getAppleName() {
        return appleName;
    }

    @Test
    public void displayName(){
        // 注入
        DynamicUtil.inject(this);

        System.out.println("水果的名字是：" + appleName);
    }




}

/**
 * 水果名称注解
 * @author peida
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface FruitName {
    String value() default "苹果";
}

/**
 * 水果颜色注解
 * @author peida
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface FruitColor {


    /**
     * 颜色枚举
     * @author peida
     *
     */
    public enum Color{ BULE,RED,GREEN};

    /**
     * 颜色属性
     * @return
     */
    Color fruitColor() default Color.GREEN;

}

class DynamicUtil {
    public static void inject(Object c) {

        // 反射
        for (Field field : c.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(FruitName.class)) {

                // 获取注解
                FruitName annotation = field.getAnnotation(FruitName.class);
                String intentKey = annotation.value();

                // 插入值
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
//                    field.set(activity, serializable);
                field.setAccessible(accessible);
                try {
                    field.set(c,intentKey);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
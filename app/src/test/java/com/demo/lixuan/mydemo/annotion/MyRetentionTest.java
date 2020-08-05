package com.demo.lixuan.mydemo.annotion;

import org.junit.Test;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;


/**
 * className: MyRetentionTest
 * description:注解
 * author：lix
 * email：lixuan_1@163.com
 * date: 2020/7/30 16:22
 */
@Deprecated
public class MyRetentionTest {

    //使用我们的自定义注解
    @MyField(description = "用户名", length = 12)
    private String username;

    @Test
    public void testMyField(){

        // 获取类模板
        Class c = MyRetentionTest.class;

        // 获取所有字段
        for(Field f : c.getDeclaredFields()){
            // 判断这个字段是否有MyField注解
            if(f.isAnnotationPresent(MyField.class)){
                MyField annotation = f.getAnnotation(MyField.class);
                System.out.println("字段:[" + f.getName() + "], 描述:[" + annotation.description() + "], 长度:[" + annotation.length() +"]");
            }
        }

    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MyField {
        String description();
        int length();
    }
}

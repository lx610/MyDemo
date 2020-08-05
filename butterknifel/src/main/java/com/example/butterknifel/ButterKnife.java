package com.example.butterknifel;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

/**
 * className: ButterKnife
 * description:java类作用描述
 * author：lix
 * email：lixuan_1@163.com
 * date: 2020/8/3 15:49
 */
public class ButterKnife {
    public static void bind(AppCompatActivity activity) {
        String name = activity.getClass().getName();
        try {
            Class<?> clazz = Class.forName(name + "_ViewBinding");
            clazz.getConstructor(activity.getClass()).newInstance(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

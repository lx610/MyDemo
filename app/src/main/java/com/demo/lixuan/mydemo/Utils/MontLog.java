package com.demo.lixuan.mydemo.Utils;

import android.util.Log;

import com.demo.lixuan.mydemo.BuildConfig;

/**
 * author: wgz
 * email:wanggaozhuo@yeah.net
 * date: 2017/7/3 13:55
 */

public class MontLog {
//    private final static boolean ERROR = true;
//    private final static boolean INFO = true;
//    private final static boolean DEBUG = true;

    public static void e(String tag, String errMsg){
        if(BuildConfig.ISLOG) {
            Log.e(tag, errMsg);
            // TODO: 2017/7/4
            //重要日志，上传服务器
            //本地记录一份
        }
    }

    public static void i(String tag, String msg){
        if(BuildConfig.ISLOG) {
//            Log.i(tag, "current thread : " + Thread.currentThread().getName());
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if(BuildConfig.ISLOG) {
            Log.d(tag, msg);
            //Log.e(tag, msg);
            //Log.i(tag, msg);
        }
    }
}

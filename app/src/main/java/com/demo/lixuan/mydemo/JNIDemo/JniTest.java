package com.demo.lixuan.mydemo.JNIDemo;

/**
 * Created by Administrator on 2018/6/9.
 */

public class JniTest {
    static {
        System.loadLibrary("jni- test");
    }

    public static void main(String ags[]){
        JniTest jniTest = new JniTest();
        System.out.println(jniTest.get());
        jniTest.set("hello world");
    }

    public native String get();
    public native void set(String str);

}

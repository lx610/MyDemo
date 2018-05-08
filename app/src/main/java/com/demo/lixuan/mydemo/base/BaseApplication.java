package com.demo.lixuan.mydemo.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * 类 名: BaseApplication
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2017/12/12
 * author lixuan
 */
public class BaseApplication extends Application{
    protected static BaseApplication mApplication;
    private static int mMainThreadId;
    private static Handler mHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mMainThreadId = android.os.Process.myTid();
        mHandler = new Handler();
    }

    /**
     * 返回上下文
     *
     * @return
     */
    public static Context getContext() {
        return mApplication;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }

    public static Handler getHandler() {
        return mHandler;
    }


}

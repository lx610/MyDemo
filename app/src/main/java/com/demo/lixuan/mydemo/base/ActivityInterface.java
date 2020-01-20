package com.demo.lixuan.mydemo.base;

import android.app.Application;

/**
 * @author lihong
 * Activity生命周期管理
 * @since 2016/10/28
 */
public interface ActivityInterface {
    /**
     * Set the callback for activity lifecycle
     *
     * @param callbacks callbacks
     */
    void setActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callbacks);
}
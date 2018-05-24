package com.demo.lixuan.mydemo.baseElement.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.demo.lixuan.mydemo.Utils.UiUtils;

import static android.app.AlarmManager.ELAPSED_REALTIME_WAKEUP;

/**
 * Created by Administrator on 2018/5/24.
 */

public class LongRunningService extends Service {

    private Handler mHandler;
    boolean isCloseAll = false;

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        UiUtils.makeText("通知关闭页面状态是 ： " + isCloseAll);
                    }
                });
                if (isCloseAll){
                    Intent offLine =new Intent("com.lixuan.demo.ACT_OFFLINE");
                    sendBroadcast(offLine);
                }
                isCloseAll=true;
            }
        }).start();

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        long oneMinute = 20 * 1000;
        long triggerTime = SystemClock.elapsedRealtime() + oneMinute;
        Intent newIntent  = new Intent(this, LongRunningService.class);//重启自己，重新执行onStartCommand方法
        PendingIntent pi = PendingIntent.getActivity(this,0,newIntent,0);

        //        ELAPSED_REALTIME 让定时任务的触发时间从系统开机算起，不会唤醒CPU
//        ELAPSED_REALTIME_WAKEUP  让定时任务的触发时间从系统开机算起 ，会唤醒CPU
//         RTC  让任务时间从1970 1 .1  0时开始算起，但不会唤醒CPU
//         RTC_WAKEUP 让任务时间从1970 1 .1  0时开始算起，会唤醒CPU
        alarmManager.set(ELAPSED_REALTIME_WAKEUP,triggerTime,pi);
        return super.onStartCommand(intent, flags, startId);

    }
}

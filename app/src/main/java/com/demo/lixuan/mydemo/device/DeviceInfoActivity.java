package com.demo.lixuan.mydemo.device;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;

import com.demo.lixuan.mydemo.base.publicLayout.LinearActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/6/25.
 */

public class DeviceInfoActivity extends LinearActivity {
    @Override
    public void initView(Bundle savedInstanceState) {
        final TextView logText = (TextView) generateTextButton("",null);

        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

       mLlContainer.addView(generateTextButton("获取当前设备可用内存", new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               int memorySize = activityManager.getMemoryClass();
               logText.append("当前设备app可用最大内存" + memorySize  + "mb" + "\n");
           }
       }));

        mLlContainer.addView(generateTextButton("获取当前设备可用内存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ActivityManager.RunningAppProcessInfo> runningProcess = activityManager.getRunningAppProcesses();
                for (int i = 0; i < runningProcess.size(); i++) {
                  String proceesName =   runningProcess.get(i).processName;
                    logText.append("进程名称：" + proceesName   + "\n");
                }
                int memorySize = activityManager.getMemoryClass();
                logText.append("当前设备app可用最大内存" + memorySize  + "mb" + "\n");
            }
        }));

        mLlContainer.addView(generateTextButton("获取当前设备宽高", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                int width = wm.getDefaultDisplay().getWidth();
                int height = wm.getDefaultDisplay().getHeight();
                logText.append("设备宽 ： " + width  + "设备高" +height + "\n");
            }
        }));




        ScrollView scrollView =new ScrollView(this);
        scrollView.addView(logText);
        mLlContainer.addView(scrollView);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}

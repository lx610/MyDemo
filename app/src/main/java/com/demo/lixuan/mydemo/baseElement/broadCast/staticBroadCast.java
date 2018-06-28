package com.demo.lixuan.mydemo.baseElement.broadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.demo.lixuan.mydemo.Utils.UiUtils;

public class staticBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        UiUtils.makeText("飞行模式改变");
    }
}

package com.demo.lixuan.mydemo.baseElement.broadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.demo.lixuan.mydemo.Utils.UiUtils;
import com.demo.lixuan.mydemo.base.publicLayout.LinearActivity;

public class OrderBroadCastActivity extends LinearActivity {
    private static final String TAG = "OrderBroadCastActivity";
    private static boolean abort =false;

    @Override
    public void initView(Bundle savedInstanceState) {
        mLlContainer.addView(generateTextButton("send broad cast", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.demo.lixuan.mydemo.ordercast");
                sendOrderedBroadcast(intent,null);
            }
        }));

        mLlContainer.addView(generateTextButton("拦截广播", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abort = !abort;
            }
        }));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
//Reciver has no zero argument constructor 内部类接收器必须设置为静态才可以解决
    public static class FistReciver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            UiUtils.makeText("第一广播接收者");
            Log.d(TAG, "onReceive: 第一广播接收者");
        }
    }
    public static class SecondReciver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            UiUtils.makeText("第二广播接收者");
            Log.d(TAG, "onReceive: 第二广播接收者");
            if (abort){//拦截广播
                abortBroadcast();
            }
        }
    }
    public static class ThirdReciver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            UiUtils.makeText("第三广播接收者");
            Log.d(TAG, "onReceive: 第三广播接收者");
        }
    }
}

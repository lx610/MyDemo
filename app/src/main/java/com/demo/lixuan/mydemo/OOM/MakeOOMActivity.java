package com.demo.lixuan.mydemo.OOM;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.demo.lixuan.mydemo.base.publicLayout.LinearActivity;

import org.simple.eventbus.EventBus;

/**
 * Created by Administrator on 2018/6/29.
 */

public class MakeOOMActivity extends LinearActivity {
    static TextView textView;
    private Handler mHandler;

    @Override
    public void initView(Bundle savedInstanceState) {
        mLlContainer.addView(generateTextButton("static view catch activity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             textView =new TextView(MakeOOMActivity.this);
            }
        }));

        mLlContainer.addView(generateTextButton("thread catch activity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //无限沉睡的线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(Integer.MAX_VALUE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }));

        final TextView textView = (TextView) generateTextButton("animation round",null);
        mLlContainer.addView(textView);

        mLlContainer.addView(generateTextButton("animation catch activity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(textView,"rotation",0,360);
                objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
                objectAnimator.start();
            }
        }));

        mLlContainer.addView(generateTextButton("third framenwork catch activity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().register(this);
            }
        }));

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

            }
        };

        mLlContainer.addView(generateTextButton("handler catch activity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //解决办法1，查看NoLeakActivity，使用软引用解决
                Message message = Message.obtain();
                message.what = 1;
                mHandler.sendMessageDelayed(message,10*60*1000);

                //解决办法2：mHandler.removeCallbacksAndMessages(null);在onDestroy调用。
            }
        }));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }



}

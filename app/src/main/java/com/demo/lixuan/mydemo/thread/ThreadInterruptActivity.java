package com.demo.lixuan.mydemo.thread;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.demo.lixuan.mydemo.base.publicLayout.LinearActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2018/6/14.
 */

public class ThreadInterruptActivity extends LinearActivity {
    Executor mExecutor = Executors.newFixedThreadPool(5);
    static Handler mHandler =new Handler();
    private static TextView mLogText;

    @Override
    public void initView() {
        mLogText = (TextView) generateTextButton("",null);

        mLlContainer.addView(generateTextButton("start from FixThreadPool", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLogText.setText("");
                mExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println(Thread.currentThread().getName() + "start================================");
                            interruptedTask();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        }));
        mLlContainer.addView(generateTextButton("start from cachePool", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLogText.setText("");
                Executors.newCachedThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println(Thread.currentThread().getName() + "start==============");
                            interruptedTask();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        }));

        mLlContainer.addView(generateTextButton("start from 1 thread no safe", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLogText.setText("");
                try {
                    System.out.println(Thread.currentThread().getName() + "start============");
                    interruptedTask();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }));

        mLlContainer.addView(generateTextButton("start from 1 thread safe", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLogText.setText("");
                try {
                    System.out.println(Thread.currentThread().getName() + "start============");
                    interruptSaftTask();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }));

        ScrollView scrollView = new ScrollView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(params);
        mLogText.setLayoutParams(params);
        scrollView.addView(mLogText);
        mLlContainer.addView(scrollView);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    void interruptedTask() throws InterruptedException {

        Thread thread =new Thread(new MoonRunner(),"MoonThread");
        thread.start();
        Thread.sleep(10);
        thread.interrupt();
    }

    void interruptSaftTask() throws InterruptedException {
        SafeStopRunner runnable =  new SafeStopRunner();
        Thread thread =new Thread(runnable,"SafeThread");
        thread.start();
        Thread.sleep(10);
        runnable.cancel();
        thread.interrupt();
    }

    public static class MoonRunner implements Runnable{
        long i = 0;
        String logSring="";
        volatile String volatileSring="";

        @Override
        public void run() {

            while (!Thread.currentThread().isInterrupted()){
                for (int j = 0; j < 5; j++) {
                    i++;
                    System.out.println(Thread.currentThread().getName() + "i = ======" + i);
                    logSring=Thread.currentThread().getName() + "i = ======" + i;
                    volatileSring="volatileSring===" + Thread.currentThread().getName() + "i = ======" + i;
//                    mHandler.post(mRunnable);
                }
            }
            System.out.println(Thread.currentThread().getName() + "=============stop");
            logSring=Thread.currentThread().getName() + "===========stop";
            volatileSring="volatileSring===" + Thread.currentThread().getName() + "===========stop";
        }
        Runnable mRunnable =new Runnable() {
            @Override
            public void run() {
                mLogText.append(logSring+ "\n");
                mLogText.append(volatileSring+ "\n");

            }
        };

    }

    public static class SafeStopRunner implements Runnable{
        long i = 0;
        String logSring="";
        volatile String volatileSring="";

        private volatile boolean on =true;
        @Override
        public void run() {
            while (on){
                for (int j = 0; j < 5; j++) {
                    i++;
                    System.out.println(Thread.currentThread().getName() + "i = ======" + i);
                    logSring=Thread.currentThread().getName() + "i = ======" + i;
                    volatileSring="volatileSring===" + Thread.currentThread().getName() + "i = ======" + i;
//                    mHandler.post(mRunnable);
                }
            }
            if (!on){
                System.out.println(Thread.currentThread().getName() + "=============stop");
                logSring=Thread.currentThread().getName() + "===========stop";
                volatileSring="volatileSring===" + Thread.currentThread().getName() + "===========stop";
            }
        }
        Runnable mRunnable =new Runnable() {
            @Override
            public void run() {
                mLogText.append(logSring+ "\n");
                mLogText.append(volatileSring+ "\n");
            }
        };


        public void cancel(){
            on=false;
        }
    }

}

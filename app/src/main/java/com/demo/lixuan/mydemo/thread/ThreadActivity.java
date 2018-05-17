package com.demo.lixuan.mydemo.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类名： ThreadActivity
 * 说明：
 * <p>
 * 修改记录：
 * <p>
 * 版 权 所 有:   Copyright  2018
 * 公       司:   深圳市旅联网络科技有限公司
 * version   2.0
 * date   2018/4/13
 * author lixuan
 * Created by elk-lx on 2018/4/13.
 */

public class ThreadActivity extends BaseActivity {
    private static final String TAG = "ThreadActivity";

    private static volatile int TICKE_NO=100;

    @BindView(R.id.ll_bt_container)
    GridLayout mLlBtContainer;
    @BindView(R.id.tv_log)
    TextView mTvLog;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_thread;
    }

    @Override
    public void initView() {
//        mLlBtContainer.addView(generateTextButton("Thread", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        }));
        //最多开启两个线程，其余的任务先放入队列等待，等前两个线程处理完了，再从任务队列冲取出
        mLlBtContainer.addView(generateTextButton("FixThreadPool", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //1.corePoolSize 核心线程数
                //2.maximumPoolSize 线程池允许的最大线程数目
                //3.keepAliveTime 非核心线程闲置的超时时间
                //4.TimeUnit 时间单位
                //5.workQueue 任务队列
                ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(
                        2,6,0, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>(5));
                mTvLog.setText("");
                threadPoolExecutor.execute(generateRunable("FixThreadPool"));
                threadPoolExecutor.execute(generateRunable("FixThreadPool"));
                threadPoolExecutor.execute(generateRunable("FixThreadPool"));
                threadPoolExecutor.execute(generateRunable("FixThreadPool"));
                threadPoolExecutor.execute(generateRunable("FixThreadPool"));
                threadPoolExecutor.execute(generateRunable("FixThreadPool"));
            }
        }));
        //会竟可能的多开线程，尽快完成任务处理
        mLlBtContainer.addView(generateTextButton("CachedThread", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //1.corePoolSize 核心线程数
                //2.maximumPoolSize 线程池允许的最大线程数目
                //3.keepAliveTime 非核心线程闲置的超时时间
                //4.TimeUnit 时间单位
                //5.workQueue 任务队列
                ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(
                        0,Integer.MAX_VALUE,60, TimeUnit.MILLISECONDS,new SynchronousQueue<Runnable>());
                mTvLog.setText("");
                threadPoolExecutor.execute(generateRunable("CachedThread"));
                threadPoolExecutor.execute(generateRunable("CachedThread"));
                threadPoolExecutor.execute(generateRunable("CachedThread"));
                threadPoolExecutor.execute(generateRunable("CachedThread"));
                threadPoolExecutor.execute(generateRunable("CachedThread"));
                threadPoolExecutor.execute(generateRunable("CachedThread"));
                threadPoolExecutor.execute(generateRunable("CachedThread"));
                threadPoolExecutor.execute(generateRunable("CachedThread"));
                threadPoolExecutor.execute(generateRunable("CachedThread"));

            }
        }));
        //只有一个线程，严格按照顺序依次处理runable的
        mLlBtContainer.addView(generateTextButton("SingleThreadExecutor", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //1.corePoolSize 核心线程数
                //2.maximumPoolSize 线程池允许的最大线程数目
                //3.keepAliveTime 非核心线程闲置的超时时间
                //4.TimeUnit 时间单位
                //5.workQueue 任务队列
                ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(
                        1,1,0, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>());
                mTvLog.setText("");
                threadPoolExecutor.execute(generateRunable("SingleThreadExecutor"));
                threadPoolExecutor.execute(generateRunable("SingleThreadExecutor"));
                threadPoolExecutor.execute(generateRunable("SingleThreadExecutor"));
                threadPoolExecutor.execute(generateRunable("SingleThreadExecutor"));
                threadPoolExecutor.execute(generateRunable("SingleThreadExecutor"));
                threadPoolExecutor.execute(generateRunable("SingleThreadExecutor"));
                threadPoolExecutor.execute(generateRunable("SingleThreadExecutor"));
                threadPoolExecutor.execute(generateRunable("SingleThreadExecutor"));

            }
        }));


        //会开启4个线程，并行同时，按顺序执行任务，然后同时结束，最大线程数是无效的。
        mLlBtContainer.addView(generateTextButton("ScheduledThreadPool", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //1.corePoolSize 核心线程数
                //2.maximumPoolSize 线程池允许的最大线程数目
                //3.keepAliveTime 非核心线程闲置的超时时间
                //4.TimeUnit 时间单位
                //5.workQueue 任务队列
                ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(
                        4,Integer.MAX_VALUE,60, TimeUnit.MILLISECONDS,new DelayQueue());
                mTvLog.setText("");
                threadPoolExecutor.execute(generateRunable("ScheduledThreadPool"));
                threadPoolExecutor.execute(generateRunable("ScheduledThreadPool"));
                threadPoolExecutor.execute(generateRunable("ScheduledThreadPool"));
                threadPoolExecutor.execute(generateRunable("ScheduledThreadPool"));
                threadPoolExecutor.execute(generateRunable("ScheduledThreadPool"));
                threadPoolExecutor.execute(generateRunable("ScheduledThreadPool"));
                threadPoolExecutor.execute(generateRunable("ScheduledThreadPool"));
                threadPoolExecutor.execute(generateRunable("ScheduledThreadPool"));
                threadPoolExecutor.execute(generateRunable("ScheduledThreadPool"));

            }
        }));

        mLlBtContainer.addView(generateTextButton("handler refresh message", new View.OnClickListener() {
            private static final int MSG_TICKET_SELL = 564;

            Handler mHandler =new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what){
                        case MSG_TICKET_SELL:
                            mTvLog.append(  "ticke number ============" + msg.obj + " \n");
                            break;
                    }
                }
            };

            @Override
            public void onClick(View v) {
                TICKE_NO=100;
                Executors.newCachedThreadPool().execute(generateSellTicketRunable());
                Executors.newCachedThreadPool().execute(generateSellTicketRunable());
                Executors.newCachedThreadPool().execute(generateSellTicketRunable());
                Executors.newCachedThreadPool().execute(generateSellTicketRunable());
                Executors.newCachedThreadPool().execute(generateSellTicketRunable());
            }
//            Message msg =new Message();//Message 对象不可以被多个线程复用
            private Runnable generateSellTicketRunable() {

                return new Runnable() {
//                    Message msg =new Message();//Message 对象不可以被多个线程复用
                    @Override
                    public void run() {
//                        synchronized (ThreadActivity.class){//放在这里会一条线程走到底
                        while (true){
//                            synchronized (ThreadActivity.class){//放在这里会出现while，判断可行然后切换线程，最后ticke 被减少成负数
                            synchronized (ThreadActivity.class){
                                if (TICKE_NO>0){
                                    TICKE_NO--;
                                    StringBuffer buffer =new StringBuffer();
                                    buffer.append("" + Thread.currentThread() +  " \n");
                                    buffer.append("" + TICKE_NO +  " \n");

                                    Message msg =new Message();//Message 对象不可以被多个线程复用
                                    msg.what=MSG_TICKET_SELL;
                                    msg.obj = buffer.toString();
                                    mHandler.sendMessage(msg);
                                    try {
                                        Thread.sleep(30);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }else {
                                    break;
                                }
                            }

                        }

                    }
                };
            }
        }));
    }



    @NonNull
    private Runnable generateRunable(final String taskName) {
        return new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, taskName + "number ============================" + i);
                    Log.d(TAG, taskName +  "currentThread name============================" + Thread.currentThread().getName());
                    Log.d(TAG, taskName +  "System.currentTimeMillis============================" + System.currentTimeMillis());
                    final int num =i;
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            mTvLog.append( taskName + "number ============" + num + " \n");
                            mTvLog.append( "currentThread name=============" + Thread.currentThread().getName() + " \n");
                            mTvLog.append( taskName +  "System.currentTimeMillis===============" + System.currentTimeMillis() + " \n");
                        }
                    });

                }

            }
        };
    }


    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

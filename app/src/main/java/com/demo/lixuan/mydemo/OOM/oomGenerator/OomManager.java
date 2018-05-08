package com.demo.lixuan.mydemo.OOM.oomGenerator;

import android.content.Context;
import android.util.Log;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * 类名： OomManager
 * 说明：
 * <p>
 * 修改记录：
 * <p>
 * 版 权 所 有:   Copyright  2018
 * 公       司:   深圳市旅联网络科技有限公司
 * version   2.0
 * date   2018/4/18
 * author lixuan
 * Created by elk-lx on 2018/4/18.
 */

public class OomManager {
    private static OomManager instence;
    Context mContext;
    OnThreadListener mOnThreadListener;
    private volatile boolean stop=false;//此变量必须加上volatile

    private Thread thread1;
    public static OomManager getInstence(Context context){
        if (instence==null){
            instence=new  OomManager(context);
        }
        return instence;
    }

    private OomManager(Context context) {
        mContext = context;
    }

    public void run(){
        stop=false;
        Runnable runable = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: ===========" + Thread.currentThread());
                for (int i = 0; i < 1000; i++) {
                    if (!stop){//多线程开启的时候，依然有可能关闭线程后，其中的某个线程走到了 mOnThreadListener.refresh(i);这一步导致空指针
                        if (mOnThreadListener != null) {
                            mOnThreadListener.refresh(i);
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        };
      thread1=  new Thread(runable);
        thread1.start();

    }


    public void onDestory(){
        stop=true;
    }

    public void setOnThreadListener(OnThreadListener onThreadListener) {
        mOnThreadListener = onThreadListener;
    }

    public interface OnThreadListener{
        void refresh(int i);
    }
}

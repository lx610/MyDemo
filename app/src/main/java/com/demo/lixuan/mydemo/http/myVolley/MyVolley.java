package com.demo.lixuan.mydemo.http.myVolley;

import android.content.Context;

import java.util.concurrent.LinkedBlockingDeque;


/**
 * Created by Administrator on 2018/6/8.
 */

public class MyVolley {
    static LinkedBlockingDeque<StringRequest> mRequestLinkedBlockingDeque =new LinkedBlockingDeque(10);
    static LinkedBlockingDeque<StringRequest> mCacheLinkedBlockingDeque =new LinkedBlockingDeque(10);
    public static VollyRequestQueue newRequestQueue(Context applicationContext) {
        return newRequestQueue();
    }

    public static VollyRequestQueue newRequestQueue(){
        VollyRequestQueue queue1 =  new VollyRequestQueue();
        return queue1;
    }

    public static class VollyRequestQueue {
        public static final String GET = "GET";

        public void add(StringRequest request){
            mRequestLinkedBlockingDeque.add(request);
        }

        public void start() throws InterruptedException {
            int size = mRequestLinkedBlockingDeque.size();
            int cacheSize = mCacheLinkedBlockingDeque.size();
            for (int i = 0; i < size; i++) {
                StringRequest request = mRequestLinkedBlockingDeque.takeFirst();
               if (request.doConnectTask()){

               }else {
                   mCacheLinkedBlockingDeque.add(request);
               }
            }


            for (int i = 0; i < cacheSize; i++) {
                StringRequest request = mCacheLinkedBlockingDeque.takeFirst();
                if (request.doConnectTask()){

                }else {
                    mCacheLinkedBlockingDeque.add(request);
                }
            }
        }
    }


}

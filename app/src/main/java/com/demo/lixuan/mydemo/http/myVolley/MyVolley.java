package com.demo.lixuan.mydemo.http.myVolley;

import android.content.Context;

import java.util.concurrent.LinkedBlockingDeque;


/**
 * Created by Administrator on 2018/6/8.
 */

public class MyVolley {
    public static VollyRequestQueue newRequestQueue(Context applicationContext) {
        return newRequestQueue();
    }

    public static VollyRequestQueue newRequestQueue(){
        VollyRequestQueue queue1 =  new VollyRequestQueue();
        return queue1;
    }

    public static class VollyRequestQueue {
        public static final String GET = "GET";
        LinkedBlockingDeque<StringRequest> mLinkedBlockingDeque =new LinkedBlockingDeque(10);
        public void add(StringRequest request){
            mLinkedBlockingDeque.add(request);
        }

        public void start() throws InterruptedException {
            StringRequest request = mLinkedBlockingDeque.takeFirst();
            request.doConnectTask();
        }
    }


}

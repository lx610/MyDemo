package com.demo.lixuan.mydemo.base.http;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2018/6/19.
 */

public abstract class OkhttpMethod<T> extends HttpClient<T>{
    private static final String TAG = "OkhttpMethod";


    private static OkHttpClient.Builder mOkHttpBuild;
    private final OkHttpClient mOkHttpClient;

    public OkhttpMethod(Context context) {

        // HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //   日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        File cacheFile = new File(context.getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(200, TimeUnit.MILLISECONDS)
                .connectTimeout(200,TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .cache(cache)
                .build();

    }
//
//    public static  OkHttpClient.Builder getInstence(Context context){
//        if (mOkHttpBuild!=null){
//            synchronized (HttpClient.class){
//                if (mOkHttpBuild!=null){
//                    new OkhttpMethod(context);
//                }
//            }
//        }
//        return mOkHttpBuild;
//    }

    @Override
    public void loadUrl(String url) {
        Request.Builder requestBuilder = new Request.Builder();

        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        FormBody formBody = formBodyBuilder.build();

        requestBuilder.post(formBody)
                .url(url);




        Request requset =requestBuilder.build();
        Call call =mOkHttpClient.newCall(requset);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: ");
            }
        });
    }

    @Override
    public abstract void success(T o);
//
//    @Override
//    public void failed() {
//
//    }

    class HttpCacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            return null;
        }
    }
}

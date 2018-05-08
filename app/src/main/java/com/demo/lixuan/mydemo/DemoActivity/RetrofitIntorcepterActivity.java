package com.demo.lixuan.mydemo.DemoActivity;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 类 名: RetrofitIntorcepterActivity
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2017/12/5
 * author lixuan
 */

public class RetrofitIntorcepterActivity extends BaseActivity {

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_retrofit_intercepter);
//        initView();
//        initData();
//
//    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_retrofit_intercepter;
    }


    public void initView() {

    }

    public void initData() {
        // HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //   日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();


        File cacheFile = new File(getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .readTimeout(200, TimeUnit.MILLISECONDS)
                .connectTimeout(200,TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .cache(cache)
                .build();


        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();

//        Retrofit retrofit=new Retrofit.Builder()
//                .client(okHttpClient)
//                .baseUrl("")
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
    }

    @Override
    public void initListener() {
    }


    class HttpCacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            return null;
        }
//
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
////            if (!NetworkUtils.isConnected()) {
////                request = request.newBuilder()
////                        .cacheControl(CacheControl.FORCE_CACHE)
////                        .build();
////                LogUtils.d("Okhttp", "no network");
////            }
////
////            Response originalResponse = chain.proceed(request);
////            if (NetworkUtils.isConnected()) {
////                //有网的时候读接口上的@Headers里的配置，可以在这里进行统一的设置
////                String cacheControl = request.cacheControl().toString();
////                return originalResponse.newBuilder()
////                        .header("Cache-Control", cacheControl)
////                        .removeHeader("Pragma")
////                        .build();
////            } else {
////                return originalResponse.newBuilder()
////                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
////                        .removeHeader("Pragma")
////                        .build();
////            }
//        }
    }

}

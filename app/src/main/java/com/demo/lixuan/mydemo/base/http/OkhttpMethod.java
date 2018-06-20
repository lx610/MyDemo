package com.demo.lixuan.mydemo.base.http;

import android.content.Context;
import android.util.Log;

import com.demo.lixuan.mydemo.http.okhttp.CharactorHandler;
import com.demo.lixuan.mydemo.http.okhttp.ZipHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;

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
                .readTimeout(2000, TimeUnit.MILLISECONDS)
                .connectTimeout(2000,TimeUnit.MILLISECONDS)
//                .addInterceptor(interceptor)
                .addNetworkInterceptor(new HttpCacheInterceptor())
//                .cache(cache)
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
    public void loadUrl(final String url) {
        Request.Builder requestBuilder = new Request.Builder();

        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        FormBody formBody = formBodyBuilder.build();

        requestBuilder.post(formBody)
                        .url(url);
        Request requset =requestBuilder.build();

            Call call  =mOkHttpClient.newCall(requset);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d(TAG, "onResponse: " + response.body());

                    Log.d(TAG, "onResponse: " + response.body());
                    Log.d(TAG, "onResponse: " + response.body());
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
            Request request = chain.request();
            Response originalResponse = chain.proceed(request);
            Log.d(TAG, "intercept: .headers().toString()" + originalResponse.headers().toString());
            Log.d(TAG, "intercept: message().toString()" + originalResponse.message().toString());
            Log.d(TAG, "intercept: request().body()" +   originalResponse.request().body().toString());
            Log.d(TAG, "intercept: request().body()" +   originalResponse.request().headers().toString());

            //读取服务器返回的结果
            ResponseBody responseBody = originalResponse.body();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            //获取content的压缩类型
            String encoding = originalResponse
                    .headers()
                    .get("Content-Encoding");

            Buffer clone = buffer.clone();
            String bodyString;

            //解析response content
            if (encoding != null && encoding.equalsIgnoreCase("gzip")) {//content使用gzip压缩
                bodyString = ZipHelper.decompressForGzip(clone.readByteArray());//解压
            } else if (encoding != null && encoding.equalsIgnoreCase("zlib")) {//content使用zlib压缩
                bodyString = ZipHelper.decompressToStringForZlib(clone.readByteArray());//解压
            } else {//content没有被压缩
                Charset charset = Charset.forName("UTF-8");
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(charset);
                }
                bodyString = clone.readString(charset);
            }


//            Timber.tag("Result").w(CharactorHandler.jsonFormat(bodyString));
            Log.d(TAG, "intercept: " + CharactorHandler.jsonFormat(bodyString));

            return originalResponse;//不能return null 会报 network interceptor must call proceed() exactly once
        }
    }
}

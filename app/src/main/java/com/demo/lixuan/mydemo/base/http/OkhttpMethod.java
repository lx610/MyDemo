package com.demo.lixuan.mydemo.base.http;

import android.content.Context;
import android.util.Log;

import com.demo.lixuan.mydemo.http.okhttp.CharactorHandler;
import com.demo.lixuan.mydemo.http.okhttp.ZipHelper;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
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

public abstract class OkhttpMethod<T extends Object> extends HttpClient<T>{
    private static final String TAG = "OkhttpMethod";


    private static OkHttpClient.Builder mOkHttpBuild;
    private final OkHttpClient mOkHttpClient;
    private final Context mContext;

    public OkhttpMethod(Context context) {

        // HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //   日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        File cacheFile = new File(context.getCacheDir(), "cache");
        mContext = context;
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
                    Gson gson = new Gson();
                    String JsonString = response.body().string();
                    Log.d(TAG, "onResponse: " + JsonString);
                    Class<T> persistentClass;
                    persistentClass=(Class<T>)getSuperClassGenricType(getClass(), 0);
                    Log.d(TAG, "onResponse:c class " + persistentClass.getSimpleName());
                    persistentClass=(Class<T>)getRawType(getClass());
                    Log.d(TAG, "onResponse:c class " + persistentClass.getSimpleName());
                    T userBasicInfo = null;
//                    T userBasicInfo = (T) gson.fromJson(JsonString, persistentClass.getClass());
                    success(userBasicInfo);
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

    @SuppressWarnings("unchecked")
    public static Class<?> getSuperClassGenricType(final Class clazz, final int index) {

        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        //返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class) params[index];
    }

    static Class<?> getRawType(Type type) {
        if (type == null) throw new NullPointerException("type == null");

        if (type instanceof Class<?>) {
            // Type is a normal class.
            return (Class<?>) type;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;

            // I'm not exactly sure why getRawType() returns Type instead of Class. Neal isn't either but
            // suspects some pathological case related to nested classes exists.
            Type rawType = parameterizedType.getRawType();
            if (!(rawType instanceof Class)) throw new IllegalArgumentException();
            return (Class<?>) rawType;
        }
        if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            return Array.newInstance(getRawType(componentType), 0).getClass();
        }
        if (type instanceof TypeVariable) {
            // We could use the variable's bounds, but that won't work if there are multiple. Having a raw
            // type that's more general than necessary is okay.
            return Object.class;
        }
        if (type instanceof WildcardType) {
            return getRawType(((WildcardType) type).getUpperBounds()[0]);
        }

        throw new IllegalArgumentException("Expected a Class, ParameterizedType, or "
                + "GenericArrayType, but <" + type + "> is of type " + type.getClass().getName());
    }
}

package com.demo.lixuan.mydemo.base.http;

/**
 * Created by Administrator on 2018/6/19.
 */

public abstract class HttpClient<T> {

    abstract void loadUrl(String url);
    abstract void success(T t);
    abstract void failed();
}

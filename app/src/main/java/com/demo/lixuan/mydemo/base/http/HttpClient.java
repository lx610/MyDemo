package com.demo.lixuan.mydemo.base.http;

/**
 * Created by Administrator on 2018/6/19.
 */

public abstract class HttpClient<T> {

    public abstract void loadUrl(String url);
    public abstract void success(T t);
    protected abstract void failed();
}

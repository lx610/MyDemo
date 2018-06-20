package com.demo.lixuan.mydemo.http.okhttp;

import com.demo.lixuan.mydemo.base.http.OkhttpMethod;
import com.demo.lixuan.mydemo.base.publicLayout.LinearActivity;

/**
 * Created by Administrator on 2018/6/19.
 */

public class OkhttpActivity extends LinearActivity{
    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        String url = "http://192.168.0.232:8081/elk-tourist-develop/home/list_1_0_0";
        loadDataFromHttp(url, new OkhttpMethod<HomeBean>(this) {
            @Override
            public void success(HomeBean o) {

            }

            @Override
            protected void failed() {

            }

        });
    }

    @Override
    public void initListener() {

    }
}

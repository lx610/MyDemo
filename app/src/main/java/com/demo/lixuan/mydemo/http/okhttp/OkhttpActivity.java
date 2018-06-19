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
        String url = "";
        loadDataFromHttp(url, new OkhttpMethod(this) {
            @Override
            public void success(Object o) {

            }

            @Override
            public void failed() {

            }
        });
    }

    @Override
    public void initListener() {

    }
}

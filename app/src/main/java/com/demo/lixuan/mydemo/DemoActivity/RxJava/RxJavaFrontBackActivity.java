package com.demo.lixuan.mydemo.DemoActivity.RxJava;

import android.os.Bundle;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;


/**
 * 类 名: RxJavaFrontPageActivity
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2018/2/2
 * author lixuan
 */

public class RxJavaFrontBackActivity extends BaseActivity {



    @Override
    public int getLayoutResId() {
        return R.layout.activity_rx_back;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

     public <R> Observable<Response<Call<R>>> adapt(Call<R> call) {
        Observable<Response<Call<R>>> observable = Observable.create(new CallOnSubscribe<>(call));
//        if (scheduler != null) {
//            return observable.subscribeOn(scheduler);
//        }
        return observable;
    }

    static final class CallOnSubscribe<T> implements Observable.OnSubscribe<Response<T>> {

        public CallOnSubscribe(T call) {

        }

        @Override
        public void call(Subscriber<? super Response<T>> subscriber) {

        }
    }

    private static class Result<R> {

        public Result(Response<R> response, Object o) {

        }

        public static <T> Result<T> response(Response<T> response) {
            if (response == null) throw new NullPointerException("response == null");
            return new Result<>(response, null);
        }

        public static <T> Result<T> error(Throwable error) {
            if (error == null) throw new NullPointerException("error == null");
            return new Result<>(null, error);
        }
    }

    private class Response<R> {
    }

    private class Call<R> {
    }



    public class dataBean{
        String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}

package com.demo.lixuan.mydemo.DemoActivity.RxJava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;


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

public class RxJavaFrontPageActivity extends BaseActivity {
    private static final String TAG = "RxJavaFrontPageActivity";

    @BindView(R.id.tv_recivie)
    TextView mTvRecivie;
    @BindView(R.id.tv_send)
    TextView mTvSend;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_rx_front;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {


    }

    @Override
    public void initListener() {
        mTvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<Result<dataBean>> observable =adapt(new Call<dataBean>());
                observable.subscribe(new Subscriber<Result<dataBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<dataBean> dataBeanResult) {

                    }
                });

            }
        });
    }

    public void doOn(){
        Log.d(TAG, "doOn: " + "继续行动");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

   public <R> Observable<Result<R>> adapt(Call<R> call) {
       Observable<Result<R>> observable = Observable.create(new CallOnSubscribe<>(call)) //
               .map(new Func1<Response<R>, Result<R>>() {
                   @Override public Result<R> call(Response<R> response) {
                       return Result.response(response);
                   }
               }).onErrorReturn(new Func1<Throwable, Result<R>>() {
                   @Override public Result<R> call(Throwable throwable) {
                       return Result.error(throwable);
                   }
               });
//       if (scheduler != null) {
//           return observable.subscribeOn(scheduler);
//       }
       return observable;
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

    static final class CallOnSubscribe<T> implements Observable.OnSubscribe<Response<T>> {
        private final Call<T> originalCall;

        CallOnSubscribe(Call<T> originalCall) {
            this.originalCall = originalCall;
        }

        @Override public void call(final Subscriber<? super Response<T>> subscriber) {
            // Since Call is a one-shot type, clone it for each new subscriber.

        }
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

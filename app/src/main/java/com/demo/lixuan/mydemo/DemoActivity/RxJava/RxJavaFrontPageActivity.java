package com.demo.lixuan.mydemo.DemoActivity.RxJava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
//import io.reactivex.Observer;
//import io.reactivex.annotations.NonNull;
//import io.reactivex.disposables.Disposable;

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
    public void initView() {

    }

    @Override
    public void initData() {


    }

    @Override
    public void initListener() {
        mTvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
}

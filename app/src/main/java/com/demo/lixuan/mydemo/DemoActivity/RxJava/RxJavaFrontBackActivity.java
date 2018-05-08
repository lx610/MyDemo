package com.demo.lixuan.mydemo.DemoActivity.RxJava;

import android.os.Bundle;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;

import butterknife.ButterKnife;

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
    public void initView() {

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
}

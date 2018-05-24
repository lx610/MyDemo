package com.demo.lixuan.mydemo.baseElement.broadCast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/24.
 */

public class FauceToCloseActivity extends BaseActivity {

    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_only_linearlayout;
    }

    @Override
    public void initView() {
        mLlContainer.addView(generateTextButton("close all activity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent offLine =new Intent("com.lixuan.demo.ACT_OFFLINE");
                sendBroadcast(offLine);
            }
        }));
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

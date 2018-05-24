package com.demo.lixuan.mydemo.baseElement.service;

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

public class ServiceActivity extends BaseActivity {
    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_only_linearlayout;
    }

    @Override
    public void initView() {
        mLlContainer.addView(generateTextButton("front service", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(ServiceActivity.this,FrontService.class));
            }
        }));

        mLlContainer.addView(generateTextButton("force to close act", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(ServiceActivity.this,LongRunningService.class));
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

package com.demo.lixuan.mydemo.java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;
import com.demo.lixuan.mydemo.java.Proxy.ProxyActivity;
import com.demo.lixuan.mydemo.java.designModel.designModelActivity;
import com.demo.lixuan.mydemo.java.staticTest.OutClassAndInerClassActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/25.
 */

public class JavaDemoActivity extends BaseActivity {
    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_only_linearlayout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mLlContainer.addView(generateTextButton("jump to proxy", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JavaDemoActivity.this, ProxyActivity.class));
            }
        }));

        mLlContainer.addView(generateTextButton("设计模式", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityFromAct( designModelActivity.class);
            }
        }));

        mLlContainer.addView(generateTextButton("jump to out and iner class", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JavaDemoActivity.this, OutClassAndInerClassActivity.class));
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

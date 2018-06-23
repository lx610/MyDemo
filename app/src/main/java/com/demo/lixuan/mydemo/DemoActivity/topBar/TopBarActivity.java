package com.demo.lixuan.mydemo.DemoActivity.topBar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类 名: TopBarActivity
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2018/1/25
 * author lixuan
 */

public class TopBarActivity extends BaseActivity {
    @BindView(R.id.ll_bt_container)
    LinearLayout mLlBtContainer;
    @BindView(R.id.activity_main)
    FrameLayout mActivityMain;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mLlBtContainer.addView(generateBtToStartActivity(new NestLinearLayoutActivity()));
        mLlBtContainer.addView(generateBtToStartActivity(new HideTopBarActvitiy()));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }


    private TextView generateBtToStartActivity(final Activity activity) {
        TextView button = new TextView(this);
        button.setText(activity.getClass().getSimpleName());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TopBarActivity.this, activity.getClass()));
            }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400, 160);
        button.setBackgroundColor(Color.YELLOW);
        params.setMargins(10, 10, 10, 10);
        button.setLayoutParams(params);
        button.setPadding(10, 10, 10, 10);
        return button;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

package com.demo.lixuan.mydemo.widgt.clock;

import android.os.Bundle;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;

/**
 * Created by Administrator on 2018/6/4.
 */

public class ClockActivity extends BaseActivity {
    @Override
    public int getLayoutResId() {
        return R.layout.activity_clock_view;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
//      RoundClockView clockView =   new RoundClockView(this);
//        LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(500,400);
//        clockView.setLayoutParams(params);
//        mLlContainer.addView(clockView);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}

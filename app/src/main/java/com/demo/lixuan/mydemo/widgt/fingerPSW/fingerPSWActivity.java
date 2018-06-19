package com.demo.lixuan.mydemo.widgt.fingerPSW;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.demo.lixuan.mydemo.base.publicLayout.LinearActivity;

/**
 * Created by Administrator on 2018/6/14.
 */

public class fingerPSWActivity extends LinearActivity {
    @Override
    public void initView() {
//        DotView dotView=new DotView  (this,1);
//        dotView.setMinimumWidth(80);
//        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(80,80);
//        dotView.setLayoutParams(params1);
//
//        mLlContainer.addView(dotView);

        LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        final NineDotView nineDotView =new NineDotView(this);
        nineDotView.setLayoutParams(params);
        nineDotView.setBackgroundColor(Color.BLACK);
        nineDotView.setAlpha(0.7f);
        nineDotView.setPadding(30,30,0,0);

        mLlContainer.addView(generateTextButton("验证密码", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nineDotView.setPswStatus(NineDotView.STATUS_CHECK_PSW);
            }
        }));
        mLlContainer.addView(generateTextButton("设置密码", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nineDotView.setPswStatus(NineDotView.STATUS_SET_PSW);
            }
        }));

        mLlContainer.addView(nineDotView);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}

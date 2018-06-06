package com.demo.lixuan.mydemo.java.designModel;

import android.view.View;

import com.demo.lixuan.mydemo.base.publicLayout.LinearActivity;
import com.demo.lixuan.mydemo.java.designModel.Stragety.StragetyModelAcitivity;

/**
 * Created by Administrator on 2018/6/4.
 */

public class designModelActivity extends LinearActivity {
    @Override
    public void initView() {
        mLlContainer.addView(generateTextButton("策略模式", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityFromAct(StragetyModelAcitivity.class);
            }
        }));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}

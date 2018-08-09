package com.demo.lixuan.mydemo.java.designModel;

import android.os.Bundle;
import android.view.View;

import com.demo.lixuan.mydemo.base.publicLayout.LinearActivity;
import com.demo.lixuan.mydemo.java.designModel.Stragety.StragetyModelAcitivity;
import com.demo.lixuan.mydemo.java.designModel.shareObject.FlyWigthDesignModelActivity;

/**
 * Created by Administrator on 2018/6/12.
 */

public class designModelActivity extends LinearActivity{
    @Override
    public void initView(Bundle savedInstanceState) {
        mLlContainer.addView(generateTextButton("策略模式", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityFromAct(StragetyModelAcitivity.class);
            }
        }));

        mLlContainer.addView(generateTextButton("享元模式", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityFromAct(FlyWigthDesignModelActivity.class);
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

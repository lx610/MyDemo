package com.demo.lixuan.mydemo.thread;

import android.view.View;

import com.demo.lixuan.mydemo.base.publicLayout.LinearActivity;

/**
 * 类名： ThreadActivity
 * 说明：
 * <p>
 * 修改记录：
 * <p>
 * 版 权 所 有:   Copyright  2018
 * 公       司:   深圳市旅联网络科技有限公司
 * version   2.0
 * date   2018/4/13
 * author lixuan
 * Created by elk-lx on 2018/4/13.
 */

public class ThreadActivity extends LinearActivity {


    @Override
    public void initView() {
        mLlContainer.addView(generateTextButton("Threadpool", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityFromAct(ThreadActivity.class);
            }
        }));

        mLlContainer.addView(generateTextButton("Thread interupt", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityFromAct(ThreadInterruptActivity.class);
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

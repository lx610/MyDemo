package com.demo.lixuan.mydemo.device;

import android.content.Intent;
import android.view.View;

import com.demo.lixuan.mydemo.LinearActivity;
import com.demo.lixuan.mydemo.device.camer.CamerActivity;

/**
 * Created by Administrator on 2018/5/25.
 */

public class DeviceActivity extends LinearActivity {
    @Override
    public void initView() {
        mLlContainer.addView(generateTextButton("camera activity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeviceActivity.this, CamerActivity.class));
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

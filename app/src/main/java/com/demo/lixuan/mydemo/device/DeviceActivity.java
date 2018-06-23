package com.demo.lixuan.mydemo.device;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.demo.lixuan.mydemo.base.publicLayout.LinearActivity;
import com.demo.lixuan.mydemo.device.camer.CamerActivity;
import com.demo.lixuan.mydemo.device.camer.LauchDeviceActivity;

/**
 * Created by Administrator on 2018/5/25.
 */

public class DeviceActivity extends LinearActivity {
    @Override
    public void initView(Bundle savedInstanceState) {
        mLlContainer.addView(generateTextButton("camera activity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeviceActivity.this, CamerActivity.class));
            }
        }));

        mLlContainer.addView(generateTextButton("lauch system device", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeviceActivity.this, LauchDeviceActivity.class));
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

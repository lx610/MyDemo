package com.demo.lixuan.mydemo.device.camer;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.demo.lixuan.mydemo.LinearActivity;

/**
 * Created by Administrator on 2018/5/25.
 */

public class LauchDeviceActivity extends LinearActivity {

    private static final int INTENT_FOR_CAMERA = 690;
    @Override
    public void initView() {
        mLlContainer.addView(generateTextButton("lauch camera", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,INTENT_FOR_CAMERA);

            }
        }));

        mLlContainer.addView(generateTextButton("lauch picture", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode!= RESULT_OK){
            return;
        }
        switch (requestCode){
            case INTENT_FOR_CAMERA:
                Bundle bundle = data.getExtras();
                break;
        }
    }
}

package com.demo.lixuan.mydemo.widgt.pupwindow.keybordWindow;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;

/**
 * className: KeyboardWindowActivity
 * description:模仿键盘弹窗
 * author：lix
 * email：lixuan_1@163.com
 * date: 2020/6/3 17:36
 */
public class KeyboardWindowActivity extends BaseActivity {

    @Override
    public int getLayoutResId() {

        return R.layout.activity_keyborad_window;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        VoiceInputWindow voiceInputWindow = new VoiceInputWindow(this);
        View vioiceButton = findViewById(R.id.tv_voice);
        EditText etInput = findViewById(R.id.et_info_input);
        voiceInputWindow.bingEditText(etInput);
        vioiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voiceInputWindow.showAtBottom();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}

package com.demo.lixuan.mydemo.frame.hotfix;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.demo.lixuan.mydemo.MainActivity;
import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;
import com.demo.lixuan.mydemo.frame.hotfix.test.BugTest;
import com.unicom.xiaowo.login.UniAuthHelper;

import java.io.File;

/**
 * className: HotFixTestClass
 * description:java类作用描述
 * author：lix
 * email：lixuan_1@163.com
 * date: 2020/6/19 14:40
 */
public class HotFixTestClass extends BaseActivity {

    private static final String DEX_DIR = "patch";
    private Button btnRun;
    private Button btnRepair;

    @Override
    public int getLayoutResId() {

        return R.layout.activity_hot_fix;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        btnRun = (Button) findViewById(R.id.btn_run);
        btnRepair = (Button) findViewById(R.id.btn_repair);

        init();
        setOnClickListener();
    }

    private void init() {
        // 补丁存放目录为 /storage/emulated/0/Android/data/com.lxbnjupt.hotfixdemo/files/patch
        File patchDir = new File(this.getExternalFilesDir(null), DEX_DIR);
        if (!patchDir.exists()) {
            patchDir.mkdirs();
        }
    }

    private void setOnClickListener() {
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new BugTest().getBug();
            }
        });

        btnRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new HotFixUtils().doHotFix(HotFixTestClass.this);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
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

package com.demo.lixuan.mydemo.process;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.Utils.SystemUtil;
import com.demo.lixuan.mydemo.Utils.UiUtils;
import com.demo.lixuan.mydemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类名： FirstActivity
 * 说明：
 * <p>
 * 修改记录：
 * <p>
 * 版 权 所 有:   Copyright  2018
 * 公       司:   深圳市旅联网络科技有限公司
 * version   2.0
 * date   2018/5/15
 * author lixuan
 * Created by elk-lx on 2018/5/15.
 */

public class SecondActivity extends BaseActivity {
    private static final String TAG = "SecondActivity";
    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_only_linearlayout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mLlContainer.addView( generateTextButton("SecondActivity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }));

        mLlContainer.addView( generateTextButton("show ThirdActivity DefaultNo", new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                UiUtils.makeText(ThirdActivity.defaultNo + "");
            }
        }));

        mLlContainer.addView( generateTextButton("add ThirdActivity DefaultNo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThirdActivity.defaultNo++;
                UiUtils.makeText(ThirdActivity.defaultNo + "");
            }
        }));


    }

    @Override
    public void initData() {
        String processName = SystemUtil.getProcessName(getApplication());
        Log.d(TAG, "processName: " + processName);

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

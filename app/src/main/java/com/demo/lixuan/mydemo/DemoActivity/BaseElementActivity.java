package com.demo.lixuan.mydemo.DemoActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;
import com.demo.lixuan.mydemo.baseElement.ContentProvideActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类名： BaseElementActivity
 * 说明：
 * <p>
 * 修改记录：
 * <p>
 * 版 权 所 有:   Copyright  2018
 * 公       司:   深圳市旅联网络科技有限公司
 * version   2.0
 * date   2018/4/20
 * author lixuan
 * Created by elk-lx on 2018/4/20.
 */

public class BaseElementActivity extends BaseActivity {
    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_only_linearlayout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mLlContainer.addView(generateTextButton("ContentProvide", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BaseElementActivity.this, ContentProvideActivity.class));
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

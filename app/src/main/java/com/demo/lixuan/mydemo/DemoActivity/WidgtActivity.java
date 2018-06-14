package com.demo.lixuan.mydemo.DemoActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;
import com.demo.lixuan.mydemo.widgt.ImageView.ImageActivity;
import com.demo.lixuan.mydemo.widgt.button.ButtonActivity;
import com.demo.lixuan.mydemo.widgt.calendar.CalendarActivity;
import com.demo.lixuan.mydemo.widgt.clock.ClockActivity;
import com.demo.lixuan.mydemo.widgt.cludeCircleImage.GiveStarsToFoucusActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类名： WidgtActivity
 * 说明：
 * <p>
 * 修改记录：
 * <p>
 * 版 权 所 有:   Copyright  2018
 * 公       司:   深圳市旅联网络科技有限公司
 * version   2.0
 * date   2018/4/19
 * author lixuan
 * Created by elk-lx on 2018/4/19.
 */

public class WidgtActivity extends BaseActivity {

    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_only_linearlayout;
    }

    @Override
    public void initView() {
        mLlContainer.addView(generateTextButton("widgtButton", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WidgtActivity.this,ButtonActivity.class));
            }
        }));

        mLlContainer.addView(generateTextButton("calendar", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WidgtActivity.this,CalendarActivity.class));
            }
        }));
        mLlContainer.addView(generateTextButton("imageView", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WidgtActivity.this,ImageActivity.class));
            }
        }));
        mLlContainer.addView(generateTextButton("ClockActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WidgtActivity.this,ClockActivity.class));
            }
        }));
        mLlContainer.addView(generateTextButton("Radom clude name", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WidgtActivity.this,GiveStarsToFoucusActivity.class));
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

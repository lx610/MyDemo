package com.demo.lixuan.mydemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.lixuan.mydemo.DemoActivity.Base64Activity;
import com.demo.lixuan.mydemo.DemoActivity.BaseElementActivity;
import com.demo.lixuan.mydemo.DemoActivity.HeadZoomScollerActvity;
import com.demo.lixuan.mydemo.DemoActivity.KotLinActivity;
import com.demo.lixuan.mydemo.DemoActivity.OOMExampleACitivity;
import com.demo.lixuan.mydemo.DemoActivity.ProcessActivity;
import com.demo.lixuan.mydemo.DemoActivity.RecyclerListDemoActivity;
import com.demo.lixuan.mydemo.DemoActivity.RetrofitIntorcepterActivity;
import com.demo.lixuan.mydemo.DemoActivity.StringAndTextViewActivity;
import com.demo.lixuan.mydemo.DemoActivity.ViewAndImageBannerBarActivity;
import com.demo.lixuan.mydemo.DemoActivity.WidgtActivity;
import com.demo.lixuan.mydemo.DemoActivity.topBar.TopBarActivity;
import com.demo.lixuan.mydemo.RecyclerView.ExpendListActivity;
import com.demo.lixuan.mydemo.animation.AnimationActivity;
import com.demo.lixuan.mydemo.annotation.AnnotationTestActivity;
import com.demo.lixuan.mydemo.base.BaseActivity;
import com.demo.lixuan.mydemo.baseElement.broadCast.FauceToCloseActivity;
import com.demo.lixuan.mydemo.baseElement.service.ServiceActivity;
import com.demo.lixuan.mydemo.device.DeviceActivity;
import com.demo.lixuan.mydemo.gradleSetting.GradleSetMutlApkActivity;
import com.demo.lixuan.mydemo.http.HttpActivity;
import com.demo.lixuan.mydemo.java.JavaDemoActivity;
import com.demo.lixuan.mydemo.thread.ThreadActivity;
import com.demo.lixuan.mydemo.widgt.button.ButtonActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class  MainActivity extends BaseActivity {


    @BindView(R.id.ll_bt_container)
    LinearLayout mLlBtContainer;
    @BindView(R.id.activity_main)
    FrameLayout mActivityMain;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
//        mLlBtContainer= (LinearLayout) findViewById(R.id.ll_bt_container);
        mLlBtContainer.addView(generateBtToStartActivity(new RetrofitIntorcepterActivity()));
        mLlBtContainer.addView(generateBtToStartActivity(new RecyclerListDemoActivity()));
        mLlBtContainer.addView(generateBtToStartActivity(new ButtonActivity()));
        mLlBtContainer.addView(generateBtToStartActivity(new StringAndTextViewActivity()));
        mLlBtContainer.addView(generateBtToStartActivity(new AnimationActivity()));
        mLlBtContainer.addView(generateBtToStartActivity(new Base64Activity()));
        mLlBtContainer.addView(generateBtToStartActivity(new HeadZoomScollerActvity()));
        mLlBtContainer.addView(generateBtToStartActivity(new ViewAndImageBannerBarActivity()));
        mLlBtContainer.addView(generateBtToStartActivity(new TopBarActivity()));
        mLlBtContainer.addView(generateBtToStartActivity(new ThreadActivity()));
        mLlBtContainer.addView(generateBtToStartActivity(new OOMExampleACitivity()));
        mLlBtContainer.addView(generateBtToStartActivity(new WidgtActivity()));
        mLlBtContainer.addView(generateBtToStartActivity(new BaseElementActivity()));
        mLlBtContainer.addView(generateBtToStartActivity(new AnnotationTestActivity()));
        mLlBtContainer.addView(generateBtToStartActivity(new ProcessActivity()));
        mLlBtContainer.addView(generateBtToStartActivity(new KotLinActivity()));
        mLlBtContainer.addView(generateBtToStartActivity(new GradleSetMutlApkActivity()));
        mLlBtContainer.addView(generateBtToStartActivity(new ServiceActivity()));
        mLlBtContainer.addView(generateBtToStartActivity(new FauceToCloseActivity()));
        mLlBtContainer.addView(generateBtToStartActivity(new JavaDemoActivity()));
        mLlBtContainer.addView(generateBtToStartActivity(new DeviceActivity()));
        mLlBtContainer.addView(generateBtToStartActivity(new HttpActivity()));



    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    public TextView generateBtToStartActivity(final Activity activity) {
        TextView button = new TextView(this);
        button.setText(activity.getClass().getSimpleName());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, activity.getClass()));
            }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400, 160);
        button.setBackgroundColor(Color.YELLOW);
        params.setMargins(10, 10, 10, 10);
        button.setLayoutParams(params);
        button.setPadding(10, 10, 10, 10);
        return button;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

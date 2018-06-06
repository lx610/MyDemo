package com.demo.lixuan.mydemo.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.Utils.UiUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类 名: BaseActivity
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2017/12/12
 * author lixuan
 */

public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder mUnbinder;
    private ForeOffLine mReciever;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        //绑定到butterknife
        mUnbinder = ButterKnife.bind(this);
        initView();
        initData();
        initListener();
        ActivityController.addActivity(this);
    }

    public abstract int getLayoutResId();
    public abstract void initView();
    public abstract void initData();
    public abstract void initListener();


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter =new IntentFilter();
        intentFilter.addAction("com.lixuan.demo.ACT_OFFLINE");
        mReciever = new ForeOffLine();
        registerReceiver(mReciever,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mReciever!=null){
            unregisterReceiver(mReciever);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        ActivityController.removeActivity(this);
    }

    public View generateTextButton(String buttonName, View.OnClickListener oncliclickLiener) {
        TextView textBt=new TextView(this);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,10,10);
        textBt.setLayoutParams(params);
        textBt.setText(buttonName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textBt.setBackground(getDrawable(R.color.pink));
        }
        textBt.setPadding(10,10,10,10);
        textBt.setOnClickListener(oncliclickLiener);
        return textBt;
    }

    public void startActivityFromAct(Class activityClass){
        startActivity(new Intent(BaseActivity.this,activityClass));
    }


    class ForeOffLine extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            UiUtils.makeText("销毁所有活动");
        }
    }
}

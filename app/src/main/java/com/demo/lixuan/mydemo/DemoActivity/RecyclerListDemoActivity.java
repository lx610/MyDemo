package com.demo.lixuan.mydemo.DemoActivity;

import android.os.Bundle;
import android.view.View;

import com.demo.lixuan.mydemo.RecyclerView.ExpendListActivity;
import com.demo.lixuan.mydemo.RecyclerView.LevelRecylerListActivity;
import com.demo.lixuan.mydemo.base.publicLayout.LinearActivity;

/**
 * className:
 * description:
 * author：lixuan
 * date: 2020/3/9
 */
public class RecyclerListDemoActivity extends LinearActivity {
    @Override
    public void initView(Bundle savedInstanceState) {
        mLlContainer.addView(generateBtToStartActivity(new ExpendListActivity()));
        mLlContainer.addView(generateBtToStartActivity(new LevelRecylerListActivity()));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}

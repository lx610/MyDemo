package com.demo.lixuan.mydemo.RecyclerView;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * className:
 * description:
 * author：lixuan
 * date: 2020/3/9
 */
public class LevelRecylerListActivity extends BaseActivity {
    @BindView(R.id.list)
    RecyclerView mList;
    @BindView(R.id.root)
    FrameLayout mRoot;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_only_recycleview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

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

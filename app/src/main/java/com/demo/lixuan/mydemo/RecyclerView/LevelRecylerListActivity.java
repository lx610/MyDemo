package com.demo.lixuan.mydemo.RecyclerView;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper.SubListFragment;
import com.demo.lixuan.mydemo.base.BaseActivity;
import com.demo.lixuan.mydemo.base.publicLayout.LinearActivity;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * className:
 * description:
 * author：lixuan
 * date: 2020/3/9
 */
public class LevelRecylerListActivity extends LinearActivity {


    @Override
    public void initView(Bundle savedInstanceState) {
        FragmentTransaction ts = getSupportFragmentManager().beginTransaction();
        ts.replace(R.id.ll_container,new SubListFragment());
        ts.commit();
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

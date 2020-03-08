package com.demo.lixuan.mydemo.base.publicLayout;

import android.os.Bundle;
import androidx.fragment.app.Fragment;


import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;

import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/30.
 */

abstract public class BaseViewPageFragmentActivity extends BaseActivity {
    @BindView(R.id.viewpage)
    ViewPager mViewpage;
    private BaseFragmentPageAdapter mAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_viewpage_fragment;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mAdapter = new BaseFragmentPageAdapter(getSupportFragmentManager());
        mViewpage.setAdapter(mAdapter);
        setFragmentList();
    }


    public void setFragmentList(){
        mAdapter.setFragmentList(getFragmentList());
    }

    protected abstract List<Fragment> getFragmentList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

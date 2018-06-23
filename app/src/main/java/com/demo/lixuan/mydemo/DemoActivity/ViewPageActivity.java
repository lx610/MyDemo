package com.demo.lixuan.mydemo.DemoActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;
import com.demo.lixuan.mydemo.base.BaseViewpageFactory;
import com.flyco.tablayout.SlidingTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类 名: ViewPageActivity
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2018/1/16
 * author lixuan
 */

public class ViewPageActivity extends BaseActivity {
    @BindView(R.id.tab)
    SlidingTabLayout mTab;
    @BindView(R.id.viewpage)
    ViewPager mViewpage;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_view_page;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mViewpage.setAdapter(new RecyclerPageAdapter(getSupportFragmentManager()));
        mTab.setViewPager(mViewpage);

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

    private class RecyclerPageAdapter extends FragmentStatePagerAdapter {
        BaseViewpageFactory mFactory;

        public RecyclerPageAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public int getCount() {
            return mFactory.getPageCount();
        }

        @Override
        public Fragment getItem(int position) {
            return mFactory.getFragment(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFactory.getPageTitle(position);
        }
    }
}

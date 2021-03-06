package com.demo.lixuan.mydemo.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseFragment;

/**
 * 类 名: ReyclerPageFragment
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2018/1/16
 * author lixuan
 */


public class ReyclerPageFragment extends BaseFragment {
    public static final String KEY_PAGE = "key_page";

    public ReyclerPageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutResour() {
        return R.layout.fragment_recycler_page;
    }

    @Override
    protected void initListener(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void initData(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }
}

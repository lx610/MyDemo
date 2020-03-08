package com.demo.lixuan.mydemo.java.staticTest;

import androidx.fragment.app.Fragment;

import com.demo.lixuan.mydemo.base.publicLayout.BaseViewPageFragmentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/30.
 */

public class OutClassAndInerClassActivity extends BaseViewPageFragmentActivity {
    List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected List<Fragment> getFragmentList() {
        NomalOutAndInerClassFragment nomalOutAndInerClassFragment =new NomalOutAndInerClassFragment();
        StaticOutAndInerClassFragment staticOutAndInerClassFragment =new StaticOutAndInerClassFragment();
        mFragmentList.add(nomalOutAndInerClassFragment);
        mFragmentList.add(staticOutAndInerClassFragment);
        return mFragmentList;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}

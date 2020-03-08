package com.demo.lixuan.mydemo.viewpage;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.demo.lixuan.mydemo.base.BaseViewpageFactory;
import com.demo.lixuan.mydemo.fragment.ReyclerPageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 类 名: RecyclerViewPageFactory
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2018/1/16
 * author lixuan
 */

public class RecyclerViewPageFactory implements BaseViewpageFactory {
    private static final String TAG = "RecyclerViewPageFactory";
    public static final int PAGE_1=0;
    public static final int PAGE_2=1;
    public static final int PAGE_3=2;
    public static final int PAGE_4=3;
    public static List<Fragment> fragmentList=new ArrayList<>();
    Fragment fragment;
    @Override
    public int getPageCount() {
        return 0;
    }

    @Override
    public Fragment getFragment(int position) {
        Bundle bundle = new Bundle();
        if (fragment==null){
            switch (position){
                case PAGE_1:
                    fragment= new ReyclerPageFragment();
                    bundle.putInt(ReyclerPageFragment.KEY_PAGE,PAGE_1);
                    return fragment;
                case PAGE_2:
                    fragment= new ReyclerPageFragment();
                    bundle.putInt(ReyclerPageFragment.KEY_PAGE,PAGE_2);
                    return fragment;
                case PAGE_3:
                    fragment= new ReyclerPageFragment();
                    bundle.putInt(ReyclerPageFragment.KEY_PAGE,PAGE_3);
                    return fragment;
                case PAGE_4:
                    fragment= new ReyclerPageFragment();
                    bundle.putInt(ReyclerPageFragment.KEY_PAGE,PAGE_4);
                    return fragment;
            }
        }else {
            return fragment;
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}

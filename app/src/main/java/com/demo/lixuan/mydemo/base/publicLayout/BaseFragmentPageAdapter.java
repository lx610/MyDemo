package com.demo.lixuan.mydemo.base.publicLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/30.
 */

public class BaseFragmentPageAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragmentList=new ArrayList<>();

    public BaseFragmentPageAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }


    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    public void setFragmentList( List<Fragment> fragmentList){
        if (fragmentList!=null){
            this.fragmentList = fragmentList;
        }
        notifyDataSetChanged();
    }

}

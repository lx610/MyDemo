package com.demo.lixuan.mydemo.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;

/**
 * 类 名: BaseFragment
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2018/1/16
 * author lixuan
 */

public abstract class BaseFragment extends Fragment{

    View mView;
    Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        if (mView==null) {
            mView = inflater.inflate(getLayoutResour(), container, false);
        }

        return mView;
    }

    protected abstract int getLayoutResour();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(mView,savedInstanceState);
        initData(mView,savedInstanceState);
        initListener(mView,savedInstanceState);
    }


    public View generateTextButton(String buttonName, View.OnClickListener oncliclickLiener) {
        TextView textBt=new TextView(mActivity);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,10,10);
        textBt.setLayoutParams(params);
        textBt.setText(buttonName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textBt.setBackground(mActivity.getDrawable(R.color.pink));
        }
        textBt.setPadding(10,10,10,10);
        textBt.setOnClickListener(oncliclickLiener);
        return textBt;
    }

    public Activity getSaftActivity(){
        return mActivity;
    }


    protected abstract void initListener(View view, Bundle savedInstanceState);

    protected abstract void initData(View view, Bundle savedInstanceState);

    protected abstract void initView(View rootView, Bundle savedInstanceState);
}

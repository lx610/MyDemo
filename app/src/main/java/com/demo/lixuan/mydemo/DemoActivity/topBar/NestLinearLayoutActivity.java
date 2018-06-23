package com.demo.lixuan.mydemo.DemoActivity.topBar;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;
import com.demo.lixuan.mydemo.view.ImageAdapter;
import com.demo.lixuan.mydemo.widgt.nestedScroll.ScrollToHideBarLinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类 名: NestLinearLayoutActivity
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2018/1/25
 * author lixuan
 */

public class NestLinearLayoutActivity extends BaseActivity {


    @BindView(R.id.iv_image)
    ImageView mIvImage;
    @BindView(R.id.list)
    RecyclerView mList;
    @BindView(R.id.nest_linear_layout)
    ScrollToHideBarLinearLayout mNestLinearLayout;

    private ArrayList mDatalist;
    private ImageAdapter mAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_nest_linear_layout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        View view = View.inflate(this, R.layout.bar_top, null);
        mNestLinearLayout.addTopBar(view);

        mNestLinearLayout.setNoHideItemCount(2);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mDatalist = new ArrayList();
        mAdapter = new ImageAdapter(mDatalist);
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setImageResource(R.mipmap.ic_launcher);
        mAdapter.addHeaderView(imageView);
        mList.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        for (int i = 0; i < 20; i++) {
            mDatalist.add(i + "");
        }
        mAdapter.notifyDataSetChanged();
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

package com.demo.lixuan.mydemo.DemoActivity;

import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;
import com.demo.lixuan.mydemo.view.HeadZoomScrollView;
import com.demo.lixuan.mydemo.view.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类 名: HeadZoomScollerActvity
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2018/1/25
 * author lixuan
 */

public class HeadZoomScollerActvity extends BaseActivity {
    @BindView(R.id.list)
    RecyclerView mList;
    @BindView(R.id.scoller_head_zoom)
    HeadZoomScrollView mScollerHeadZoom;
    @BindView(R.id.iv_image)
    ImageView mIvImage;
    private List<String> mDatalist;
    private ImageAdapter mAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_head_zoom_scroller;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mList.setLayoutManager(new LinearLayoutManager(this));
        mDatalist = new ArrayList();
        mAdapter = new ImageAdapter(mDatalist);
        ImageView imageView=new ImageView(this);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setImageResource(R.mipmap.ic_launcher);
        mAdapter.addHeaderView(imageView);
        mList.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Glide.with(this).load("http://pic11.nipic.com/20101201/4452735_182232064453_2.jpg")
                    .placeholder(getDrawable(R.mipmap.ic_launcher))
                    .into(mIvImage);
        }

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

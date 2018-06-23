package com.demo.lixuan.mydemo.DemoActivity.topBar;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;
import com.demo.lixuan.mydemo.view.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类 名: HideTopBarActvitiy
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2018/1/29
 * author lixuan
 */

public class HideTopBarActvitiy extends BaseActivity {
    @BindView(R.id.list)
    RecyclerView mList;
    @BindView(R.id.root)
    FrameLayout mRoot;

    private List<String> mStringList;
    private BaseQuickAdapter mAdapter;
    private View mHideBar;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_only_recycleview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mHideBar = generaterBarView();
        mHideBar.setVisibility(View.GONE);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mRoot.addView(mHideBar);
        mStringList = new ArrayList<>();
        mAdapter = new ImageAdapter(mStringList);
        mAdapter.addHeaderView(generaterBarView());
        mList.setAdapter(mAdapter);
    }

    private View generaterBarView() {
        View view = View.inflate(this, R.layout.layout_base_bar_text_right, null);
        TextView title = view.findViewById(R.id.tv_title);
        title.setText("隐藏bar");
        ImageView back = view.findViewById(R.id.iv_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        return view;
    }

    @Override
    public void initData() {
        for (int i = 0; i < 20; i++) {
            mStringList.add(i + "");
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void initListener() {
        mList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState==RecyclerView.SCROLL_STATE_SETTLING){
                    mHideBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (dy>0){
                    if (manager.findFirstVisibleItemPosition()>1){
                        mHideBar.setVisibility(View.GONE);
                    }else {
                        mHideBar.setVisibility(View.VISIBLE);
                    }
                }else if (dy<0){
                    mHideBar.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

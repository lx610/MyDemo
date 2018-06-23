package com.demo.lixuan.mydemo.RecyclerView;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类 名: ExpendListActivity
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2017/12/12
 * author lixuan
 */

public class ExpendListActivity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.bt_expend)
    Button mBtExpend;
    @BindView(R.id.rv_list2)
    RecyclerView mRvList2;
    @BindView(R.id.bt_expend2)
    Button mBtExpend2;
    @BindView(R.id.tv_button)
    TextView mTvButton;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;


    private TicketAdapter mTicketAdapter;
    List<String> itemList = new ArrayList();
    private boolean isExpend = false;
    private TicketAdapter mTicketAdapter2;
    private boolean mIsScroll;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_expend_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRvList = (RecyclerView) findViewById(R.id.rv_list);
        mBtExpend = (Button) findViewById(R.id.bt_expend);
        RecyclerView list3 = new RecyclerView(this);
        mRvList.setLayoutManager(new GridLayoutManager(this, 2));
        mRvList2.setLayoutManager(new GridLayoutManager(this, 2));
        list3.setLayoutManager(new GridLayoutManager(this, 2));


        mTicketAdapter = new TicketAdapter(itemList);
        mTicketAdapter2 = new TicketAdapter(itemList);
        mRvList.setAdapter(mTicketAdapter);
        mRvList2.setAdapter(mTicketAdapter2);
        list3.setAdapter(mTicketAdapter2);

        mTicketAdapter.addHeaderView(list3);

        mRvList.setNestedScrollingEnabled(false);
        mRvList2.setNestedScrollingEnabled(false);
    }

    @Override
    public void initData() {
        for (int i = 0; i < 10; i++) {
            itemList.add(i + "");
        }
    }

    @Override
    public void initListener() {
        mBtExpend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpend) {
                    isExpend = false;
                    mTicketAdapter.setItemContToShow(2);
                    mTicketAdapter.notifyDataSetChanged();
                } else {
                    isExpend = true;
                    mTicketAdapter.setItemContToShow(itemList.size());
                    mTicketAdapter.notifyDataSetChanged();
                }
            }
        });
        mBtExpend2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpend) {
                    isExpend = false;
                    mTicketAdapter2.setItemContToShow(2);
                    mTicketAdapter2.notifyDataSetChanged();
                } else {
                    isExpend = true;
                    mTicketAdapter2.setItemContToShow(itemList.size());
                    mTicketAdapter2.notifyDataSetChanged();
                }
            }
        });

        mRvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                scorelToHideButton(recyclerView, newState, mTvButton);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }
        });
        mRvList2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                scorelToHideButton(recyclerView, newState, mTvButton);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }
        });


//        //设置滑动监听动画
//        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            float distence = mTvButton.getWidth();
//            @Override
//            public void onScrollChanged() {
//                //滑动中
//                if (!mIsScroll) {
//                    ObjectAnimator animator = ObjectAnimator.ofFloat(mTvButton, "translationX",  0f,  -distence, -distence);
//                    animator.setDuration(1000);
//                    animator.start();
//                    mIsScroll = true;
//                }else {
//                    //滑动到了底部
//                    if (mScrollView.getChildAt(0).getHeight() - mScrollView.getHeight()
//                            == mScrollView.getScrollY()){
//                        ObjectAnimator animator = ObjectAnimator.ofFloat(mTvButton, "translationX", -distence, -0f, -0f);
//                        animator.setDuration(1000);
//                        animator.start();
//                        mIsScroll = false;
//                    }
////                        滑动到了顶部
//                    if(mScrollView.getScrollY() == 0){
//                        ObjectAnimator animator = ObjectAnimator.ofFloat(mTvButton, "translationX", -distence, -0f, -0f);
//                        animator.setDuration(1000);
//                        animator.start();
//                        mIsScroll = false;
//                    }
//                }
//            }
//        });
    }


    private void scorelToHideButton(RecyclerView recyclerView, int newState, View button) {
        float distence = button.getWidth();
        if (newState != RecyclerView.SCROLL_STATE_IDLE) {
            //滑动中
            if (!mIsScroll) {
                float curTranslationX = button.getTranslationX();
                ObjectAnimator animator = ObjectAnimator.ofFloat(button, "translationX", -0f, distence, distence);
                animator.setDuration(1000);
                animator.start();
                mIsScroll = true;
            }
        } else {
            //没有滑动
            if (mIsScroll) {
                float curTranslationX = button.getTranslationX();
                ObjectAnimator animator = ObjectAnimator.ofFloat(button, "translationX", distence, 0f, 0f);
                animator.setDuration(1000);
                animator.start();
                mIsScroll = false;
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

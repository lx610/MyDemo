package com.demo.lixuan.mydemo.DemoActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.animation.InterpolatorActivity;
import com.demo.lixuan.mydemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类 名: AnimationActivity
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2018/1/2
 * author lixuan
 */

public class AnimationActivity extends BaseActivity {
    @BindView(R.id.tv_transt)
    TextView mTvTranst;
    @BindView(R.id.tv_play_left)
    TextView mTvPlayLeft;
    @BindView(R.id.tv_play_right)
    TextView mTvPlayRight;
    @BindView(R.id.ae_view)
    LottieAnimationView mAeView;
    @BindView(R.id.ae_container)
    FrameLayout mAeContainer;
    @BindView(R.id.tv_intorpolator_activity)
    TextView mTvIntorpolatorActivity;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_animation;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        mAeView.setProgress(1.0f);
    }

    private void TranseAimaLeft(View view) {
        int distence = view.getWidth();
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", distence, 0f, 0f);
        animator.setDuration(1000);
        animator.start();
    }

    private void TranseAimaRight(View view) {
        int distence = view.getWidth();
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", -0f, distence, distence);
        animator.setDuration(1000);
        animator.start();
    }

    @Override
    public void initListener() {
        mTvPlayLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TranseAimaLeft(mTvTranst);
            }
        });

        mTvPlayRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TranseAimaRight(mTvTranst);
            }
        });

        mAeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAeContainer.setVisibility(View.GONE);
            }
        });
        mTvIntorpolatorActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnimationActivity.this, InterpolatorActivity.class));
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

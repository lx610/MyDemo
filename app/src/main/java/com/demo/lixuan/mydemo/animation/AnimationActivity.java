package com.demo.lixuan.mydemo.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.demo.lixuan.mydemo.animation.drag.DragActivity;
import com.demo.lixuan.mydemo.base.publicLayout.LinearActivity;

/**
 * Created by Administrator on 2018/6/5.
 */

public class AnimationActivity extends LinearActivity {
    @Override
    public void initView() {
        final TextView text = (TextView) generateTextButton("move button", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mLlContainer.addView(generateTextButton("hard ware ani", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setLayerType(View.LAYER_TYPE_HARDWARE,null);
                ObjectAnimator animator = ObjectAnimator.ofFloat(text,"translationx",300);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        text.setLayerType(View.LAYER_TYPE_NONE,null);
                    }
                });

                animator.start();
            }
        }));

        mLlContainer.addView(generateTextButton("wigdt animat", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnimationActivity.this,AnimationActivity.class));
            }
        }));
        mLlContainer.addView(generateTextButton("drag activity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnimationActivity.this,DragActivity.class));
            }
        }));

        mLlContainer.addView(text);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}

package com.demo.lixuan.mydemo.animation;

import android.animation.AnimatorSet;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.Utils.UiUtils;
import com.demo.lixuan.mydemo.animation.wight.InterPolatorTextView;
import com.demo.lixuan.mydemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类名： InterpolatorActivity
 * 说明：
 * <p>
 * 修改记录：
 * <p>
 * 版 权 所 有:   Copyright  2018
 * 公       司:   深圳市旅联网络科技有限公司
 * version   2.0
 * date   2018/5/15
 * author lixuan
 * Created by elk-lx on 2018/5/15.
 */

public class InterpolatorActivity extends BaseActivity {
    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_only_linearlayout;
    }

    @Override
    public void initView() {
        final InterPolatorTextView changeBt = generaterInterPolatorTextView("DeceleraterInterplator", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.makeText("被点击到了");
            }
        });

        final TextView slowBt = (TextView) generateTextButton("click change bt", new View.OnClickListener() {
            boolean isLong=false;
            LinearLayout.LayoutParams endParams =getLayParms(60);
            LinearLayout.LayoutParams startParams =getLayParms(10);
            @Override
            public void onClick(View v) {
                if (!isLong){
                    ObjectAnimator parmsAnim = ObjectAnimator.ofInt(changeBt, "width", 500);
//                    ObjectAnimator.ofObject(changeBt,"LayoutParams",changeBt,startParams,endParams);
                    ObjectAnimator anim = ObjectAnimator.ofObject(changeBt, "color", new ColorEvaluator(),
                            "#0000FF", "#FF0000");
                    ObjectAnimator animParms = ObjectAnimator.ofObject(changeBt, "leftMargin", new IntEvaluator(),
                            10, 60);
                    AnimatorSet animSet = new AnimatorSet();
                    animSet.play(parmsAnim).with(anim).with(animParms);
                    animSet.setDuration(5000);
                    animSet.start();
                    isLong=true;
                }else {
                    ObjectAnimator parmsAnim = ObjectAnimator.ofInt(changeBt,"width",200);
//                    ObjectAnimator.ofObject(changeBt,"LayoutParams",changeBt,endParams,startParams);
                    ObjectAnimator anim = ObjectAnimator.ofObject(changeBt, "color", new ColorEvaluator(),
                            "#FF0000", "#0000FF");
                    ObjectAnimator animParms = ObjectAnimator.ofObject(changeBt, "leftMargin", new IntEvaluator(),
                            60, 10);
                    AnimatorSet animSet = new AnimatorSet();
                    animSet.play(parmsAnim).with(anim).with(animParms);
                    animSet.setDuration(5000);
                    animSet.start();
                    isLong=false;
                }


            }
        });
        mLlContainer.addView(slowBt);
        mLlContainer.addView(changeBt);
    }

    private LinearLayout.LayoutParams getLayParms(int rightLineParms) {
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(rightLineParms,10,10,10);
        return params;
    }

    @Override
    public void initData() {

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

    public InterPolatorTextView generaterInterPolatorTextView(String buttonName, View.OnClickListener oncliclickLiener){
        InterPolatorTextView textView =new InterPolatorTextView(this);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,10,10);
        textView.setLayoutParams(params);
        textView.setText(buttonName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textView.setBackground(getDrawable(R.color.pink));
        }
        textView.setPadding(10,10,10,10);
        textView.setOnClickListener(oncliclickLiener);
        return textView;
    }
}

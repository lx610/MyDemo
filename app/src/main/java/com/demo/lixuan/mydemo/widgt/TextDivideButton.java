package com.demo.lixuan.mydemo.widgt;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.Utils.DeviceUtil;
import com.demo.lixuan.mydemo.Utils.UiUtils;


/**
 * 类 名: TextDivideButton
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2018/1/2
 * author lixuan
 */

public class TextDivideButton extends FrameLayout {
    ClickDivideButton mBtDivid;
    TextView mTvLeft;
    TextView mTvRight;
    FrameLayout mFlContainer;
    private int animTime = 500;
    private OnSideClcikListener mOnSideClcikListener;
    private OnSideChangeListener mOnSideChangeListener;
    private int centerDistence;

    public TextDivideButton(Context context) {
        super(context);
    }

    public TextDivideButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        moveFromLetToCenter(mTvLeft);
        initListener();
    }


    private void init(Context context) {
        View view = View.inflate(context, R.layout.view_text_divid_button, this);
        mBtDivid= (ClickDivideButton) view.findViewById(R.id.bt_divid);
        mTvLeft= (TextView) view.findViewById(R.id.tv_left);
        mTvRight= (TextView) view.findViewById(R.id.tv_right);
        mFlContainer= (FrameLayout) view.findViewById(R.id.fl_container);
        mBtDivid.setAnimationTime(animTime);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        DeviceUtil.getDeviceWidth();
        centerDistence=(mFlContainer.getMeasuredWidth()-mFlContainer.getMeasuredHeight()*2)/2;
        layoutParams.setMargins(centerDistence,0,0,0);
        layoutParams.gravity= Gravity.CENTER_VERTICAL;
        mTvLeft.setLayoutParams(layoutParams);
    }

    private void initListener() {
        mBtDivid.setOnStateChangedListener(new ClickDivideButton.OnStateChangedListener() {
            @Override
            public void onStateChanged(boolean isShowRight) {
                if (isShowRight) {
//                    展开右边，字移动到中间，中间的文字退回到左边
                    moveFromCenterToLeft(mTvLeft);
                    moveFromRigthToCenter(mTvRight);
                    mTvRight.setText("登录");
                    mTvRight.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
                    mTvLeft.setText("账号登录");
                    mTvLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

                    if (mOnSideChangeListener!=null){
                        mOnSideChangeListener.onSideChange(true);
                    }
                } else {
                    //展开左边，左边的文字移动到中间，中间的文字退回到右边
                    moveFromLetToCenter(mTvLeft);
                    moveFromeCenterToRigth(mTvRight);
                    mTvRight.setText("快捷登录");
                    mTvRight.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                    mTvLeft.setText("登录");
                    mTvLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                    if (mOnSideChangeListener!=null){
                        mOnSideChangeListener.onSideChange(false);
                    }
                }
            }


        });


        mBtDivid.setOnSideClcikListener(new ClickDivideButton.OnSideClcikListener() {
            @Override
            public void onClickListener(boolean isLeftSide) {
                if (isLeftSide){
                    if (mOnSideClcikListener!=null){
                        mOnSideClcikListener.onClickListener(isLeftSide);
                    }
                }else {
                    if (mOnSideClcikListener!=null){
                        mOnSideClcikListener.onClickListener(isLeftSide);
                    }
                }
            }
        });



    }

    private void moveFromeCenterToRigth(View view) {
        int distence = centerDistence-mFlContainer.getMeasuredHeight()/2;
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", -distence, 0f, 0f);
        animator.setDuration(animTime);
        animator.start();
    }

    private void moveFromLetToCenter(View view) {
        int distence =centerDistence-mFlContainer.getMeasuredHeight()/2 +UiUtils.dip2px(5);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", -distence, 0f, 0f);
        animator.setDuration(animTime);
        animator.start();
    }

    private void moveFromCenterToLeft(View view) {
        int distence = centerDistence-mFlContainer.getMeasuredHeight()/2 + UiUtils.dip2px(5);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", 0f, -distence, -distence );
        animator.setDuration(animTime);
        animator.start();
    }

    private void moveFromRigthToCenter(View view) {
        int distence =centerDistence-mFlContainer.getMeasuredHeight()/2;
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", -0f, -distence, -distence);
        animator.setDuration(animTime);
        animator.start();

    }


    public void setTvLeftString(String tvLeftString) {
        mTvLeft.setText(tvLeftString);
    }

    public void setTvRightString(String tvRightString) {
        mTvRight.setText(tvRightString);
    }

    public void setAnimTime(int animTime) {
        this.animTime = animTime;
    }

    public void setRightBgColor(int Color){
        mBtDivid.setRightSideColor(Color);
    }

    public void setLeftBgColor(int Color){
        mBtDivid.setLeftSideColor(Color);
    }


    public void setOnSideClcikListener(OnSideClcikListener onSideClcikListener) {
        mOnSideClcikListener = onSideClcikListener;
    }
    public interface OnSideClcikListener{
        void onClickListener(boolean isLeftSide);
    }

    public interface OnSideChangeListener{
        void onSideChange(boolean isShowingRight);
    }

    public void setOnSideChangeListener(OnSideChangeListener onSideChangeListener) {
        mOnSideChangeListener = onSideChangeListener;
    }
}

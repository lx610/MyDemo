package com.demo.lixuan.mydemo.widgt.fingerPSW;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2018/6/14.
 */

public class DotView extends View {

    private int mViewWidth;
    private int floatRect;
    private Paint whitePainter;
    private boolean isOnTouch;//被按住的状态
    private int halfWidthDotRect;
    private Paint halfWhitePainter;//按住的时候出现扩大的圆

    public DotView(Context context) {
        super(context);
        initView(context);
    }



    public DotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DotView(Context context, int i) {
        super(context);
        initView(context);
    }


    private void initView(Context context) {
        whitePainter = new Paint();
        whitePainter.setColor(Color.WHITE);
        whitePainter.setStyle(Paint.Style.FILL_AND_STROKE);

        halfWhitePainter = new Paint();
        halfWhitePainter.setColor(Color.WHITE);
        halfWhitePainter.setStyle(Paint.Style.FILL_AND_STROKE);
        halfWhitePainter.setAlpha(128);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int mWidthsp = widthMeasureSpec >= heightMeasureSpec ? heightMeasureSpec : widthMeasureSpec;
//        super.onMeasure(mWidthsp, mWidthsp);
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec),getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec));

        mViewWidth = getMeasuredWidth();
        int viewHight = getMeasuredHeight();

        mViewWidth = mViewWidth <viewHight? mViewWidth :viewHight;
        halfWidthDotRect =halfWidthDotRect/4;
        floatRect =halfWidthDotRect;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isOnTouch=true;
                growCricle();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                isOnTouch=false;
                deGrowCircle();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);

    }

    /**
     * 让园变小
     */
    private void deGrowCircle() {
        ValueAnimator valueAnimate=new ValueAnimator();
        valueAnimate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer aniValue = (Integer) animation.getAnimatedValue();
                if (floatRect>halfWidthDotRect){
                    floatRect = floatRect - aniValue;
                }
                invalidate();
            }
        });
        valueAnimate.setDuration(500).start();
    }

    /**
     * 让园变大
     */
    private void growCricle() {
        ValueAnimator valueAnimate=new ValueAnimator();
        valueAnimate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer aniValue = (Integer) animation.getAnimatedValue();
                if (floatRect<mViewWidth/2){
                    floatRect = floatRect + aniValue;
                }
                invalidate();
            }
        });
        valueAnimate.setDuration(500).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mViewWidth/2,mViewWidth/2, halfWidthDotRect,whitePainter);

        if (isOnTouch){
            canvas.drawCircle(mViewWidth/2,mViewWidth/2,floatRect,halfWhitePainter);
        }
    }


}

package com.demo.lixuan.mydemo.widgt.fingerPSW;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

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
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int minWidth=180;
        int minHeight=180;

        if (getLayoutParams().width== ViewGroup.LayoutParams.WRAP_CONTENT
                &&getLayoutParams().height== ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(minWidth,minHeight);
        }else if (getLayoutParams().width== ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(minWidth,heightSize);
        }else if (getLayoutParams().height== ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(widthSize,minHeight);
        }else {
            setMeasuredDimension(widthSize,heightSize);
        }

        mViewWidth = getMeasuredWidth();
        int viewHight = getMeasuredHeight();
        mViewWidth = mViewWidth <viewHight? mViewWidth :viewHight;
        halfWidthDotRect =mViewWidth/4;
        floatRect =halfWidthDotRect;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean getEvent = false;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isOnTouch=true;
                getEvent=true;
                growCricle();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isOnTouch=false){
                    getEvent=true;
                    growCricle();
                }else if (isOnTouch=true){
                    getEvent=true;
                }else {
                    getEvent=false;
                }
                break;
            case MotionEvent.ACTION_UP:
                isOnTouch=false;
                deGrowCircle();
                getEvent=true;
                break;
            default:
                getEvent=false;
                break;
        }
//        return super.onTouchEvent(event);这句会造成MOVE up事件被fu'kong'j
        return getEvent;
    }

    /**
     * 让园变小
     */
    private void deGrowCircle() {

        ValueAnimator valueAnimate=ValueAnimator.ofInt(floatRect,halfWidthDotRect);
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
        valueAnimate.setDuration(600).start();
    }

    /**
     * 让园变大
     */
    private void growCricle() {
        final IntEvaluator mEvaluate = new IntEvaluator();
        ValueAnimator valueAnimate=ValueAnimator.ofInt(floatRect,mViewWidth/2);
        valueAnimate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer aniValue = (Integer) animation.getAnimatedValue();
                float fraction =animation.getAnimatedFraction();
                if (floatRect<mViewWidth/2){
                    floatRect = mEvaluate.evaluate(fraction,floatRect,mViewWidth/2);
                    invalidate();
                }

            }
        });
        valueAnimate.setDuration(600).start();
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

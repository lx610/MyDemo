package com.demo.lixuan.mydemo.widgt;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import static android.R.attr.button;

/**
 * 类 名: DivideButton
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2017/12/26
 * author lixuan
 */

public class ClickDivideButton extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); //抗锯齿

    boolean isRightShow = false;
    float curX = 0;
    float centerY; //y固定
    float viewWidth;
    float radius;
    float lineStart; //直线段开始的位置（横坐标，即
    float lineEnd; //直线段结束的位置（纵坐标
    float lineWidth;

    float buttonAtRight;
    float buttonAtLeft;
    final int SCALE = 4; // 控件长度为滑动的圆的半径的倍数

    OnStateChangedListener onStateChangedListener;//状态变化响应
    OnSideClcikListener mOnSideClcikListener;
    private int mLeftSideColor =Color.RED;
    private int mRightSideColor =Color.YELLOW;
    private int animationTime =1000;


    public ClickDivideButton(Context context) {
        super(context);
    }

    public ClickDivideButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickDivideButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
//        curX = event.getX();
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            if (false== isRightShow){
                //当左侧展开的时候
                if (event.getX()>buttonAtRight){//注意，lineEnd距离最右边还差了1个半径
                    //如果点击到了右侧收缩区域，展开右侧，收起左侧

                    //播放动画,平移动画
                    ValueAnimator anim = ValueAnimator.ofFloat(buttonAtRight, buttonAtLeft);
                    anim.setDuration(animationTime);
                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float currentValue = (float) animation.getAnimatedValue();
                            curX=currentValue;
                            if (curX<=buttonAtLeft + radius){
                                isRightShow = true;//改变状态，改变半圆颜色
                            }
                            ClickDivideButton.this.postInvalidate();
                        }
                    });
                    anim.start();

//                    curX=radius*2;
                    //只有状态发生改变才调用回调函数， 下同
                    if(null != onStateChangedListener)
                    {
                        onStateChangedListener.onStateChanged(true);
                    }

                }else {
                    //响应左侧点击响应
                    if (mOnSideClcikListener!=null){
                        mOnSideClcikListener.onClickListener(true);
                    }
                }
            }else {
                //当右侧展开的时候
                if (event.getX()<radius*4){
                    //如果点击到了左侧收缩区域，展开左侧，收起右侧

                    //播放动画,平移动画
                    ValueAnimator anim = ValueAnimator.ofFloat(buttonAtLeft, buttonAtRight);
                    anim.setDuration(animationTime);
                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float currentValue = (float) animation.getAnimatedValue();
                            curX=currentValue;
                            if (curX>=buttonAtRight - radius){
                                isRightShow = false;//改变状态，改变半圆颜色
                            }
                            //重绘
                            ClickDivideButton.this.postInvalidate();
                        }
                    });
                    anim.start();

//                    curX=lineEnd-radius*1;
                    //只有状态发生改变才调用回调函数， 下同
                    if(null != onStateChangedListener)
                    {
                        onStateChangedListener.onStateChanged(false);
                    }

                }else {
                    //响应右侧点击响应
                    if (mOnSideClcikListener!=null){
                        mOnSideClcikListener.onClickListener(false);
                    }
                }
            }
        }
//        释放判断，否则不会有限位效果
        if(event.getAction() == MotionEvent.ACTION_UP)
        {
            if(false== isRightShow)
            {
                curX = buttonAtRight;//滑动到空一个圆的位置
            }
            else  if (true== isRightShow)
            {
                curX = buttonAtLeft;
            }
        }
        /*通过刷新调用onDraw*/
        this.postInvalidate();
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // TODO Auto-generated method stub
        /*保持宽是高的SCALE / 2倍， 即圆的直径*/
//        this.setMeasuredDimension(this.getMeasuredWidth(), this.getMeasuredWidth() * 2 / SCALE);

        this.setMeasuredDimension(this.getMeasuredWidth() , this.getMeasuredHeight());
        viewWidth = this.getMeasuredWidth();
//        radius = viewWidth / SCALE;
        radius = this.getMeasuredHeight() / 2;
        lineWidth = radius * 2f; //直线宽度等于滑块直径
        lineEnd = this.getMeasuredWidth()-radius*1;
        if (curX==0){
             buttonAtRight=viewWidth-3*radius;
             buttonAtLeft=3*radius;
            curX =viewWidth-radius * 4f;//滑块初始位置
        }
//        centerY = this.getMeasuredWidth() / SCALE; //centerY为高度的一半
        centerY = this.getMeasuredHeight() / 2; //centerY为高度的一半
        lineStart = radius;
//        lineEnd = (SCALE - 1) * radius;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        /*划线*/
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(lineWidth);
        /*左边部分的线，蓝色*/
        mPaint.setColor(mLeftSideColor);
        canvas.drawLine(lineStart, centerY, curX, centerY, mPaint);
        /*右边部分的线，灰色*/
        mPaint.setColor(mRightSideColor);
        canvas.drawLine(curX, centerY, lineEnd, centerY, mPaint);

        /*画圆*/
        /*画最左和最右的圆，直径为直线段宽度， 即在直线段两边分别再加上一个半圆*/
        mPaint.setStyle(Paint.Style.FILL);
        //右边的圆
        mPaint.setColor(mRightSideColor);
        canvas.drawCircle(lineEnd, centerY, lineWidth / 2, mPaint);
        //左边的圆
        mPaint.setColor(mLeftSideColor);
        canvas.drawCircle(lineStart, centerY, lineWidth / 2, mPaint);

//        滑块
        /*圆形滑块*/
        if (isRightShow){
            mPaint.setColor(mLeftSideColor);
            canvas.drawCircle(curX, centerY, radius , mPaint);
        }else {
            mPaint.setColor(mRightSideColor);
            canvas.drawCircle(curX, centerY, radius , mPaint);
        }



        //画圆角矩形
//        RectF oval3 = new RectF(80, 260, 200, 300);// 设置个新的长方形
//        canvas.drawRoundRect(oval3, 20, 5, mPaint);//第二个参数是x半径，第三个参数是y半径
//        canvas.drawArc(radius, radius, radius ,radius, mPaint);
//        mPaint.setColor(Color.YELLOW);
    }

    public void setRightSideColor(int rightSideColor) {
        mRightSideColor = rightSideColor;
    }

    public void setAnimationTime(int animationTime) {
        this.animationTime = animationTime;
    }

    public void setLeftSideColor(int leftSideColor) {
        mLeftSideColor = leftSideColor;
    }

    /*设置开关状态改变监听器*/
    public void setOnStateChangedListener(OnStateChangedListener o)
    {
        this.onStateChangedListener = o;
    }

    /*内部接口，开关状态改变监听器*/
    public interface OnStateChangedListener
    {
        public void onStateChanged(boolean isShowRight);
    }

    public interface OnSideClcikListener{
        void onClickListener(boolean isLeftSide);
    }

    public void setOnSideClcikListener(OnSideClcikListener onSideClcikListener) {
        mOnSideClcikListener = onSideClcikListener;
    }
}

package com.demo.lixuan.mydemo.widgt;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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

public class DivideButton extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); //抗锯齿

    boolean isOn = false;
    float curX = 0;
    float centerY; //y固定
    float viewWidth;
    float radius;
    float lineStart; //直线段开始的位置（横坐标，即
    float lineEnd; //直线段结束的位置（纵坐标
    float lineWidth;
    final int SCALE = 4; // 控件长度为滑动的圆的半径的倍数

    OnStateChangedListener onStateChangedListener;

    public DivideButton(Context context) {
        super(context);
    }

    public DivideButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DivideButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        curX = event.getX();
        if(event.getAction() == MotionEvent.ACTION_UP)
        {
            if(curX > viewWidth / 2)
            {
                curX = lineEnd-radius*2;//滑动到空一个圆的位置
                if(false == isOn)
                {
                    //只有状态发生改变才调用回调函数， 下同
                    if(null != onStateChangedListener)
                    {
                        onStateChangedListener.onStateChanged(true);
                    }
                    isOn = true;
                }
            }
            else
            {
                curX = lineStart + radius*2;
                if(true == isOn)
                {
                    if(null != onStateChangedListener)
                    {
                        onStateChangedListener.onStateChanged(false);
                    }
                    isOn = false;
                }
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
        curX = radius*3;//滑块初始位置
//        centerY = this.getMeasuredWidth() / SCALE; //centerY为高度的一半
        centerY = this.getMeasuredHeight() / 2; //centerY为高度的一半
        lineStart = radius;
//        lineEnd = (SCALE - 1) * radius;
        lineEnd = this.getMeasuredWidth()-radius*1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        /*划线*/
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(lineWidth);
        /*左边部分的线，蓝色*/
        mPaint.setColor(Color.RED);
        canvas.drawLine(lineStart, centerY, curX, centerY, mPaint);
        /*右边部分的线，灰色*/
        mPaint.setColor(Color.YELLOW);
        canvas.drawLine(curX, centerY, lineEnd, centerY, mPaint);

        /*画圆*/
        /*画最左和最右的圆，直径为直线段宽度， 即在直线段两边分别再加上一个半圆*/
        mPaint.setStyle(Paint.Style.FILL);
        //右边的圆
        mPaint.setColor(Color.YELLOW);
        canvas.drawCircle(lineEnd, centerY, lineWidth / 2, mPaint);
        //左边的圆
        mPaint.setColor(Color.RED);
        canvas.drawCircle(lineStart, centerY, lineWidth / 2, mPaint);
//        滑块
        /*圆形滑块*/
        mPaint.setColor(Color.YELLOW);
        canvas.drawCircle(curX, centerY, radius , mPaint);
        //画圆角矩形
//        RectF oval3 = new RectF(80, 260, 200, 300);// 设置个新的长方形
//        canvas.drawRoundRect(oval3, 20, 5, mPaint);//第二个参数是x半径，第三个参数是y半径
//        canvas.drawArc(radius, radius, radius ,radius, mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawText("开始写字了！",20, centerY, mPaint);// 画文本
    }

    /*设置开关状态改变监听器*/
    public void setOnStateChangedListener(OnStateChangedListener o)
    {
        this.onStateChangedListener = o;
    }

    /*内部接口，开关状态改变监听器*/
    public interface OnStateChangedListener
    {
        public void onStateChanged(boolean state);
    }
}

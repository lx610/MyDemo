package com.demo.lixuan.mydemo.widgt.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.demo.lixuan.mydemo.R;

/**
 * Created by Administrator on 2018/6/4.
 */

public class RoundClockView extends View {
    private static final String TAG = "RoundClockView";

    private int mWidth;
    private int mRect;
    private Paint mBlackPaint;
    private boolean firstDraw=true;
    private Paint mTextPaint;
    private int hight;
    private  int startime = 1;
    private int mSecond;
    private Handler mHandler;


    public RoundClockView(Context context) {
        super(context);
        init(context);
    }


    public RoundClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mBlackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBlackPaint.setStyle(Paint.Style.STROKE);
        mBlackPaint.setColor(Color.BLACK);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(getResources().getColor(R.color.green));
        mTextPaint.setTextSize(24);

        mHandler = new Handler();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mWidthsp = widthMeasureSpec >= heightMeasureSpec ? heightMeasureSpec : widthMeasureSpec;
        mWidth= getMeasuredWidth();
        hight = getMeasuredHeight();
        mRect = Math.min(mWidth,hight)/2;
        super.onMeasure(mWidthsp, mWidthsp);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout: getwidth" + getWidth());
        Log.d(TAG, "onLayout: getheight" + getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int peddingLeft = getPaddingLeft();
        int peddingRight = getPaddingRight();


            canvas.drawCircle(mRect,mRect,mRect,mBlackPaint);//画表盘
            for (int i = 1; i < 13; i++) {//
                int textRect = mRect;
                canvas.rotate(30,textRect,textRect);

                canvas.rotate(-30*i,textRect,0);
                canvas.drawText(i + "",textRect,0,mTextPaint);//画时间刻度
                canvas.rotate(30*i,textRect,0);//把画布转回来

            }
        //转动画布实现秒帧移动
        canvas.rotate(6*mSecond,mRect,mRect);

        Path path =new Path();
        path.moveTo(mRect,0);
        path.lineTo(mRect+ 25,mRect +25);
        path.lineTo(mRect,mRect +10);
        path.lineTo(mRect- 25,mRect +25);
        path.setLastPoint(mRect,0);
        canvas.drawPath(path,mTextPaint);
        //把画布再转回来，让表盘朝上
        canvas.rotate(-6,mRect,mRect);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSecond = startime % 60;
                startime++;
                postInvalidate();
            }
        },1000);
    }
}

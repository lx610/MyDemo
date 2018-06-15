package com.demo.lixuan.mydemo.widgt.fingerPSW;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 说明：9点手势密码，核心原理：9个点对应1-9的数字，
 *         手指按下后，进入输入密码的状态，滑动坐标判断是否按到数字
 *         数字不会被重复
 * Created by Administrator on 2018/6/14.
 */

public class NineDotView extends FrameLayout {
    private int viewWidth;
    private int viewHight;
    private int cellWidth;
    private int cellHeight;

    public NineDotView(Context context) {
        super(context);
        init(context);
    }



    public NineDotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            int lineNum = i / 3;
            int posion = i % 3;
            View child=  getChildAt(i);
            int childeHight = child.getMeasuredHeight();//getHight是0
            int childLeft = childeHight * posion;
            int childTop    =childeHight *lineNum;
            int right =childLeft + childeHight;
            int childBottom = childeHight + childeHight;
            child.layout(childLeft,childTop,right,childBottom);
        }

    }

    private void init(Context context) {
        for (int i = 1; i < 10; i++) {
            DotView dotView=new DotView(context,i);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(180,180);
            dotView.setLayoutParams(params);
            addView(dotView);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        viewHight = getMeasuredHeight();
        cellWidth =viewWidth/3;
        cellHeight = viewHight/3;
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // TODO: 2018/6/14 画出9个点

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                // TODO: 2018/6/14  如果按下了某个点，要开始画线
                boolean isDrawingPSW = false;
                isDrawingPSW = checkDrawStatus(event);
              break;
            case MotionEvent.ACTION_MOVE:
                // TODO: 2018/6/14 如果允许画线根据手指目前的位置连接直线
                // TODO: 2018/6/14 如果连接到了一个点，更新起点
                isDrawingPSW = checkDrawStatus(event);
                break;
            case MotionEvent.ACTION_UP:
                // TODO: 2018/6/14 如果是画线状态，抬手后，开始输入密码
                isDrawingPSW = checkDrawStatus(event);
                readPsw();
                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    private void readPsw() {
        
    }


//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        boolean isInterupt=true;
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                isInterupt=true;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                isInterupt=true;
//                break;
//            case MotionEvent.ACTION_UP:
//                isInterupt=true;
//                break;
//            default:
//                isInterupt=true;
//                break;
//        }
//        return isInterupt;
//
//    }

    /**根据触摸位置判断，是否在输入密码
     * @return
     * @param event
     */
    private boolean checkDrawStatus(MotionEvent event) {

        return false;
    }
}

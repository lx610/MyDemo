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
    private int mCellWidth;

    int dotWidth = 120;
    private int mDefaultGap;

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
        int pBottom = getPaddingBottom();
        int pLeft= getPaddingLeft();
        int pRight = getPaddingRight();
        int pTop = getPaddingTop();

        mDefaultGap = 45;
        mDefaultGap= (getMeasuredWidth()-pLeft-pRight - getChildAt(0).getMeasuredWidth()*3)/2;

        for (int i = 0; i < getChildCount(); i++) {
            int lineNum = i / 3;
            int posion = i % 3;
            View child=  getChildAt(i);
            int childeHight = child.getMeasuredHeight();//getHight是0
            mCellWidth = childeHight;
            int childLeft;
            if (posion!=0){
                childLeft = childeHight * posion + pLeft + mDefaultGap *posion;
            }else {
                childLeft = childeHight * posion + pLeft;
            }

            int childTop;
            if (lineNum!=0){
                childTop    =childeHight *lineNum + pTop+ mDefaultGap *lineNum;
            }else {
                childTop    =childeHight *lineNum + pTop;
            }

            int   right =childLeft + childeHight;
            int   childBottom = childTop + childeHight + pBottom;
            child.layout(childLeft,childTop,right,childBottom);
        }

    }

    private void init(Context context) {
        for (int i = 1; i < 10; i++) {
            DotView dotView=new DotView(context,i);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(dotWidth,dotWidth);
            dotView.setLayoutParams(params);
            addView(dotView);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int minWidth=450;
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
                moveToClickButton(event);
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

    private void moveToClickButton(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_MOVE){
            int x =(int) event.getX();
            int y =(int)event.getY();
            x = x -getPaddingLeft();
            y = y - getPaddingTop();

            int lineNum = 0;
            if (y>0){
                if (y>mCellWidth){
                    if (y>2*mCellWidth + 2 * mDefaultGap){
                        if (y<3*mCellWidth + 2 * mDefaultGap){
                            lineNum = 2;//第三行
                        }else {
                            return;
                        }
                    }else {
                        if (y>mCellWidth + mDefaultGap&&y<mCellWidth*2 + mDefaultGap)
                        {
                            lineNum = 1;//第er行
                        }else {
                            return;
                        }
                    }
                }else {
                    lineNum = 0;//第一行
                }
            }else {
                return;
            }

            int position;
            if (x>0){
                if (x>mCellWidth){
                    if (x>2*mCellWidth + 2 * mDefaultGap){
                        if (x<3*mCellWidth + 2 * mDefaultGap){
                            position = 2;//第三行
                        }else {
                            return;
                        }
                    }else {
                        if (x>mCellWidth + mDefaultGap&&x<mCellWidth*2 + mDefaultGap)
                        {
                            position = 1;//第er行
                        }else {
                            return;
                        }
                    }
                }else {
                    position = 0;//第一行
                }
            }else {
                return;
            }

//            if (y-getPaddingTop()-mCellWidth > mCellWidth &&  y-getPaddingTop()-mCellWidth < getPaddingTop() + mCellWidth +mDefaultGap)
//            int lineNum = y / mCellWidth;
//            int position = x -getPaddingLeft()/mCellWidth;
            int index = lineNum * 3 + position;
            DotView clickChild = (DotView) getChildAt(index);
            clickChild.growCricle();
        }

    }

    private void readPsw() {

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x =(int) ev.getX();
        int y =(int)ev.getY();
        boolean intercepted = false;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                intercepted =true;
//                moveToClickButton(ev);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return intercepted;

    }

    /**根据触摸位置判断，是否在输入密码
     * @return
     * @param event
     */
    private boolean checkDrawStatus(MotionEvent event) {

        return false;
    }
}

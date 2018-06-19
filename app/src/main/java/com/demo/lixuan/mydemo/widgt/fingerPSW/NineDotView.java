package com.demo.lixuan.mydemo.widgt.fingerPSW;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.demo.lixuan.mydemo.Utils.MD5Util;
import com.demo.lixuan.mydemo.Utils.UiUtils;

import java.util.ArrayList;

/**
 * 说明：9点手势密码，核心原理：9个点对应1-9的数字，
 *         手指按下后，进入输入密码的状态，滑动坐标判断是否按到数字
 *         数字不会被重复
 * Created by Administrator on 2018/6/14.
 */

public class NineDotView extends FrameLayout {
    public static final int STATUS_SET_PSW=10;
    public static final int STATUS_CHECK_PSW=11;
    public static final String SP_CODE="9DotCode";
    public static final String SP_KEY_CODE="sp_key_code";

    private int viewWidth;
    private int viewHight;
    private int cellWidth;
    private int cellHeight;
    private int mCellWidth;

    int dotWidth = 120;
    private int mDefaultGap;
    private Paint pathPaint;
    private Path mPath;
    private ArrayList<DotView> mSelectDotList;
    private android.graphics.Path mRecordPath = new Path();
    int mPswStatus =11;
    private Context mContext;

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

    public void setPswStatus(int status){
        mPswStatus = status;
    }

    private void init(Context context) {
        mContext = context;

        pathPaint = new Paint();
        pathPaint.setColor(Color.GREEN);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(20.0f);

        mPath = new Path();

        for (int i = 1; i < 10; i++) {
            DotView dotView=new DotView(context,i);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(dotWidth,dotWidth);
            dotView.setLayoutParams(params);
            addView(dotView);
        }

        mSelectDotList = new ArrayList<>();

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
        canvas.drawPath(mPath,pathPaint);
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
                mPath.reset();
                mPath.addPath(mRecordPath);
                isDrawingPSW = checkDrawStatus(event);
                moveToClickButton(event);
                mPath.lineTo(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_UP:
                // TODO: 2018/6/14 如果是画线状态，抬手后，开始输入密码
                isDrawingPSW = checkDrawStatus(event);
                readPsw();
                break;
            default:
                break;
        }
        invalidate();
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

            if (mSelectDotList.size()==0){
                if (!clickChild.isOnTouch()){
                    mSelectDotList.add(clickChild);
                    mRecordPath.moveTo(clickChild.getX()+ mCellWidth/2,clickChild.getY() + + mCellWidth/2);
                }
            }else {
                if (!clickChild.isOnTouch()){
                    mSelectDotList.add(clickChild);
                    mRecordPath.lineTo(clickChild.getX()+ mCellWidth/2,clickChild.getY() + + mCellWidth/2);
                    mRecordPath.moveTo(clickChild.getX()+ mCellWidth/2,clickChild.getY() + + mCellWidth/2);
                }
            }
            clickChild.growCricle();

        }

    }

    private void readPsw() {
        ArrayList pswList = new ArrayList();
        StringBuilder stringBuilder =new StringBuilder();
        //清除绘画痕迹，读取密码
        int dotSize = mSelectDotList.size();
        for (int i = 0; i < dotSize; i++) {
            DotView dot = mSelectDotList.get(i);
            pswList.add( dot.getPsw());
            stringBuilder.append(dot.getPsw());
        }

        switch (mPswStatus){
            case STATUS_CHECK_PSW:
                String code = MD5Util.MD5_String(stringBuilder.toString());
                SharedPreferences sp = mContext.getSharedPreferences(SP_CODE,Context.MODE_PRIVATE);
                String codeSaved = sp.getString(SP_KEY_CODE, "");

                if (code.equals(codeSaved)){
                    UiUtils.makeText("解锁");
                }else {
                    UiUtils.makeText("密码错误");
                }

                break;
            case STATUS_SET_PSW:
                String code2 = MD5Util.MD5_String(stringBuilder.toString());
                SharedPreferences sp2 =mContext.getSharedPreferences(SP_CODE,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor =sp2.edit();
                editor.putString(SP_KEY_CODE,code2);
                editor.commit();
                UiUtils.makeText("密码保存");
                break;
        }

        for (int i = 0; i < dotSize; i++) {
            DotView dot = mSelectDotList.get(i);
            dot.deGrowCircle();
        }
        mSelectDotList.clear();
        mRecordPath.reset();
        mPath.reset();
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

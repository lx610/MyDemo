package com.demo.lixuan.mydemo.widgt.nestedScroll;

import android.content.Context;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.demo.lixuan.mydemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 类 名: ScrollToHideBar
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2018/1/25
 * author lixuan
 */

public class ScrollToHideBarLinearLayout2 extends FrameLayout implements NestedScrollingParent {
    private static final String TAG = "ScrollToHideBarLinearLa";

    private int mTopViewHeight;

    private int noHideItemCount =1;
    private FrameLayout mBarContainer;
    private LinearLayout mLinearContainer;
    private View mFloatbar;


    public ScrollToHideBarLinearLayout2(Context context) {
        super(context);
    }

    public ScrollToHideBarLinearLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        View view=View.inflate(context, R.layout.layout_top_hide_bar2,this);
        mLinearContainer = view.findViewById(R.id.ll_container);
        mBarContainer = view.findViewById(R.id.bar_container);

    }

    public List<View> addTopBar(Context context, int resId){
        View scrollerBar = View.inflate(context, resId, null);
        mFloatbar = View.inflate(context, resId, null);
        mBarContainer.addView(scrollerBar);
        addView(mFloatbar);
        List list=new ArrayList();
        list.add(scrollerBar);
        list.add(mFloatbar);
        return list;
    }

    public void setNoHideItemCount(int noHideItemCount) {
        this.noHideItemCount = noHideItemCount;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).getId()==R.id.ll_bt_container){
                Log.d(TAG, "onLayout: " + i +"是线性布局");
            }else if (getChildAt(i).getId()==R.id.bar_container){
                Log.d(TAG, "onLayout: " + i +"标题布局");
            }else {

            }


        }

        final int count = getChildCount();
        int childMeasureWidth = 0;
        int childMeasureHeight = 0;
        int layoutWidth = 0;    // 容器已经占据的宽度
        int layoutHeight = 0;   // 容器已经占据的宽度
        int maxChildHeight = 0; //一行中子控件最高的高度，用于决定下一行高度应该在目前基础上累加多少
        for(int i = 0; i<count; i++){
            View child = getChildAt(i);
            //注意此处不能使用getWidth和getHeight，这两个方法必须在onLayout执行完，才能正确获取宽高
            childMeasureWidth = child.getMeasuredWidth();
            childMeasureHeight = child.getMeasuredHeight();

//            排满后换行
            layoutWidth = 0;
            layoutHeight += maxChildHeight;
            maxChildHeight = 0;

            left = layoutWidth;
            right = left+childMeasureWidth;
            top = layoutHeight;
            bottom = top+childMeasureHeight;

            layoutWidth += childMeasureWidth;  //宽度累加
            if(childMeasureHeight>maxChildHeight){
                maxChildHeight = childMeasureHeight;
            }

            //确定子控件的位置，四个参数分别代表（左上右下）点的坐标值
            child.layout(left, top, right, bottom);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        int newHight=0;
        for (int i = 0; i < getChildCount() - noHideItemCount-1; i++) {
            newHight =newHight + getChildAt(i).getMeasuredHeight();
        }
        if (newHight>=mTopViewHeight){
            mTopViewHeight=newHight;
        }

//        View topview = getChildAt(1);
//        mTopViewHeight =topview.getMeasuredHeight();
    }

    /**，该方法决定了当前控件是否能接收到其内部View(非并非是直接子View)滑动时的参数；
     * 假设你只涉及到纵向滑动，这里可以根据nestedScrollAxes这个参数，进行纵向判断。
     * @param child
     * @param target
     * @param nestedScrollAxes
     * @return
     */
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
//        return false;
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }


    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {

    }

//    @Override
//    public void onStopNestedScroll(View target) {
//
//    }
//
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }


    /**该方法的会传入内部View移动的dx,dy，
     * 如果你需要消耗一定的dx,dy，
     * 就通过最后一个参数consumed进行指定，
     * 例如我要消耗一半的dy，就可以写consumed[1]=dy/2
     * @param target
     * @param dx
     * @param dy
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        boolean hiddenTop = dy > 0 && getScrollY() < mTopViewHeight;
        boolean showTop = dy < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(target, -1);

        if (hiddenTop || showTop)
        {
            scrollBy(0, dy);
            consumed[1] = dy;
        }

        if (dy>0){
            //隐藏抬头
            if (mFloatbar.getVisibility()==VISIBLE){
                mFloatbar.setVisibility(GONE);
            }
        }else {
            //显示抬头
            if (mFloatbar.getVisibility()==GONE){
                mFloatbar.setVisibility(VISIBLE);
            }
        }
    }

    /**onNestedFling你可以捕获对内部View的fling事件，
     * 如果return true则表示拦截掉内部View的事件。
     * @param target
     * @param velocityX
     * @param velocityY
     * @param consumed
     * @return
     */
    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        if (getScrollY() >= mTopViewHeight) return false;
        fling((int) velocityY);
        return true;
    }

    public void fling(int velocityY)
    {
//        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec=heightMeasureSpec+mTopViewHeight;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
//    @Override
//    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
//        return false;
//    }
//
//    @Override
//    public int getNestedScrollAxes() {
//        return 0;
//    }



    @Override
    public void scrollTo(int x, int y)
    {
        if (y < 0)
        {
            y = 0;
        }
        if (y > mTopViewHeight)
        {
            y = mTopViewHeight;
        }
        if (y != getScrollY())
        {
            mFloatbar.scrollTo(x, -y);
            super.scrollTo(x, y);
        }
    }
}

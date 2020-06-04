package com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper;

import android.content.Context;

import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

/**
 * className: InsideRecyclerView
 * description:java类作用描述
 * author：lix
 * email：lixuan_1@163.com
 * date: 2020/3/6 18:11
 */
public class InsideRecyclerView extends RecyclerView {
    public InsideRecyclerView(Context context) {
        super(context);
    }

    public InsideRecyclerView( Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InsideRecyclerView(Context context,  AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return false;
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;

    }
}

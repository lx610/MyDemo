package com.demo.lixuan.mydemo.animation.wight;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 类名： InterPolatorTextView
 * 说明：
 * <p>
 * 修改记录：
 * <p>
 * 版 权 所 有:   Copyright  2018
 * 公       司:   深圳市旅联网络科技有限公司
 * version   2.0
 * date   2018/5/15
 * author lixuan
 * Created by elk-lx on 2018/5/15.
 */

public class InterPolatorTextView extends TextView{
    Context mContext;
    String color;
    int leftMargin =0;
    public InterPolatorTextView(Context context) {
        super(context);
        init(context);
    }


    public InterPolatorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext=context;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        this.setBackgroundColor(Color.parseColor(color));
        invalidate();
    }

    public int getLeftMargin() {
        return leftMargin;
    }

    public void setLeftMargin(int leftMargin) {
        this.leftMargin = leftMargin;
        LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(leftMargin,10,10,10);
        setLayoutParams(params);
    }

    //
//    /**
//     * @param fraction 估值小数
//     * @param startValue 开始值
//     * @param endValue  结束值
//     * @return
//     */
////    @Override
////    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
////        int startInt = startValue;
////        return (int) (startInt + fraction * (endValue - startInt));
////    }
//
//    @Override
//    public Object evaluate(float fraction, Object startValue, Object endValue) {
//        LinearLayout.LayoutParams startParams = (LinearLayout.LayoutParams) startValue;
//        int startLeft = startParams.leftMargin;
//        LinearLayout.LayoutParams endParams = (LinearLayout.LayoutParams) endValue;
//        int endLeft = endParams.leftMargin;
//        int nowMarginLeft =(int) (startParams.leftMargin + fraction * (startLeft - endLeft));
//        LinearLayout.LayoutParams newParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        newParams.setMargins(nowMarginLeft,10,10,10);
//        return newParams;
//    }
}

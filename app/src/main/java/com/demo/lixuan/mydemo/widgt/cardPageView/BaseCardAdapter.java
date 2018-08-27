package com.demo.lixuan.mydemo.widgt.cardPageView;

import android.animation.AnimatorSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;

public abstract class BaseCardAdapter extends BaseAdapter {

    /**设置指定的position
     * @param topPosition
     */
    public abstract void refreshTopItemPosition(int topPosition);

    /**设置翻页动画
     * @param topView
     * @param event
     * @param downX
     * @param downY
     * @return
     */
    public abstract AnimatorSet getTurnPageAnimator(View topView, MotionEvent event, float downX, float downY);

    /**设置顶部view的onTouchEvent使能
     * @return
     */
    public abstract boolean getTurnPageDisEnable();

    /**设置移动顶部card的动作
     * @param topView
     * @param event
     * @param downX
     * @param downY
     * @return  返回true 启动view回弹归位，返回false 启动翻页
     */
    public abstract boolean moveTopView(View topView, MotionEvent event, float downX, float downY);

    /**设置顶部view回弹归位动作
     * @param topView
     * @param event
     * @param downX
     * @param downY
     */
    public abstract void rebackToView(View topView, MotionEvent event, float downX, float downY);

    /**设置item的点击事件使能
     * @param topView
     * @param ev
     * @param downX
     * @param downY
     * @return 返回fase 允许item 相应点击事件，返回true 拦截item的点击事件
     */
    public abstract boolean InterceptActionUp(View topView, MotionEvent ev, float downX, float downY);

    /**设置拦截Move动作
     * @param topView
     * @param ev
     * @param downX
     * @param downY
     * @return 返回ture 拦截，返回fase，不拦截
     */
    public abstract boolean InterceptActionMove(View topView, MotionEvent ev, float downX, float downY);


    public interface OnItemClickListener{
        void onItemClick(View TopView, int topPosition);
    }
    OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }
}

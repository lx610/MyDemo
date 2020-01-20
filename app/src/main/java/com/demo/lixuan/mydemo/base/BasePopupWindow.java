package com.demo.lixuan.mydemo.base;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Time:2019/5/29
 * <p>
 * Author:lixuan
 * <p>
 * version:3.1
 * <p>
 * Description: 弹窗基类
 */
public abstract class BasePopupWindow extends PopupWindow {

    public Activity mActivity;
    //是否允许点击弹窗外围关闭弹窗
    public boolean isEnbleOutsideColse = true;

    private View mContentView;

    OnWindowDismissListener mOnWindowDismissListener;
    //是否让外围屏幕变暗
    public boolean isNeedDarkScreen = true;

    public BasePopupWindow(Activity activity) {
//        super(activity);
        super(activity);
        this.mActivity = activity;
        this.init(activity);
    }


    /**获取布局
     * @return
     */
    protected abstract int getLayoutResID();

    /**在布局前做预处理的接口
     * @param currentActivity
     * @param rootView
     */
    protected abstract void preSetContentView(Activity currentActivity, View rootView);

    protected abstract void initView(View contentView);

    protected abstract void initListener(View contentView);

    private void init(Activity context) {
        mContentView = this.getPopContentView(context);
        mContentView.requestLayout();
        this.setContentView(mContentView);
        ViewGroup viewGrop = (ViewGroup)mActivity.getWindow().getDecorView();

        //是否开启点击外围让弹窗消失的功能
        if (this.isEnbleOutsideColse) {
            this.setFocusable(true);
            this.setBackgroundDrawable(new ColorDrawable(0));
            mContentView.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == 4) {
                        BasePopupWindow.this.dismiss();
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }else {//允许window下面的activity可以继续响应点击事件
            this.setFocusable(false);
            this.setBackgroundDrawable(null);
            mContentView.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == 4) {
                        BasePopupWindow.this.dismiss();
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }

        this.initView(mContentView);
        this.initListener(mContentView);
    }

    /**宽度设置LayoutParams.MATCH_PARENT，如果布局没有填满，点击两侧，不会触发点击外围消失的功能
     * 需要设置LayoutParams.WRAP_CONTENT为宽度，触发点击两侧退出，
     * 宽度在preSetContentView（）方法中自定义设置LayoutParams
     * @param activity
     * @return
     */
    private View getPopContentView(Activity activity) {
        View contentView = LayoutInflater.from(activity).inflate(this.getLayoutResID(), (ViewGroup)null, false);
        //设置这个，不然在有些手机上显示不出来
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.preSetContentView(activity, contentView);
        return contentView;
    }

    /**在中间展示
     */
    public void showAtCenter() {
        if (mActivity == null) {
            try {
                throw new Exception("activity is null,try show after Activity's onResum() method runed ");
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

        ViewGroup viewGrop = (ViewGroup)mActivity.getWindow().getDecorView();
        if (isNeedDarkScreen){
            makeSreenDark();
        }
        this.setOnDismissListener(new OnDismissListener() {
            public void onDismiss() {
                BasePopupWindow.this.doOnDismiss();
            }
        });
        this.showAtLocation(viewGrop, Gravity.CENTER, 0, 0);
        this.onWindowShowUp();
    }



    /**在顶部展示
     */
    public void showAtTop() {
        if (mActivity == null) {
            try {
                throw new Exception("activity is null,try show after Activity's onResum() method runed ");
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

        ViewGroup viewGrop = (ViewGroup)mActivity.getWindow().getDecorView();
        if (isNeedDarkScreen){
            makeSreenDark();
        }
        this.setOnDismissListener(new OnDismissListener() {
            public void onDismiss() {
                BasePopupWindow.this.doOnDismiss();
            }
        });
        this.showAtLocation(viewGrop, Gravity.TOP, 0, 0);
        this.onWindowShowUp();
    }



    /**在底部展示
     */
    public void showAtBottom() {
        if (mActivity == null) {
            try {
                throw new Exception("activity is null,try show after Activity's onResum() method runed ");
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }
        mContentView.getWidth();
        mContentView.getMeasuredWidth();
        ViewGroup.LayoutParams parms = mContentView.getLayoutParams();

        ViewGroup viewGrop = (ViewGroup)mActivity.getWindow().getDecorView();
        if (isNeedDarkScreen){
            makeSreenDark();
        }
        this.setOnDismissListener(new OnDismissListener() {
            public void onDismiss() {
                BasePopupWindow.this.doOnDismiss();
            }
        });
        this.showAtLocation(viewGrop, Gravity.BOTTOM, 0, 0);
        this.onWindowShowUp();
    }


    /**在对话框中弹出，在屏幕底部展示
     * @param dialog
     */
    public void showAtDailogBottom(Dialog dialog) {
        if (mActivity == null) {
            try {
                throw new Exception("activity is null,try show after Activity's onResum() method runed ");
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

        View viewGrop = dialog.getWindow().getDecorView();
        if (isNeedDarkScreen){
            makeSreenDark();
        }
        this.setOnDismissListener(new OnDismissListener() {
            public void onDismiss() {
                BasePopupWindow.this.doOnDismiss();
            }
        });
        this.showAtLocation(viewGrop, Gravity.BOTTOM, 0, 0);
        this.onWindowShowUp();
    }

    /**
     * 让弹窗外围变暗
     */
    private void makeSreenDark() {
//        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
//        //让屏幕变暗
//        mActivity.getWindow().addFlags(2);
//        lp.alpha = 0.7F;
//        mActivity.getWindow().setAttributes(lp);
        setBackgroundAlpha(0.7F);
    }


    /**
     * window 展示的时候的处理
     */
    public void onWindowShowUp() {
    }

    /**
     * 窗口消失响应
     */
    public void doOnDismiss() {
        //让屏幕恢复亮度
//        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
//        lp.alpha = 1.0F;
//        mActivity.getWindow().setAttributes(lp);
        setBackgroundAlpha(1.0F);
        if (mOnWindowDismissListener!=null){
            mOnWindowDismissListener.onDismiss();
        }
    }

    public void setOnWindowDismissListener(OnWindowDismissListener onWindowDismissListener) {
        mOnWindowDismissListener = onWindowDismissListener;
    }

    public interface OnWindowDismissListener{
        void onDismiss();
    }

    /**
     * 设置添加屏幕的背景透明度
     * <p>
     *
     * @param bgAlpha
     */
    public void setBackgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,可能出现黑屏的bug
        } else {
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        mActivity.getWindow().setAttributes(lp);
    }

}

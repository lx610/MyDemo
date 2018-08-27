package com.demo.lixuan.mydemo.widgt.cardPageView;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;


/**双选对话框
 * Created by lx on 2018/8/6.
 */

public class DoubleSelcetDailog extends PopupWindow {

    private TextView mTvAlertInfo;
    private TextView mTvCanel;
    private TextView mTvOk;
    OnCancelClickListener mOnCancelClickListener;
    Activity mActivity;

    public DoubleSelcetDailog(Activity activity) {
        super(activity);
        init(activity);

        mActivity = activity;
    }





    private void init(Activity activity) {
        LayoutInflater inflate =
                (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflate.from(activity).inflate(R.layout.view_double_select_dailogue_window_qb_1, null);
//        View contentView =  LayoutInflater.from(view_double_select_dailogue_window_qb_1).inflate(R.layout.view_double_select_dailogue_window_qb_1, null, false);
        setContentView(contentView);
        mTvAlertInfo = (TextView) contentView.findViewById(R.id.tv_alter_info);
        mTvCanel = (TextView) contentView.findViewById(R.id.tv_canel);
        mTvOk = (TextView) contentView.findViewById(R.id.tv_ok);

        setBackgroundDrawable(new ColorDrawable(0x00000000));//没错，这个设置点击对话框之外关闭对话框


        initListener();
    }

    public void setAlertText(String info){
        mTvAlertInfo.setText(info);
    }

    public void setOkButtonText(String text){
        mTvOk.setText(text);
    }



    public void setOkOnclickListener(View.OnClickListener listener){
        mTvOk.setOnClickListener(listener);
    }

    private void initListener() {
        mTvCanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }



    /**在屏幕中间弹出对话框
     * @param activiy
     */
    public void showAtCenter(final Activity activiy){
        if (activiy==null){
            try {
                throw new Exception("activity is null,try show after Activity's onResum() method runed ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ViewGroup viewGrop= (ViewGroup) mActivity.getWindow().getDecorView().findViewById(android.R.id.content);
        showAtLocation(viewGrop, Gravity.CENTER,0,0);

        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        //解决华为手机不变暗
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//多加这一句，问题就解决了！这句的官方文档解释是：让窗口背景后面的任何东西变暗
        lp.alpha = 0.7f;
        mActivity.getWindow().setAttributes(lp);



        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha = 1f;
                mActivity.getWindow().setAttributes(lp);
                if (mOnCancelClickListener!=null){
                    mOnCancelClickListener.onClick();
                }
            }
        });

    }

    public interface OnCancelClickListener{
        void onClick();
    }

    public void setOnDissmissListener(OnCancelClickListener onCancelClickListener) {
        mOnCancelClickListener = onCancelClickListener;
    }
}

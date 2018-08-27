package com.demo.lixuan.mydemo.widgt.pupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;


/**
 * 类 名: DeleteAlertWindow
 * 说 明: 删除提示对话框，有删除，和取消两个按钮，背景设置为灰色
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2017/3/13
 * author lixuan
 */

public class DeleteAlertWindow extends PopupWindow {
    Activity mActivity;
    private  TextView mTvDelete;
    private  TextView mTvCancel;
    private PopupWindow mMPopWindow;

    /**
     * 要在activity完成onResum后调用
     */
//    public DeleteAlertWindow() {
////        this.mActivity =activity;
//        WEApplication application= (WEApplication) UiUtils.getContext();
//        AppComponent app=application.getAppComponent();
//        mActivity =app.appManager().getCurrentActivity();
//        initWinidow();
//    }

    public DeleteAlertWindow(Activity activity) {
//        WEApplication application= (WEApplication) UiUtils.getContext();
//        AppComponent app=application.getAppComponent();
        mActivity =activity;
        initWinidow();
    }

    private void initWinidow() {
        LayoutInflater inflate =
                (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflate.from(mActivity).inflate(R.layout.popuwindow_delete_comment, null);
        mMPopWindow = new PopupWindow(contentView,
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        mMPopWindow.setContentView(contentView);
        mMPopWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//没错，这个设置点击对话框之外关闭对话框
        mTvDelete = (TextView) contentView.findViewById(R.id.tv_delete);
        mTvCancel = (TextView) contentView.findViewById(R.id.tv_cancel);

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMPopWindow.dismiss();
            }
        });
//        mMPopWindow.showAtLocation(container, Gravity.BOTTOM, 0, 0);
//
//        // 设置背景颜色变暗
//        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
//        lp.alpha = 0.7f;
//        mActivity. getWindow().setAttributes(lp);
//        mMPopWindow.setOnDismissListener(new OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
//                lp.alpha = 1f;
//                mActivity.getWindow().setAttributes(lp);
//            }
//        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mMPopWindow.dismiss();
    }

    public void showAtBottom(){

        mMPopWindow.showAtLocation(mActivity.getWindow().getDecorView().getRootView(), Gravity.BOTTOM, 0, 0);

        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        //解决华为手机不变暗
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//多加这一句，问题就解决了！这句的官方文档解释是：让窗口背景后面的任何东西变暗
        lp.alpha = 0.7f;
        mActivity. getWindow().setAttributes(lp);
        mMPopWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha = 1f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
    }



    public TextView getTvDelete() {
        return mTvDelete;
    }

    public void setTvDelete(TextView tvDelete) {
        mTvDelete = tvDelete;
    }

    public TextView getTvCancel() {
        return mTvCancel;
    }

    public void setTvCancel(TextView tvCancel) {
        mTvCancel = tvCancel;
    }
}

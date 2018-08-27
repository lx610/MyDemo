package com.demo.lixuan.mydemo.widgt.cardPageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.Utils.UiUtils;

/**
 * Created by lx on 2018/8/16.
 */

public class MyCardAdapter extends QuickCardHorizonSkipAdapter<String> {

    boolean isShowDelete;
    private OnDeleteCardListener mOnDeleteCardListener;
    private View mFlDetelet;

    public MyCardAdapter(Context context) {
        super(context);
    }


    @Override
    protected View getCardView(final int position,
                               View convertView, final ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_card_page, parent, false);
        }
        RelativeLayout relativeLayout=convertView.findViewById(R.id.rl_root);
        mFlDetelet=convertView.findViewById(R.id.fl_delete);

        TextView tv = (TextView) convertView.findViewById(R.id.textView1);
        final String text = getItem(position%mData.size());
        tv.setText(text);
        if (position%mData.size()==2){
            relativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.pink));
        }
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiUtils.makeText(position + text + "被点击");
            }
        });

        final View finalConvertView = convertView;
        relativeLayout.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
            @Override
            public void onDoubleClick() {
                mFlDetelet = finalConvertView.findViewById(R.id.fl_delete);
                UiUtils.makeText(position + text + "被双击了");
                setDeleteVisiabel(true);

                mFlDetelet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnDeleteCardListener!=null){
                            mOnDeleteCardListener.onClick(position, finalConvertView,parent);
                        }
                    }
                });

            }
        }));


        return convertView;
    }

    public void setDeleteVisiabel(boolean isVisiable){
        if (isVisiable){
            mFlDetelet.setVisibility(View.VISIBLE);
            isShowDelete =true;
        }else {
            mFlDetelet.setVisibility(View.GONE);
            isShowDelete =false;
        }
    }

    @Override
    public boolean getTurnPageDisEnable() {
        if (isShowDelete){
            return true;
        }else {
            return false;
        }
    }


    @Override
    public boolean InterceptActionMove(View topView, MotionEvent ev, float downX, float downY) {
        return true;
    }

    public interface OnDeleteCardListener{
        void onClick(int position, View convertView, ViewGroup parent);
    }

    public void setOnDeleteCardListener(OnDeleteCardListener onDeleteCardListener) {
        mOnDeleteCardListener = onDeleteCardListener;
    }
}

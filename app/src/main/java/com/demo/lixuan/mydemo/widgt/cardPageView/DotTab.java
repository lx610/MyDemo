package com.demo.lixuan.mydemo.widgt.cardPageView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.widgt.CircleImageView;


/**
 * 类 名: dotTab
 * author lixuan
 */

public class DotTab extends LinearLayout {
//   int ResHasSeleted= R.drawable.reward;
//   int ResNotSeleted= R.drawable.reward_a;
   int ResHasSeleted= R.drawable.shang_1a;
   int ResNotSeleted= R.drawable.c1_2a;
    Context mContext;
    private LinearLayout mLinearLayout;
    int mLastPosition =0;
    private dotOnclickListener mOnClickListner;


    public DotTab(Context context) {
        super(context);
        mContext=context;
        init(context);
    }

    public DotTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        init(context);
    }

    private void init(Context context) {
        View view=View.inflate(context, R.layout.activity_only_linearlayout,this);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.ll_container);
        mLinearLayout.setOrientation(HORIZONTAL);
        mLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
    }

    public void generateDots(int dots){
       generateDots(dots, ResHasSeleted, ResNotSeleted,16,10,10);
    }
    public void generateDots(int dots,int margtin,int with,int height){
        generateDots(dots, ResHasSeleted, ResNotSeleted,margtin,with,height);
    }

    public void generateDots(int dots,int ResSelectIamge,int NotSeleteImage,int marginRight,int with,int height){
        ResHasSeleted=ResSelectIamge;
        mLinearLayout.removeAllViews();
        for (int i = 0; i < dots; i++) {
            CircleImageView dot=new CircleImageView(mContext);
            LayoutParams params=new LayoutParams(with, height);
            params.setMargins(0,0,marginRight,0);
            dot.setLayoutParams(params);
//            dot.setImageResource(NotSeleteImage);
            dot.setBackgroundResource(NotSeleteImage);
            dot.setOnClickListener(dotListener);
            mLinearLayout.addView(dot,i);
        }
    }



    OnClickListener dotListener=new OnClickListener() {
        @Override
        public void onClick(View v) {
            int currentPosition = mLinearLayout.indexOfChild(v);
            changeFocusDot(currentPosition);
            mOnClickListner.onclick(currentPosition);
        }
    };

    private void changeFocusDot(int currentPosition) {
        ImageView imageView= (ImageView) mLinearLayout.getChildAt(currentPosition);
        if (currentPosition==mLastPosition){//初始化
            setOnChecked(imageView);
        }else {
            setOnChecked(imageView);
            setNotChecked((ImageView) mLinearLayout.getChildAt(mLastPosition));
        }

        mLastPosition =currentPosition;
    }

    public int getCurrentPositon() {
        return mLastPosition;
    }


    public void setDotOnClickListener(dotOnclickListener listener) {
        mOnClickListner=listener;
    }

    private void setNotChecked(ImageView imageView) {
//        imageView.setImageResource(ResNotSeleted);
        imageView.setBackgroundResource(ResNotSeleted);
    }

    private void setOnChecked(ImageView imageView) {
//        imageView.setImageResource(ResHasSeleted);
        imageView.setBackgroundResource(ResHasSeleted);
    }

    public void setPositionDotOnclick(int postion){
        if (mLinearLayout.getChildCount()>postion&&postion>=0){
            changeFocusDot(postion);
        }
    }

    public interface dotOnclickListener{
        void onclick(int currentPosition);
    }

}

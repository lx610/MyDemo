package com.demo.lixuan.mydemo.widgt;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.lixuan.mydemo.R;

/**
 * 类 名: TouchAndClickTestView
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2018/1/5
 * author lixuan
 */

public class TouchAndClickTestView extends FrameLayout {
    private static final String TAG = "TouchAndClickTestView";

    private FrameLayout mFlContainer;
    private TextView mTvLeft;
    private TextView mTvRight;
    private Context mContext;

    public TouchAndClickTestView(Context context) {
        super(context);
    }

    public TouchAndClickTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        View view=View.inflate(context, R.layout.view_touch_and_click,this);
        mFlContainer = view.findViewById(R.id.fl_container);
        mTvLeft = view.findViewById(R.id.tv_left);
        mTvRight = view.findViewById(R.id.tv_right);

        mFlContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"Fl容器被点击",Toast.LENGTH_SHORT);
                Log.d(TAG, "mFlContainer onClick:===============================    Fl容器被点击");
            }
        });

        mTvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"右边bt被点击",Toast.LENGTH_SHORT);
                Log.d(TAG, "mTvRight onClick:===============================    右边bt被点击");
            }
        });

        mFlContainer.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    Toast.makeText(mContext,"Fl被按下",Toast.LENGTH_SHORT);
                    Log.d(TAG, "mFlContainer onClick:===============================    Fl被按下");

                }

                return true;
            }
        });
    }
}

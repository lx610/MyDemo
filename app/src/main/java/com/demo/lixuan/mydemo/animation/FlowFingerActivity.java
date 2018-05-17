package com.demo.lixuan.mydemo.animation;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类名： FlowFingerActivity
 * 说明：
 * <p>
 * 修改记录：
 * <p>
 * 版 权 所 有:   Copyright  2018
 * 公       司:   深圳市旅联网络科技有限公司
 * version   2.0
 * date   2018/5/17
 * author lixuan
 * Created by elk-lx on 2018/5/17.
 */

public class FlowFingerActivity extends BaseActivity {
    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;
    private TextView mButton;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_only_linearlayout;
    }

    @Override
    public void initView() {
        mButton = (TextView) generateTextButton("flow finger button", new View.OnClickListener() {
              @Override
              public void onClick(View v) {

              }
          });
        mLlContainer.addView(mButton);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        mButton.setOnTouchListener(new View.OnTouchListener() {
            double lastX = mButton.getX();
            double lastY =  mButton.getY();

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                double x =event.getX();
                double y = event.getY();


                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                         lastX = x;
                         lastY = y;
                        break;
                    case MotionEvent.ACTION_MOVE:

                        double offX =  x - lastX;
                        double offY = y -lastY;

                        int newLeft = (int) (v.getLeft() + offX);
                        int newTop = (int) (v.getTop() + offY);
                        int newRight = (int) (v.getRight() +offX);
                        int newbottom= (int) (v.getBottom() + offY);

                        mButton.layout(newLeft,newTop,newRight,newbottom);
                        break;
                    case MotionEvent.ACTION_UP:
                        lastX = x;
                        lastY = y;
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

package com.demo.lixuan.mydemo.animation;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.Utils.UiUtils;
import com.demo.lixuan.mydemo.base.BaseActivity;

import java.util.HashMap;
import java.util.Iterator;

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
    private static final String TAG = "FlowFingerActivity";

    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;
    private TextView mButton;
    private TextView mButton2;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_only_linearlayout;
    }

    @Override
    public void initView() {
        mButton = (TextView) generateTextButton("pull to move", new View.OnClickListener() {
              @Override
              public void onClick(View v) {

              }
          });
        mButton2 = (TextView) generateTextButton("pull to move", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mLlContainer.addView(mButton);
        mLlContainer.addView(mButton2);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        makeViewFlowFinger(mButton);
        makeViewFlowFinger(mButton2);

//        mButton.setOnTouchListener(new View.OnTouchListener() {
//            double lastX = mButton.getX();
//            double lastY =  mButton.getY();
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                double x =event.getX();
//                double y = event.getY();
//
//
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                         lastX = x;
//                         lastY = y;
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//
//                        double offX =  x - lastX;
//                        double offY = y -lastY;
//
//                        int newLeft = (int) (v.getLeft() + offX);
//                        int newTop = (int) (v.getTop() + offY);
//                        int newRight = (int) (v.getRight() +offX);
//                        int newbottom= (int) (v.getBottom() + offY);
//
//                        mButton.layout(newLeft,newTop,newRight,newbottom);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        lastX = x;
//                        lastY = y;
//                        break;
//                }
//                return true;
//            }
//        });
//
//        mButton2.setOnTouchListener(new View.OnTouchListener() {
//            double lastX = mButton.getX();
//            double lastY =  mButton.getY();
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                double x =event.getX();
//                double y = event.getY();
//
//
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        lastX = x;
//                        lastY = y;
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//
//                        double offX =  x - lastX;
//                        double offY = y -lastY;
//
//                        int newLeft = (int) (v.getLeft() + offX);
//                        int newTop = (int) (v.getTop() + offY);
//                        int newRight = (int) (v.getRight() +offX);
//                        int newbottom= (int) (v.getBottom() + offY);
//
//                        mButton2.layout(newLeft,newTop,newRight,newbottom);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        lastX = x;
//                        lastY = y;
//                        break;
//                }
//                return true;
//            }
//        });
    }


    HashMap<View,LayoutParmsBean> map =new HashMap();
    private void makeViewFlowFinger(final View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            double lastX = view.getX();
            double lastY =  view.getY();


            int newLeft =view.getLeft();
            int newTop=view.getTop() ;
            int newRight=view.getRight();
            int newbottom = view.getBottom();

            int STATUS=0;

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
                        if (checkViewAttach(view)){//发生碰撞，则还原

                            LayoutParmsBean bean=map.get(view);
                            if (bean!=null){//首次移动为空
                                newLeft = bean.left;
                                newTop =  bean.top;
                                newRight =  bean.right;
                                newbottom=  bean.bottom;
                            }
                            view.layout(newLeft,newTop,newRight,newbottom);
                            break;
                        }
                        double offX =  x - lastX;
                        double offY = y -lastY;

                         newLeft = (int) (v.getLeft() + offX);
                         newTop = (int) (v.getTop() + offY);
                         newRight = (int) (v.getRight() +offX);
                         newbottom= (int) (v.getBottom() + offY);

                        view.layout(newLeft,newTop,newRight,newbottom);

                        break;
                    case MotionEvent.ACTION_UP:
                        lastX = x;
                        lastY = y;
                        saveParms(view,newLeft,newTop,newRight,newbottom);
                        break;
                }
                return true;
            }

            private void saveParms(View view, int newLeft, int newTop, int newRight, int newbottom) {
                map.put(view,new LayoutParmsBean(  newLeft,  newTop,  newRight,  newbottom));
            }

            /**如果和其他view 碰撞，返回true
             * @param view
             * @return
             */
            private boolean checkViewAttach(View view) {
               final int STATUS_LEFT_ATTACH = 8;//二进制1000
               final int STATUS_TOP_ATTACH = 8>>1;//二进制100
               final int STATUS_RIGHT_ATTACH = 8>>2;//二进制10
               final int STATUS_BOTTOM_ATTACT = 8>>3;//二进制1
               final int STATUS_WORK = 0;//二进制0



                Iterator iterator = map.keySet().iterator();
                while (iterator.hasNext()){
//                    LayoutParmsBean  layoutParmsBean = (LayoutParmsBean) iterator.next();
                    View viewKey= (View) iterator.next();
                    if (viewKey ==view){
                        continue;
                    }
                    LayoutParmsBean  layoutParmsBean =map.get(viewKey);
                    if (layoutParmsBean==null){
                        continue;
                    }

                    if (layoutParmsBean.left>view.getRight()||
                            layoutParmsBean.top>view.getBottom()||layoutParmsBean.right<view.getLeft()||layoutParmsBean.bottom<view.getTop()){
                        //如果取出的view，位于现在移动的view 的右边，下面，左边,下边，那么允许放置

                    }else {
                        if (layoutParmsBean.left<view.getRight()){
                            STATUS = STATUS |STATUS_RIGHT_ATTACH;
                        }
                        if ( layoutParmsBean.top<view.getBottom()){
                            STATUS = STATUS |STATUS_BOTTOM_ATTACT;
                        }

                        if (layoutParmsBean.right>view.getLeft()){
                            STATUS = STATUS |STATUS_LEFT_ATTACH;
                        }

                        if (layoutParmsBean.bottom>view.getTop()){
                            STATUS = STATUS |STATUS_TOP_ATTACH;
                        }


                        Log.d(TAG, "checkViewAttach: STATUS" + STATUS);
                        UiUtils.makeText("STATUS :" + STATUS);
                        return true;
                    }
                }
                return false;
            }
        });
    }



    public class LayoutParmsBean{
        int left;
        int top;
        int right;
        int bottom;

        public LayoutParmsBean(int newLeft, int newTop, int newRight, int newbottom) {
            left=newLeft;
            top= newTop;
            right =newRight;
            bottom =newbottom;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

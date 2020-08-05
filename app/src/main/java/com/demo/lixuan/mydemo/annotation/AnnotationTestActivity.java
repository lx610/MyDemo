package com.demo.lixuan.mydemo.annotation;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;


import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 类名： AnnotationTestActivity
 * 说明：
 * <p>
 * 修改记录：
 * <p>
 * 版 权 所 有:
 * 公       司:
 * version   2.0
 * date   2018/5/7
 * author lixuan
 * Created by elk-lx on 2018/5/7.
 */

public class AnnotationTestActivity extends BaseActivity {


    @BindView(R.id.ll_bt_container)
    GridLayout mLlBtContainer;
    @BindView(R.id.tv_log)
    TextView mTvLog;
    //这个bindview是自定义的
    @Override
    public int getLayoutResId() {
        return R.layout.activity_thread;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mLlBtContainer.addView(generateTextButton("show method value", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvLog.setText("");
                Method[] methods = AnnotationTest.class.getDeclaredMethods();
                for (Method m:methods){
                    GET get = m.getAnnotation(GET.class);
                    if (get!=null){
                        mTvLog.append(get.value() + "" + "\n");
                        System.out.println(get.value() + "");
                    }

                }
            }
        }));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

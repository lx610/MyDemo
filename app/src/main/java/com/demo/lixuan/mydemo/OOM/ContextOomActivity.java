package com.demo.lixuan.mydemo.OOM;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.lixuan.mydemo.OOM.oomGenerator.OomManager;
import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类名： ContextOomActivity
 * 说明：
 * <p>
 * 修改记录：
 * <p>
 * 版 权 所 有:   Copyright  2018
 * 公       司:   深圳市旅联网络科技有限公司
 * version   2.0
 * date   2018/4/18
 * author lixuan
 * Created by elk-lx on 2018/4/18.
 */

public class ContextOomActivity extends BaseActivity {

    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;
    @BindView(R.id.tv_text)
    TextView mTvText;
    private OomManager mManage;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_context_oom;
    }

    @Override
    public void initView() {
        mManage = OomManager.getInstence(ContextOomActivity.this);
        mManage.setOnThreadListener(new OomManager.OnThreadListener() {
            @Override
            public void refresh(final int i) {
                //当activity finish，子线程计数器没有结束，mTvText这里会报空指针
                //所以在manage中增加了stop标记，调用后终止循环执行。
                mTvText.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mTvText!=null){
                            mTvText.setText(i + "");
                        }else {
                            Log.d("ContextOomActivity", "run: ================" + "TextView报空指针了。");
                        }
                    }
                });
            }
        });


        mLlContainer.addView(generateTextButton("按下获取context,泄露内存", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mManage = OomManager.getInstence(ContextOomActivity.this);
                mManage.run();
            }
        }));
        mLlContainer.addView(generateTextButton("关闭activity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mManage.onDestory();
    }
}

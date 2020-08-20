package com.demo.lixuan.mydemo.widgt.lockview;

import android.os.Bundle;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;

import java.util.List;

/**
 * className: NineDotLockViewActivity
 * description:java类作用描述
 * author：lix
 * email：lixuan_1@163.com
 * date: 2020/8/20 10:09
 */
public class NineDotLockViewActivity extends BaseActivity {

    private TextView mTvResult;

    @Override
    public int getLayoutResId() {

        return R.layout.activity_nine_dot_lock_view;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mTvResult = findViewById(R.id.tv_result);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        LockPatternView  lockPatternView = (LockPatternView) findViewById(R.id.lock_pattern);
        lockPatternView.setOnPatternListener(new LockPatternView.OnPatternListener() {
            @Override
            public void onPatternStart() {

            }

            @Override
            public void onPatternCleared() {

            }

            @Override
            public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

            }

            @Override
            public void onPatternDetected(List<LockPatternView.Cell> pattern) {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < pattern.size(); i++) {
                    LockPatternView.Cell cell = pattern.get(i);

                    builder.append("[" + cell.column + "," + cell.row + "]");
                }
                mTvResult.setText(builder.toString());
                lockPatternView.clearPattern();
            }
        });
    }
}

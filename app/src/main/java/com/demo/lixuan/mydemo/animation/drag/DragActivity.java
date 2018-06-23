package com.demo.lixuan.mydemo.animation.drag;

import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/5.
 */

public class DragActivity extends BaseActivity {
    @BindView(R.id.root_grid_layout)
    GridLayout mRootGridLayout;
    private TextView mTextView1;
    private TextView mTextView9;

    @Override
    public int getLayoutResId() {
        return R.layout.layout_only_gridlayout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTextView1 = (TextView) generateTextButton("1",null);
        mTextView9 = (TextView) generateTextButton("9",null);
        mRootGridLayout.addView(mTextView1);

        mRootGridLayout.addView(generateTextButton("2",null));
        mRootGridLayout.addView(generateTextButton("3",null));
        mRootGridLayout.addView(generateTextButton("4",null));
        mRootGridLayout.addView(generateTextButton("5",null));
        mRootGridLayout.addView(generateTextButton("6",null));
        mRootGridLayout.addView(generateTextButton("7",null));
        mRootGridLayout.addView(generateTextButton("8",null));
        mRootGridLayout.addView(mTextView9);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        for (int i = 0; i < mRootGridLayout.getChildCount(); i++) {
            final TextView textView = (TextView) mRootGridLayout.getChildAt(i);
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    TextView tv= (TextView) v;
                    ClipData data =ClipData.newPlainText("value",  tv.getText());
                    v.startDrag(data,new View.DragShadowBuilder(v),v,0);
                    return true;
                }
            });

            textView.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent event) {
                    switch (event.getAction()) {

                        case DragEvent.ACTION_DRAG_STARTED:
                            return true;

                        case DragEvent.ACTION_DRAG_ENTERED:
                            v.setBackgroundColor(Color.CYAN);
                            return true;

                        case DragEvent.ACTION_DRAG_LOCATION:
                            return true;

                        case DragEvent.ACTION_DRAG_EXITED:
                            v.setBackgroundColor(Color.RED);
                            return true;

                        case DragEvent.ACTION_DROP:
                            v.setBackgroundColor(Color.GREEN);
                            //得到拖动的值
                            int dragVal = Integer.parseInt(event.getClipData().getItemAt(0).getText().toString());
                            //得到textview的值
                            int viewVal = Integer.parseInt(((TextView) v).getText().toString());
                            ((TextView) v).setText("" + (dragVal + viewVal));
                            return true;

                        case DragEvent.ACTION_DRAG_ENDED:
                            return true;
                    }

                    return false;
                }
            });

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

package com.demo.lixuan.mydemo.widgt.button;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;
import com.demo.lixuan.mydemo.widgt.ClickDivideButton;
import com.demo.lixuan.mydemo.widgt.DivideButton;
import com.demo.lixuan.mydemo.widgt.TextDivideButton;
import com.demo.lixuan.mydemo.widgt.cardPageView.DoubleSelcetDailog;
import com.demo.lixuan.mydemo.widgt.pupwindow.DeleteAlertWindow;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类 名: ButtonActivity
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2017/12/26
 * author lixuan
 */

public class ButtonActivity extends BaseActivity {
    private static final String TAG = "ButtonActivity";

    @BindView(R.id.bt_divideButton)
    DivideButton mBtDivideButton;
    @BindView(R.id.bt_click_show)
    ClickDivideButton mBtClickShow;
    @BindView(R.id.bt_text_divid)
    TextDivideButton mBtTextDivid;


    String code = "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";
    @BindView(R.id.iv_image)
    ImageView mIvImage;
    private String mDecodePath;
    private DoubleSelcetDailog mWindow;
    private DeleteAlertWindow mDeleteAlertWindow;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_widgt;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mWindow = new DoubleSelcetDailog(this);
        mWindow.setAlertText("asdfsadfasdfsadfsdfasfdf");

        mDeleteAlertWindow = new DeleteAlertWindow(this);
    }

    @Override
    public void initData() {
        mIvImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteAlertWindow.showAtBottom();
            }
        });
    }


    private void saveBitmap(Bitmap bitmap) {
        try {
            mDecodePath = Environment.getExternalStorageDirectory().getPath()
                    + "/decodeImage.jpg";
            Log.d("linc", "path is " + mDecodePath);
            OutputStream stream = new FileOutputStream(mDecodePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            stream.close();
            Log.e("linc", "jpg okay!");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("linc", "failed: " + e.getMessage());
        }
    }

    @Override
    public void initListener() {
        mBtDivideButton.setOnStateChangedListener(new DivideButton.OnStateChangedListener() {
            @Override
            public void onStateChanged(boolean state) {
                // TODO Auto-generated method stub
                if (true == state) {
                    Toast.makeText(ButtonActivity.this, "开关已打开", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ButtonActivity.this, "开关已关闭", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mBtClickShow.setAnimationTime(500);
        mBtClickShow.setLeftSideColor(Color.GREEN);


        mBtClickShow.setOnSideClcikListener(new ClickDivideButton.OnSideClcikListener() {
            @Override
            public void onClickListener(boolean isLeftSide) {
                if (isLeftSide) {
                    Toast.makeText(ButtonActivity.this, "左侧被点击", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ButtonActivity.this, "右侧被点击", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBtClickShow.setOnStateChangedListener(new ClickDivideButton.OnStateChangedListener() {
            @Override
            public void onStateChanged(boolean isShowRight) {
                if (isShowRight) {
                    Toast.makeText(ButtonActivity.this, "展开右侧", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ButtonActivity.this, "展开左侧", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBtTextDivid.setOnSideClcikListener(new TextDivideButton.OnSideClcikListener() {
            @Override
            public void onClickListener(boolean isLeftSide) {
                if (isLeftSide) {
                    Toast.makeText(ButtonActivity.this, "左侧被点击", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ButtonActivity.this, "右侧被点击", Toast.LENGTH_SHORT).show();
                }
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

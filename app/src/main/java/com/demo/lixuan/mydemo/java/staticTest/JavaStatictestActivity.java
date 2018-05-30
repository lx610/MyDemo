package com.demo.lixuan.mydemo.java.staticTest;

import android.view.View;
import android.widget.TextView;

import com.demo.lixuan.mydemo.base.publicLayout.LinearActivity;

/**
 * Created by Administrator on 2018/5/30.
 */

public class JavaStatictestActivity extends LinearActivity {

    private TextView mLogText;
    OutClass outClass = new OutClass();
    OutClass.inerClass inerClass = outClass.new inerClass();

    @Override
    public void initView() {
        mLogText = (TextView) generateTextButton("", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

        mLlContainer.addView(generateTextButton("outclass add number", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printLog(outClass.addNum() + "");
            }
        }));

        mLlContainer.addView(generateTextButton("outclass sub number", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printLog(  outClass.subNum() + "");

            }
        }));

        mLlContainer.addView(generateTextButton("inerClass add number", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printLog(   inerClass.addNum() + "");

            }
        }));

        mLlContainer.addView(generateTextButton("inerClass sub number", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                printLog( inerClass.subNum() + "");
            }
        }));

        mLlContainer.addView(mLogText);
    }

    public void printLog(String  log){
        mLogText.append(log + "\n");
    }



}

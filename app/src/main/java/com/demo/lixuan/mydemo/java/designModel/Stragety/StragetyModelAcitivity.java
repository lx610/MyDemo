package com.demo.lixuan.mydemo.java.designModel.Stragety;

import android.os.Bundle;
import android.view.View;

import com.demo.lixuan.mydemo.Utils.UiUtils;
import com.demo.lixuan.mydemo.base.publicLayout.LinearActivity;

/**
 * Created by Administrator on 2018/6/6.
 */

public class StragetyModelAcitivity extends LinearActivity{
    Fighter mFighter =new Fighter();
    JiuYinZhengJing mJiuYinZhengJing = new JiuYinZhengJing();
    KuiHuaBaoDian mKuiHuaBaoDian = new KuiHuaBaoDian();

    @Override
    public void initView(Bundle savedInstanceState) {
        mLlContainer.addView(generateTextButton(" 学习九阴真经", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFighter.learnFight(mJiuYinZhengJing);
                mFighter.fight();
            }
        }));

        mLlContainer.addView(generateTextButton("学习葵花宝典", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFighter.learnFight(mKuiHuaBaoDian);
                mFighter.fight();
            }
        }));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    public class JiuYinZhengJing implements FightStragety{

        @Override
        public void fight() {
            UiUtils.makeText("打出 九阴真经");
        }
    }

    public class KuiHuaBaoDian implements FightStragety{

        @Override
        public void fight() {
            UiUtils.makeText("打出 葵花宝典");
        }
    }

}

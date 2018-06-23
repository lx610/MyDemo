package com.demo.lixuan.mydemo.java.designModel.shareObject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.demo.lixuan.mydemo.Utils.UiUtils;
import com.demo.lixuan.mydemo.base.publicLayout.LinearActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2018/6/7.
 */

public class FlyWigthDesignModelActivity extends LinearActivity {
    private static FlyWeightFactory factory ;
    private Menu mList;
    private List mList1;

    @Override
    public void initView(Bundle savedInstanceState) {
        final TextView logText = (TextView) generateTextButton("",null);

        mLlContainer.addView(generateTextButton("打印菜单数量", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.makeText(factory.getNumber() + "");
            }
        }));
        mLlContainer.addView(generateTextButton("打印菜单名称", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List list2 = mList.findPersonMenu("ai921",mList1);
                Iterator it = list2.iterator();
                while(it.hasNext())
                {
                    logText.append(" "+it.next() + "\n");
                }

            }
        }));
        mLlContainer.addView(logText);
    }

    @Override
    public void initData() {
        mList1 = new ArrayList();
        factory = FlyWeightFactory.getInstance();
        mList = factory.factory("尖椒土豆丝");
        mList.setPersonMenu("ai92", mList1);
        mList = factory.factory("红烧肉");
        mList.setPersonMenu("ai92", mList1);
        mList = factory.factory("地三鲜");
        mList.setPersonMenu("ai92", mList1);
        mList = factory.factory("地三鲜");
        mList.setPersonMenu("ai92", mList1);
        mList = factory.factory("红焖鲤鱼");
        mList.setPersonMenu("ai92", mList1);
        mList = factory.factory("红烧肉");
        mList.setPersonMenu("ai921", mList1);
        mList = factory.factory("红焖鲤鱼");
        mList.setPersonMenu("ai921", mList1);
        mList = factory.factory("地三鲜");
        mList.setPersonMenu("ai921", mList1);
    }

    @Override
    public void initListener() {

    }
}

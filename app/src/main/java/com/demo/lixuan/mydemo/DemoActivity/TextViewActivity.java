package com.demo.lixuan.mydemo.DemoActivity;

import android.os.Bundle;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;
import com.demo.lixuan.mydemo.widgt.textView.adapter.TextSpanBean;
import com.demo.lixuan.mydemo.widgt.textView.adapter.TextSpanListAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * className: TextViewActivity
 * description:java类作用描述
 * author：lix
 * email：lixuan_1@163.com
 * date: 2020/5/14 14:04
 */
public class TextViewActivity extends BaseActivity {

    @Override
    public int getLayoutResId() {

        return R.layout.activity_text_view;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        RecyclerView recyclerView  = findViewById(R.id.list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<TextSpanBean> list = new ArrayList<>();
        List<TextSpanBean> textList = generateTextList();
        list.addAll(textList);
        TextSpanListAdapter textViewAdapter = new TextSpanListAdapter(list);
        recyclerView.setAdapter(textViewAdapter);
    }

    private List<TextSpanBean> generateTextList() {

        List<TextSpanBean> newList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            TextSpanBean textSpanBean = new TextSpanBean();
            textSpanBean.setContentText(getString(R.string.span_test_text));
            textSpanBean.setTagText("标记" + i);
            textSpanBean.setClickToast("点击了" + i);
            newList.add(textSpanBean);

        }

        return newList;
    }


    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}

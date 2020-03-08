package com.demo.lixuan.mydemo.widgt.calendar.recycleCalenarView;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;

/**
 * 类名： RecyclerCalenarView
 * 说明：
 * <p>
 * 修改记录：
 * <p>
 * 版 权 所 有:   Copyright  2018
 * 公       司:   深圳市旅联网络科技有限公司
 * version   2.0
 * date   2018/4/20
 * author lixuan
 * Created by elk-lx on 2018/4/20.
 */

public class RecyclerCalenarView extends LinearLayout {
    String[] week = {"一","二","三","四","五","六","日"};
    public RecyclerCalenarView(Context context) {
        super(context);
        init(context);
    }


    public RecyclerCalenarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View rootView=View.inflate(context,R.layout.view_recycler_calenar,this);
        LinearLayout weekContainer=rootView.findViewById(R.id.ll_week_text_container);
        for (int i = 0; i < 7; i++) {
            weekContainer.addView(generateWeekText(context,week[i]));
        }
        RecyclerView monthList=rootView.findViewById(R.id.rv_month);

        LinearLayoutManager manager=new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        monthList.setLayoutManager(manager);

        CalenarInfoCotroller calenarInfoCotroller=new CalenarInfoCotroller();
        MonthListAdapter adapter=new MonthListAdapter(context,calenarInfoCotroller);
        monthList.setAdapter(adapter);

    }

    private View generateWeekText(Context context,String week) {
        TextView textView=new TextView(context);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight=1;
        textView.setLayoutParams(params);
        textView.setText(week);
        return textView;
    }


}

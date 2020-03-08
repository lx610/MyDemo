package com.demo.lixuan.mydemo.widgt.calendar.recycleCalenarView;

import android.content.Context;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.lixuan.mydemo.R;

import java.util.Calendar;
import java.util.List;

/**
 * 类名： MonthListAdapter
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

public class MonthListAdapter extends RecyclerView.Adapter<MonthListAdapter.MonthView> {
    CalenarInfoCotroller calenarInfoCotroller;
    Context mContext;
    public MonthListAdapter(Context context,CalenarInfoCotroller calenarInfoCotroller) {
        this.calenarInfoCotroller=calenarInfoCotroller;
        this.mContext=context;
    }

    @Override
    public MonthView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(mContext, R.layout.view_day_list,null);
        MonthView monthView=new MonthView(view);
        return monthView;
    }

    @Override
    public void onBindViewHolder(MonthView holder, int position) {
      List<Calendar> dayList= calenarInfoCotroller.getDayListInMonth(position);
        GridLayoutManager manager=new GridLayoutManager(mContext,7);
        holder.mDaylist.setLayoutManager(manager);
        int offset=computerFirstOffset(position);
        DayAdapter adapter=new DayAdapter(mContext,dayList,offset);
        holder.mDaylist.setAdapter(adapter);
    }

    public int computerFirstOffset(int position){//计算第一天前面的空缺
        List<Calendar> dayList= calenarInfoCotroller.getDayListInMonth(position);
        int i = dayList.get(0).get(Calendar.DAY_OF_WEEK);
//        int firstDayOfWeek = dayList.get(0).getFirstDayOfWeek();
//        if (i < firstDayOfWeek) {
//            i += 7;
//        }
//        return i - firstDayOfWeek;
        return i;
    }

    @Override
    public int getItemCount() {
        return calenarInfoCotroller.getTotallMoth();
    }

    class MonthView extends BaseViewHolder{

        public RecyclerView mDaylist;

        public MonthView(View view) {
            super(view);
            mDaylist = view.findViewById(R.id.rv_day_list);


        }
    }
}

package com.demo.lixuan.mydemo.widgt.calendar.recycleCalenarView;

import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.lixuan.mydemo.R;

import java.util.Calendar;
import java.util.List;

/**
 * 类名： DayAdapter
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

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayHolder> {
    private int rootWidth;
    List<Calendar> dayList;
    int firstOffset;//第一天前面的空位

    Context mContext;
    public DayAdapter(Context context, List<Calendar> dayList, int offset) {
        mContext=context;
        this.dayList=dayList;

        DisplayMetrics dm = new DisplayMetrics();
        Activity activity=(Activity)mContext;
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        rootWidth=screenWidth/7;

        this.firstOffset=offset;
    }

    @Override
    public DayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.view_day,null);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(rootWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        DayHolder holder=new DayHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DayHolder holder, int position) {
        if (position>=firstOffset){
            Calendar day=dayList.get(position-firstOffset);
            holder.mTextView.setText(day.get(Calendar.DATE) + "");
        }else {
            holder.mTextView.setText( "");
        }
    }

    @Override
    public int getItemCount() {
        return dayList.size() + firstOffset;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class DayHolder extends BaseViewHolder {
        TextView mTextView;
        public DayHolder(View view) {
            super(view);
            mTextView=view.findViewById(R.id.tv_text);

        }
    }
}

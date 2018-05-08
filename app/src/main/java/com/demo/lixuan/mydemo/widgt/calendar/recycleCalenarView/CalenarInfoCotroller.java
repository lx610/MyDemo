package com.demo.lixuan.mydemo.widgt.calendar.recycleCalenarView;

import com.demo.lixuan.mydemo.Utils.TimeUtil;
import com.demo.lixuan.mydemo.Utils.UiUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 类名： CalenarInfoCotroller
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

public class CalenarInfoCotroller {

    private Calendar mToday;
    List<List<Calendar>> monthList=new ArrayList<>();

    public CalenarInfoCotroller() {
        Date mTodayDate= TimeUtil.getNowTimeDate();
        mToday = Calendar.getInstance();
        mToday.setTime(mTodayDate);
        mToday.getTime();
        generateDayList(mToday);
    }

    private void generateDayList(Calendar today) {
        int month = today.get(Calendar.MONTH);
        int year=today.get(Calendar.YEAR);
        for (int i = 0; i < 4; i++) {
            int days=TimeUtil.getMonthLastDay(year,month);
            List<Calendar> dayList=new ArrayList<>();
            for (int j = 0; j < days; j++) {
                Calendar day=Calendar.getInstance();
                day.set(Calendar. DAY_OF_WEEK, Calendar.MONDAY);//以周一作为一周的开始
                day.set(year,month,j +1);
                dayList.add(day);
            }
            monthList.add(i,dayList);
            ++month;
            if (month>12){
                month=1;
                ++year;
            }
        }
    }


    public List<Calendar> getDayListInMonth(int position) {
        if (position>monthList.size()-1){
            UiUtils.makeText("没有这么多月份");
            return null;
        }else {
            return monthList.get(position);
        }
    }

    public int getTotallMoth() {
        return monthList.size();
    }
}

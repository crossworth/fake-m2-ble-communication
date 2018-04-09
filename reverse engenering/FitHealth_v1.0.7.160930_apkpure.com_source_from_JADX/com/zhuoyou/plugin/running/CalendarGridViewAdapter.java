package com.zhuoyou.plugin.running;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import com.zhuoyou.plugin.view.CalView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarGridViewAdapter extends BaseAdapter {
    private Calendar calSelected = Calendar.getInstance();
    private Calendar calStartDate = Calendar.getInstance();
    private Calendar calToday = Calendar.getInstance();
    private String currDate = "";
    private Calendar firstCalendar = Calendar.getInstance();
    private Calendar lastCalendar = Calendar.getInstance();
    private Context mContext;
    private List<Integer> mDays = null;
    private int[] mHDate = null;
    private DisplayMetrics metric;
    private Resources resources;
    ArrayList<Date> titles;

    public void setSelectedDate(Calendar cal) {
        this.calSelected = cal;
    }

    private void UpdateStartDateForMonth() {
        this.calStartDate.set(5, 1);
        if (this.mHDate != null) {
            this.firstCalendar.setTime(this.calStartDate.getTime());
            this.lastCalendar.setTime(this.calStartDate.getTime());
            this.firstCalendar.add(2, -1);
            this.lastCalendar.add(2, 2);
            int year_s = this.firstCalendar.get(1);
            int month_s = this.firstCalendar.get(2) + 1;
            int startTime = ((year_s * 10000) + (month_s * 100)) + this.firstCalendar.get(5);
            int year_e = this.lastCalendar.get(1);
            int month_e = this.lastCalendar.get(2) + 1;
            int endTime = ((year_e * 10000) + (month_e * 100)) + this.lastCalendar.get(5);
            this.mDays = new ArrayList();
            int i = 0;
            while (i < this.mHDate.length) {
                if (startTime <= this.mHDate[i] && this.mHDate[i] <= endTime) {
                    this.mDays.add(Integer.valueOf(this.mHDate[i]));
                } else if (endTime < this.mHDate[i]) {
                    break;
                }
                i++;
            }
        }
        int iDay = 0;
        int iStartDay = 2;
        if (iStartDay == 2) {
            iDay = this.calStartDate.get(7) - 2;
            if (iDay < 0) {
                iDay = 6;
            }
        }
        if (iStartDay == 1) {
            iDay = this.calStartDate.get(7) - 1;
            if (iDay < 0) {
                iDay = 6;
            }
        }
        this.calStartDate.add(7, -iDay);
        this.calStartDate.add(5, -1);
    }

    private ArrayList<Date> getDates() {
        UpdateStartDateForMonth();
        ArrayList<Date> alArrayList = new ArrayList();
        for (int i = 1; i <= 42; i++) {
            Date mdate = this.calStartDate.getTime();
            mdate.setTime(mdate.getTime() + (86400000 * ((long) (i - 1))));
            alArrayList.add(mdate);
        }
        return alArrayList;
    }

    public CalendarGridViewAdapter(Context con, Calendar cal, int[] days, String date, DisplayMetrics met) {
        this.calStartDate = cal;
        this.mContext = con;
        this.resources = this.mContext.getResources();
        this.currDate = date;
        this.mHDate = days;
        this.titles = getDates();
        this.metric = met;
    }

    public CalendarGridViewAdapter(Context a) {
        this.mContext = a;
        this.resources = this.mContext.getResources();
    }

    public int getCount() {
        return this.titles.size();
    }

    public Object getItem(int position) {
        return this.titles.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        int textColor;
        Date myDate = (Date) getItem(position);
        int i = this.metric.widthPixels / 7;
        boolean isShowCricle = false;
        boolean isShowBack = false;
        int day = Tools.DataToInteger(myDate).intValue();
        int calDay = Tools.DataToInteger(this.calToday.getTime()).intValue();
        if (equalsDate(Tools.stringToDate(this.currDate), myDate).booleanValue()) {
            isShowBack = true;
        }
        if (this.mHDate.length > 0) {
            if (day == calDay) {
                textColor = 1;
            } else if (this.mHDate[0] > day || day >= calDay) {
                textColor = 3;
            } else {
                textColor = 2;
            }
        } else if (day == calDay) {
            textColor = 1;
        } else {
            textColor = 3;
        }
        for (int y = 0; y < this.mDays.size(); y++) {
            if (day == ((Integer) this.mDays.get(y)).intValue()) {
                isShowCricle = true;
                break;
            }
        }
        View calView = new CalView(this.mContext, String.valueOf(myDate.getDate()), isShowCricle, isShowBack, textColor, i);
        calView.setLayoutParams(new LayoutParams(-1, i));
        calView.setId(position + 5000);
        calView.setTag(myDate);
        return calView;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    private Boolean equalsDate(Date date1, Date date2) {
        boolean rs1 = false;
        if (date1.getTime() / 86400000 == date2.getTime() / 86400000) {
            rs1 = true;
        }
        return Boolean.valueOf(rs1);
    }
}

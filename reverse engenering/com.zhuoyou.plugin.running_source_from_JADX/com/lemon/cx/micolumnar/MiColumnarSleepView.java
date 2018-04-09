package com.lemon.cx.micolumnar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MiColumnarSleepView extends MiColumnarView<MiColumnarSleepData> {
    private static final int ONE_DAY = 86400000;
    private static final String TAG = "MiColumnarSleepView";
    private static final SimpleDateFormat format = new SimpleDateFormat("MM/dd", Locale.CHINA);
    private int maxDay = 60;
    private int maxMonth = 10;
    private int maxWeek = 20;
    private String[] monthStr;
    private Resources res;
    private String[] weekStr;

    class C10821 implements Runnable {
        C10821() {
        }

        public void run() {
            MiColumnarSleepView.this.doListener(0);
        }
    }

    public MiColumnarSleepView(Context context) {
        super(context);
        init(context);
    }

    public MiColumnarSleepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.res = context.getResources();
        this.weekStr = this.res.getStringArray(C1085R.array.week);
        this.monthStr = this.res.getStringArray(C1085R.array.month);
        setColorBootom(Color.parseColor("#f9f6fb"));
        setColorTarget(Color.parseColor("#9d73f1"));
        setColorTargetSelect(Color.parseColor("#b899f6"));
        setColorValue(Color.parseColor("#ca9fff"));
        setColorValueSelect(Color.parseColor("#e2cbff"));
    }

    public void setData(List<MiColumnarSleepData> list) {
        if (list.size() > 0) {
            Log.i("zhuqichao", "list.size=" + list.size());
            List<MiColumnarSleepData> dayList = getDayList(list);
            List<MiColumnarSleepData> weekList = getWeekList(dayList);
            List<MiColumnarSleepData> monthList = getMonthList(dayList);
            setDayData(dayList.subList(Math.max(dayList.size() - this.maxDay, 0), dayList.size()));
            setWeekData(weekList.subList(Math.max(weekList.size() - this.maxWeek, 0), weekList.size()));
            setMonthData(monthList.subList(Math.max(monthList.size() - this.maxMonth, 0), monthList.size()));
            refreshSize();
            postInvalidate();
            post(new C10821());
        }
    }

    private ArrayList<MiColumnarSleepData> getDayList(List<MiColumnarSleepData> list) {
        int i;
        Collections.sort(list);
        ArrayList<MiColumnarSleepData> dayList = new ArrayList();
        int dayCount = getDayCount(list);
        Log.i("zhuqichao", "dayCount=" + dayCount);
        for (i = 0; i < dayCount; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(6, (i + 1) - dayCount);
            dayList.add(new MiColumnarSleepData(calendar, formatDate(calendar, 0), 0, 0));
        }
        i = 0;
        int j = 0;
        while (i < dayList.size() && j < list.size()) {
            MiColumnarSleepData item = (MiColumnarSleepData) list.get(j);
            MiColumnarSleepData day = (MiColumnarSleepData) dayList.get(i);
            if (Tools.inSameDay(day.getDate(), item.getDate())) {
                day.setTarget(item.getTarget());
                day.setValue(item.getValue());
                i++;
                j++;
            } else if (item.getDate().before(day.getDate())) {
                j++;
            } else {
                i++;
            }
        }
        return dayList;
    }

    private ArrayList<MiColumnarSleepData> getWeekList(List<MiColumnarSleepData> dayList) {
        ArrayList<MiColumnarSleepData> weekList = new ArrayList();
        MiColumnarSleepData tmpData = new MiColumnarSleepData((MiColumnarSleepData) dayList.get(0));
        tmpData.setLabel(formatDate(tmpData.getDate(), 1));
        for (int i = 1; i < dayList.size(); i++) {
            MiColumnarSleepData item = (MiColumnarSleepData) dayList.get(i);
            if (Tools.inSameWeek(tmpData.getDate(), item.getDate())) {
                tmpData.setTarget(tmpData.getTarget() + item.getTarget());
                tmpData.setValue(tmpData.getValue() + item.getValue());
            } else {
                weekList.add(tmpData);
                tmpData = new MiColumnarSleepData(item);
                tmpData.setLabel(formatDate(tmpData.getDate(), 1));
            }
        }
        weekList.add(tmpData);
        return weekList;
    }

    private ArrayList<MiColumnarSleepData> getMonthList(List<MiColumnarSleepData> dayList) {
        ArrayList<MiColumnarSleepData> monthList = new ArrayList();
        MiColumnarSleepData tmpData = new MiColumnarSleepData((MiColumnarSleepData) dayList.get(0));
        tmpData.setLabel(formatDate(tmpData.getDate(), 2));
        for (int i = 1; i < dayList.size(); i++) {
            MiColumnarSleepData item = (MiColumnarSleepData) dayList.get(i);
            if (Tools.inSameMonth(tmpData.getDate(), item.getDate())) {
                tmpData.setTarget(tmpData.getTarget() + item.getTarget());
                tmpData.setValue(tmpData.getValue() + item.getValue());
            } else {
                monthList.add(tmpData);
                tmpData = new MiColumnarSleepData(item);
                tmpData.setLabel(formatDate(tmpData.getDate(), 2));
            }
        }
        monthList.add(tmpData);
        return monthList;
    }

    private int getDayCount(List<MiColumnarSleepData> list) {
        long time = System.currentTimeMillis() - ((MiColumnarSleepData) list.get(0)).getDate().getTimeInMillis();
        int day = ((int) (time / LogBuilder.MAX_INTERVAL)) + (time % LogBuilder.MAX_INTERVAL == 0 ? 1 : 2);
        if (day > 365) {
            return 365;
        }
        return day;
    }

    private String formatDate(Calendar calendar, int type) {
        String value = "00/00";
        switch (type) {
            case 0:
                if (Tools.inSameDay(calendar, Calendar.getInstance())) {
                    return this.res.getString(C1085R.string.today);
                }
                if (Tools.inSameWeek(calendar, Calendar.getInstance())) {
                    return this.weekStr[calendar.get(7) - 1];
                }
                return format.format(calendar.getTime());
            case 1:
                if (Tools.inSameWeek(calendar, Calendar.getInstance())) {
                    return this.res.getString(C1085R.string.thisWeek);
                }
                return this.res.getString(C1085R.string.week_num, new Object[]{Integer.valueOf(calendar.get(3))});
            case 2:
                if (Tools.inSameMonth(calendar, Calendar.getInstance())) {
                    return this.res.getString(C1085R.string.thisMonth);
                }
                return this.monthStr[calendar.get(2)];
            default:
                return value;
        }
    }
}

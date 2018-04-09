package com.zhuoyou.plugin.running;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import com.zhuoyou.plugin.database.DataBaseContants;
import java.util.ArrayList;
import java.util.List;

public class DataStatsCenter {
    private int caloriesAddSport;
    private List<StatsItem> dailyStatses = new ArrayList();
    private Context mContext;
    private List<StatsItem> monthlyStatses;
    private List<StatsItem> weeklyStatses;

    public DataStatsCenter(Context context) {
        this.mContext = context;
        String today = Tools.getDate(0);
        String enter_day = today;
        List<String> date_list = HomePageFragment.mInstance.getDateList();
        if (date_list != null && date_list.size() > 0) {
            enter_day = (String) date_list.get(0);
        }
        int count = Tools.getDayCount(enter_day, today, "yyyy-MM-dd");
        ContentResolver cr = this.mContext.getContentResolver();
        for (int i = 0; i < count; i++) {
            StatsItem statsdate = new StatsItem();
            String day = Tools.getDate(enter_day, 0 - i);
            if (date_list == null || date_list.size() <= 0 || date_list.indexOf(day) == -1) {
                statsdate.setDate(day);
                statsdate.setCalories(0);
                statsdate.setSteps(0);
                statsdate.setMeter(0);
            } else {
                initAddSport(day);
                Cursor c = cr.query(DataBaseContants.CONTENT_URI, new String[]{"_id", "steps", DataBaseContants.CALORIES, DataBaseContants.KILOMETER}, "date  = ? AND statistics = ? ", new String[]{day, "1"}, null);
                c.moveToFirst();
                if (c.getCount() <= 0 || !c.moveToFirst() || c.getLong(c.getColumnIndex("_id")) <= 0) {
                    statsdate.setDate(day);
                    statsdate.setCalories(this.caloriesAddSport);
                    statsdate.setSteps(0);
                    statsdate.setMeter(0);
                } else {
                    statsdate.setDate(c.getString(c.getColumnIndex("date")));
                    statsdate.setCalories(c.getInt(c.getColumnIndex(DataBaseContants.CALORIES)) + this.caloriesAddSport);
                    statsdate.setSteps(c.getInt(c.getColumnIndex("steps")));
                    statsdate.setMeter(c.getInt(c.getColumnIndex(DataBaseContants.KILOMETER)));
                }
                c.close();
            }
            this.dailyStatses.add(statsdate);
        }
        getWeeklyStats();
        getMonthlyStats();
    }

    private void initAddSport(String day) {
        this.caloriesAddSport = 0;
        int calories = 0;
        Cursor cAddSport = this.mContext.getContentResolver().query(DataBaseContants.CONTENT_URI, new String[]{"_id", DataBaseContants.CALORIES, DataBaseContants.SPORTS_TYPE, "type"}, "date  = ? AND statistics = ?", new String[]{day, "0"}, null);
        cAddSport.moveToFirst();
        if (cAddSport.getCount() > 0) {
            for (int y = 0; y < cAddSport.getCount(); y++) {
                if (cAddSport.getInt(cAddSport.getColumnIndex("type")) == 2 && cAddSport.getInt(cAddSport.getColumnIndex(DataBaseContants.SPORTS_TYPE)) != 0) {
                    calories += cAddSport.getInt(cAddSport.getColumnIndex(DataBaseContants.CALORIES));
                    this.caloriesAddSport = calories;
                }
                cAddSport.moveToNext();
            }
        }
        cAddSport.close();
    }

    public StatsItem getDailyStatses(int paramInt) {
        return (StatsItem) this.dailyStatses.get(paramInt);
    }

    public int getDailyStatsesCount() {
        return this.dailyStatses.size();
    }

    public List<StatsItem> getDailyStatsesList() {
        return this.dailyStatses;
    }

    private void getWeeklyStats() {
        this.weeklyStatses = new ArrayList();
        String date = "";
        int step = 0;
        int cal = 0;
        int meter = 0;
        if (this.dailyStatses != null && this.dailyStatses.size() > 0) {
            for (int i = 0; i < this.dailyStatses.size(); i++) {
                StatsItem statsdate;
                if (i == 0) {
                    date = ((StatsItem) this.dailyStatses.get(0)).getDate();
                    step = ((StatsItem) this.dailyStatses.get(0)).getSteps();
                    cal = ((StatsItem) this.dailyStatses.get(0)).getCalories();
                    meter = ((StatsItem) this.dailyStatses.get(0)).getMeter();
                } else {
                    String date1 = ((StatsItem) this.dailyStatses.get(i - 1)).getDate();
                    String date2 = ((StatsItem) this.dailyStatses.get(i)).getDate();
                    if (Tools.isSameWeek(date1, date2).booleanValue()) {
                        step += ((StatsItem) this.dailyStatses.get(i)).getSteps();
                        cal += ((StatsItem) this.dailyStatses.get(i)).getCalories();
                        meter += ((StatsItem) this.dailyStatses.get(i)).getMeter();
                    } else {
                        date = date + "|" + date1;
                        statsdate = new StatsItem();
                        statsdate.setDate(date);
                        statsdate.setCalories(cal);
                        statsdate.setSteps(step);
                        statsdate.setMeter(meter);
                        this.weeklyStatses.add(statsdate);
                        date = date2;
                        step = ((StatsItem) this.dailyStatses.get(i)).getSteps();
                        cal = ((StatsItem) this.dailyStatses.get(i)).getCalories();
                        meter = ((StatsItem) this.dailyStatses.get(i)).getMeter();
                    }
                }
                if (this.dailyStatses.size() == i + 1) {
                    statsdate = new StatsItem();
                    statsdate.setDate(date + "|" + ((StatsItem) this.dailyStatses.get(i)).getDate());
                    statsdate.setCalories(cal);
                    statsdate.setSteps(step);
                    statsdate.setMeter(meter);
                    this.weeklyStatses.add(statsdate);
                }
            }
        }
    }

    public StatsItem getWeeklyStatses(int paramInt) {
        return (StatsItem) this.weeklyStatses.get(paramInt);
    }

    public int getWeeklyStatsesCount() {
        return this.weeklyStatses.size();
    }

    public List<StatsItem> getWeeklyStatsesList() {
        return this.weeklyStatses;
    }

    private void getMonthlyStats() {
        this.monthlyStatses = new ArrayList();
        String date = "";
        int step = 0;
        int cal = 0;
        int meter = 0;
        if (this.dailyStatses != null && this.dailyStatses.size() > 0) {
            for (int i = 0; i < this.dailyStatses.size(); i++) {
                StatsItem statsdate;
                if (i == 0) {
                    date = ((StatsItem) this.dailyStatses.get(0)).getDate();
                    step = ((StatsItem) this.dailyStatses.get(0)).getSteps();
                    cal = ((StatsItem) this.dailyStatses.get(0)).getCalories();
                    meter = ((StatsItem) this.dailyStatses.get(0)).getMeter();
                } else {
                    String date1 = ((StatsItem) this.dailyStatses.get(i - 1)).getDate();
                    String date2 = ((StatsItem) this.dailyStatses.get(i)).getDate();
                    if (Tools.isSameMonth(date1, date2).booleanValue()) {
                        step += ((StatsItem) this.dailyStatses.get(i)).getSteps();
                        cal += ((StatsItem) this.dailyStatses.get(i)).getCalories();
                        meter += ((StatsItem) this.dailyStatses.get(i)).getMeter();
                    } else {
                        date = date + "|" + date1;
                        statsdate = new StatsItem();
                        statsdate.setDate(date);
                        statsdate.setCalories(cal);
                        statsdate.setSteps(step);
                        statsdate.setMeter(meter);
                        this.monthlyStatses.add(statsdate);
                        date = date2;
                        step = ((StatsItem) this.dailyStatses.get(i)).getSteps();
                        cal = ((StatsItem) this.dailyStatses.get(i)).getCalories();
                        meter = ((StatsItem) this.dailyStatses.get(i)).getMeter();
                    }
                }
                if (this.dailyStatses.size() == i + 1) {
                    statsdate = new StatsItem();
                    statsdate.setDate(date + "|" + ((StatsItem) this.dailyStatses.get(i)).getDate());
                    statsdate.setCalories(cal);
                    statsdate.setSteps(step);
                    statsdate.setMeter(meter);
                    this.monthlyStatses.add(statsdate);
                }
            }
        }
    }

    public StatsItem getMonthlyStatses(int paramInt) {
        return (StatsItem) this.monthlyStatses.get(paramInt);
    }

    public int getMonthlyStatsesCount() {
        return this.monthlyStatses.size();
    }

    public List<StatsItem> getMonthlyStatsesList() {
        return this.monthlyStatses;
    }
}

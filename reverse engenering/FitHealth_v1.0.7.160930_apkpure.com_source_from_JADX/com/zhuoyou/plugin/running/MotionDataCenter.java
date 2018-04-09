package com.zhuoyou.plugin.running;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import com.zhuoyou.plugin.database.DataBaseContants;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MotionDataCenter {
    private List<String> calories = new ArrayList();
    private int caloriesAddSport;
    private List<String> complete = new ArrayList();
    private List<String> kilometers = new ArrayList();
    private Context mContext;
    private PersonalConfig mPersonalConfig = Tools.getPersonalConfig();
    private List<String> steps = new ArrayList();

    public MotionDataCenter(Context context) {
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
            String day = Tools.getDate(enter_day, 0 - i);
            if (date_list == null || date_list.size() <= 0 || date_list.indexOf(day) == -1) {
                this.steps.add(String.valueOf(0));
                this.calories.add(String.valueOf(0));
                this.kilometers.add(String.valueOf(0));
                this.complete.add(String.valueOf(0));
            } else {
                initAddSport(day);
                Cursor c = cr.query(DataBaseContants.CONTENT_URI, new String[]{"_id", "steps", DataBaseContants.CALORIES, DataBaseContants.KILOMETER, DataBaseContants.COMPLETE}, "date  = ? AND statistics = ? ", new String[]{day, "1"}, null);
                c.moveToFirst();
                if (c.getCount() <= 0 || !c.moveToFirst() || c.getLong(c.getColumnIndex("_id")) <= 0) {
                    this.steps.add(String.valueOf(0));
                    this.calories.add(String.valueOf(this.caloriesAddSport));
                    this.kilometers.add(String.valueOf(0));
                    this.complete.add(String.valueOf(0));
                } else {
                    this.steps.add(String.valueOf(c.getInt(c.getColumnIndex("steps"))));
                    this.calories.add(String.valueOf(c.getInt(c.getColumnIndex(DataBaseContants.CALORIES)) + this.caloriesAddSport));
                    this.kilometers.add(String.valueOf(c.getInt(c.getColumnIndex(DataBaseContants.KILOMETER))));
                    this.complete.add(String.valueOf(c.getInt(c.getColumnIndex(DataBaseContants.COMPLETE))));
                }
                c.close();
            }
        }
    }

    void initAddSport(String day) {
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

    public int getBestSteps() {
        int best_value = 0;
        if (this.steps != null && this.steps.size() > 0) {
            for (int i = 0; i < this.steps.size(); i++) {
                int step = Integer.parseInt((String) this.steps.get(i));
                if (step > best_value) {
                    best_value = step;
                }
            }
        }
        return best_value;
    }

    public int getBestCalories() {
        int best_value = 0;
        if (this.calories != null && this.calories.size() > 0) {
            for (int i = 0; i < this.calories.size(); i++) {
                int cal = Integer.parseInt((String) this.calories.get(i));
                if (cal > best_value) {
                    best_value = cal;
                }
            }
        }
        return best_value;
    }

    public int getAvgSteps() {
        int total = 0;
        if (this.steps == null || this.steps.size() <= 0) {
            return 0;
        }
        for (int i = 0; i < this.steps.size(); i++) {
            total += Integer.parseInt((String) this.steps.get(i));
        }
        return total / this.steps.size();
    }

    public int getAvgCalories() {
        int total = 0;
        if (this.calories == null || this.calories.size() <= 0) {
            return 0;
        }
        for (int i = 0; i < this.calories.size(); i++) {
            total += Integer.parseInt((String) this.calories.get(i));
        }
        return total / this.calories.size();
    }

    public double getTotalKM() {
        int total = 0;
        if (this.kilometers == null || this.kilometers.size() <= 0) {
            return 0.0d;
        }
        for (int i = 0; i < this.kilometers.size(); i++) {
            total += Integer.parseInt((String) this.kilometers.get(i));
        }
        return ((double) total) / 1000.0d;
    }

    public int getTotalCalories() {
        int total = 0;
        if (this.calories != null && this.calories.size() > 0) {
            for (int i = 0; i < this.calories.size(); i++) {
                total += Integer.parseInt((String) this.calories.get(i));
            }
        }
        return total;
    }

    public String getGoal() {
        int day = 0;
        String percent = "0%";
        if (this.complete != null && this.complete.size() > 0) {
            for (int i = 0; i < this.complete.size(); i++) {
                if (Integer.parseInt((String) this.complete.get(i)) == 1) {
                    day++;
                }
            }
            double d = ((double) day) / ((double) this.complete.size());
            NumberFormat numberformat = NumberFormat.getPercentInstance();
            numberformat.setMinimumFractionDigits(1);
            percent = numberformat.format(d);
        }
        return String.valueOf(day) + SeparatorConstants.SEPARATOR_ADS_ID + percent;
    }

    public double getBMI() {
        float weight = this.mPersonalConfig.getWeightNum();
        int height = this.mPersonalConfig.getHeight();
        return (((double) weight) * 10000.0d) / ((double) (height * height));
    }

    public int getBMR() {
        double bmr = 0.0d;
        float weight = this.mPersonalConfig.getWeightNum();
        int height = this.mPersonalConfig.getHeight();
        int age = Calendar.getInstance().get(1) - this.mPersonalConfig.getYear();
        if (this.mPersonalConfig.getSex() == 0) {
            bmr = (((13.7d * ((double) weight)) + (5.0d * ((double) height))) - (6.8d * ((double) age))) + 66.0d;
        } else if (this.mPersonalConfig.getSex() == 1) {
            bmr = (((9.6d * ((double) weight)) + (1.8d * ((double) height))) - (4.7d * ((double) age))) + 655.0d;
        }
        if (bmr < 0.0d) {
            bmr = 0.0d;
        }
        return (int) bmr;
    }
}

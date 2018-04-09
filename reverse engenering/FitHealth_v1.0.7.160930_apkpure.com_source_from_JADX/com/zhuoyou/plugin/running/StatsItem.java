package com.zhuoyou.plugin.running;

public class StatsItem {
    private int mCalories = 0;
    private String mDate = "";
    private int mMeter = 0;
    private int mSteps = 0;

    public String getDate() {
        return this.mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public int getSteps() {
        return this.mSteps;
    }

    public void setSteps(int mSteps) {
        this.mSteps = mSteps;
    }

    public int getCalories() {
        return this.mCalories;
    }

    public void setCalories(int mCalories) {
        this.mCalories = mCalories;
    }

    public int getMeter() {
        return this.mMeter;
    }

    public void setMeter(int mMeter) {
        this.mMeter = mMeter;
    }
}

package com.zhuoyou.plugin.running;

import java.util.Calendar;

public class PersonalConfig {
    public static final int SEX_MAN = 0;
    public static final int SEX_WOMAN = 1;
    public float id;
    private int mHeight;
    private int mSex;
    private String mWeight;
    private int mYear;

    public PersonalConfig(int sex, String w, int h, int year) {
        this.mSex = sex;
        this.mWeight = w;
        this.mHeight = h;
        this.mYear = year;
    }

    public int getSex() {
        return this.mSex;
    }

    public void setSex(int Sex) {
        this.mSex = Sex;
    }

    public String getWeight() {
        return this.mWeight;
    }

    public float getWeightNum() {
        return Float.valueOf(this.mWeight).floatValue();
    }

    public void setWeight(String Weight) {
        this.mWeight = Weight;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public void setHeight(int Height) {
        this.mHeight = Height;
    }

    public int getYear() {
        return this.mYear;
    }

    public void setYear(int Year) {
        this.mYear = Year;
    }

    public boolean isEquals(PersonalConfig config) {
        return this.mSex == config.mSex && this.mWeight.split("\\.")[0].equals(config.mWeight.split("\\.")[0]) && this.mHeight == config.mHeight && this.mYear == config.mYear;
    }

    public String toString() {
        int sex;
        if (this.mSex == 0) {
            sex = 1;
        } else {
            sex = 0;
        }
        return sex + "|" + this.mHeight + "|" + this.mWeight.split("\\.")[0] + "|" + (Calendar.getInstance().get(1) - this.mYear) + "|";
    }
}

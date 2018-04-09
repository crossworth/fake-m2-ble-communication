package com.zhuoyou.plugin.running;

import android.content.ContentValues;
import com.zhuoyou.plugin.database.DataBaseContants;
import java.io.Serializable;
import java.math.BigDecimal;

public class RunningItem implements Serializable, Cloneable {
    private static final long serialVersionUID = -738540965238317438L;
    private String heart_rate_count;
    private long heart_rate_time;
    private long id = 0;
    private String img_cloud;
    private int isComplete;
    private int isStatistics = 0;
    private String mBmi;
    private int mCalories = 0;
    private String mDate = "";
    private String mDuration = "";
    private String mEndTime;
    private String mExplain;
    private String mFrom;
    private String mImgUri;
    private int mKilometer = 0;
    private int mPm25 = 0;
    private int mSportsType;
    private String mStartTime;
    private int mSteps = 0;
    private int mType;
    private String mWeight;

    public long getID() {
        return this.id;
    }

    public String getDate() {
        return this.mDate;
    }

    public String getDuration() {
        if (this.mDuration == null) {
            this.mDuration = "";
        }
        return this.mDuration;
    }

    public String getStartTime() {
        return this.mStartTime;
    }

    public String getEndTime() {
        return this.mEndTime;
    }

    public int getPm25() {
        return this.mPm25;
    }

    public int getSteps() {
        return this.mSteps;
    }

    public float getKilometer() {
        return new BigDecimal((double) (((float) this.mKilometer) / 1000.0f)).setScale(2, 4).floatValue();
    }

    public int getMeter() {
        return this.mKilometer;
    }

    public int getCalories() {
        return this.mCalories;
    }

    public void setID(long id) {
        this.id = id;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public void setStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public void setEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }

    public void setPm25(int mPm25) {
        this.mPm25 = mPm25;
    }

    public void setSteps(int mSteps) {
        this.mSteps = mSteps;
    }

    public void setKilometer(int mKilometer) {
        this.mKilometer = mKilometer;
    }

    public void setCalories(int mCalories) {
        this.mCalories = mCalories;
    }

    public void setDuration(String mDuration) {
        this.mDuration = mDuration;
    }

    public void setisComplete(int is) {
        this.isComplete = is;
    }

    public void setisStatistics(int is) {
        this.isStatistics = is;
    }

    public int getIsComplete() {
        return this.isComplete;
    }

    public int getIsStatistics() {
        return this.isStatistics;
    }

    public String getmWeight() {
        return this.mWeight;
    }

    public void setmWeight(String mWeight) {
        this.mWeight = mWeight;
    }

    public String getmBmi() {
        return this.mBmi;
    }

    public void setmBmi(String mBmi) {
        this.mBmi = mBmi;
    }

    public String getmImgUri() {
        return this.mImgUri;
    }

    public void setmImgUri(String mImgUri) {
        this.mImgUri = mImgUri;
    }

    public String getmExplain() {
        return this.mExplain;
    }

    public void setmExplain(String mExplain) {
        this.mExplain = mExplain;
    }

    public int getSportsType() {
        return this.mSportsType;
    }

    public void setSportsType(int sportsType) {
        this.mSportsType = sportsType;
    }

    public int getmType() {
        return this.mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public String getImg_cloud() {
        return this.img_cloud;
    }

    public void setImg_cloud(String img_cloud) {
        this.img_cloud = img_cloud;
    }

    public String getDataFrom() {
        return this.mFrom;
    }

    public void setDataFrom(String from) {
        this.mFrom = from;
    }

    public long getHeart_rate_time() {
        return this.heart_rate_time;
    }

    public void setHeart_rate_time(long heart_rate_time) {
        this.heart_rate_time = heart_rate_time;
    }

    public String getHeart_rate_count() {
        return this.heart_rate_count;
    }

    public void setHeart_rate_count(String heart_rate_count) {
        this.heart_rate_count = heart_rate_count;
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("date", this.mDate);
        cv.put(DataBaseContants.TIME_DURATION, this.mDuration);
        cv.put(DataBaseContants.TIME_START, this.mStartTime);
        cv.put(DataBaseContants.TIME_END, this.mEndTime);
        cv.put("steps", Integer.valueOf(this.mSteps));
        cv.put(DataBaseContants.KILOMETER, Integer.valueOf(this.mKilometer));
        cv.put(DataBaseContants.CALORIES, Integer.valueOf(this.mCalories));
        cv.put(DataBaseContants.SPORTS_TYPE, Integer.valueOf(this.mSportsType));
        cv.put("type", Integer.valueOf(this.mType));
        cv.put(DataBaseContants.COMPLETE, Integer.valueOf(this.isComplete));
        cv.put(DataBaseContants.STATISTICS, Integer.valueOf(this.isStatistics));
        cv.put(DataBaseContants.DATA_FROM, this.mFrom);
        return cv;
    }

    public String toString() {
        return (((((((((((((((((((((((((("" + "mSteps = ") + this.mSteps) + " | ") + "mDuration = ") + this.mDuration) + " | ") + "mStartTime = ") + this.mStartTime) + " | ") + "mEndTime = ") + this.mEndTime) + " | ") + "mPm25 = ") + this.mPm25) + " | ") + "mSteps = ") + this.mSteps) + " | ") + "mKilometer = ") + this.mKilometer) + " | ") + "mCalories = ") + this.mCalories) + " | ") + "isStatistics = ") + this.isStatistics) + " | ";
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

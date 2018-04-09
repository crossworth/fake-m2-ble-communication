package com.zhuoyou.plugin.running;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class SleepItem implements Serializable {
    private static final long serialVersionUID = 1;
    private List<SleepBean> datas;
    private Calendar endCal;
    private long id;
    private int mDSleepT;
    private String mDate = "";
    private String mEndT;
    private String mImgUri;
    private int mSleepT;
    private String mStartT;
    private int mWSleepT;
    private Calendar startCal;

    public SleepItem(int mSleepT, int mDSleepT, int mWSleepT, String mStartT, String mEndT) {
        this.mSleepT = mSleepT;
        this.mDSleepT = mDSleepT;
        this.mWSleepT = mWSleepT;
        this.mStartT = mStartT;
        this.mEndT = mEndT;
    }

    public SleepItem(int mSleepT, int mDSleepT, int mWSleepT, String mStartT, String mEndT, List<SleepBean> data) {
        this.mSleepT = mSleepT;
        this.mDSleepT = mDSleepT;
        this.mWSleepT = mWSleepT;
        this.mStartT = mStartT;
        this.mEndT = mEndT;
        this.datas = data;
    }

    public int getmSleepT() {
        return this.mSleepT;
    }

    public int getmDSleepT() {
        return this.mDSleepT;
    }

    public int getmWSleepT() {
        return this.mWSleepT;
    }

    public String getmStartT() {
        return this.mStartT;
    }

    public String getmEndT() {
        return this.mEndT;
    }

    public List<SleepBean> getData() {
        return this.datas;
    }

    public void setmSleepT(int mSleepT) {
        this.mSleepT = mSleepT;
    }

    public void setmDSleepT(int mDSleepT) {
        this.mDSleepT = mDSleepT;
    }

    public void setmWSleepT(int mWSleepT) {
        this.mWSleepT = mWSleepT;
    }

    public void setmStartT(String mStartT) {
        this.mStartT = mStartT;
    }

    public void setmEndT(String mEndT) {
        this.mEndT = mEndT;
    }

    public void setData(List<SleepBean> data) {
        this.datas = data;
    }

    public Calendar getStartCal() {
        return this.startCal;
    }

    public void setStartCal(Calendar startCal) {
        this.startCal = startCal;
    }

    public Calendar getEndCal() {
        return this.endCal;
    }

    public void setEndCal(Calendar endCal) {
        this.endCal = endCal;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getmImgUri() {
        return this.mImgUri;
    }

    public String getDate() {
        return this.mDate;
    }
}

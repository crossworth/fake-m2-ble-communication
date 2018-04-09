package com.zhuoyou.plugin.action;

import java.io.Serializable;

public class ActionListItemInfo implements Serializable {
    private int mActId;
    private String mCurTime;
    private String mEndTime;
    private boolean mFlag;
    private String mImgUrl;
    private String mNum;
    private String mStartTime;
    private String mTitle;
    private boolean mTop;

    public ActionListItemInfo(int actid, String title, String startTime, String endTime, String curTime, String num, int flag, int top, String imgUrl) {
        boolean z;
        boolean z2 = true;
        this.mActId = actid;
        this.mTitle = title;
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        this.mCurTime = curTime;
        this.mNum = num;
        if (flag == 0) {
            z = true;
        } else {
            z = false;
        }
        this.mFlag = z;
        if (top != 0) {
            z2 = false;
        }
        this.mTop = z2;
        this.mImgUrl = imgUrl;
    }

    public int GetActivtyId() {
        return this.mActId;
    }

    public String GetActivtyTitle() {
        return this.mTitle;
    }

    public String GetActivtyStartTime() {
        return this.mStartTime;
    }

    public String GetActivtyEndTime() {
        return this.mEndTime;
    }

    public String GetActivtyCurTime() {
        return this.mCurTime;
    }

    public String GetActivtyNum() {
        return this.mNum;
    }

    public boolean GetActiviyFlag() {
        return this.mFlag;
    }

    public boolean GetActiviyTop() {
        return this.mTop;
    }

    public String GetActiviyImgUrl() {
        return this.mImgUrl;
    }
}

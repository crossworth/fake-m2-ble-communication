package com.zhuoyou.plugin.add;

import java.io.Serializable;

public class HeartRate implements Serializable {
    private int heartrateCount;
    private String heartrateId;
    private String heartrateTime;

    public HeartRate(String heartrateId, int heartrateCount, String heartrateTime) {
        this.heartrateCount = heartrateCount;
        this.heartrateTime = heartrateTime;
    }

    public String getHeartrateId() {
        return this.heartrateId;
    }

    public void setHeartrateId(String heartrateId) {
        this.heartrateId = heartrateId;
    }

    public int getHeartrateCount() {
        return this.heartrateCount;
    }

    public void setHeartrateCount(int heartrateCount) {
        this.heartrateCount = heartrateCount;
    }

    public String getHeartrateTime() {
        return this.heartrateTime;
    }

    public void setHeartrateTime(String heartrateTime) {
        this.heartrateTime = heartrateTime;
    }
}

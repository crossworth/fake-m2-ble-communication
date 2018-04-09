package com.zhuoyou.plugin.running;

import java.io.Serializable;

public class SleepBean implements Serializable {
    private static final long serialVersionUID = 1407251029862793480L;
    private String endTime;
    private boolean isDeep;
    private String startTime;

    public SleepBean(boolean isDeep, String startTime, String endTime) {
        this.isDeep = isDeep;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean isDeep() {
        return this.isDeep;
    }

    public void setDeep(boolean isDeep) {
        this.isDeep = isDeep;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

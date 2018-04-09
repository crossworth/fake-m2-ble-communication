package com.droi.btlib.service;

public class SleepInfo {
    private long endTime;
    private String sleepDetail;
    private long startTime;
    private int type;

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getSleepDetail() {
        return this.sleepDetail;
    }

    public void setSleepDetail(String sleepDetail) {
        this.sleepDetail = sleepDetail;
    }

    public String toString() {
        return "SleepInfo{type=" + this.type + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", sleepDetail='" + this.sleepDetail + '\'' + '}';
    }
}

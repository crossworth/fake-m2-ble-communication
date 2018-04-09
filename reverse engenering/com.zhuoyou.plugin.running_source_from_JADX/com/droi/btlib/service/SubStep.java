package com.droi.btlib.service;

public class SubStep {
    String endTime;
    String startTime;
    int step;

    public int getStep() {
        return this.step;
    }

    public void setStep(int step) {
        this.step = step;
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

    public String toString() {
        return "SubStep{step=" + this.step + ", startTime='" + this.startTime + '\'' + ", endTime='" + this.endTime + '\'' + '}';
    }
}

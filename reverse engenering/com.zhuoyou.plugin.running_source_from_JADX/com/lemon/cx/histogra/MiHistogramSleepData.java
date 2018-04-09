package com.lemon.cx.histogra;

public class MiHistogramSleepData {
    private boolean deep = true;
    private int endTime;
    private int startTime;

    public MiHistogramSleepData(int startTime, int endTime, boolean deep) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.deep = deep;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public boolean isDeep() {
        return this.deep;
    }

    public void setDeep(boolean deep) {
        this.deep = deep;
    }

    public String toString() {
        return "MiHistogramSleepData{startTime=" + this.startTime + ", endTime=" + this.endTime + ", deep=" + this.deep + '}';
    }
}

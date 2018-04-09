package com.lemon.cx.histogra;

public class MiHistogramSportData {
    private int endTime;
    private int startTime;
    private int step;

    public MiHistogramSportData(int startTime, int endTime, int step) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.step = step;
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

    public int getStep() {
        return this.step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String toString() {
        return "MiHistogramSportData{startTime=" + this.startTime + ", endTime=" + this.endTime + ", step=" + this.step + '}';
    }
}

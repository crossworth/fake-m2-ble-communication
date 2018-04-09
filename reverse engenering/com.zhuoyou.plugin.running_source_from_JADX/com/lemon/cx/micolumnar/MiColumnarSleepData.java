package com.lemon.cx.micolumnar;

import java.util.Calendar;

public class MiColumnarSleepData extends MiColumnarData implements Comparable<MiColumnarSleepData> {
    private Calendar date;

    public MiColumnarSleepData(Calendar date) {
        this.date = date;
    }

    public MiColumnarSleepData(Calendar date, int totalSleep, int deepSleep) {
        this.date = date;
        setSleepTime(totalSleep, deepSleep);
    }

    public MiColumnarSleepData(Calendar date, String label, int totalSleep, int deepSleep) {
        this.date = date;
        this.label = label;
        setSleepTime(totalSleep, deepSleep);
    }

    public MiColumnarSleepData(MiColumnarSleepData data) {
        this.label = data.getLabel();
        this.date = data.getDate();
        this.target = data.getTarget();
        this.value = data.getValue();
    }

    public Calendar getDate() {
        return this.date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setSleepTime(int totalSleep, int deepSleep) {
        this.target = totalSleep;
        this.value = this.target - deepSleep;
    }

    public int getdSleepTime() {
        return this.target - this.value;
    }

    public int getlSleepTime() {
        return this.value;
    }

    public int compareTo(MiColumnarSleepData another) {
        return this.date.compareTo(another.date);
    }

    public String toString() {
        return "MiColumnarSleepData{, dSleepTime=" + (this.target - this.value) + ", lSleepTime=" + this.value + '}';
    }
}

package com.zhuoyou.plugin.running.bean;

import java.text.DecimalFormat;

public class SedentaryBean {
    public String EndTime;
    public String RemindTime;
    public String StartTime;
    public int TimeLag;
    private int endHour;
    private int endMin;
    public Boolean isOpen;
    public Boolean isSync;
    private int periodTime;
    private int startHour;
    private int startMin;

    public String getRemindTime() {
        return this.RemindTime;
    }

    public void setRemindTime(String remindTime) {
        this.RemindTime = remindTime;
    }

    public String getStartTime() {
        return this.StartTime;
    }

    public void setStartTime(String startTime) {
        this.StartTime = startTime;
    }

    public String getEndTime() {
        return this.EndTime;
    }

    public void setEndTime(String endTime) {
        this.EndTime = endTime;
    }

    public int getTimeLag() {
        return this.TimeLag;
    }

    public void setTimeLag(int timeLag) {
        this.TimeLag = timeLag;
    }

    public Boolean getIsOpen() {
        return this.isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Boolean getIsSync() {
        return this.isSync;
    }

    public void setIsSync(Boolean isSync) {
        this.isSync = isSync;
    }

    public int getPeriodTime() {
        return this.periodTime;
    }

    public void setPeriodTime(int periodTime) {
        this.periodTime = periodTime;
    }

    public int getStartHour() {
        return this.startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMin() {
        return this.startMin;
    }

    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    public int getEndHour() {
        return this.endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMin() {
        return this.endMin;
    }

    public void setEndMin(int endMin) {
        this.endMin = endMin;
    }

    public String toString() {
        return (this.isOpen.booleanValue() ? 1 : 0) + "|" + new DecimalFormat("#00").format((long) this.periodTime) + "|";
    }

    public final String saveSedentaryShareP() {
        return (this.isOpen.booleanValue() ? 1 : 0) + "|" + new DecimalFormat("#00").format((long) this.periodTime) + "|";
    }
}

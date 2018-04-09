package com.zhuoyou.plugin.running.bean;

import java.text.DecimalFormat;

public class AlarmData {
    public static int OPEN_TYPE_CUSTOM = 3;
    public static int OPEN_TYPE_ONCE = 0;
    public static int OPEN_TYPE_REPEAT = 1;
    public static int OPEN_TYPE_WORK = 2;
    private int customData = 11111;
    private int hour;
    private int id;
    private boolean isBrain = false;
    private boolean isOpen = false;
    private int min;
    private String openDate;
    private int openType = -1;

    public AlarmData(int id, boolean isOpen, int openType, int customData) {
        this.id = id;
        this.isOpen = isOpen;
        this.openType = openType;
        this.customData = customData;
    }

    public int getId() {
        return this.id;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMin() {
        return this.min;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public boolean isBrain() {
        return this.isBrain;
    }

    public int getOpenType() {
        return this.openType;
    }

    public int getCustomData() {
        return this.customData;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void setBrain(boolean isBrain) {
        this.isBrain = isBrain;
    }

    public void setOpenType(int openType) {
        this.openType = openType;
    }

    public void setCustomData(int customData) {
        this.customData = customData;
    }

    public String getOpenDate() {
        return this.openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public final String toString() {
        int i;
        int i2 = 1;
        DecimalFormat intFormat = new DecimalFormat("#00");
        DecimalFormat byteFormat = new DecimalFormat("#0000000");
        StringBuilder append = new StringBuilder().append(intFormat.format((long) this.hour)).append(intFormat.format((long) this.min)).append("|").append(this.id);
        if (this.isBrain) {
            i = 1;
        } else {
            i = 0;
        }
        StringBuilder append2 = append.append(i);
        if (!this.isOpen) {
            i2 = 0;
        }
        return append2.append(i2).append("|").append(this.openType).append("|").append(byteFormat.format((long) this.customData)).append("|").append(getOpenDate()).append("|").toString();
    }

    public final String toBTcmd() {
        int i;
        int i2 = 1;
        DecimalFormat intFormat = new DecimalFormat("#00");
        DecimalFormat byteFormat = new DecimalFormat("#0000000");
        StringBuilder append = new StringBuilder().append(intFormat.format((long) this.hour)).append(intFormat.format((long) this.min)).append("|").append(this.id);
        if (this.isBrain) {
            i = 1;
        } else {
            i = 0;
        }
        StringBuilder append2 = append.append(i);
        if (!this.isOpen) {
            i2 = 0;
        }
        return append2.append(i2).append("|").append(this.openType).append("|").append(byteFormat.format((long) this.customData)).append("|").toString();
    }
}

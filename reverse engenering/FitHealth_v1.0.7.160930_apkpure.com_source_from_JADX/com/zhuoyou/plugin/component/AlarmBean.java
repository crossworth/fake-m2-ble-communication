package com.zhuoyou.plugin.component;

import java.io.Serializable;
import java.text.DecimalFormat;

public class AlarmBean implements Serializable {
    private static final long serialVersionUID = 6967510102698932010L;
    private int customData = 11111;
    private int hour;
    private int id;
    private boolean isBrain;
    private boolean isOpen;
    private int min;
    private int openType;

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
        return append2.append(i2).append("|").append(this.openType).append("|").append(byteFormat.format((long) this.customData)).append("|").toString();
    }

    public final String saveShareP() {
        int i;
        int i2 = 1;
        DecimalFormat intFormat = new DecimalFormat("#00");
        DecimalFormat byteFormat = new DecimalFormat("#0000000");
        StringBuilder append = new StringBuilder().append(intFormat.format((long) this.hour)).append(intFormat.format((long) this.min)).append("|").append(this.id).append("|");
        if (this.isBrain) {
            i = 1;
        } else {
            i = 0;
        }
        StringBuilder append2 = append.append(i).append("|");
        if (!this.isOpen) {
            i2 = 0;
        }
        return append2.append(i2).append("|").append(this.openType).append("|").append(byteFormat.format((long) this.customData)).append("|").toString();
    }
}

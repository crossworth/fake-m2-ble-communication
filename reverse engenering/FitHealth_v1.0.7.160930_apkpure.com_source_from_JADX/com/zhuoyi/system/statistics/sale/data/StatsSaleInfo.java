package com.zhuoyi.system.statistics.sale.data;

import java.io.Serializable;

public class StatsSaleInfo implements Serializable {
    private static final long serialVersionUID = 3213552809755687038L;
    private String IMSI;
    private int activeState;
    private String activeTime;
    private long stayTime;

    public String getIMSI() {
        return this.IMSI;
    }

    public void setIMSI(String iMSI) {
        this.IMSI = iMSI;
    }

    public String getActiveTime() {
        return this.activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public long getStayTime() {
        return this.stayTime;
    }

    public void setStayTime(long stayTime) {
        this.stayTime = stayTime;
    }

    public int getActiveState() {
        return this.activeState;
    }

    public void setActiveState(int activeState) {
        this.activeState = activeState;
    }
}

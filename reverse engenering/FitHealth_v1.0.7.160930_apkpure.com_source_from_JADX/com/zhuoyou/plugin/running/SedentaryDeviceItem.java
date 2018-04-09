package com.zhuoyou.plugin.running;

import java.io.Serializable;

public class SedentaryDeviceItem implements Serializable {
    private static final long serialVersionUID = 1;
    public String DeviceName;
    public String EndTime;
    public String StartTime;
    public Boolean State;
    public int TimeLag;
    public Boolean isSync;

    public SedentaryDeviceItem(String DeviceName, String StartTime, String EndTime, int TimeLag, Boolean State) {
        this.DeviceName = DeviceName;
        this.StartTime = StartTime;
        this.EndTime = EndTime;
        this.TimeLag = TimeLag;
        this.State = State;
    }

    public String getDeviceName() {
        return this.DeviceName;
    }

    public void setDeviceName(String deviceName) {
        this.DeviceName = deviceName;
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

    public Boolean getState() {
        return this.State;
    }

    public void setState(Boolean state) {
        this.State = state;
    }

    public Boolean getIsSync() {
        return this.isSync;
    }

    public void setIsSync(Boolean isSync) {
        this.isSync = isSync;
    }

    public String toString() {
        return this.StartTime + "|" + this.EndTime + "|" + this.TimeLag + "|" + this.State;
    }
}

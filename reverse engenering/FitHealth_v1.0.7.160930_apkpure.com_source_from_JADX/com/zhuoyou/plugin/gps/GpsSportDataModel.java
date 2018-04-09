package com.zhuoyou.plugin.gps;

import java.io.Serializable;

public class GpsSportDataModel implements Serializable {
    private static final long serialVersionUID = 7172694132214059534L;
    private double avespeed;
    private double calorie;
    private long durationtime;
    private String endAddress;
    private long endSystime;
    private long endtime;
    private long gpsId;
    private String heartCount;
    private long starSysttime;
    private String startAddress;
    private long starttime;
    private int steps;
    private int syncState;
    private double totalDistance;

    public long getGpsId() {
        return this.gpsId;
    }

    public void setGpsId(long gpsId) {
        this.gpsId = gpsId;
    }

    public long getStarttime() {
        return this.starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public long getEndtime() {
        return this.endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public long getDurationtime() {
        return this.durationtime;
    }

    public void setDurationtime(long durationtime) {
        this.durationtime = durationtime;
    }

    public double getAvespeed() {
        return this.avespeed;
    }

    public void setAvespeed(double avespeed) {
        this.avespeed = avespeed;
    }

    public double getTotalDistance() {
        return this.totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public int getSteps() {
        return this.steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public double getCalorie() {
        return this.calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

    public long getStarSysttime() {
        return this.starSysttime;
    }

    public void setStarSysttime(long starSysttime) {
        this.starSysttime = starSysttime;
    }

    public long getEndSystime() {
        return this.endSystime;
    }

    public void setEndSystime(long endSystime) {
        this.endSystime = endSystime;
    }

    public String getStartAddress() {
        return this.startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return this.endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public int getSyncState() {
        return this.syncState;
    }

    public void setSyncState(int syncState) {
        this.syncState = syncState;
    }

    public String getHeartCount() {
        return this.heartCount;
    }

    public void setHeartCount(String heartCount) {
        this.heartCount = heartCount;
    }

    public void clearData() {
        this.starttime = 0;
        this.endtime = 0;
        this.starSysttime = 0;
        this.endSystime = 0;
        this.durationtime = 0;
        this.avespeed = 0.0d;
        this.totalDistance = 0.0d;
        this.steps = 0;
        this.calorie = 0.0d;
        this.gpsId = 0;
        this.startAddress = "";
        this.endAddress = "";
        this.syncState = 0;
        this.heartCount = "";
    }
}

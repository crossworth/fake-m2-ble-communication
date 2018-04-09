package com.zhuoyou.plugin.gps;

import java.io.Serializable;

public class GuidePointModel implements Serializable {
    private static final long serialVersionUID = -4710693202712413234L;
    private float accuracy;
    private String address;
    private double altitude;
    private int gpsStatus;
    private long guideId;
    private double latitude;
    private double longitude;
    private int pointState;
    private String provider;
    private float speed;
    private int syncState;
    private long sysTime;
    private long time;

    public GuidePointModel(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getAccuracy() {
        return this.accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public String getProvider() {
        return this.provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public double getAltitude() {
        return this.altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public int getGpsStatus() {
        return this.gpsStatus;
    }

    public void setGpsStatus(int gpsStatus) {
        this.gpsStatus = gpsStatus;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getSysTime() {
        return this.sysTime;
    }

    public void setSysTime(long sysTime) {
        this.sysTime = sysTime;
    }

    public int getPointState() {
        return this.pointState;
    }

    public void setPointState(int pointState) {
        this.pointState = pointState;
    }

    public long getGuideId() {
        return this.guideId;
    }

    public void setGuideId(long guideId) {
        this.guideId = guideId;
    }

    public int getSyncState() {
        return this.syncState;
    }

    public void setSyncState(int syncState) {
        this.syncState = syncState;
    }
}

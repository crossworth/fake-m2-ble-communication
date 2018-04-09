package com.zhuoyou.plugin.gps;

public class TempDataModel {
    private int tempCalories;
    private String tempDate;
    private double tempDistance;
    private String tempDuration;
    private String tempEndAddress;
    private String tempEndTime;
    private long tempGpsId;
    private String tempHeartRate;
    private long tempId;
    private String tempImageUrl;
    private String tempStaAddress;
    private String tempStaTime;
    private int tempState;
    private int tempStatistics;
    private int tempStep;
    private int tempType;

    public long getTempId() {
        return this.tempId;
    }

    public void setTempId(long tempId) {
        this.tempId = tempId;
    }

    public String getTempDate() {
        return this.tempDate;
    }

    public void setTempDate(String tempDate) {
        this.tempDate = tempDate;
    }

    public String getTempStaTime() {
        return this.tempStaTime;
    }

    public void setTempStaTime(String tempStaTime) {
        this.tempStaTime = tempStaTime;
    }

    public String getTempDuration() {
        return this.tempDuration;
    }

    public void setTempDuration(String tempDuration) {
        this.tempDuration = tempDuration;
    }

    public String getTempEndTime() {
        return this.tempEndTime;
    }

    public void setTempEndTime(String tempEndTime) {
        this.tempEndTime = tempEndTime;
    }

    public int getTempCalories() {
        return this.tempCalories;
    }

    public void setTempCalories(int tempCalories) {
        this.tempCalories = tempCalories;
    }

    public int getTempStep() {
        return this.tempStep;
    }

    public void setTempStep(int tempStep) {
        this.tempStep = tempStep;
    }

    public double getTempDistance() {
        return this.tempDistance;
    }

    public void setTempDistance(double tempDistance) {
        this.tempDistance = tempDistance;
    }

    public int getTempType() {
        return this.tempType;
    }

    public void setTempType(int tempType) {
        this.tempType = tempType;
    }

    public int getTempStatistics() {
        return this.tempStatistics;
    }

    public void setTempStatistics(int tempStatistics) {
        this.tempStatistics = tempStatistics;
    }

    public int getTempState() {
        return this.tempState;
    }

    public void setTempState(int tempState) {
        this.tempState = tempState;
    }

    public String getTempStaAddress() {
        return this.tempStaAddress;
    }

    public void setTempStaAddress(String tempStaAddress) {
        this.tempStaAddress = tempStaAddress;
    }

    public String getTempEndAddress() {
        return this.tempEndAddress;
    }

    public void setTempEndAddress(String tempEndAddress) {
        this.tempEndAddress = tempEndAddress;
    }

    public String getTempImageUrl() {
        return this.tempImageUrl;
    }

    public void setTempImageUrl(String tempImageUrl) {
        this.tempImageUrl = tempImageUrl;
    }

    public long getTempGpsId() {
        return this.tempGpsId;
    }

    public void setTempGpsId(long tempGpsId) {
        this.tempGpsId = tempGpsId;
    }

    public String getTempHeartRate() {
        return this.tempHeartRate;
    }

    public void setTempHeartRate(String tempHeartRate) {
        this.tempHeartRate = tempHeartRate;
    }

    public void clearData() {
        this.tempId = 0;
        this.tempDate = "";
        this.tempStaTime = "";
        this.tempDuration = "";
        this.tempEndTime = "";
        this.tempCalories = 0;
        this.tempStep = 0;
        this.tempDistance = 0.0d;
        this.tempType = 0;
        this.tempStatistics = 0;
        this.tempState = 0;
        this.tempStaAddress = "";
        this.tempEndAddress = "";
        this.tempImageUrl = "";
        this.tempGpsId = 0;
        this.tempHeartRate = "";
    }
}

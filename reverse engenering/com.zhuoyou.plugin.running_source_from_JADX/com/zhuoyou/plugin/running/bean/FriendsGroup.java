package com.zhuoyou.plugin.running.bean;

import java.io.Serializable;

public class FriendsGroup implements Serializable {
    private String areaId;
    private String areaName;
    private long numbers;
    private String summary;
    private String title;

    public FriendsGroup(String title, String summary) {
        this.title = title;
        this.summary = summary;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAreaId() {
        return this.areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public long getNumbers() {
        return this.numbers;
    }

    public void setNumbers(long numbers) {
        this.numbers = numbers;
    }
}

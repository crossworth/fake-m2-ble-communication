package com.amap.api.maps.model;

public class MyTrafficStyle {
    private int f898a = -16735735;
    private int f899b = -35576;
    private int f900c = -1441006;
    private int f901d = -7208950;

    public int getSmoothColor() {
        return this.f898a;
    }

    public void setSmoothColor(int i) {
        this.f898a = i;
    }

    public int getSlowColor() {
        return this.f899b;
    }

    public void setSlowColor(int i) {
        this.f899b = i;
    }

    public int getCongestedColor() {
        return this.f900c;
    }

    public void setCongestedColor(int i) {
        this.f900c = i;
    }

    public int getSeriousCongestedColor() {
        return this.f901d;
    }

    public void setSeriousCongestedColor(int i) {
        this.f901d = i;
    }
}

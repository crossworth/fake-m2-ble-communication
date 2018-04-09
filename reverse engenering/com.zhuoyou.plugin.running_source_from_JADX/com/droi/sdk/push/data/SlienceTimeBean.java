package com.droi.sdk.push.data;

import java.io.Serializable;

public class SlienceTimeBean implements Serializable {
    private int f3268a = -1;
    private int f3269b = -1;
    private int f3270c = -1;
    private int f3271d = -1;

    public SlienceTimeBean(int i, int i2, int i3, int i4) {
        this.f3268a = i;
        this.f3269b = i2;
        this.f3270c = i3;
        this.f3271d = i4;
    }

    public int getEndHour() {
        return this.f3270c;
    }

    public int getEndMin() {
        return this.f3271d;
    }

    public int getStartHour() {
        return this.f3268a;
    }

    public int getStartMin() {
        return this.f3269b;
    }

    public void setEndHour(int i) {
        this.f3270c = i;
    }

    public void setEndMin(int i) {
        this.f3271d = i;
    }

    public void setStartHour(int i) {
        this.f3268a = i;
    }

    public void setStartMin(int i) {
        this.f3269b = i;
    }
}

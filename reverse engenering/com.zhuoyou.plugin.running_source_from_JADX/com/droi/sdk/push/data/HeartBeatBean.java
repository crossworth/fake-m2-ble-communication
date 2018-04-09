package com.droi.sdk.push.data;

import java.io.Serializable;

public class HeartBeatBean implements Serializable {
    private int f3264a;

    public HeartBeatBean(int i) {
        this.f3264a = i;
    }

    public int getTime() {
        return this.f3264a;
    }

    public void setTime(int i) {
        this.f3264a = i;
    }
}

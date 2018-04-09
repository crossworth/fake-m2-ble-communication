package com.droi.sdk.push.data;

import java.io.Serializable;

public class RecoveryTimeBean implements Serializable {
    private long f3265a = System.currentTimeMillis();
    private long f3266b;

    public RecoveryTimeBean(long j) {
        this.f3266b = j;
    }

    public boolean isRecovered() {
        return this.f3266b <= 0 || Math.abs(System.currentTimeMillis() - this.f3265a) > this.f3266b;
    }
}

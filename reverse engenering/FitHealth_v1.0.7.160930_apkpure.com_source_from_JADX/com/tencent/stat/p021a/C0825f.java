package com.tencent.stat.p021a;

import com.amap.api.services.core.AMapException;

public enum C0825f {
    PAGE_VIEW(1),
    SESSION_ENV(2),
    ERROR(3),
    CUSTOM(1000),
    ADDITION(1001),
    MONITOR_STAT(1002),
    MTA_GAME_USER(AMapException.CODE_AMAP_SERVICE_NOT_AVAILBALE),
    NETWORK_MONITOR(1004);
    
    private int f2860i;

    private C0825f(int i) {
        this.f2860i = i;
    }

    public int m2663a() {
        return this.f2860i;
    }
}

package com.tencent.wxop.stat.p022a;

import com.amap.api.services.core.AMapException;

public enum C0874e {
    PAGE_VIEW(1),
    SESSION_ENV(2),
    ERROR(3),
    CUSTOM(1000),
    ADDITION(1001),
    MONITOR_STAT(1002),
    MTA_GAME_USER(AMapException.CODE_AMAP_SERVICE_NOT_AVAILBALE),
    NETWORK_MONITOR(1004),
    NETWORK_DETECTOR(AMapException.CODE_AMAP_ACCESS_TOO_FREQUENT);
    
    private int bG;

    private C0874e(int i) {
        this.bG = i;
    }

    public final int m2838r() {
        return this.bG;
    }
}

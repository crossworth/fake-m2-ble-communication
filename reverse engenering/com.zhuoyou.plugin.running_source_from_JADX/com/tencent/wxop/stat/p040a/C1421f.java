package com.tencent.wxop.stat.p040a;

import com.baidu.mapapi.UIMsg.f_FUN;

public enum C1421f {
    PAGE_VIEW(1),
    SESSION_ENV(2),
    ERROR(3),
    CUSTOM(1000),
    ADDITION(1001),
    MONITOR_STAT(1002),
    MTA_GAME_USER(f_FUN.FUN_ID_MAP_STATE),
    NETWORK_MONITOR(1004),
    NETWORK_DETECTOR(1005);
    
    private int f4622j;

    private C1421f(int i) {
        this.f4622j = i;
    }

    public final int m4284a() {
        return this.f4622j;
    }
}

package com.tencent.stat.p039a;

import com.baidu.mapapi.UIMsg.f_FUN;

public enum C1370f {
    PAGE_VIEW(1),
    SESSION_ENV(2),
    ERROR(3),
    CUSTOM(1000),
    ADDITION(1001),
    MONITOR_STAT(1002),
    MTA_GAME_USER(f_FUN.FUN_ID_MAP_STATE),
    NETWORK_MONITOR(1004);
    
    private int f4368i;

    private C1370f(int i) {
        this.f4368i = i;
    }

    public int m4057a() {
        return this.f4368i;
    }
}

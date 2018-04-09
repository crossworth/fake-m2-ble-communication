package com.tencent.wxop.stat;

public enum C0895d {
    INSTANT(1),
    ONLY_WIFI(2),
    BATCH(3),
    APP_LAUNCH(4),
    DEVELOPER(5),
    PERIOD(6),
    ONLY_WIFI_NO_CACHE(7);
    
    int aI;

    private C0895d(int i) {
        this.aI = i;
    }

    public static C0895d m2969a(int i) {
        for (C0895d c0895d : C0895d.values()) {
            if (i == c0895d.aI) {
                return c0895d;
            }
        }
        return null;
    }
}

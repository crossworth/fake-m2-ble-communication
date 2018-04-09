package com.tencent.wxop.stat;

public enum StatReportStrategy {
    INSTANT(1),
    ONLY_WIFI(2),
    BATCH(3),
    APP_LAUNCH(4),
    DEVELOPER(5),
    PERIOD(6),
    ONLY_WIFI_NO_CACHE(7);
    
    int f4564a;

    private StatReportStrategy(int i) {
        this.f4564a = i;
    }

    public static StatReportStrategy getStatReportStrategy(int i) {
        for (StatReportStrategy statReportStrategy : values()) {
            if (i == statReportStrategy.m4237a()) {
                return statReportStrategy;
            }
        }
        return null;
    }

    public final int m4237a() {
        return this.f4564a;
    }
}

package com.tencent.stat;

public enum StatReportStrategy {
    INSTANT(1),
    ONLY_WIFI(2),
    BATCH(3),
    APP_LAUNCH(4),
    DEVELOPER(5),
    PERIOD(6),
    ONLY_WIFI_NO_CACHE(7);
    
    int f4327a;

    private StatReportStrategy(int i) {
        this.f4327a = i;
    }

    public static StatReportStrategy getStatReportStrategy(int i) {
        for (StatReportStrategy statReportStrategy : values()) {
            if (i == statReportStrategy.m4028a()) {
                return statReportStrategy;
            }
        }
        return null;
    }

    public int m4028a() {
        return this.f4327a;
    }
}

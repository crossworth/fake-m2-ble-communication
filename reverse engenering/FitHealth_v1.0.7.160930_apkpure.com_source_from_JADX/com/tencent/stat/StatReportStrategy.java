package com.tencent.stat;

public enum StatReportStrategy {
    INSTANT(1),
    ONLY_WIFI(2),
    BATCH(3),
    APP_LAUNCH(4),
    DEVELOPER(5),
    PERIOD(6),
    ONLY_WIFI_NO_CACHE(7);
    
    int f2825a;

    private StatReportStrategy(int i) {
        this.f2825a = i;
    }

    public static StatReportStrategy getStatReportStrategy(int i) {
        for (StatReportStrategy statReportStrategy : values()) {
            if (i == statReportStrategy.m2644a()) {
                return statReportStrategy;
            }
        }
        return null;
    }

    public int m2644a() {
        return this.f2825a;
    }
}

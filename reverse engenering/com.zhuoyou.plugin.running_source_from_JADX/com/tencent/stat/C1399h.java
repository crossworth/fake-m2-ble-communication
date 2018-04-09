package com.tencent.stat;

/* synthetic */ class C1399h {
    static final /* synthetic */ int[] f4462a = new int[StatReportStrategy.values().length];

    static {
        try {
            f4462a[StatReportStrategy.INSTANT.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            f4462a[StatReportStrategy.ONLY_WIFI.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            f4462a[StatReportStrategy.APP_LAUNCH.ordinal()] = 3;
        } catch (NoSuchFieldError e3) {
        }
        try {
            f4462a[StatReportStrategy.DEVELOPER.ordinal()] = 4;
        } catch (NoSuchFieldError e4) {
        }
        try {
            f4462a[StatReportStrategy.BATCH.ordinal()] = 5;
        } catch (NoSuchFieldError e5) {
        }
        try {
            f4462a[StatReportStrategy.PERIOD.ordinal()] = 6;
        } catch (NoSuchFieldError e6) {
        }
        try {
            f4462a[StatReportStrategy.ONLY_WIFI_NO_CACHE.ordinal()] = 7;
        } catch (NoSuchFieldError e7) {
        }
    }
}

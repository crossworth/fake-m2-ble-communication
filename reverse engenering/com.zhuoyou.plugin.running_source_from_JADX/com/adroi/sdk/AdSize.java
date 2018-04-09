package com.adroi.sdk;

public enum AdSize {
    Banner(1),
    SplashAd(2),
    InterstitialAd(3);
    
    public static final int ICON_TEXT = 256;
    public static final int IMAGE = 16;
    public static final int TEXT = 1;
    private int f9a;

    public int getValue() {
        return this.f9a;
    }

    private AdSize(int i) {
        this.f9a = 0;
        this.f9a = i;
    }
}

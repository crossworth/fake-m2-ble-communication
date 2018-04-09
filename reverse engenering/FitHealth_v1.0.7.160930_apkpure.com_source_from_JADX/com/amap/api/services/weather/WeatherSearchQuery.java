package com.amap.api.services.weather;

import com.amap.api.services.proguard.C0390i;

public class WeatherSearchQuery implements Cloneable {
    public static final int WEATHER_TYPE_FORECAST = 2;
    public static final int WEATHER_TYPE_LIVE = 1;
    private String f1682a;
    private int f1683b = 1;

    public WeatherSearchQuery(String str, int i) {
        this.f1682a = str;
        this.f1683b = i;
    }

    public String getCity() {
        return this.f1682a;
    }

    public int getType() {
        return this.f1683b;
    }

    public WeatherSearchQuery clone() {
        try {
            super.clone();
        } catch (Throwable e) {
            C0390i.m1594a(e, "WeatherSearchQuery", "clone");
        }
        return new WeatherSearchQuery(this.f1682a, this.f1683b);
    }
}

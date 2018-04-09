package com.amap.api.location;

import com.amap.api.location.core.AMapLocException;

public class AMapLocalWeatherLive {
    private String f19a;
    private String f20b;
    private String f21c;
    private String f22d;
    private String f23e;
    private String f24f;
    private AMapLocException f25g;
    private String f26h;
    private String f27i;
    private String f28j;

    public String getCity() {
        return this.f26h;
    }

    public void setCity(String str) {
        this.f26h = str;
    }

    public String getProvince() {
        return this.f27i;
    }

    public void setProvince(String str) {
        this.f27i = str;
    }

    public String getCityCode() {
        return this.f28j;
    }

    public void setCityCode(String str) {
        this.f28j = str;
    }

    public AMapLocException getAMapException() {
        return this.f25g;
    }

    void m22a(AMapLocException aMapLocException) {
        this.f25g = aMapLocException;
    }

    public String getWeather() {
        return this.f19a;
    }

    void m23a(String str) {
        this.f19a = str;
    }

    public String getTemperature() {
        return this.f20b;
    }

    void m24b(String str) {
        this.f20b = str;
    }

    public String getWindDir() {
        return this.f21c;
    }

    void m25c(String str) {
        this.f21c = str;
    }

    public String getWindPower() {
        return this.f22d;
    }

    void m26d(String str) {
        this.f22d = str;
    }

    public String getHumidity() {
        return this.f23e;
    }

    void m27e(String str) {
        this.f23e = str;
    }

    public String getReportTime() {
        return this.f24f;
    }

    void m28f(String str) {
        this.f24f = str;
    }
}

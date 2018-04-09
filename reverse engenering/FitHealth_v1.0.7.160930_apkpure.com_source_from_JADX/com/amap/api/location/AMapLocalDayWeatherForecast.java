package com.amap.api.location;

public class AMapLocalDayWeatherForecast {
    private String f3a;
    private String f4b;
    private String f5c;
    private String f6d;
    private String f7e;
    private String f8f;
    private String f9g;
    private String f10h;
    private String f11i;
    private String f12j;
    private String f13k;
    private String f14l;
    private String f15m;

    public String getCity() {
        return this.f13k;
    }

    public void setCity(String str) {
        this.f13k = str;
    }

    public String getProvince() {
        return this.f14l;
    }

    public void setProvince(String str) {
        this.f14l = str;
    }

    public String getCityCode() {
        return this.f15m;
    }

    public void setCityCode(String str) {
        this.f15m = str;
    }

    public String getDate() {
        return this.f3a;
    }

    void m9a(String str) {
        this.f3a = str;
    }

    public String getWeek() {
        return this.f4b;
    }

    void m10b(String str) {
        this.f4b = str;
    }

    public String getDayWeather() {
        return this.f5c;
    }

    void m11c(String str) {
        this.f5c = str;
    }

    public String getNightWeather() {
        return this.f6d;
    }

    void m12d(String str) {
        this.f6d = str;
    }

    public String getDayTemp() {
        return this.f7e;
    }

    void m13e(String str) {
        this.f7e = str;
    }

    public String getNightTemp() {
        return this.f8f;
    }

    void m14f(String str) {
        this.f8f = str;
    }

    public String getDayWindDir() {
        return this.f9g;
    }

    void m15g(String str) {
        this.f9g = str;
    }

    public String getNightWindDir() {
        return this.f10h;
    }

    void m16h(String str) {
        this.f10h = str;
    }

    public String getDayWindPower() {
        return this.f11i;
    }

    void m17i(String str) {
        this.f11i = str;
    }

    public String getNightWindPower() {
        return this.f12j;
    }

    void m18j(String str) {
        this.f12j = str;
    }
}

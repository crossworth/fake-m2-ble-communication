package com.amap.api.services.core;

public class SuggestionCity {
    private String f1129a;
    private String f1130b;
    private String f1131c;
    private int f1132d;

    protected SuggestionCity() {
    }

    public SuggestionCity(String str, String str2, String str3, int i) {
        this.f1129a = str;
        this.f1130b = str2;
        this.f1131c = str3;
        this.f1132d = i;
    }

    public String getCityName() {
        return this.f1129a;
    }

    public void setCityName(String str) {
        this.f1129a = str;
    }

    public String getCityCode() {
        return this.f1130b;
    }

    public void setCityCode(String str) {
        this.f1130b = str;
    }

    public String getAdCode() {
        return this.f1131c;
    }

    public void setAdCode(String str) {
        this.f1131c = str;
    }

    public int getSuggestionNum() {
        return this.f1132d;
    }

    public void setSuggestionNum(int i) {
        this.f1132d = i;
    }
}

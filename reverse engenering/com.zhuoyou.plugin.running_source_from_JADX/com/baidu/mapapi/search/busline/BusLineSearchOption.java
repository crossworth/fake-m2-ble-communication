package com.baidu.mapapi.search.busline;

public class BusLineSearchOption {
    String f1519a = null;
    String f1520b = null;

    public BusLineSearchOption city(String str) {
        this.f1520b = str;
        return this;
    }

    public BusLineSearchOption uid(String str) {
        this.f1519a = str;
        return this;
    }
}

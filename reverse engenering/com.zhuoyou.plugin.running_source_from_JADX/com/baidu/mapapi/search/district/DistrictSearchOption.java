package com.baidu.mapapi.search.district;

public class DistrictSearchOption {
    String f1573a;
    String f1574b;

    public DistrictSearchOption cityName(String str) {
        this.f1573a = str;
        return this;
    }

    public DistrictSearchOption districtName(String str) {
        this.f1574b = str;
        return this;
    }
}

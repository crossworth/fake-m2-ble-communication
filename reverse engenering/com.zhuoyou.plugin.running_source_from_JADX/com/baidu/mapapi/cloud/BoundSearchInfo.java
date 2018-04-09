package com.baidu.mapapi.cloud;

public class BoundSearchInfo extends BaseCloudSearchInfo {
    public String bound;

    public BoundSearchInfo() {
        this.a = "http://api.map.baidu.com/geosearch/v2/bound";
    }

    String mo1757a() {
        StringBuilder stringBuilder = new StringBuilder();
        if (super.mo1757a() == null) {
            return null;
        }
        stringBuilder.append(super.mo1757a());
        if (this.bound == null || this.bound.equals("")) {
            return null;
        }
        stringBuilder.append("&");
        stringBuilder.append("bounds");
        stringBuilder.append("=");
        stringBuilder.append(this.bound);
        return stringBuilder.toString();
    }
}

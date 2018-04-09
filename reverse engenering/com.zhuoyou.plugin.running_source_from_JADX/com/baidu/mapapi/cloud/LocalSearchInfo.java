package com.baidu.mapapi.cloud;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LocalSearchInfo extends BaseCloudSearchInfo {
    public String region;

    public LocalSearchInfo() {
        this.a = "http://api.map.baidu.com/geosearch/v2/local";
    }

    String mo1757a() {
        StringBuilder stringBuilder = new StringBuilder();
        if (super.mo1757a() == null) {
            return null;
        }
        stringBuilder.append(super.mo1757a());
        if (this.region == null || this.region.equals("") || this.region.length() > 25) {
            return null;
        }
        stringBuilder.append("&");
        stringBuilder.append("region");
        stringBuilder.append("=");
        try {
            stringBuilder.append(URLEncoder.encode(this.region, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}

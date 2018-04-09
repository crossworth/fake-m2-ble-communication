package com.baidu.mapapi.cloud;

import com.umeng.socialize.common.SocializeConstants;

public class NearbySearchInfo extends BaseCloudSearchInfo {
    public String location;
    public int radius;

    public NearbySearchInfo() {
        this.a = "http://api.map.baidu.com/geosearch/v2/nearby";
        this.radius = 1000;
    }

    String mo1757a() {
        StringBuilder stringBuilder = new StringBuilder();
        if (super.mo1757a() == null) {
            return null;
        }
        stringBuilder.append(super.mo1757a());
        if (this.location == null || this.location.equals("")) {
            return null;
        }
        stringBuilder.append("&");
        stringBuilder.append(SocializeConstants.KEY_LOCATION);
        stringBuilder.append("=");
        stringBuilder.append(this.location);
        if (this.radius >= 0) {
            stringBuilder.append("&");
            stringBuilder.append("radius");
            stringBuilder.append("=");
            stringBuilder.append(this.radius);
        }
        return stringBuilder.toString();
    }
}

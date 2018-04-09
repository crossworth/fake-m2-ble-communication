package com.baidu.mapapi.cloud;

import com.umeng.socialize.net.utils.SocializeProtocolConstants;

public class DetailSearchInfo extends BaseSearchInfo {
    public int uid;

    public DetailSearchInfo() {
        this.a = "http://api.map.baidu.com/geosearch/v2/detail/";
    }

    String mo1757a() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.a);
        stringBuilder.append(this.uid).append('?');
        if (this.ak == null || this.ak.equals("") || this.ak.length() > 50) {
            return null;
        }
        stringBuilder.append(SocializeProtocolConstants.PROTOCOL_KEY_AK);
        stringBuilder.append("=");
        stringBuilder.append(this.ak);
        if (this.geoTableId == 0) {
            return null;
        }
        stringBuilder.append("&");
        stringBuilder.append("geotable_id");
        stringBuilder.append("=");
        stringBuilder.append(this.geoTableId);
        if (!(this.sn == null || this.sn.equals("") || this.sn.length() > 50)) {
            stringBuilder.append("&");
            stringBuilder.append("sn");
            stringBuilder.append("=");
            stringBuilder.append(this.sn);
        }
        return stringBuilder.toString();
    }
}

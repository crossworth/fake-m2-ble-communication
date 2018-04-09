package com.baidu.mapapi.map;

import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import org.json.JSONObject;

public final class MapPoi {
    private static final String f1141d = MapPoi.class.getSimpleName();
    String f1142a;
    LatLng f1143b;
    String f1144c;

    void m1144a(JSONObject jSONObject) {
        this.f1142a = jSONObject.optString("tx");
        this.f1143b = CoordUtil.decodeNodeLocation(jSONObject.optString("geo"));
        this.f1144c = jSONObject.optString("ud");
    }

    public String getName() {
        return this.f1142a;
    }

    public LatLng getPosition() {
        return this.f1143b;
    }

    public String getUid() {
        return this.f1144c;
    }
}

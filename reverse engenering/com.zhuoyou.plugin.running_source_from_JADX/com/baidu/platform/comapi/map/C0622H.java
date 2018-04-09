package com.baidu.platform.comapi.map;

import android.graphics.Point;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comjni.map.basemap.C0673a;
import org.json.JSONException;
import org.json.JSONObject;

public class C0622H {
    private C0673a f1993a;

    public C0622H(C0673a c0673a) {
        this.f1993a = c0673a;
    }

    public Point m1919a(GeoPoint geoPoint) {
        if (geoPoint == null) {
            return null;
        }
        Point point = new Point(0, 0);
        String b = this.f1993a.m2213b((int) geoPoint.getLongitudeE6(), (int) geoPoint.getLatitudeE6());
        if (b == null) {
            return point;
        }
        try {
            JSONObject jSONObject = new JSONObject(b);
            point.x = jSONObject.getInt("scrx");
            point.y = jSONObject.getInt("scry");
            return point;
        } catch (JSONException e) {
            e.printStackTrace();
            return point;
        }
    }

    public GeoPoint m1920a(int i, int i2) {
        String a = this.f1993a.m2197a(i, i2);
        GeoPoint geoPoint = new GeoPoint(0.0d, 0.0d);
        if (a != null) {
            try {
                JSONObject jSONObject = new JSONObject(a);
                geoPoint.setLongitudeE6((double) jSONObject.getInt("geox"));
                geoPoint.setLatitudeE6((double) jSONObject.getInt("geoy"));
                return geoPoint;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

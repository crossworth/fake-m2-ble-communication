package com.amap.api.maps;

import android.content.Context;
import com.amap.api.mapcore.util.bb;
import com.amap.api.mapcore.util.ee;
import com.amap.api.maps.model.LatLng;

public class CoordinateConverter {
    private Context f802a;
    private CoordType f803b = null;
    private LatLng f804c = null;

    public enum CoordType {
        BAIDU,
        MAPBAR,
        GPS,
        MAPABC,
        SOSOMAP,
        ALIYUN,
        GOOGLE
    }

    public CoordinateConverter(Context context) {
        this.f802a = context;
    }

    public CoordinateConverter from(CoordType coordType) {
        this.f803b = coordType;
        return this;
    }

    public CoordinateConverter coord(LatLng latLng) {
        this.f804c = latLng;
        return this;
    }

    public LatLng convert() {
        if (this.f803b == null || this.f804c == null) {
            return null;
        }
        try {
            switch (C0298a.f818a[this.f803b.ordinal()]) {
                case 1:
                    return bb.m246a(this.f804c);
                case 2:
                    return bb.m252b(this.f802a, this.f804c);
                case 3:
                case 4:
                case 5:
                case 6:
                    return this.f804c;
                case 7:
                    return bb.m245a(this.f802a, this.f804c);
                default:
                    return null;
            }
        } catch (Throwable th) {
            th.printStackTrace();
            ee.m4243a(th, "CoordinateConverter", "convert");
            return this.f804c;
        }
    }
}

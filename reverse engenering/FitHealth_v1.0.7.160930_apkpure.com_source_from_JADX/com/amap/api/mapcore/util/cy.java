package com.amap.api.mapcore.util;

import com.autonavi.amap.mapcore.DPoint;

/* compiled from: Bounds */
public class cy {
    public final double f406a;
    public final double f407b;
    public final double f408c;
    public final double f409d;
    public final double f410e;
    public final double f411f;

    public cy(double d, double d2, double d3, double d4) {
        this.f406a = d;
        this.f407b = d3;
        this.f408c = d2;
        this.f409d = d4;
        this.f410e = (d + d2) / 2.0d;
        this.f411f = (d3 + d4) / 2.0d;
    }

    public boolean m473a(double d, double d2) {
        return this.f406a <= d && d <= this.f408c && this.f407b <= d2 && d2 <= this.f409d;
    }

    public boolean m476a(DPoint dPoint) {
        return m473a(dPoint.f2026x, dPoint.f2027y);
    }

    public boolean m474a(double d, double d2, double d3, double d4) {
        return d < this.f408c && this.f406a < d2 && d3 < this.f409d && this.f407b < d4;
    }

    public boolean m475a(cy cyVar) {
        return m474a(cyVar.f406a, cyVar.f408c, cyVar.f407b, cyVar.f409d);
    }

    public boolean m477b(cy cyVar) {
        return cyVar.f406a >= this.f406a && cyVar.f408c <= this.f408c && cyVar.f407b >= this.f407b && cyVar.f409d <= this.f409d;
    }
}

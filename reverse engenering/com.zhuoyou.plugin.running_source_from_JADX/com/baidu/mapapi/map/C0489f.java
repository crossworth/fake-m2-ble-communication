package com.baidu.mapapi.map;

import android.graphics.Point;

class C0489f {
    public final double f1415a;
    public final double f1416b;
    public final double f1417c;
    public final double f1418d;
    public final double f1419e;
    public final double f1420f;

    public C0489f(double d, double d2, double d3, double d4) {
        this.f1415a = d;
        this.f1416b = d3;
        this.f1417c = d2;
        this.f1418d = d4;
        this.f1419e = (d + d2) / 2.0d;
        this.f1420f = (d3 + d4) / 2.0d;
    }

    public boolean m1295a(double d, double d2) {
        return this.f1415a <= d && d <= this.f1417c && this.f1416b <= d2 && d2 <= this.f1418d;
    }

    public boolean m1296a(double d, double d2, double d3, double d4) {
        return d < this.f1417c && this.f1415a < d2 && d3 < this.f1418d && this.f1416b < d4;
    }

    public boolean m1297a(Point point) {
        return m1295a((double) point.x, (double) point.y);
    }

    public boolean m1298a(C0489f c0489f) {
        return m1296a(c0489f.f1415a, c0489f.f1417c, c0489f.f1416b, c0489f.f1418d);
    }

    public boolean m1299b(C0489f c0489f) {
        return c0489f.f1415a >= this.f1415a && c0489f.f1417c <= this.f1417c && c0489f.f1416b >= this.f1416b && c0489f.f1418d <= this.f1418d;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("minX: " + this.f1415a);
        stringBuilder.append(" minY: " + this.f1416b);
        stringBuilder.append(" maxX: " + this.f1417c);
        stringBuilder.append(" maxY: " + this.f1418d);
        stringBuilder.append(" midX: " + this.f1419e);
        stringBuilder.append(" midY: " + this.f1420f);
        return stringBuilder.toString();
    }
}

package com.amap.api.mapcore.util;

import android.content.Context;
import android.text.TextUtils;
import com.amap.api.maps.model.LatLng;
import com.autonavi.amap.mapcore.CoordUtil;
import com.autonavi.amap.mapcore.DPoint;
import java.io.File;
import java.math.BigDecimal;

/* compiled from: OffsetUtil */
public class bb {
    static double f234a = 3.141592653589793d;
    private static boolean f235b = false;

    public static LatLng m245a(Context context, LatLng latLng) {
        if (context == null) {
            return null;
        }
        String a = du.m4221a(context, "libwgs2gcj.so");
        if (!(TextUtils.isEmpty(a) || !new File(a).exists() || f235b)) {
            try {
                System.load(a);
                f235b = true;
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        DPoint a2 = m249a(new DPoint(latLng.longitude, latLng.latitude), f235b);
        return new LatLng(a2.f2027y, a2.f2026x, false);
    }

    private static DPoint m249a(DPoint dPoint, boolean z) {
        double[] dArr;
        double[] dArr2;
        try {
            dArr2 = new double[2];
            if (z) {
                if (CoordUtil.convertToGcj(new double[]{dPoint.f2026x, dPoint.f2027y}, dArr2) != 0) {
                    dArr2 = gd.m996a(dPoint.f2026x, dPoint.f2027y);
                }
                dArr = dArr2;
            } else {
                dArr = gd.m996a(dPoint.f2026x, dPoint.f2027y);
            }
        } catch (Throwable th) {
            return dPoint;
        }
        return new DPoint(dArr[0], dArr[1]);
    }

    public static LatLng m252b(Context context, LatLng latLng) {
        try {
            DPoint c = m253c(latLng.longitude, latLng.latitude);
            latLng = m245a(context, new LatLng(c.f2027y, c.f2026x, false));
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return latLng;
    }

    public static double m243a(double d, double d2) {
        return (Math.cos(d2 / 100000.0d) * (d / 18000.0d)) + (Math.sin(d / 100000.0d) * (d2 / 9000.0d));
    }

    public static double m251b(double d, double d2) {
        return (Math.sin(d2 / 100000.0d) * (d / 18000.0d)) + (Math.cos(d / 100000.0d) * (d2 / 9000.0d));
    }

    private static DPoint m253c(double d, double d2) {
        double d3 = (double) (((long) (100000.0d * d)) % 36000000);
        double d4 = (double) (((long) (100000.0d * d2)) % 36000000);
        int i = (int) ((-m251b(d3, d4)) + d4);
        int i2 = (int) (((double) (d3 > 0.0d ? 1 : -1)) + ((-m243a((double) ((int) ((-m243a(d3, d4)) + d3)), (double) i)) + d3));
        return new DPoint(((double) i2) / 100000.0d, ((double) ((int) (((double) (d4 > 0.0d ? 1 : -1)) + ((-m251b((double) i2, (double) i)) + d4)))) / 100000.0d);
    }

    public static LatLng m246a(LatLng latLng) {
        if (latLng != null) {
            try {
                DPoint a = m248a(new DPoint(latLng.longitude, latLng.latitude), 2);
                return new LatLng(a.f2027y, a.f2026x, false);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return latLng;
    }

    private static double m242a(double d) {
        return Math.sin((3000.0d * d) * (f234a / 180.0d)) * 2.0E-5d;
    }

    private static double m250b(double d) {
        return Math.cos((3000.0d * d) * (f234a / 180.0d)) * 3.0E-6d;
    }

    private static DPoint m254d(double d, double d2) {
        DPoint dPoint = new DPoint();
        double sin = (Math.sin(m250b(d) + Math.atan2(d2, d)) * (m242a(d2) + Math.sqrt((d * d) + (d2 * d2)))) + 0.006d;
        dPoint.f2026x = m244a((Math.cos(m250b(d) + Math.atan2(d2, d)) * (m242a(d2) + Math.sqrt((d * d) + (d2 * d2)))) + 0.0065d, 8);
        dPoint.f2027y = m244a(sin, 8);
        return dPoint;
    }

    private static double m244a(double d, int i) {
        return new BigDecimal(d).setScale(i, 4).doubleValue();
    }

    private static DPoint m248a(DPoint dPoint, int i) {
        double d = 0.006401062d;
        double d2 = 0.0060424805d;
        int i2 = 0;
        DPoint dPoint2 = null;
        while (i2 < i) {
            DPoint a = m247a(dPoint.f2026x, dPoint.f2027y, d, d2);
            d = dPoint.f2026x - a.f2026x;
            d2 = dPoint.f2027y - a.f2027y;
            i2++;
            dPoint2 = a;
        }
        return dPoint2;
    }

    private static DPoint m247a(double d, double d2, double d3, double d4) {
        DPoint dPoint = new DPoint();
        double d5 = d - d3;
        double d6 = d2 - d4;
        DPoint d7 = m254d(d5, d6);
        dPoint.f2026x = m244a((d5 + d) - d7.f2026x, 8);
        dPoint.f2027y = m244a((d2 + d6) - d7.f2027y, 8);
        return dPoint;
    }
}

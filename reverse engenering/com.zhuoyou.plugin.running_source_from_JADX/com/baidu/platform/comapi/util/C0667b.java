package com.baidu.platform.comapi.util;

import android.os.Bundle;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.C0513a;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.model.inner.Point;
import com.baidu.platform.comapi.location.CoordinateType;
import com.baidu.platform.comjni.tools.C0680a;
import com.baidu.platform.comjni.tools.JNITools;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class C0667b {
    static Bundle f2174a = new Bundle();
    static double[] f2175b = new double[]{1.289059486E7d, 8362377.87d, 5591021.0d, 3481989.83d, 1678043.12d, 0.0d};
    static double[] f2176c = new double[]{7.5E7d, 6.0E7d, 4.5E7d, 3.0E7d, 1.5E7d, 0.0d};
    static double[][] f2177d = new double[][]{new double[]{1.410526172116255E-8d, 8.98305509648872E-6d, -1.9939833816331d, 200.9824383106796d, -187.2403703815547d, 91.6087516669843d, -23.38765649603339d, 2.57121317296198d, -0.03801003308653d, 1.73379812E7d}, new double[]{-7.435856389565537E-9d, 8.983055097726239E-6d, -0.78625201886289d, 96.32687599759846d, -1.85204757529826d, -59.36935905485877d, 47.40033549296737d, -16.50741931063887d, 2.28786674699375d, 1.026014486E7d}, new double[]{-3.030883460898826E-8d, 8.98305509983578E-6d, 0.30071316287616d, 59.74293618442277d, 7.357984074871d, -25.38371002664745d, 13.45380521110908d, -3.29883767235584d, 0.32710905363475d, 6856817.37d}, new double[]{-1.981981304930552E-8d, 8.983055099779535E-6d, 0.03278182852591d, 40.31678527705744d, 0.65659298677277d, -4.44255534477492d, 0.85341911805263d, 0.12923347998204d, -0.04625736007561d, 4482777.06d}, new double[]{3.09191371068437E-9d, 8.983055096812155E-6d, 6.995724062E-5d, 23.10934304144901d, -2.3663490511E-4d, -0.6321817810242d, -0.00663494467273d, 0.03430082397953d, -0.00466043876332d, 2555164.4d}, new double[]{2.890871144776878E-9d, 8.983055095805407E-6d, -3.068298E-8d, 7.47137025468032d, -3.53937994E-6d, -0.02145144861037d, -1.234426596E-5d, 1.0322952773E-4d, -3.23890364E-6d, 826088.5d}};
    static double[][] f2178e = new double[][]{new double[]{-0.0015702102444d, 111320.7020616939d, 1.704480524535203E15d, -1.033898737604234E16d, 2.611266785660388E16d, -3.51496691766537E16d, 2.659570071840392E16d, -1.072501245418824E16d, 1.800819912950474E15d, 82.5d}, new double[]{8.277824516172526E-4d, 111320.7020463578d, 6.477955746671607E8d, -4.082003173641316E9d, 1.077490566351142E10d, -1.517187553151559E10d, 1.205306533862167E10d, -5.124939663577472E9d, 9.133119359512032E8d, 67.5d}, new double[]{0.00337398766765d, 111320.7020202162d, 4481351.045890365d, -2.339375119931662E7d, 7.968221547186455E7d, -1.159649932797253E8d, 9.723671115602145E7d, -4.366194633752821E7d, 8477230.501135234d, 52.5d}, new double[]{0.00220636496208d, 111320.7020209128d, 51751.86112841131d, 3796837.749470245d, 992013.7397791013d, -1221952.21711287d, 1340652.697009075d, -620943.6990984312d, 144416.9293806241d, 37.5d}, new double[]{-3.441963504368392E-4d, 111320.7020576856d, 278.2353980772752d, 2485758.690035394d, 6070.750963243378d, 54821.18345352118d, 9540.606633304236d, -2710.55326746645d, 1405.483844121726d, 22.5d}, new double[]{-3.218135878613132E-4d, 111320.7020701615d, 0.00369383431289d, 823725.6402795718d, 0.46104986909093d, 2351.343141331292d, 1.58060784298199d, 8.77738589078284d, 0.37238884252424d, 7.45d}};

    static class C0666a {
        double f2172a;
        double f2173b;

        C0666a() {
        }
    }

    public static int m2140a(LatLng latLng, int i) {
        LatLng latLng2 = new LatLng((((double) i) / 111000.0d) + latLng.latitude, latLng.longitude);
        GeoPoint a = C0667b.m2143a(latLng);
        GeoPoint a2 = C0667b.m2143a(latLng2);
        return (int) Math.sqrt(Math.pow(a.getLongitudeE6() - a2.getLongitudeE6(), 2.0d) + Math.pow(a.getLatitudeE6() - a2.getLatitudeE6(), 2.0d));
    }

    public static LatLng m2141a(GeoPoint geoPoint) {
        C0666a c0666a = new C0666a();
        c0666a.f2172a = geoPoint.getLongitudeE6();
        c0666a.f2173b = geoPoint.getLatitudeE6();
        C0666a c0666a2 = new C0666a();
        c0666a2.f2172a = c0666a.f2172a;
        if (c0666a2.f2172a > 2.0037508342E7d) {
            c0666a2.f2172a = 2.0037508342E7d;
        } else if (c0666a2.f2172a < -2.0037508342E7d) {
            c0666a2.f2172a = -2.0037508342E7d;
        }
        c0666a2.f2173b = c0666a.f2173b;
        if (c0666a2.f2173b < 1.0E-6d && c0666a2.f2173b >= 0.0d) {
            c0666a2.f2173b = 1.0E-6d;
        } else if (c0666a2.f2173b < 0.0d && c0666a2.f2173b > -1.0E-6d) {
            c0666a2.f2173b = -1.0E-6d;
        } else if (c0666a2.f2173b > 2.0037508342E7d) {
            c0666a2.f2173b = 2.0037508342E7d;
        } else if (c0666a2.f2173b < -2.0037508342E7d) {
            c0666a2.f2173b = -2.0037508342E7d;
        }
        double[] dArr = new double[10];
        for (int i = 0; i < 6; i++) {
            if (Math.abs(c0666a2.f2173b) > f2175b[i]) {
                dArr = f2177d[i];
                break;
            }
        }
        c0666a = C0667b.m2145a(c0666a2, dArr);
        return new LatLng(c0666a.f2173b, c0666a.f2172a);
    }

    public static LatLng m2142a(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        f2174a.clear();
        f2174a.putString("strkey", str);
        JNITools.TransGeoStr2Pt(f2174a);
        GeoPoint geoPoint = new GeoPoint(0.0d, 0.0d);
        geoPoint.setLongitudeE6((double) f2174a.getInt("ptx"));
        geoPoint.setLatitudeE6((double) f2174a.getInt("pty"));
        return C0667b.m2141a(geoPoint);
    }

    public static GeoPoint m2143a(LatLng latLng) {
        double[] dArr;
        C0666a c0666a = new C0666a();
        double[] dArr2 = new double[10];
        c0666a.f2173b = Math.abs(latLng.latitude * 1000000.0d);
        if (c0666a.f2173b < 0.1d) {
            c0666a.f2173b = 0.1d;
        }
        for (int i = 0; i < f2176c.length; i++) {
            if (c0666a.f2173b > f2176c[i]) {
                dArr = f2178e[i];
                break;
            }
        }
        dArr = dArr2;
        c0666a.f2172a = latLng.longitude;
        c0666a.f2173b = latLng.latitude;
        C0666a a = C0667b.m2145a(c0666a, dArr);
        return new GeoPoint(a.f2173b, a.f2172a);
    }

    public static Point m2144a(float f, float f2, String str) {
        if (str == null) {
            return null;
        }
        if (str.equals("")) {
            str = "bd09ll";
        }
        if (!str.equals("bd09ll") && !str.equals(CoordinateType.BD09MC) && !str.equals(CoordinateType.GCJ02) && !str.equals(CoordinateType.WGS84)) {
            return null;
        }
        if (str.equals(CoordinateType.BD09MC)) {
            return new Point((int) f, (int) f2);
        }
        Bundle bundle = new Bundle();
        JNITools.CoordinateEncryptEx(f, f2, str, bundle);
        if (bundle.isEmpty()) {
            return null;
        }
        Point point = new Point(0, 0);
        point.setmPtx((int) bundle.getDouble("x"));
        point.setmPty((int) bundle.getDouble("y"));
        return point;
    }

    static C0666a m2145a(C0666a c0666a, double[] dArr) {
        int i = -1;
        C0666a c0666a2 = new C0666a();
        c0666a2.f2172a = dArr[0] + (dArr[1] * Math.abs(c0666a.f2172a));
        double abs = Math.abs(c0666a.f2173b) / dArr[9];
        c0666a2.f2173b = (abs * (((((dArr[8] * abs) * abs) * abs) * abs) * abs)) + (((((dArr[2] + (dArr[3] * abs)) + ((dArr[4] * abs) * abs)) + (((dArr[5] * abs) * abs) * abs)) + ((((dArr[6] * abs) * abs) * abs) * abs)) + (((((dArr[7] * abs) * abs) * abs) * abs) * abs));
        c0666a2.f2172a *= (double) (c0666a.f2172a < 0.0d ? -1 : 1);
        abs = c0666a2.f2173b;
        if (c0666a.f2173b >= 0.0d) {
            i = 1;
        }
        c0666a2.f2173b = ((double) i) * abs;
        return c0666a2;
    }

    public static LatLng m2146b(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        f2174a.clear();
        f2174a.putString("strkey", str);
        JNITools.TransNodeStr2Pt(f2174a);
        return C0667b.m2141a(new GeoPoint(f2174a.getDouble("pty"), f2174a.getDouble("ptx")));
    }

    public static Point m2147b(LatLng latLng) {
        return latLng == null ? null : C0667b.m2144a((float) latLng.longitude, (float) latLng.latitude, "bd09ll");
    }

    public static List<LatLng> m2148c(String str) {
        C0513a a = C0680a.m2303a(str);
        List<LatLng> arrayList = new ArrayList();
        if (a == null || a.f1470d == null) {
            return null;
        }
        ArrayList arrayList2 = a.f1470d;
        if (arrayList2.size() > 0) {
            arrayList2 = (ArrayList) arrayList2.get(0);
            for (int i = 0; i < arrayList2.size(); i++) {
                Point point = (Point) arrayList2.get(i);
                arrayList.add(C0667b.m2141a(new GeoPoint((double) (point.f1466y / 100), (double) (point.f1465x / 100))));
            }
        }
        return arrayList;
    }

    public static List<List<LatLng>> m2149d(String str) {
        C0513a a = C0680a.m2303a(str);
        if (a == null || a.f1470d == null) {
            return null;
        }
        ArrayList arrayList = a.f1470d;
        List<List<LatLng>> arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList = (ArrayList) it.next();
            List arrayList3 = new ArrayList();
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                Point point = (Point) it2.next();
                arrayList3.add(C0667b.m2141a(new GeoPoint((double) (point.f1466y / 100), (double) (point.f1465x / 100))));
            }
            arrayList2.add(arrayList3);
        }
        return arrayList2;
    }
}

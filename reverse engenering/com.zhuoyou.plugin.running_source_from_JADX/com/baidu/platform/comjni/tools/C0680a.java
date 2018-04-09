package com.baidu.platform.comjni.tools;

import android.os.Bundle;
import com.baidu.mapapi.model.inner.C0513a;
import com.baidu.mapapi.model.inner.Point;
import java.util.ArrayList;

public class C0680a {
    public static double m2302a(Point point, Point point2) {
        Bundle bundle = new Bundle();
        bundle.putDouble("x1", (double) point.f1465x);
        bundle.putDouble("y1", (double) point.f1466y);
        bundle.putDouble("x2", (double) point2.f1465x);
        bundle.putDouble("y2", (double) point2.f1466y);
        JNITools.GetDistanceByMC(bundle);
        return bundle.getDouble("distance");
    }

    public static C0513a m2303a(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        Bundle bundle;
        Bundle bundle2 = new Bundle();
        bundle2.putString("strkey", str);
        JNITools.TransGeoStr2ComplexPt(bundle2);
        C0513a c0513a = new C0513a();
        Bundle bundle3 = bundle2.getBundle("map_bound");
        if (bundle3 != null) {
            bundle = bundle3.getBundle("ll");
            if (bundle != null) {
                c0513a.f1468b = new Point((int) bundle.getDouble("ptx"), (int) bundle.getDouble("pty"));
            }
            bundle3 = bundle3.getBundle("ru");
            if (bundle3 != null) {
                c0513a.f1469c = new Point((int) bundle3.getDouble("ptx"), (int) bundle3.getDouble("pty"));
            }
        }
        ParcelItem[] parcelItemArr = (ParcelItem[]) bundle2.getParcelableArray("poly_line");
        for (ParcelItem bundle4 : parcelItemArr) {
            if (c0513a.f1470d == null) {
                c0513a.f1470d = new ArrayList();
            }
            bundle = bundle4.getBundle();
            if (bundle != null) {
                ParcelItem[] parcelItemArr2 = (ParcelItem[]) bundle.getParcelableArray("point_array");
                ArrayList arrayList = new ArrayList();
                for (ParcelItem bundle5 : parcelItemArr2) {
                    Bundle bundle6 = bundle5.getBundle();
                    if (bundle6 != null) {
                        arrayList.add(new Point((int) bundle6.getDouble("ptx"), (int) bundle6.getDouble("pty")));
                    }
                }
                arrayList.trimToSize();
                c0513a.f1470d.add(arrayList);
            }
        }
        c0513a.f1470d.trimToSize();
        c0513a.f1467a = (int) bundle2.getDouble("type");
        return c0513a;
    }

    public static String m2304a() {
        return JNITools.GetToken();
    }

    public static void m2305a(boolean z, int i) {
        JNITools.openLogEnable(z, i);
    }
}

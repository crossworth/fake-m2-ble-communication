package com.aps;

import android.util.SparseArray;

/* compiled from: Const */
public class C0446f {
    static String f1847a = null;
    static String f1848b = null;
    static String f1849c = null;
    static String f1850d = "";
    static String f1851e = "";
    static String f1852f = "";
    static boolean f1853g = false;
    static boolean f1854h = true;
    static long f1855i = 10000;
    static long f1856j = 30000;
    static boolean f1857k = true;
    static final SparseArray<String> f1858l = new SparseArray();
    static final String[] f1859m = new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS", "android.permission.ACCESS_NETWORK_STATE", "android.permission.ACCESS_WIFI_STATE", "android.permission.CHANGE_WIFI_STATE", "android.permission.INTERNET", "android.permission.READ_PHONE_STATE", "android.permission.WRITE_EXTERNAL_STORAGE"};

    private C0446f() {
    }

    static {
        f1858l.append(0, "UNKNOWN");
        f1858l.append(1, "GPRS");
        f1858l.append(2, "EDGE");
        f1858l.append(3, "UMTS");
        f1858l.append(4, "CDMA");
        f1858l.append(5, "EVDO_0");
        f1858l.append(6, "EVDO_A");
        f1858l.append(7, "1xRTT");
        f1858l.append(8, "HSDPA");
        f1858l.append(9, "HSUPA");
        f1858l.append(10, "HSPA");
        f1858l.append(11, "IDEN");
        f1858l.append(12, "EVDO_B");
        f1858l.append(13, "LTE");
        f1858l.append(14, "EHRPD");
        f1858l.append(15, "HSPAP");
    }

    static void m1901a(String str) {
        f1850d = str;
    }

    static void m1903b(String str) {
        f1851e = str;
    }

    static void m1904c(String str) {
        f1852f = str;
    }

    static void m1902a(boolean z) {
        f1853g = z;
    }
}

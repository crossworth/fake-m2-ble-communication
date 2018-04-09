package com.tencent.mm.sdk.p017b;

import android.os.Build;
import android.os.Build.VERSION;
import android.os.Looper;
import android.os.Process;

public final class C0765a {
    private static int level = 6;
    public static C0767d f2643q;
    private static C0764a f2644r;
    private static C0764a f2645s;
    private static final String f2646t;

    public interface C0764a {
        void mo2103e(String str, String str2);

        void mo2104f(String str, String str2);

        void mo2105g(String str, String str2);

        int mo2106h();

        void mo2107h(String str, String str2);
    }

    static {
        C0764a c1723b = new C1723b();
        f2644r = c1723b;
        f2645s = c1723b;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("VERSION.RELEASE:[" + VERSION.RELEASE);
        stringBuilder.append("] VERSION.CODENAME:[" + VERSION.CODENAME);
        stringBuilder.append("] VERSION.INCREMENTAL:[" + VERSION.INCREMENTAL);
        stringBuilder.append("] BOARD:[" + Build.BOARD);
        stringBuilder.append("] DEVICE:[" + Build.DEVICE);
        stringBuilder.append("] DISPLAY:[" + Build.DISPLAY);
        stringBuilder.append("] FINGERPRINT:[" + Build.FINGERPRINT);
        stringBuilder.append("] HOST:[" + Build.HOST);
        stringBuilder.append("] MANUFACTURER:[" + Build.MANUFACTURER);
        stringBuilder.append("] MODEL:[" + Build.MODEL);
        stringBuilder.append("] PRODUCT:[" + Build.PRODUCT);
        stringBuilder.append("] TAGS:[" + Build.TAGS);
        stringBuilder.append("] TYPE:[" + Build.TYPE);
        stringBuilder.append("] USER:[" + Build.USER + "]");
        f2646t = stringBuilder.toString();
    }

    public static void m2514a(String str, String str2) {
        C0765a.m2515a(str, str2, null);
    }

    public static void m2515a(String str, String str2, Object... objArr) {
        if (f2645s != null && f2645s.mo2106h() <= 4) {
            String format = objArr == null ? str2 : String.format(str2, objArr);
            if (format == null) {
                format = "";
            }
            String i = C0765a.m2520i(str);
            C0764a c0764a = f2645s;
            Process.myPid();
            Thread.currentThread().getId();
            Looper.getMainLooper().getThread().getId();
            c0764a.mo2107h(i, format);
        }
    }

    public static void m2516b(String str, String str2) {
        if (f2645s != null && f2645s.mo2106h() <= 3) {
            if (str2 == null) {
                str2 = "";
            }
            String i = C0765a.m2520i(str);
            C0764a c0764a = f2645s;
            Process.myPid();
            Thread.currentThread().getId();
            Looper.getMainLooper().getThread().getId();
            c0764a.mo2105g(i, str2);
        }
    }

    public static void m2517c(String str, String str2) {
        if (f2645s != null && f2645s.mo2106h() <= 2) {
            if (str2 == null) {
                str2 = "";
            }
            String i = C0765a.m2520i(str);
            C0764a c0764a = f2645s;
            Process.myPid();
            Thread.currentThread().getId();
            Looper.getMainLooper().getThread().getId();
            c0764a.mo2103e(i, str2);
        }
    }

    public static void m2518d(String str, String str2) {
        if (f2645s != null && f2645s.mo2106h() <= 1) {
            if (str2 == null) {
                str2 = "";
            }
            String i = C0765a.m2520i(str);
            C0764a c0764a = f2645s;
            Process.myPid();
            Thread.currentThread().getId();
            Looper.getMainLooper().getThread().getId();
            c0764a.mo2104f(i, str2);
        }
    }

    private static String m2520i(String str) {
        return f2643q != null ? f2643q.m2522i(str) : str;
    }
}

package com.tencent.mm.sdk.p032b;

import android.os.Build;
import android.os.Build.VERSION;
import android.os.Looper;
import android.os.Process;

public final class C1242b {
    private static C1241a aG;
    private static C1241a aH;
    private static final String aI;
    private static int level = 6;

    public interface C1241a {
        void mo2159f(String str, String str2);

        void mo2160g(String str, String str2);

        int getLogLevel();

        void mo2162h(String str, String str2);

        void mo2163i(String str, String str2);
    }

    static {
        C1241a c1243c = new C1243c();
        aG = c1243c;
        aH = c1243c;
        StringBuilder stringBuilder = new StringBuilder();
        try {
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
        } catch (Throwable th) {
            th.printStackTrace();
        }
        aI = stringBuilder.toString();
    }

    public static void m3666a(String str, String str2, Object... objArr) {
        if (aH != null && aH.getLogLevel() <= 4) {
            String format = objArr == null ? str2 : String.format(str2, objArr);
            if (format == null) {
                format = "";
            }
            C1241a c1241a = aH;
            Process.myPid();
            Thread.currentThread().getId();
            Looper.getMainLooper().getThread().getId();
            c1241a.mo2163i(str, format);
        }
    }

    public static void m3667b(String str, String str2) {
        C1242b.m3666a(str, str2, null);
    }

    public static void m3668c(String str, String str2) {
        if (aH != null && aH.getLogLevel() <= 3) {
            C1241a c1241a = aH;
            Process.myPid();
            Thread.currentThread().getId();
            Looper.getMainLooper().getThread().getId();
            c1241a.mo2162h(str, str2);
        }
    }

    public static void m3669d(String str, String str2) {
        if (aH != null && aH.getLogLevel() <= 2) {
            C1241a c1241a = aH;
            Process.myPid();
            Thread.currentThread().getId();
            Looper.getMainLooper().getThread().getId();
            c1241a.mo2159f(str, str2);
        }
    }

    public static void m3670e(String str, String str2) {
        if (aH != null && aH.getLogLevel() <= 1) {
            if (str2 == null) {
                str2 = "";
            }
            C1241a c1241a = aH;
            Process.myPid();
            Thread.currentThread().getId();
            Looper.getMainLooper().getThread().getId();
            c1241a.mo2160g(str, str2);
        }
    }
}

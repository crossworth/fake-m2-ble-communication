package com.adroi.sdk.p000a;

import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;

public final class C0278c {
    private static final SimpleDateFormat f33a = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");

    static {
        C0278c.m34a();
    }

    public static void m34a() {
    }

    private static synchronized void m35a(String str, String str2) {
        synchronized (C0278c.class) {
        }
    }

    private static void m36a(String str, String str2, Throwable th) {
        Writer stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        C0278c.m35a(str, "\n" + stringWriter.toString());
        printWriter.close();
        try {
            stringWriter.close();
        } catch (Throwable e) {
            Log.w("Log.debug", "", e);
        }
    }

    public static boolean m38a(String str, int i) {
        return i >= 4;
    }

    public static boolean m37a(int i) {
        return C0278c.m38a("adroicore", i);
    }

    private static String m42b(Object[] objArr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object append : objArr) {
            stringBuilder.append(append).append(' ');
        }
        return stringBuilder.toString();
    }

    public static int m33a(Object... objArr) {
        if (C0278c.m37a(3)) {
            return C0278c.m30a(C0278c.m42b(objArr));
        }
        return -1;
    }

    public static int m30a(String str) {
        if (!C0278c.m37a(3)) {
            return -1;
        }
        C0278c.m35a("adroicore", str);
        return Log.d("adroicore", str);
    }

    public static int m32a(Throwable th) {
        return C0278c.m31a("", th);
    }

    public static int m31a(String str, Throwable th) {
        if (!C0278c.m37a(3)) {
            return -1;
        }
        C0278c.m36a("adroicore", str, th);
        return Log.d("adroicore", str, th);
    }

    public static int m39b(String str) {
        if (!C0278c.m37a(5)) {
            return -1;
        }
        C0278c.m35a("adroicore", str);
        return Log.w("adroicore", str);
    }

    public static int m41b(Throwable th) {
        return C0278c.m40b("", th);
    }

    public static int m40b(String str, Throwable th) {
        if (!C0278c.m37a(6)) {
            return -1;
        }
        C0278c.m36a("adroicore", str, th);
        return Log.e("adroicore", str, th);
    }

    public static int m43c(String str) {
        if (!C0278c.m37a(4)) {
            return -1;
        }
        C0278c.m35a("adroicore", str);
        return Log.i("adroicore", str);
    }
}

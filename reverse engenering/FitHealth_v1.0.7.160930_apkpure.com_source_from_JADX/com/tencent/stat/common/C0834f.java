package com.tencent.stat.common;

import android.content.Context;

public class C0834f {
    static long f2899a = -1;

    static long m2694a(Context context, String str) {
        return C0842p.m2760a(context, str, f2899a);
    }

    static void m2695a(Context context, String str, long j) {
        C0842p.m2764b(context, str, j);
    }

    public static synchronized boolean m2696a(Context context) {
        boolean z;
        synchronized (C0834f.class) {
            long a = C0834f.m2694a(context, "1.6.2_begin_protection");
            long a2 = C0834f.m2694a(context, "1.6.2_end__protection");
            if (a <= 0 || a2 != f2899a) {
                if (a == f2899a) {
                    C0834f.m2695a(context, "1.6.2_begin_protection", System.currentTimeMillis());
                }
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }

    public static synchronized void m2697b(Context context) {
        synchronized (C0834f.class) {
            if (C0834f.m2694a(context, "1.6.2_end__protection") == f2899a) {
                C0834f.m2695a(context, "1.6.2_end__protection", System.currentTimeMillis());
            }
        }
    }
}

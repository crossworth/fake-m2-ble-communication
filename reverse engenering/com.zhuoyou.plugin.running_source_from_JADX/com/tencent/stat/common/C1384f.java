package com.tencent.stat.common;

import android.content.Context;

public class C1384f {
    static long f4419a = -1;

    static long m4099a(Context context, String str) {
        return C1394p.m4167a(context, str, f4419a);
    }

    static void m4100a(Context context, String str, long j) {
        C1394p.m4171b(context, str, j);
    }

    public static synchronized boolean m4101a(Context context) {
        boolean z;
        synchronized (C1384f.class) {
            long a = C1384f.m4099a(context, "1.6.2_begin_protection");
            long a2 = C1384f.m4099a(context, "1.6.2_end__protection");
            if (a <= 0 || a2 != f4419a) {
                if (a == f4419a) {
                    C1384f.m4100a(context, "1.6.2_begin_protection", System.currentTimeMillis());
                }
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }

    public static synchronized void m4102b(Context context) {
        synchronized (C1384f.class) {
            if (C1384f.m4099a(context, "1.6.2_end__protection") == f4419a) {
                C1384f.m4100a(context, "1.6.2_end__protection", System.currentTimeMillis());
            }
        }
    }
}

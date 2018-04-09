package com.tencent.open.p037b;

import com.tencent.open.utils.Global;
import com.tencent.open.utils.OpenConfig;

/* compiled from: ProGuard */
public class C1323e {
    public static int m3902a(String str) {
        int i = OpenConfig.getInstance(Global.getContext(), str).getInt("Common_BusinessReportFrequency");
        if (i == 0) {
            return 100;
        }
        return i;
    }

    public static int m3901a() {
        int i = OpenConfig.getInstance(Global.getContext(), null).getInt("Common_HttpRetryCount");
        if (i == 0) {
            return 2;
        }
        return i;
    }
}

package com.tencent.stat;

import android.content.Context;
import com.tencent.stat.common.C0837k;
import com.tencent.stat.common.StatLogger;

public class StatMid {
    private static StatLogger f2816a = C0837k.m2718b();
    private static DeviceInfo f2817b = null;

    static synchronized DeviceInfo m2637a(Context context) {
        DeviceInfo a;
        synchronized (StatMid.class) {
            try {
                C0826a a2 = C0826a.m2664a(context);
                DeviceInfo a3 = m2640a(a2.m2671d(DeviceInfo.TAG_FLAG, null));
                f2816a.m2678d("get device info from internal storage:" + a3);
                DeviceInfo a4 = m2640a(a2.m2673f(DeviceInfo.TAG_FLAG, null));
                f2816a.m2678d("get device info from setting.system:" + a4);
                a = m2640a(a2.m2669b(DeviceInfo.TAG_FLAG, null));
                f2816a.m2678d("get device info from SharedPreference:" + a);
                f2817b = m2639a(a, a4, a3);
                if (f2817b == null) {
                    f2817b = new DeviceInfo();
                }
                a = C0850n.m2778a(context).m2802b(context);
                if (a != null) {
                    f2817b.m2617d(a.getImei());
                    f2817b.m2618e(a.getMac());
                    f2817b.m2613b(a.getUserType());
                }
            } catch (Object th) {
                f2816a.m2680e(th);
            }
            a = f2817b;
        }
        return a;
    }

    static DeviceInfo m2638a(DeviceInfo deviceInfo, DeviceInfo deviceInfo2) {
        return (deviceInfo == null || deviceInfo2 == null) ? deviceInfo == null ? deviceInfo2 != null ? deviceInfo2 : null : deviceInfo : deviceInfo.m2609a(deviceInfo2) >= 0 ? deviceInfo : deviceInfo2;
    }

    static DeviceInfo m2639a(DeviceInfo deviceInfo, DeviceInfo deviceInfo2, DeviceInfo deviceInfo3) {
        return m2638a(m2638a(deviceInfo, deviceInfo2), m2638a(deviceInfo2, deviceInfo3));
    }

    private static DeviceInfo m2640a(String str) {
        return str != null ? DeviceInfo.m2607a(C0837k.m2726d(str)) : null;
    }

    public static DeviceInfo getDeviceInfo(Context context) {
        if (context == null) {
            f2816a.error((Object) "Context for StatConfig.getDeviceInfo is null.");
            return null;
        }
        if (f2817b == null) {
            m2637a(context);
        }
        return f2817b;
    }

    public static String getMid(Context context) {
        if (f2817b == null) {
            getDeviceInfo(context);
        }
        return f2817b.getMid();
    }

    public static void updateDeviceInfo(Context context, String str) {
        try {
            getDeviceInfo(context);
            f2817b.m2616c(str);
            f2817b.m2610a(f2817b.m2608a() + 1);
            f2817b.m2611a(System.currentTimeMillis());
            String jSONObject = f2817b.m2615c().toString();
            f2816a.m2678d("save DeviceInfo:" + jSONObject);
            jSONObject = C0837k.m2723c(jSONObject).replace("\n", "");
            C0826a a = C0826a.m2664a(context);
            a.m2670c(DeviceInfo.TAG_FLAG, jSONObject);
            a.m2672e(DeviceInfo.TAG_FLAG, jSONObject);
            a.m2668a(DeviceInfo.TAG_FLAG, jSONObject);
        } catch (Object th) {
            f2816a.m2680e(th);
        }
    }
}

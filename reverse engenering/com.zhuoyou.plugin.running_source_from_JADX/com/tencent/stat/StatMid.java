package com.tencent.stat;

import android.content.Context;
import com.tencent.stat.common.C1389k;
import com.tencent.stat.common.StatLogger;

public class StatMid {
    private static StatLogger f4318a = C1389k.m4125b();
    private static DeviceInfo f4319b = null;

    static synchronized DeviceInfo m4021a(Context context) {
        DeviceInfo a;
        synchronized (StatMid.class) {
            try {
                C1376a a2 = C1376a.m4069a(context);
                DeviceInfo a3 = m4024a(a2.m4076d(DeviceInfo.TAG_FLAG, null));
                f4318a.m4083d("get device info from internal storage:" + a3);
                DeviceInfo a4 = m4024a(a2.m4078f(DeviceInfo.TAG_FLAG, null));
                f4318a.m4083d("get device info from setting.system:" + a4);
                a = m4024a(a2.m4074b(DeviceInfo.TAG_FLAG, null));
                f4318a.m4083d("get device info from SharedPreference:" + a);
                f4319b = m4023a(a, a4, a3);
                if (f4319b == null) {
                    f4319b = new DeviceInfo();
                }
                a = C1405n.m4189a(context).m4213b(context);
                if (a != null) {
                    f4319b.m4001d(a.getImei());
                    f4319b.m4002e(a.getMac());
                    f4319b.m3997b(a.getUserType());
                }
            } catch (Object th) {
                f4318a.m4085e(th);
            }
            a = f4319b;
        }
        return a;
    }

    static DeviceInfo m4022a(DeviceInfo deviceInfo, DeviceInfo deviceInfo2) {
        return (deviceInfo == null || deviceInfo2 == null) ? deviceInfo == null ? deviceInfo2 != null ? deviceInfo2 : null : deviceInfo : deviceInfo.m3993a(deviceInfo2) >= 0 ? deviceInfo : deviceInfo2;
    }

    static DeviceInfo m4023a(DeviceInfo deviceInfo, DeviceInfo deviceInfo2, DeviceInfo deviceInfo3) {
        return m4022a(m4022a(deviceInfo, deviceInfo2), m4022a(deviceInfo2, deviceInfo3));
    }

    private static DeviceInfo m4024a(String str) {
        return str != null ? DeviceInfo.m3991a(C1389k.m4133d(str)) : null;
    }

    public static DeviceInfo getDeviceInfo(Context context) {
        if (context == null) {
            f4318a.error((Object) "Context for StatConfig.getDeviceInfo is null.");
            return null;
        }
        if (f4319b == null) {
            m4021a(context);
        }
        return f4319b;
    }

    public static String getMid(Context context) {
        if (f4319b == null) {
            getDeviceInfo(context);
        }
        return f4319b.getMid();
    }

    public static void updateDeviceInfo(Context context, String str) {
        try {
            getDeviceInfo(context);
            f4319b.m4000c(str);
            f4319b.m3994a(f4319b.m3992a() + 1);
            f4319b.m3995a(System.currentTimeMillis());
            String jSONObject = f4319b.m3999c().toString();
            f4318a.m4083d("save DeviceInfo:" + jSONObject);
            jSONObject = C1389k.m4130c(jSONObject).replace("\n", "");
            C1376a a = C1376a.m4069a(context);
            a.m4075c(DeviceInfo.TAG_FLAG, jSONObject);
            a.m4077e(DeviceInfo.TAG_FLAG, jSONObject);
            a.m4073a(DeviceInfo.TAG_FLAG, jSONObject);
        } catch (Object th) {
            f4318a.m4085e(th);
        }
    }
}

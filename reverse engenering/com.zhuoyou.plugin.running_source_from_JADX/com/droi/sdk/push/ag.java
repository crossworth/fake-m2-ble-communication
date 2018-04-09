package com.droi.sdk.push;

import android.content.Context;
import android.text.TextUtils;
import com.droi.sdk.analytics.priv.AnalyticsModule;
import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1015j;
import com.tencent.stat.DeviceInfo;
import org.json.JSONObject;

public class ag {
    private static AnalyticsModule f3241a = null;

    private static String m3005a(int i, int i2, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(3);
        stringBuffer.append("|");
        stringBuffer.append(i);
        stringBuffer.append("|");
        stringBuffer.append(i2);
        stringBuffer.append("|");
        stringBuffer.append(str);
        return stringBuffer.toString();
    }

    private static String m3006a(String str, long j, int i, String str2) {
        String str3 = null;
        if (j >= 0 && i >= 0 && C1015j.m3168d(str) && C1015j.m3168d(str2)) {
            long currentTimeMillis = System.currentTimeMillis();
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("mi", j);
                jSONObject.put("ma", i);
                jSONObject.put("ti", currentTimeMillis);
                jSONObject.put("di", str2);
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("mt", str);
                jSONObject2.put(DeviceInfo.TAG_MAC, jSONObject);
                str3 = jSONObject2.toString();
            } catch (Exception e) {
                C1012g.m3139b(e);
            }
        }
        return str3;
    }

    public static void m3007a(Context context, long j, String str, int i, int i2, int i3, String str2) {
        AnalyticsModule analyticsModule = null;
        if (context != null) {
            Object a = DroiPush.m2876a(context);
            if (!TextUtils.isEmpty(a)) {
                if (f3241a == null) {
                    Context applicationContext = context.getApplicationContext();
                    if (applicationContext == null) {
                        analyticsModule = new AnalyticsModule(context);
                    } else {
                        f3241a = new AnalyticsModule(applicationContext);
                    }
                }
                if (f3241a != null) {
                    analyticsModule = f3241a;
                }
                analyticsModule.send(0, m3005a(i2, i3, str2), m3006a(str, j, i, a));
            }
        }
    }
}

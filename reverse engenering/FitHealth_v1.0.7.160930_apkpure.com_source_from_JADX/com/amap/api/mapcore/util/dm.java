package com.amap.api.mapcore.util;

import android.content.Context;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpHeaders;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

@Deprecated
/* compiled from: AuthManager */
public class dm {
    public static int f465a = -1;
    public static String f466b = "";
    private static dv f467c;
    private static String f468d = "http://apiinit.amap.com/v3/log/init";
    private static String f469e = null;

    private static boolean m613a(Context context, dv dvVar, boolean z) {
        boolean z2 = true;
        f467c = dvVar;
        try {
            String a = m609a();
            Map hashMap = new HashMap();
            hashMap.put("Content-Type", "application/x-www-form-urlencoded");
            hashMap.put(HttpHeaders.ACCEPT_ENCODING, "gzip");
            hashMap.put("Connection", HTTP.CONN_KEEP_ALIVE);
            hashMap.put("User-Agent", f467c.m709c());
            hashMap.put("X-INFO", dn.m620a(context, f467c, null, z));
            hashMap.put("logversion", "2.1");
            hashMap.put("platinfo", String.format("platform=Android&sdkversion=%s&product=%s", new Object[]{f467c.m708b(), f467c.m706a()}));
            fq a2 = fq.m948a();
            fw dyVar = new dy();
            dyVar.m973a(dt.m688a(context));
            dyVar.m4231a(hashMap);
            dyVar.m4233b(m610a(context));
            dyVar.m4230a(a);
            z2 = m614a(a2.mo1651b(dyVar));
        } catch (Throwable th) {
            eb.m742a(th, "Auth", "getAuth");
        }
        return z2;
    }

    @Deprecated
    public static synchronized boolean m612a(Context context, dv dvVar) {
        boolean a;
        synchronized (dm.class) {
            a = m613a(context, dvVar, false);
        }
        return a;
    }

    public static void m611a(String str) {
        dl.m602a(str);
    }

    private static String m609a() {
        return f468d;
    }

    private static boolean m614a(byte[] bArr) {
        if (bArr == null) {
            return true;
        }
        try {
            JSONObject jSONObject = new JSONObject(dx.m715a(bArr));
            if (jSONObject.has("status")) {
                int i = jSONObject.getInt("status");
                if (i == 1) {
                    f465a = 1;
                } else if (i == 0) {
                    f465a = 0;
                }
            }
            if (jSONObject.has("info")) {
                f466b = jSONObject.getString("info");
            }
            if (f465a == 0) {
                Log.i("AuthFailure", f466b);
            }
            if (f465a != 1) {
                return false;
            }
            return true;
        } catch (Throwable e) {
            eb.m742a(e, "Auth", "lData");
            return false;
        } catch (Throwable e2) {
            eb.m742a(e2, "Auth", "lData");
            return false;
        }
    }

    private static Map<String, String> m610a(Context context) {
        Map<String, String> hashMap = new HashMap();
        try {
            hashMap.put("resType", "json");
            hashMap.put("encode", "UTF-8");
            String a = dn.m616a();
            hashMap.put("ts", a);
            hashMap.put("key", dl.m607f(context));
            hashMap.put("scode", dn.m621a(context, a, dx.m727d("resType=json&encode=UTF-8&key=" + dl.m607f(context))));
        } catch (Throwable th) {
            eb.m742a(th, "Auth", "gParams");
        }
        return hashMap;
    }
}

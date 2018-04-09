package com.amap.api.location.core;

import android.content.Context;
import android.util.Log;
import com.aps.C0456l;
import java.util.Arrays;
import org.json.JSONObject;

/* compiled from: AuthManager */
public class C0186a {
    static String f90a = "";
    private static int f91b = -1;

    public static int m76a() {
        return f91b;
    }

    public static synchronized boolean m78a(Context context) {
        boolean c;
        synchronized (C0186a.class) {
            try {
                byte[] c2 = C0186a.m82c();
                String a = C0456l.m1967a().m1974a(context, C0186a.m79b(), c2, "loc");
                if (a != null) {
                    c = C0186a.m81c(a);
                } else {
                    f91b = 0;
                    c = true;
                }
                if (f91b != 1) {
                    f91b = 0;
                }
            } catch (Throwable th) {
                if (f91b != 1) {
                    f91b = 0;
                }
            }
        }
        return c;
    }

    private static String m79b() {
        return "http://apiinit.amap.com/v3/log/init";
    }

    private static boolean m81c(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("status")) {
                int i = jSONObject.getInt("status");
                if (i == 1) {
                    f91b = 1;
                } else if (i == 0) {
                    f91b = 0;
                }
            }
            if (jSONObject.has("info")) {
                f90a = jSONObject.getString("info");
            }
            if (f91b == 0) {
                Log.i("AuthFailure", f90a);
            }
        } catch (Exception e) {
            e.printStackTrace();
            f91b = 0;
        }
        if (f91b == 1) {
            return true;
        }
        return false;
    }

    private static byte[] m82c() {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("resType=json&encode=UTF-8&ec=1");
            return C0186a.m80b(C0186a.m77a(stringBuffer.toString())).toString().getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String m77a(String str) {
        String[] split = str.split("&");
        Arrays.sort(split);
        StringBuffer stringBuffer = new StringBuffer();
        for (String append : split) {
            stringBuffer.append(append);
            stringBuffer.append("&");
        }
        String stringBuffer2 = stringBuffer.toString();
        if (stringBuffer2.length() > 1) {
            return (String) stringBuffer2.subSequence(0, stringBuffer2.length() - 1);
        }
        return str;
    }

    public static String m80b(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        String a = C0189d.m103a();
        stringBuffer.append("&ts=" + a);
        stringBuffer.append("&scode=" + C0189d.m104a(a, str));
        return stringBuffer.toString();
    }
}

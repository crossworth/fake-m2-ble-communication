package com.amap.api.mapcore.util;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.zhuoyi.system.promotion.util.PromConstants;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
/* compiled from: ConfigManager */
public class dp {

    @Deprecated
    /* compiled from: ConfigManager */
    public static class C0246a {
        public JSONObject f497a;
        public JSONObject f498b;
        public JSONObject f499c;
        public JSONObject f500d;
        @Deprecated
        public JSONObject f501e;
        public JSONObject f502f;
        public C0243a f503g;
        public C0245c f504h;
        public C0244b f505i;

        /* compiled from: ConfigManager */
        public static class C0243a {
            public boolean f490a;
            public boolean f491b;
        }

        /* compiled from: ConfigManager */
        public static class C0244b {
            public String f492a;
            public String f493b;
        }

        /* compiled from: ConfigManager */
        public static class C0245c {
            public String f494a;
            public String f495b;
            public String f496c;
        }
    }

    /* compiled from: ConfigManager */
    static class C1598b extends fw {
        private Context f4168a;
        private dv f4169b;
        private String f4170c = "";

        C1598b(Context context, dv dvVar, String str) {
            this.f4168a = context;
            this.f4169b = dvVar;
            this.f4170c = str;
        }

        public Map<String, String> mo1632c() {
            Map<String, String> hashMap = new HashMap();
            hashMap.put("User-Agent", this.f4169b.m709c());
            hashMap.put("platinfo", String.format("platform=Android&sdkversion=%s&product=%s", new Object[]{this.f4169b.m708b(), this.f4169b.m706a()}));
            hashMap.put("logversion", SocializeConstants.PROTOCOL_VERSON);
            return hashMap;
        }

        public Map<String, String> mo1631b() {
            Object q = dq.m660q(this.f4168a);
            if (!TextUtils.isEmpty(q)) {
                q = ds.m681b(new StringBuilder(q).reverse().toString());
            }
            Map hashMap = new HashMap();
            hashMap.put("key", dl.m607f(this.f4168a));
            hashMap.put("opertype", this.f4170c);
            hashMap.put("plattype", "android");
            hashMap.put("product", this.f4169b.m706a());
            hashMap.put("version", this.f4169b.m708b());
            hashMap.put("output", "json");
            hashMap.put("androidversion", VERSION.SDK_INT + "");
            hashMap.put("deviceId", q);
            hashMap.put("abitype", Build.CPU_ABI);
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_EXTEND, this.f4169b.m710d());
            String a = dn.m616a();
            String a2 = dn.m621a(this.f4168a, a, dx.m723b(hashMap));
            hashMap.put("ts", a);
            hashMap.put("scode", a2);
            return hashMap;
        }

        public String mo1630a() {
            return "https://restapi.amap.com/v3/fastconnect";
        }
    }

    @Deprecated
    public static C0246a m631a(byte[] bArr) {
        boolean z = false;
        C0246a c0246a = new C0246a();
        if (bArr != null) {
            try {
                if (bArr.length != 0) {
                    JSONObject jSONObject = new JSONObject(dx.m715a(bArr));
                    if ("1".equals(m632a(jSONObject, "status")) && jSONObject.has("result")) {
                        jSONObject = jSONObject.getJSONObject("result");
                        if (jSONObject != null) {
                            boolean b;
                            JSONObject jSONObject2;
                            if (dx.m719a(jSONObject, "exception")) {
                                b = m637b(jSONObject.getJSONObject("exception"));
                            } else {
                                b = false;
                            }
                            if (dx.m719a(jSONObject, "common")) {
                                z = m636a(jSONObject.getJSONObject("common"));
                            }
                            C0243a c0243a = new C0243a();
                            c0243a.f490a = b;
                            c0243a.f491b = z;
                            c0246a.f503g = c0243a;
                            if (jSONObject.has("sdkupdate")) {
                                jSONObject2 = jSONObject.getJSONObject("sdkupdate");
                                C0245c c0245c = new C0245c();
                                m634a(jSONObject2, c0245c);
                                c0246a.f504h = c0245c;
                            }
                            if (dx.m719a(jSONObject, "sdkcoordinate")) {
                                jSONObject2 = jSONObject.getJSONObject("sdkcoordinate");
                                C0244b c0244b = new C0244b();
                                m633a(jSONObject2, c0244b);
                                c0246a.f505i = c0244b;
                            }
                            if (dx.m719a(jSONObject, "callamap")) {
                                c0246a.f501e = jSONObject.getJSONObject("callamap");
                            }
                            if (dx.m719a(jSONObject, "ca")) {
                                c0246a.f502f = jSONObject.getJSONObject("ca");
                            }
                            if (dx.m719a(jSONObject, "locate")) {
                                c0246a.f500d = jSONObject.getJSONObject("locate");
                            }
                            if (dx.m719a(jSONObject, "callamappro")) {
                                c0246a.f499c = jSONObject.getJSONObject("callamappro");
                            }
                            if (dx.m719a(jSONObject, "opflag")) {
                                c0246a.f498b = jSONObject.getJSONObject("opflag");
                            }
                            if (dx.m719a(jSONObject, "amappushflag")) {
                                c0246a.f497a = jSONObject.getJSONObject("amappushflag");
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                eb.m742a(th, "ConfigManager", "loadConfig");
            }
        }
        return c0246a;
    }

    @Deprecated
    public static C0246a m630a(Context context, dv dvVar, String str) {
        try {
            return m631a(new fq().m950a(new C1598b(context, dvVar, str)));
        } catch (Throwable th) {
            eb.m742a(th, "ConfigManager", "loadConfig");
            return new C0246a();
        }
    }

    private static boolean m635a(String str) {
        if (str != null && str.equals("1")) {
            return true;
        }
        return false;
    }

    public static String m632a(JSONObject jSONObject, String str) throws JSONException {
        if (jSONObject == null) {
            return "";
        }
        String str2 = "";
        if (!jSONObject.has(str) || jSONObject.getString(str).equals("[]")) {
            return str2;
        }
        return jSONObject.optString(str);
    }

    private static void m633a(JSONObject jSONObject, C0244b c0244b) {
        if (jSONObject != null) {
            try {
                String a = m632a(jSONObject, PromConstants.PROM_HTML5_INFO_MD5);
                String a2 = m632a(jSONObject, "url");
                c0244b.f493b = a;
                c0244b.f492a = a2;
            } catch (Throwable th) {
                eb.m742a(th, "ConfigManager", "parseSDKCoordinate");
            }
        }
    }

    private static void m634a(JSONObject jSONObject, C0245c c0245c) {
        if (jSONObject != null) {
            try {
                Object a = m632a(jSONObject, PromConstants.PROM_HTML5_INFO_MD5);
                Object a2 = m632a(jSONObject, "url");
                Object a3 = m632a(jSONObject, "sdkversion");
                if (!TextUtils.isEmpty(a) && !TextUtils.isEmpty(a2) && !TextUtils.isEmpty(a3)) {
                    c0245c.f494a = a2;
                    c0245c.f495b = a;
                    c0245c.f496c = a3;
                }
            } catch (Throwable th) {
                eb.m742a(th, "ConfigManager", "parseSDKUpdate");
            }
        }
    }

    private static boolean m636a(JSONObject jSONObject) {
        boolean z = false;
        if (jSONObject != null) {
            try {
                z = m635a(m632a(jSONObject.getJSONObject("commoninfo"), "com_isupload"));
            } catch (Throwable th) {
                eb.m742a(th, "ConfigManager", "parseCommon");
            }
        }
        return z;
    }

    private static boolean m637b(JSONObject jSONObject) {
        boolean z = false;
        if (jSONObject != null) {
            try {
                z = m635a(m632a(jSONObject.getJSONObject("exceptinfo"), "ex_isupload"));
            } catch (Throwable th) {
                eb.m742a(th, "ConfigManager", "parseException");
            }
        }
        return z;
    }
}

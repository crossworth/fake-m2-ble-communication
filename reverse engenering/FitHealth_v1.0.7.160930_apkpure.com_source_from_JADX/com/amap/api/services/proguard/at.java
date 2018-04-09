package com.amap.api.services.proguard;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.tencent.stat.DeviceInfo;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.zhuoyi.system.promotion.util.PromConstants;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: AuthConfigManager */
public class at {
    public static int f1343a = -1;
    public static String f1344b = "";

    /* compiled from: AuthConfigManager */
    public static class C0364a {
        public String f1329a;
        public int f1330b = -1;
        public JSONObject f1331c;
        public JSONObject f1332d;
        public JSONObject f1333e;
        public JSONObject f1334f;
        public JSONObject f1335g;
        public JSONObject f1336h;
        public JSONObject f1337i;
        public JSONObject f1338j;
        public JSONObject f1339k;
        public C0361a f1340l;
        public C0363c f1341m;
        public C0362b f1342n;

        /* compiled from: AuthConfigManager */
        public static class C0361a {
            public boolean f1322a;
            public boolean f1323b;
        }

        /* compiled from: AuthConfigManager */
        public static class C0362b {
            public String f1324a;
            public String f1325b;
        }

        /* compiled from: AuthConfigManager */
        public static class C0363c {
            public String f1326a;
            public String f1327b;
            public String f1328c;
        }
    }

    /* compiled from: AuthConfigManager */
    static class C1971b extends cr {
        private String f5411c;
        private Map<String, String> f5412d;

        C1971b(Context context, ba baVar, String str, Map<String, String> map) {
            super(context, baVar);
            this.f5411c = str;
            this.f5412d = map;
        }

        public Map<String, String> mo1757c() {
            return null;
        }

        public String mo1759g() {
            return "https://restapi.amap.com/v3/iasdkauth";
        }

        public byte[] mo3044a() {
            return null;
        }

        public byte[] mo3045d() {
            return bb.m1321a(bb.m1314a(m5782k()));
        }

        protected String mo3046e() {
            return "3.0";
        }

        private Map<String, String> m5782k() {
            Object q = aw.m1261q(this.a);
            if (!TextUtils.isEmpty(q)) {
                q = ay.m1282b(new StringBuilder(q).reverse().toString());
            }
            Map<String, String> hashMap = new HashMap();
            hashMap.put("authkey", this.f5411c);
            hashMap.put("plattype", "android");
            hashMap.put("product", this.b.m1308a());
            hashMap.put("version", this.b.m1309b());
            hashMap.put("output", "json");
            hashMap.put("androidversion", VERSION.SDK_INT + "");
            hashMap.put("deviceId", q);
            if (!(this.f5412d == null || this.f5412d.isEmpty())) {
                hashMap.putAll(this.f5412d);
            }
            if (VERSION.SDK_INT >= 21) {
                try {
                    ApplicationInfo applicationInfo = this.a.getApplicationInfo();
                    Field declaredField = Class.forName(ApplicationInfo.class.getName()).getDeclaredField("primaryCpuAbi");
                    declaredField.setAccessible(true);
                    q = (String) declaredField.get(applicationInfo);
                } catch (Throwable th) {
                    be.m1340a(th, "ConfigManager", "getcpu");
                }
                if (TextUtils.isEmpty(q)) {
                    q = Build.CPU_ABI;
                }
                hashMap.put("abitype", q);
                hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_EXTEND, this.b.m1310c());
                return hashMap;
            }
            q = null;
            if (TextUtils.isEmpty(q)) {
                q = Build.CPU_ABI;
            }
            hashMap.put("abitype", q);
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_EXTEND, this.b.m1310c());
            return hashMap;
        }
    }

    public static boolean m1221a(String str, boolean z) {
        try {
            String[] split = URLDecoder.decode(str).split("/");
            if (split[split.length - 1].charAt(4) % 2 == 1) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            return z;
        }
    }

    public static C0364a m1217a(Context context, ba baVar, String str, Map<String, String> map) {
        byte[] a;
        String a2;
        ar e;
        JSONObject jSONObject;
        int i;
        C0361a c0361a;
        JSONObject jSONObject2;
        C0363c c0363c;
        C0362b c0362b;
        Throwable th;
        String str2 = null;
        C0364a c0364a = new C0364a();
        try {
            a = new cq().m1547a(new C1971b(context, baVar, str, map));
            try {
                Object obj = new byte[16];
                Object obj2 = new byte[(a.length - 16)];
                System.arraycopy(a, 0, obj, 0, 16);
                System.arraycopy(a, 16, obj2, 0, a.length - 16);
                Key secretKeySpec = new SecretKeySpec(obj, "AES");
                Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
                instance.init(2, secretKeySpec, new IvParameterSpec(bb.m1320a()));
                a2 = bb.m1315a(instance.doFinal(obj2));
            } catch (ar e2) {
                e = e2;
                c0364a.f1329a = e.m1207a();
                a2 = str2;
                if (a != null) {
                    return c0364a;
                }
                if (TextUtils.isEmpty(a2)) {
                    a2 = bb.m1315a(a);
                }
                try {
                    jSONObject = new JSONObject(a2);
                    if (jSONObject.has("status")) {
                        i = jSONObject.getInt("status");
                        if (i != 1) {
                            f1343a = 1;
                        } else if (i == 0) {
                            f1343a = 0;
                            if (jSONObject.has("info")) {
                                f1344b = jSONObject.getString("info");
                            }
                            if (f1343a == 0) {
                                c0364a.f1329a = f1344b;
                                return c0364a;
                            }
                        }
                        if (jSONObject.has(DeviceInfo.TAG_VERSION)) {
                            c0364a.f1330b = jSONObject.getInt(DeviceInfo.TAG_VERSION);
                        }
                        if (bb.m1319a(jSONObject, "result")) {
                            c0361a = new C0361a();
                            c0361a.f1322a = false;
                            c0361a.f1323b = false;
                            c0364a.f1340l = c0361a;
                            jSONObject = jSONObject.getJSONObject("result");
                            if (bb.m1319a(jSONObject, "11K")) {
                                c0361a.f1322a = m1221a(jSONObject.getJSONObject("11K").getString("able"), false);
                            }
                            if (bb.m1319a(jSONObject, "11B")) {
                                c0364a.f1331c = jSONObject.getJSONObject("11B");
                            }
                            if (bb.m1319a(jSONObject, "11C")) {
                                c0364a.f1332d = jSONObject.getJSONObject("11C");
                            }
                            if (bb.m1319a(jSONObject, "11I")) {
                                c0364a.f1333e = jSONObject.getJSONObject("11I");
                            }
                            if (bb.m1319a(jSONObject, "11H")) {
                                c0364a.f1334f = jSONObject.getJSONObject("11H");
                            }
                            if (bb.m1319a(jSONObject, "11E")) {
                                c0364a.f1335g = jSONObject.getJSONObject("11E");
                            }
                            if (bb.m1319a(jSONObject, "11F")) {
                                c0364a.f1336h = jSONObject.getJSONObject("11F");
                            }
                            if (bb.m1319a(jSONObject, "11G")) {
                                c0364a.f1337i = jSONObject.getJSONObject("11G");
                            }
                            if (bb.m1319a(jSONObject, "001")) {
                                jSONObject2 = jSONObject.getJSONObject("001");
                                c0363c = new C0363c();
                                m1220a(jSONObject2, c0363c);
                                c0364a.f1341m = c0363c;
                            }
                            if (bb.m1319a(jSONObject, "002")) {
                                jSONObject2 = jSONObject.getJSONObject("002");
                                c0362b = new C0362b();
                                m1219a(jSONObject2, c0362b);
                                c0364a.f1342n = c0362b;
                            }
                            if (bb.m1319a(jSONObject, "006")) {
                                c0364a.f1338j = jSONObject.getJSONObject("006");
                            }
                            if (bb.m1319a(jSONObject, "010")) {
                                c0364a.f1339k = jSONObject.getJSONObject("010");
                            }
                        }
                        return c0364a;
                    }
                } catch (Throwable th2) {
                    be.m1340a(th2, "AuthConfigManager", "loadConfig");
                }
                return c0364a;
            } catch (IllegalBlockSizeException e3) {
                a2 = str2;
                if (a != null) {
                    return c0364a;
                }
                if (TextUtils.isEmpty(a2)) {
                    a2 = bb.m1315a(a);
                }
                jSONObject = new JSONObject(a2);
                if (jSONObject.has("status")) {
                    i = jSONObject.getInt("status");
                    if (i != 1) {
                        f1343a = 1;
                    } else if (i == 0) {
                        f1343a = 0;
                        if (jSONObject.has("info")) {
                            f1344b = jSONObject.getString("info");
                        }
                        if (f1343a == 0) {
                            c0364a.f1329a = f1344b;
                            return c0364a;
                        }
                    }
                    if (jSONObject.has(DeviceInfo.TAG_VERSION)) {
                        c0364a.f1330b = jSONObject.getInt(DeviceInfo.TAG_VERSION);
                    }
                    if (bb.m1319a(jSONObject, "result")) {
                        c0361a = new C0361a();
                        c0361a.f1322a = false;
                        c0361a.f1323b = false;
                        c0364a.f1340l = c0361a;
                        jSONObject = jSONObject.getJSONObject("result");
                        if (bb.m1319a(jSONObject, "11K")) {
                            c0361a.f1322a = m1221a(jSONObject.getJSONObject("11K").getString("able"), false);
                        }
                        if (bb.m1319a(jSONObject, "11B")) {
                            c0364a.f1331c = jSONObject.getJSONObject("11B");
                        }
                        if (bb.m1319a(jSONObject, "11C")) {
                            c0364a.f1332d = jSONObject.getJSONObject("11C");
                        }
                        if (bb.m1319a(jSONObject, "11I")) {
                            c0364a.f1333e = jSONObject.getJSONObject("11I");
                        }
                        if (bb.m1319a(jSONObject, "11H")) {
                            c0364a.f1334f = jSONObject.getJSONObject("11H");
                        }
                        if (bb.m1319a(jSONObject, "11E")) {
                            c0364a.f1335g = jSONObject.getJSONObject("11E");
                        }
                        if (bb.m1319a(jSONObject, "11F")) {
                            c0364a.f1336h = jSONObject.getJSONObject("11F");
                        }
                        if (bb.m1319a(jSONObject, "11G")) {
                            c0364a.f1337i = jSONObject.getJSONObject("11G");
                        }
                        if (bb.m1319a(jSONObject, "001")) {
                            jSONObject2 = jSONObject.getJSONObject("001");
                            c0363c = new C0363c();
                            m1220a(jSONObject2, c0363c);
                            c0364a.f1341m = c0363c;
                        }
                        if (bb.m1319a(jSONObject, "002")) {
                            jSONObject2 = jSONObject.getJSONObject("002");
                            c0362b = new C0362b();
                            m1219a(jSONObject2, c0362b);
                            c0364a.f1342n = c0362b;
                        }
                        if (bb.m1319a(jSONObject, "006")) {
                            c0364a.f1338j = jSONObject.getJSONObject("006");
                        }
                        if (bb.m1319a(jSONObject, "010")) {
                            c0364a.f1339k = jSONObject.getJSONObject("010");
                        }
                    }
                    return c0364a;
                }
                return c0364a;
            } catch (Throwable th3) {
                th2 = th3;
                be.m1340a(th2, "ConfigManager", "loadConfig");
                a2 = str2;
                if (a != null) {
                    return c0364a;
                }
                if (TextUtils.isEmpty(a2)) {
                    a2 = bb.m1315a(a);
                }
                jSONObject = new JSONObject(a2);
                if (jSONObject.has("status")) {
                    i = jSONObject.getInt("status");
                    if (i != 1) {
                        f1343a = 1;
                    } else if (i == 0) {
                        f1343a = 0;
                        if (jSONObject.has("info")) {
                            f1344b = jSONObject.getString("info");
                        }
                        if (f1343a == 0) {
                            c0364a.f1329a = f1344b;
                            return c0364a;
                        }
                    }
                    if (jSONObject.has(DeviceInfo.TAG_VERSION)) {
                        c0364a.f1330b = jSONObject.getInt(DeviceInfo.TAG_VERSION);
                    }
                    if (bb.m1319a(jSONObject, "result")) {
                        c0361a = new C0361a();
                        c0361a.f1322a = false;
                        c0361a.f1323b = false;
                        c0364a.f1340l = c0361a;
                        jSONObject = jSONObject.getJSONObject("result");
                        if (bb.m1319a(jSONObject, "11K")) {
                            c0361a.f1322a = m1221a(jSONObject.getJSONObject("11K").getString("able"), false);
                        }
                        if (bb.m1319a(jSONObject, "11B")) {
                            c0364a.f1331c = jSONObject.getJSONObject("11B");
                        }
                        if (bb.m1319a(jSONObject, "11C")) {
                            c0364a.f1332d = jSONObject.getJSONObject("11C");
                        }
                        if (bb.m1319a(jSONObject, "11I")) {
                            c0364a.f1333e = jSONObject.getJSONObject("11I");
                        }
                        if (bb.m1319a(jSONObject, "11H")) {
                            c0364a.f1334f = jSONObject.getJSONObject("11H");
                        }
                        if (bb.m1319a(jSONObject, "11E")) {
                            c0364a.f1335g = jSONObject.getJSONObject("11E");
                        }
                        if (bb.m1319a(jSONObject, "11F")) {
                            c0364a.f1336h = jSONObject.getJSONObject("11F");
                        }
                        if (bb.m1319a(jSONObject, "11G")) {
                            c0364a.f1337i = jSONObject.getJSONObject("11G");
                        }
                        if (bb.m1319a(jSONObject, "001")) {
                            jSONObject2 = jSONObject.getJSONObject("001");
                            c0363c = new C0363c();
                            m1220a(jSONObject2, c0363c);
                            c0364a.f1341m = c0363c;
                        }
                        if (bb.m1319a(jSONObject, "002")) {
                            jSONObject2 = jSONObject.getJSONObject("002");
                            c0362b = new C0362b();
                            m1219a(jSONObject2, c0362b);
                            c0364a.f1342n = c0362b;
                        }
                        if (bb.m1319a(jSONObject, "006")) {
                            c0364a.f1338j = jSONObject.getJSONObject("006");
                        }
                        if (bb.m1319a(jSONObject, "010")) {
                            c0364a.f1339k = jSONObject.getJSONObject("010");
                        }
                    }
                    return c0364a;
                }
                return c0364a;
            }
        } catch (ar e4) {
            e = e4;
            a = str2;
            c0364a.f1329a = e.m1207a();
            a2 = str2;
            if (a != null) {
                return c0364a;
            }
            if (TextUtils.isEmpty(a2)) {
                a2 = bb.m1315a(a);
            }
            jSONObject = new JSONObject(a2);
            if (jSONObject.has("status")) {
                i = jSONObject.getInt("status");
                if (i != 1) {
                    f1343a = 1;
                } else if (i == 0) {
                    f1343a = 0;
                    if (jSONObject.has("info")) {
                        f1344b = jSONObject.getString("info");
                    }
                    if (f1343a == 0) {
                        c0364a.f1329a = f1344b;
                        return c0364a;
                    }
                }
                if (jSONObject.has(DeviceInfo.TAG_VERSION)) {
                    c0364a.f1330b = jSONObject.getInt(DeviceInfo.TAG_VERSION);
                }
                if (bb.m1319a(jSONObject, "result")) {
                    c0361a = new C0361a();
                    c0361a.f1322a = false;
                    c0361a.f1323b = false;
                    c0364a.f1340l = c0361a;
                    jSONObject = jSONObject.getJSONObject("result");
                    if (bb.m1319a(jSONObject, "11K")) {
                        c0361a.f1322a = m1221a(jSONObject.getJSONObject("11K").getString("able"), false);
                    }
                    if (bb.m1319a(jSONObject, "11B")) {
                        c0364a.f1331c = jSONObject.getJSONObject("11B");
                    }
                    if (bb.m1319a(jSONObject, "11C")) {
                        c0364a.f1332d = jSONObject.getJSONObject("11C");
                    }
                    if (bb.m1319a(jSONObject, "11I")) {
                        c0364a.f1333e = jSONObject.getJSONObject("11I");
                    }
                    if (bb.m1319a(jSONObject, "11H")) {
                        c0364a.f1334f = jSONObject.getJSONObject("11H");
                    }
                    if (bb.m1319a(jSONObject, "11E")) {
                        c0364a.f1335g = jSONObject.getJSONObject("11E");
                    }
                    if (bb.m1319a(jSONObject, "11F")) {
                        c0364a.f1336h = jSONObject.getJSONObject("11F");
                    }
                    if (bb.m1319a(jSONObject, "11G")) {
                        c0364a.f1337i = jSONObject.getJSONObject("11G");
                    }
                    if (bb.m1319a(jSONObject, "001")) {
                        jSONObject2 = jSONObject.getJSONObject("001");
                        c0363c = new C0363c();
                        m1220a(jSONObject2, c0363c);
                        c0364a.f1341m = c0363c;
                    }
                    if (bb.m1319a(jSONObject, "002")) {
                        jSONObject2 = jSONObject.getJSONObject("002");
                        c0362b = new C0362b();
                        m1219a(jSONObject2, c0362b);
                        c0364a.f1342n = c0362b;
                    }
                    if (bb.m1319a(jSONObject, "006")) {
                        c0364a.f1338j = jSONObject.getJSONObject("006");
                    }
                    if (bb.m1319a(jSONObject, "010")) {
                        c0364a.f1339k = jSONObject.getJSONObject("010");
                    }
                }
                return c0364a;
            }
            return c0364a;
        } catch (IllegalBlockSizeException e5) {
            a = str2;
            a2 = str2;
            if (a != null) {
                return c0364a;
            }
            if (TextUtils.isEmpty(a2)) {
                a2 = bb.m1315a(a);
            }
            jSONObject = new JSONObject(a2);
            if (jSONObject.has("status")) {
                i = jSONObject.getInt("status");
                if (i != 1) {
                    f1343a = 1;
                } else if (i == 0) {
                    f1343a = 0;
                    if (jSONObject.has("info")) {
                        f1344b = jSONObject.getString("info");
                    }
                    if (f1343a == 0) {
                        c0364a.f1329a = f1344b;
                        return c0364a;
                    }
                }
                if (jSONObject.has(DeviceInfo.TAG_VERSION)) {
                    c0364a.f1330b = jSONObject.getInt(DeviceInfo.TAG_VERSION);
                }
                if (bb.m1319a(jSONObject, "result")) {
                    c0361a = new C0361a();
                    c0361a.f1322a = false;
                    c0361a.f1323b = false;
                    c0364a.f1340l = c0361a;
                    jSONObject = jSONObject.getJSONObject("result");
                    if (bb.m1319a(jSONObject, "11K")) {
                        c0361a.f1322a = m1221a(jSONObject.getJSONObject("11K").getString("able"), false);
                    }
                    if (bb.m1319a(jSONObject, "11B")) {
                        c0364a.f1331c = jSONObject.getJSONObject("11B");
                    }
                    if (bb.m1319a(jSONObject, "11C")) {
                        c0364a.f1332d = jSONObject.getJSONObject("11C");
                    }
                    if (bb.m1319a(jSONObject, "11I")) {
                        c0364a.f1333e = jSONObject.getJSONObject("11I");
                    }
                    if (bb.m1319a(jSONObject, "11H")) {
                        c0364a.f1334f = jSONObject.getJSONObject("11H");
                    }
                    if (bb.m1319a(jSONObject, "11E")) {
                        c0364a.f1335g = jSONObject.getJSONObject("11E");
                    }
                    if (bb.m1319a(jSONObject, "11F")) {
                        c0364a.f1336h = jSONObject.getJSONObject("11F");
                    }
                    if (bb.m1319a(jSONObject, "11G")) {
                        c0364a.f1337i = jSONObject.getJSONObject("11G");
                    }
                    if (bb.m1319a(jSONObject, "001")) {
                        jSONObject2 = jSONObject.getJSONObject("001");
                        c0363c = new C0363c();
                        m1220a(jSONObject2, c0363c);
                        c0364a.f1341m = c0363c;
                    }
                    if (bb.m1319a(jSONObject, "002")) {
                        jSONObject2 = jSONObject.getJSONObject("002");
                        c0362b = new C0362b();
                        m1219a(jSONObject2, c0362b);
                        c0364a.f1342n = c0362b;
                    }
                    if (bb.m1319a(jSONObject, "006")) {
                        c0364a.f1338j = jSONObject.getJSONObject("006");
                    }
                    if (bb.m1319a(jSONObject, "010")) {
                        c0364a.f1339k = jSONObject.getJSONObject("010");
                    }
                }
                return c0364a;
            }
            return c0364a;
        } catch (Throwable th4) {
            th2 = th4;
            a = str2;
            be.m1340a(th2, "ConfigManager", "loadConfig");
            a2 = str2;
            if (a != null) {
                return c0364a;
            }
            if (TextUtils.isEmpty(a2)) {
                a2 = bb.m1315a(a);
            }
            jSONObject = new JSONObject(a2);
            if (jSONObject.has("status")) {
                i = jSONObject.getInt("status");
                if (i != 1) {
                    f1343a = 1;
                } else if (i == 0) {
                    f1343a = 0;
                    if (jSONObject.has("info")) {
                        f1344b = jSONObject.getString("info");
                    }
                    if (f1343a == 0) {
                        c0364a.f1329a = f1344b;
                        return c0364a;
                    }
                }
                if (jSONObject.has(DeviceInfo.TAG_VERSION)) {
                    c0364a.f1330b = jSONObject.getInt(DeviceInfo.TAG_VERSION);
                }
                if (bb.m1319a(jSONObject, "result")) {
                    c0361a = new C0361a();
                    c0361a.f1322a = false;
                    c0361a.f1323b = false;
                    c0364a.f1340l = c0361a;
                    jSONObject = jSONObject.getJSONObject("result");
                    if (bb.m1319a(jSONObject, "11K")) {
                        c0361a.f1322a = m1221a(jSONObject.getJSONObject("11K").getString("able"), false);
                    }
                    if (bb.m1319a(jSONObject, "11B")) {
                        c0364a.f1331c = jSONObject.getJSONObject("11B");
                    }
                    if (bb.m1319a(jSONObject, "11C")) {
                        c0364a.f1332d = jSONObject.getJSONObject("11C");
                    }
                    if (bb.m1319a(jSONObject, "11I")) {
                        c0364a.f1333e = jSONObject.getJSONObject("11I");
                    }
                    if (bb.m1319a(jSONObject, "11H")) {
                        c0364a.f1334f = jSONObject.getJSONObject("11H");
                    }
                    if (bb.m1319a(jSONObject, "11E")) {
                        c0364a.f1335g = jSONObject.getJSONObject("11E");
                    }
                    if (bb.m1319a(jSONObject, "11F")) {
                        c0364a.f1336h = jSONObject.getJSONObject("11F");
                    }
                    if (bb.m1319a(jSONObject, "11G")) {
                        c0364a.f1337i = jSONObject.getJSONObject("11G");
                    }
                    if (bb.m1319a(jSONObject, "001")) {
                        jSONObject2 = jSONObject.getJSONObject("001");
                        c0363c = new C0363c();
                        m1220a(jSONObject2, c0363c);
                        c0364a.f1341m = c0363c;
                    }
                    if (bb.m1319a(jSONObject, "002")) {
                        jSONObject2 = jSONObject.getJSONObject("002");
                        c0362b = new C0362b();
                        m1219a(jSONObject2, c0362b);
                        c0364a.f1342n = c0362b;
                    }
                    if (bb.m1319a(jSONObject, "006")) {
                        c0364a.f1338j = jSONObject.getJSONObject("006");
                    }
                    if (bb.m1319a(jSONObject, "010")) {
                        c0364a.f1339k = jSONObject.getJSONObject("010");
                    }
                }
                return c0364a;
            }
            return c0364a;
        }
        if (a != null) {
            return c0364a;
        }
        if (TextUtils.isEmpty(a2)) {
            a2 = bb.m1315a(a);
        }
        jSONObject = new JSONObject(a2);
        if (jSONObject.has("status")) {
            i = jSONObject.getInt("status");
            if (i != 1) {
                f1343a = 1;
            } else if (i == 0) {
                f1343a = 0;
                if (jSONObject.has("info")) {
                    f1344b = jSONObject.getString("info");
                }
                if (f1343a == 0) {
                    c0364a.f1329a = f1344b;
                    return c0364a;
                }
            }
            if (jSONObject.has(DeviceInfo.TAG_VERSION)) {
                c0364a.f1330b = jSONObject.getInt(DeviceInfo.TAG_VERSION);
            }
            if (bb.m1319a(jSONObject, "result")) {
                c0361a = new C0361a();
                c0361a.f1322a = false;
                c0361a.f1323b = false;
                c0364a.f1340l = c0361a;
                jSONObject = jSONObject.getJSONObject("result");
                if (bb.m1319a(jSONObject, "11K")) {
                    c0361a.f1322a = m1221a(jSONObject.getJSONObject("11K").getString("able"), false);
                }
                if (bb.m1319a(jSONObject, "11B")) {
                    c0364a.f1331c = jSONObject.getJSONObject("11B");
                }
                if (bb.m1319a(jSONObject, "11C")) {
                    c0364a.f1332d = jSONObject.getJSONObject("11C");
                }
                if (bb.m1319a(jSONObject, "11I")) {
                    c0364a.f1333e = jSONObject.getJSONObject("11I");
                }
                if (bb.m1319a(jSONObject, "11H")) {
                    c0364a.f1334f = jSONObject.getJSONObject("11H");
                }
                if (bb.m1319a(jSONObject, "11E")) {
                    c0364a.f1335g = jSONObject.getJSONObject("11E");
                }
                if (bb.m1319a(jSONObject, "11F")) {
                    c0364a.f1336h = jSONObject.getJSONObject("11F");
                }
                if (bb.m1319a(jSONObject, "11G")) {
                    c0364a.f1337i = jSONObject.getJSONObject("11G");
                }
                if (bb.m1319a(jSONObject, "001")) {
                    jSONObject2 = jSONObject.getJSONObject("001");
                    c0363c = new C0363c();
                    m1220a(jSONObject2, c0363c);
                    c0364a.f1341m = c0363c;
                }
                if (bb.m1319a(jSONObject, "002")) {
                    jSONObject2 = jSONObject.getJSONObject("002");
                    c0362b = new C0362b();
                    m1219a(jSONObject2, c0362b);
                    c0364a.f1342n = c0362b;
                }
                if (bb.m1319a(jSONObject, "006")) {
                    c0364a.f1338j = jSONObject.getJSONObject("006");
                }
                if (bb.m1319a(jSONObject, "010")) {
                    c0364a.f1339k = jSONObject.getJSONObject("010");
                }
            }
            return c0364a;
        }
        return c0364a;
    }

    public static String m1218a(JSONObject jSONObject, String str) throws JSONException {
        if (jSONObject == null) {
            return "";
        }
        String str2 = "";
        if (!jSONObject.has(str) || jSONObject.getString(str).equals("[]")) {
            return str2;
        }
        return jSONObject.optString(str);
    }

    private static void m1219a(JSONObject jSONObject, C0362b c0362b) {
        if (jSONObject != null) {
            try {
                String a = m1218a(jSONObject, PromConstants.PROM_HTML5_INFO_MD5);
                String a2 = m1218a(jSONObject, "url");
                c0362b.f1325b = a;
                c0362b.f1324a = a2;
            } catch (Throwable th) {
                be.m1340a(th, "ConfigManager", "parseSDKCoordinate");
            }
        }
    }

    private static void m1220a(JSONObject jSONObject, C0363c c0363c) {
        if (jSONObject != null) {
            try {
                Object a = m1218a(jSONObject, PromConstants.PROM_HTML5_INFO_MD5);
                Object a2 = m1218a(jSONObject, "url");
                Object a3 = m1218a(jSONObject, "sdkversion");
                if (!TextUtils.isEmpty(a) && !TextUtils.isEmpty(a2) && !TextUtils.isEmpty(a3)) {
                    c0363c.f1326a = a2;
                    c0363c.f1327b = a;
                    c0363c.f1328c = a3;
                }
            } catch (Throwable th) {
                be.m1340a(th, "ConfigManager", "parseSDKUpdate");
            }
        }
    }
}

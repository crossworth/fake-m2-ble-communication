package com.droi.sdk.core.priv;

import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiRunnable;
import com.droi.sdk.internal.DroiLog;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

public class C0907i {
    private static final String f2934a = "OAuthKeys";
    private static C0907i f2935b;
    private boolean f2936c;
    private boolean f2937d;
    private C0906a f2938e = new C0906a();
    private final Object f2939f = new Object();
    private Map<String, Map<String, Object>> f2940g;

    private static class C0906a extends DroiRunnable {
        public DroiError f2932a;
        private C0907i f2933b;

        private C0906a() {
        }

        private DroiError m2677a() {
            DroiError droiError = new DroiError();
            HashMap hashMap = new HashMap();
            try {
                String b = C0939m.m2772b(C0899e.f2906k, null, droiError);
                if (droiError.isOk()) {
                    JSONObject jSONObject = new JSONObject(b);
                    int i = jSONObject.getInt("Code");
                    if (i != 0) {
                        return new DroiError(i, null);
                    }
                    if (jSONObject.has("Result")) {
                        jSONObject = jSONObject.getJSONObject("Result");
                        if (jSONObject != null) {
                            PersistSettings.instance(PersistSettings.CONFIG).setString(PersistSettings.KEY_OAUTH_KEYS, jSONObject.toString());
                            this.f2933b.m2681a(jSONObject);
                        }
                    }
                    return new DroiError();
                }
                DroiLog.m2874w(C0907i.f2934a, droiError.toString());
                return droiError;
            } catch (Exception e) {
                Exception exception = e;
                DroiLog.m2870e(C0907i.f2934a, "error to fetch oauth data. " + exception);
                return new DroiError(DroiError.ERROR, exception.toString());
            }
        }

        public void m2678a(C0907i c0907i) {
            this.f2933b = c0907i;
        }

        public void run() {
            try {
                this.f2933b.f2936c = true;
                this.f2932a = m2677a();
                if (this.f2932a.isOk()) {
                    this.f2933b.f2937d = true;
                }
                this.f2933b.f2936c = false;
                this.f2933b = null;
            } catch (Exception e) {
                DroiLog.m2869e(C0907i.f2934a, e);
                this.f2932a = new DroiError(DroiError.ERROR, e.toString());
            }
        }
    }

    private C0907i() {
        String string = PersistSettings.instance(PersistSettings.CONFIG).getString(PersistSettings.KEY_OAUTH_KEYS, null);
        if (string != null) {
            try {
                m2681a(new JSONObject(string));
            } catch (Exception e) {
                DroiLog.m2869e(f2934a, e);
            }
        }
    }

    public static synchronized C0907i m2679a() {
        C0907i c0907i;
        synchronized (C0907i.class) {
            if (f2935b == null) {
                f2935b = new C0907i();
            }
            c0907i = f2935b;
        }
        return c0907i;
    }

    private void m2681a(JSONObject jSONObject) {
        try {
            Iterator keys = jSONObject.keys();
            Map hashMap = new HashMap();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                JSONObject jSONObject2 = jSONObject.getJSONObject(str);
                Map hashMap2 = new HashMap();
                Iterator keys2 = jSONObject2.keys();
                while (keys2.hasNext()) {
                    String str2 = (String) keys2.next();
                    hashMap2.put(str2, jSONObject2.get(str2));
                }
                hashMap.put(str, hashMap2);
                synchronized (this.f2939f) {
                    this.f2940g = hashMap;
                }
            }
        } catch (Exception e) {
            DroiLog.m2869e(f2934a, e);
        }
    }

    public Map<String, Object> m2684a(String str) {
        Map<String, Object> map;
        synchronized (this.f2939f) {
            if (this.f2940g.containsKey(str)) {
                map = (Map) this.f2940g.get(str);
            } else {
                map = null;
            }
        }
        return map;
    }

    public DroiError m2685b() {
        this.f2938e.m2678a(this);
        this.f2938e.run();
        return this.f2938e.f2932a;
    }

    public boolean m2686c() {
        return this.f2937d;
    }

    public boolean m2687d() {
        return this.f2936c;
    }
}

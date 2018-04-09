package com.droi.sdk.core;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.priv.C0899e;
import com.droi.sdk.core.priv.C0939m;
import com.droi.sdk.core.priv.PersistSettings;
import com.droi.sdk.internal.DroiLog;
import org.json.JSONException;
import org.json.JSONObject;

public class DroiPreference {
    private static DroiPreference f2610a = null;
    private static final String f2611b = "DroiPrefernece";
    private final Object f2612c = new Object();
    private JSONObject f2613d;
    private boolean f2614e;
    private boolean f2615f;
    private C0842a f2616g = new C0842a();

    private static class C0842a extends DroiRunnable {
        public DroiError f2608a;
        private DroiPreference f2609b;

        private C0842a() {
        }

        private DroiError m2560a() {
            try {
                DroiError droiError = new DroiError();
                String b = C0939m.m2772b(C0899e.f2907l, null, droiError);
                if (!droiError.isOk()) {
                    DroiLog.m2870e(DroiPreference.f2611b, droiError.toString());
                }
                if (b == null) {
                    return droiError;
                }
                JSONObject jSONObject = new JSONObject(b);
                int i = jSONObject.getInt("Code");
                if (i != 0) {
                    return new DroiError(i, null);
                }
                if (jSONObject.has("Result")) {
                    jSONObject = jSONObject.getJSONObject("Result");
                    PersistSettings.instance(PersistSettings.CONFIG).setString(PersistSettings.KEY_PREFERENCE, jSONObject.toString());
                    this.f2609b.f2613d = jSONObject;
                }
                return new DroiError();
            } catch (Exception e) {
                Exception exception = e;
                DroiLog.m2870e(DroiPreference.f2611b, "error to fetch preference data. " + exception);
                return new DroiError(DroiError.ERROR, exception.toString());
            }
        }

        public void m2561a(DroiPreference droiPreference) {
            this.f2609b = droiPreference;
        }

        public void run() {
            try {
                this.f2609b.f2614e = true;
                this.f2608a = m2560a();
                if (this.f2608a.isOk()) {
                    this.f2609b.f2615f = true;
                }
                this.f2609b.f2614e = false;
                this.f2609b = null;
            } catch (Exception e) {
                DroiLog.m2869e(DroiPreference.f2611b, e);
                this.f2608a = new DroiError(DroiError.ERROR, e.toString());
            }
        }
    }

    private DroiPreference() {
        String string = PersistSettings.instance(PersistSettings.CONFIG).getString(PersistSettings.KEY_PREFERENCE, null);
        if (string != null) {
            try {
                this.f2613d = new JSONObject(string);
            } catch (Exception e) {
                DroiLog.m2869e(f2611b, e);
            }
        }
    }

    public static synchronized DroiPreference instance() {
        DroiPreference droiPreference;
        synchronized (DroiPreference.class) {
            if (f2610a == null) {
                f2610a = new DroiPreference();
            }
            droiPreference = f2610a;
        }
        return droiPreference;
    }

    public boolean getBoolean(String str, boolean z) {
        if (this.f2613d != null && this.f2613d.has(str)) {
            try {
                z = this.f2613d.getBoolean(str);
            } catch (JSONException e) {
            }
        }
        return z;
    }

    public double getDouble(String str, double d) {
        if (this.f2613d != null && this.f2613d.has(str)) {
            try {
                d = this.f2613d.getDouble(str);
            } catch (JSONException e) {
            }
        }
        return d;
    }

    public int getInt(String str, int i) {
        if (this.f2613d != null && this.f2613d.has(str)) {
            try {
                i = this.f2613d.getInt(str);
            } catch (JSONException e) {
            }
        }
        return i;
    }

    public JSONObject getJsonObject(String str) {
        JSONObject jSONObject = null;
        if (this.f2613d != null && this.f2613d.has(str)) {
            try {
                jSONObject = this.f2613d.getJSONObject(str);
            } catch (JSONException e) {
            }
        }
        return jSONObject;
    }

    public String getString(String str) {
        String str2 = null;
        if (this.f2613d != null && this.f2613d.has(str)) {
            try {
                str2 = this.f2613d.getString(str);
            } catch (JSONException e) {
            }
        }
        return str2;
    }

    public boolean isReady() {
        return this.f2615f;
    }

    public boolean isRefreshing() {
        return this.f2614e;
    }

    public DroiError refresh() {
        this.f2616g.m2561a(this);
        this.f2616g.run();
        return this.f2616g.f2608a;
    }

    public boolean refreshInBackground(final DroiCallback<Boolean> droiCallback) {
        TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
        this.f2616g.m2561a(this);
        return DroiTask.create(this.f2616g).callback(new DroiRunnable(this) {
            final /* synthetic */ DroiPreference f2607b;

            public void run() {
                DroiError droiError = this.f2607b.f2616g.f2608a;
                if (droiCallback != null) {
                    droiCallback.result(Boolean.valueOf(droiError.isOk()), droiError);
                }
            }
        }, currentTaskDispatcher.name()).runInBackground("TaskDispatcher_DroiBackgroundThread").booleanValue();
    }
}

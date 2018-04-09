package com.droi.sdk.core;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.priv.C0899e;
import com.droi.sdk.core.priv.C0939m;
import com.tyd.aidlservice.internal.Constants;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONObject;

public class DroiCloud {
    private static final String f2436a = "DROI_CLOUD";

    public enum Method {
        GET,
        HEAD,
        POST,
        PUT,
        PATCH,
        DELETE,
        TRACE,
        OPTIONS
    }

    public static <T extends DroiObject, V extends DroiObject> V callCloudService(String str, T t, Class<V> cls, DroiError droiError) {
        String jSONObject;
        if (droiError == null) {
            droiError = new DroiError();
        }
        if (t != null) {
            try {
                JSONObject toJson = t.toJson(droiError);
                if (!droiError.isOk()) {
                    return null;
                }
                jSONObject = toJson.toString();
            } catch (Exception e) {
                if (droiError == null) {
                    return null;
                }
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage(e.toString());
                return null;
            }
        }
        jSONObject = null;
        jSONObject = C0939m.m2772b(String.format("%s/%s", new Object[]{C0899e.f2896a, str}), jSONObject, droiError);
        if (!droiError.isOk() || jSONObject == null) {
            return null;
        }
        JSONObject jSONObject2 = new JSONObject(jSONObject);
        int i = jSONObject2.getInt("Code");
        if (i == 0) {
            if (!jSONObject2.has("Result")) {
                return null;
            }
            V fromJson = DroiObject.fromJson(jSONObject2.getJSONObject("Result"), cls);
            if (fromJson == null) {
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage("Form DroiObject fail. class: " + cls.getSimpleName());
                return null;
            } else if (fromJson.getClass() == cls) {
                return fromJson;
            } else {
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage("The class need to registered first. " + cls);
                return null;
            }
        } else if (droiError == null) {
            return null;
        } else {
            droiError.setCode(i);
            return null;
        }
    }

    public static <T extends DroiObject, V extends DroiObject> boolean callCloudServiceInBackground(String str, T t, final DroiCallback<V> droiCallback, Class<V> cls) {
        final AtomicReference atomicReference = new AtomicReference(null);
        final DroiError droiError = new DroiError();
        final TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
        final String str2 = str;
        final T t2 = t;
        final Class<V> cls2 = cls;
        return DroiTask.create(new DroiRunnable() {
            public void run() {
                atomicReference.set(DroiCloud.callCloudService(str2, t2, cls2, droiError));
            }
        }).callback(new DroiRunnable() {

            class C07891 implements Runnable {
                final /* synthetic */ C07901 f2414a;

                C07891(C07901 c07901) {
                    this.f2414a = c07901;
                }

                public void run() {
                    droiCallback.result(atomicReference.get(), droiError);
                }
            }

            public void run() {
                if (droiCallback != null) {
                    currentTaskDispatcher.enqueueTask(new C07891(this));
                }
            }
        }).runInBackground("TaskDispatcher_DroiBackgroundThread").booleanValue();
    }

    public static <T extends DroiObject, V extends DroiObject> boolean callCloudServiceInBackground(String str, String str2, Method method, T t, final DroiCallback<V> droiCallback, Class<V> cls) {
        final AtomicReference atomicReference = new AtomicReference(null);
        final DroiError droiError = new DroiError();
        final TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
        final String str3 = str;
        final String str4 = str2;
        final Method method2 = method;
        final T t2 = t;
        final Class<V> cls2 = cls;
        return DroiTask.create(new DroiRunnable() {
            public void run() {
                atomicReference.set(DroiCloud.callRestApi(str3, str4, method2, t2, cls2, droiError));
            }
        }).callback(new DroiRunnable() {

            class C07921 implements Runnable {
                final /* synthetic */ C07933 f2424a;

                C07921(C07933 c07933) {
                    this.f2424a = c07933;
                }

                public void run() {
                    droiCallback.result(atomicReference.get(), droiError);
                }
            }

            public void run() {
                if (droiCallback != null) {
                    currentTaskDispatcher.enqueueTask(new C07921(this));
                }
            }
        }).runInBackground("TaskDispatcher_DroiBackgroundThread").booleanValue();
    }

    public static <T extends DroiObject, V extends DroiObject> V callRestApi(String str, String str2, Method method, T t, Class<V> cls, DroiError droiError) {
        String jSONObject;
        if (droiError == null) {
            droiError = new DroiError();
        }
        if (t != null) {
            try {
                JSONObject toJson = t.toJson(droiError);
                if (!droiError.isOk()) {
                    return null;
                }
                jSONObject = toJson.toString();
            } catch (Exception e) {
                if (droiError == null) {
                    return null;
                }
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage(e.toString());
                return null;
            }
        }
        jSONObject = null;
        Map hashMap = new HashMap();
        hashMap.put(Constants.HTTP_HEADER_API_KEY, str);
        jSONObject = C0939m.m2766a(str2, method.toString(), jSONObject, hashMap, droiError);
        if (!droiError.isOk() || jSONObject == null) {
            return null;
        }
        JSONObject jSONObject2 = new JSONObject(jSONObject);
        int i = jSONObject2.getInt("Code");
        if (i == 0) {
            if (!jSONObject2.has("Result")) {
                return null;
            }
            V fromJson = DroiObject.fromJson(jSONObject2.getJSONObject("Result"), cls);
            if (fromJson == null) {
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage("Form DroiObject fail. class: " + cls.getSimpleName());
                return null;
            } else if (fromJson.getClass() == cls) {
                return fromJson;
            } else {
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage("The class need to registered first. " + cls);
                return null;
            }
        } else if (droiError == null) {
            return null;
        } else {
            droiError.setCode(i);
            return null;
        }
    }
}

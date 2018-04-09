package com.droi.sdk.push.p019a;

import android.content.Context;
import android.text.TextUtils;
import com.droi.sdk.push.C1004t;
import com.droi.sdk.push.DroiPush;
import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1015j;
import com.umeng.facebook.GraphResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class C0977d implements Runnable {
    private String f3219a;
    protected boolean f3220b = false;
    protected boolean f3221c = false;
    protected Thread f3222d;
    private Context f3223e;

    public C0977d(Context context, String str) {
        this.f3223e = context;
        this.f3219a = str;
    }

    private String m2950d() {
        String str = null;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("osType", 1);
            jSONObject.put("timeStamp", System.currentTimeMillis() + "");
            jSONObject.put("sdkVer", 2);
            jSONObject.put("deviceId", this.f3219a);
            str = jSONObject.toString();
        } catch (Exception e) {
            C1012g.m3137a(e);
        }
        return str;
    }

    private void m2951e() {
        String str = null;
        Object obj;
        try {
            String d = m2950d();
            if (d != null) {
                JSONObject jSONObject;
                Object string;
                String a = C1015j.m3152a(this.f3223e.getApplicationContext(), "http://push_service.droibaas.com:2500/device/message/polling", d, DroiPush.getSecret(this.f3223e), DroiPush.getAppId(this.f3223e));
                if (a != null) {
                    jSONObject = new JSONObject(a);
                    if (jSONObject == null) {
                        try {
                            string = jSONObject.getString("result");
                            try {
                                jSONObject = jSONObject.getJSONObject("data");
                            } catch (JSONException e) {
                                C1012g.m3140b("Parse json error and return, reason: " + e);
                                return;
                            }
                        } catch (JSONException e2) {
                            C1012g.m3140b("Parse json error and return, reason: " + e2);
                            return;
                        }
                    }
                    obj = str;
                    a = str;
                    if (!TextUtils.isEmpty(string) || jSONObject == null) {
                        C1012g.m3142d("The data or result received in cmwap mode is null!");
                    }
                    if ((string.equals(GraphResponse.SUCCESS_KEY) ? 1 : null) != null) {
                        try {
                            JSONArray jSONArray = jSONObject.getJSONArray("msgList");
                            if (jSONArray != null) {
                                for (int i = 0; i < jSONArray.length(); i++) {
                                    try {
                                        str = jSONArray.getString(i);
                                        if (C1015j.m3168d(str)) {
                                            mo1928a(new C1004t(str));
                                        }
                                    } catch (Exception e3) {
                                        C1012g.m3137a(e3);
                                    }
                                }
                                return;
                            }
                            return;
                        } catch (JSONException e22) {
                            C1012g.m3140b("Parse json error and return, reason: " + e22);
                            return;
                        }
                    }
                    try {
                        str = jSONObject.getString("errorCode");
                    } catch (JSONException e4) {
                    }
                    C1012g.m3142d("Get push message in cmwap mode failed, errorCode: " + str);
                    return;
                }
                obj = str;
                if (jSONObject == null) {
                    obj = str;
                    a = str;
                } else {
                    string = jSONObject.getString("result");
                    jSONObject = jSONObject.getJSONObject("data");
                }
                if (TextUtils.isEmpty(string)) {
                }
                C1012g.m3142d("The data or result received in cmwap mode is null!");
            }
        } catch (Exception e5) {
            C1012g.m3137a(e5);
            obj = str;
        } catch (JSONException e222) {
            C1012g.m3142d("Parse json error and return, reason: " + e222);
        }
    }

    public abstract void mo1928a(C1004t c1004t);

    public abstract boolean mo1929a();

    public synchronized void m2954b() {
        if (!this.f3220b) {
            this.f3222d = new Thread(this, "wap-client");
            this.f3222d.setDaemon(true);
            this.f3222d.start();
            this.f3220b = true;
        }
    }

    public void m2955c() {
        this.f3221c = true;
    }

    public void run() {
        while (!this.f3221c) {
            try {
                if (mo1929a()) {
                    m2951e();
                    Thread.sleep(300000);
                } else {
                    Thread.sleep(60000);
                }
            } catch (Exception e) {
                C1012g.m3137a(e);
            }
        }
    }
}

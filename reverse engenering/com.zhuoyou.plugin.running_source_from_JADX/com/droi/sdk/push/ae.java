package com.droi.sdk.push;

import android.content.Context;
import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1015j;
import com.umeng.facebook.GraphResponse;
import org.json.JSONException;
import org.json.JSONObject;

final class ae implements Runnable {
    final /* synthetic */ C0994n f3234a;
    final /* synthetic */ Context f3235b;
    final /* synthetic */ String f3236c;
    final /* synthetic */ String f3237d;
    final /* synthetic */ String f3238e;
    final /* synthetic */ String f3239f;
    private C0994n f3240g = this.f3234a;

    ae(C0994n c0994n, Context context, String str, String str2, String str3, String str4) {
        this.f3234a = c0994n;
        this.f3235b = context;
        this.f3236c = str;
        this.f3237d = str2;
        this.f3238e = str3;
        this.f3239f = str4;
    }

    public void run() {
        JSONObject jSONObject = null;
        try {
            String a = C1015j.m3152a(this.f3235b, this.f3236c, this.f3237d, this.f3238e, this.f3239f);
            if (a != null) {
                try {
                    Object string;
                    JSONObject jSONObject2 = new JSONObject(a);
                    JSONObject jSONObject3;
                    if (jSONObject2 != null) {
                        try {
                            string = jSONObject2.getString("result");
                        } catch (JSONException e) {
                            C1012g.m3140b("getRunnable: json has no 'result' field");
                            jSONObject3 = jSONObject;
                        }
                        try {
                            jSONObject = jSONObject2.getJSONObject("data");
                        } catch (JSONException e2) {
                            C1012g.m3140b("getRunnable: json has no 'data' field");
                        }
                    } else {
                        jSONObject3 = jSONObject;
                    }
                    this.f3240g.mo1927a(GraphResponse.SUCCESS_KEY.equals(string), jSONObject);
                } catch (JSONException e3) {
                    C1012g.m3140b("getRunnable createJsonError: " + a);
                }
            }
        } catch (Exception e4) {
            C1012g.m3137a(e4);
        }
    }
}

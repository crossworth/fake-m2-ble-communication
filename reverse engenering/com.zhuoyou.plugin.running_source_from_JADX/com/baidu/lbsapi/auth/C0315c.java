package com.baidu.lbsapi.auth;

import android.content.Context;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

class C0315c {
    private Context f72a;
    private HashMap<String, String> f73b = null;
    private C0314a<String> f74c = null;

    interface C0314a<Result> {
        void mo1738a(Result result);
    }

    protected C0315c(Context context) {
        this.f72a = context;
    }

    private HashMap<String, String> m137a(HashMap<String, String> hashMap) {
        HashMap<String, String> hashMap2 = new HashMap();
        for (String str : hashMap.keySet()) {
            String str2 = str2.toString();
            hashMap2.put(str2, hashMap.get(str2));
        }
        return hashMap2;
    }

    private void m139a(String str) {
        JSONObject jSONObject;
        if (str == null) {
            str = "";
        }
        try {
            jSONObject = new JSONObject(str);
            if (!jSONObject.has("status")) {
                jSONObject.put("status", -1);
            }
        } catch (JSONException e) {
            jSONObject = new JSONObject();
            try {
                jSONObject.put("status", -1);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        if (this.f74c != null) {
            this.f74c.mo1738a(jSONObject != null ? jSONObject.toString() : new JSONObject().toString());
        }
    }

    protected void m141a(HashMap<String, String> hashMap, C0314a<String> c0314a) {
        this.f73b = m137a((HashMap) hashMap);
        this.f74c = c0314a;
        new Thread(new C0316d(this)).start();
    }
}

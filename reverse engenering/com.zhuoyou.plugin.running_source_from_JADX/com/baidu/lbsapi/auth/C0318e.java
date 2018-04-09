package com.baidu.lbsapi.auth;

import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

class C0318e {
    private Context f76a;
    private List<HashMap<String, String>> f77b = null;
    private C0317a<String> f78c = null;

    interface C0317a<Result> {
        void mo1739a(Result result);
    }

    protected C0318e(Context context) {
        this.f76a = context;
    }

    private List<HashMap<String, String>> m144a(HashMap<String, String> hashMap, String[] strArr) {
        List<HashMap<String, String>> arrayList = new ArrayList();
        String str;
        if (strArr == null || strArr.length <= 0) {
            HashMap hashMap2 = new HashMap();
            for (String str2 : hashMap.keySet()) {
                str2 = str2.toString();
                hashMap2.put(str2, hashMap.get(str2));
            }
            arrayList.add(hashMap2);
        } else {
            for (Object put : strArr) {
                HashMap hashMap3 = new HashMap();
                for (String str22 : hashMap.keySet()) {
                    str22 = str22.toString();
                    hashMap3.put(str22, hashMap.get(str22));
                }
                hashMap3.put("mcode", put);
                arrayList.add(hashMap3);
            }
        }
        return arrayList;
    }

    private void m146a(String str) {
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
        if (this.f78c != null) {
            this.f78c.mo1739a(jSONObject != null ? jSONObject.toString() : new JSONObject().toString());
        }
    }

    private void m147a(List<HashMap<String, String>> list) {
        C0311a.m122a("syncConnect start Thread id = " + String.valueOf(Thread.currentThread().getId()));
        if (list == null || list.size() == 0) {
            C0311a.m123b("syncConnect failed,params list is null or size is 0");
            return;
        }
        List arrayList = new ArrayList();
        int i = 0;
        while (i < list.size()) {
            JSONObject jSONObject;
            C0311a.m122a("syncConnect resuest " + i + "  start!!!");
            HashMap hashMap = (HashMap) list.get(i);
            C0320g c0320g = new C0320g(this.f76a);
            if (c0320g.m155a()) {
                String a = c0320g.m154a(hashMap);
                if (a == null) {
                    a = "";
                }
                C0311a.m122a("syncConnect resuest " + i + "  result:" + a);
                arrayList.add(a);
                try {
                    jSONObject = new JSONObject(a);
                    if (jSONObject.has("status") && jSONObject.getInt("status") == 0) {
                        C0311a.m122a("auth end and break");
                        m146a(a);
                        return;
                    }
                } catch (JSONException e) {
                    C0311a.m122a("continue-------------------------------");
                }
            } else {
                C0311a.m122a("Current network is not available.");
                arrayList.add(ErrorMessage.m102a("Current network is not available."));
            }
            C0311a.m122a("syncConnect end");
            i++;
        }
        C0311a.m122a("--iiiiii:" + i + "<><>paramList.size():" + list.size() + "<><>authResults.size():" + arrayList.size());
        if (list.size() > 0 && i == list.size() && arrayList.size() > 0 && i == arrayList.size() && i - 1 > 0) {
            try {
                jSONObject = new JSONObject((String) arrayList.get(i - 1));
                if (jSONObject.has("status") && jSONObject.getInt("status") != 0) {
                    C0311a.m122a("i-1 result is not 0,return first result");
                    m146a((String) arrayList.get(0));
                }
            } catch (JSONException e2) {
                m146a(ErrorMessage.m102a("JSONException:" + e2.getMessage()));
            }
        }
    }

    protected void m148a(HashMap<String, String> hashMap, String[] strArr, C0317a<String> c0317a) {
        this.f77b = m144a((HashMap) hashMap, strArr);
        this.f78c = c0317a;
        new Thread(new C0319f(this)).start();
    }
}

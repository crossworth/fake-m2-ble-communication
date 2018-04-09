package com.droi.sdk.push;

import com.droi.sdk.push.utils.C1012g;
import org.json.JSONException;
import org.json.JSONObject;

class C0996j implements C0994n {
    final /* synthetic */ DroiPushService f3311a;

    C0996j(DroiPushService droiPushService) {
        this.f3311a = droiPushService;
    }

    public void mo1927a(boolean z, JSONObject jSONObject) {
        if (z) {
            C1012g.m3141c("getServerIpAddress: success!");
            ad.m2995a(this.f3311a.f3191g, jSONObject);
            this.f3311a.m2918a(true);
            return;
        }
        C1012g.m3141c("getServerIpAddress: failed!");
        String str = null;
        try {
            str = jSONObject.getString("errorCode");
        } catch (JSONException e) {
            C1012g.m3140b("parse 'errorCode' for ips failed");
        }
        if (str != null) {
            C1012g.m3140b("Get server ip address error, code = " + str);
        }
    }
}

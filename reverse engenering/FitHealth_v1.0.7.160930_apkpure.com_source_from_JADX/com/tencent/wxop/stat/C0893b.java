package com.tencent.wxop.stat;

import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import org.json.JSONException;
import org.json.JSONObject;

public final class C0893b {
    private long f3039K = 0;
    private int f3040L = 0;
    private String f3041M = "";
    private String f3042c = "";
    private int f3043g = 0;

    public final void m2924a(long j) {
        this.f3039K = j;
    }

    public final JSONObject m2925i() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("tm", this.f3039K);
            jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_ST, this.f3043g);
            if (this.f3042c != null) {
                jSONObject.put("dm", this.f3042c);
            }
            jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_SNSACCOUNT_ICON, this.f3040L);
            if (this.f3041M != null) {
                jSONObject.put("rip", this.f3041M);
            }
            jSONObject.put("ts", System.currentTimeMillis() / 1000);
        } catch (JSONException e) {
        }
        return jSONObject;
    }

    public final void m2926k(String str) {
        this.f3041M = str;
    }

    public final void setDomain(String str) {
        this.f3042c = str;
    }

    public final void setPort(int i) {
        this.f3040L = i;
    }

    public final void setStatusCode(int i) {
        this.f3043g = i;
    }
}

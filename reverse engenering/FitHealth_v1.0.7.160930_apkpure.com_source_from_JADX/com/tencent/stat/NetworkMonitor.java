package com.tencent.stat;

import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class NetworkMonitor {
    private long f2772a = 0;
    private int f2773b = 0;
    private String f2774c = "";
    private int f2775d = 0;
    private String f2776e = "";

    public String getDomain() {
        return this.f2774c;
    }

    public long getMillisecondsConsume() {
        return this.f2772a;
    }

    public int getPort() {
        return this.f2775d;
    }

    public String getRemoteIp() {
        return this.f2776e;
    }

    public int getStatusCode() {
        return this.f2773b;
    }

    public void setDomain(String str) {
        this.f2774c = str;
    }

    public void setMillisecondsConsume(long j) {
        this.f2772a = j;
    }

    public void setPort(int i) {
        this.f2775d = i;
    }

    public void setRemoteIp(String str) {
        this.f2776e = str;
    }

    public void setStatusCode(int i) {
        this.f2773b = i;
    }

    public JSONObject toJSONObject() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("tm", this.f2772a);
            jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_ST, this.f2773b);
            if (this.f2774c != null) {
                jSONObject.put("dm", this.f2774c);
            }
            jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_SNSACCOUNT_ICON, this.f2775d);
            if (this.f2776e != null) {
                jSONObject.put("rip", this.f2776e);
            }
            jSONObject.put("ts", System.currentTimeMillis() / 1000);
        } catch (JSONException e) {
        }
        return jSONObject;
    }
}

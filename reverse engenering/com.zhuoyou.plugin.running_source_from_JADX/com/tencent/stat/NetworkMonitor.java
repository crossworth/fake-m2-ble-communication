package com.tencent.stat;

import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class NetworkMonitor {
    private long f4274a = 0;
    private int f4275b = 0;
    private String f4276c = "";
    private int f4277d = 0;
    private String f4278e = "";

    public String getDomain() {
        return this.f4276c;
    }

    public long getMillisecondsConsume() {
        return this.f4274a;
    }

    public int getPort() {
        return this.f4277d;
    }

    public String getRemoteIp() {
        return this.f4278e;
    }

    public int getStatusCode() {
        return this.f4275b;
    }

    public void setDomain(String str) {
        this.f4276c = str;
    }

    public void setMillisecondsConsume(long j) {
        this.f4274a = j;
    }

    public void setPort(int i) {
        this.f4277d = i;
    }

    public void setRemoteIp(String str) {
        this.f4278e = str;
    }

    public void setStatusCode(int i) {
        this.f4275b = i;
    }

    public JSONObject toJSONObject() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("tm", this.f4274a);
            jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_ST, this.f4275b);
            if (this.f4276c != null) {
                jSONObject.put("dm", this.f4276c);
            }
            jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_SNSACCOUNT_ICON, this.f4277d);
            if (this.f4278e != null) {
                jSONObject.put("rip", this.f4278e);
            }
            jSONObject.put(DeviceInfo.TAG_TIMESTAMPS, System.currentTimeMillis() / 1000);
        } catch (JSONException e) {
        }
        return jSONObject;
    }
}

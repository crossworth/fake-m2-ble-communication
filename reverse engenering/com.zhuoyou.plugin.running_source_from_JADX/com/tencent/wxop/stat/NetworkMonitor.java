package com.tencent.wxop.stat;

import com.tencent.stat.DeviceInfo;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class NetworkMonitor {
    private long f4499a = 0;
    private int f4500b = 0;
    private String f4501c = "";
    private int f4502d = 0;
    private String f4503e = "";

    public String getDomain() {
        return this.f4501c;
    }

    public long getMillisecondsConsume() {
        return this.f4499a;
    }

    public int getPort() {
        return this.f4502d;
    }

    public String getRemoteIp() {
        return this.f4503e;
    }

    public int getStatusCode() {
        return this.f4500b;
    }

    public void setDomain(String str) {
        this.f4501c = str;
    }

    public void setMillisecondsConsume(long j) {
        this.f4499a = j;
    }

    public void setPort(int i) {
        this.f4502d = i;
    }

    public void setRemoteIp(String str) {
        this.f4503e = str;
    }

    public void setStatusCode(int i) {
        this.f4500b = i;
    }

    public JSONObject toJSONObject() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("tm", this.f4499a);
            jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_ST, this.f4500b);
            if (this.f4501c != null) {
                jSONObject.put("dm", this.f4501c);
            }
            jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_SNSACCOUNT_ICON, this.f4502d);
            if (this.f4503e != null) {
                jSONObject.put("rip", this.f4503e);
            }
            jSONObject.put(DeviceInfo.TAG_TIMESTAMPS, System.currentTimeMillis() / 1000);
        } catch (JSONException e) {
        }
        return jSONObject;
    }
}

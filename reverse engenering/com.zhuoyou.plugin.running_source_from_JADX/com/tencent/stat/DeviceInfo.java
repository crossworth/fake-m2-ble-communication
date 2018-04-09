package com.tencent.stat;

import com.tencent.stat.common.C1389k;
import com.tencent.stat.common.StatLogger;
import org.json.JSONObject;

public class DeviceInfo {
    public static final int NEW_USER = 0;
    public static final int OLD_USER = 1;
    public static final String TAG_ANDROID_ID = "aid";
    public static final String TAG_FLAG = "__MTA_DEVICE_INFO__";
    public static final String TAG_IMEI = "ui";
    public static final String TAG_MAC = "mc";
    public static final String TAG_MID = "mid";
    public static final String TAG_TIMESTAMPS = "ts";
    public static final String TAG_VERSION = "ver";
    public static final int UPGRADE_USER = 2;
    private static StatLogger f4266h = C1389k.m4125b();
    private String f4267a = null;
    private String f4268b = null;
    private String f4269c = null;
    private String f4270d = "0";
    private int f4271e;
    private int f4272f = 0;
    private long f4273g = 0;

    DeviceInfo() {
    }

    DeviceInfo(String str, String str2, int i) {
        this.f4267a = str;
        this.f4268b = str2;
        this.f4271e = i;
    }

    static DeviceInfo m3991a(String str) {
        DeviceInfo deviceInfo = new DeviceInfo();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.isNull(TAG_IMEI)) {
                deviceInfo.m4001d(jSONObject.getString(TAG_IMEI));
            }
            if (!jSONObject.isNull(TAG_MAC)) {
                deviceInfo.m4002e(jSONObject.getString(TAG_MAC));
            }
            if (!jSONObject.isNull(TAG_MID)) {
                deviceInfo.m4000c(jSONObject.getString(TAG_MID));
            }
            if (!jSONObject.isNull("aid")) {
                deviceInfo.m3998b(jSONObject.getString("aid"));
            }
            if (!jSONObject.isNull(TAG_TIMESTAMPS)) {
                deviceInfo.m3995a(jSONObject.getLong(TAG_TIMESTAMPS));
            }
            if (!jSONObject.isNull(TAG_VERSION)) {
                deviceInfo.m3994a(jSONObject.getInt(TAG_VERSION));
            }
        } catch (Exception e) {
            f4266h.m4084e(e);
        }
        return deviceInfo;
    }

    int m3992a() {
        return this.f4272f;
    }

    int m3993a(DeviceInfo deviceInfo) {
        if (deviceInfo == null) {
            return 1;
        }
        String mid = getMid();
        String mid2 = deviceInfo.getMid();
        if (mid != null && mid2 != null && mid.equals(mid2)) {
            return 0;
        }
        int a = m3992a();
        int a2 = deviceInfo.m3992a();
        if (a > a2) {
            return 1;
        }
        if (a != a2) {
            return -1;
        }
        long b = m3996b();
        long b2 = deviceInfo.m3996b();
        return b <= b2 ? b == b2 ? 0 : -1 : 1;
    }

    void m3994a(int i) {
        this.f4272f = i;
    }

    void m3995a(long j) {
        this.f4273g = j;
    }

    long m3996b() {
        return this.f4273g;
    }

    void m3997b(int i) {
        this.f4271e = i;
    }

    void m3998b(String str) {
        this.f4269c = str;
    }

    JSONObject m3999c() {
        JSONObject jSONObject = new JSONObject();
        try {
            C1389k.m4121a(jSONObject, TAG_IMEI, this.f4267a);
            C1389k.m4121a(jSONObject, TAG_MAC, this.f4268b);
            C1389k.m4121a(jSONObject, TAG_MID, this.f4270d);
            C1389k.m4121a(jSONObject, "aid", this.f4269c);
            jSONObject.put(TAG_TIMESTAMPS, this.f4273g);
            jSONObject.put(TAG_VERSION, this.f4272f);
        } catch (Exception e) {
            f4266h.m4084e(e);
        }
        return jSONObject;
    }

    void m4000c(String str) {
        this.f4270d = str;
    }

    void m4001d(String str) {
        this.f4267a = str;
    }

    void m4002e(String str) {
        this.f4268b = str;
    }

    public String getImei() {
        return this.f4267a;
    }

    public String getMac() {
        return this.f4268b;
    }

    public String getMid() {
        return this.f4270d;
    }

    public int getUserType() {
        return this.f4271e;
    }

    public String toString() {
        return m3999c().toString();
    }
}

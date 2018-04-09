package com.tencent.stat;

import com.tencent.stat.common.C0837k;
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
    private static StatLogger f2764h = C0837k.m2718b();
    private String f2765a = null;
    private String f2766b = null;
    private String f2767c = null;
    private String f2768d = "0";
    private int f2769e;
    private int f2770f = 0;
    private long f2771g = 0;

    DeviceInfo() {
    }

    DeviceInfo(String str, String str2, int i) {
        this.f2765a = str;
        this.f2766b = str2;
        this.f2769e = i;
    }

    static DeviceInfo m2607a(String str) {
        DeviceInfo deviceInfo = new DeviceInfo();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.isNull(TAG_IMEI)) {
                deviceInfo.m2617d(jSONObject.getString(TAG_IMEI));
            }
            if (!jSONObject.isNull("mc")) {
                deviceInfo.m2618e(jSONObject.getString("mc"));
            }
            if (!jSONObject.isNull(TAG_MID)) {
                deviceInfo.m2616c(jSONObject.getString(TAG_MID));
            }
            if (!jSONObject.isNull(TAG_ANDROID_ID)) {
                deviceInfo.m2614b(jSONObject.getString(TAG_ANDROID_ID));
            }
            if (!jSONObject.isNull("ts")) {
                deviceInfo.m2611a(jSONObject.getLong("ts"));
            }
            if (!jSONObject.isNull(TAG_VERSION)) {
                deviceInfo.m2610a(jSONObject.getInt(TAG_VERSION));
            }
        } catch (Exception e) {
            f2764h.m2679e(e);
        }
        return deviceInfo;
    }

    int m2608a() {
        return this.f2770f;
    }

    int m2609a(DeviceInfo deviceInfo) {
        if (deviceInfo == null) {
            return 1;
        }
        String mid = getMid();
        String mid2 = deviceInfo.getMid();
        if (mid != null && mid2 != null && mid.equals(mid2)) {
            return 0;
        }
        int a = m2608a();
        int a2 = deviceInfo.m2608a();
        if (a > a2) {
            return 1;
        }
        if (a != a2) {
            return -1;
        }
        long b = m2612b();
        long b2 = deviceInfo.m2612b();
        return b <= b2 ? b == b2 ? 0 : -1 : 1;
    }

    void m2610a(int i) {
        this.f2770f = i;
    }

    void m2611a(long j) {
        this.f2771g = j;
    }

    long m2612b() {
        return this.f2771g;
    }

    void m2613b(int i) {
        this.f2769e = i;
    }

    void m2614b(String str) {
        this.f2767c = str;
    }

    JSONObject m2615c() {
        JSONObject jSONObject = new JSONObject();
        try {
            C0837k.m2714a(jSONObject, TAG_IMEI, this.f2765a);
            C0837k.m2714a(jSONObject, "mc", this.f2766b);
            C0837k.m2714a(jSONObject, TAG_MID, this.f2768d);
            C0837k.m2714a(jSONObject, TAG_ANDROID_ID, this.f2767c);
            jSONObject.put("ts", this.f2771g);
            jSONObject.put(TAG_VERSION, this.f2770f);
        } catch (Exception e) {
            f2764h.m2679e(e);
        }
        return jSONObject;
    }

    void m2616c(String str) {
        this.f2768d = str;
    }

    void m2617d(String str) {
        this.f2765a = str;
    }

    void m2618e(String str) {
        this.f2766b = str;
    }

    public String getImei() {
        return this.f2765a;
    }

    public String getMac() {
        return this.f2766b;
    }

    public String getMid() {
        return this.f2768d;
    }

    public int getUserType() {
        return this.f2769e;
    }

    public String toString() {
        return m2615c().toString();
    }
}

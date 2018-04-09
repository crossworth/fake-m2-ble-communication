package com.tencent.connect.auth;

/* compiled from: ProGuard */
public class QQToken {
    public static final int AUTH_QQ = 2;
    public static final int AUTH_QZONE = 3;
    public static final int AUTH_WEB = 1;
    private String f2379a;
    private String f2380b;
    private String f2381c;
    private int f2382d = 1;
    private long f2383e = -1;

    public QQToken(String str) {
        this.f2379a = str;
    }

    public boolean isSessionValid() {
        return this.f2380b != null && System.currentTimeMillis() < this.f2383e;
    }

    public String getAppId() {
        return this.f2379a;
    }

    public void setAppId(String str) {
        this.f2379a = str;
    }

    public String getAccessToken() {
        return this.f2380b;
    }

    public void setAccessToken(String str, String str2) throws NumberFormatException {
        this.f2380b = str;
        this.f2383e = 0;
        if (str2 != null) {
            this.f2383e = System.currentTimeMillis() + (Long.parseLong(str2) * 1000);
        }
    }

    public String getOpenId() {
        return this.f2381c;
    }

    public void setOpenId(String str) {
        this.f2381c = str;
    }

    public int getAuthSource() {
        return this.f2382d;
    }

    public void setAuthSource(int i) {
        this.f2382d = i;
    }

    public long getExpireTimeInSecond() {
        return this.f2383e;
    }
}

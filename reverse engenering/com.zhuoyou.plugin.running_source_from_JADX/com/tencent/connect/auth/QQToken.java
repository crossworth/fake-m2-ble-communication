package com.tencent.connect.auth;

/* compiled from: ProGuard */
public class QQToken {
    public static final int AUTH_QQ = 2;
    public static final int AUTH_QZONE = 3;
    public static final int AUTH_WEB = 1;
    private String f3585a;
    private String f3586b;
    private String f3587c;
    private int f3588d = 1;
    private long f3589e = -1;

    public QQToken(String str) {
        this.f3585a = str;
    }

    public boolean isSessionValid() {
        return this.f3586b != null && System.currentTimeMillis() < this.f3589e;
    }

    public String getAppId() {
        return this.f3585a;
    }

    public void setAppId(String str) {
        this.f3585a = str;
    }

    public String getAccessToken() {
        return this.f3586b;
    }

    public void setAccessToken(String str, String str2) throws NumberFormatException {
        this.f3586b = str;
        this.f3589e = 0;
        if (str2 != null) {
            this.f3589e = System.currentTimeMillis() + (Long.parseLong(str2) * 1000);
        }
    }

    public String getOpenId() {
        return this.f3587c;
    }

    public void setOpenId(String str) {
        this.f3587c = str;
    }

    public int getAuthSource() {
        return this.f3588d;
    }

    public void setAuthSource(int i) {
        this.f3588d = i;
    }

    public long getExpireTimeInSecond() {
        return this.f3589e;
    }
}

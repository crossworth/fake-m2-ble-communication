package com.umeng.socialize;

public class SocializeException extends RuntimeException {
    private static final long f3229b = 1;
    protected int f3230a = 5000;
    private String f3231c = "";

    public int getErrorCode() {
        return this.f3230a;
    }

    public SocializeException(int i, String str) {
        super(str);
        this.f3230a = i;
        this.f3231c = str;
    }

    public SocializeException(String str, Throwable th) {
        super(str, th);
        this.f3231c = str;
    }

    public SocializeException(String str) {
        super(str);
        this.f3231c = str;
    }

    public String getMessage() {
        return this.f3231c;
    }
}

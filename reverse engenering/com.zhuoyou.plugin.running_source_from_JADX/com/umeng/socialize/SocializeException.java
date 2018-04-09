package com.umeng.socialize;

public class SocializeException extends RuntimeException {
    private static final long f4885b = 1;
    protected int f4886a = 5000;
    private String f4887c = "";

    public int getErrorCode() {
        return this.f4886a;
    }

    public SocializeException(int i, String str) {
        super(str);
        this.f4886a = i;
        this.f4887c = str;
    }

    public SocializeException(String str, Throwable th) {
        super(str, th);
        this.f4887c = str;
    }

    public SocializeException(String str) {
        super(str);
        this.f4887c = str;
    }

    public String getMessage() {
        return this.f4887c;
    }
}

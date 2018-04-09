package com.tencent.mm.sdk.diffdev.p034a;

import twitter4j.HttpResponseCode;

public enum C1262g {
    UUID_EXPIRED(402),
    UUID_CANCELED(HttpResponseCode.FORBIDDEN),
    UUID_SCANED(404),
    UUID_CONFIRM(405),
    UUID_KEEP_CONNECT(408),
    UUID_ERROR(500);
    
    private int code;

    private C1262g(int i) {
        this.code = i;
    }

    public final int getCode() {
        return this.code;
    }

    public final String toString() {
        return "UUIDStatusCode:" + this.code;
    }
}

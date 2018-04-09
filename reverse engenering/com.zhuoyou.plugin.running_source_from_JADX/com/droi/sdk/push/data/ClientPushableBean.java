package com.droi.sdk.push.data;

import java.io.Serializable;

public class ClientPushableBean implements Serializable {
    private boolean f3263a;

    public ClientPushableBean(boolean z) {
        this.f3263a = z;
    }

    public boolean isPushable() {
        return this.f3263a;
    }
}

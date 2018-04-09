package com.tencent.stat;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.protocol.HttpContext;

class C2007e extends DefaultConnectionKeepAliveStrategy {
    final /* synthetic */ C0843d f5435a;

    C2007e(C0843d c0843d) {
        this.f5435a = c0843d;
    }

    public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
        long keepAliveDuration = super.getKeepAliveDuration(httpResponse, httpContext);
        return keepAliveDuration == -1 ? 20000 : keepAliveDuration;
    }
}

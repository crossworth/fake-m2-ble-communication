package com.tencent.wxop.stat;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.protocol.HttpContext;

class C1455j extends DefaultConnectionKeepAliveStrategy {
    final /* synthetic */ C1454i f4828a;

    C1455j(C1454i c1454i) {
        this.f4828a = c1454i;
    }

    public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
        long keepAliveDuration = super.getKeepAliveDuration(httpResponse, httpContext);
        return keepAliveDuration == -1 ? StatisticConfig.MIN_UPLOAD_INTERVAL : keepAliveDuration;
    }
}

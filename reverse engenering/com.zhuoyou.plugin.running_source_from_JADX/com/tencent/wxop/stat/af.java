package com.tencent.wxop.stat;

import android.content.Context;
import java.util.Map;

final class af implements Runnable {
    final /* synthetic */ Context f4655a;
    final /* synthetic */ Map f4656b;
    final /* synthetic */ StatSpecifyReportedInfo f4657c;

    af(Context context, Map map, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.f4655a = context;
        this.f4656b = map;
        this.f4657c = statSpecifyReportedInfo;
    }

    public final void run() {
        try {
            new Thread(new ap(this.f4655a, this.f4656b, this.f4657c), "NetworkMonitorTask").start();
        } catch (Throwable th) {
            StatServiceImpl.f4581q.m4375e(th);
            StatServiceImpl.m4244a(this.f4655a, th);
        }
    }
}

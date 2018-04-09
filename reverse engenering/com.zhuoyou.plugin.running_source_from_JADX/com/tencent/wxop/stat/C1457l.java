package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.common.C1442k;

final class C1457l implements Runnable {
    final /* synthetic */ Context f4832a;

    C1457l(Context context) {
        this.f4832a = context;
    }

    public final void run() {
        C1428a.m4298a(StatServiceImpl.f4584t).m4313h();
        C1442k.m4405a(this.f4832a, true);
        au.m4332a(this.f4832a);
        C1454i.m4486b(this.f4832a);
        StatServiceImpl.f4582r = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new ao());
        if (StatConfig.getStatSendStrategy() == StatReportStrategy.APP_LAUNCH) {
            StatServiceImpl.commitEvents(this.f4832a, -1);
        }
        if (StatConfig.isDebugEnable()) {
            StatServiceImpl.f4581q.m4373d("Init MTA StatService success.");
        }
    }
}

package com.tencent.wxop.stat;

import android.content.Context;

final class C1468w implements Runnable {
    final /* synthetic */ String f4854a;
    final /* synthetic */ Context f4855b;
    final /* synthetic */ StatSpecifyReportedInfo f4856c;

    C1468w(String str, Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.f4854a = str;
        this.f4855b = context;
        this.f4856c = statSpecifyReportedInfo;
    }

    public final void run() {
        try {
            synchronized (StatServiceImpl.f4579o) {
                if (StatServiceImpl.f4579o.size() >= StatConfig.getMaxParallelTimmingEvents()) {
                    StatServiceImpl.f4581q.error("The number of page events exceeds the maximum value " + Integer.toString(StatConfig.getMaxParallelTimmingEvents()));
                    return;
                }
                StatServiceImpl.f4577m = this.f4854a;
                if (StatServiceImpl.f4579o.containsKey(StatServiceImpl.f4577m)) {
                    StatServiceImpl.f4581q.m4374e("Duplicate PageID : " + StatServiceImpl.f4577m + ", onResume() repeated?");
                    return;
                }
                StatServiceImpl.f4579o.put(StatServiceImpl.f4577m, Long.valueOf(System.currentTimeMillis()));
                StatServiceImpl.m4238a(this.f4855b, true, this.f4856c);
            }
        } catch (Throwable th) {
            StatServiceImpl.f4581q.m4375e(th);
            StatServiceImpl.m4244a(this.f4855b, th);
        }
    }
}

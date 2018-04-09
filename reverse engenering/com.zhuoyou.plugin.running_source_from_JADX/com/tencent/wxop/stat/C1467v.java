package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p040a.C1419c;

final class C1467v implements Runnable {
    final /* synthetic */ String f4851a;
    final /* synthetic */ C1419c f4852b;
    final /* synthetic */ Context f4853c;

    C1467v(String str, C1419c c1419c, Context context) {
        this.f4851a = str;
        this.f4852b = c1419c;
        this.f4853c = context;
    }

    public final void run() {
        try {
            if (StatServiceImpl.m4246a(this.f4851a)) {
                StatServiceImpl.f4581q.error((Object) "The event_id of StatService.trackCustomBeginEvent() can not be null or empty.");
                return;
            }
            if (StatConfig.isDebugEnable()) {
                StatServiceImpl.f4581q.m4376i("add begin key:" + this.f4852b.toString());
            }
            if (StatServiceImpl.f4569e.containsKey(this.f4852b)) {
                StatServiceImpl.f4581q.error("Duplicate CustomEvent key: " + this.f4852b.toString() + ", trackCustomBeginEvent() repeated?");
            } else if (StatServiceImpl.f4569e.size() <= StatConfig.getMaxParallelTimmingEvents()) {
                StatServiceImpl.f4569e.put(this.f4852b, Long.valueOf(System.currentTimeMillis()));
            } else {
                StatServiceImpl.f4581q.error("The number of timedEvent exceeds the maximum value " + Integer.toString(StatConfig.getMaxParallelTimmingEvents()));
            }
        } catch (Throwable th) {
            StatServiceImpl.f4581q.m4375e(th);
            StatServiceImpl.m4244a(this.f4853c, th);
        }
    }
}

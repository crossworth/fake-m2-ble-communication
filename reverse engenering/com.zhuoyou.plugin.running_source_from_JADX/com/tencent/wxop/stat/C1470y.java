package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p040a.C1419c;

final class C1470y implements Runnable {
    final /* synthetic */ String f4861a;
    final /* synthetic */ C1419c f4862b;
    final /* synthetic */ Context f4863c;

    C1470y(String str, C1419c c1419c, Context context) {
        this.f4861a = str;
        this.f4862b = c1419c;
        this.f4863c = context;
    }

    public final void run() {
        try {
            if (StatServiceImpl.m4246a(this.f4861a)) {
                StatServiceImpl.f4581q.error((Object) "The event_id of StatService.trackCustomBeginEvent() can not be null or empty.");
                return;
            }
            if (StatConfig.isDebugEnable()) {
                StatServiceImpl.f4581q.m4376i("add begin key:" + this.f4862b);
            }
            if (StatServiceImpl.f4569e.containsKey(this.f4862b)) {
                StatServiceImpl.f4581q.warn("Duplicate CustomEvent key: " + this.f4862b.toString() + ", trackCustomBeginKVEvent() repeated?");
            } else if (StatServiceImpl.f4569e.size() <= StatConfig.getMaxParallelTimmingEvents()) {
                StatServiceImpl.f4569e.put(this.f4862b, Long.valueOf(System.currentTimeMillis()));
            } else {
                StatServiceImpl.f4581q.error("The number of timedEvent exceeds the maximum value " + Integer.toString(StatConfig.getMaxParallelTimmingEvents()));
            }
        } catch (Throwable th) {
            StatServiceImpl.f4581q.m4375e(th);
            StatServiceImpl.m4244a(this.f4863c, th);
        }
    }
}

package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.common.C1442k;

final class C1459n implements Runnable {
    final /* synthetic */ Context f4835a;

    C1459n(Context context) {
        this.f4835a = context;
    }

    public final void run() {
        if (this.f4835a == null) {
            StatServiceImpl.f4581q.error((Object) "The Context of StatService.onStop() can not be null!");
            return;
        }
        StatServiceImpl.flushDataToDB(this.f4835a);
        if (!StatServiceImpl.m4245a()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (C1442k.m4401B(this.f4835a)) {
                if (StatConfig.isDebugEnable()) {
                    StatServiceImpl.f4581q.m4376i("onStop isBackgroundRunning flushDataToDB");
                }
                StatServiceImpl.commitEvents(this.f4835a, -1);
            }
        }
    }
}

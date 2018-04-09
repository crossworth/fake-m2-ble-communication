package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p040a.C1420d;
import com.tencent.wxop.stat.p040a.C1424i;

final class C1462q implements Runnable {
    final /* synthetic */ Context f4840a;
    final /* synthetic */ Throwable f4841b;

    C1462q(Context context, Throwable th) {
        this.f4840a = context;
        this.f4841b = th;
    }

    public final void run() {
        try {
            if (StatConfig.isEnableStatService()) {
                new aq(new C1420d(this.f4840a, StatServiceImpl.m4238a(this.f4840a, false, null), 99, this.f4841b, C1424i.f4627a)).m4323a();
            }
        } catch (Throwable th) {
            StatServiceImpl.f4581q.m4374e("reportSdkSelfException error: " + th);
        }
    }
}

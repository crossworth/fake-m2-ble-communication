package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p040a.C1420d;

final class C1463r implements Runnable {
    final /* synthetic */ Throwable f4842a;
    final /* synthetic */ Context f4843b;
    final /* synthetic */ StatSpecifyReportedInfo f4844c;

    C1463r(Throwable th, Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.f4842a = th;
        this.f4843b = context;
        this.f4844c = statSpecifyReportedInfo;
    }

    public final void run() {
        if (this.f4842a == null) {
            StatServiceImpl.f4581q.error((Object) "The Throwable error message of StatService.reportException() can not be null!");
        } else {
            new aq(new C1420d(this.f4843b, StatServiceImpl.m4238a(this.f4843b, false, this.f4844c), 1, this.f4842a, this.f4844c)).m4323a();
        }
    }
}

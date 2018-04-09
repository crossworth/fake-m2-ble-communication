package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p040a.C1420d;

final class C1461p implements Runnable {
    final /* synthetic */ String f4837a;
    final /* synthetic */ Context f4838b;
    final /* synthetic */ StatSpecifyReportedInfo f4839c;

    C1461p(String str, Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.f4837a = str;
        this.f4838b = context;
        this.f4839c = statSpecifyReportedInfo;
    }

    public final void run() {
        try {
            if (StatServiceImpl.m4246a(this.f4837a)) {
                StatServiceImpl.f4581q.error((Object) "Error message in StatService.reportError() is empty.");
            } else {
                new aq(new C1420d(this.f4838b, StatServiceImpl.m4238a(this.f4838b, false, this.f4839c), this.f4837a, 0, StatConfig.getMaxReportEventLength(), null, this.f4839c)).m4323a();
            }
        } catch (Throwable th) {
            StatServiceImpl.f4581q.m4375e(th);
            StatServiceImpl.m4244a(this.f4838b, th);
        }
    }
}

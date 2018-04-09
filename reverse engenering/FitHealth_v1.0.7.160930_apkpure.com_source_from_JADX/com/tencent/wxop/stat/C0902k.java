package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p022a.C0873d;
import com.tencent.wxop.stat.p022a.C1759h;
import com.umeng.socialize.common.SocializeConstants;

final class C0902k implements Runnable {
    final /* synthetic */ String f3072b;
    final /* synthetic */ C0897f bM;
    final /* synthetic */ Context f3073e;

    C0902k(Context context, String str, C0897f c0897f) {
        this.f3073e = context;
        this.f3072b = str;
        this.bM = c0897f;
    }

    public final void run() {
        try {
            Long l;
            C0896e.m2999p(this.f3073e);
            synchronized (C0896e.aT) {
                l = (Long) C0896e.aT.remove(this.f3072b);
            }
            if (l != null) {
                Long valueOf = Long.valueOf((System.currentTimeMillis() - l.longValue()) / 1000);
                if (valueOf.longValue() <= 0) {
                    valueOf = Long.valueOf(1);
                }
                String O = C0896e.aS;
                if (O != null && O.equals(this.f3072b)) {
                    O = SocializeConstants.OP_DIVIDER_MINUS;
                }
                C0873d c1759h = new C1759h(this.f3073e, O, this.f3072b, C0896e.m2981a(this.f3073e, false, this.bM), valueOf, this.bM);
                if (!this.f3072b.equals(C0896e.aR)) {
                    C0896e.aV.warn("Invalid invocation since previous onResume on diff page.");
                }
                new C0907p(c1759h).ah();
                C0896e.aS = this.f3072b;
                return;
            }
            C0896e.aV.m2854d("Starttime for PageID:" + this.f3072b + " not found, lost onResume()?");
        } catch (Throwable th) {
            C0896e.aV.m2852b(th);
            C0896e.m2985a(this.f3073e, th);
        }
    }
}

package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.common.C1442k;

final class C1458m implements Runnable {
    final /* synthetic */ Context f4833a;
    final /* synthetic */ StatSpecifyReportedInfo f4834b;

    C1458m(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.f4833a = context;
        this.f4834b = statSpecifyReportedInfo;
    }

    public final void run() {
        if (this.f4833a == null) {
            StatServiceImpl.f4581q.error((Object) "The Context of StatService.onPause() can not be null!");
        } else {
            StatServiceImpl.trackEndPage(this.f4833a, C1442k.m4430h(this.f4833a), this.f4834b);
        }
    }
}

package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.common.C1442k;

final class aj implements Runnable {
    final /* synthetic */ Context f4664a;
    final /* synthetic */ StatSpecifyReportedInfo f4665b;

    aj(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.f4664a = context;
        this.f4665b = statSpecifyReportedInfo;
    }

    public final void run() {
        if (this.f4664a == null) {
            StatServiceImpl.f4581q.error((Object) "The Context of StatService.onResume() can not be null!");
        } else {
            StatServiceImpl.trackBeginPage(this.f4664a, C1442k.m4430h(this.f4664a), this.f4665b);
        }
    }
}

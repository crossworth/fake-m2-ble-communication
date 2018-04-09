package com.tencent.wxop.stat;

import android.content.Context;

final class ak implements Runnable {
    final /* synthetic */ String f4666a;
    final /* synthetic */ Context f4667b;
    final /* synthetic */ StatSpecifyReportedInfo f4668c;

    ak(String str, Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.f4666a = str;
        this.f4667b = context;
        this.f4668c = statSpecifyReportedInfo;
    }

    public final void run() {
        if (this.f4666a == null || this.f4666a.trim().length() == 0) {
            StatServiceImpl.f4581q.m4378w("qq num is null or empty.");
            return;
        }
        StatConfig.f4539f = this.f4666a;
        StatServiceImpl.m4249b(this.f4667b, new StatAccount(this.f4666a), this.f4668c);
    }
}

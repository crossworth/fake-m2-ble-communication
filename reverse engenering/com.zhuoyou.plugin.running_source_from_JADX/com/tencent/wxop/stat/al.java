package com.tencent.wxop.stat;

import android.content.Context;

final class al implements Runnable {
    final /* synthetic */ StatAccount f4669a;
    final /* synthetic */ Context f4670b;
    final /* synthetic */ StatSpecifyReportedInfo f4671c;

    al(StatAccount statAccount, Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.f4669a = statAccount;
        this.f4670b = context;
        this.f4671c = statSpecifyReportedInfo;
    }

    public final void run() {
        if (this.f4669a == null || this.f4669a.getAccount().trim().length() == 0) {
            StatServiceImpl.f4581q.m4378w("account is null or empty.");
            return;
        }
        StatConfig.setQQ(this.f4670b, this.f4669a.getAccount());
        StatServiceImpl.m4249b(this.f4670b, this.f4669a, this.f4671c);
    }
}

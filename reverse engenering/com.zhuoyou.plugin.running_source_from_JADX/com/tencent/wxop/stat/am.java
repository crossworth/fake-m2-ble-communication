package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p040a.C1422g;

final class am implements Runnable {
    final /* synthetic */ StatGameUser f4672a;
    final /* synthetic */ Context f4673b;
    final /* synthetic */ StatSpecifyReportedInfo f4674c;

    am(StatGameUser statGameUser, Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.f4672a = statGameUser;
        this.f4673b = context;
        this.f4674c = statSpecifyReportedInfo;
    }

    public final void run() {
        if (this.f4672a == null) {
            StatServiceImpl.f4581q.error((Object) "The gameUser of StatService.reportGameUser() can not be null!");
        } else if (this.f4672a.getAccount() == null || this.f4672a.getAccount().length() == 0) {
            StatServiceImpl.f4581q.error((Object) "The account of gameUser on StatService.reportGameUser() can not be null or empty!");
        } else {
            try {
                new aq(new C1422g(this.f4673b, StatServiceImpl.m4238a(this.f4673b, false, this.f4674c), this.f4672a, this.f4674c)).m4323a();
            } catch (Throwable th) {
                StatServiceImpl.f4581q.m4375e(th);
                StatServiceImpl.m4244a(this.f4673b, th);
            }
        }
    }
}

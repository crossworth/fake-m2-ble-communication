package com.tencent.wxop.stat;

import android.content.Context;

final class C1460o implements Runnable {
    final /* synthetic */ Context f4836a;

    C1460o(Context context) {
        this.f4836a = context;
    }

    public final void run() {
        StatServiceImpl.flushDataToDB(this.f4836a);
    }
}

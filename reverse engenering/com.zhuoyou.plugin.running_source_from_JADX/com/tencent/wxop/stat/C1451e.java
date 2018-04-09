package com.tencent.wxop.stat;

import com.tencent.wxop.stat.common.C1442k;
import java.util.TimerTask;

class C1451e extends TimerTask {
    final /* synthetic */ C1450d f4816a;

    C1451e(C1450d c1450d) {
        this.f4816a = c1450d;
    }

    public void run() {
        if (StatConfig.isDebugEnable()) {
            C1442k.m4416b().m4376i("TimerTask run");
        }
        StatServiceImpl.m4257e(this.f4816a.f4815c);
        cancel();
        this.f4816a.m4475a();
    }
}

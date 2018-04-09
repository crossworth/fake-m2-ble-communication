package com.tencent.mm.sdk.diffdev.p034a;

import com.tencent.mm.sdk.diffdev.OAuthListener;
import java.util.ArrayList;
import java.util.List;

final class C1256c implements Runnable {
    final /* synthetic */ C1255b ah;

    C1256c(C1255b c1255b) {
        this.ah = c1255b;
    }

    public final void run() {
        List<OAuthListener> arrayList = new ArrayList();
        arrayList.addAll(this.ah.ag.ad);
        for (OAuthListener onQrcodeScanned : arrayList) {
            onQrcodeScanned.onQrcodeScanned();
        }
    }
}

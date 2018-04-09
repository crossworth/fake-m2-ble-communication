package com.droi.sdk.push;

import android.content.Context;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;

final class C0982b implements DroiCallback {
    final /* synthetic */ Context f3257a;

    C0982b(Context context) {
        this.f3257a = context;
    }

    public void m3022a(String str, DroiError droiError) {
        DroiPush.m2879b(this.f3257a, str, droiError);
    }

    public /* synthetic */ void result(Object obj, DroiError droiError) {
        m3022a((String) obj, droiError);
    }
}

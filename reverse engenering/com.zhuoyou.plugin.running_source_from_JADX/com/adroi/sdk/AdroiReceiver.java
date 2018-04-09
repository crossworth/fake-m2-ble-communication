package com.adroi.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.adroi.sdk.p000a.C0277b;
import com.adroi.sdk.p000a.C0278c;

public class AdroiReceiver extends BroadcastReceiver {
    private static Class<?> f16a;

    public void onReceive(Context context, Intent intent) {
        C0278c.m30a("AdroiReceiver:" + intent.getAction());
        try {
            f16a = C0277b.m25a(context, "com.adroi.sdk.remote.DownLoadReceiver");
            f16a.getDeclaredMethod("onReceive", new Class[]{Context.class, Intent.class}).invoke(null, new Object[]{context, intent});
        } catch (Throwable e) {
            C0278c.m32a(e);
        }
    }
}

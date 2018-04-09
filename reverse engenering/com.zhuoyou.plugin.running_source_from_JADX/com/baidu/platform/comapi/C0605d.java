package com.baidu.platform.comapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.baidu.platform.comapi.util.C0668c;
import com.baidu.platform.comapi.util.C0671f;

public class C0605d extends BroadcastReceiver {
    public static final String f1904a = C0605d.class.getSimpleName();

    public void m1867a(Context context) {
        String c = C0668c.m2153c(context);
        if (!C0671f.m2174d().equals(c)) {
            C0671f.m2167a(c);
        }
    }

    public void onReceive(Context context, Intent intent) {
        m1867a(context);
        C0668c.m2150a(context);
    }
}

package com.tencent.map.p028b;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/* compiled from: ProGuard */
public final class C1227l {
    private static C1227l f3923b;
    private Context f3924a;

    public static C1227l m3644a() {
        if (f3923b == null) {
            f3923b = new C1227l();
        }
        return f3923b;
    }

    private C1227l() {
    }

    public final void m3648a(Context context) {
        if (this.f3924a == null) {
            this.f3924a = context.getApplicationContext();
        }
    }

    public static Context m3645b() {
        return C1227l.m3644a().f3924a;
    }

    public static boolean m3646c() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) C1227l.m3644a().f3924a.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.getType() == 1) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean m3647d() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) C1227l.m3644a().f3924a.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                return activeNetworkInfo.isAvailable();
            }
        } catch (Exception e) {
        }
        return false;
    }
}

package com.tencent.map.p013b;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/* compiled from: ProGuard */
public final class C0754t {
    private static C0754t f2628b;
    private Context f2629a;

    public static C0754t m2498a() {
        if (f2628b == null) {
            f2628b = new C0754t();
        }
        return f2628b;
    }

    private C0754t() {
    }

    public final void m2502a(Context context) {
        if (this.f2629a == null) {
            this.f2629a = context.getApplicationContext();
        }
    }

    public static Context m2499b() {
        return C0754t.m2498a().f2629a;
    }

    public static boolean m2500c() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) C0754t.m2498a().f2629a.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.getType() == 1) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean m2501d() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) C0754t.m2498a().f2629a.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                return activeNetworkInfo.isAvailable();
            }
        } catch (Exception e) {
        }
        return false;
    }
}

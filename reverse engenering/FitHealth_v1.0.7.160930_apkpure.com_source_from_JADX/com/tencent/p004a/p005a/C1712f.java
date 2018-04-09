package com.tencent.p004a.p005a;

import android.util.Log;

/* compiled from: ProGuard */
public final class C1712f extends C0673g {
    public static final C1712f f4507a = new C1712f();

    protected void mo2080a(int i, Thread thread, long j, String str, String str2, Throwable th) {
        switch (i) {
            case 1:
                Log.v(str, str2, th);
                return;
            case 2:
                Log.d(str, str2, th);
                return;
            case 4:
                Log.i(str, str2, th);
                return;
            case 8:
                Log.w(str, str2, th);
                return;
            case 16:
                Log.e(str, str2, th);
                return;
            case 32:
                Log.e(str, str2, th);
                return;
            default:
                return;
        }
    }
}

package com.tencent.mm.sdk.p032b;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.tencent.mm.sdk.p032b.C1242b.C1241a;

final class C1243c implements C1241a {
    private Handler handler = new Handler(Looper.getMainLooper());

    C1243c() {
    }

    public final void mo2159f(String str, String str2) {
        if (C1242b.level <= 2) {
            Log.i(str, str2);
        }
    }

    public final void mo2160g(String str, String str2) {
        if (C1242b.level <= 1) {
            Log.d(str, str2);
        }
    }

    public final int getLogLevel() {
        return C1242b.level;
    }

    public final void mo2162h(String str, String str2) {
        if (C1242b.level <= 3) {
            Log.w(str, str2);
        }
    }

    public final void mo2163i(String str, String str2) {
        if (C1242b.level <= 4) {
            Log.e(str, str2);
        }
    }
}

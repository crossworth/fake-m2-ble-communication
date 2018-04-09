package com.tencent.mm.sdk.p017b;

import android.util.Log;
import com.tencent.mm.sdk.p017b.C0765a.C0764a;

final class C1723b implements C0764a {
    C1723b() {
    }

    public final void mo2103e(String str, String str2) {
        if (C0765a.level <= 2) {
            Log.i(str, str2);
        }
    }

    public final void mo2104f(String str, String str2) {
        if (C0765a.level <= 1) {
            Log.d(str, str2);
        }
    }

    public final void mo2105g(String str, String str2) {
        if (C0765a.level <= 3) {
            Log.w(str, str2);
        }
    }

    public final int mo2106h() {
        return C0765a.level;
    }

    public final void mo2107h(String str, String str2) {
        if (C0765a.level <= 4) {
            Log.e(str, str2);
        }
    }
}

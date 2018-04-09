package com.tencent.p004a.p008b;

import android.content.Context;
import java.io.File;

/* compiled from: ProGuard */
public final class C0678a {
    private static Context f2348a;

    public static final Context m2290a() {
        if (f2348a == null) {
            return null;
        }
        return f2348a;
    }

    public static final void m2291a(Context context) {
        f2348a = context;
    }

    public static final String m2292b() {
        return C0678a.m2290a().getPackageName();
    }

    public static final File m2293c() {
        return C0678a.m2290a().getFilesDir();
    }
}

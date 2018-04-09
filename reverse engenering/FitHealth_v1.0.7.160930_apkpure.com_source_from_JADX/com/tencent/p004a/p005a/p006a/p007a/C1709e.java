package com.tencent.p004a.p005a.p006a.p007a;

import android.content.Context;
import android.provider.Settings.System;
import android.util.Log;

public final class C1709e extends C0667f {
    public C1709e(Context context) {
        super(context);
    }

    protected final boolean mo2077a() {
        return C0669h.m2235a(this.e, "android.permission.WRITE_SETTINGS");
    }

    protected final String mo2078b() {
        String string;
        synchronized (this) {
            Log.i("MID", "read mid from Settings.System");
            string = System.getString(this.e.getContentResolver(), C0669h.m2240f("4kU71lN96TJUomD1vOU9lgj9Tw=="));
        }
        return string;
    }

    protected final void mo2079b(String str) {
        synchronized (this) {
            Log.i("MID", "write mid to Settings.System");
            System.putString(this.e.getContentResolver(), C0669h.m2240f("4kU71lN96TJUomD1vOU9lgj9Tw=="), str);
        }
    }
}

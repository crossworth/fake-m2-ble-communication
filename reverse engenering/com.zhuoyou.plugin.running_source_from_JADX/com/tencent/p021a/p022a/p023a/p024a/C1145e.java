package com.tencent.p021a.p022a.p023a.p024a;

import android.content.Context;
import android.provider.Settings.System;
import android.util.Log;

public final class C1145e extends C1141f {
    public C1145e(Context context) {
        super(context);
    }

    protected final void mo2138a(String str) {
        synchronized (this) {
            Log.i("MID", "write mid to Settings.System");
            System.putString(this.a.getContentResolver(), C1147h.m3342f("4kU71lN96TJUomD1vOU9lgj9Tw=="), str);
        }
    }

    protected final boolean mo2139a() {
        return C1147h.m3338a(this.a, "android.permission.WRITE_SETTINGS");
    }

    protected final String mo2140b() {
        String string;
        synchronized (this) {
            Log.i("MID", "read mid from Settings.System");
            string = System.getString(this.a.getContentResolver(), C1147h.m3342f("4kU71lN96TJUomD1vOU9lgj9Tw=="));
        }
        return string;
    }
}

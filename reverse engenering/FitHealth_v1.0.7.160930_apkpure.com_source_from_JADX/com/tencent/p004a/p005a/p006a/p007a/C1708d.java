package com.tencent.p004a.p005a.p006a.p007a;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

final class C1708d extends C0667f {
    public C1708d(Context context) {
        super(context);
    }

    protected final boolean mo2077a() {
        return true;
    }

    protected final String mo2078b() {
        String string;
        synchronized (this) {
            Log.i("MID", "read mid from sharedPreferences");
            string = PreferenceManager.getDefaultSharedPreferences(this.e).getString(C0669h.m2240f("4kU71lN96TJUomD1vOU9lgj9Tw=="), null);
        }
        return string;
    }

    protected final void mo2079b(String str) {
        synchronized (this) {
            Log.i("MID", "write mid to sharedPreferences");
            Editor edit = PreferenceManager.getDefaultSharedPreferences(this.e).edit();
            edit.putString(C0669h.m2240f("4kU71lN96TJUomD1vOU9lgj9Tw=="), str);
            edit.commit();
        }
    }
}

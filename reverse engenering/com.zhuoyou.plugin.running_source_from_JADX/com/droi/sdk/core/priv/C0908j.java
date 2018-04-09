package com.droi.sdk.core.priv;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

public class C0908j {
    public static final String f2941a = "key_unbind";
    private static C0908j f2942b;
    private SharedPreferences f2943c;

    private C0908j(Context context) {
        this.f2943c = context.getSharedPreferences("pref_settings.dat", 0);
    }

    public static C0908j m2688a() {
        if (f2942b != null) {
            return f2942b;
        }
        throw new RuntimeException("Need to call initialize first.");
    }

    public static synchronized C0908j m2689a(Context context) {
        C0908j c0908j;
        synchronized (C0908j.class) {
            if (f2942b == null) {
                f2942b = new C0908j(context);
            }
            c0908j = f2942b;
        }
        return c0908j;
    }

    public void m2690a(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        this.f2943c.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public void m2691a(String str, boolean z) {
        Editor edit = this.f2943c.edit();
        edit.putBoolean(str, z);
        edit.apply();
    }

    public boolean m2692a(String str) {
        return this.f2943c.getBoolean(str, false);
    }

    public void m2693b(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        this.f2943c.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }
}

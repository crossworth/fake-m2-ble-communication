package com.droi.sdk.analytics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

class C0779o {
    private SharedPreferences f2348a = null;
    private Editor f2349b = null;

    @SuppressLint({"CommitPrefEdits"})
    public C0779o(Context context) {
        this.f2348a = context.getSharedPreferences("Droi_SharedPref", 0);
        this.f2349b = this.f2348a.edit();
    }

    public void m2404a(String str, long j) {
        this.f2349b.putLong(str, j);
        this.f2349b.apply();
    }

    public void m2405a(String str, String str2) {
        this.f2349b.putString(str, str2);
        this.f2349b.apply();
    }

    public long m2406b(String str, long j) {
        return this.f2348a.getLong(str, j);
    }

    public String m2407b(String str, String str2) {
        return this.f2348a.getString(str, str2);
    }
}

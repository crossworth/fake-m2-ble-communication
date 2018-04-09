package com.tencent.p004a.p008b;

import android.os.Environment;

/* compiled from: ProGuard */
public class C0681c {
    public static boolean m2294a() {
        String externalStorageState = Environment.getExternalStorageState();
        return "mounted".equals(externalStorageState) || "mounted_ro".equals(externalStorageState);
    }

    public static C0682d m2295b() {
        if (C0681c.m2294a()) {
            return C0682d.m2296b(Environment.getExternalStorageDirectory());
        }
        return null;
    }
}

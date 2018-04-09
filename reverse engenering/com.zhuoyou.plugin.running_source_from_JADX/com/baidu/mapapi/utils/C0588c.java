package com.baidu.mapapi.utils;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import com.baidu.p001a.p002a.p003a.C0296b.C0298a;

final class C0588c implements ServiceConnection {
    C0588c() {
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (C0586a.f1847v != null) {
            C0586a.f1847v.interrupt();
        }
        Log.d(C0586a.f1828c, "onServiceConnected " + componentName);
        try {
            if (C0586a.f1829d != null) {
                C0586a.f1829d = null;
            }
            C0586a.f1829d = C0298a.m57a(iBinder);
            C0586a.f1829d.mo1736a(new C0589d(this));
        } catch (Throwable e) {
            Log.d(C0586a.f1828c, "getComOpenClient ", e);
            if (C0586a.f1829d != null) {
                C0586a.f1829d = null;
            }
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        Log.d(C0586a.f1828c, "onServiceDisconnected " + componentName);
        if (C0586a.f1829d != null) {
            C0586a.f1829d = null;
            C0586a.f1846u = false;
        }
    }
}

package com.baidu.mapapi.utils;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.baidu.p001a.p002a.p003a.C0293a.C0295a;
import com.baidu.p001a.p002a.p003a.C0299c.C0301a;

class C0589d extends C0301a {
    final /* synthetic */ C0588c f1849a;

    C0589d(C0588c c0588c) {
        this.f1849a = c0588c;
    }

    public void mo1737a(IBinder iBinder) throws RemoteException {
        Log.d(C0586a.f1828c, "onClientReady");
        if (C0586a.f1830e != null) {
            C0586a.f1830e = null;
        }
        C0586a.f1830e = C0295a.m54a(iBinder);
        if (!C0586a.f1845t) {
            C0586a.m1787a(C0586a.f1826a);
        }
        C0586a.f1845t = true;
    }
}

package com.baidu.mapapi.utils;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.baidu.p001a.p002a.p003a.C0293a.C0295a;
import com.baidu.p001a.p002a.p003a.C0299c.C0301a;

final class C0587b extends C0301a {
    final /* synthetic */ int f1848a;

    C0587b(int i) {
        this.f1848a = i;
    }

    public void mo1737a(IBinder iBinder) throws RemoteException {
        Log.d(C0586a.f1828c, "onClientReady");
        if (C0586a.f1830e != null) {
            C0586a.f1830e = null;
        }
        C0586a.f1830e = C0295a.m54a(iBinder);
        C0586a.m1787a(this.f1848a);
        C0586a.f1845t = true;
    }
}

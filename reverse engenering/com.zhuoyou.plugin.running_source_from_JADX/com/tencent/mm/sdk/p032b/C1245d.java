package com.tencent.mm.sdk.p032b;

import android.os.Looper;
import com.tencent.mm.sdk.p032b.C1247e.C1244a;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public final class C1245d implements C1244a {
    private C1247e aJ;
    private ConcurrentHashMap<Runnable, WeakReference<C1249g>> aK;
    private int aL;
    private LinkedList<WeakReference<C1249g>> aM;

    public C1245d() {
        this.aK = new ConcurrentHashMap();
        this.aM = new LinkedList();
        this.aJ = new C1247e(this);
        if (this.aJ.getLooper().getThread().getName().equals("initThread")) {
            C1242b.m3666a("MicroMsg.MMHandler", "MMHandler can not init handler with initThread looper, stack %s", C1250h.m3683u());
        }
    }

    public C1245d(Looper looper) {
        this.aK = new ConcurrentHashMap();
        this.aM = new LinkedList();
        this.aJ = new C1247e(looper, this);
        if (looper.getThread().getName().equals("initThread")) {
            C1242b.m3666a("MicroMsg.MMHandler", "MMHandler can not init handler with initThread looper, stack %s", C1250h.m3683u());
        }
    }

    public final void mo2164a(Runnable runnable, C1249g c1249g) {
        this.aK.put(runnable, new WeakReference(c1249g));
    }

    public final void mo2165b(Runnable runnable, C1249g c1249g) {
        WeakReference weakReference = (WeakReference) this.aK.get(runnable);
        if (weakReference != null && weakReference.get() != null && weakReference.get() == c1249g) {
            this.aK.remove(runnable);
            if (this.aL > 0) {
                if (this.aM.size() == this.aL) {
                    this.aM.pop();
                }
                this.aM.add(weakReference);
            }
        }
    }

    public final boolean post(Runnable runnable) {
        return this.aJ.post(runnable);
    }

    public final String toString() {
        return "MMHandler(" + getClass().getName() + ")";
    }
}

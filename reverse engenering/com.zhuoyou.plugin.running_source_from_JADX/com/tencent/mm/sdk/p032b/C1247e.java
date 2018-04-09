package com.tencent.mm.sdk.p032b;

import android.os.Debug;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import com.tencent.mm.sdk.p032b.C1249g.C1246a;
import junit.framework.Assert;

final class C1247e extends Handler implements C1246a {
    private Looper aN = getLooper();
    private Callback aO;
    C1244a aP;

    public interface C1244a {
        void mo2164a(Runnable runnable, C1249g c1249g);

        void mo2165b(Runnable runnable, C1249g c1249g);
    }

    C1247e(Looper looper, C1244a c1244a) {
        super(looper);
        this.aP = c1244a;
    }

    C1247e(C1244a c1244a) {
        this.aP = c1244a;
    }

    public final void mo2166c(Runnable runnable, C1249g c1249g) {
        if (this.aP != null) {
            this.aP.mo2165b(runnable, c1249g);
        }
    }

    public final void dispatchMessage(Message message) {
        if (message.getCallback() == null && this.aO == null) {
            System.currentTimeMillis();
            Debug.threadCpuTimeNanos();
            handleMessage(message);
            if (this.aP != null) {
                this.aN.getThread();
                System.currentTimeMillis();
                Debug.threadCpuTimeNanos();
                return;
            }
            return;
        }
        super.dispatchMessage(message);
    }

    public final void handleMessage(Message message) {
    }

    public final boolean sendMessageAtTime(Message message, long j) {
        Assert.assertTrue("msg is null", message != null);
        Runnable callback = message.getCallback();
        if (callback == null) {
            return super.sendMessageAtTime(message, j);
        }
        long uptimeMillis = j - SystemClock.uptimeMillis();
        C1249g c1249g = new C1249g(this.aN.getThread(), message.getTarget() == null ? this : message.getTarget(), callback, message.obj, this);
        if (uptimeMillis > 0) {
            c1249g.aY = uptimeMillis;
        }
        Message obtain = Message.obtain(message.getTarget(), c1249g);
        obtain.what = message.what;
        obtain.arg1 = message.arg1;
        obtain.arg2 = message.arg2;
        obtain.obj = message.obj;
        obtain.replyTo = message.replyTo;
        obtain.setData(message.getData());
        message.recycle();
        if (this.aP != null) {
            this.aP.mo2164a(callback, c1249g);
        }
        boolean sendMessageAtTime = super.sendMessageAtTime(obtain, j);
        if (!(sendMessageAtTime || this.aP == null)) {
            this.aP.mo2165b(callback, c1249g);
        }
        return sendMessageAtTime;
    }

    public final String toString() {
        return "MMInnerHandler{listener = " + this.aP + "}";
    }
}

package com.baidu.vi;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

public class VMsg {
    private static final String f2249a = VMsg.class.getSimpleName();
    private static Handler f2250b;
    private static HandlerThread f2251c;

    static class C0682a extends Handler {
        public C0682a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            VMsg.OnUserCommand1(message.what, message.arg1, message.arg2, message.obj == null ? 0 : ((Long) message.obj).longValue());
        }
    }

    private static native void OnUserCommand1(int i, int i2, int i3, long j);

    public static void destroy() {
        f2251c.quit();
        f2251c = null;
        f2250b.removeCallbacksAndMessages(null);
        f2250b = null;
    }

    public static void init() {
        f2251c = new HandlerThread("VIMsgThread");
        f2251c.start();
        f2250b = new C0682a(f2251c.getLooper());
    }

    private static void postMessage(int i, int i2, int i3, long j) {
        if (f2250b != null) {
            Message.obtain(f2250b, i, i2, i3, Long.valueOf(j)).sendToTarget();
        }
    }
}

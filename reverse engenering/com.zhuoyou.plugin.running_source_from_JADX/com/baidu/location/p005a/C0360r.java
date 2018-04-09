package com.baidu.location.p005a;

import android.os.HandlerThread;

public class C0360r {
    private static HandlerThread f300a = null;

    public static synchronized HandlerThread m345a() {
        HandlerThread handlerThread;
        synchronized (C0360r.class) {
            if (f300a == null) {
                f300a = new HandlerThread("ServiceStartArguments", 10);
                f300a.start();
            }
            handlerThread = f300a;
        }
        return handlerThread;
    }
}

package com.baidu.location;

import android.os.HandlerThread;

class af {
    private static HandlerThread f2161a = null;

    af() {
    }

    static HandlerThread m2142a() {
        if (f2161a == null) {
            f2161a = new HandlerThread("ServiceStartArguments", 10);
            f2161a.start();
        }
        return f2161a;
    }
}

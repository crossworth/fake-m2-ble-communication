package com.baidu.mapapi;

import android.os.Handler;
import com.baidu.platform.comjni.engine.C0672a;

public class MessageCenter {
    public static void registMessage(int i, Handler handler) {
        C0672a.m2189a(i, handler);
    }

    public static void unregistMessage(int i, Handler handler) {
        C0672a.m2190b(i, handler);
    }
}

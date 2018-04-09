package com.baidu.platform.comjni.engine;

import android.content.Context;
import android.os.Bundle;

public class AppEngine {
    public static void InitClass() {
        JNIEngine.initClass(new Bundle(), 0);
    }

    public static boolean InitEngine(Context context, Bundle bundle) {
        return JNIEngine.InitEngine(context, bundle);
    }

    public static void SetProxyInfo(String str, int i) {
        JNIEngine.SetProxyInfo(str, i);
    }

    public static boolean StartSocketProc() {
        return JNIEngine.StartSocketProc();
    }

    public static boolean UnInitEngine() {
        return JNIEngine.UnInitEngine();
    }

    public static void despatchMessage(int i, int i2, int i3, long j) {
        C0672a.m2188a(i, i2, i3, j);
    }
}

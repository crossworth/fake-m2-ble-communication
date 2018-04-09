package com.baidu.mapapi;

import android.content.Context;
import com.baidu.platform.comapi.C0601a;

public class BMapManager {
    public static void destroy() {
        C0601a.m1853a().m1861d();
    }

    public static Context getContext() {
        return C0601a.m1853a().m1862e();
    }

    public static void init() {
        C0601a.m1853a().m1859b();
    }
}

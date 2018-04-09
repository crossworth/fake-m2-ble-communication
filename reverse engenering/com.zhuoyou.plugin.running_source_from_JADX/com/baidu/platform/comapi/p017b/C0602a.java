package com.baidu.platform.comapi.p017b;

import android.content.Context;

public class C0602a {
    private static int f1901a = 621133959;

    public static boolean m1863a(Context context) {
        return C0602a.m1865c(context);
    }

    private static int m1864b(Context context) {
        int i = 0;
        try {
            i = context.getPackageManager().getPackageInfo("com.baidu.BaiduMap", 64).signatures[0].hashCode();
        } catch (Exception e) {
        }
        return i;
    }

    private static boolean m1865c(Context context) {
        return C0602a.m1864b(context) == f1901a;
    }
}

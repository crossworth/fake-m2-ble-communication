package com.tencent.wxop.stat.common;

import java.io.File;

class C1446o {
    private static int f4810a = -1;

    public static boolean m4453a() {
        if (f4810a == 1) {
            return true;
        }
        if (f4810a == 0) {
            return false;
        }
        String[] strArr = new String[]{"/bin", "/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        int i = 0;
        while (i < 6) {
            try {
                if (new File(strArr[i] + "su").exists()) {
                    f4810a = 1;
                    return true;
                }
                i++;
            } catch (Exception e) {
            }
        }
        f4810a = 0;
        return false;
    }
}

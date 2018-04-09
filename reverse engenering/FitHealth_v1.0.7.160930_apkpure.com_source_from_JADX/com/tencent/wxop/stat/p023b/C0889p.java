package com.tencent.wxop.stat.p023b;

import java.io.File;

final class C0889p {
    private static int aI = -1;

    public static boolean m2905a() {
        if (aI == 1) {
            return true;
        }
        if (aI == 0) {
            return false;
        }
        String[] strArr = new String[]{"/bin", "/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        int i = 0;
        while (i < strArr.length) {
            try {
                if (new File(strArr[i] + "su").exists()) {
                    aI = 1;
                    return true;
                }
                i++;
            } catch (Exception e) {
            }
        }
        aI = 0;
        return false;
    }
}

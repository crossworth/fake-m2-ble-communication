package com.tencent.stat.common;

import java.io.File;

class C0841o {
    private static int f2914a = -1;

    public static boolean m2758a() {
        if (f2914a == 1) {
            return true;
        }
        if (f2914a == 0) {
            return false;
        }
        String[] strArr = new String[]{"/bin", "/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        int i = 0;
        while (i < strArr.length) {
            try {
                File file = new File(strArr[i] + "su");
                if (file == null || !file.exists()) {
                    i++;
                } else {
                    f2914a = 1;
                    return true;
                }
            } catch (Exception e) {
            }
        }
        f2914a = 0;
        return false;
    }
}

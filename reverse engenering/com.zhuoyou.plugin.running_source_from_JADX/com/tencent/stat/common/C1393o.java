package com.tencent.stat.common;

import java.io.File;

class C1393o {
    private static int f4449a = -1;

    public static boolean m4165a() {
        if (f4449a == 1) {
            return true;
        }
        if (f4449a == 0) {
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
                    f4449a = 1;
                    return true;
                }
            } catch (Exception e) {
            }
        }
        f4449a = 0;
        return false;
    }
}

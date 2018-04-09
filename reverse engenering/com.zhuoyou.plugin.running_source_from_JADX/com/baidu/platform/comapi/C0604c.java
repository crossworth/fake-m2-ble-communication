package com.baidu.platform.comapi;

import android.app.Application;
import android.content.Context;
import com.baidu.mapapi.common.EnvironmentUtilities;
import java.io.File;

public class C0604c {
    private static boolean f1903a;

    public static void m1866a(String str, Context context) {
        if (!f1903a) {
            if (context == null) {
                throw new IllegalArgumentException("context can not be null");
            } else if (context instanceof Application) {
                NativeLoader.getInstance();
                NativeLoader.setContext(context);
                C0601a.m1853a().m1856a(context);
                C0601a.m1853a().m1860c();
                if (!(str == null || str.equals(""))) {
                    try {
                        File file = new File(str + "/test.0");
                        if (file.exists()) {
                            file.delete();
                        }
                        file.createNewFile();
                        if (file.exists()) {
                            file.delete();
                        }
                        EnvironmentUtilities.setSDCardPath(str);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new IllegalArgumentException("provided sdcard path can not used.");
                    }
                }
                f1903a = true;
            } else {
                throw new RuntimeException("context must be an Application Context");
            }
        }
    }
}

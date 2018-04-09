package com.baidu.mapapi.common;

import android.content.Context;
import com.baidu.platform.comapi.util.C0670e;
import java.io.File;

public class EnvironmentUtilities {
    static String f946a;
    static String f947b;
    static String f948c;
    static int f949d;
    static int f950e;
    static int f951f;
    private static C0670e f952g = null;

    public static String getAppCachePath() {
        return f947b;
    }

    public static String getAppSDCardPath() {
        String str = f946a + "/BaiduMapSDKNew";
        if (str.length() != 0) {
            File file = new File(str);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return str;
    }

    public static String getAppSecondCachePath() {
        return f948c;
    }

    public static int getDomTmpStgMax() {
        return f950e;
    }

    public static int getItsTmpStgMax() {
        return f951f;
    }

    public static int getMapTmpStgMax() {
        return f949d;
    }

    public static String getSDCardPath() {
        return f946a;
    }

    public static void initAppDirectory(Context context) {
        if (f952g == null) {
            f952g = C0670e.m2158a();
            f952g.m2162a(context);
        }
        if (f946a == null || f946a.length() <= 0) {
            f946a = f952g.m2164b().m2154a();
            f947b = f952g.m2164b().m2156c();
        } else {
            f947b = f946a + File.separator + "BaiduMapSDKNew" + File.separator + "cache";
        }
        f948c = f952g.m2164b().m2157d();
        f949d = 20971520;
        f950e = 52428800;
        f951f = 5242880;
    }

    public static void setSDCardPath(String str) {
        f946a = str;
    }
}

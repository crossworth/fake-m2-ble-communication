package com.droi.sdk.analytics;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.IOException;

class C0774j {
    protected static String m2380a() {
        Context context = C0770f.f2324a;
        String str = (C0755c.m2326a() && C0755c.m2336d(context)) ? Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ".droiAnalytics" + File.separator + context.getPackageName() : context.getFilesDir().getAbsolutePath() + File.separator + "droiAnalytics";
        File file = new File(str);
        if (file.mkdirs() || file.isDirectory()) {
            return str;
        }
        throw new IOException("There is no permission to create path");
    }

    protected static boolean m2381a(String str) {
        return str == null || str.length() <= 0;
    }

    public static String m2382b() {
        Context context = C0770f.f2324a;
        if (C0755c.m2326a() && C0755c.m2336d(context)) {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + ".droiAnalytics");
            if (file.mkdirs() || file.isDirectory()) {
                return file.getAbsolutePath();
            }
        }
        return C0774j.m2384c();
    }

    protected static boolean m2383b(String str) {
        return !C0774j.m2381a(str) && new File(str).exists();
    }

    protected static String m2384c() {
        File file = new File(C0770f.f2324a.getFilesDir().getAbsolutePath() + File.separator + "droiAnalytics");
        if (file.mkdirs() || file.isDirectory()) {
            return file.getAbsolutePath();
        }
        throw new IOException("There is no permission to create priva path");
    }

    protected static boolean m2385c(String str) {
        File file = new File(str);
        return (C0774j.m2381a(str) || !file.exists()) ? true : file.delete();
    }
}

package com.tencent.stat;

import android.content.Context;
import com.tencent.stat.common.C0837k;
import com.tencent.stat.common.C0842p;
import com.tencent.stat.common.StatLogger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashSet;

public class StatNativeCrashReport {
    public static final String PRE_TAG_TOMBSTONE_FNAME = "tombstone_";
    static StatNativeCrashReport f2818a = new StatNativeCrashReport();
    private static StatLogger f2819b = C0837k.m2718b();
    private static boolean f2820d;
    private static boolean f2821e = false;
    private static String f2822f = null;
    private volatile boolean f2823c = false;

    static {
        f2820d = false;
        try {
            System.loadLibrary("MtaNativeCrash");
        } catch (Throwable th) {
            f2820d = false;
            f2819b.m2683w(th);
        }
    }

    static String m2641a(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                stringBuilder.append(readLine);
                stringBuilder.append('\n');
            }
            bufferedReader.close();
        } catch (Exception e) {
            f2819b.m2679e(e);
        }
        return stringBuilder.toString();
    }

    static LinkedHashSet<File> m2642a(Context context) {
        LinkedHashSet<File> linkedHashSet = new LinkedHashSet();
        String tombstonesDir = getTombstonesDir(context);
        if (tombstonesDir != null) {
            File file = new File(tombstonesDir);
            if (file != null && file.isDirectory()) {
                File[] listFiles = file.listFiles();
                if (listFiles != null) {
                    for (File file2 : listFiles) {
                        if (file2.getName().startsWith(PRE_TAG_TOMBSTONE_FNAME) && file2.isFile()) {
                            f2819b.m2678d("get tombstone file:" + file2.getAbsolutePath().toString());
                            linkedHashSet.add(file2.getAbsoluteFile());
                        }
                    }
                }
            }
        }
        return linkedHashSet;
    }

    static long m2643b(File file) {
        long j = 0;
        try {
            j = Long.valueOf(file.getName().replace(PRE_TAG_TOMBSTONE_FNAME, "")).longValue();
        } catch (Exception e) {
            f2819b.m2679e(e);
        }
        return j;
    }

    public static void doNativeCrashTest() {
        f2818a.makeJniCrash();
    }

    public static String getTombstonesDir(Context context) {
        if (f2822f == null) {
            f2822f = C0842p.m2762a(context, "__mta_tombstone__", "");
        }
        return f2822f;
    }

    public static void initNativeCrash(Context context, String str) {
        if (!f2818a.f2823c) {
            if (str == null) {
                try {
                    str = context.getDir("tombstones", 0).getAbsolutePath();
                } catch (Throwable th) {
                    f2819b.m2683w(th);
                    return;
                }
            }
            if (str.length() > 128) {
                f2819b.m2680e("The length of tombstones dir: " + str + " can't exceeds 200 bytes.");
                return;
            }
            f2822f = str;
            C0842p.m2765b(context, "__mta_tombstone__", str);
            setNativeCrashEnable(true);
            f2818a.initJNICrash(str);
            f2818a.f2823c = true;
            f2819b.m2678d("initNativeCrash success.");
        }
    }

    public static boolean isNativeCrashDebugEnable() {
        return f2821e;
    }

    public static boolean isNativeCrashEnable() {
        return f2820d;
    }

    public static String onNativeCrashHappened() {
        String str = "";
        try {
            new RuntimeException("MTA has caught a native crash, java stack:\n").printStackTrace();
            return str;
        } catch (RuntimeException e) {
            return e.toString();
        }
    }

    public static void setNativeCrashDebugEnable(boolean z) {
        try {
            f2818a.enableNativeCrashDebug(z);
            f2821e = z;
        } catch (Throwable th) {
            f2819b.m2683w(th);
        }
    }

    public static void setNativeCrashEnable(boolean z) {
        try {
            f2818a.enableNativeCrash(z);
            f2820d = z;
        } catch (Throwable th) {
            f2819b.m2683w(th);
        }
    }

    public native void enableNativeCrash(boolean z);

    public native void enableNativeCrashDebug(boolean z);

    public native boolean initJNICrash(String str);

    public native String makeJniCrash();

    public native String stringFromJNI();
}

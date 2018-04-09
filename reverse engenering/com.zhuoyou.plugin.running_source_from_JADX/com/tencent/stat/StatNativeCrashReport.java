package com.tencent.stat;

import android.content.Context;
import com.tencent.stat.common.C1389k;
import com.tencent.stat.common.C1394p;
import com.tencent.stat.common.StatLogger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashSet;

public class StatNativeCrashReport {
    public static final String PRE_TAG_TOMBSTONE_FNAME = "tombstone_";
    static StatNativeCrashReport f4320a = new StatNativeCrashReport();
    private static StatLogger f4321b = C1389k.m4125b();
    private static boolean f4322d;
    private static boolean f4323e = false;
    private static String f4324f = null;
    private volatile boolean f4325c = false;

    static {
        f4322d = false;
        try {
            System.loadLibrary("MtaNativeCrash");
        } catch (Throwable th) {
            f4322d = false;
            f4321b.m4088w(th);
        }
    }

    static String m4025a(File file) {
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
            f4321b.m4084e(e);
        }
        return stringBuilder.toString();
    }

    static LinkedHashSet<File> m4026a(Context context) {
        LinkedHashSet<File> linkedHashSet = new LinkedHashSet();
        String tombstonesDir = getTombstonesDir(context);
        if (tombstonesDir != null) {
            File file = new File(tombstonesDir);
            if (file != null && file.isDirectory()) {
                File[] listFiles = file.listFiles();
                if (listFiles != null) {
                    for (File file2 : listFiles) {
                        if (file2.getName().startsWith(PRE_TAG_TOMBSTONE_FNAME) && file2.isFile()) {
                            f4321b.m4083d("get tombstone file:" + file2.getAbsolutePath().toString());
                            linkedHashSet.add(file2.getAbsoluteFile());
                        }
                    }
                }
            }
        }
        return linkedHashSet;
    }

    static long m4027b(File file) {
        long j = 0;
        try {
            j = Long.valueOf(file.getName().replace(PRE_TAG_TOMBSTONE_FNAME, "")).longValue();
        } catch (Exception e) {
            f4321b.m4084e(e);
        }
        return j;
    }

    public static void doNativeCrashTest() {
        f4320a.makeJniCrash();
    }

    public static String getTombstonesDir(Context context) {
        if (f4324f == null) {
            f4324f = C1394p.m4169a(context, "__mta_tombstone__", "");
        }
        return f4324f;
    }

    public static void initNativeCrash(Context context, String str) {
        if (!f4320a.f4325c) {
            if (str == null) {
                try {
                    str = context.getDir("tombstones", 0).getAbsolutePath();
                } catch (Throwable th) {
                    f4321b.m4088w(th);
                    return;
                }
            }
            if (str.length() > 128) {
                f4321b.m4085e("The length of tombstones dir: " + str + " can't exceeds 200 bytes.");
                return;
            }
            f4324f = str;
            C1394p.m4172b(context, "__mta_tombstone__", str);
            setNativeCrashEnable(true);
            f4320a.initJNICrash(str);
            f4320a.f4325c = true;
            f4321b.m4083d("initNativeCrash success.");
        }
    }

    public static boolean isNativeCrashDebugEnable() {
        return f4323e;
    }

    public static boolean isNativeCrashEnable() {
        return f4322d;
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
            f4320a.enableNativeCrashDebug(z);
            f4323e = z;
        } catch (Throwable th) {
            f4321b.m4088w(th);
        }
    }

    public static void setNativeCrashEnable(boolean z) {
        try {
            f4320a.enableNativeCrash(z);
            f4322d = z;
        } catch (Throwable th) {
            f4321b.m4088w(th);
        }
    }

    public native void enableNativeCrash(boolean z);

    public native void enableNativeCrashDebug(boolean z);

    public native boolean initJNICrash(String str);

    public native String makeJniCrash();

    public native String stringFromJNI();
}

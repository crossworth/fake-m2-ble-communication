package com.tencent.stat;

import android.content.Context;
import android.os.Environment;
import android.provider.Settings.System;
import com.tencent.stat.common.C0832d;
import com.tencent.stat.common.C0837k;
import com.tencent.stat.common.C0842p;
import com.tencent.stat.common.StatLogger;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class C0826a {
    private static C0826a f2861b = null;
    private StatLogger f2862a = C0837k.m2718b();
    private boolean f2863c = false;
    private boolean f2864d = false;
    private boolean f2865e = false;
    private Context f2866f = null;

    private C0826a(Context context) {
        this.f2866f = context.getApplicationContext();
        this.f2863c = m2665b(context);
        this.f2864d = m2667d(context);
        this.f2865e = m2666c(context);
    }

    public static synchronized C0826a m2664a(Context context) {
        C0826a c0826a;
        synchronized (C0826a.class) {
            if (f2861b == null) {
                f2861b = new C0826a(context);
            }
            c0826a = f2861b;
        }
        return c0826a;
    }

    private boolean m2665b(Context context) {
        if (C0837k.m2715a(context, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            return true;
        }
        this.f2862a.m2680e((Object) "Check permission failed: android.permission.WRITE_EXTERNAL_STORAGE");
        return false;
    }

    private boolean m2666c(Context context) {
        if (C0837k.m2715a(context, "android.permission.WRITE_SETTINGS")) {
            return true;
        }
        this.f2862a.m2680e((Object) "Check permission failed: android.permission.WRITE_SETTINGS");
        return false;
    }

    private boolean m2667d(Context context) {
        return C0837k.m2724d() < 14 ? m2665b(context) : true;
    }

    public boolean m2668a(String str, String str2) {
        C0842p.m2765b(this.f2866f, str, str2);
        return true;
    }

    public String m2669b(String str, String str2) {
        return C0842p.m2762a(this.f2866f, str, str2);
    }

    public boolean m2670c(String str, String str2) {
        if (!this.f2863c) {
            return false;
        }
        try {
            C0832d.m2688a(Environment.getExternalStorageDirectory() + "/" + "Tencent/mta");
            File file = new File(Environment.getExternalStorageDirectory(), "Tencent/mta/.mid.txt");
            if (file != null) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                bufferedWriter.write(str + SeparatorConstants.SEPARATOR_ADS_ID + str2);
                bufferedWriter.write("\n");
                bufferedWriter.close();
            }
            return true;
        } catch (Throwable th) {
            this.f2862a.m2683w(th);
            return false;
        }
    }

    public String m2671d(String str, String str2) {
        if (!this.f2863c) {
            return null;
        }
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "Tencent/mta/.mid.txt");
            if (file != null) {
                for (String split : C0832d.m2689a(file)) {
                    String[] split2 = split.split(SeparatorConstants.SEPARATOR_ADS_ID);
                    if (split2.length == 2 && split2[0].equals(str)) {
                        return split2[1];
                    }
                }
            }
        } catch (FileNotFoundException e) {
            this.f2862a.m2683w("Tencent/mta/.mid.txt not found.");
        } catch (Throwable th) {
            this.f2862a.m2683w(th);
        }
        return null;
    }

    public boolean m2672e(String str, String str2) {
        if (!this.f2865e) {
            return false;
        }
        System.putString(this.f2866f.getContentResolver(), str, str2);
        return true;
    }

    public String m2673f(String str, String str2) {
        return !this.f2865e ? str2 : System.getString(this.f2866f.getContentResolver(), str);
    }
}

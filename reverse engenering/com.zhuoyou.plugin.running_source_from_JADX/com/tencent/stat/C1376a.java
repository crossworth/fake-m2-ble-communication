package com.tencent.stat;

import android.content.Context;
import android.os.Environment;
import android.provider.Settings.System;
import com.tencent.stat.common.C1382d;
import com.tencent.stat.common.C1389k;
import com.tencent.stat.common.C1394p;
import com.tencent.stat.common.StatLogger;
import com.zhuoyou.plugin.running.app.Permissions;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class C1376a {
    private static C1376a f4381b = null;
    private StatLogger f4382a = C1389k.m4125b();
    private boolean f4383c = false;
    private boolean f4384d = false;
    private boolean f4385e = false;
    private Context f4386f = null;

    private C1376a(Context context) {
        this.f4386f = context.getApplicationContext();
        this.f4383c = m4070b(context);
        this.f4384d = m4072d(context);
        this.f4385e = m4071c(context);
    }

    public static synchronized C1376a m4069a(Context context) {
        C1376a c1376a;
        synchronized (C1376a.class) {
            if (f4381b == null) {
                f4381b = new C1376a(context);
            }
            c1376a = f4381b;
        }
        return c1376a;
    }

    private boolean m4070b(Context context) {
        if (C1389k.m4122a(context, Permissions.PERMISSION_WRITE_STORAGE)) {
            return true;
        }
        this.f4382a.m4085e((Object) "Check permission failed: android.permission.WRITE_EXTERNAL_STORAGE");
        return false;
    }

    private boolean m4071c(Context context) {
        if (C1389k.m4122a(context, "android.permission.WRITE_SETTINGS")) {
            return true;
        }
        this.f4382a.m4085e((Object) "Check permission failed: android.permission.WRITE_SETTINGS");
        return false;
    }

    private boolean m4072d(Context context) {
        return C1389k.m4131d() < 14 ? m4070b(context) : true;
    }

    public boolean m4073a(String str, String str2) {
        C1394p.m4172b(this.f4386f, str, str2);
        return true;
    }

    public String m4074b(String str, String str2) {
        return C1394p.m4169a(this.f4386f, str, str2);
    }

    public boolean m4075c(String str, String str2) {
        if (!this.f4383c) {
            return false;
        }
        try {
            C1382d.m4093a(Environment.getExternalStorageDirectory() + "/" + "Tencent/mta");
            File file = new File(Environment.getExternalStorageDirectory(), "Tencent/mta/.mid.txt");
            if (file != null) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                bufferedWriter.write(str + "," + str2);
                bufferedWriter.write("\n");
                bufferedWriter.close();
            }
            return true;
        } catch (Throwable th) {
            this.f4382a.m4088w(th);
            return false;
        }
    }

    public String m4076d(String str, String str2) {
        if (!this.f4383c) {
            return null;
        }
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "Tencent/mta/.mid.txt");
            if (file != null) {
                for (String split : C1382d.m4094a(file)) {
                    String[] split2 = split.split(",");
                    if (split2.length == 2 && split2[0].equals(str)) {
                        return split2[1];
                    }
                }
            }
        } catch (FileNotFoundException e) {
            this.f4382a.m4088w("Tencent/mta/.mid.txt not found.");
        } catch (Throwable th) {
            this.f4382a.m4088w(th);
        }
        return null;
    }

    public boolean m4077e(String str, String str2) {
        if (!this.f4385e) {
            return false;
        }
        System.putString(this.f4386f.getContentResolver(), str, str2);
        return true;
    }

    public String m4078f(String str, String str2) {
        return !this.f4385e ? str2 : System.getString(this.f4386f.getContentResolver(), str);
    }
}

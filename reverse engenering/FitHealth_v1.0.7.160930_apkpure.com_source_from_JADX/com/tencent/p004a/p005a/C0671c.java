package com.tencent.p004a.p005a;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Environment;
import com.tencent.p004a.p008b.C0678a;
import com.tencent.p004a.p008b.C0680b.C0679a;
import com.tencent.p004a.p008b.C0681c;
import com.tencent.p004a.p008b.C0682d;
import java.io.File;

/* compiled from: ProGuard */
public class C0671c implements OnSharedPreferenceChangeListener {
    protected static final C0674h f2322a;
    protected static final C0674h f2323b;
    protected C1710a f2324c;
    private volatile boolean f2325d = false;
    private volatile boolean f2326e = true;
    private volatile boolean f2327f = true;

    public static File m2244a() {
        Object obj = null;
        String str = C0679a.f2349a + File.separator + C0678a.m2292b();
        C0682d b = C0681c.m2295b();
        if (b != null && b.m2302c() > 8388608) {
            obj = 1;
        }
        if (obj != null) {
            return new File(Environment.getExternalStorageDirectory(), str);
        }
        return new File(C0678a.m2293c(), str);
    }

    static {
        File a = C0671c.m2244a();
        f2322a = new C0674h(a, 24, 262144, 8192, "OpenSDK.Client.File.Tracer", 10000, 10, ".app.log", 604800000);
        f2323b = new C0674h(a, 24, 262144, 8192, "OpenSDK.File.Tracer", 10000, 10, ".OpenSDK.log", 604800000);
    }

    public void mo2081b() {
        if (this.f2324c != null) {
            this.f2324c.m4630a();
            this.f2324c.m4634b();
        }
    }

    public void m2245a(int i, String str, String str2, Throwable th) {
        if (m2247c()) {
            if (m2248d()) {
                if (this.f2324c != null) {
                    this.f2324c.m2258b(i, Thread.currentThread(), System.currentTimeMillis(), str, str2, th);
                } else {
                    return;
                }
            }
            if (m2249e()) {
                C1712f.f4507a.m2258b(i, Thread.currentThread(), System.currentTimeMillis(), str, str2, th);
            }
        }
    }

    public final boolean m2247c() {
        return this.f2325d;
    }

    public final boolean m2248d() {
        return this.f2326e;
    }

    public final boolean m2249e() {
        return this.f2327f;
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
        if ("debug.file.tracelevel".equals(str)) {
            int i = sharedPreferences.getInt("debug.file.tracelevel", 63);
            m2245a(8, "WnsTracer", "File Trace Level Changed = " + i, null);
            this.f2324c.m2254a(i);
        }
    }
}

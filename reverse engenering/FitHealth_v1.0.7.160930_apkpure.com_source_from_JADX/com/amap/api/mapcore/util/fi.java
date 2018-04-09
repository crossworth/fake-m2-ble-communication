package com.amap.api.mapcore.util;

import android.content.Context;
import android.text.TextUtils;
import com.amap.api.mapcore.util.fa.C0255a;
import com.amap.api.mapcore.util.fd.C0256a;
import dalvik.system.DexFile;
import java.io.File;
import java.util.Date;

/* compiled from: DynamicClassLoader */
class fi extends fg {
    public void m4288a(String str, String str2) throws Exception {
        try {
            m890b();
            this.c = DexFile.loadDex(str, str2, 0);
        } catch (Throwable e) {
            eb.m742a(e, "DynamicClassLoader", "loadDexFile()");
            throw new Exception("load dex fail");
        }
    }

    private boolean m4284a(ek ekVar, dv dvVar, String str) {
        return fa.m860a(ekVar, fa.m853a(this.a, dvVar.m706a(), dvVar.m708b()), str, dvVar);
    }

    private boolean m4285a(ek ekVar, String str, String str2) {
        String a = fa.m852a(this.a, str);
        if (fa.m860a(ekVar, str, a, this.e)) {
            return true;
        }
        if (C0255a.m847a(ekVar, str) != null) {
            return false;
        }
        if (!TextUtils.isEmpty(this.f)) {
            C0255a.m849a(ekVar, new C0256a(str, ds.m678a(a), this.e.m706a(), this.e.m708b(), str2).m872a("useodex").m873a(), fd.m877b(str));
        }
        return true;
    }

    public fi(final Context context, dv dvVar, boolean z) throws Exception {
        super(context, dvVar, z);
        final String b = fa.m862b(context, dvVar.m706a(), dvVar.m708b());
        final String a = fa.m850a(context);
        if (TextUtils.isEmpty(b) || TextUtils.isEmpty(a)) {
            throw new Exception("dexPath or dexOutputDir is null.");
        }
        File file = new File(b);
        File parentFile = file.getParentFile();
        if (!file.exists()) {
            if (parentFile != null && parentFile.exists()) {
                fa.m864c(context, dvVar.m706a(), dvVar.m708b());
            }
            throw new Exception("file not exist!");
        } else if (z) {
            m4288a(b, a + File.separator + fa.m854a(file.getName()));
            new Thread(this) {
                final /* synthetic */ fi f608d;

                public void run() {
                    try {
                        this.f608d.m4286a(context, b, a);
                    } catch (Throwable th) {
                        eb.m742a(th, "DynamicClassLoader", "run()");
                    }
                }
            }.start();
        }
    }

    protected Class<?> findClass(String str) throws ClassNotFoundException {
        try {
            if (this.c == null) {
                throw new ClassNotFoundException(str);
            }
            Class<?> cls = (Class) this.b.get(str);
            if (cls == null) {
                cls = this.c.loadClass(str, this);
                this.b.put(str, cls);
                if (cls == null) {
                    throw new ClassNotFoundException(str);
                }
            }
            return cls;
        } catch (Throwable th) {
            eb.m742a(th, "DynamicClassLoader", "findClass()");
            ClassNotFoundException classNotFoundException = new ClassNotFoundException(str);
        }
    }

    void m4286a(Context context, String str, String str2) throws Exception {
        new Date().getTime();
        try {
            ek ekVar = new ek(context, fc.m4276a());
            File file = new File(str);
            fd a = C0255a.m847a(ekVar, file.getName());
            if (a != null) {
                this.f = a.m883d();
            }
            if (!m4284a(ekVar, this.e, file.getAbsolutePath())) {
                this.d = false;
                fa.m856a(this.a, ekVar, file.getName());
                Object a2 = fa.m851a(this.a, ekVar, this.e);
                if (!TextUtils.isEmpty(a2)) {
                    this.f = a2;
                    m887a(this.a, this.e);
                }
            }
            if (file.exists()) {
                String str3 = str2 + File.separator + fa.m854a(file.getName());
                File file2 = new File(str3);
                if (file2.exists() && !m4285a(ekVar, fa.m854a(file.getName()), this.f)) {
                    m4288a(str, str2 + File.separator + fa.m854a(file.getName()));
                    mo1650a(file2, str3, this.f, ekVar);
                }
                new Date().getTime();
            }
        } catch (Throwable th) {
            eb.m742a(th, "DynamicClassLoader", "verifyDynamicSDK()");
        }
    }

    protected void mo1650a(File file, String str, String str2, ek ekVar) {
        if (!TextUtils.isEmpty(this.f) || !file.exists()) {
            String a = ds.m678a(str);
            String name = file.getName();
            C0255a.m849a(ekVar, new C0256a(name, a, this.e.m706a(), this.e.m708b(), str2).m872a("useodex").m873a(), fd.m877b(name));
        }
    }
}

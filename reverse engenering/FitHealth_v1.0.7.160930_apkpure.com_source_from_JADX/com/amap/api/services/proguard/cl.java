package com.amap.api.services.proguard;

import android.content.Context;
import android.text.TextUtils;
import com.amap.api.services.proguard.cd.C0374a;
import com.amap.api.services.proguard.cg.C0375a;
import dalvik.system.DexFile;
import java.io.File;
import java.util.Date;

/* compiled from: DynamicClassLoader */
class cl extends cj {
    public void m4481a(String str, String str2) throws Exception {
        try {
            m1487b();
            this.c = DexFile.loadDex(str, str2, 0);
        } catch (Throwable e) {
            be.m1340a(e, "DynamicClassLoader", "loadDexFile()");
            throw new Exception("load dex fail");
        }
    }

    private boolean m4477a(bn bnVar, ba baVar, String str) {
        return cd.m1457a(bnVar, cd.m1450a(this.a, baVar.m1308a(), baVar.m1309b()), str, baVar);
    }

    private boolean m4478a(bn bnVar, String str, String str2) {
        String a = cd.m1449a(this.a, str);
        if (cd.m1457a(bnVar, str, a, this.e)) {
            return true;
        }
        if (C0374a.m1444a(bnVar, str) != null) {
            return false;
        }
        if (!TextUtils.isEmpty(this.f)) {
            C0374a.m1446a(bnVar, new C0375a(str, ay.m1279a(a), this.e.m1308a(), this.e.m1309b(), str2).m1469a("useodex").m1470a(), cg.m1474b(str));
        }
        return true;
    }

    public cl(final Context context, ba baVar, boolean z) throws Exception {
        super(context, baVar, z);
        final String b = cd.m1459b(context, baVar.m1308a(), baVar.m1309b());
        final String a = cd.m1447a(context);
        if (TextUtils.isEmpty(b) || TextUtils.isEmpty(a)) {
            throw new Exception("dexPath or dexOutputDir is null.");
        }
        File file = new File(b);
        File parentFile = file.getParentFile();
        if (!file.exists()) {
            if (parentFile != null && parentFile.exists()) {
                cd.m1461c(context, baVar.m1308a(), baVar.m1309b());
            }
            throw new Exception("file not exist!");
        } else if (z) {
            m4481a(b, a + File.separator + cd.m1451a(file.getName()));
            new Thread(this) {
                final /* synthetic */ cl f1469d;

                public void run() {
                    try {
                        this.f1469d.m4479a(context, b, a);
                    } catch (Throwable th) {
                        be.m1340a(th, "DynamicClassLoader", "run()");
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
            be.m1340a(th, "DynamicClassLoader", "findClass()");
            ClassNotFoundException classNotFoundException = new ClassNotFoundException(str);
        }
    }

    void m4479a(Context context, String str, String str2) throws Exception {
        new Date().getTime();
        try {
            bn bnVar = new bn(context, cf.m4469c());
            File file = new File(str);
            cg a = C0374a.m1444a(bnVar, file.getName());
            if (a != null) {
                this.f = a.m1480d();
            }
            if (!m4477a(bnVar, this.e, file.getAbsolutePath())) {
                this.d = false;
                cd.m1453a(this.a, bnVar, file.getName());
                Object a2 = cd.m1448a(this.a, bnVar, this.e);
                if (!TextUtils.isEmpty(a2)) {
                    this.f = a2;
                    m1484a(this.a, this.e);
                }
            }
            if (file.exists()) {
                String str3 = str2 + File.separator + cd.m1451a(file.getName());
                File file2 = new File(str3);
                if (file2.exists() && !m4478a(bnVar, cd.m1451a(file.getName()), this.f)) {
                    m4481a(str, str2 + File.separator + cd.m1451a(file.getName()));
                    mo1775a(file2, str3, this.f, bnVar);
                }
                new Date().getTime();
            }
        } catch (Throwable th) {
            be.m1340a(th, "DynamicClassLoader", "verifyDynamicSDK()");
        }
    }

    protected void mo1775a(File file, String str, String str2, bn bnVar) {
        if (!TextUtils.isEmpty(this.f) || !file.exists()) {
            String a = ay.m1279a(str);
            String name = file.getName();
            C0374a.m1446a(bnVar, new C0375a(name, a, this.e.m1308a(), this.e.m1309b(), str2).m1469a("useodex").m1470a(), cg.m1474b(name));
        }
    }
}

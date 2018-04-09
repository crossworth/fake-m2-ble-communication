package com.amap.api.services.proguard;

import android.content.Context;
import android.text.TextUtils;
import dalvik.system.DexFile;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/* compiled from: BaseClassLoader */
abstract class cj extends ClassLoader {
    protected final Context f1460a;
    protected final Map<String, Class<?>> f1461b = new HashMap();
    protected DexFile f1462c = null;
    volatile boolean f1463d = true;
    protected ba f1464e;
    protected String f1465f;

    abstract void mo1775a(File file, String str, String str2, bn bnVar);

    public cj(Context context, ba baVar, boolean z) {
        super(context.getClassLoader());
        this.f1460a = context;
        this.f1464e = baVar;
    }

    public boolean m1486a() {
        return this.f1462c != null;
    }

    protected void m1484a(Context context, ba baVar) {
        String b = cd.m1459b(context, baVar.m1308a(), baVar.m1309b());
        Object a = cd.m1447a(context);
        if (!TextUtils.isEmpty(b) && !TextUtils.isEmpty(a)) {
            ce.m1462a(context, baVar);
            try {
                File file = new File(b);
                File parentFile = file.getParentFile();
                if (file.exists()) {
                    String str = a + File.separator + cd.m1451a(file.getName());
                    DexFile loadDex = DexFile.loadDex(b, str, 0);
                    if (loadDex != null) {
                        loadDex.close();
                        mo1775a(new File(str), str, this.f1465f, new bn(context, cf.m4469c()));
                    }
                } else if (parentFile != null && parentFile.exists()) {
                    cd.m1461c(context, baVar.m1308a(), baVar.m1309b());
                }
            } catch (Throwable th) {
                be.m1340a(th, "DynamicClassLoader", "getInstanceByThread()");
            }
        }
    }

    protected void m1487b() {
        try {
            this.f1461b.clear();
            if (this.f1462c != null) {
                this.f1462c.close();
            }
        } catch (Throwable th) {
            be.m1340a(th, "DynamicClassLoader", "preReleaseDexFile()");
        }
    }
}

package com.amap.api.mapcore.util;

import android.content.Context;
import android.text.TextUtils;
import dalvik.system.DexFile;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/* compiled from: BaseClassLoader */
abstract class fg extends ClassLoader {
    protected final Context f599a;
    protected final Map<String, Class<?>> f600b = new HashMap();
    protected DexFile f601c = null;
    volatile boolean f602d = true;
    protected dv f603e;
    protected String f604f;

    abstract void mo1650a(File file, String str, String str2, ek ekVar);

    public fg(Context context, dv dvVar, boolean z) {
        super(context.getClassLoader());
        this.f599a = context;
        this.f603e = dvVar;
    }

    public boolean m889a() {
        return this.f601c != null;
    }

    protected void m887a(Context context, dv dvVar) {
        String b = fa.m862b(context, dvVar.m706a(), dvVar.m708b());
        Object a = fa.m850a(context);
        if (!TextUtils.isEmpty(b) && !TextUtils.isEmpty(a)) {
            fb.m865a(context, dvVar);
            try {
                File file = new File(b);
                File parentFile = file.getParentFile();
                if (file.exists()) {
                    String str = a + File.separator + fa.m854a(file.getName());
                    DexFile loadDex = DexFile.loadDex(b, str, 0);
                    if (loadDex != null) {
                        loadDex.close();
                        mo1650a(new File(str), str, this.f604f, new ek(context, fc.m4276a()));
                    }
                } else if (parentFile != null && parentFile.exists()) {
                    fa.m864c(context, dvVar.m706a(), dvVar.m708b());
                }
            } catch (Throwable th) {
                eb.m742a(th, "DynamicClassLoader", "getInstanceByThread()");
            }
        }
    }

    protected void m890b() {
        try {
            this.f600b.clear();
            if (this.f601c != null) {
                this.f601c.close();
            }
        } catch (Throwable th) {
            eb.m742a(th, "DynamicClassLoader", "preReleaseDexFile()");
        }
    }
}

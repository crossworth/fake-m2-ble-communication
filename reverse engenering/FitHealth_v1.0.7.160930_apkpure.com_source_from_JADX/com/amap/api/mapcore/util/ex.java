package com.amap.api.mapcore.util;

import android.content.Context;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* compiled from: ClassLoaderFactory */
public class ex {
    private static final ex f574a = new ex();
    private final Map<String, fg> f575b = new HashMap();

    private ex() {
    }

    public static ex m840a() {
        return f574a;
    }

    public synchronized fg m842a(Context context, dv dvVar) throws Exception {
        fg fgVar;
        if (m841a(dvVar)) {
            String a = dvVar.m706a();
            fgVar = (fg) this.f575b.get(a);
            if (fgVar == null) {
                try {
                    fg fiVar = new fi(context.getApplicationContext(), dvVar, true);
                    try {
                        this.f575b.put(a, fiVar);
                        fb.m865a(context, dvVar);
                        fgVar = fiVar;
                    } catch (Throwable th) {
                        fgVar = fiVar;
                    }
                } catch (Throwable th2) {
                }
            }
        } else {
            throw new Exception("sdkInfo referance is null");
        }
        return fgVar;
    }

    public fg m843b(Context context, dv dvVar) throws Exception {
        fg fgVar = (fg) this.f575b.get(dvVar.m706a());
        if (fgVar != null) {
            fgVar.m887a(context, dvVar);
            return fgVar;
        }
        fgVar = new fi(context.getApplicationContext(), dvVar, false);
        fgVar.m887a(context, dvVar);
        this.f575b.put(dvVar.m706a(), fgVar);
        fb.m865a(context, dvVar);
        return fgVar;
    }

    private boolean m841a(dv dvVar) {
        if (dvVar == null || TextUtils.isEmpty(dvVar.m708b()) || TextUtils.isEmpty(dvVar.m706a())) {
            return false;
        }
        return true;
    }
}

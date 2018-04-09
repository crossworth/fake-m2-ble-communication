package com.amap.api.mapcore.util;

import android.content.Context;
import java.util.List;

/* compiled from: LogDBOperation */
public class es {
    private ek f562a;

    public es(Context context) {
        try {
            this.f562a = new ek(context, ek.m790a(er.class));
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void m816a(String str, Class<? extends et> cls) {
        try {
            m813c(str, cls);
        } catch (Throwable th) {
            eb.m742a(th, "LogDB", "delLog");
        }
    }

    public void m818b(String str, Class<? extends et> cls) {
        try {
            m813c(str, cls);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void m813c(String str, Class<? extends et> cls) {
        this.f562a.m804a(et.m820c(str), (Class) cls);
    }

    public List<? extends et> m814a(int i, Class<? extends et> cls) {
        List<? extends et> list = null;
        try {
            list = this.f562a.m808b(et.m819c(i), cls);
        } catch (Throwable th) {
            eb.m742a(th, "LogDB", "ByState");
        }
        return list;
    }

    public void m815a(et etVar) {
        if (etVar != null) {
            String c = et.m820c(etVar.m824b());
            List a = this.f562a.m800a(c, etVar.getClass(), true);
            if (a == null || a.size() == 0) {
                this.f562a.m803a((Object) etVar, true);
                return;
            }
            Object obj = (et) a.get(0);
            if (etVar.m821a() == 0) {
                obj.m825b(obj.m827c() + 1);
            } else {
                obj.m825b(0);
            }
            this.f562a.m806a(c, obj, true);
        }
    }

    public void m817b(et etVar) {
        try {
            this.f562a.m805a(et.m820c(etVar.m824b()), (Object) etVar);
        } catch (Throwable th) {
            eb.m742a(th, "LogDB", "updateLogInfo");
        }
    }
}

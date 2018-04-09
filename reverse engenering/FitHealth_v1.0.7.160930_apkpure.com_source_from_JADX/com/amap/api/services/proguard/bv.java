package com.amap.api.services.proguard;

import android.content.Context;
import java.util.List;

/* compiled from: LogDBOperation */
public class bv {
    private bn f1424a;

    public bv(Context context) {
        try {
            this.f1424a = new bn(context, bn.m1388a(bu.class));
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void m1413a(String str, Class<? extends bw> cls) {
        try {
            m1410c(str, cls);
        } catch (Throwable th) {
            be.m1340a(th, "LogDB", "delLog");
        }
    }

    public void m1415b(String str, Class<? extends bw> cls) {
        try {
            m1410c(str, cls);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void m1410c(String str, Class<? extends bw> cls) {
        this.f1424a.m1402a(bw.m1417c(str), (Class) cls);
    }

    public List<? extends bw> m1411a(int i, Class<? extends bw> cls) {
        List<? extends bw> list = null;
        try {
            list = this.f1424a.m1405b(bw.m1416c(i), cls);
        } catch (Throwable th) {
            be.m1340a(th, "LogDB", "ByState");
        }
        return list;
    }

    public void m1412a(bw bwVar) {
        if (bwVar != null) {
            String c = bw.m1417c(bwVar.m1421b());
            List a = this.f1424a.m1398a(c, bwVar.getClass(), true);
            if (a == null || a.size() == 0) {
                this.f1424a.m1401a((Object) bwVar, true);
                return;
            }
            Object obj = (bw) a.get(0);
            if (bwVar.m1418a() == 0) {
                obj.m1422b(obj.m1424c() + 1);
            } else {
                obj.m1422b(0);
            }
            this.f1424a.m1404a(c, obj, true);
        }
    }

    public void m1414b(bw bwVar) {
        try {
            this.f1424a.m1403a(bw.m1417c(bwVar.m1421b()), (Object) bwVar);
        } catch (Throwable th) {
            be.m1340a(th, "LogDB", "updateLogInfo");
        }
    }
}

package com.amap.api.services.proguard;

import android.content.Context;
import java.util.List;

/* compiled from: SDKDBOperation */
public class bx {
    private bn f1429a;
    private Context f1430b;

    public bx(Context context, boolean z) {
        this.f1430b = context;
        this.f1429a = m1425a(this.f1430b, z);
    }

    private bn m1425a(Context context, boolean z) {
        try {
            return new bn(context, bn.m1388a(bu.class));
        } catch (Throwable th) {
            if (z) {
                th.printStackTrace();
                return null;
            }
            be.m1340a(th, "SDKDB", "getDB");
            return null;
        }
    }

    public void m1427a(ba baVar) {
        if (baVar != null) {
            try {
                if (this.f1429a == null) {
                    this.f1429a = m1425a(this.f1430b, false);
                }
                String a = ba.m1304a(baVar.m1308a());
                List b = this.f1429a.m1405b(a, ba.class);
                if (b == null || b.size() == 0) {
                    this.f1429a.m1399a((Object) baVar);
                } else {
                    this.f1429a.m1403a(a, (Object) baVar);
                }
            } catch (Throwable th) {
                be.m1340a(th, "SDKDB", "insert");
                th.printStackTrace();
            }
        }
    }

    public List<ba> m1426a() {
        List<ba> list = null;
        try {
            list = this.f1429a.m1398a(ba.m1307e(), ba.class, true);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return list;
    }
}

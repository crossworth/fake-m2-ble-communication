package com.amap.api.services.proguard;

import android.content.Context;
import java.util.List;

/* compiled from: UpdateLogDBOperation */
public class by {
    private bn f1431a = m1428a(this.f1432b);
    private Context f1432b;

    public by(Context context) {
        this.f1432b = context;
    }

    private bn m1428a(Context context) {
        try {
            return new bn(context, bn.m1388a(bu.class));
        } catch (Throwable th) {
            be.m1340a(th, "UpdateLogDB", "getDB");
            return null;
        }
    }

    public bz m1429a() {
        try {
            if (this.f1431a == null) {
                this.f1431a = m1428a(this.f1432b);
            }
            List b = this.f1431a.m1405b("1=1", bz.class);
            if (b.size() > 0) {
                return (bz) b.get(0);
            }
        } catch (Throwable th) {
            be.m1340a(th, "UpdateLogDB", "getUpdateLog");
        }
        return null;
    }

    public void m1430a(bz bzVar) {
        if (bzVar != null) {
            try {
                if (this.f1431a == null) {
                    this.f1431a = m1428a(this.f1432b);
                }
                String str = "1=1";
                List b = this.f1431a.m1405b(str, bz.class);
                if (b == null || b.size() == 0) {
                    this.f1431a.m1399a((Object) bzVar);
                } else {
                    this.f1431a.m1403a(str, (Object) bzVar);
                }
            } catch (Throwable th) {
                be.m1340a(th, "UpdateLogDB", "updateLog");
            }
        }
    }
}

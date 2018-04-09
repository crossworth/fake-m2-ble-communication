package com.amap.api.mapcore.util;

import android.content.Context;
import java.util.List;

/* compiled from: UpdateLogDBOperation */
public class ev {
    private ek f569a = m831a(this.f570b);
    private Context f570b;

    public ev(Context context) {
        this.f570b = context;
    }

    private ek m831a(Context context) {
        try {
            return new ek(context, ek.m790a(er.class));
        } catch (Throwable th) {
            eb.m742a(th, "UpdateLogDB", "getDB");
            return null;
        }
    }

    public ew m832a() {
        try {
            if (this.f569a == null) {
                this.f569a = m831a(this.f570b);
            }
            List b = this.f569a.m808b("1=1", ew.class);
            if (b.size() > 0) {
                return (ew) b.get(0);
            }
        } catch (Throwable th) {
            eb.m742a(th, "UpdateLogDB", "getUpdateLog");
        }
        return null;
    }

    public void m833a(ew ewVar) {
        if (ewVar != null) {
            try {
                if (this.f569a == null) {
                    this.f569a = m831a(this.f570b);
                }
                String str = "1=1";
                List b = this.f569a.m808b(str, ew.class);
                if (b == null || b.size() == 0) {
                    this.f569a.m801a((Object) ewVar);
                } else {
                    this.f569a.m805a(str, (Object) ewVar);
                }
            } catch (Throwable th) {
                eb.m742a(th, "UpdateLogDB", "updateLog");
            }
        }
    }
}

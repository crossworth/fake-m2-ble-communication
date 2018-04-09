package com.amap.api.mapcore.util;

import android.content.Context;
import java.util.List;

/* compiled from: SDKDBOperation */
public class eu {
    private ek f567a;
    private Context f568b;

    public eu(Context context, boolean z) {
        this.f568b = context;
        this.f567a = m828a(this.f568b, z);
    }

    private ek m828a(Context context, boolean z) {
        try {
            return new ek(context, ek.m790a(er.class));
        } catch (Throwable th) {
            if (z) {
                th.printStackTrace();
                return null;
            }
            eb.m742a(th, "SDKDB", "getDB");
            return null;
        }
    }

    public void m830a(dv dvVar) {
        if (dvVar != null) {
            try {
                if (this.f567a == null) {
                    this.f567a = m828a(this.f568b, false);
                }
                String a = dv.m702a(dvVar.m706a());
                List b = this.f567a.m808b(a, dv.class);
                if (b == null || b.size() == 0) {
                    this.f567a.m801a((Object) dvVar);
                } else {
                    this.f567a.m805a(a, (Object) dvVar);
                }
            } catch (Throwable th) {
                eb.m742a(th, "SDKDB", "insert");
                th.printStackTrace();
            }
        }
    }

    public List<dv> m829a() {
        List<dv> list = null;
        try {
            list = this.f567a.m800a(dv.m705f(), dv.class, true);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return list;
    }
}

package com.amap.api.services.proguard;

import java.net.Proxy;

/* compiled from: BaseNetManager */
public class cq {
    private static cq f1513a;

    public static cq m1545a() {
        if (f1513a == null) {
            f1513a = new cq();
        }
        return f1513a;
    }

    public byte[] m1547a(cw cwVar) throws ar {
        ar e;
        try {
            cy a = m1546a(cwVar, true);
            if (a != null) {
                return a.f1530a;
            }
            return null;
        } catch (ar e2) {
            throw e2;
        } catch (Throwable th) {
            e2 = new ar("未知的错误");
        }
    }

    public byte[] mo1777b(cw cwVar) throws ar {
        ar e;
        try {
            cy a = m1546a(cwVar, false);
            if (a != null) {
                return a.f1530a;
            }
            return null;
        } catch (ar e2) {
            throw e2;
        } catch (Throwable th) {
            be.m1340a(th, "BaseNetManager", "makeSyncPostRequest");
            e2 = new ar("未知的错误");
        }
    }

    protected void m1549c(cw cwVar) throws ar {
        if (cwVar == null) {
            throw new ar("requeust is null");
        } else if (cwVar.mo1759g() == null || "".equals(cwVar.mo1759g())) {
            throw new ar("request url is empty");
        }
    }

    protected cy m1546a(cw cwVar, boolean z) throws ar {
        ar e;
        try {
            Proxy proxy;
            m1549c(cwVar);
            if (cwVar.f1529g == null) {
                proxy = null;
            } else {
                proxy = cwVar.f1529g;
            }
            return new ct(cwVar.f1527e, cwVar.f1528f, proxy, z).m1560a(cwVar.m1573i(), cwVar.mo1757c(), cwVar.m1574j());
        } catch (ar e2) {
            throw e2;
        } catch (Throwable th) {
            th.printStackTrace();
            e2 = new ar("未知的错误");
        }
    }
}

package com.amap.api.mapcore.util;

import java.net.Proxy;

/* compiled from: BaseNetManager */
public class fq {
    private static fq f652a;

    public static fq m948a() {
        if (f652a == null) {
            f652a = new fq();
        }
        return f652a;
    }

    public byte[] m950a(fw fwVar) throws dk {
        dk e;
        try {
            fy a = m949a(fwVar, true);
            if (a != null) {
                return a.f669a;
            }
            return null;
        } catch (dk e2) {
            throw e2;
        } catch (Throwable th) {
            e2 = new dk("未知的错误");
        }
    }

    public byte[] mo1651b(fw fwVar) throws dk {
        dk e;
        try {
            fy a = m949a(fwVar, false);
            if (a != null) {
                return a.f669a;
            }
            return null;
        } catch (dk e2) {
            throw e2;
        } catch (Throwable th) {
            eb.m742a(th, "BaseNetManager", "makeSyncPostRequest");
            e2 = new dk("未知的错误");
        }
    }

    protected void m952c(fw fwVar) throws dk {
        if (fwVar == null) {
            throw new dk("requeust is null");
        } else if (fwVar.mo1630a() == null || "".equals(fwVar.mo1630a())) {
            throw new dk("request url is empty");
        }
    }

    protected fy m949a(fw fwVar, boolean z) throws dk {
        dk e;
        try {
            Proxy proxy;
            m952c(fwVar);
            if (fwVar.f668i == null) {
                proxy = null;
            } else {
                proxy = fwVar.f668i;
            }
            return new fs(fwVar.f666g, fwVar.f667h, proxy, z).m964a(fwVar.m977f(), fwVar.mo1632c(), fwVar.m978g());
        } catch (dk e2) {
            throw e2;
        } catch (Throwable th) {
            th.printStackTrace();
            e2 = new dk("未知的错误");
        }
    }
}

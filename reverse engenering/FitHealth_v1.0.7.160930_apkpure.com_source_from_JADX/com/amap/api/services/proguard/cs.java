package com.amap.api.services.proguard;

import java.net.Proxy;

/* compiled from: DownloadManager */
public class cs {
    private ct f1514a;
    private cw f1515b;

    /* compiled from: DownloadManager */
    public interface C0385a {
        void mo1771a(Throwable th);

        void mo1772a(byte[] bArr, long j);

        void mo1773b();

        void mo1774c();
    }

    public cs(cw cwVar) {
        this(cwVar, 0, -1);
    }

    public cs(cw cwVar, long j, long j2) {
        Proxy proxy;
        this.f1515b = cwVar;
        if (cwVar.f1529g == null) {
            proxy = null;
        } else {
            proxy = cwVar.f1529g;
        }
        this.f1514a = new ct(this.f1515b.f1527e, this.f1515b.f1528f, proxy);
        this.f1514a.m1564b(j2);
        this.f1514a.m1562a(j);
    }

    public void m1554a(C0385a c0385a) {
        this.f1514a.m1563a(this.f1515b.mo1759g(), this.f1515b.mo1757c(), this.f1515b.mo1756b(), c0385a);
    }
}

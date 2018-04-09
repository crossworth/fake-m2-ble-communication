package com.amap.api.mapcore.util;

import java.net.Proxy;

/* compiled from: DownloadManager */
public class fr {
    private fs f653a;
    private fw f654b;

    /* compiled from: DownloadManager */
    public interface C0263a {
        void mo1624a(Throwable th);

        void mo1625a(byte[] bArr, long j);

        void mo1626d();

        void mo1627e();
    }

    public fr(fw fwVar) {
        this(fwVar, 0, -1);
    }

    public fr(fw fwVar, long j, long j2) {
        Proxy proxy;
        this.f654b = fwVar;
        if (fwVar.f668i == null) {
            proxy = null;
        } else {
            proxy = fwVar.f668i;
        }
        this.f653a = new fs(this.f654b.f666g, this.f654b.f667h, proxy);
        this.f653a.m969b(j2);
        this.f653a.m967a(j);
    }

    public void m958a(C0263a c0263a) {
        this.f653a.m968a(this.f654b.mo1630a(), this.f654b.mo1632c(), this.f654b.mo1631b(), c0263a);
    }

    public void m957a() {
        this.f653a.m966a();
    }
}

package com.amap.api.mapcore.util;

import android.content.Context;
import com.amap.api.mapcore.util.ch.C0231a;
import com.amap.api.mapcore.util.fr.C0263a;
import com.amap.api.maps.AMapException;
import java.io.File;
import java.io.IOException;

/* compiled from: NetFileFetch */
public class cc implements C0263a {
    cd f4139a = null;
    long f4140b = 0;
    long f4141c = 0;
    long f4142d;
    boolean f4143e = true;
    bx f4144f;
    long f4145g = 0;
    C0227a f4146h;
    private Context f4147i;
    private ch f4148j;
    private String f4149k;
    private fr f4150l;
    private by f4151m;

    /* compiled from: NetFileFetch */
    public interface C0227a {
        void mo1474d();
    }

    public cc(cd cdVar, String str, Context context, ch chVar) throws IOException {
        this.f4144f = bx.m366a(context.getApplicationContext());
        this.f4139a = cdVar;
        this.f4147i = context;
        this.f4149k = str;
        this.f4148j = chVar;
        m4160g();
    }

    private void m4159f() throws IOException {
        fw ciVar = new ci(this.f4149k);
        ciVar.m972a(1800000);
        ciVar.m975b(1800000);
        this.f4150l = new fr(ciVar, this.f4140b, this.f4141c);
        this.f4151m = new by(this.f4139a.m399b() + File.separator + this.f4139a.m400c(), this.f4140b);
    }

    private void m4160g() {
        if (this.f4144f.m381f(this.f4139a.m402e())) {
            this.f4143e = false;
            m4165l();
            return;
        }
        this.f4140b = 0;
        this.f4141c = 0;
    }

    public void m4166a() {
        try {
            if (dj.m595c(this.f4147i)) {
                m4162i();
                if (dm.f465a == 1) {
                    if (!m4161h()) {
                        this.f4143e = true;
                    }
                    if (this.f4143e) {
                        this.f4142d = m4170b();
                        if (this.f4142d == -1) {
                            cf.m424a("File Length is not known!");
                        } else if (this.f4142d == -2) {
                            cf.m424a("File is not access!");
                        } else {
                            this.f4141c = this.f4142d;
                        }
                        this.f4140b = 0;
                    }
                    if (this.f4148j != null) {
                        this.f4148j.mo2993m();
                    }
                    m4159f();
                    this.f4150l.m958a(this);
                } else if (this.f4148j != null) {
                    this.f4148j.mo2990a(C0231a.amap_exception);
                }
            } else if (this.f4148j != null) {
                this.f4148j.mo2990a(C0231a.network_exception);
            }
        } catch (Throwable e) {
            ee.m4243a(e, "SiteFileFetch", "download");
            if (this.f4148j != null) {
                this.f4148j.mo2990a(C0231a.amap_exception);
            }
        } catch (IOException e2) {
            if (this.f4148j != null) {
                this.f4148j.mo2990a(C0231a.file_io_exception);
            }
        }
    }

    private boolean m4161h() {
        if (new File(this.f4139a.m399b() + File.separator + this.f4139a.m400c()).length() < 10) {
            return false;
        }
        return true;
    }

    private void m4162i() throws AMapException {
        if (dm.f465a != 1) {
            int i = 0;
            while (i < 3) {
                try {
                    if (!dm.m612a(this.f4147i, dj.m597e())) {
                        i++;
                    } else {
                        return;
                    }
                } catch (Throwable th) {
                    ee.m4243a(th, "SiteFileFetch", "authOffLineDownLoad");
                    th.printStackTrace();
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long m4170b() throws java.io.IOException {
        /*
        r5 = this;
        r2 = -1;
        r0 = new java.net.URL;
        r1 = r5.f4139a;
        r1 = r1.m398a();
        r0.<init>(r1);
        r0 = r0.openConnection();
        r0 = (java.net.HttpURLConnection) r0;
        r1 = "User-Agent";
        r3 = com.amap.api.mapcore.util.C0273r.f697d;
        r0.setRequestProperty(r1, r3);
        r1 = r0.getResponseCode();
        r3 = 400; // 0x190 float:5.6E-43 double:1.976E-321;
        if (r1 < r3) goto L_0x0027;
    L_0x0021:
        r5.m4157a(r1);
        r0 = -2;
    L_0x0026:
        return r0;
    L_0x0027:
        r1 = 1;
    L_0x0028:
        r3 = r0.getHeaderFieldKey(r1);
        if (r3 == 0) goto L_0x0043;
    L_0x002e:
        r4 = "Content-Length";
        r4 = r3.equalsIgnoreCase(r4);
        if (r4 == 0) goto L_0x0040;
    L_0x0036:
        r0 = r0.getHeaderField(r3);
        r0 = java.lang.Integer.parseInt(r0);
    L_0x003e:
        r0 = (long) r0;
        goto L_0x0026;
    L_0x0040:
        r1 = r1 + 1;
        goto L_0x0028;
    L_0x0043:
        r0 = r2;
        goto L_0x003e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.api.mapcore.util.cc.b():long");
    }

    private void m4163j() {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.f4139a != null && currentTimeMillis - this.f4145g > 500) {
            m4164k();
            this.f4145g = currentTimeMillis;
            m4158a(this.f4140b);
        }
    }

    private void m4164k() {
        this.f4144f.m374a(this.f4139a.m402e(), this.f4139a.m401d(), this.f4142d, this.f4140b, this.f4141c);
    }

    private void m4158a(long j) {
        if (this.f4142d > 0 && this.f4148j != null) {
            this.f4148j.mo2989a(this.f4142d, j);
            this.f4145g = System.currentTimeMillis();
        }
    }

    private boolean m4165l() {
        if (!this.f4144f.m381f(this.f4139a.m402e())) {
            return false;
        }
        this.f4142d = (long) this.f4144f.m379d(this.f4139a.m402e());
        long[] a = this.f4144f.m376a(this.f4139a.m402e(), 0);
        this.f4140b = a[0];
        this.f4141c = a[1];
        return true;
    }

    private void m4157a(int i) {
        System.err.println("Error Code : " + i);
    }

    public void m4171c() {
        if (this.f4150l != null) {
            this.f4150l.m957a();
        }
    }

    public void mo1626d() {
        if (this.f4148j != null) {
            this.f4148j.mo2995o();
        }
        m4164k();
    }

    public void mo1627e() {
        m4163j();
        if (this.f4148j != null) {
            this.f4148j.mo2994n();
        }
        if (this.f4151m != null) {
            this.f4151m.m383a();
        }
        if (this.f4146h != null) {
            this.f4146h.mo1474d();
        }
    }

    public void mo1624a(Throwable th) {
        if (this.f4148j != null) {
            this.f4148j.mo2990a(C0231a.network_exception);
        }
        if (!(th instanceof IOException) && this.f4151m != null) {
            this.f4151m.m383a();
        }
    }

    public void mo1625a(byte[] bArr, long j) {
        try {
            this.f4151m.m382a(bArr);
            this.f4140b = j;
            m4163j();
        } catch (Throwable e) {
            e.printStackTrace();
            ee.m4243a(e, "fileAccessI", "fileAccessI.write(byte[] data)");
            if (this.f4148j != null) {
                this.f4148j.mo2990a(C0231a.file_io_exception);
            }
            if (this.f4150l != null) {
                this.f4150l.m957a();
            }
        }
    }

    public void m4167a(C0227a c0227a) {
        this.f4146h = c0227a;
    }
}

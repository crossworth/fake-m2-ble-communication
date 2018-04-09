package com.tencent.open.p036a;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/* compiled from: ProGuard */
public class C1305a extends C1304i implements Callback {
    private C1306b f4085a;
    private FileWriter f4086b;
    private File f4087c;
    private char[] f4088d;
    private volatile C1315g f4089e;
    private volatile C1315g f4090f;
    private volatile C1315g f4091g;
    private volatile C1315g f4092h;
    private volatile boolean f4093i;
    private HandlerThread f4094j;
    private Handler f4095k;

    public C1305a(C1306b c1306b) {
        this(C1307c.f4107b, true, C1316h.f4130a, c1306b);
    }

    public C1305a(int i, boolean z, C1316h c1316h, C1306b c1306b) {
        super(i, z, c1316h);
        this.f4093i = false;
        m3829a(c1306b);
        this.f4089e = new C1315g();
        this.f4090f = new C1315g();
        this.f4091g = this.f4089e;
        this.f4092h = this.f4090f;
        this.f4088d = new char[c1306b.m3847d()];
        m3824g();
        this.f4094j = new HandlerThread(c1306b.m3845c(), c1306b.m3850f());
        if (this.f4094j != null) {
            this.f4094j.start();
        }
        if (this.f4094j.isAlive() && this.f4094j.getLooper() != null) {
            this.f4095k = new Handler(this.f4094j.getLooper(), this);
        }
    }

    public void m3827a() {
        if (this.f4095k.hasMessages(1024)) {
            this.f4095k.removeMessages(1024);
        }
        this.f4095k.sendEmptyMessage(1024);
    }

    public void m3831b() {
        m3825h();
        this.f4094j.quit();
    }

    protected void mo2208a(int i, Thread thread, long j, String str, String str2, Throwable th) {
        m3830a(m3822e().m3880a(i, thread, j, str, str2, th));
    }

    protected void m3830a(String str) {
        this.f4091g.m3876a(str);
        if (this.f4091g.m3875a() >= m3832c().m3847d()) {
            m3827a();
        }
    }

    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 1024:
                m3823f();
                break;
        }
        return true;
    }

    private void m3823f() {
        if (Thread.currentThread() == this.f4094j && !this.f4093i) {
            this.f4093i = true;
            m3826i();
            try {
                this.f4092h.m3877a(m3824g(), this.f4088d);
            } catch (IOException e) {
            } finally {
                this.f4092h.m3878b();
            }
            this.f4093i = false;
        }
    }

    private Writer m3824g() {
        File a = m3832c().m3836a();
        if (!(a == null || a.equals(this.f4087c)) || (this.f4086b == null && a != null)) {
            this.f4087c = a;
            m3825h();
            try {
                this.f4086b = new FileWriter(this.f4087c, true);
            } catch (IOException e) {
                return null;
            }
        }
        return this.f4086b;
    }

    private void m3825h() {
        try {
            if (this.f4086b != null) {
                this.f4086b.flush();
                this.f4086b.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void m3826i() {
        synchronized (this) {
            if (this.f4091g == this.f4089e) {
                this.f4091g = this.f4090f;
                this.f4092h = this.f4089e;
            } else {
                this.f4091g = this.f4089e;
                this.f4092h = this.f4090f;
            }
        }
    }

    public C1306b m3832c() {
        return this.f4085a;
    }

    public void m3829a(C1306b c1306b) {
        this.f4085a = c1306b;
    }
}

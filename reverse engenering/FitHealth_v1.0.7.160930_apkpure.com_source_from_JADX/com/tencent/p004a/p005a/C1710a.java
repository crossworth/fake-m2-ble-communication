package com.tencent.p004a.p005a;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/* compiled from: ProGuard */
public class C1710a extends C0673g implements Callback {
    private C0674h f4495a;
    private FileWriter f4496b;
    private File f4497c;
    private char[] f4498d;
    private volatile C0672e f4499e;
    private volatile C0672e f4500f;
    private volatile C0672e f4501g;
    private volatile C0672e f4502h;
    private volatile boolean f4503i;
    private HandlerThread f4504j;
    private Handler f4505k;

    public C1710a(C0674h c0674h) {
        this(63, true, C0670b.f2321a, c0674h);
    }

    public C1710a(int i, boolean z, C0670b c0670b, C0674h c0674h) {
        super(i, z, c0670b);
        this.f4503i = false;
        m4632a(c0674h);
        this.f4499e = new C0672e();
        this.f4500f = new C0672e();
        this.f4501g = this.f4499e;
        this.f4502h = this.f4500f;
        this.f4498d = new char[c0674h.m2283f()];
        c0674h.m2271b();
        m4627h();
        this.f4504j = new HandlerThread(c0674h.m2276c(), c0674h.m2286i());
        if (this.f4504j != null) {
            this.f4504j.start();
        }
        if (this.f4504j.isAlive() && this.f4504j.getLooper() != null) {
            this.f4505k = new Handler(this.f4504j.getLooper(), this);
        }
        m4625f();
    }

    public void m4630a() {
        if (this.f4505k.hasMessages(1024)) {
            this.f4505k.removeMessages(1024);
        }
    }

    public void m4634b() {
        m4628i();
        this.f4504j.quit();
    }

    protected void mo2080a(int i, Thread thread, long j, String str, String str2, Throwable th) {
        m4633a(m2260e().m2243a(i, thread, j, str, str2, th));
    }

    protected void m4633a(String str) {
        this.f4501g.m2251a(str);
        if (this.f4501g.m2250a() >= m4635c().m2283f()) {
            m4630a();
        }
    }

    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 1024:
                m4626g();
                m4625f();
                break;
        }
        return true;
    }

    private void m4625f() {
        if (this.f4505k != null) {
            this.f4505k.sendEmptyMessageDelayed(1024, m4635c().m2284g());
        }
    }

    private void m4626g() {
        if (Thread.currentThread() == this.f4504j && !this.f4503i) {
            this.f4503i = true;
            m4629j();
            try {
                this.f4502h.m2252a(m4627h(), this.f4498d);
            } catch (IOException e) {
            } finally {
                this.f4502h.m2253b();
            }
            this.f4503i = false;
        }
    }

    private Writer m4627h() {
        File a = m4635c().m2266a();
        if (!(a == null || a.equals(this.f4497c))) {
            this.f4497c = a;
            m4628i();
            try {
                this.f4496b = new FileWriter(this.f4497c, true);
            } catch (IOException e) {
                return null;
            }
        }
        return this.f4496b;
    }

    private void m4628i() {
        try {
            if (this.f4496b != null) {
                this.f4496b.flush();
                this.f4496b.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void m4629j() {
        synchronized (this) {
            if (this.f4501g == this.f4499e) {
                this.f4501g = this.f4500f;
                this.f4502h = this.f4499e;
            } else {
                this.f4501g = this.f4499e;
                this.f4502h = this.f4500f;
            }
        }
    }

    public C0674h m4635c() {
        return this.f4495a;
    }

    public void m4632a(C0674h c0674h) {
        this.f4495a = c0674h;
    }
}

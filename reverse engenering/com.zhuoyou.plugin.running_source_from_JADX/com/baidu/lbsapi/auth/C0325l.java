package com.baidu.lbsapi.auth;

import android.os.Handler;
import android.os.Looper;

class C0325l extends Thread {
    Handler f95a = null;
    private Object f96b = new Object();
    private boolean f97c = false;

    C0325l() {
    }

    C0325l(String str) {
        super(str);
    }

    public void m160a() {
        if (C0311a.f70a) {
            C0311a.m122a("Looper thread quit()");
        }
        this.f95a.getLooper().quit();
    }

    public void m161b() {
        synchronized (this.f96b) {
            try {
                if (!this.f97c) {
                    this.f96b.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void m162c() {
        synchronized (this.f96b) {
            this.f97c = true;
            this.f96b.notifyAll();
        }
    }

    public void run() {
        Looper.prepare();
        this.f95a = new Handler();
        if (C0311a.f70a) {
            C0311a.m122a("new Handler() finish!!");
        }
        Looper.loop();
        if (C0311a.f70a) {
            C0311a.m122a("LooperThread run() thread id:" + String.valueOf(Thread.currentThread().getId()));
        }
    }
}

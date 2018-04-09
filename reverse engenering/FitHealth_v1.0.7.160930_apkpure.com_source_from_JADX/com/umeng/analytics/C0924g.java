package com.umeng.analytics;

/* compiled from: SafeRunnable */
public abstract class C0924g implements Runnable {
    public abstract void mo2152a();

    public void run() {
        try {
            mo2152a();
        } catch (Throwable th) {
            if (th != null) {
                th.printStackTrace();
            }
        }
    }
}

package com.droi.sdk.core;

import android.net.LocalServerSocket;
import java.io.IOException;

public class SocketLock {
    private final String f2757a;
    private LocalServerSocket f2758b;

    public SocketLock(String str) {
        this.f2757a = str;
    }

    public final synchronized void lock() {
        while (true) {
            try {
                tryLock();
                break;
            } catch (IOException e) {
                try {
                    Thread.sleep(10, 0);
                } catch (InterruptedException e2) {
                }
            }
        }
    }

    public final synchronized void release() {
        if (this.f2758b != null) {
            try {
                this.f2758b.close();
            } catch (IOException e) {
            }
        }
    }

    public final synchronized boolean timedLock(int i) {
        boolean z = false;
        synchronized (this) {
            long currentTimeMillis = System.currentTimeMillis() + ((long) i);
            while (System.currentTimeMillis() <= currentTimeMillis) {
                try {
                    tryLock();
                    z = true;
                    break;
                } catch (IOException e) {
                    try {
                        Thread.sleep(10, 0);
                    } catch (InterruptedException e2) {
                    }
                }
            }
        }
        return z;
    }

    public final synchronized void tryLock() throws IOException {
        if (this.f2758b == null) {
            this.f2758b = new LocalServerSocket(this.f2757a);
        } else {
            throw new IllegalStateException("tryLock but has locked");
        }
    }
}

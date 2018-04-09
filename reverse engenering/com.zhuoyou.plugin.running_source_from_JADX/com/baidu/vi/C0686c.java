package com.baidu.vi;

import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class C0686c {
    public String f2254a;
    public int f2255b;
    public int f2256c;

    static /* synthetic */ class C06851 {
        static final /* synthetic */ int[] f2253a = new int[State.values().length];

        static {
            try {
                f2253a[State.CONNECTED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f2253a[State.CONNECTING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f2253a[State.DISCONNECTED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f2253a[State.DISCONNECTING.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f2253a[State.SUSPENDED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public C0686c(NetworkInfo networkInfo) {
        this.f2254a = networkInfo.getTypeName();
        this.f2255b = networkInfo.getType();
        switch (C06851.f2253a[networkInfo.getState().ordinal()]) {
            case 1:
                this.f2256c = 2;
                return;
            case 2:
                this.f2256c = 1;
                return;
            default:
                this.f2256c = 0;
                return;
        }
    }
}

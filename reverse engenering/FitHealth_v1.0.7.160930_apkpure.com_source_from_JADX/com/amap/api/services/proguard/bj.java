package com.amap.api.services.proguard;

import android.content.Context;
import android.os.Looper;
import java.util.Date;
import java.util.List;

/* compiled from: CrashLogProcessor */
public class bj extends bl {
    private static boolean f4357a = true;

    protected bj(int i) {
        super(i);
    }

    protected boolean mo1764a(Context context) {
        if (!f4357a) {
            return false;
        }
        f4357a = false;
        synchronized (Looper.getMainLooper()) {
            by byVar = new by(context);
            bz a = byVar.m1429a();
            if (a == null) {
                return true;
            } else if (a.m1432a()) {
                a.m1431a(false);
                byVar.m1430a(a);
                return true;
            } else {
                return false;
            }
        }
    }

    protected String mo1765a(String str) {
        return ay.m1284c(str + bb.m1312a(new Date().getTime()));
    }

    protected String mo1763a(List<ba> list) {
        return null;
    }
}
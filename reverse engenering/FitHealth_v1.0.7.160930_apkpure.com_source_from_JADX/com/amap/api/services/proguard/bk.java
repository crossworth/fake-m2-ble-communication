package com.amap.api.services.proguard;

import android.content.Context;
import android.os.Looper;
import java.util.List;

/* compiled from: ExceptionLogProcessor */
public class bk extends bl {
    private static boolean f4358a = true;

    protected bk(int i) {
        super(i);
    }

    protected boolean mo1764a(Context context) {
        if (aw.m1257m(context) != 1 || !f4358a) {
            return false;
        }
        f4358a = false;
        synchronized (Looper.getMainLooper()) {
            by byVar = new by(context);
            bz a = byVar.m1429a();
            if (a == null) {
                return true;
            } else if (a.m1434b()) {
                a.m1433b(false);
                byVar.m1430a(a);
                return true;
            } else {
                return false;
            }
        }
    }

    protected String mo1763a(List<ba> list) {
        return null;
    }
}

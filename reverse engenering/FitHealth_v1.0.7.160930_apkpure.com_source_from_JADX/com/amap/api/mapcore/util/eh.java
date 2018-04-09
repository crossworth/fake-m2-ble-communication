package com.amap.api.mapcore.util;

import android.content.Context;
import android.os.Looper;
import java.util.List;

/* compiled from: ExceptionLogProcessor */
public class eh extends ei {
    private static boolean f4195a = true;

    protected eh(int i) {
        super(i);
    }

    protected boolean mo1647a(Context context) {
        if (dq.m656m(context) != 1 || !f4195a) {
            return false;
        }
        f4195a = false;
        synchronized (Looper.getMainLooper()) {
            ev evVar = new ev(context);
            ew a = evVar.m832a();
            if (a == null) {
                return true;
            } else if (a.m837b()) {
                a.m836b(false);
                evVar.m833a(a);
                return true;
            } else {
                return false;
            }
        }
    }

    protected String mo1646a(List<dv> list) {
        return null;
    }
}

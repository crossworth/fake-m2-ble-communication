package com.amap.api.mapcore.util;

import android.content.Context;
import android.os.Looper;
import java.util.Date;
import java.util.List;

/* compiled from: CrashLogProcessor */
public class eg extends ei {
    private static boolean f4194a = true;

    protected eg(int i) {
        super(i);
    }

    protected boolean mo1647a(Context context) {
        if (!f4194a) {
            return false;
        }
        f4194a = false;
        synchronized (Looper.getMainLooper()) {
            ev evVar = new ev(context);
            ew a = evVar.m832a();
            if (a == null) {
                return true;
            } else if (a.m835a()) {
                a.m834a(false);
                evVar.m833a(a);
                return true;
            } else {
                return false;
            }
        }
    }

    protected String mo1648a(String str) {
        return ds.m683c(str + dx.m712a(new Date().getTime()));
    }

    protected String mo1646a(List<dv> list) {
        return null;
    }
}

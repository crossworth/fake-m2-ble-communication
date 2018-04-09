package com.amap.api.services.proguard;

import android.content.Context;

/* compiled from: PoiHandler */
abstract class C2055u<T, V> extends C1972b<T, V> {
    public C2055u(Context context, T t) {
        super(context, t);
    }

    protected boolean mo3703d(String str) {
        if (str == null || str.equals("") || str.equals("[]")) {
            return true;
        }
        return false;
    }
}

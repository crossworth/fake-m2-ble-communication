package com.baidu.android.bbalbs.common.util;

import com.baidu.android.bbalbs.common.util.C0309b.C0307a;
import java.util.Comparator;

class C0310c implements Comparator<C0307a> {
    final /* synthetic */ C0309b f61a;

    C0310c(C0309b c0309b) {
        this.f61a = c0309b;
    }

    public int m101a(C0307a c0307a, C0307a c0307a2) {
        int i = c0307a2.f51b - c0307a.f51b;
        return i == 0 ? (c0307a.f53d && c0307a2.f53d) ? 0 : c0307a.f53d ? -1 : c0307a2.f53d ? 1 : i : i;
    }

    public /* synthetic */ int compare(Object obj, Object obj2) {
        return m101a((C0307a) obj, (C0307a) obj2);
    }
}

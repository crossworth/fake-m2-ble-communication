package com.baidu.platform.comjni.map.basemap;

import android.os.Bundle;
import android.util.LongSparseArray;

public class BaseMapCallback {
    private static LongSparseArray<C0632b> f2227a = new LongSparseArray();

    public static int ReqLayerData(Bundle bundle, long j, int i, Bundle bundle2) {
        int size = f2227a.size();
        for (int i2 = 0; i2 < size; i2++) {
            C0632b c0632b = (C0632b) f2227a.valueAt(i2);
            if (c0632b != null && c0632b.mo1833a(j)) {
                return c0632b.mo1832a(bundle, j, i, bundle2);
            }
        }
        return 0;
    }

    public static void addLayerDataInterface(long j, C0632b c0632b) {
        f2227a.put(j, c0632b);
    }

    public static void removeLayerDataInterface(long j) {
        f2227a.remove(j);
    }
}

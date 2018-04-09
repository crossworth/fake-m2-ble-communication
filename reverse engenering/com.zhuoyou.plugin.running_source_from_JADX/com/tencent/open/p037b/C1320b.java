package com.tencent.open.p037b;

import android.os.Bundle;
import java.io.Serializable;
import java.util.HashMap;

/* compiled from: ProGuard */
public class C1320b implements Serializable {
    public final HashMap<String, String> f4134a = new HashMap();

    public C1320b(Bundle bundle) {
        if (bundle != null) {
            for (String str : bundle.keySet()) {
                this.f4134a.put(str, bundle.getString(str));
            }
        }
    }
}

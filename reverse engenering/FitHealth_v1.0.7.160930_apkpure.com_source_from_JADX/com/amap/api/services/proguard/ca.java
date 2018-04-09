package com.amap.api.services.proguard;

import android.content.Context;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* compiled from: ClassLoaderFactory */
public class ca {
    private static final ca f1436a = new ca();
    private final Map<String, cj> f1437b = new HashMap();

    private ca() {
    }

    public static ca m1437a() {
        return f1436a;
    }

    public synchronized cj m1439a(Context context, ba baVar) throws Exception {
        cj cjVar;
        if (m1438a(baVar)) {
            String a = baVar.m1308a();
            cjVar = (cj) this.f1437b.get(a);
            if (cjVar == null) {
                try {
                    cj clVar = new cl(context.getApplicationContext(), baVar, true);
                    try {
                        this.f1437b.put(a, clVar);
                        ce.m1462a(context, baVar);
                        cjVar = clVar;
                    } catch (Throwable th) {
                        cjVar = clVar;
                    }
                } catch (Throwable th2) {
                }
            }
        } else {
            throw new Exception("sdkInfo referance is null");
        }
        return cjVar;
    }

    public cj m1440b(Context context, ba baVar) throws Exception {
        cj cjVar = (cj) this.f1437b.get(baVar.m1308a());
        if (cjVar != null) {
            cjVar.m1484a(context, baVar);
            return cjVar;
        }
        cjVar = new cl(context.getApplicationContext(), baVar, false);
        cjVar.m1484a(context, baVar);
        this.f1437b.put(baVar.m1308a(), cjVar);
        ce.m1462a(context, baVar);
        return cjVar;
    }

    private boolean m1438a(ba baVar) {
        if (baVar == null || TextUtils.isEmpty(baVar.m1309b()) || TextUtils.isEmpty(baVar.m1308a())) {
            return false;
        }
        return true;
    }
}

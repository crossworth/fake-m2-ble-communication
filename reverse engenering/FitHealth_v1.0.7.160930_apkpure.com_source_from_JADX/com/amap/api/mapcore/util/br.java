package com.amap.api.mapcore.util;

import android.content.Context;
import com.amap.api.maps.AMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/* compiled from: TaskManager */
public class br {
    private static br f279a;
    private ga f280b;
    private LinkedHashMap<String, gc> f281c = new LinkedHashMap();
    private boolean f282d = true;

    public static br m340a(int i) {
        return m341a(true, i);
    }

    private static synchronized br m341a(boolean z, int i) {
        br brVar;
        synchronized (br.class) {
            try {
                if (f279a == null) {
                    f279a = new br(z, i);
                } else if (z) {
                    if (f279a.f280b == null) {
                        f279a.f280b = ga.m982a(i);
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            brVar = f279a;
        }
        return brVar;
    }

    private br(boolean z, int i) {
        if (z) {
            try {
                this.f280b = ga.m982a(i);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public void m342a() {
        synchronized (this.f281c) {
            if (this.f281c.size() < 1) {
                return;
            }
            for (Entry entry : this.f281c.entrySet()) {
                entry.getKey();
                ((bn) entry.getValue()).m4019b();
            }
            this.f281c.clear();
        }
    }

    public void m343a(bq bqVar) {
        synchronized (this.f281c) {
            bn bnVar = (bn) this.f281c.get(bqVar.mo2991b());
            if (bnVar == null) {
                return;
            }
            bnVar.m4019b();
        }
    }

    public void m344a(bq bqVar, Context context, AMap aMap) throws dk {
        if (this.f280b == null) {
        }
        if (!this.f281c.containsKey(bqVar.mo2991b())) {
            bn bnVar = new bn((cg) bqVar, context.getApplicationContext(), aMap);
            synchronized (this.f281c) {
                this.f281c.put(bqVar.mo2991b(), bnVar);
            }
        }
        this.f280b.m989a((gc) this.f281c.get(bqVar.mo2991b()));
    }

    public void m345b() {
        m342a();
        ga gaVar = this.f280b;
        ga.m983a();
        this.f280b = null;
        f279a = null;
    }

    public void m346b(bq bqVar) {
        bn bnVar = (bn) this.f281c.get(bqVar.mo2991b());
        if (bnVar != null) {
            synchronized (this.f281c) {
                bnVar.m4020c();
                this.f281c.remove(bqVar.mo2991b());
            }
        }
    }
}

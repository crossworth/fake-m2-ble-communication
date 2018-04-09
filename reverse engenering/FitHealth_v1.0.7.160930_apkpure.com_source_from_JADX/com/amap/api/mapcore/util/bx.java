package com.amap.api.mapcore.util;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

/* compiled from: OfflineDBOperation */
public class bx {
    private static volatile bx f302a;
    private static ek f303b;
    private Context f304c;

    public static bx m366a(Context context) {
        if (f302a == null) {
            synchronized (bx.class) {
                if (f302a == null) {
                    f302a = new bx(context);
                }
            }
        }
        return f302a;
    }

    private bx(Context context) {
        this.f304c = context;
        f303b = m369b(this.f304c);
    }

    private ek m369b(Context context) {
        try {
            return new ek(context, bw.m4028a());
        } catch (Throwable th) {
            ee.m4243a(th, "OfflineDB", "getDB");
            th.printStackTrace();
            return null;
        }
    }

    private boolean m370b() {
        if (f303b == null) {
            f303b = m369b(this.f304c);
        }
        if (f303b == null) {
            return false;
        }
        return true;
    }

    public ArrayList<bs> m371a() {
        ArrayList<bs> arrayList = new ArrayList();
        if (!m370b()) {
            return arrayList;
        }
        for (bs add : f303b.m808b("", bs.class)) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public synchronized void m373a(bs bsVar) {
        if (m370b()) {
            f303b.m802a((Object) bsVar, bv.m356d(bsVar.m362g()));
            m368a(bsVar.m362g(), bsVar.m4026c());
        }
    }

    private void m368a(String str, String str2) {
        if (str2 != null && str2.length() > 0) {
            String a = bu.m353a(str);
            if (f303b.m808b(a, bu.class).size() > 0) {
                f303b.m804a(a, bu.class);
            }
            String[] split = str2.split(";");
            List arrayList = new ArrayList();
            for (String buVar : split) {
                arrayList.add(new bu(str, buVar));
            }
            f303b.m807a(arrayList);
        }
    }

    public synchronized List<String> m372a(String str) {
        List arrayList;
        arrayList = new ArrayList();
        if (m370b()) {
            arrayList.addAll(m367a(f303b.m808b(bu.m353a(str), bu.class)));
        }
        return arrayList;
    }

    public synchronized List<String> m377b(String str) {
        List arrayList;
        arrayList = new ArrayList();
        if (m370b()) {
            arrayList.addAll(m367a(f303b.m808b(bu.m354b(str), bu.class)));
        }
        return arrayList;
    }

    private List<String> m367a(List<bu> list) {
        List<String> arrayList = new ArrayList();
        if (list.size() > 0) {
            for (bu a : list) {
                arrayList.add(a.m355a());
            }
        }
        return arrayList;
    }

    public synchronized void m378c(String str) {
        if (m370b()) {
            f303b.m804a(bv.m356d(str), bv.class);
            f303b.m804a(bu.m353a(str), bu.class);
            f303b.m804a(bt.m347a(str), bt.class);
        }
    }

    public void m374a(String str, int i, long j, long j2, long j3) {
        if (m370b()) {
            m375a(str, i, j, new long[]{j2, 0, 0, 0, 0}, new long[]{j3, 0, 0, 0, 0});
        }
    }

    public synchronized void m375a(String str, int i, long j, long[] jArr, long[] jArr2) {
        if (m370b()) {
            f303b.m802a(new bt(str, j, i, jArr[0], jArr2[0]), bt.m347a(str));
        }
    }

    public synchronized long[] m376a(String str, int i) {
        long[] jArr;
        if (m370b()) {
            long a;
            long b;
            List b2 = f303b.m808b(bt.m347a(str), bt.class);
            if (b2.size() > 0) {
                a = ((bt) b2.get(0)).m349a(i);
                b = ((bt) b2.get(0)).m351b(i);
            } else {
                b = 0;
                a = 0;
            }
            jArr = new long[]{a, b};
        } else {
            jArr = new long[]{0, 0};
        }
        return jArr;
    }

    public synchronized int m379d(String str) {
        int i = 0;
        synchronized (this) {
            if (m370b()) {
                List b = f303b.m808b(bt.m347a(str), bt.class);
                long j = 0;
                if (b.size() > 0) {
                    j = ((bt) b.get(0)).m348a();
                }
                i = (int) j;
            }
        }
        return i;
    }

    public synchronized String m380e(String str) {
        String str2;
        str2 = null;
        if (m370b()) {
            List b = f303b.m808b(bv.m356d(str), bv.class);
            if (b.size() > 0) {
                str2 = ((bv) b.get(0)).m361f();
            }
        }
        return str2;
    }

    public synchronized boolean m381f(String str) {
        boolean z = false;
        synchronized (this) {
            if (m370b()) {
                if (f303b.m808b(bt.m347a(str), bt.class).size() > 0) {
                    z = true;
                }
            }
        }
        return z;
    }
}

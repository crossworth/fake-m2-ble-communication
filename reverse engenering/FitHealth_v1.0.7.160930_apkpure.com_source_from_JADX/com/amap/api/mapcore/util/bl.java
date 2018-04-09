package com.amap.api.mapcore.util;

import android.content.Context;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: OfflineMapDataVerify */
public class bl extends Thread {
    private Context f272a;
    private bx f273b;

    public bl(Context context) {
        this.f272a = context;
        this.f273b = bx.m366a(context);
    }

    public void run() {
        m311a();
    }

    private void m311a() {
        ArrayList a;
        Object obj;
        ArrayList arrayList = new ArrayList();
        ArrayList a2 = this.f273b.m371a();
        if (a2.size() < 1) {
            a = m310a(this.f272a);
            obj = 1;
        } else {
            a = a2;
            obj = null;
        }
        Iterator it = a.iterator();
        while (it.hasNext()) {
            bs bsVar = (bs) it.next();
            if (!(bsVar == null || bsVar.m360e() == null || bsVar.m362g().length() < 1)) {
                if (Thread.interrupted()) {
                    break;
                }
                if (obj != null) {
                    arrayList.add(bsVar);
                }
                if ((bsVar.l == 4 || bsVar.l == 7) && !m312a(bsVar.m362g())) {
                    bsVar.m4024b();
                    try {
                        cf.m425a(bsVar.m362g(), this.f272a);
                    } catch (Exception e) {
                    }
                    arrayList.add(bsVar);
                }
            }
        }
        bi a3 = bi.m277a(this.f272a);
        if (a3 != null) {
            a3.m293a(arrayList);
        }
    }

    private ArrayList<bs> m310a(Context context) {
        ArrayList<bs> arrayList = new ArrayList();
        File file = new File(dj.m589b(context));
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    if (file2.getName().endsWith(".zip.tmp.dt")) {
                        bs a = m309a(file2);
                        if (!(a == null || a.m360e() == null)) {
                            arrayList.add(a);
                            this.f273b.m373a(a);
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    private bs m309a(File file) {
        String a = dj.m571a(file);
        bs bsVar = new bs();
        bsVar.m4025b(a);
        return bsVar;
    }

    private boolean m312a(String str) {
        List<String> a = this.f273b.m372a(str);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dj.m570a(this.f272a));
        stringBuilder.append("vmap/");
        int length = stringBuilder.length();
        for (String replace : a) {
            stringBuilder.replace(length, stringBuilder.length(), replace);
            if (!new File(stringBuilder.toString()).exists()) {
                return false;
            }
        }
        return true;
    }

    public void destroy() {
        this.f272a = null;
        this.f273b = null;
    }
}

package com.droi.sdk.push;

import android.content.Context;
import android.database.Cursor;
import com.droi.sdk.push.data.C0987b;
import com.droi.sdk.push.utils.C1015j;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeSet;

class C1002q {
    static HashMap f3320a;
    private long f3321b;
    private Context f3322c;
    private C0987b f3323d = null;

    public C1002q(Context context) {
        this.f3322c = context;
        this.f3323d = C0987b.m3029a(this.f3322c);
        this.f3321b = this.f3323d.m3041d();
        m3069c();
    }

    private HashMap m3068b() {
        Object obj;
        long currentTimeMillis = System.currentTimeMillis();
        HashMap hashMap = new HashMap();
        Cursor c = this.f3323d.m3040c();
        if (c != null) {
            if (c.getCount() > 0) {
                c.moveToFirst();
                obj = null;
                while (!c.isAfterLast()) {
                    Object obj2;
                    String string = c.getString(c.getColumnIndexOrThrow("appid"));
                    long j = c.getLong(c.getColumnIndexOrThrow("msg_id"));
                    long j2 = c.getLong(c.getColumnIndexOrThrow("createtime"));
                    if (currentTimeMillis - j2 >= 2592000000L) {
                        obj2 = 1;
                        this.f3323d.m3039b(string, j);
                    } else {
                        TreeSet treeSet;
                        TreeSet treeSet2 = (TreeSet) hashMap.get(string);
                        if (treeSet2 == null) {
                            treeSet2 = new TreeSet();
                            hashMap.put(string, treeSet2);
                            treeSet = treeSet2;
                        } else {
                            treeSet = treeSet2;
                        }
                        treeSet.add(new C1003s(this, j, j2));
                        obj2 = obj;
                    }
                    c.moveToNext();
                    obj = obj2;
                }
            } else {
                obj = null;
            }
            c.close();
        } else {
            obj = null;
        }
        if (obj != null) {
            this.f3323d.m3032a(currentTimeMillis);
        }
        return hashMap;
    }

    private void m3069c() {
        f3320a = m3068b();
    }

    void m3070a() {
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(currentTimeMillis - this.f3321b) >= 2592000000L) {
            Iterator it = f3320a.entrySet().iterator();
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                TreeSet treeSet = (TreeSet) entry.getValue();
                String str = (String) entry.getKey();
                if (treeSet != null) {
                    Iterator it2 = treeSet.iterator();
                    while (it2.hasNext()) {
                        C1003s c1003s = (C1003s) it2.next();
                        if (c1003s != null) {
                            if (Math.abs(currentTimeMillis - c1003s.f3325b) < 2592000000L) {
                                break;
                            }
                            this.f3323d.m3039b(str, c1003s.f3324a);
                            it2.remove();
                        }
                    }
                    if (treeSet.isEmpty()) {
                        it.remove();
                    }
                }
            }
            this.f3321b = currentTimeMillis;
            this.f3323d.m3032a(currentTimeMillis);
        }
    }

    synchronized boolean m3071a(String str, long j) {
        boolean z;
        boolean z2 = false;
        synchronized (this) {
            if (!C1015j.m3168d(str) || j < 0) {
                z = false;
            } else {
                if (str != null) {
                    if (str.length() != 0) {
                        TreeSet treeSet;
                        TreeSet treeSet2 = (TreeSet) f3320a.get(str);
                        if (treeSet2 == null) {
                            treeSet2 = new TreeSet();
                            f3320a.put(str, treeSet2);
                            treeSet = treeSet2;
                        } else {
                            treeSet = treeSet2;
                        }
                        long currentTimeMillis = System.currentTimeMillis();
                        if (!treeSet.add(new C1003s(this, j, currentTimeMillis))) {
                            z2 = true;
                        }
                        if (!z2) {
                            this.f3323d.m3033a(j, str, currentTimeMillis);
                        }
                    }
                }
                z = z2;
            }
        }
        return z;
    }
}

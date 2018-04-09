package com.amap.api.mapcore.util;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

/* compiled from: LruCache */
public class df<K, V> {
    private final LinkedHashMap<K, V> f443a;
    private int f444b;
    private int f445c;
    private int f446d;
    private int f447e;
    private int f448f;
    private int f449g;
    private int f450h;

    public df(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.f445c = i;
        this.f443a = new LinkedHashMap(0, 0.75f, true);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final V m540a(K r5) {
        /*
        r4 = this;
        if (r5 != 0) goto L_0x000a;
    L_0x0002:
        r0 = new java.lang.NullPointerException;
        r1 = "key == null";
        r0.<init>(r1);
        throw r0;
    L_0x000a:
        monitor-enter(r4);
        r0 = r4.f443a;	 Catch:{ all -> 0x002a }
        r0 = r0.get(r5);	 Catch:{ all -> 0x002a }
        if (r0 == 0) goto L_0x001b;
    L_0x0013:
        r1 = r4.f449g;	 Catch:{ all -> 0x002a }
        r1 = r1 + 1;
        r4.f449g = r1;	 Catch:{ all -> 0x002a }
        monitor-exit(r4);	 Catch:{ all -> 0x002a }
    L_0x001a:
        return r0;
    L_0x001b:
        r0 = r4.f450h;	 Catch:{ all -> 0x002a }
        r0 = r0 + 1;
        r4.f450h = r0;	 Catch:{ all -> 0x002a }
        monitor-exit(r4);	 Catch:{ all -> 0x002a }
        r1 = r4.m543b(r5);
        if (r1 != 0) goto L_0x002d;
    L_0x0028:
        r0 = 0;
        goto L_0x001a;
    L_0x002a:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x002a }
        throw r0;
    L_0x002d:
        monitor-enter(r4);
        r0 = r4.f447e;	 Catch:{ all -> 0x0053 }
        r0 = r0 + 1;
        r4.f447e = r0;	 Catch:{ all -> 0x0053 }
        r0 = r4.f443a;	 Catch:{ all -> 0x0053 }
        r0 = r0.put(r5, r1);	 Catch:{ all -> 0x0053 }
        if (r0 == 0) goto L_0x0049;
    L_0x003c:
        r2 = r4.f443a;	 Catch:{ all -> 0x0053 }
        r2.put(r5, r0);	 Catch:{ all -> 0x0053 }
    L_0x0041:
        monitor-exit(r4);	 Catch:{ all -> 0x0053 }
        if (r0 == 0) goto L_0x0056;
    L_0x0044:
        r2 = 0;
        r4.mo1639a(r2, r5, r1, r0);
        goto L_0x001a;
    L_0x0049:
        r2 = r4.f444b;	 Catch:{ all -> 0x0053 }
        r3 = r4.m538c(r5, r1);	 Catch:{ all -> 0x0053 }
        r2 = r2 + r3;
        r4.f444b = r2;	 Catch:{ all -> 0x0053 }
        goto L_0x0041;
    L_0x0053:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0053 }
        throw r0;
    L_0x0056:
        r0 = r4.f445c;
        r4.m537a(r0);
        r0 = r1;
        goto L_0x001a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.api.mapcore.util.df.a(java.lang.Object):V");
    }

    public final V m544b(K k, V v) {
        if (k == null || v == null) {
            throw new NullPointerException("key == null || value == null");
        }
        V put;
        synchronized (this) {
            this.f446d++;
            this.f444b += m538c(k, v);
            put = this.f443a.put(k, v);
            if (put != null) {
                this.f444b -= m538c(k, put);
            }
        }
        if (put != null) {
            mo1639a(false, k, put, v);
        }
        m537a(this.f445c);
        return put;
    }

    private void m537a(int i) {
        while (true) {
            Object key;
            Object value;
            synchronized (this) {
                if (this.f444b >= 0 && this.f443a.isEmpty() && this.f444b == 0) {
                }
                if (this.f444b <= i) {
                    return;
                }
                Entry entry = null;
                for (Entry entry2 : this.f443a.entrySet()) {
                }
                if (entry == null) {
                    return;
                }
                key = entry.getKey();
                value = entry.getValue();
                this.f443a.remove(key);
                this.f444b -= m538c(key, value);
                this.f448f++;
            }
            mo1639a(true, key, value, null);
        }
    }

    protected void mo1639a(boolean z, K k, V v, V v2) {
    }

    protected V m543b(K k) {
        return null;
    }

    private int m538c(K k, V v) {
        int a = mo1638a(k, v);
        if (a >= 0) {
            return a;
        }
        throw new IllegalStateException("Negative size: " + k + "=" + v);
    }

    protected int mo1638a(K k, V v) {
        return 1;
    }

    public final void m541a() {
        m537a(-1);
    }

    public final synchronized String toString() {
        String format;
        int i = 0;
        synchronized (this) {
            int i2 = this.f449g + this.f450h;
            if (i2 != 0) {
                i = (this.f449g * 100) / i2;
            }
            format = String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", new Object[]{Integer.valueOf(this.f445c), Integer.valueOf(this.f449g), Integer.valueOf(this.f450h), Integer.valueOf(i)});
        }
        return format;
    }
}

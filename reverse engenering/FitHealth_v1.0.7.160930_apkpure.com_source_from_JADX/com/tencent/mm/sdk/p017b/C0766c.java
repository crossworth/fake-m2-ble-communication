package com.tencent.mm.sdk.p017b;

import java.util.LinkedHashMap;

public final class C0766c<K, V> {
    private int f2647A;
    private int f2648B;
    private int f2649C;
    private int f2650D;
    private int size;
    private final LinkedHashMap<K, V> f2651u;
    private int f2652v;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void trimToSize(int r4) {
        /*
        r3 = this;
    L_0x0000:
        monitor-enter(r3);
        r0 = r3.size;	 Catch:{ all -> 0x0032 }
        if (r0 < 0) goto L_0x0011;
    L_0x0005:
        r0 = r3.f2651u;	 Catch:{ all -> 0x0032 }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x0032 }
        if (r0 == 0) goto L_0x0035;
    L_0x000d:
        r0 = r3.size;	 Catch:{ all -> 0x0032 }
        if (r0 == 0) goto L_0x0035;
    L_0x0011:
        r0 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x0032 }
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0032 }
        r1.<init>();	 Catch:{ all -> 0x0032 }
        r2 = r3.getClass();	 Catch:{ all -> 0x0032 }
        r2 = r2.getName();	 Catch:{ all -> 0x0032 }
        r1 = r1.append(r2);	 Catch:{ all -> 0x0032 }
        r2 = ".sizeOf() is reporting inconsistent results!";
        r1 = r1.append(r2);	 Catch:{ all -> 0x0032 }
        r1 = r1.toString();	 Catch:{ all -> 0x0032 }
        r0.<init>(r1);	 Catch:{ all -> 0x0032 }
        throw r0;	 Catch:{ all -> 0x0032 }
    L_0x0032:
        r0 = move-exception;
        monitor-exit(r3);
        throw r0;
    L_0x0035:
        r0 = r3.size;	 Catch:{ all -> 0x0032 }
        if (r0 <= r4) goto L_0x0041;
    L_0x0039:
        r0 = r3.f2651u;	 Catch:{ all -> 0x0032 }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x0032 }
        if (r0 == 0) goto L_0x0043;
    L_0x0041:
        monitor-exit(r3);	 Catch:{ all -> 0x0032 }
        return;
    L_0x0043:
        r0 = r3.f2651u;	 Catch:{ all -> 0x0032 }
        r0 = r0.entrySet();	 Catch:{ all -> 0x0032 }
        r0 = r0.iterator();	 Catch:{ all -> 0x0032 }
        r0 = r0.next();	 Catch:{ all -> 0x0032 }
        r0 = (java.util.Map.Entry) r0;	 Catch:{ all -> 0x0032 }
        r1 = r0.getKey();	 Catch:{ all -> 0x0032 }
        r0.getValue();	 Catch:{ all -> 0x0032 }
        r0 = r3.f2651u;	 Catch:{ all -> 0x0032 }
        r0.remove(r1);	 Catch:{ all -> 0x0032 }
        r0 = r3.size;	 Catch:{ all -> 0x0032 }
        r0 = r0 + -1;
        r3.size = r0;	 Catch:{ all -> 0x0032 }
        r0 = r3.f2648B;	 Catch:{ all -> 0x0032 }
        r0 = r0 + 1;
        r3.f2648B = r0;	 Catch:{ all -> 0x0032 }
        monitor-exit(r3);	 Catch:{ all -> 0x0032 }
        goto L_0x0000;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mm.sdk.b.c.trimToSize(int):void");
    }

    public final synchronized boolean m2521a(K k) {
        return this.f2651u.containsKey(k);
    }

    public final V get(K k) {
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            V v = this.f2651u.get(k);
            if (v != null) {
                this.f2649C++;
                return v;
            }
            this.f2650D++;
            return null;
        }
    }

    public final V put(K k, V v) {
        if (k == null || v == null) {
            throw new NullPointerException("key == null || value == null");
        }
        V put;
        synchronized (this) {
            this.f2647A++;
            this.size++;
            put = this.f2651u.put(k, v);
            if (put != null) {
                this.size--;
            }
        }
        trimToSize(this.f2652v);
        return put;
    }

    public final synchronized String toString() {
        String format;
        int i = 0;
        synchronized (this) {
            int i2 = this.f2649C + this.f2650D;
            if (i2 != 0) {
                i = (this.f2649C * 100) / i2;
            }
            format = String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", new Object[]{Integer.valueOf(this.f2652v), Integer.valueOf(this.f2649C), Integer.valueOf(this.f2650D), Integer.valueOf(i)});
        }
        return format;
    }
}
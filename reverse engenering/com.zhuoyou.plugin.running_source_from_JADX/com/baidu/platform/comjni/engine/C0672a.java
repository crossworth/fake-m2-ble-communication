package com.baidu.platform.comjni.engine;

import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import java.util.List;

public class C0672a {
    private static final String f2225a = C0672a.class.getSimpleName();
    private static SparseArray<List<Handler>> f2226b = new SparseArray();

    public static void m2187a() {
        synchronized (f2226b) {
            int size = f2226b.size();
            for (int i = 0; i < size; i++) {
                List list = (List) f2226b.get(f2226b.keyAt(i));
                if (list != null) {
                    list.clear();
                }
            }
            f2226b.clear();
        }
    }

    public static void m2188a(int i, int i2, int i3, long j) {
        synchronized (f2226b) {
            List<Handler> list = (List) f2226b.get(i);
            if (!(list == null || list.isEmpty())) {
                for (Handler obtain : list) {
                    Message.obtain(obtain, i, i2, i3, Long.valueOf(j)).sendToTarget();
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void m2189a(int r3, android.os.Handler r4) {
        /*
        r1 = f2226b;
        monitor-enter(r1);
        if (r4 != 0) goto L_0x0007;
    L_0x0005:
        monitor-exit(r1);	 Catch:{ all -> 0x001c }
    L_0x0006:
        return;
    L_0x0007:
        r0 = f2226b;	 Catch:{ all -> 0x001c }
        r0 = r0.get(r3);	 Catch:{ all -> 0x001c }
        r0 = (java.util.List) r0;	 Catch:{ all -> 0x001c }
        if (r0 == 0) goto L_0x001f;
    L_0x0011:
        r2 = r0.contains(r4);	 Catch:{ all -> 0x001c }
        if (r2 != 0) goto L_0x001a;
    L_0x0017:
        r0.add(r4);	 Catch:{ all -> 0x001c }
    L_0x001a:
        monitor-exit(r1);	 Catch:{ all -> 0x001c }
        goto L_0x0006;
    L_0x001c:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x001c }
        throw r0;
    L_0x001f:
        r0 = new java.util.ArrayList;	 Catch:{ all -> 0x001c }
        r0.<init>();	 Catch:{ all -> 0x001c }
        r0.add(r4);	 Catch:{ all -> 0x001c }
        r2 = f2226b;	 Catch:{ all -> 0x001c }
        r2.put(r3, r0);	 Catch:{ all -> 0x001c }
        goto L_0x001a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.comjni.engine.a.a(int, android.os.Handler):void");
    }

    public static void m2190b(int i, Handler handler) {
        synchronized (f2226b) {
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
                List list = (List) f2226b.get(i);
                if (list != null) {
                    list.remove(handler);
                }
            }
        }
    }
}

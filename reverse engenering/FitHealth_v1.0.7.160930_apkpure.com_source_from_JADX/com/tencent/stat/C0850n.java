package com.tencent.stat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Handler;
import android.os.HandlerThread;
import com.tencent.stat.common.C0837k;
import com.tencent.stat.common.StatLogger;
import com.tencent.stat.p021a.C0824e;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import com.zhuoyou.plugin.database.DataBaseContants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class C0850n {
    private static StatLogger f2933e = C0837k.m2718b();
    private static C0850n f2934f = null;
    Handler f2935a = null;
    volatile int f2936b = 0;
    DeviceInfo f2937c = null;
    private C0858w f2938d;
    private HashMap<String, String> f2939g = new HashMap();

    private C0850n(Context context) {
        try {
            HandlerThread handlerThread = new HandlerThread("StatStore");
            handlerThread.start();
            f2933e.m2683w("Launch store thread:" + handlerThread);
            this.f2935a = new Handler(handlerThread.getLooper());
            Context applicationContext = context.getApplicationContext();
            this.f2938d = new C0858w(applicationContext);
            this.f2938d.getWritableDatabase();
            this.f2938d.getReadableDatabase();
            m2802b(applicationContext);
            m2803c();
            m2795f();
            this.f2935a.post(new C0851o(this));
        } catch (Object th) {
            f2933e.m2680e(th);
        }
    }

    public static synchronized C0850n m2778a(Context context) {
        C0850n c0850n;
        synchronized (C0850n.class) {
            if (f2934f == null) {
                f2934f = new C0850n(context);
            }
            c0850n = f2934f;
        }
        return c0850n;
    }

    public static C0850n m2785b() {
        return f2934f;
    }

    private synchronized void m2787b(int i) {
        try {
            if (this.f2936b > 0 && i > 0) {
                f2933e.m2681i("Load " + Integer.toString(this.f2936b) + " unsent events");
                List arrayList = new ArrayList();
                List<C0859x> arrayList2 = new ArrayList();
                if (i == -1 || i > StatConfig.m2619a()) {
                    i = StatConfig.m2619a();
                }
                this.f2936b -= i;
                m2792c(arrayList2, i);
                f2933e.m2681i("Peek " + Integer.toString(arrayList2.size()) + " unsent events.");
                if (!arrayList2.isEmpty()) {
                    m2791b((List) arrayList2, 2);
                    for (C0859x c0859x : arrayList2) {
                        arrayList.add(c0859x.f2955b);
                    }
                    C0843d.m2768b().m2772b(arrayList, new C1748u(this, arrayList2, i));
                }
            }
        } catch (Object th) {
            f2933e.m2680e(th);
        }
    }

    private synchronized void m2788b(C0824e c0824e, C0828c c0828c) {
        if (StatConfig.getMaxStoreEventCount() > 0) {
            try {
                this.f2938d.getWritableDatabase().beginTransaction();
                if (this.f2936b > StatConfig.getMaxStoreEventCount()) {
                    f2933e.warn("Too many events stored in db.");
                    this.f2936b -= this.f2938d.getWritableDatabase().delete("events", "event_id in (select event_id from events where timestamp in (select min(timestamp) from events) limit 1)", null);
                }
                ContentValues contentValues = new ContentValues();
                String c = C0837k.m2723c(c0824e.m2662d());
                contentValues.put("content", c);
                contentValues.put("send_count", "0");
                contentValues.put("status", Integer.toString(1));
                contentValues.put(MessageObj.TIEMSTAMP, Long.valueOf(c0824e.m2659b()));
                if (this.f2938d.getWritableDatabase().insert("events", null, contentValues) == -1) {
                    f2933e.error("Failed to store event:" + c);
                } else {
                    this.f2936b++;
                    this.f2938d.getWritableDatabase().setTransactionSuccessful();
                    if (c0828c != null) {
                        c0828c.mo2144a();
                    }
                }
                try {
                    this.f2938d.getWritableDatabase().endTransaction();
                } catch (Throwable th) {
                }
            } catch (Throwable th2) {
            }
        }
        return;
    }

    private synchronized void m2789b(C0827b c0827b) {
        Cursor query;
        Throwable th;
        Object obj;
        try {
            long update;
            String a = c0827b.m2674a();
            String a2 = C0837k.m2712a(a);
            ContentValues contentValues = new ContentValues();
            contentValues.put("content", c0827b.f2868b.toString());
            contentValues.put("md5sum", a2);
            c0827b.f2869c = a2;
            contentValues.put("version", Integer.valueOf(c0827b.f2870d));
            query = this.f2938d.getReadableDatabase().query(DataBaseContants.TABLE_CONFIG_NAME, null, null, null, null, null, null);
            do {
                try {
                    if (!query.moveToNext()) {
                        obj = null;
                        break;
                    }
                } catch (Throwable th2) {
                    obj = th2;
                }
            } while (query.getInt(0) != c0827b.f2867a);
            obj = 1;
            if (1 == obj) {
                update = (long) this.f2938d.getWritableDatabase().update(DataBaseContants.TABLE_CONFIG_NAME, contentValues, "type=?", new String[]{Integer.toString(c0827b.f2867a)});
            } else {
                contentValues.put("type", Integer.valueOf(c0827b.f2867a));
                update = this.f2938d.getWritableDatabase().insert(DataBaseContants.TABLE_CONFIG_NAME, null, contentValues);
            }
            if (update == -1) {
                f2933e.m2680e("Failed to store cfg:" + a);
            } else {
                f2933e.m2678d("Sucessed to store cfg:" + a);
            }
            if (query != null) {
                query.close();
            }
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void m2790b(java.util.List<com.tencent.stat.C0859x> r11) {
        /*
        r10 = this;
        monitor-enter(r10);
        r0 = f2933e;	 Catch:{ all -> 0x009f }
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x009f }
        r1.<init>();	 Catch:{ all -> 0x009f }
        r2 = "Delete ";
        r1 = r1.append(r2);	 Catch:{ all -> 0x009f }
        r2 = r11.size();	 Catch:{ all -> 0x009f }
        r1 = r1.append(r2);	 Catch:{ all -> 0x009f }
        r2 = " sent events in thread:";
        r1 = r1.append(r2);	 Catch:{ all -> 0x009f }
        r2 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x009f }
        r1 = r1.append(r2);	 Catch:{ all -> 0x009f }
        r1 = r1.toString();	 Catch:{ all -> 0x009f }
        r0.m2681i(r1);	 Catch:{ all -> 0x009f }
        r0 = r10.f2938d;	 Catch:{ Throwable -> 0x0065 }
        r0 = r0.getWritableDatabase();	 Catch:{ Throwable -> 0x0065 }
        r0.beginTransaction();	 Catch:{ Throwable -> 0x0065 }
        r1 = r11.iterator();	 Catch:{ Throwable -> 0x0065 }
    L_0x0038:
        r0 = r1.hasNext();	 Catch:{ Throwable -> 0x0065 }
        if (r0 == 0) goto L_0x0076;
    L_0x003e:
        r0 = r1.next();	 Catch:{ Throwable -> 0x0065 }
        r0 = (com.tencent.stat.C0859x) r0;	 Catch:{ Throwable -> 0x0065 }
        r2 = r10.f2936b;	 Catch:{ Throwable -> 0x0065 }
        r3 = r10.f2938d;	 Catch:{ Throwable -> 0x0065 }
        r3 = r3.getWritableDatabase();	 Catch:{ Throwable -> 0x0065 }
        r4 = "events";
        r5 = "event_id = ?";
        r6 = 1;
        r6 = new java.lang.String[r6];	 Catch:{ Throwable -> 0x0065 }
        r7 = 0;
        r8 = r0.f2954a;	 Catch:{ Throwable -> 0x0065 }
        r0 = java.lang.Long.toString(r8);	 Catch:{ Throwable -> 0x0065 }
        r6[r7] = r0;	 Catch:{ Throwable -> 0x0065 }
        r0 = r3.delete(r4, r5, r6);	 Catch:{ Throwable -> 0x0065 }
        r0 = r2 - r0;
        r10.f2936b = r0;	 Catch:{ Throwable -> 0x0065 }
        goto L_0x0038;
    L_0x0065:
        r0 = move-exception;
        r1 = f2933e;	 Catch:{ all -> 0x00a9 }
        r1.m2680e(r0);	 Catch:{ all -> 0x00a9 }
        r0 = r10.f2938d;	 Catch:{ SQLiteException -> 0x00a2 }
        r0 = r0.getWritableDatabase();	 Catch:{ SQLiteException -> 0x00a2 }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x00a2 }
    L_0x0074:
        monitor-exit(r10);
        return;
    L_0x0076:
        r0 = r10.f2938d;	 Catch:{ Throwable -> 0x0065 }
        r0 = r0.getWritableDatabase();	 Catch:{ Throwable -> 0x0065 }
        r0.setTransactionSuccessful();	 Catch:{ Throwable -> 0x0065 }
        r0 = r10.f2938d;	 Catch:{ Throwable -> 0x0065 }
        r0 = r0.getReadableDatabase();	 Catch:{ Throwable -> 0x0065 }
        r1 = "events";
        r0 = android.database.DatabaseUtils.queryNumEntries(r0, r1);	 Catch:{ Throwable -> 0x0065 }
        r0 = (int) r0;	 Catch:{ Throwable -> 0x0065 }
        r10.f2936b = r0;	 Catch:{ Throwable -> 0x0065 }
        r0 = r10.f2938d;	 Catch:{ SQLiteException -> 0x0098 }
        r0 = r0.getWritableDatabase();	 Catch:{ SQLiteException -> 0x0098 }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x0098 }
        goto L_0x0074;
    L_0x0098:
        r0 = move-exception;
        r1 = f2933e;	 Catch:{ all -> 0x009f }
        r1.m2679e(r0);	 Catch:{ all -> 0x009f }
        goto L_0x0074;
    L_0x009f:
        r0 = move-exception;
        monitor-exit(r10);
        throw r0;
    L_0x00a2:
        r0 = move-exception;
        r1 = f2933e;	 Catch:{ all -> 0x009f }
        r1.m2679e(r0);	 Catch:{ all -> 0x009f }
        goto L_0x0074;
    L_0x00a9:
        r0 = move-exception;
        r1 = r10.f2938d;	 Catch:{ SQLiteException -> 0x00b4 }
        r1 = r1.getWritableDatabase();	 Catch:{ SQLiteException -> 0x00b4 }
        r1.endTransaction();	 Catch:{ SQLiteException -> 0x00b4 }
    L_0x00b3:
        throw r0;	 Catch:{ all -> 0x009f }
    L_0x00b4:
        r1 = move-exception;
        r2 = f2933e;	 Catch:{ all -> 0x009f }
        r2.m2679e(r1);	 Catch:{ all -> 0x009f }
        goto L_0x00b3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.stat.n.b(java.util.List):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void m2791b(java.util.List<com.tencent.stat.C0859x> r13, int r14) {
        /*
        r12 = this;
        monitor-enter(r12);
        r0 = f2933e;	 Catch:{ all -> 0x010e }
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x010e }
        r1.<init>();	 Catch:{ all -> 0x010e }
        r2 = "Update ";
        r1 = r1.append(r2);	 Catch:{ all -> 0x010e }
        r2 = r13.size();	 Catch:{ all -> 0x010e }
        r1 = r1.append(r2);	 Catch:{ all -> 0x010e }
        r2 = " sending events to status:";
        r1 = r1.append(r2);	 Catch:{ all -> 0x010e }
        r1 = r1.append(r14);	 Catch:{ all -> 0x010e }
        r2 = " in thread:";
        r1 = r1.append(r2);	 Catch:{ all -> 0x010e }
        r2 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x010e }
        r1 = r1.append(r2);	 Catch:{ all -> 0x010e }
        r1 = r1.toString();	 Catch:{ all -> 0x010e }
        r0.m2681i(r1);	 Catch:{ all -> 0x010e }
        r1 = new android.content.ContentValues;	 Catch:{ Throwable -> 0x0087 }
        r1.<init>();	 Catch:{ Throwable -> 0x0087 }
        r0 = "status";
        r2 = java.lang.Integer.toString(r14);	 Catch:{ Throwable -> 0x0087 }
        r1.put(r0, r2);	 Catch:{ Throwable -> 0x0087 }
        r0 = r12.f2938d;	 Catch:{ Throwable -> 0x0087 }
        r0 = r0.getWritableDatabase();	 Catch:{ Throwable -> 0x0087 }
        r0.beginTransaction();	 Catch:{ Throwable -> 0x0087 }
        r2 = r13.iterator();	 Catch:{ Throwable -> 0x0087 }
    L_0x0050:
        r0 = r2.hasNext();	 Catch:{ Throwable -> 0x0087 }
        if (r0 == 0) goto L_0x0111;
    L_0x0056:
        r0 = r2.next();	 Catch:{ Throwable -> 0x0087 }
        r0 = (com.tencent.stat.C0859x) r0;	 Catch:{ Throwable -> 0x0087 }
        r3 = r0.f2957d;	 Catch:{ Throwable -> 0x0087 }
        r3 = r3 + 1;
        r4 = com.tencent.stat.StatConfig.getMaxSendRetryCount();	 Catch:{ Throwable -> 0x0087 }
        if (r3 <= r4) goto L_0x0098;
    L_0x0066:
        r3 = r12.f2936b;	 Catch:{ Throwable -> 0x0087 }
        r4 = r12.f2938d;	 Catch:{ Throwable -> 0x0087 }
        r4 = r4.getWritableDatabase();	 Catch:{ Throwable -> 0x0087 }
        r5 = "events";
        r6 = "event_id=?";
        r7 = 1;
        r7 = new java.lang.String[r7];	 Catch:{ Throwable -> 0x0087 }
        r8 = 0;
        r10 = r0.f2954a;	 Catch:{ Throwable -> 0x0087 }
        r0 = java.lang.Long.toString(r10);	 Catch:{ Throwable -> 0x0087 }
        r7[r8] = r0;	 Catch:{ Throwable -> 0x0087 }
        r0 = r4.delete(r5, r6, r7);	 Catch:{ Throwable -> 0x0087 }
        r0 = r3 - r0;
        r12.f2936b = r0;	 Catch:{ Throwable -> 0x0087 }
        goto L_0x0050;
    L_0x0087:
        r0 = move-exception;
        r1 = f2933e;	 Catch:{ all -> 0x0103 }
        r1.m2680e(r0);	 Catch:{ all -> 0x0103 }
        r0 = r12.f2938d;	 Catch:{ SQLiteException -> 0x013c }
        r0 = r0.getWritableDatabase();	 Catch:{ SQLiteException -> 0x013c }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x013c }
    L_0x0096:
        monitor-exit(r12);
        return;
    L_0x0098:
        r3 = "send_count";
        r4 = r0.f2957d;	 Catch:{ Throwable -> 0x0087 }
        r4 = r4 + 1;
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ Throwable -> 0x0087 }
        r1.put(r3, r4);	 Catch:{ Throwable -> 0x0087 }
        r3 = f2933e;	 Catch:{ Throwable -> 0x0087 }
        r4 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0087 }
        r4.<init>();	 Catch:{ Throwable -> 0x0087 }
        r5 = "Update event:";
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x0087 }
        r6 = r0.f2954a;	 Catch:{ Throwable -> 0x0087 }
        r4 = r4.append(r6);	 Catch:{ Throwable -> 0x0087 }
        r5 = " for content:";
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x0087 }
        r4 = r4.append(r1);	 Catch:{ Throwable -> 0x0087 }
        r4 = r4.toString();	 Catch:{ Throwable -> 0x0087 }
        r3.m2681i(r4);	 Catch:{ Throwable -> 0x0087 }
        r3 = r12.f2938d;	 Catch:{ Throwable -> 0x0087 }
        r3 = r3.getWritableDatabase();	 Catch:{ Throwable -> 0x0087 }
        r4 = "events";
        r5 = "event_id=?";
        r6 = 1;
        r6 = new java.lang.String[r6];	 Catch:{ Throwable -> 0x0087 }
        r7 = 0;
        r8 = r0.f2954a;	 Catch:{ Throwable -> 0x0087 }
        r0 = java.lang.Long.toString(r8);	 Catch:{ Throwable -> 0x0087 }
        r6[r7] = r0;	 Catch:{ Throwable -> 0x0087 }
        r0 = r3.update(r4, r1, r5, r6);	 Catch:{ Throwable -> 0x0087 }
        if (r0 > 0) goto L_0x0050;
    L_0x00e5:
        r3 = f2933e;	 Catch:{ Throwable -> 0x0087 }
        r4 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0087 }
        r4.<init>();	 Catch:{ Throwable -> 0x0087 }
        r5 = "Failed to update db, error code:";
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x0087 }
        r0 = java.lang.Integer.toString(r0);	 Catch:{ Throwable -> 0x0087 }
        r0 = r4.append(r0);	 Catch:{ Throwable -> 0x0087 }
        r0 = r0.toString();	 Catch:{ Throwable -> 0x0087 }
        r3.m2680e(r0);	 Catch:{ Throwable -> 0x0087 }
        goto L_0x0050;
    L_0x0103:
        r0 = move-exception;
        r1 = r12.f2938d;	 Catch:{ SQLiteException -> 0x0144 }
        r1 = r1.getWritableDatabase();	 Catch:{ SQLiteException -> 0x0144 }
        r1.endTransaction();	 Catch:{ SQLiteException -> 0x0144 }
    L_0x010d:
        throw r0;	 Catch:{ all -> 0x010e }
    L_0x010e:
        r0 = move-exception;
        monitor-exit(r12);
        throw r0;
    L_0x0111:
        r0 = r12.f2938d;	 Catch:{ Throwable -> 0x0087 }
        r0 = r0.getWritableDatabase();	 Catch:{ Throwable -> 0x0087 }
        r0.setTransactionSuccessful();	 Catch:{ Throwable -> 0x0087 }
        r0 = r12.f2938d;	 Catch:{ Throwable -> 0x0087 }
        r0 = r0.getReadableDatabase();	 Catch:{ Throwable -> 0x0087 }
        r1 = "events";
        r0 = android.database.DatabaseUtils.queryNumEntries(r0, r1);	 Catch:{ Throwable -> 0x0087 }
        r0 = (int) r0;	 Catch:{ Throwable -> 0x0087 }
        r12.f2936b = r0;	 Catch:{ Throwable -> 0x0087 }
        r0 = r12.f2938d;	 Catch:{ SQLiteException -> 0x0134 }
        r0 = r0.getWritableDatabase();	 Catch:{ SQLiteException -> 0x0134 }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x0134 }
        goto L_0x0096;
    L_0x0134:
        r0 = move-exception;
        r1 = f2933e;	 Catch:{ all -> 0x010e }
        r1.m2679e(r0);	 Catch:{ all -> 0x010e }
        goto L_0x0096;
    L_0x013c:
        r0 = move-exception;
        r1 = f2933e;	 Catch:{ all -> 0x010e }
        r1.m2679e(r0);	 Catch:{ all -> 0x010e }
        goto L_0x0096;
    L_0x0144:
        r1 = move-exception;
        r2 = f2933e;	 Catch:{ all -> 0x010e }
        r2.m2679e(r1);	 Catch:{ all -> 0x010e }
        goto L_0x010d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.stat.n.b(java.util.List, int):void");
    }

    private void m2792c(List<C0859x> list, int i) {
        Object th;
        Cursor cursor;
        Throwable th2;
        Cursor cursor2 = null;
        try {
            Cursor query = this.f2938d.getReadableDatabase().query("events", null, "status=?", new String[]{Integer.toString(1)}, null, null, "event_id", Integer.toString(i));
            while (query.moveToNext()) {
                try {
                    list.add(new C0859x(query.getLong(0), C0837k.m2726d(query.getString(1)), query.getInt(2), query.getInt(3)));
                } catch (Throwable th3) {
                    th2 = th3;
                    cursor2 = query;
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Throwable th4) {
            th2 = th4;
            if (cursor2 != null) {
                cursor2.close();
            }
            throw th2;
        }
    }

    private void m2794e() {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("status", Integer.valueOf(1));
            this.f2938d.getWritableDatabase().update("events", contentValues, "status=?", new String[]{Long.toString(2)});
            this.f2936b = (int) DatabaseUtils.queryNumEntries(this.f2938d.getReadableDatabase(), "events");
            f2933e.m2681i("Total " + this.f2936b + " unsent events.");
        } catch (Object th) {
            f2933e.m2680e(th);
        }
    }

    private void m2795f() {
        Object th;
        Throwable th2;
        Cursor query;
        try {
            query = this.f2938d.getReadableDatabase().query("keyvalues", null, null, null, null, null, null);
            while (query.moveToNext()) {
                try {
                    this.f2939g.put(query.getString(0), query.getString(1));
                } catch (Throwable th3) {
                    th = th3;
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Throwable th4) {
            th2 = th4;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th2;
        }
    }

    public int m2796a() {
        return this.f2936b;
    }

    void m2797a(int i) {
        this.f2935a.post(new C0857v(this, i));
    }

    void m2798a(C0824e c0824e, C0828c c0828c) {
        if (StatConfig.isEnableStatService()) {
            try {
                if (Thread.currentThread().getId() == this.f2935a.getLooper().getThread().getId()) {
                    m2788b(c0824e, c0828c);
                } else {
                    this.f2935a.post(new C0854r(this, c0824e, c0828c));
                }
            } catch (Object th) {
                f2933e.m2680e(th);
            }
        }
    }

    void m2799a(C0827b c0827b) {
        if (c0827b != null) {
            this.f2935a.post(new C0855s(this, c0827b));
        }
    }

    void m2800a(List<C0859x> list) {
        try {
            if (Thread.currentThread().getId() == this.f2935a.getLooper().getThread().getId()) {
                m2790b((List) list);
            } else {
                this.f2935a.post(new C0853q(this, list));
            }
        } catch (Exception e) {
            f2933e.m2679e(e);
        }
    }

    void m2801a(List<C0859x> list, int i) {
        try {
            if (Thread.currentThread().getId() == this.f2935a.getLooper().getThread().getId()) {
                m2791b((List) list, i);
            } else {
                this.f2935a.post(new C0852p(this, list, i));
            }
        } catch (Object th) {
            f2933e.m2680e(th);
        }
    }

    public synchronized DeviceInfo m2802b(Context context) {
        DeviceInfo deviceInfo;
        Cursor cursor;
        Throwable th;
        if (this.f2937c != null) {
            deviceInfo = this.f2937c;
        } else {
            Cursor query;
            Object obj;
            try {
                query = this.f2938d.getReadableDatabase().query("user", null, null, null, null, null, null, null);
                obj = null;
                try {
                    String d;
                    String str;
                    String l;
                    String str2 = "";
                    if (query.moveToNext()) {
                        Object obj2;
                        d = C0837k.m2726d(query.getString(0));
                        int i = query.getInt(1);
                        str2 = query.getString(2);
                        long currentTimeMillis = System.currentTimeMillis() / 1000;
                        int i2 = (i == 1 || C0837k.m2711a(query.getLong(3) * 1000).equals(C0837k.m2711a(1000 * currentTimeMillis))) ? i : 1;
                        int i3 = !str2.equals(C0837k.m2745r(context)) ? i2 | 2 : i2;
                        String[] split = d.split(SeparatorConstants.SEPARATOR_ADS_ID);
                        if (split == null || split.length <= 0) {
                            str2 = C0837k.m2719b(context);
                            d = str2;
                            str = str2;
                            int i4 = 1;
                        } else {
                            str2 = split[0];
                            if (str2 == null || str2.length() < 11) {
                                l = C0837k.m2739l(context);
                                if (l == null || l.length() <= 10) {
                                    l = str2;
                                    obj2 = null;
                                } else {
                                    obj2 = 1;
                                }
                                str = d;
                                d = l;
                            } else {
                                String str3 = str2;
                                obj2 = null;
                                str = d;
                                d = str3;
                            }
                        }
                        if (split == null || split.length < 2) {
                            l = C0837k.m2722c(context);
                            if (l != null && l.length() > 0) {
                                str = d + SeparatorConstants.SEPARATOR_ADS_ID + l;
                                obj2 = 1;
                            }
                        } else {
                            l = split[1];
                            str = d + SeparatorConstants.SEPARATOR_ADS_ID + l;
                        }
                        this.f2937c = new DeviceInfo(d, l, i3);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("uid", C0837k.m2723c(str));
                        contentValues.put("user_type", Integer.valueOf(i3));
                        contentValues.put("app_ver", C0837k.m2745r(context));
                        contentValues.put("ts", Long.valueOf(currentTimeMillis));
                        if (obj2 != null) {
                            this.f2938d.getWritableDatabase().update("user", contentValues, "uid=?", new String[]{r10});
                        }
                        if (i3 != i) {
                            this.f2938d.getWritableDatabase().replace("user", null, contentValues);
                            obj = 1;
                        } else {
                            i2 = 1;
                        }
                    }
                    if (obj == null) {
                        str2 = C0837k.m2719b(context);
                        str = C0837k.m2722c(context);
                        l = (str == null || str.length() <= 0) ? str2 : str2 + SeparatorConstants.SEPARATOR_ADS_ID + str;
                        long currentTimeMillis2 = System.currentTimeMillis() / 1000;
                        d = C0837k.m2745r(context);
                        ContentValues contentValues2 = new ContentValues();
                        contentValues2.put("uid", C0837k.m2723c(l));
                        contentValues2.put("user_type", Integer.valueOf(0));
                        contentValues2.put("app_ver", d);
                        contentValues2.put("ts", Long.valueOf(currentTimeMillis2));
                        this.f2938d.getWritableDatabase().insert("user", null, contentValues2);
                        this.f2937c = new DeviceInfo(str2, str, 0);
                    }
                    if (query != null) {
                        query.close();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (query != null) {
                        query.close();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                query = null;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
            deviceInfo = this.f2937c;
        }
        return deviceInfo;
    }

    void m2803c() {
        this.f2935a.post(new C0856t(this));
    }
}

package com.tencent.stat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Handler;
import android.os.HandlerThread;
import com.droi.btlib.connection.MessageObj;
import com.droi.sdk.core.priv.PersistSettings;
import com.tencent.stat.common.C1389k;
import com.tencent.stat.common.StatLogger;
import com.tencent.stat.p039a.C1365e;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class C1405n {
    private static StatLogger f4471e = C1389k.m4125b();
    private static C1405n f4472f = null;
    Handler f4473a = null;
    volatile int f4474b = 0;
    DeviceInfo f4475c = null;
    private C1414w f4476d;
    private HashMap<String, String> f4477g = new HashMap();

    private C1405n(Context context) {
        try {
            HandlerThread handlerThread = new HandlerThread("StatStore");
            handlerThread.start();
            f4471e.m4088w("Launch store thread:" + handlerThread);
            this.f4473a = new Handler(handlerThread.getLooper());
            Context applicationContext = context.getApplicationContext();
            this.f4476d = new C1414w(applicationContext);
            this.f4476d.getWritableDatabase();
            this.f4476d.getReadableDatabase();
            m4213b(applicationContext);
            m4214c();
            m4206f();
            this.f4473a.post(new C1406o(this));
        } catch (Object th) {
            f4471e.m4085e(th);
        }
    }

    public static synchronized C1405n m4189a(Context context) {
        C1405n c1405n;
        synchronized (C1405n.class) {
            if (f4472f == null) {
                f4472f = new C1405n(context);
            }
            c1405n = f4472f;
        }
        return c1405n;
    }

    public static C1405n m4196b() {
        return f4472f;
    }

    private synchronized void m4198b(int i) {
        try {
            if (this.f4474b > 0 && i > 0) {
                f4471e.m4086i("Load " + Integer.toString(this.f4474b) + " unsent events");
                List arrayList = new ArrayList();
                List<C1415x> arrayList2 = new ArrayList();
                if (i == -1 || i > StatConfig.m4003a()) {
                    i = StatConfig.m4003a();
                }
                this.f4474b -= i;
                m4203c(arrayList2, i);
                f4471e.m4086i("Peek " + Integer.toString(arrayList2.size()) + " unsent events.");
                if (!arrayList2.isEmpty()) {
                    m4202b((List) arrayList2, 2);
                    for (C1415x c1415x : arrayList2) {
                        arrayList.add(c1415x.f4496b);
                    }
                    C1395d.m4175b().m4179b(arrayList, new C1412u(this, arrayList2, i));
                }
            }
        } catch (Object th) {
            f4471e.m4085e(th);
        }
    }

    private synchronized void m4199b(C1365e c1365e, C1378c c1378c) {
        if (StatConfig.getMaxStoreEventCount() > 0) {
            try {
                this.f4476d.getWritableDatabase().beginTransaction();
                if (this.f4474b > StatConfig.getMaxStoreEventCount()) {
                    f4471e.warn("Too many events stored in db.");
                    this.f4474b -= this.f4476d.getWritableDatabase().delete("events", "event_id in (select event_id from events where timestamp in (select min(timestamp) from events) limit 1)", null);
                }
                ContentValues contentValues = new ContentValues();
                String c = C1389k.m4130c(c1365e.m4046d());
                contentValues.put("content", c);
                contentValues.put("send_count", "0");
                contentValues.put("status", Integer.toString(1));
                contentValues.put(MessageObj.TIEMSTAMP, Long.valueOf(c1365e.m4043b()));
                if (this.f4476d.getWritableDatabase().insert("events", null, contentValues) == -1) {
                    f4471e.error("Failed to store event:" + c);
                } else {
                    this.f4474b++;
                    this.f4476d.getWritableDatabase().setTransactionSuccessful();
                    if (c1378c != null) {
                        c1378c.mo2221a();
                    }
                }
                try {
                    this.f4476d.getWritableDatabase().endTransaction();
                } catch (Throwable th) {
                }
            } catch (Throwable th2) {
            }
        }
        return;
    }

    private synchronized void m4200b(C1377b c1377b) {
        Throwable th;
        Cursor query;
        Object obj;
        try {
            long update;
            String a = c1377b.m4079a();
            String a2 = C1389k.m4119a(a);
            ContentValues contentValues = new ContentValues();
            contentValues.put("content", c1377b.f4388b.toString());
            contentValues.put("md5sum", a2);
            c1377b.f4389c = a2;
            contentValues.put("version", Integer.valueOf(c1377b.f4390d));
            query = this.f4476d.getReadableDatabase().query(PersistSettings.CONFIG, null, null, null, null, null, null);
            do {
                try {
                    if (!query.moveToNext()) {
                        obj = null;
                        break;
                    }
                } catch (Throwable th2) {
                    obj = th2;
                }
            } while (query.getInt(0) != c1377b.f4387a);
            obj = 1;
            if (1 == obj) {
                update = (long) this.f4476d.getWritableDatabase().update(PersistSettings.CONFIG, contentValues, "type=?", new String[]{Integer.toString(c1377b.f4387a)});
            } else {
                contentValues.put("type", Integer.valueOf(c1377b.f4387a));
                update = this.f4476d.getWritableDatabase().insert(PersistSettings.CONFIG, null, contentValues);
            }
            if (update == -1) {
                f4471e.m4085e("Failed to store cfg:" + a);
            } else {
                f4471e.m4083d("Sucessed to store cfg:" + a);
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
    private synchronized void m4201b(java.util.List<com.tencent.stat.C1415x> r11) {
        /*
        r10 = this;
        monitor-enter(r10);
        r0 = f4471e;	 Catch:{ all -> 0x009f }
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
        r0.m4086i(r1);	 Catch:{ all -> 0x009f }
        r0 = r10.f4476d;	 Catch:{ Throwable -> 0x0065 }
        r0 = r0.getWritableDatabase();	 Catch:{ Throwable -> 0x0065 }
        r0.beginTransaction();	 Catch:{ Throwable -> 0x0065 }
        r1 = r11.iterator();	 Catch:{ Throwable -> 0x0065 }
    L_0x0038:
        r0 = r1.hasNext();	 Catch:{ Throwable -> 0x0065 }
        if (r0 == 0) goto L_0x0076;
    L_0x003e:
        r0 = r1.next();	 Catch:{ Throwable -> 0x0065 }
        r0 = (com.tencent.stat.C1415x) r0;	 Catch:{ Throwable -> 0x0065 }
        r2 = r10.f4474b;	 Catch:{ Throwable -> 0x0065 }
        r3 = r10.f4476d;	 Catch:{ Throwable -> 0x0065 }
        r3 = r3.getWritableDatabase();	 Catch:{ Throwable -> 0x0065 }
        r4 = "events";
        r5 = "event_id = ?";
        r6 = 1;
        r6 = new java.lang.String[r6];	 Catch:{ Throwable -> 0x0065 }
        r7 = 0;
        r8 = r0.f4495a;	 Catch:{ Throwable -> 0x0065 }
        r0 = java.lang.Long.toString(r8);	 Catch:{ Throwable -> 0x0065 }
        r6[r7] = r0;	 Catch:{ Throwable -> 0x0065 }
        r0 = r3.delete(r4, r5, r6);	 Catch:{ Throwable -> 0x0065 }
        r0 = r2 - r0;
        r10.f4474b = r0;	 Catch:{ Throwable -> 0x0065 }
        goto L_0x0038;
    L_0x0065:
        r0 = move-exception;
        r1 = f4471e;	 Catch:{ all -> 0x00a9 }
        r1.m4085e(r0);	 Catch:{ all -> 0x00a9 }
        r0 = r10.f4476d;	 Catch:{ SQLiteException -> 0x00a2 }
        r0 = r0.getWritableDatabase();	 Catch:{ SQLiteException -> 0x00a2 }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x00a2 }
    L_0x0074:
        monitor-exit(r10);
        return;
    L_0x0076:
        r0 = r10.f4476d;	 Catch:{ Throwable -> 0x0065 }
        r0 = r0.getWritableDatabase();	 Catch:{ Throwable -> 0x0065 }
        r0.setTransactionSuccessful();	 Catch:{ Throwable -> 0x0065 }
        r0 = r10.f4476d;	 Catch:{ Throwable -> 0x0065 }
        r0 = r0.getReadableDatabase();	 Catch:{ Throwable -> 0x0065 }
        r1 = "events";
        r0 = android.database.DatabaseUtils.queryNumEntries(r0, r1);	 Catch:{ Throwable -> 0x0065 }
        r0 = (int) r0;	 Catch:{ Throwable -> 0x0065 }
        r10.f4474b = r0;	 Catch:{ Throwable -> 0x0065 }
        r0 = r10.f4476d;	 Catch:{ SQLiteException -> 0x0098 }
        r0 = r0.getWritableDatabase();	 Catch:{ SQLiteException -> 0x0098 }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x0098 }
        goto L_0x0074;
    L_0x0098:
        r0 = move-exception;
        r1 = f4471e;	 Catch:{ all -> 0x009f }
        r1.m4084e(r0);	 Catch:{ all -> 0x009f }
        goto L_0x0074;
    L_0x009f:
        r0 = move-exception;
        monitor-exit(r10);
        throw r0;
    L_0x00a2:
        r0 = move-exception;
        r1 = f4471e;	 Catch:{ all -> 0x009f }
        r1.m4084e(r0);	 Catch:{ all -> 0x009f }
        goto L_0x0074;
    L_0x00a9:
        r0 = move-exception;
        r1 = r10.f4476d;	 Catch:{ SQLiteException -> 0x00b4 }
        r1 = r1.getWritableDatabase();	 Catch:{ SQLiteException -> 0x00b4 }
        r1.endTransaction();	 Catch:{ SQLiteException -> 0x00b4 }
    L_0x00b3:
        throw r0;	 Catch:{ all -> 0x009f }
    L_0x00b4:
        r1 = move-exception;
        r2 = f4471e;	 Catch:{ all -> 0x009f }
        r2.m4084e(r1);	 Catch:{ all -> 0x009f }
        goto L_0x00b3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.stat.n.b(java.util.List):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void m4202b(java.util.List<com.tencent.stat.C1415x> r13, int r14) {
        /*
        r12 = this;
        monitor-enter(r12);
        r0 = f4471e;	 Catch:{ all -> 0x010e }
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
        r0.m4086i(r1);	 Catch:{ all -> 0x010e }
        r1 = new android.content.ContentValues;	 Catch:{ Throwable -> 0x0087 }
        r1.<init>();	 Catch:{ Throwable -> 0x0087 }
        r0 = "status";
        r2 = java.lang.Integer.toString(r14);	 Catch:{ Throwable -> 0x0087 }
        r1.put(r0, r2);	 Catch:{ Throwable -> 0x0087 }
        r0 = r12.f4476d;	 Catch:{ Throwable -> 0x0087 }
        r0 = r0.getWritableDatabase();	 Catch:{ Throwable -> 0x0087 }
        r0.beginTransaction();	 Catch:{ Throwable -> 0x0087 }
        r2 = r13.iterator();	 Catch:{ Throwable -> 0x0087 }
    L_0x0050:
        r0 = r2.hasNext();	 Catch:{ Throwable -> 0x0087 }
        if (r0 == 0) goto L_0x0111;
    L_0x0056:
        r0 = r2.next();	 Catch:{ Throwable -> 0x0087 }
        r0 = (com.tencent.stat.C1415x) r0;	 Catch:{ Throwable -> 0x0087 }
        r3 = r0.f4498d;	 Catch:{ Throwable -> 0x0087 }
        r3 = r3 + 1;
        r4 = com.tencent.stat.StatConfig.getMaxSendRetryCount();	 Catch:{ Throwable -> 0x0087 }
        if (r3 <= r4) goto L_0x0098;
    L_0x0066:
        r3 = r12.f4474b;	 Catch:{ Throwable -> 0x0087 }
        r4 = r12.f4476d;	 Catch:{ Throwable -> 0x0087 }
        r4 = r4.getWritableDatabase();	 Catch:{ Throwable -> 0x0087 }
        r5 = "events";
        r6 = "event_id=?";
        r7 = 1;
        r7 = new java.lang.String[r7];	 Catch:{ Throwable -> 0x0087 }
        r8 = 0;
        r10 = r0.f4495a;	 Catch:{ Throwable -> 0x0087 }
        r0 = java.lang.Long.toString(r10);	 Catch:{ Throwable -> 0x0087 }
        r7[r8] = r0;	 Catch:{ Throwable -> 0x0087 }
        r0 = r4.delete(r5, r6, r7);	 Catch:{ Throwable -> 0x0087 }
        r0 = r3 - r0;
        r12.f4474b = r0;	 Catch:{ Throwable -> 0x0087 }
        goto L_0x0050;
    L_0x0087:
        r0 = move-exception;
        r1 = f4471e;	 Catch:{ all -> 0x0103 }
        r1.m4085e(r0);	 Catch:{ all -> 0x0103 }
        r0 = r12.f4476d;	 Catch:{ SQLiteException -> 0x013c }
        r0 = r0.getWritableDatabase();	 Catch:{ SQLiteException -> 0x013c }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x013c }
    L_0x0096:
        monitor-exit(r12);
        return;
    L_0x0098:
        r3 = "send_count";
        r4 = r0.f4498d;	 Catch:{ Throwable -> 0x0087 }
        r4 = r4 + 1;
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ Throwable -> 0x0087 }
        r1.put(r3, r4);	 Catch:{ Throwable -> 0x0087 }
        r3 = f4471e;	 Catch:{ Throwable -> 0x0087 }
        r4 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0087 }
        r4.<init>();	 Catch:{ Throwable -> 0x0087 }
        r5 = "Update event:";
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x0087 }
        r6 = r0.f4495a;	 Catch:{ Throwable -> 0x0087 }
        r4 = r4.append(r6);	 Catch:{ Throwable -> 0x0087 }
        r5 = " for content:";
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x0087 }
        r4 = r4.append(r1);	 Catch:{ Throwable -> 0x0087 }
        r4 = r4.toString();	 Catch:{ Throwable -> 0x0087 }
        r3.m4086i(r4);	 Catch:{ Throwable -> 0x0087 }
        r3 = r12.f4476d;	 Catch:{ Throwable -> 0x0087 }
        r3 = r3.getWritableDatabase();	 Catch:{ Throwable -> 0x0087 }
        r4 = "events";
        r5 = "event_id=?";
        r6 = 1;
        r6 = new java.lang.String[r6];	 Catch:{ Throwable -> 0x0087 }
        r7 = 0;
        r8 = r0.f4495a;	 Catch:{ Throwable -> 0x0087 }
        r0 = java.lang.Long.toString(r8);	 Catch:{ Throwable -> 0x0087 }
        r6[r7] = r0;	 Catch:{ Throwable -> 0x0087 }
        r0 = r3.update(r4, r1, r5, r6);	 Catch:{ Throwable -> 0x0087 }
        if (r0 > 0) goto L_0x0050;
    L_0x00e5:
        r3 = f4471e;	 Catch:{ Throwable -> 0x0087 }
        r4 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0087 }
        r4.<init>();	 Catch:{ Throwable -> 0x0087 }
        r5 = "Failed to update db, error code:";
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x0087 }
        r0 = java.lang.Integer.toString(r0);	 Catch:{ Throwable -> 0x0087 }
        r0 = r4.append(r0);	 Catch:{ Throwable -> 0x0087 }
        r0 = r0.toString();	 Catch:{ Throwable -> 0x0087 }
        r3.m4085e(r0);	 Catch:{ Throwable -> 0x0087 }
        goto L_0x0050;
    L_0x0103:
        r0 = move-exception;
        r1 = r12.f4476d;	 Catch:{ SQLiteException -> 0x0144 }
        r1 = r1.getWritableDatabase();	 Catch:{ SQLiteException -> 0x0144 }
        r1.endTransaction();	 Catch:{ SQLiteException -> 0x0144 }
    L_0x010d:
        throw r0;	 Catch:{ all -> 0x010e }
    L_0x010e:
        r0 = move-exception;
        monitor-exit(r12);
        throw r0;
    L_0x0111:
        r0 = r12.f4476d;	 Catch:{ Throwable -> 0x0087 }
        r0 = r0.getWritableDatabase();	 Catch:{ Throwable -> 0x0087 }
        r0.setTransactionSuccessful();	 Catch:{ Throwable -> 0x0087 }
        r0 = r12.f4476d;	 Catch:{ Throwable -> 0x0087 }
        r0 = r0.getReadableDatabase();	 Catch:{ Throwable -> 0x0087 }
        r1 = "events";
        r0 = android.database.DatabaseUtils.queryNumEntries(r0, r1);	 Catch:{ Throwable -> 0x0087 }
        r0 = (int) r0;	 Catch:{ Throwable -> 0x0087 }
        r12.f4474b = r0;	 Catch:{ Throwable -> 0x0087 }
        r0 = r12.f4476d;	 Catch:{ SQLiteException -> 0x0134 }
        r0 = r0.getWritableDatabase();	 Catch:{ SQLiteException -> 0x0134 }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x0134 }
        goto L_0x0096;
    L_0x0134:
        r0 = move-exception;
        r1 = f4471e;	 Catch:{ all -> 0x010e }
        r1.m4084e(r0);	 Catch:{ all -> 0x010e }
        goto L_0x0096;
    L_0x013c:
        r0 = move-exception;
        r1 = f4471e;	 Catch:{ all -> 0x010e }
        r1.m4084e(r0);	 Catch:{ all -> 0x010e }
        goto L_0x0096;
    L_0x0144:
        r1 = move-exception;
        r2 = f4471e;	 Catch:{ all -> 0x010e }
        r2.m4084e(r1);	 Catch:{ all -> 0x010e }
        goto L_0x010d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.stat.n.b(java.util.List, int):void");
    }

    private void m4203c(List<C1415x> list, int i) {
        Object th;
        Cursor cursor;
        Throwable th2;
        Cursor cursor2 = null;
        try {
            Cursor query = this.f4476d.getReadableDatabase().query("events", null, "status=?", new String[]{Integer.toString(1)}, null, null, "event_id", Integer.toString(i));
            while (query.moveToNext()) {
                try {
                    list.add(new C1415x(query.getLong(0), C1389k.m4133d(query.getString(1)), query.getInt(2), query.getInt(3)));
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

    private void m4205e() {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("status", Integer.valueOf(1));
            this.f4476d.getWritableDatabase().update("events", contentValues, "status=?", new String[]{Long.toString(2)});
            this.f4474b = (int) DatabaseUtils.queryNumEntries(this.f4476d.getReadableDatabase(), "events");
            f4471e.m4086i("Total " + this.f4474b + " unsent events.");
        } catch (Object th) {
            f4471e.m4085e(th);
        }
    }

    private void m4206f() {
        Cursor query;
        Object th;
        Throwable th2;
        try {
            query = this.f4476d.getReadableDatabase().query("keyvalues", null, null, null, null, null, null);
            while (query.moveToNext()) {
                try {
                    this.f4477g.put(query.getString(0), query.getString(1));
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

    public int m4207a() {
        return this.f4474b;
    }

    void m4208a(int i) {
        this.f4473a.post(new C1413v(this, i));
    }

    void m4209a(C1365e c1365e, C1378c c1378c) {
        if (StatConfig.isEnableStatService()) {
            try {
                if (Thread.currentThread().getId() == this.f4473a.getLooper().getThread().getId()) {
                    m4199b(c1365e, c1378c);
                } else {
                    this.f4473a.post(new C1409r(this, c1365e, c1378c));
                }
            } catch (Object th) {
                f4471e.m4085e(th);
            }
        }
    }

    void m4210a(C1377b c1377b) {
        if (c1377b != null) {
            this.f4473a.post(new C1410s(this, c1377b));
        }
    }

    void m4211a(List<C1415x> list) {
        try {
            if (Thread.currentThread().getId() == this.f4473a.getLooper().getThread().getId()) {
                m4201b((List) list);
            } else {
                this.f4473a.post(new C1408q(this, list));
            }
        } catch (Exception e) {
            f4471e.m4084e(e);
        }
    }

    void m4212a(List<C1415x> list, int i) {
        try {
            if (Thread.currentThread().getId() == this.f4473a.getLooper().getThread().getId()) {
                m4202b((List) list, i);
            } else {
                this.f4473a.post(new C1407p(this, list, i));
            }
        } catch (Object th) {
            f4471e.m4085e(th);
        }
    }

    public synchronized DeviceInfo m4213b(Context context) {
        DeviceInfo deviceInfo;
        Object obj;
        Cursor cursor;
        Throwable th;
        if (this.f4475c != null) {
            deviceInfo = this.f4475c;
        } else {
            Cursor query;
            try {
                query = this.f4476d.getReadableDatabase().query("user", null, null, null, null, null, null, null);
                obj = null;
                try {
                    String d;
                    String str;
                    String l;
                    String str2 = "";
                    if (query.moveToNext()) {
                        Object obj2;
                        d = C1389k.m4133d(query.getString(0));
                        int i = query.getInt(1);
                        str2 = query.getString(2);
                        long currentTimeMillis = System.currentTimeMillis() / 1000;
                        int i2 = (i == 1 || C1389k.m4118a(query.getLong(3) * 1000).equals(C1389k.m4118a(1000 * currentTimeMillis))) ? i : 1;
                        int i3 = !str2.equals(C1389k.m4152r(context)) ? i2 | 2 : i2;
                        String[] split = d.split(",");
                        if (split == null || split.length <= 0) {
                            str2 = C1389k.m4126b(context);
                            d = str2;
                            str = str2;
                            int i4 = 1;
                        } else {
                            str2 = split[0];
                            if (str2 == null || str2.length() < 11) {
                                l = C1389k.m4146l(context);
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
                            l = C1389k.m4129c(context);
                            if (l != null && l.length() > 0) {
                                str = d + "," + l;
                                obj2 = 1;
                            }
                        } else {
                            l = split[1];
                            str = d + "," + l;
                        }
                        this.f4475c = new DeviceInfo(d, l, i3);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("uid", C1389k.m4130c(str));
                        contentValues.put("user_type", Integer.valueOf(i3));
                        contentValues.put("app_ver", C1389k.m4152r(context));
                        contentValues.put(DeviceInfo.TAG_TIMESTAMPS, Long.valueOf(currentTimeMillis));
                        if (obj2 != null) {
                            this.f4476d.getWritableDatabase().update("user", contentValues, "uid=?", new String[]{r10});
                        }
                        if (i3 != i) {
                            this.f4476d.getWritableDatabase().replace("user", null, contentValues);
                            obj = 1;
                        } else {
                            i2 = 1;
                        }
                    }
                    if (obj == null) {
                        str2 = C1389k.m4126b(context);
                        str = C1389k.m4129c(context);
                        l = (str == null || str.length() <= 0) ? str2 : str2 + "," + str;
                        long currentTimeMillis2 = System.currentTimeMillis() / 1000;
                        d = C1389k.m4152r(context);
                        ContentValues contentValues2 = new ContentValues();
                        contentValues2.put("uid", C1389k.m4130c(l));
                        contentValues2.put("user_type", Integer.valueOf(0));
                        contentValues2.put("app_ver", d);
                        contentValues2.put(DeviceInfo.TAG_TIMESTAMPS, Long.valueOf(currentTimeMillis2));
                        this.f4476d.getWritableDatabase().insert("user", null, contentValues2);
                        this.f4475c = new DeviceInfo(str2, str, 0);
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
            deviceInfo = this.f4475c;
        }
        return deviceInfo;
    }

    void m4214c() {
        this.f4473a.post(new C1411t(this));
    }
}

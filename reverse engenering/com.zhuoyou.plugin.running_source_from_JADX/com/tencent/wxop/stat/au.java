package com.tencent.wxop.stat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import com.droi.btlib.connection.MessageObj;
import com.droi.sdk.core.priv.PersistSettings;
import com.tencent.stat.DeviceInfo;
import com.tencent.wxop.stat.common.C1432a;
import com.tencent.wxop.stat.common.C1436e;
import com.tencent.wxop.stat.common.C1442k;
import com.tencent.wxop.stat.common.C1448q;
import com.tencent.wxop.stat.common.StatLogger;
import com.tencent.wxop.stat.p040a.C1416e;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

public class au {
    private static StatLogger f4689h = C1442k.m4416b();
    private static Context f4690i = null;
    private static au f4691j = null;
    volatile int f4692a = 0;
    C1432a f4693b = null;
    private bc f4694c = null;
    private bc f4695d = null;
    private C1436e f4696e = null;
    private String f4697f = "";
    private String f4698g = "";
    private int f4699k = 0;
    private ConcurrentHashMap<C1416e, String> f4700l = null;
    private boolean f4701m = false;
    private HashMap<String, String> f4702n = new HashMap();

    private au(Context context) {
        try {
            this.f4696e = new C1436e();
            f4690i = context.getApplicationContext();
            this.f4700l = new ConcurrentHashMap();
            this.f4697f = C1442k.m4440r(context);
            this.f4698g = "pri_" + C1442k.m4440r(context);
            this.f4694c = new bc(f4690i, this.f4697f);
            this.f4695d = new bc(f4690i, this.f4698g);
            m4344a(true);
            m4344a(false);
            m4354f();
            m4365b(f4690i);
            m4367d();
            m4358j();
        } catch (Throwable th) {
            f4689h.m4375e(th);
        }
    }

    public static au m4332a(Context context) {
        if (f4691j == null) {
            synchronized (au.class) {
                if (f4691j == null) {
                    f4691j = new au(context);
                }
            }
        }
        return f4691j;
    }

    private String m4333a(List<bd> list) {
        StringBuilder stringBuilder = new StringBuilder(list.size() * 3);
        stringBuilder.append("event_id in (");
        int size = list.size();
        int i = 0;
        for (bd bdVar : list) {
            stringBuilder.append(bdVar.f4728a);
            if (i != size - 1) {
                stringBuilder.append(",");
            }
            i++;
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private synchronized void m4334a(int i, boolean z) {
        try {
            if (this.f4692a > 0 && i > 0 && !StatServiceImpl.m4245a()) {
                if (StatConfig.isDebugEnable()) {
                    f4689h.m4376i("Load " + this.f4692a + " unsent events");
                }
                List arrayList = new ArrayList(i);
                m4350b(arrayList, i, z);
                if (arrayList.size() > 0) {
                    if (StatConfig.isDebugEnable()) {
                        f4689h.m4376i("Peek " + arrayList.size() + " unsent events.");
                    }
                    m4342a(arrayList, 2, z);
                    C1454i.m4486b(f4690i).m4489b(arrayList, new ba(this, arrayList, z));
                }
            }
        } catch (Throwable th) {
            f4689h.m4375e(th);
        }
    }

    private void m4335a(C1416e c1416e, C1429h c1429h, boolean z) {
        long insert;
        long j;
        SQLiteDatabase sQLiteDatabase = null;
        try {
            sQLiteDatabase = m4351c(z);
            sQLiteDatabase.beginTransaction();
            if (!z && this.f4692a > StatConfig.getMaxStoreEventCount()) {
                f4689h.warn("Too many events stored in db.");
                this.f4692a -= this.f4694c.getWritableDatabase().delete("events", "event_id in (select event_id from events where timestamp in (select min(timestamp) from events) limit 1)", null);
            }
            ContentValues contentValues = new ContentValues();
            String g = c1416e.m4273g();
            if (StatConfig.isDebugEnable()) {
                f4689h.m4376i("insert 1 event, content:" + g);
            }
            contentValues.put("content", C1448q.m4467b(g));
            contentValues.put("send_count", "0");
            contentValues.put("status", Integer.toString(1));
            contentValues.put(MessageObj.TIEMSTAMP, Long.valueOf(c1416e.m4269c()));
            insert = sQLiteDatabase.insert("events", null, contentValues);
            sQLiteDatabase.setTransactionSuccessful();
            if (sQLiteDatabase != null) {
                try {
                    sQLiteDatabase.endTransaction();
                    j = insert;
                } catch (Throwable th) {
                    f4689h.m4375e(th);
                    j = insert;
                }
                if (j <= 0) {
                    this.f4692a++;
                    if (StatConfig.isDebugEnable()) {
                        f4689h.m4373d("directStoreEvent insert event to db, event:" + c1416e.m4273g());
                    }
                    if (c1429h != null) {
                        c1429h.mo2225a();
                    }
                }
                f4689h.error("Failed to store event:" + c1416e.m4273g());
                return;
            }
        } catch (Throwable th2) {
            f4689h.m4375e(th2);
            j = -1;
        }
        j = insert;
        if (j <= 0) {
            f4689h.error("Failed to store event:" + c1416e.m4273g());
            return;
        }
        this.f4692a++;
        if (StatConfig.isDebugEnable()) {
            f4689h.m4373d("directStoreEvent insert event to db, event:" + c1416e.m4273g());
        }
        if (c1429h != null) {
            c1429h.mo2225a();
        }
    }

    private synchronized void m4342a(List<bd> list, int i, boolean z) {
        SQLiteDatabase c;
        Throwable th;
        String str = null;
        synchronized (this) {
            if (list.size() != 0) {
                int b = m4345b(z);
                try {
                    String str2;
                    c = m4351c(z);
                    if (i == 2) {
                        try {
                            str2 = "update events set status=" + i + ", send_count=send_count+1  where " + m4333a((List) list);
                        } catch (Throwable th2) {
                            th = th2;
                            try {
                                f4689h.m4375e(th);
                                if (c != null) {
                                    try {
                                        c.endTransaction();
                                    } catch (Throwable th3) {
                                        f4689h.m4375e(th3);
                                    }
                                }
                            } catch (Throwable th4) {
                                th3 = th4;
                                if (c != null) {
                                    try {
                                        c.endTransaction();
                                    } catch (Throwable th5) {
                                        f4689h.m4375e(th5);
                                    }
                                }
                                throw th3;
                            }
                        }
                    }
                    str2 = "update events set status=" + i + " where " + m4333a((List) list);
                    if (this.f4699k % 3 == 0) {
                        str = "delete from events where send_count>" + b;
                    }
                    this.f4699k++;
                    if (StatConfig.isDebugEnable()) {
                        f4689h.m4376i("update sql:" + str2);
                    }
                    c.beginTransaction();
                    c.execSQL(str2);
                    if (str != null) {
                        f4689h.m4376i("update for delete sql:" + str);
                        c.execSQL(str);
                        m4354f();
                    }
                    c.setTransactionSuccessful();
                    if (c != null) {
                        try {
                            c.endTransaction();
                        } catch (Throwable th32) {
                            f4689h.m4375e(th32);
                        }
                    }
                } catch (Throwable th6) {
                    th32 = th6;
                    c = null;
                    if (c != null) {
                        c.endTransaction();
                    }
                    throw th32;
                }
            }
        }
    }

    private synchronized void m4343a(java.util.List<com.tencent.wxop.stat.bd> r9, boolean r10) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.tencent.wxop.stat.au.a(java.util.List, boolean):void. bs: [B:26:0x00c1, B:49:0x00e9]
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:86)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r8 = this;
        r1 = 0;
        monitor-enter(r8);
        r0 = r9.size();	 Catch:{ all -> 0x00ce }
        if (r0 != 0) goto L_0x000a;
    L_0x0008:
        monitor-exit(r8);
        return;
    L_0x000a:
        r0 = com.tencent.wxop.stat.StatConfig.isDebugEnable();	 Catch:{ all -> 0x00ce }
        if (r0 == 0) goto L_0x0032;	 Catch:{ all -> 0x00ce }
    L_0x0010:
        r0 = f4689h;	 Catch:{ all -> 0x00ce }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00ce }
        r3 = "Delete ";	 Catch:{ all -> 0x00ce }
        r2.<init>(r3);	 Catch:{ all -> 0x00ce }
        r3 = r9.size();	 Catch:{ all -> 0x00ce }
        r2 = r2.append(r3);	 Catch:{ all -> 0x00ce }
        r3 = " events, important:";	 Catch:{ all -> 0x00ce }
        r2 = r2.append(r3);	 Catch:{ all -> 0x00ce }
        r2 = r2.append(r10);	 Catch:{ all -> 0x00ce }
        r2 = r2.toString();	 Catch:{ all -> 0x00ce }
        r0.m4376i(r2);	 Catch:{ all -> 0x00ce }
    L_0x0032:
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00ce }
        r0 = r9.size();	 Catch:{ all -> 0x00ce }
        r0 = r0 * 3;	 Catch:{ all -> 0x00ce }
        r3.<init>(r0);	 Catch:{ all -> 0x00ce }
        r0 = "event_id in (";	 Catch:{ all -> 0x00ce }
        r3.append(r0);	 Catch:{ all -> 0x00ce }
        r0 = 0;	 Catch:{ all -> 0x00ce }
        r4 = r9.size();	 Catch:{ all -> 0x00ce }
        r5 = r9.iterator();	 Catch:{ all -> 0x00ce }
        r2 = r0;	 Catch:{ all -> 0x00ce }
    L_0x004c:
        r0 = r5.hasNext();	 Catch:{ all -> 0x00ce }
        if (r0 == 0) goto L_0x006a;	 Catch:{ all -> 0x00ce }
    L_0x0052:
        r0 = r5.next();	 Catch:{ all -> 0x00ce }
        r0 = (com.tencent.wxop.stat.bd) r0;	 Catch:{ all -> 0x00ce }
        r6 = r0.f4728a;	 Catch:{ all -> 0x00ce }
        r3.append(r6);	 Catch:{ all -> 0x00ce }
        r0 = r4 + -1;	 Catch:{ all -> 0x00ce }
        if (r2 == r0) goto L_0x0066;	 Catch:{ all -> 0x00ce }
    L_0x0061:
        r0 = ",";	 Catch:{ all -> 0x00ce }
        r3.append(r0);	 Catch:{ all -> 0x00ce }
    L_0x0066:
        r0 = r2 + 1;	 Catch:{ all -> 0x00ce }
        r2 = r0;	 Catch:{ all -> 0x00ce }
        goto L_0x004c;	 Catch:{ all -> 0x00ce }
    L_0x006a:
        r0 = ")";	 Catch:{ all -> 0x00ce }
        r3.append(r0);	 Catch:{ all -> 0x00ce }
        r1 = r8.m4351c(r10);	 Catch:{ Throwable -> 0x00d1 }
        r1.beginTransaction();	 Catch:{ Throwable -> 0x00d1 }
        r0 = "events";	 Catch:{ Throwable -> 0x00d1 }
        r2 = r3.toString();	 Catch:{ Throwable -> 0x00d1 }
        r5 = 0;	 Catch:{ Throwable -> 0x00d1 }
        r0 = r1.delete(r0, r2, r5);	 Catch:{ Throwable -> 0x00d1 }
        r2 = com.tencent.wxop.stat.StatConfig.isDebugEnable();	 Catch:{ Throwable -> 0x00d1 }
        if (r2 == 0) goto L_0x00b3;	 Catch:{ Throwable -> 0x00d1 }
    L_0x0087:
        r2 = f4689h;	 Catch:{ Throwable -> 0x00d1 }
        r5 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00d1 }
        r6 = "delete ";	 Catch:{ Throwable -> 0x00d1 }
        r5.<init>(r6);	 Catch:{ Throwable -> 0x00d1 }
        r4 = r5.append(r4);	 Catch:{ Throwable -> 0x00d1 }
        r5 = " event ";	 Catch:{ Throwable -> 0x00d1 }
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x00d1 }
        r3 = r3.toString();	 Catch:{ Throwable -> 0x00d1 }
        r3 = r4.append(r3);	 Catch:{ Throwable -> 0x00d1 }
        r4 = ", success delete:";	 Catch:{ Throwable -> 0x00d1 }
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x00d1 }
        r3 = r3.append(r0);	 Catch:{ Throwable -> 0x00d1 }
        r3 = r3.toString();	 Catch:{ Throwable -> 0x00d1 }
        r2.m4376i(r3);	 Catch:{ Throwable -> 0x00d1 }
    L_0x00b3:
        r2 = r8.f4692a;	 Catch:{ Throwable -> 0x00d1 }
        r0 = r2 - r0;	 Catch:{ Throwable -> 0x00d1 }
        r8.f4692a = r0;	 Catch:{ Throwable -> 0x00d1 }
        r1.setTransactionSuccessful();	 Catch:{ Throwable -> 0x00d1 }
        r8.m4354f();	 Catch:{ Throwable -> 0x00d1 }
        if (r1 == 0) goto L_0x0008;
    L_0x00c1:
        r1.endTransaction();	 Catch:{ Throwable -> 0x00c6 }
        goto L_0x0008;
    L_0x00c6:
        r0 = move-exception;
        r1 = f4689h;	 Catch:{ all -> 0x00ce }
        r1.m4375e(r0);	 Catch:{ all -> 0x00ce }
        goto L_0x0008;
    L_0x00ce:
        r0 = move-exception;
        monitor-exit(r8);
        throw r0;
    L_0x00d1:
        r0 = move-exception;
        r2 = f4689h;	 Catch:{ all -> 0x00e6 }
        r2.m4375e(r0);	 Catch:{ all -> 0x00e6 }
        if (r1 == 0) goto L_0x0008;
    L_0x00d9:
        r1.endTransaction();	 Catch:{ Throwable -> 0x00de }
        goto L_0x0008;
    L_0x00de:
        r0 = move-exception;
        r1 = f4689h;	 Catch:{ all -> 0x00ce }
        r1.m4375e(r0);	 Catch:{ all -> 0x00ce }
        goto L_0x0008;
    L_0x00e6:
        r0 = move-exception;
        if (r1 == 0) goto L_0x00ec;
    L_0x00e9:
        r1.endTransaction();	 Catch:{ Throwable -> 0x00ed }
    L_0x00ec:
        throw r0;	 Catch:{ all -> 0x00ce }
    L_0x00ed:
        r1 = move-exception;	 Catch:{ all -> 0x00ce }
        r2 = f4689h;	 Catch:{ all -> 0x00ce }
        r2.m4375e(r1);	 Catch:{ all -> 0x00ce }
        goto L_0x00ec;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.wxop.stat.au.a(java.util.List, boolean):void");
    }

    private void m4344a(boolean z) {
        SQLiteDatabase sQLiteDatabase = null;
        try {
            sQLiteDatabase = m4351c(z);
            sQLiteDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put("status", Integer.valueOf(1));
            int update = sQLiteDatabase.update("events", contentValues, "status=?", new String[]{Long.toString(2)});
            if (StatConfig.isDebugEnable()) {
                f4689h.m4376i("update " + update + " unsent events.");
            }
            sQLiteDatabase.setTransactionSuccessful();
            if (sQLiteDatabase != null) {
                try {
                    sQLiteDatabase.endTransaction();
                } catch (Throwable th) {
                    f4689h.m4375e(th);
                }
            }
        } catch (Throwable th2) {
            f4689h.m4375e(th2);
        }
    }

    private int m4345b(boolean z) {
        return !z ? StatConfig.getMaxSendRetryCount() : StatConfig.getMaxImportantDataSendRetryCount();
    }

    public static au m4346b() {
        return f4691j;
    }

    private void m4347b(int i, boolean z) {
        int g = i == -1 ? !z ? m4355g() : m4356h() : i;
        if (g > 0) {
            int sendPeriodMinutes = (StatConfig.getSendPeriodMinutes() * 60) * StatConfig.getNumEventsCommitPerSec();
            if (g > sendPeriodMinutes && sendPeriodMinutes > 0) {
                g = sendPeriodMinutes;
            }
            int a = StatConfig.m4219a();
            int i2 = g / a;
            int i3 = g % a;
            if (StatConfig.isDebugEnable()) {
                f4689h.m4376i("sentStoreEventsByDb sendNumbers=" + g + ",important=" + z + ",maxSendNumPerFor1Period=" + sendPeriodMinutes + ",maxCount=" + i2 + ",restNumbers=" + i3);
            }
            for (g = 0; g < i2; g++) {
                m4334a(a, z);
            }
            if (i3 > 0) {
                m4334a(i3, z);
            }
        }
    }

    private synchronized void m4348b(C1416e c1416e, C1429h c1429h, boolean z, boolean z2) {
        if (StatConfig.getMaxStoreEventCount() > 0) {
            if (StatConfig.f4546m <= 0 || z || z2) {
                m4335a(c1416e, c1429h, z);
            } else if (StatConfig.f4546m > 0) {
                if (StatConfig.isDebugEnable()) {
                    f4689h.m4376i("cacheEventsInMemory.size():" + this.f4700l.size() + ",numEventsCachedInMemory:" + StatConfig.f4546m + ",numStoredEvents:" + this.f4692a);
                    f4689h.m4376i("cache event:" + c1416e.m4273g());
                }
                this.f4700l.put(c1416e, "");
                if (this.f4700l.size() >= StatConfig.f4546m) {
                    m4357i();
                }
                if (c1429h != null) {
                    if (this.f4700l.size() > 0) {
                        m4357i();
                    }
                    c1429h.mo2225a();
                }
            }
        }
    }

    private synchronized void m4349b(C1452f c1452f) {
        Cursor query;
        Throwable th;
        try {
            Object obj;
            long update;
            String a = c1452f.m4477a();
            String a2 = C1442k.m4410a(a);
            ContentValues contentValues = new ContentValues();
            contentValues.put("content", c1452f.f4818b.toString());
            contentValues.put("md5sum", a2);
            c1452f.f4819c = a2;
            contentValues.put("version", Integer.valueOf(c1452f.f4820d));
            query = this.f4694c.getReadableDatabase().query(PersistSettings.CONFIG, null, null, null, null, null, null);
            do {
                try {
                    if (!query.moveToNext()) {
                        obj = null;
                        break;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } while (query.getInt(0) != c1452f.f4817a);
            obj = 1;
            this.f4694c.getWritableDatabase().beginTransaction();
            if (1 == obj) {
                update = (long) this.f4694c.getWritableDatabase().update(PersistSettings.CONFIG, contentValues, "type=?", new String[]{Integer.toString(c1452f.f4817a)});
            } else {
                contentValues.put("type", Integer.valueOf(c1452f.f4817a));
                update = this.f4694c.getWritableDatabase().insert(PersistSettings.CONFIG, null, contentValues);
            }
            if (update == -1) {
                f4689h.m4374e("Failed to store cfg:" + a);
            } else {
                f4689h.m4373d("Sucessed to store cfg:" + a);
            }
            this.f4694c.getWritableDatabase().setTransactionSuccessful();
            if (query != null) {
                query.close();
            }
            try {
                this.f4694c.getWritableDatabase().endTransaction();
            } catch (Exception e) {
            }
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            this.f4694c.getWritableDatabase().endTransaction();
            throw th;
        }
        return;
    }

    private void m4350b(List<bd> list, int i, boolean z) {
        Cursor query;
        Throwable th;
        Cursor cursor;
        try {
            query = m4352d(z).query("events", null, "status=?", new String[]{Integer.toString(1)}, null, null, null, Integer.toString(i));
            while (query.moveToNext()) {
                try {
                    long j = query.getLong(0);
                    String string = query.getString(1);
                    if (!StatConfig.f4540g) {
                        string = C1448q.m4462a(string);
                    }
                    int i2 = query.getInt(2);
                    int i3 = query.getInt(3);
                    bd bdVar = new bd(j, string, i2, i3);
                    if (StatConfig.isDebugEnable()) {
                        f4689h.m4376i("peek event, id=" + j + ",send_count=" + i3 + ",timestamp=" + query.getLong(4));
                    }
                    list.add(bdVar);
                } catch (Throwable th2) {
                    th = th2;
                }
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

    private SQLiteDatabase m4351c(boolean z) {
        return !z ? this.f4694c.getWritableDatabase() : this.f4695d.getWritableDatabase();
    }

    private SQLiteDatabase m4352d(boolean z) {
        return !z ? this.f4694c.getReadableDatabase() : this.f4695d.getReadableDatabase();
    }

    private void m4354f() {
        this.f4692a = m4355g() + m4356h();
    }

    private int m4355g() {
        return (int) DatabaseUtils.queryNumEntries(this.f4694c.getReadableDatabase(), "events");
    }

    private int m4356h() {
        return (int) DatabaseUtils.queryNumEntries(this.f4695d.getReadableDatabase(), "events");
    }

    private void m4357i() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.tencent.wxop.stat.au.i():void. bs: [B:42:0x011b, B:53:0x0133]
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:86)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r9 = this;
        r1 = 0;
        r0 = r9.f4701m;
        if (r0 == 0) goto L_0x0006;
    L_0x0005:
        return;
    L_0x0006:
        r2 = r9.f4700l;
        monitor-enter(r2);
        r0 = r9.f4700l;	 Catch:{ all -> 0x0013 }
        r0 = r0.size();	 Catch:{ all -> 0x0013 }
        if (r0 != 0) goto L_0x0016;	 Catch:{ all -> 0x0013 }
    L_0x0011:
        monitor-exit(r2);	 Catch:{ all -> 0x0013 }
        goto L_0x0005;	 Catch:{ all -> 0x0013 }
    L_0x0013:
        r0 = move-exception;	 Catch:{ all -> 0x0013 }
        monitor-exit(r2);	 Catch:{ all -> 0x0013 }
        throw r0;
    L_0x0016:
        r0 = 1;
        r9.f4701m = r0;	 Catch:{ all -> 0x0013 }
        r0 = com.tencent.wxop.stat.StatConfig.isDebugEnable();	 Catch:{ all -> 0x0013 }
        if (r0 == 0) goto L_0x0051;	 Catch:{ all -> 0x0013 }
    L_0x001f:
        r0 = f4689h;	 Catch:{ all -> 0x0013 }
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0013 }
        r4 = "insert ";	 Catch:{ all -> 0x0013 }
        r3.<init>(r4);	 Catch:{ all -> 0x0013 }
        r4 = r9.f4700l;	 Catch:{ all -> 0x0013 }
        r4 = r4.size();	 Catch:{ all -> 0x0013 }
        r3 = r3.append(r4);	 Catch:{ all -> 0x0013 }
        r4 = " events ,numEventsCachedInMemory:";	 Catch:{ all -> 0x0013 }
        r3 = r3.append(r4);	 Catch:{ all -> 0x0013 }
        r4 = com.tencent.wxop.stat.StatConfig.f4546m;	 Catch:{ all -> 0x0013 }
        r3 = r3.append(r4);	 Catch:{ all -> 0x0013 }
        r4 = ",numStoredEvents:";	 Catch:{ all -> 0x0013 }
        r3 = r3.append(r4);	 Catch:{ all -> 0x0013 }
        r4 = r9.f4692a;	 Catch:{ all -> 0x0013 }
        r3 = r3.append(r4);	 Catch:{ all -> 0x0013 }
        r3 = r3.toString();	 Catch:{ all -> 0x0013 }
        r0.m4376i(r3);	 Catch:{ all -> 0x0013 }
    L_0x0051:
        r0 = r9.f4694c;	 Catch:{ Throwable -> 0x00ca }
        r1 = r0.getWritableDatabase();	 Catch:{ Throwable -> 0x00ca }
        r1.beginTransaction();	 Catch:{ Throwable -> 0x00ca }
        r0 = r9.f4700l;	 Catch:{ Throwable -> 0x00ca }
        r0 = r0.entrySet();	 Catch:{ Throwable -> 0x00ca }
        r3 = r0.iterator();	 Catch:{ Throwable -> 0x00ca }
    L_0x0064:
        r0 = r3.hasNext();	 Catch:{ Throwable -> 0x00ca }
        if (r0 == 0) goto L_0x0116;	 Catch:{ Throwable -> 0x00ca }
    L_0x006a:
        r0 = r3.next();	 Catch:{ Throwable -> 0x00ca }
        r0 = (java.util.Map.Entry) r0;	 Catch:{ Throwable -> 0x00ca }
        r0 = r0.getKey();	 Catch:{ Throwable -> 0x00ca }
        r0 = (com.tencent.wxop.stat.p040a.C1416e) r0;	 Catch:{ Throwable -> 0x00ca }
        r4 = new android.content.ContentValues;	 Catch:{ Throwable -> 0x00ca }
        r4.<init>();	 Catch:{ Throwable -> 0x00ca }
        r5 = r0.m4273g();	 Catch:{ Throwable -> 0x00ca }
        r6 = com.tencent.wxop.stat.StatConfig.isDebugEnable();	 Catch:{ Throwable -> 0x00ca }
        if (r6 == 0) goto L_0x0099;	 Catch:{ Throwable -> 0x00ca }
    L_0x0085:
        r6 = f4689h;	 Catch:{ Throwable -> 0x00ca }
        r7 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00ca }
        r8 = "insert content:";	 Catch:{ Throwable -> 0x00ca }
        r7.<init>(r8);	 Catch:{ Throwable -> 0x00ca }
        r7 = r7.append(r5);	 Catch:{ Throwable -> 0x00ca }
        r7 = r7.toString();	 Catch:{ Throwable -> 0x00ca }
        r6.m4376i(r7);	 Catch:{ Throwable -> 0x00ca }
    L_0x0099:
        r5 = com.tencent.wxop.stat.common.C1448q.m4467b(r5);	 Catch:{ Throwable -> 0x00ca }
        r6 = "content";	 Catch:{ Throwable -> 0x00ca }
        r4.put(r6, r5);	 Catch:{ Throwable -> 0x00ca }
        r5 = "send_count";	 Catch:{ Throwable -> 0x00ca }
        r6 = "0";	 Catch:{ Throwable -> 0x00ca }
        r4.put(r5, r6);	 Catch:{ Throwable -> 0x00ca }
        r5 = "status";	 Catch:{ Throwable -> 0x00ca }
        r6 = 1;	 Catch:{ Throwable -> 0x00ca }
        r6 = java.lang.Integer.toString(r6);	 Catch:{ Throwable -> 0x00ca }
        r4.put(r5, r6);	 Catch:{ Throwable -> 0x00ca }
        r5 = "timestamp";	 Catch:{ Throwable -> 0x00ca }
        r6 = r0.m4269c();	 Catch:{ Throwable -> 0x00ca }
        r0 = java.lang.Long.valueOf(r6);	 Catch:{ Throwable -> 0x00ca }
        r4.put(r5, r0);	 Catch:{ Throwable -> 0x00ca }
        r0 = "events";	 Catch:{ Throwable -> 0x00ca }
        r5 = 0;	 Catch:{ Throwable -> 0x00ca }
        r1.insert(r0, r5, r4);	 Catch:{ Throwable -> 0x00ca }
        r3.remove();	 Catch:{ Throwable -> 0x00ca }
        goto L_0x0064;
    L_0x00ca:
        r0 = move-exception;
        r3 = f4689h;	 Catch:{ all -> 0x0130 }
        r3.m4375e(r0);	 Catch:{ all -> 0x0130 }
        if (r1 == 0) goto L_0x00d8;
    L_0x00d2:
        r1.endTransaction();	 Catch:{ Throwable -> 0x0129 }
        r9.m4354f();	 Catch:{ Throwable -> 0x0129 }
    L_0x00d8:
        r0 = 0;
        r9.f4701m = r0;	 Catch:{ all -> 0x0013 }
        r0 = com.tencent.wxop.stat.StatConfig.isDebugEnable();	 Catch:{ all -> 0x0013 }
        if (r0 == 0) goto L_0x0113;	 Catch:{ all -> 0x0013 }
    L_0x00e1:
        r0 = f4689h;	 Catch:{ all -> 0x0013 }
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0013 }
        r3 = "after insert, cacheEventsInMemory.size():";	 Catch:{ all -> 0x0013 }
        r1.<init>(r3);	 Catch:{ all -> 0x0013 }
        r3 = r9.f4700l;	 Catch:{ all -> 0x0013 }
        r3 = r3.size();	 Catch:{ all -> 0x0013 }
        r1 = r1.append(r3);	 Catch:{ all -> 0x0013 }
        r3 = ",numEventsCachedInMemory:";	 Catch:{ all -> 0x0013 }
        r1 = r1.append(r3);	 Catch:{ all -> 0x0013 }
        r3 = com.tencent.wxop.stat.StatConfig.f4546m;	 Catch:{ all -> 0x0013 }
        r1 = r1.append(r3);	 Catch:{ all -> 0x0013 }
        r3 = ",numStoredEvents:";	 Catch:{ all -> 0x0013 }
        r1 = r1.append(r3);	 Catch:{ all -> 0x0013 }
        r3 = r9.f4692a;	 Catch:{ all -> 0x0013 }
        r1 = r1.append(r3);	 Catch:{ all -> 0x0013 }
        r1 = r1.toString();	 Catch:{ all -> 0x0013 }
        r0.m4376i(r1);	 Catch:{ all -> 0x0013 }
    L_0x0113:
        monitor-exit(r2);	 Catch:{ all -> 0x0013 }
        goto L_0x0005;
    L_0x0116:
        r1.setTransactionSuccessful();	 Catch:{ Throwable -> 0x00ca }
        if (r1 == 0) goto L_0x00d8;
    L_0x011b:
        r1.endTransaction();	 Catch:{ Throwable -> 0x0122 }
        r9.m4354f();	 Catch:{ Throwable -> 0x0122 }
        goto L_0x00d8;
    L_0x0122:
        r0 = move-exception;
        r1 = f4689h;	 Catch:{ all -> 0x0013 }
        r1.m4375e(r0);	 Catch:{ all -> 0x0013 }
        goto L_0x00d8;	 Catch:{ all -> 0x0013 }
    L_0x0129:
        r0 = move-exception;	 Catch:{ all -> 0x0013 }
        r1 = f4689h;	 Catch:{ all -> 0x0013 }
        r1.m4375e(r0);	 Catch:{ all -> 0x0013 }
        goto L_0x00d8;
    L_0x0130:
        r0 = move-exception;
        if (r1 == 0) goto L_0x0139;
    L_0x0133:
        r1.endTransaction();	 Catch:{ Throwable -> 0x013a }
        r9.m4354f();	 Catch:{ Throwable -> 0x013a }
    L_0x0139:
        throw r0;	 Catch:{ all -> 0x0013 }
    L_0x013a:
        r1 = move-exception;	 Catch:{ all -> 0x0013 }
        r3 = f4689h;	 Catch:{ all -> 0x0013 }
        r3.m4375e(r1);	 Catch:{ all -> 0x0013 }
        goto L_0x0139;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.wxop.stat.au.i():void");
    }

    private void m4358j() {
        Throwable th;
        Cursor query;
        try {
            query = this.f4694c.getReadableDatabase().query("keyvalues", null, null, null, null, null, null);
            while (query.moveToNext()) {
                try {
                    this.f4702n.put(query.getString(0), query.getString(1));
                } catch (Throwable th2) {
                    th = th2;
                }
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

    public int m4359a() {
        return this.f4692a;
    }

    void m4360a(int i) {
        this.f4696e.m4388a(new bb(this, i));
    }

    void m4361a(C1416e c1416e, C1429h c1429h, boolean z, boolean z2) {
        if (this.f4696e != null) {
            this.f4696e.m4388a(new ay(this, c1416e, c1429h, z, z2));
        }
    }

    void m4362a(C1452f c1452f) {
        if (c1452f != null) {
            this.f4696e.m4388a(new az(this, c1452f));
        }
    }

    void m4363a(List<bd> list, int i, boolean z, boolean z2) {
        if (this.f4696e != null) {
            this.f4696e.m4388a(new av(this, list, i, z, z2));
        }
    }

    void m4364a(List<bd> list, boolean z, boolean z2) {
        if (this.f4696e != null) {
            this.f4696e.m4388a(new aw(this, list, z, z2));
        }
    }

    public synchronized C1432a m4365b(Context context) {
        C1432a c1432a;
        Throwable th;
        Cursor cursor;
        if (this.f4693b != null) {
            c1432a = this.f4693b;
        } else {
            Cursor query;
            try {
                this.f4694c.getWritableDatabase().beginTransaction();
                if (StatConfig.isDebugEnable()) {
                    f4689h.m4376i("try to load user info from db.");
                }
                query = this.f4694c.getReadableDatabase().query("user", null, null, null, null, null, null, null);
                Object obj = null;
                try {
                    String string;
                    String b;
                    if (query.moveToNext()) {
                        String a = C1448q.m4462a(query.getString(0));
                        int i = query.getInt(1);
                        string = query.getString(2);
                        long currentTimeMillis = System.currentTimeMillis() / 1000;
                        int i2 = (i == 1 || C1442k.m4408a(query.getLong(3) * 1000).equals(C1442k.m4408a(1000 * currentTimeMillis))) ? i : 1;
                        int i3 = !string.equals(C1442k.m4436n(context)) ? i2 | 2 : i2;
                        String[] split = a.split(",");
                        obj = null;
                        if (split == null || split.length <= 0) {
                            b = C1442k.m4417b(context);
                            obj = 1;
                            a = b;
                        } else {
                            b = split[0];
                            if (b == null || b.length() < 11) {
                                string = C1448q.m4461a(context);
                                if (string == null || string.length() <= 10) {
                                    string = b;
                                } else {
                                    obj = 1;
                                }
                                b = a;
                                a = string;
                            } else {
                                String str = b;
                                b = a;
                                a = str;
                            }
                        }
                        if (split == null || split.length < 2) {
                            string = C1442k.m4419c(context);
                            if (string != null && string.length() > 0) {
                                b = a + "," + string;
                                obj = 1;
                            }
                        } else {
                            string = split[1];
                            b = a + "," + string;
                        }
                        this.f4693b = new C1432a(a, string, i3);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("uid", C1448q.m4467b(b));
                        contentValues.put("user_type", Integer.valueOf(i3));
                        contentValues.put("app_ver", C1442k.m4436n(context));
                        contentValues.put(DeviceInfo.TAG_TIMESTAMPS, Long.valueOf(currentTimeMillis));
                        if (obj != null) {
                            this.f4694c.getWritableDatabase().update("user", contentValues, "uid=?", new String[]{r10});
                        }
                        if (i3 != i) {
                            this.f4694c.getWritableDatabase().replace("user", null, contentValues);
                        }
                        obj = 1;
                    }
                    if (obj == null) {
                        string = C1442k.m4417b(context);
                        b = C1442k.m4419c(context);
                        String str2 = (b == null || b.length() <= 0) ? string : string + "," + b;
                        long currentTimeMillis2 = System.currentTimeMillis() / 1000;
                        String n = C1442k.m4436n(context);
                        ContentValues contentValues2 = new ContentValues();
                        contentValues2.put("uid", C1448q.m4467b(str2));
                        contentValues2.put("user_type", Integer.valueOf(0));
                        contentValues2.put("app_ver", n);
                        contentValues2.put(DeviceInfo.TAG_TIMESTAMPS, Long.valueOf(currentTimeMillis2));
                        this.f4694c.getWritableDatabase().insert("user", null, contentValues2);
                        this.f4693b = new C1432a(string, b, 0);
                    }
                    this.f4694c.getWritableDatabase().setTransactionSuccessful();
                    if (query != null) {
                        try {
                            query.close();
                        } catch (Throwable th2) {
                            f4689h.m4375e(th2);
                        }
                    }
                    this.f4694c.getWritableDatabase().endTransaction();
                } catch (Throwable th3) {
                    th2 = th3;
                    if (query != null) {
                        query.close();
                    }
                    this.f4694c.getWritableDatabase().endTransaction();
                    throw th2;
                }
            } catch (Throwable th4) {
                th2 = th4;
                query = null;
                if (query != null) {
                    query.close();
                }
                this.f4694c.getWritableDatabase().endTransaction();
                throw th2;
            }
            c1432a = this.f4693b;
        }
        return c1432a;
    }

    void m4366c() {
        if (StatConfig.isEnableStatService()) {
            try {
                this.f4696e.m4388a(new ax(this));
            } catch (Throwable th) {
                f4689h.m4375e(th);
            }
        }
    }

    void m4367d() {
        Cursor query;
        Throwable th;
        try {
            query = this.f4694c.getReadableDatabase().query(PersistSettings.CONFIG, null, null, null, null, null, null);
            while (query.moveToNext()) {
                try {
                    int i = query.getInt(0);
                    String string = query.getString(1);
                    String string2 = query.getString(2);
                    int i2 = query.getInt(3);
                    C1452f c1452f = new C1452f(i);
                    c1452f.f4817a = i;
                    c1452f.f4818b = new JSONObject(string);
                    c1452f.f4819c = string2;
                    c1452f.f4820d = i2;
                    StatConfig.m4224a(f4690i, c1452f);
                } catch (Throwable th2) {
                    th = th2;
                }
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
}

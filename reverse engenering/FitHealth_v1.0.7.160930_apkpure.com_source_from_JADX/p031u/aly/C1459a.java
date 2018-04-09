package p031u.aly;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.facebook.GraphResponse;
import com.umeng.analytics.C0919a;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import p031u.aly.C1502d.C1495a;
import p031u.aly.C1502d.C1495a.C1493a;
import p031u.aly.C1502d.C1498b;
import p031u.aly.C1502d.C1498b.C1496a;
import p031u.aly.C1502d.C1501c;
import p031u.aly.C1502d.C1501c.C1499a;
import p031u.aly.av.C1465e;
import p031u.aly.av.C1466f;

/* compiled from: CCSQLManager */
public class C1459a {
    public static void m3394a(android.database.sqlite.SQLiteDatabase r12, java.util.Map<java.lang.String, p031u.aly.C1508k> r13, p031u.aly.C1950f r14) {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1431)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r6 = 0;
        r4 = 0;
        r1 = 0;
        r0 = "__ag_of";	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r0 = r13.get(r0);	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r0 = (p031u.aly.C1508k) r0;	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        if (r0 != 0) goto L_0x0014;
    L_0x000e:
        if (r1 == 0) goto L_0x0013;
    L_0x0010:
        r1.close();
    L_0x0013:
        return;
    L_0x0014:
        r5 = "system where key=\"__ag_of\"";	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r2 = new java.lang.StringBuilder;	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r2.<init>();	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r3 = "select * from ";	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r2 = r2.append(r3);	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r2 = r2.append(r5);	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r2 = r2.toString();	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r3 = 0;	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r1 = r12.rawQuery(r2, r3);	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r1.moveToFirst();	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r2 = r6;	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
    L_0x0032:
        r8 = r1.isAfterLast();	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        if (r8 != 0) goto L_0x008d;	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
    L_0x0038:
        r8 = r1.getCount();	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        if (r8 <= 0) goto L_0x0068;	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
    L_0x003e:
        r2 = "count";	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r2 = r1.getColumnIndex(r2);	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r4 = r1.getInt(r2);	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r2 = "timeStamp";	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r2 = r1.getColumnIndex(r2);	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r2 = r1.getLong(r2);	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r8 = new java.lang.StringBuilder;	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r8.<init>();	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r9 = "delete from ";	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r8 = r8.append(r9);	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r8 = r8.append(r5);	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r8 = r8.toString();	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r12.execSQL(r8);	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
    L_0x0068:
        r1.moveToNext();	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        goto L_0x0032;
    L_0x006c:
        r0 = move-exception;
        r2 = new java.lang.StringBuilder;	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r2.<init>();	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r3 = "save to system table error ";	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r2 = r2.append(r3);	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r0 = r0.toString();	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r0 = r2.append(r0);	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r0 = r0.toString();	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        p031u.aly.bl.m3594e(r0);	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        if (r1 == 0) goto L_0x0013;
    L_0x0089:
        r1.close();
        goto L_0x0013;
    L_0x008d:
        r8 = new android.content.ContentValues;	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r8.<init>();	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r5 = "key";	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r9 = r0.m3797c();	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r8.put(r5, r9);	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r9 = "count";	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        if (r4 != 0) goto L_0x00ce;	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
    L_0x009f:
        r4 = r0.m3799e();	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
    L_0x00a3:
        r4 = java.lang.Long.valueOf(r4);	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r8.put(r9, r4);	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r4 = "timeStamp";	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r5 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        if (r5 != 0) goto L_0x00b4;	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
    L_0x00b0:
        r2 = r0.m3798d();	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
    L_0x00b4:
        r0 = java.lang.Long.valueOf(r2);	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r8.put(r4, r0);	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r0 = "system";	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r2 = 0;	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r12.insert(r0, r2, r8);	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r0 = "success";	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r2 = 0;	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r14.mo2823a(r0, r2);	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        if (r1 == 0) goto L_0x0013;
    L_0x00c9:
        r1.close();
        goto L_0x0013;
    L_0x00ce:
        r4 = (long) r4;
        r10 = r0.m3799e();	 Catch:{ SQLException -> 0x006c, all -> 0x00d5 }
        r4 = r4 + r10;
        goto L_0x00a3;
    L_0x00d5:
        r0 = move-exception;
        if (r1 == 0) goto L_0x00db;
    L_0x00d8:
        r1.close();
    L_0x00db:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: u.aly.a.a(android.database.sqlite.SQLiteDatabase, java.util.Map, u.aly.f):void");
    }

    public static boolean m3398a(android.database.sqlite.SQLiteDatabase r4, java.util.Collection<p031u.aly.C1506i> r5) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:20:? in {3, 8, 15, 17, 18, 19, 22, 23} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.rerun(BlockProcessor.java:44)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:57)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r4.beginTransaction();	 Catch:{ SQLException -> 0x002b, all -> 0x003e }
        r0 = "aggregated_cache";	 Catch:{ SQLException -> 0x002b, all -> 0x003e }
        r0 = p031u.aly.C1459a.m3404c(r4, r0);	 Catch:{ SQLException -> 0x002b, all -> 0x003e }
        if (r0 <= 0) goto L_0x0010;	 Catch:{ SQLException -> 0x002b, all -> 0x003e }
    L_0x000b:
        r0 = "aggregated_cache";	 Catch:{ SQLException -> 0x002b, all -> 0x003e }
        p031u.aly.C1459a.m3402b(r4, r0);	 Catch:{ SQLException -> 0x002b, all -> 0x003e }
    L_0x0010:
        r1 = r5.iterator();	 Catch:{ SQLException -> 0x002b, all -> 0x003e }
    L_0x0014:
        r0 = r1.hasNext();	 Catch:{ SQLException -> 0x002b, all -> 0x003e }
        if (r0 == 0) goto L_0x0036;	 Catch:{ SQLException -> 0x002b, all -> 0x003e }
    L_0x001a:
        r0 = r1.next();	 Catch:{ SQLException -> 0x002b, all -> 0x003e }
        r0 = (p031u.aly.C1506i) r0;	 Catch:{ SQLException -> 0x002b, all -> 0x003e }
        r0 = p031u.aly.C1459a.m3390a(r0);	 Catch:{ SQLException -> 0x002b, all -> 0x003e }
        r2 = "aggregated_cache";	 Catch:{ SQLException -> 0x002b, all -> 0x003e }
        r3 = 0;	 Catch:{ SQLException -> 0x002b, all -> 0x003e }
        r4.insert(r2, r3, r0);	 Catch:{ SQLException -> 0x002b, all -> 0x003e }
        goto L_0x0014;
    L_0x002b:
        r0 = move-exception;
        r0 = "insert to Aggregated cache table faild!";	 Catch:{ SQLException -> 0x002b, all -> 0x003e }
        p031u.aly.bl.m3594e(r0);	 Catch:{ SQLException -> 0x002b, all -> 0x003e }
        r0 = 0;
        r4.endTransaction();
    L_0x0035:
        return r0;
    L_0x0036:
        r4.setTransactionSuccessful();	 Catch:{ SQLException -> 0x002b, all -> 0x003e }
        r4.endTransaction();
        r0 = 1;
        goto L_0x0035;
    L_0x003e:
        r0 = move-exception;
        r4.endTransaction();
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: u.aly.a.a(android.database.sqlite.SQLiteDatabase, java.util.Collection):boolean");
    }

    private static void m3406d(android.database.sqlite.SQLiteDatabase r2, java.lang.String r3) {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1431)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r2.beginTransaction();	 Catch:{ SQLException -> 0x0028, all -> 0x002f }
        r0 = new java.lang.StringBuilder;	 Catch:{ SQLException -> 0x0028, all -> 0x002f }
        r0.<init>();	 Catch:{ SQLException -> 0x0028, all -> 0x002f }
        r1 = "update system set count=count+1 where key like '";	 Catch:{ SQLException -> 0x0028, all -> 0x002f }
        r0 = r0.append(r1);	 Catch:{ SQLException -> 0x0028, all -> 0x002f }
        r0 = r0.append(r3);	 Catch:{ SQLException -> 0x0028, all -> 0x002f }
        r1 = "'";	 Catch:{ SQLException -> 0x0028, all -> 0x002f }
        r0 = r0.append(r1);	 Catch:{ SQLException -> 0x0028, all -> 0x002f }
        r0 = r0.toString();	 Catch:{ SQLException -> 0x0028, all -> 0x002f }
        r2.execSQL(r0);	 Catch:{ SQLException -> 0x0028, all -> 0x002f }
        r2.setTransactionSuccessful();	 Catch:{ SQLException -> 0x0028, all -> 0x002f }
        if (r2 == 0) goto L_0x0027;
    L_0x0024:
        r2.endTransaction();
    L_0x0027:
        return;
    L_0x0028:
        r0 = move-exception;
        if (r2 == 0) goto L_0x0027;
    L_0x002b:
        r2.endTransaction();
        goto L_0x0027;
    L_0x002f:
        r0 = move-exception;
        if (r2 == 0) goto L_0x0035;
    L_0x0032:
        r2.endTransaction();
    L_0x0035:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: u.aly.a.d(android.database.sqlite.SQLiteDatabase, java.lang.String):void");
    }

    public static boolean m3397a(SQLiteDatabase sQLiteDatabase, String str) {
        try {
            sQLiteDatabase.execSQL("drop table if exists " + str);
            return true;
        } catch (SQLException e) {
            bl.m3594e("delete table faild!");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean m3402b(SQLiteDatabase sQLiteDatabase, String str) {
        try {
            if (C1459a.m3404c(sQLiteDatabase, str) >= 0) {
                sQLiteDatabase.execSQL("delete from " + str);
            }
            return true;
        } catch (SQLException e) {
            bl.m3594e("cleanTableData faild!" + e.toString());
            return false;
        }
    }

    public static int m3404c(SQLiteDatabase sQLiteDatabase, String str) {
        Cursor cursor = null;
        int i = 0;
        try {
            cursor = sQLiteDatabase.rawQuery("select * from " + str, null);
            i = cursor.getCount();
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            bl.m3594e("count error " + e.toString());
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return i;
    }

    public static boolean m3400a(C1950f c1950f, SQLiteDatabase sQLiteDatabase, Collection<C1506i> collection) {
        try {
            sQLiteDatabase.beginTransaction();
            for (C1506i a : collection) {
                sQLiteDatabase.insert(C1495a.f3797a, null, C1459a.m3390a(a));
            }
            sQLiteDatabase.setTransactionSuccessful();
            C1459a.m3402b(sQLiteDatabase, C1495a.f3798b);
            c1950f.mo2823a(GraphResponse.SUCCESS_KEY, false);
            return true;
        } catch (SQLException e) {
            bl.m3594e("insert to Aggregated cache table faild!");
            return false;
        } finally {
            sQLiteDatabase.endTransaction();
        }
    }

    public static boolean m3399a(SQLiteDatabase sQLiteDatabase, C1950f c1950f) {
        try {
            sQLiteDatabase.beginTransaction();
            if (C1459a.m3404c(sQLiteDatabase, C1495a.f3798b) <= 0) {
                c1950f.mo2823a("faild", false);
                return false;
            }
            sQLiteDatabase.execSQL("insert into aggregated(key, count, value, totalTimestamp, timeWindowNum, label) select key, count, value, totalTimestamp, timeWindowNum, label from aggregated_cache");
            sQLiteDatabase.setTransactionSuccessful();
            C1459a.m3402b(sQLiteDatabase, C1495a.f3798b);
            c1950f.mo2823a(GraphResponse.SUCCESS_KEY, false);
            sQLiteDatabase.endTransaction();
            return true;
        } catch (SQLException e) {
            c1950f.mo2823a(Boolean.valueOf(false), false);
            bl.m3594e("cacheToAggregatedTable happen " + e.toString());
        } finally {
            sQLiteDatabase.endTransaction();
        }
    }

    private static ContentValues m3390a(C1506i c1506i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("key", c1506i.m3775a());
        contentValues.put("label", c1506i.m3785c());
        contentValues.put("count", Long.valueOf(c1506i.m3790g()));
        contentValues.put("value", Long.valueOf(c1506i.m3789f()));
        contentValues.put(C1493a.f3786b, Long.valueOf(c1506i.m3788e()));
        contentValues.put(C1493a.f3790f, c1506i.m3791h());
        return contentValues;
    }

    public static boolean m3403b(SQLiteDatabase sQLiteDatabase, C1950f c1950f) {
        Cursor cursor = null;
        try {
            Map hashMap = new HashMap();
            cursor = sQLiteDatabase.rawQuery("select * from aggregated_cache", null);
            while (cursor.moveToNext()) {
                C1506i c1506i = new C1506i();
                c1506i.m3778a(C1502d.m3742a(cursor.getString(cursor.getColumnIndex("key"))));
                c1506i.m3784b(C1502d.m3742a(cursor.getString(cursor.getColumnIndex("label"))));
                c1506i.m3786c((long) cursor.getInt(cursor.getColumnIndex("count")));
                c1506i.m3782b((long) cursor.getInt(cursor.getColumnIndex("value")));
                c1506i.m3783b(cursor.getString(cursor.getColumnIndex(C1493a.f3790f)));
                c1506i.m3776a(Long.parseLong(cursor.getString(cursor.getColumnIndex(C1493a.f3786b))));
                hashMap.put(C1502d.m3742a(cursor.getString(cursor.getColumnIndex("key"))), c1506i);
            }
            if (hashMap.size() > 0) {
                c1950f.mo2823a(hashMap, false);
            } else {
                c1950f.mo2823a("faild", false);
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (SQLException e) {
            c1950f.mo2823a(Boolean.valueOf(false), false);
            bl.m3594e("cacheToMemory happen " + e.toString());
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    public static void m3395a(SQLiteDatabase sQLiteDatabase, boolean z, C1950f c1950f) {
        C1459a.m3402b(sQLiteDatabase, C1501c.f3810a);
        C1459a.m3402b(sQLiteDatabase, C1495a.f3797a);
        if (!z) {
            C1459a.m3402b(sQLiteDatabase, C1498b.f3801a);
            c1950f.mo2823a(GraphResponse.SUCCESS_KEY, false);
        }
    }

    public static void m3393a(SQLiteDatabase sQLiteDatabase, String str, long j, long j2) {
        try {
            int c = C1459a.m3404c(sQLiteDatabase, C1501c.f3810a);
            int c2 = C1516n.m3847a().m3854c();
            ContentValues contentValues;
            if (c < c2) {
                contentValues = new ContentValues();
                contentValues.put("key", str);
                contentValues.put(C1499a.f3803b, Long.valueOf(j2));
                contentValues.put("count", Long.valueOf(j));
                sQLiteDatabase.insert(C1501c.f3810a, null, contentValues);
            } else if (c == c2) {
                contentValues = new ContentValues();
                contentValues.put("key", C0919a.f3124u);
                contentValues.put(C1499a.f3803b, Long.valueOf(System.currentTimeMillis()));
                contentValues.put("count", Integer.valueOf(1));
                sQLiteDatabase.insert(C1501c.f3810a, null, contentValues);
            } else {
                C1459a.m3406d(sQLiteDatabase, C0919a.f3124u);
            }
        } catch (SQLException e) {
        }
    }

    public static void m3396a(C1950f c1950f, SQLiteDatabase sQLiteDatabase, List<String> list) {
        try {
            sQLiteDatabase.beginTransaction();
            if (C1459a.m3404c(sQLiteDatabase, C1498b.f3801a) > 0) {
                C1459a.m3402b(sQLiteDatabase, C1498b.f3801a);
            }
            for (String str : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(C1496a.f3799a, str);
                sQLiteDatabase.insert(C1498b.f3801a, null, contentValues);
            }
            sQLiteDatabase.setTransactionSuccessful();
            c1950f.mo2823a(GraphResponse.SUCCESS_KEY, false);
        } catch (SQLException e) {
            bl.m3594e("insertToLimitCKTable error " + e.toString());
        } finally {
            sQLiteDatabase.endTransaction();
        }
    }

    public static String m3391a(SQLiteDatabase sQLiteDatabase) {
        String valueOf;
        Cursor rawQuery;
        Object obj;
        SQLException sQLException;
        SQLException sQLException2;
        Throwable th;
        Cursor cursor = null;
        try {
            sQLiteDatabase.beginTransaction();
            if (C1459a.m3404c(sQLiteDatabase, C1495a.f3798b) <= 0) {
                valueOf = String.valueOf(0);
                if (cursor != null) {
                    cursor.close();
                }
                sQLiteDatabase.endTransaction();
            } else {
                rawQuery = sQLiteDatabase.rawQuery("select * from aggregated_cache", null);
                try {
                    if (rawQuery.moveToLast()) {
                        valueOf = rawQuery.getString(rawQuery.getColumnIndex(C1493a.f3790f));
                    } else {
                        obj = cursor;
                    }
                } catch (SQLException e) {
                    sQLException = e;
                    obj = cursor;
                    sQLException2 = sQLException;
                    try {
                        bl.m3594e("queryLastTimeWindowNumFromCache error " + sQLException2.toString());
                        if (rawQuery != null) {
                            rawQuery.close();
                        }
                        sQLiteDatabase.endTransaction();
                        return valueOf;
                    } catch (Throwable th2) {
                        th = th2;
                        if (rawQuery != null) {
                            rawQuery.close();
                        }
                        sQLiteDatabase.endTransaction();
                        throw th;
                    }
                }
                try {
                    sQLiteDatabase.setTransactionSuccessful();
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    sQLiteDatabase.endTransaction();
                } catch (SQLException e2) {
                    sQLException2 = e2;
                    bl.m3594e("queryLastTimeWindowNumFromCache error " + sQLException2.toString());
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    sQLiteDatabase.endTransaction();
                    return valueOf;
                }
            }
        } catch (SQLException e3) {
            rawQuery = cursor;
            sQLException = e3;
            valueOf = cursor;
            sQLException2 = sQLException;
            bl.m3594e("queryLastTimeWindowNumFromCache error " + sQLException2.toString());
            if (rawQuery != null) {
                rawQuery.close();
            }
            sQLiteDatabase.endTransaction();
            return valueOf;
        } catch (Throwable th3) {
            th = th3;
            rawQuery = cursor;
            if (rawQuery != null) {
                rawQuery.close();
            }
            sQLiteDatabase.endTransaction();
            throw th;
        }
        return valueOf;
    }

    public static Map<String, List<C1465e>> m3401b(SQLiteDatabase sQLiteDatabase) {
        Cursor rawQuery;
        SQLException e;
        Cursor cursor;
        Throwable th;
        Cursor cursor2 = null;
        try {
            if (C1459a.m3404c(sQLiteDatabase, C1495a.f3797a) > 0) {
                rawQuery = sQLiteDatabase.rawQuery("select * from aggregated", null);
                try {
                    Map<String, List<C1465e>> hashMap = new HashMap();
                    while (rawQuery.moveToNext()) {
                        List list;
                        String string = rawQuery.getString(rawQuery.getColumnIndex("key"));
                        if (hashMap.containsKey(string)) {
                            list = (List) hashMap.get(string);
                            hashMap.remove(string);
                        } else {
                            list = new ArrayList();
                        }
                        C1465e c1465e = new C1465e();
                        c1465e.f3614e = C1502d.m3742a(rawQuery.getString(rawQuery.getColumnIndex("label")));
                        c1465e.f3610a = rawQuery.getLong(rawQuery.getColumnIndex("value"));
                        c1465e.f3611b = rawQuery.getLong(rawQuery.getColumnIndex(C1493a.f3786b));
                        c1465e.f3612c = Integer.parseInt(rawQuery.getString(rawQuery.getColumnIndex(C1493a.f3790f)));
                        c1465e.f3613d = (int) rawQuery.getLong(rawQuery.getColumnIndex("count"));
                        list.add(c1465e);
                        hashMap.put(string, list);
                    }
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return hashMap;
                } catch (SQLException e2) {
                    e = e2;
                    cursor = rawQuery;
                } catch (Throwable th2) {
                    th = th2;
                }
            } else {
                if (cursor2 != null) {
                    cursor2.close();
                }
                return cursor2;
            }
        } catch (SQLException e3) {
            e = e3;
            cursor = cursor2;
            try {
                bl.m3594e("readAllAggregatedDataForUpload error " + e.toString());
                if (cursor != null) {
                    cursor.close();
                }
                return cursor2;
            } catch (Throwable th3) {
                th = th3;
                rawQuery = cursor;
                if (rawQuery != null) {
                    rawQuery.close();
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            rawQuery = cursor2;
            if (rawQuery != null) {
                rawQuery.close();
            }
            throw th;
        }
    }

    public static Map<String, List<C1466f>> m3392a(C1950f c1950f, SQLiteDatabase sQLiteDatabase) {
        Cursor rawQuery;
        SQLException e;
        Throwable th;
        try {
            Cursor cursor;
            Map<String, List<C1466f>> hashMap = new HashMap();
            if (C1459a.m3404c(sQLiteDatabase, C1501c.f3810a) > 0) {
                rawQuery = sQLiteDatabase.rawQuery("select * from system", null);
                while (rawQuery.moveToNext()) {
                    List list;
                    String string = rawQuery.getString(rawQuery.getColumnIndex("key"));
                    if (hashMap.containsKey(string)) {
                        list = (List) hashMap.get(string);
                        hashMap.remove(string);
                    } else {
                        try {
                            list = new ArrayList();
                        } catch (SQLException e2) {
                            e = e2;
                        }
                    }
                    C1466f c1466f = new C1466f();
                    c1466f.f3617b = rawQuery.getLong(rawQuery.getColumnIndex(C1499a.f3803b));
                    c1466f.f3616a = (int) rawQuery.getLong(rawQuery.getColumnIndex("count"));
                    list.add(c1466f);
                    hashMap.put(string, list);
                }
                cursor = rawQuery;
            } else {
                cursor = null;
            }
            if (cursor != null) {
                cursor.close();
            }
            return hashMap;
        } catch (SQLException e3) {
            e = e3;
            rawQuery = null;
            try {
                c1950f.mo2823a("faild", false);
                bl.m3594e("readAllSystemDataForUpload error " + e.toString());
                if (rawQuery == null) {
                    return null;
                }
                rawQuery.close();
                return null;
            } catch (Throwable th2) {
                th = th2;
                if (rawQuery != null) {
                    rawQuery.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            rawQuery = null;
            if (rawQuery != null) {
                rawQuery.close();
            }
            throw th;
        }
    }

    public static List<String> m3405c(SQLiteDatabase sQLiteDatabase) {
        Cursor rawQuery;
        SQLException e;
        Throwable th;
        Cursor cursor = null;
        try {
            if (C1459a.m3404c(sQLiteDatabase, C1498b.f3801a) > 0) {
                rawQuery = sQLiteDatabase.rawQuery("select * from limitedck", null);
                try {
                    List<String> arrayList = new ArrayList();
                    while (rawQuery.moveToNext()) {
                        arrayList.add(rawQuery.getString(rawQuery.getColumnIndex(C1496a.f3799a)));
                    }
                    if (rawQuery == null) {
                        return arrayList;
                    }
                    rawQuery.close();
                    return arrayList;
                } catch (SQLException e2) {
                    e = e2;
                    try {
                        bl.m3594e("loadLimitCKFromDB error " + e.toString());
                        if (rawQuery != null) {
                            rawQuery.close();
                        }
                        return cursor;
                    } catch (Throwable th2) {
                        th = th2;
                        if (rawQuery != null) {
                            rawQuery.close();
                        }
                        throw th;
                    }
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            return cursor;
        } catch (SQLException e3) {
            e = e3;
            rawQuery = cursor;
            bl.m3594e("loadLimitCKFromDB error " + e.toString());
            if (rawQuery != null) {
                rawQuery.close();
            }
            return cursor;
        } catch (Throwable th3) {
            th = th3;
            rawQuery = cursor;
            if (rawQuery != null) {
                rawQuery.close();
            }
            throw th;
        }
    }
}

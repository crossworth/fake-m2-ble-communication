package com.droi.sdk.push.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;
import com.droi.sdk.push.C1004t;
import com.droi.sdk.push.utils.C1008c;
import com.droi.sdk.push.utils.C1011f;
import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1015j;
import com.umeng.facebook.share.internal.ShareConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class C0987b {
    private static C0987b f3274a;
    private C0989d f3275b;
    private SQLiteDatabase f3276c;
    private Context f3277d;

    private C0987b(Context context) {
        this.f3277d = context.getApplicationContext();
        if (C1015j.m3172g(context)) {
            C1012g.m3141c("use external database!");
            this.f3275b = new C0989d(new C0988c(this.f3277d.getApplicationContext()));
        } else {
            C1012g.m3141c("use internal database!");
            this.f3275b = new C0989d(this.f3277d);
        }
        try {
            this.f3276c = this.f3275b.getWritableDatabase();
        } catch (Exception e) {
            C1012g.m3137a(e);
        }
    }

    private int m3025a(String str, ContentValues contentValues, String str2, String[] strArr) {
        synchronized (C0987b.class) {
            if (this.f3276c != null) {
                this.f3276c.update(str, contentValues, str2, null);
            }
        }
        return 0;
    }

    private int m3026a(String str, String str2, String[] strArr) {
        int i;
        synchronized (C0987b.class) {
            i = 0;
            if (this.f3276c != null) {
                i = this.f3276c.delete(str, str2, strArr);
            }
        }
        return i;
    }

    private long m3027a(String str, String str2, ContentValues contentValues) {
        long j;
        synchronized (C0987b.class) {
            j = -1;
            if (this.f3276c != null) {
                j = this.f3276c.insert(str, str2, contentValues);
            }
        }
        return j;
    }

    private Cursor m3028a(String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5) {
        Cursor cursor;
        synchronized (C0987b.class) {
            cursor = null;
            if (this.f3276c != null) {
                cursor = this.f3276c.query(str, strArr, str2, strArr2, str3, str4, str5);
            }
        }
        return cursor;
    }

    public static C0987b m3029a(Context context) {
        synchronized (C0987b.class) {
            if (f3274a == null) {
                f3274a = new C0987b(context);
            }
        }
        return f3274a;
    }

    private void m3030f() {
        if (!C1015j.m3172g(this.f3277d)) {
            this.f3275b.close();
            this.f3275b = null;
            this.f3275b = new C0989d(this.f3277d);
            try {
                this.f3276c = this.f3275b.getWritableDatabase();
            } catch (Exception e) {
                C1012g.m3137a(e);
            }
        } else if (!new File(C1011f.m3136b() + "/" + "push.db").exists()) {
            this.f3275b.close();
            this.f3275b = null;
            this.f3275b = new C0989d(new C0988c(this.f3277d.getApplicationContext()));
            try {
                this.f3276c = this.f3275b.getWritableDatabase();
            } catch (Exception e2) {
                C1012g.m3137a(e2);
            }
        }
    }

    public List m3031a() {
        List list;
        Exception exception;
        try {
            if (this.f3276c != null) {
                Cursor a = m3028a(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, C0986a.f3272a, null, null, null, null, null);
                if (a != null) {
                    if (a.getCount() > 0) {
                        List arrayList = new ArrayList();
                        try {
                            a.moveToFirst();
                            while (!a.isAfterLast()) {
                                arrayList.add(new C1004t(a.getLong(a.getColumnIndexOrThrow("msg_id")), a.getString(a.getColumnIndexOrThrow("appid")), a.getString(a.getColumnIndexOrThrow("tag")), a.getString(a.getColumnIndexOrThrow("content")), a.getLong(a.getColumnIndexOrThrow("interval")), a.getLong(a.getColumnIndexOrThrow("time_1")), a.getLong(a.getColumnIndexOrThrow("time_2"))));
                                a.moveToNext();
                            }
                            list = arrayList;
                        } catch (Exception e) {
                            exception = e;
                            list = arrayList;
                            C1012g.m3137a(exception);
                            m3030f();
                            return list;
                        }
                    }
                    list = null;
                    try {
                        a.close();
                        return list;
                    } catch (Exception e2) {
                        exception = e2;
                        C1012g.m3137a(exception);
                        m3030f();
                        return list;
                    }
                }
            }
            return null;
        } catch (Exception e3) {
            exception = e3;
            list = null;
            C1012g.m3137a(exception);
            m3030f();
            return list;
        }
    }

    public void m3032a(long j) {
        try {
            if (this.f3276c != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("reorgtime", Long.valueOf(j));
                m3025a("sdk", contentValues, null, null);
            }
        } catch (Exception e) {
            C1012g.m3137a(e);
            m3030f();
        }
    }

    public void m3033a(long j, String str, long j2) {
        try {
            if (this.f3276c != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("msg_id", Long.valueOf(j));
                contentValues.put("appid", str);
                contentValues.put("createtime", Long.valueOf(j2));
                m3027a("history", null, contentValues);
            }
        } catch (Exception e) {
            C1012g.m3137a(e);
            m3030f();
        }
    }

    public void m3034a(String str) {
        try {
            if (this.f3276c != null && str != null) {
                m3026a(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, "tag like '" + str + "'", null);
            }
        } catch (Exception e) {
            C1012g.m3137a(e);
            m3030f();
        }
    }

    public void m3035a(String str, long j) {
        try {
            if (this.f3276c != null) {
                String str2 = "tag like '" + str + "'";
                ContentValues contentValues = new ContentValues();
                contentValues.put("time_1", Long.valueOf(j));
                m3025a(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, contentValues, str2, null);
            }
        } catch (Exception e) {
            C1012g.m3137a(e);
            m3030f();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void m3036a(java.lang.String r11, java.lang.String r12, java.lang.String r13) {
        /*
        r10 = this;
        r8 = 0;
        r0 = r10.f3276c;
        if (r0 == 0) goto L_0x0064;
    L_0x0005:
        r0 = com.droi.sdk.push.utils.C1008c.m3127b(r11);
        r1 = com.droi.sdk.push.utils.C1008c.m3127b(r12);
        r2 = com.droi.sdk.push.utils.C1008c.m3127b(r13);
        r9 = new android.content.ContentValues;
        r9.<init>();
        r3 = "appid";
        r9.put(r3, r0);
        r3 = "secret";
        r9.put(r3, r1);
        r1 = "package";
        r9.put(r1, r2);
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "appid like '";
        r1 = r1.append(r2);
        r0 = r1.append(r0);
        r1 = "'";
        r0 = r0.append(r1);
        r3 = r0.toString();
        r1 = "appinfo";
        r0 = 1;
        r2 = new java.lang.String[r0];	 Catch:{ Exception -> 0x0065 }
        r0 = 0;
        r4 = "appid";
        r2[r0] = r4;	 Catch:{ Exception -> 0x0065 }
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r0 = r10;
        r1 = r0.m3028a(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Exception -> 0x0065 }
        if (r1 == 0) goto L_0x0059;
    L_0x0053:
        r0 = r1.getCount();	 Catch:{ Exception -> 0x00a2, all -> 0x009f }
        if (r0 != 0) goto L_0x005f;
    L_0x0059:
        r0 = "appinfo";
        r2 = 0;
        r10.m3027a(r0, r2, r9);	 Catch:{ Exception -> 0x00a2, all -> 0x009f }
    L_0x005f:
        if (r1 == 0) goto L_0x0064;
    L_0x0061:
        r1.close();
    L_0x0064:
        return;
    L_0x0065:
        r0 = move-exception;
    L_0x0066:
        com.droi.sdk.push.utils.C1012g.m3137a(r0);	 Catch:{ all -> 0x0098 }
        r10.m3030f();	 Catch:{ all -> 0x0098 }
        r1 = "appinfo";
        r0 = 1;
        r2 = new java.lang.String[r0];	 Catch:{ Exception -> 0x0093 }
        r0 = 0;
        r4 = "appid";
        r2[r0] = r4;	 Catch:{ Exception -> 0x0093 }
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r0 = r10;
        r8 = r0.m3028a(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Exception -> 0x0093 }
        if (r8 == 0) goto L_0x0087;
    L_0x0081:
        r0 = r8.getCount();	 Catch:{ Exception -> 0x0093 }
        if (r0 != 0) goto L_0x008d;
    L_0x0087:
        r0 = "appinfo";
        r1 = 0;
        r10.m3027a(r0, r1, r9);	 Catch:{ Exception -> 0x0093 }
    L_0x008d:
        if (r8 == 0) goto L_0x0064;
    L_0x008f:
        r8.close();
        goto L_0x0064;
    L_0x0093:
        r0 = move-exception;
        com.droi.sdk.push.utils.C1012g.m3137a(r0);	 Catch:{ all -> 0x0098 }
        goto L_0x008d;
    L_0x0098:
        r0 = move-exception;
    L_0x0099:
        if (r8 == 0) goto L_0x009e;
    L_0x009b:
        r8.close();
    L_0x009e:
        throw r0;
    L_0x009f:
        r0 = move-exception;
        r8 = r1;
        goto L_0x0099;
    L_0x00a2:
        r0 = move-exception;
        r8 = r1;
        goto L_0x0066;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.push.data.b.a(java.lang.String, java.lang.String, java.lang.String):void");
    }

    public boolean m3037a(C1004t c1004t) {
        if (c1004t == null) {
            return false;
        }
        try {
            if (this.f3276c == null || !this.f3276c.isOpen()) {
                return false;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("msg_id", Long.valueOf(c1004t.f3327a));
            contentValues.put("appid", c1004t.f3329c);
            contentValues.put("tag", c1004t.f3337k);
            contentValues.put("content", c1004t.m3093a());
            contentValues.put("interval", Long.valueOf(c1004t.f3330d));
            contentValues.put("time_1", Long.valueOf(c1004t.f3331e));
            contentValues.put("time_2", Long.valueOf(c1004t.f3332f));
            m3027a(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, null, contentValues);
            return true;
        } catch (Exception e) {
            C1012g.m3137a(e);
            m3030f();
            return false;
        }
    }

    public HashMap m3038b() {
        HashMap hashMap = new HashMap();
        try {
            if (this.f3276c != null) {
                Cursor a = m3028a("appinfo", null, null, null, null, null, null);
                if (a != null) {
                    if (a.getCount() > 0) {
                        a.moveToFirst();
                        while (!a.isAfterLast()) {
                            hashMap.put(C1008c.m3126a(a.getString(a.getColumnIndexOrThrow("appid"))), new Pair(C1008c.m3126a(a.getString(a.getColumnIndexOrThrow("secret"))), C1008c.m3126a(a.getString(a.getColumnIndexOrThrow("package")))));
                            a.moveToNext();
                        }
                    }
                    a.close();
                }
            }
        } catch (Exception e) {
            C1012g.m3137a(e);
            m3030f();
        }
        return hashMap;
    }

    public void m3039b(String str, long j) {
        try {
            m3026a("history", "appid like '" + str + "' AND " + "msg_id" + "=" + j, null);
        } catch (Exception e) {
            C1012g.m3137a(e);
            m3030f();
        }
    }

    public Cursor m3040c() {
        try {
            String[] strArr = new String[]{"appid", "msg_id", "createtime"};
            if (this.f3276c != null) {
                return m3028a("history", strArr, null, null, null, null, null);
            }
        } catch (Exception e) {
            C1012g.m3137a(e);
            m3030f();
        }
        return null;
    }

    public long m3041d() {
        long j;
        Exception e;
        try {
            String[] strArr = new String[]{"reorgtime"};
            if (this.f3276c != null) {
                Cursor a = m3028a("sdk", strArr, null, null, null, null, null);
                if (a != null) {
                    if (a.getCount() > 0) {
                        a.moveToFirst();
                        j = a.getLong(a.getColumnIndexOrThrow("reorgtime"));
                    } else {
                        j = 0;
                    }
                    try {
                        a.close();
                        return j;
                    } catch (Exception e2) {
                        e = e2;
                    }
                }
            }
            return 0;
        } catch (Exception e3) {
            e = e3;
            j = 0;
            C1012g.m3137a(e);
            m3030f();
            return j;
        }
    }

    public void m3042e() {
        if (this.f3276c != null) {
            try {
                this.f3276c.close();
            } catch (Exception e) {
                C1012g.m3137a(e);
            }
            this.f3276c = null;
        }
        if (this.f3275b != null) {
            try {
                this.f3275b.close();
            } catch (Exception e2) {
                C1012g.m3137a(e2);
            }
            this.f3275b = null;
        }
    }
}

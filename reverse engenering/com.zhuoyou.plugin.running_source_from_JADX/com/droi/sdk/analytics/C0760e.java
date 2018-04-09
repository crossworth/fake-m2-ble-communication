package com.droi.sdk.analytics;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.AnalyticsCoreHelper;
import com.tencent.stat.DeviceInfo;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;

class C0760e {
    private Context f2285a;
    private C0759c f2286b;

    public static class C0757a {
        public String f2276a;
        public long f2277b;
        public String f2278c;
        public String f2279d;
        public String f2280e;
        public String f2281f;
        public int f2282g = 0;
        public int f2283h = 1;
    }

    public static class C0758b extends ArrayList<C0757a> {
    }

    static class C0759c extends SQLiteOpenHelper {
        C0775k f2284a = new C0775k(1, 0, 2, "m06");

        C0759c(Context context, String str) {
            super(context, str, null, 1);
        }

        private JSONObject m2338a(C0757a c0757a) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("did", AnalyticsCoreHelper.getDeviceId());
                jSONObject.put("t", c0757a.f2277b);
                if (c0757a.f2283h == 1) {
                    jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_ST, C0755c.m2332b(c0757a.f2278c));
                } else {
                    jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_ST, C0755c.m2332b(C0755c.m2324a(c0757a.f2278c, 100)));
                }
                jSONObject.put("md5", c0757a.f2281f);
                jSONObject.put("a03", AnalyticsCoreHelper.getAppId());
                jSONObject.put("a04", c0757a.f2279d);
                jSONObject.put("a05", c0757a.f2280e);
                return jSONObject;
            } catch (Exception e) {
                C0753a.m2311a("CrashManager", e);
                return null;
            }
        }

        private void m2339a(SQLiteDatabase sQLiteDatabase, String str, C0757a c0757a) {
            try {
                sQLiteDatabase.execSQL(String.format("UPDATE %s SET t = %d, hasSend = 0, firstSend = 0 WHERE md5 = '%s' and a05 = '%s'", new Object[]{str, Long.valueOf(c0757a.f2277b), c0757a.f2281f, c0757a.f2280e}));
            } catch (Exception e) {
                C0753a.m2311a("CrashManager", e);
            }
        }

        private void m2340a(SQLiteDatabase sQLiteDatabase, String str, C0758b c0758b) {
            if (c0758b != null) {
                try {
                    C0753a.m2312a("CrashManager", "list size:" + c0758b.size());
                    Iterator it = c0758b.iterator();
                    while (it.hasNext()) {
                        C0757a c0757a = (C0757a) it.next();
                        int b = C0760e.m2348b();
                        if (b >= 20) {
                            C0753a.m2312a("CrashManager", "exceed maximum");
                            return;
                        }
                        JSONObject jSONObject = new JSONObject();
                        JSONObject a = m2338a(c0757a);
                        if (a != null) {
                            jSONObject.put("mt", "m06");
                            jSONObject.put(DeviceInfo.TAG_MAC, a);
                            C0760e.m2349b(b + 1);
                            m2342b(sQLiteDatabase, str, c0757a);
                            C0770f.m2357c(this.f2284a.toString(), jSONObject.toString());
                        } else {
                            return;
                        }
                    }
                } catch (Exception e) {
                    C0753a.m2311a("CrashManager", e);
                }
            }
        }

        private void m2341a(String str, SQLiteDatabase sQLiteDatabase) {
            try {
                sQLiteDatabase.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (%s)", new Object[]{str, "t INTEGER,st TEXT,a04 TEXT,a05 TEXT,md5 TEXT,hasSend INTEGER,firstSend INTEGER"}));
            } catch (Exception e) {
                C0753a.m2311a("CrashManager", e);
            }
        }

        private void m2342b(SQLiteDatabase sQLiteDatabase, String str, C0757a c0757a) {
            try {
                sQLiteDatabase.execSQL(String.format("UPDATE %s SET hasSend = 1 WHERE md5 = '%s' and a05 = '%s'", new Object[]{str, c0757a.f2281f, c0757a.f2280e}));
            } catch (Exception e) {
                C0753a.m2311a("CrashManager", e);
            }
        }

        private void m2343b(String str, SQLiteDatabase sQLiteDatabase) {
            try {
                sQLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS %s", new Object[]{str}));
            } catch (Exception e) {
                C0753a.m2311a("CrashManager", e);
            }
        }

        DroiError m2344a(String str, C0757a c0757a) {
            DroiError droiError = new DroiError();
            SQLiteDatabase sQLiteDatabase = null;
            try {
                sQLiteDatabase = getWritableDatabase();
                if (m2346a(sQLiteDatabase, str, c0757a, droiError)) {
                    C0753a.m2312a("CrashManager", "Exist");
                    m2339a(sQLiteDatabase, str, c0757a);
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.close();
                    }
                } else {
                    sQLiteDatabase.execSQL(String.format("INSERT INTO %s (%s) VALUES(%s)", new Object[]{str, "t,st,a04,a05,md5,hasSend,firstSend", c0757a.f2277b + ",'" + c0757a.f2278c + "','" + c0757a.f2279d + "','" + c0757a.f2280e + "','" + c0757a.f2281f + "'," + c0757a.f2282g + "," + c0757a.f2283h}));
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.close();
                    }
                }
            } catch (Exception e) {
                C0753a.m2311a("CrashManager", e);
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage("PutRecord fail. " + e);
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            } catch (Throwable th) {
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            }
            return droiError;
        }

        C0758b m2345a(String str, int i, DroiError droiError) {
            Cursor rawQuery;
            Object e;
            Throwable th;
            String format = String.format("SELECT rowid, * FROM %s WHERE %s = %d", new Object[]{str, "hasSend", Integer.valueOf(i)});
            SQLiteDatabase writableDatabase;
            try {
                writableDatabase = getWritableDatabase();
                try {
                    rawQuery = writableDatabase.rawQuery(format, null);
                } catch (Exception e2) {
                    e = e2;
                    rawQuery = null;
                    if (droiError != null) {
                        try {
                            droiError.setCode(DroiError.ERROR);
                            droiError.setAppendedMessage("get Record fail. " + e);
                        } catch (Throwable th2) {
                            th = th2;
                            if (writableDatabase != null) {
                                writableDatabase.close();
                            }
                            if (rawQuery != null) {
                                rawQuery.close();
                            }
                            throw th;
                        }
                    }
                    if (writableDatabase != null) {
                        writableDatabase.close();
                    }
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return null;
                } catch (Throwable th3) {
                    th = th3;
                    rawQuery = null;
                    if (writableDatabase != null) {
                        writableDatabase.close();
                    }
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    throw th;
                }
                try {
                    C0758b c0758b = new C0758b();
                    if (rawQuery != null) {
                        while (rawQuery.moveToNext()) {
                            C0757a c0757a = new C0757a();
                            c0757a.f2276a = rawQuery.getString(0);
                            c0757a.f2277b = rawQuery.getLong(1);
                            c0757a.f2278c = rawQuery.getString(2);
                            c0757a.f2279d = rawQuery.getString(3);
                            c0757a.f2280e = rawQuery.getString(4);
                            c0757a.f2281f = rawQuery.getString(5);
                            c0757a.f2282g = rawQuery.getInt(6);
                            c0757a.f2283h = rawQuery.getInt(7);
                            c0758b.add(c0757a);
                        }
                    }
                    m2340a(writableDatabase, str, c0758b);
                    if (droiError != null) {
                        droiError.setCode(0);
                        droiError.setAppendedMessage(null);
                    }
                    if (writableDatabase != null) {
                        writableDatabase.close();
                    }
                    if (rawQuery == null) {
                        return c0758b;
                    }
                    rawQuery.close();
                    return c0758b;
                } catch (Exception e3) {
                    e = e3;
                    if (droiError != null) {
                        droiError.setCode(DroiError.ERROR);
                        droiError.setAppendedMessage("get Record fail. " + e);
                    }
                    if (writableDatabase != null) {
                        writableDatabase.close();
                    }
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return null;
                }
            } catch (Exception e4) {
                e = e4;
                writableDatabase = null;
                rawQuery = null;
                if (droiError != null) {
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage("get Record fail. " + e);
                }
                if (writableDatabase != null) {
                    writableDatabase.close();
                }
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return null;
            } catch (Throwable th4) {
                th = th4;
                writableDatabase = null;
                rawQuery = null;
                if (writableDatabase != null) {
                    writableDatabase.close();
                }
                if (rawQuery != null) {
                    rawQuery.close();
                }
                throw th;
            }
        }

        boolean m2346a(android.database.sqlite.SQLiteDatabase r8, java.lang.String r9, com.droi.sdk.analytics.C0760e.C0757a r10, com.droi.sdk.DroiError r11) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0033 in list [B:9:0x0030]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r7 = this;
            r0 = 1;
            r2 = 0;
            r1 = 0;
            r3 = "SELECT rowid, * FROM %s WHERE md5 = '%s' and a05 = '%s'";
            r4 = 3;
            r4 = new java.lang.Object[r4];
            r4[r1] = r9;
            r5 = r10.f2281f;
            r4[r0] = r5;
            r5 = 2;
            r6 = r10.f2280e;
            r4[r5] = r6;
            r3 = java.lang.String.format(r3, r4);
            r4 = 0;
            r2 = r8.rawQuery(r3, r4);	 Catch:{ Exception -> 0x0036, all -> 0x0061 }
            if (r2 == 0) goto L_0x0068;	 Catch:{ Exception -> 0x0036, all -> 0x0061 }
        L_0x001e:
            r3 = r2.getCount();	 Catch:{ Exception -> 0x0036, all -> 0x0061 }
        L_0x0022:
            if (r11 == 0) goto L_0x002c;	 Catch:{ Exception -> 0x0036, all -> 0x0061 }
        L_0x0024:
            r4 = 0;	 Catch:{ Exception -> 0x0036, all -> 0x0061 }
            r11.setCode(r4);	 Catch:{ Exception -> 0x0036, all -> 0x0061 }
            r4 = 0;	 Catch:{ Exception -> 0x0036, all -> 0x0061 }
            r11.setAppendedMessage(r4);	 Catch:{ Exception -> 0x0036, all -> 0x0061 }
        L_0x002c:
            if (r3 <= 0) goto L_0x0034;
        L_0x002e:
            if (r2 == 0) goto L_0x0033;
        L_0x0030:
            r2.close();
        L_0x0033:
            return r0;
        L_0x0034:
            r0 = r1;
            goto L_0x002e;
        L_0x0036:
            r0 = move-exception;
            r3 = "CrashManager";	 Catch:{ Exception -> 0x0036, all -> 0x0061 }
            com.droi.sdk.analytics.C0753a.m2311a(r3, r0);	 Catch:{ Exception -> 0x0036, all -> 0x0061 }
            if (r11 == 0) goto L_0x005a;	 Catch:{ Exception -> 0x0036, all -> 0x0061 }
        L_0x003e:
            r3 = 1070001; // 0x1053b1 float:1.499391E-39 double:5.286507E-318;	 Catch:{ Exception -> 0x0036, all -> 0x0061 }
            r11.setCode(r3);	 Catch:{ Exception -> 0x0036, all -> 0x0061 }
            r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0036, all -> 0x0061 }
            r3.<init>();	 Catch:{ Exception -> 0x0036, all -> 0x0061 }
            r4 = "get Record fail. ";	 Catch:{ Exception -> 0x0036, all -> 0x0061 }
            r3 = r3.append(r4);	 Catch:{ Exception -> 0x0036, all -> 0x0061 }
            r0 = r3.append(r0);	 Catch:{ Exception -> 0x0036, all -> 0x0061 }
            r0 = r0.toString();	 Catch:{ Exception -> 0x0036, all -> 0x0061 }
            r11.setAppendedMessage(r0);	 Catch:{ Exception -> 0x0036, all -> 0x0061 }
        L_0x005a:
            if (r2 == 0) goto L_0x005f;
        L_0x005c:
            r2.close();
        L_0x005f:
            r0 = r1;
            goto L_0x0033;
        L_0x0061:
            r0 = move-exception;
            if (r2 == 0) goto L_0x0067;
        L_0x0064:
            r2.close();
        L_0x0067:
            throw r0;
        L_0x0068:
            r3 = r1;
            goto L_0x0022;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.analytics.e.c.a(android.database.sqlite.SQLiteDatabase, java.lang.String, com.droi.sdk.analytics.e$a, com.droi.sdk.DroiError):boolean");
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            m2341a("crashlog_table", sQLiteDatabase);
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            m2343b("crashlog_table", sQLiteDatabase);
            m2341a("crashlog_table", sQLiteDatabase);
        }
    }

    public C0760e(Context context) {
        this.f2285a = context;
        this.f2286b = new C0759c(context, "crashlog");
    }

    protected static int m2348b() {
        String b = new C0779o(C0770f.f2324a).m2407b("error_count", "");
        int i = -1;
        long j = -1;
        if (!b.isEmpty()) {
            try {
                String[] split = b.split("::");
                j = Long.valueOf(split[0]).longValue();
                i = Integer.valueOf(split[1]).intValue();
            } catch (Exception e) {
                C0753a.m2311a("CrashManager", e);
            }
        }
        long b2 = C0755c.m2329b();
        if (i >= 0 && r0 >= 0 && b2 - r0 < 3600000) {
            return i;
        }
        C0760e.m2349b(0);
        return 0;
    }

    private static void m2349b(int i) {
        new C0779o(C0770f.f2324a).m2405a("error_count", C0755c.m2329b() + "::" + i);
    }

    public void m2350a() {
        C0753a.m2312a("CrashManager", "sendCrashInfo");
        this.f2286b.m2345a("crashlog_table", 0, null);
    }

    public void m2351a(C0757a c0757a) {
        this.f2286b.m2344a("crashlog_table", c0757a);
    }
}

package com.baidu.location.p011e;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.net.wifi.ScanResult;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import com.baidu.location.BDLocation;
import com.baidu.location.C0455f;
import com.baidu.location.Jni;
import com.baidu.location.p005a.C0332a;
import com.baidu.location.p005a.C0351i;
import com.baidu.location.p006h.C0459b;
import com.baidu.location.p006h.C0468j;
import com.baidu.location.p012f.C0441a;
import com.baidu.location.p012f.C0443b;
import com.baidu.location.p012f.C0451g;
import com.baidu.location.p012f.C0454h;
import com.umeng.facebook.share.internal.ShareConstants;
import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONObject;

public final class C0411a {
    private static C0411a f574b = null;
    private static final String f575l = (Environment.getExternalStorageDirectory().getPath() + "/baidu/tempdata/");
    private static final String f576m = (Environment.getExternalStorageDirectory().getPath() + "/baidu/tempdata" + "/ls.db");
    public boolean f577a = false;
    private String f578c = null;
    private boolean f579d = false;
    private boolean f580e = false;
    private double f581f = 0.0d;
    private double f582g = 0.0d;
    private double f583h = 0.0d;
    private double f584i = 0.0d;
    private double f585j = 0.0d;
    private volatile boolean f586k = false;
    private Handler f587n = null;

    private class C0409a extends AsyncTask<Boolean, Void, Boolean> {
        final /* synthetic */ C0411a f572a;

        private C0409a(C0411a c0411a) {
            this.f572a = c0411a;
        }

        protected Boolean m635a(Boolean... boolArr) {
            SQLiteDatabase sQLiteDatabase = null;
            if (boolArr.length != 4) {
                return Boolean.valueOf(false);
            }
            try {
                sQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(C0411a.f576m, null);
            } catch (Exception e) {
            }
            if (sQLiteDatabase == null) {
                return Boolean.valueOf(false);
            }
            int currentTimeMillis = (int) (System.currentTimeMillis() >> 28);
            try {
                sQLiteDatabase.beginTransaction();
                if (boolArr[0].booleanValue()) {
                    try {
                        sQLiteDatabase.execSQL("delete from wof where ac < " + (currentTimeMillis - 35));
                    } catch (Exception e2) {
                    }
                }
                if (boolArr[1].booleanValue()) {
                    try {
                        sQLiteDatabase.execSQL("delete from bdcltb09 where ac is NULL or ac < " + (currentTimeMillis - 130));
                    } catch (Exception e3) {
                    }
                }
                sQLiteDatabase.setTransactionSuccessful();
                sQLiteDatabase.endTransaction();
                sQLiteDatabase.close();
            } catch (Exception e4) {
            }
            return Boolean.valueOf(true);
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return m635a((Boolean[]) objArr);
        }
    }

    private class C0410b extends AsyncTask<Object, Void, Boolean> {
        final /* synthetic */ C0411a f573a;

        private C0410b(C0411a c0411a) {
            this.f573a = c0411a;
        }

        protected Boolean m636a(Object... objArr) {
            SQLiteDatabase sQLiteDatabase = null;
            if (objArr.length != 4) {
                this.f573a.f586k = false;
                return Boolean.valueOf(false);
            }
            SQLiteDatabase openOrCreateDatabase;
            try {
                openOrCreateDatabase = SQLiteDatabase.openOrCreateDatabase(C0411a.f576m, null);
            } catch (Exception e) {
                openOrCreateDatabase = sQLiteDatabase;
            }
            if (openOrCreateDatabase == null) {
                this.f573a.f586k = false;
                return Boolean.valueOf(false);
            }
            try {
                openOrCreateDatabase.beginTransaction();
                this.f573a.m645a((String) objArr[0], (C0441a) objArr[1], openOrCreateDatabase);
                this.f573a.m643a((C0451g) objArr[2], (BDLocation) objArr[3], openOrCreateDatabase);
                openOrCreateDatabase.setTransactionSuccessful();
                openOrCreateDatabase.endTransaction();
                openOrCreateDatabase.close();
            } catch (Exception e2) {
            }
            this.f573a.f586k = false;
            return Boolean.valueOf(true);
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return m636a(objArr);
        }
    }

    private C0411a() {
        m652d();
    }

    public static synchronized C0411a m637a() {
        C0411a c0411a;
        synchronized (C0411a.class) {
            if (f574b == null) {
                f574b = new C0411a();
            }
            c0411a = f574b;
        }
        return c0411a;
    }

    private void m643a(C0451g c0451g, BDLocation bDLocation, SQLiteDatabase sQLiteDatabase) {
        if (bDLocation != null && bDLocation.getLocType() == 161) {
            if (("wf".equals(bDLocation.getNetworkLocationType()) || bDLocation.getRadius() < 300.0f) && c0451g.f802a != null) {
                int currentTimeMillis = (int) (System.currentTimeMillis() >> 28);
                System.currentTimeMillis();
                int i = 0;
                for (ScanResult scanResult : c0451g.f802a) {
                    if (scanResult.level != 0) {
                        int i2 = i + 1;
                        if (i2 <= 6) {
                            ContentValues contentValues = new ContentValues();
                            String encode2 = Jni.encode2(scanResult.BSSID.replace(":", ""));
                            try {
                                int i3;
                                int i4;
                                double d;
                                Object obj;
                                double d2;
                                Cursor rawQuery = sQLiteDatabase.rawQuery("select * from wof where id = \"" + encode2 + "\";", null);
                                if (rawQuery == null || !rawQuery.moveToFirst()) {
                                    i3 = 0;
                                    i4 = 0;
                                    d = 0.0d;
                                    obj = null;
                                    d2 = 0.0d;
                                } else {
                                    double d3 = rawQuery.getDouble(1) - 113.2349d;
                                    double d4 = rawQuery.getDouble(2) - 432.1238d;
                                    int i5 = rawQuery.getInt(4);
                                    i3 = rawQuery.getInt(5);
                                    i4 = i5;
                                    d = d3;
                                    double d5 = d4;
                                    obj = 1;
                                    d2 = d5;
                                }
                                if (rawQuery != null) {
                                    rawQuery.close();
                                }
                                if (obj == null) {
                                    contentValues.put("mktime", Double.valueOf(bDLocation.getLongitude() + 113.2349d));
                                    contentValues.put(LogColumns.TIME, Double.valueOf(bDLocation.getLatitude() + 432.1238d));
                                    contentValues.put("bc", Integer.valueOf(1));
                                    contentValues.put("cc", Integer.valueOf(1));
                                    contentValues.put("ac", Integer.valueOf(currentTimeMillis));
                                    contentValues.put(ShareConstants.WEB_DIALOG_PARAM_ID, encode2);
                                    sQLiteDatabase.insert("wof", null, contentValues);
                                } else if (i3 == 0) {
                                    i = i2;
                                } else {
                                    float[] fArr = new float[1];
                                    Location.distanceBetween(d2, d, bDLocation.getLatitude(), bDLocation.getLongitude(), fArr);
                                    if (fArr[0] > 1500.0f) {
                                        int i6 = i3 + 1;
                                        if (i6 <= 10 || i6 <= i4 * 3) {
                                            contentValues.put("cc", Integer.valueOf(i6));
                                        } else {
                                            contentValues.put("mktime", Double.valueOf(bDLocation.getLongitude() + 113.2349d));
                                            contentValues.put(LogColumns.TIME, Double.valueOf(bDLocation.getLatitude() + 432.1238d));
                                            contentValues.put("bc", Integer.valueOf(1));
                                            contentValues.put("cc", Integer.valueOf(1));
                                            contentValues.put("ac", Integer.valueOf(currentTimeMillis));
                                        }
                                    } else {
                                        d2 = ((d2 * ((double) i4)) + bDLocation.getLatitude()) / ((double) (i4 + 1));
                                        ContentValues contentValues2 = contentValues;
                                        contentValues2.put("mktime", Double.valueOf((((d * ((double) i4)) + bDLocation.getLongitude()) / ((double) (i4 + 1))) + 113.2349d));
                                        contentValues.put(LogColumns.TIME, Double.valueOf(d2 + 432.1238d));
                                        contentValues.put("bc", Integer.valueOf(i4 + 1));
                                        contentValues.put("ac", Integer.valueOf(currentTimeMillis));
                                    }
                                    try {
                                        if (sQLiteDatabase.update("wof", contentValues, "id = \"" + encode2 + "\"", null) <= 0) {
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                            } catch (Exception e2) {
                            }
                            i = i2;
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m644a(java.lang.String r8, android.database.sqlite.SQLiteDatabase r9) {
        /*
        r7 = this;
        r0 = 0;
        if (r8 == 0) goto L_0x000b;
    L_0x0003:
        r1 = r7.f578c;
        r1 = r8.equals(r1);
        if (r1 == 0) goto L_0x000c;
    L_0x000b:
        return;
    L_0x000c:
        r1 = 0;
        r7.f579d = r1;
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0067, all -> 0x0070 }
        r1.<init>();	 Catch:{ Exception -> 0x0067, all -> 0x0070 }
        r2 = "select * from bdcltb09 where id = \"";
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x0067, all -> 0x0070 }
        r1 = r1.append(r8);	 Catch:{ Exception -> 0x0067, all -> 0x0070 }
        r2 = "\";";
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x0067, all -> 0x0070 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x0067, all -> 0x0070 }
        r2 = 0;
        r0 = r9.rawQuery(r1, r2);	 Catch:{ Exception -> 0x0067, all -> 0x0070 }
        r7.f578c = r8;	 Catch:{ Exception -> 0x0067, all -> 0x007c }
        r1 = r0.moveToFirst();	 Catch:{ Exception -> 0x0067, all -> 0x007c }
        if (r1 == 0) goto L_0x005f;
    L_0x0035:
        r1 = 1;
        r2 = r0.getDouble(r1);	 Catch:{ Exception -> 0x0067, all -> 0x007c }
        r4 = 4653148304163072062; // 0x40934dbaacd9e83e float:-6.193295E-12 double:1235.4323;
        r2 = r2 - r4;
        r7.f582g = r2;	 Catch:{ Exception -> 0x0067, all -> 0x007c }
        r1 = 2;
        r2 = r0.getDouble(r1);	 Catch:{ Exception -> 0x0067, all -> 0x007c }
        r4 = 4661478502002851840; // 0x40b0e60000000000 float:0.0 double:4326.0;
        r2 = r2 - r4;
        r7.f581f = r2;	 Catch:{ Exception -> 0x0067, all -> 0x007c }
        r1 = 3;
        r2 = r0.getDouble(r1);	 Catch:{ Exception -> 0x0067, all -> 0x007c }
        r4 = 4657424210545395263; // 0x40a27ea4b5dcc63f float:-1.6448975E-6 double:2367.3217;
        r2 = r2 - r4;
        r7.f583h = r2;	 Catch:{ Exception -> 0x0067, all -> 0x007c }
        r1 = 1;
        r7.f579d = r1;	 Catch:{ Exception -> 0x0067, all -> 0x007c }
    L_0x005f:
        if (r0 == 0) goto L_0x000b;
    L_0x0061:
        r0.close();	 Catch:{ Exception -> 0x0065 }
        goto L_0x000b;
    L_0x0065:
        r0 = move-exception;
        goto L_0x000b;
    L_0x0067:
        r1 = move-exception;
        if (r0 == 0) goto L_0x000b;
    L_0x006a:
        r0.close();	 Catch:{ Exception -> 0x006e }
        goto L_0x000b;
    L_0x006e:
        r0 = move-exception;
        goto L_0x000b;
    L_0x0070:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
    L_0x0074:
        if (r1 == 0) goto L_0x0079;
    L_0x0076:
        r1.close();	 Catch:{ Exception -> 0x007a }
    L_0x0079:
        throw r0;
    L_0x007a:
        r1 = move-exception;
        goto L_0x0079;
    L_0x007c:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x0074;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.e.a.a(java.lang.String, android.database.sqlite.SQLiteDatabase):void");
    }

    private void m645a(String str, C0441a c0441a, SQLiteDatabase sQLiteDatabase) {
        Object obj = null;
        double d = 0.0d;
        if (c0441a.m843b() && C0351i.m280c().m304h()) {
            System.currentTimeMillis();
            int currentTimeMillis = (int) (System.currentTimeMillis() >> 28);
            String g = c0441a.m848g();
            try {
                double parseDouble;
                float parseFloat;
                JSONObject jSONObject = new JSONObject(str);
                int parseInt = Integer.parseInt(jSONObject.getJSONObject("result").getString("error"));
                int i;
                if (parseInt == 161) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("content");
                    if (jSONObject2.has("clf")) {
                        String string = jSONObject2.getString("clf");
                        if (string.equals("0")) {
                            JSONObject jSONObject3 = jSONObject2.getJSONObject("point");
                            d = Double.parseDouble(jSONObject3.getString("x"));
                            parseDouble = Double.parseDouble(jSONObject3.getString("y"));
                            parseFloat = Float.parseFloat(jSONObject2.getString("radius"));
                        } else {
                            String[] split = string.split("\\|");
                            d = Double.parseDouble(split[0]);
                            parseDouble = Double.parseDouble(split[1]);
                            parseFloat = Float.parseFloat(split[2]);
                        }
                    }
                    i = 1;
                    parseFloat = 0.0f;
                    parseDouble = 0.0d;
                } else {
                    if (parseInt == BDLocation.TypeServerError) {
                        sQLiteDatabase.delete("bdcltb09", "id = \"" + g + "\"", null);
                        return;
                    }
                    i = 1;
                    parseFloat = 0.0f;
                    parseDouble = 0.0d;
                }
                if (obj == null) {
                    d += 1235.4323d;
                    parseDouble += 2367.3217d;
                    float f = 4326.0f + parseFloat;
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(LogColumns.TIME, Double.valueOf(d));
                    contentValues.put("tag", Float.valueOf(f));
                    contentValues.put("type", Double.valueOf(parseDouble));
                    contentValues.put("ac", Integer.valueOf(currentTimeMillis));
                    try {
                        if (sQLiteDatabase.update("bdcltb09", contentValues, "id = \"" + g + "\"", null) <= 0) {
                            contentValues.put(ShareConstants.WEB_DIALOG_PARAM_ID, g);
                            sQLiteDatabase.insert("bdcltb09", null, contentValues);
                        }
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e2) {
            }
        }
    }

    private void m646a(String str, List<ScanResult> list) {
        SQLiteDatabase sQLiteDatabase;
        if (str == null || str.equals(this.f578c)) {
            sQLiteDatabase = null;
        } else {
            sQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(f576m, null);
            m644a(str, sQLiteDatabase);
        }
        if (list != null) {
            if (sQLiteDatabase == null) {
                sQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(f576m, null);
            }
            m647a((List) list, sQLiteDatabase);
        }
        if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
            sQLiteDatabase.close();
        }
    }

    private void m647a(List<ScanResult> list, SQLiteDatabase sQLiteDatabase) {
        Throwable th;
        System.currentTimeMillis();
        this.f580e = false;
        if (list != null && sQLiteDatabase != null && list != null) {
            double d = 0.0d;
            double d2 = 0.0d;
            int i = 0;
            Object obj = null;
            double[] dArr = new double[8];
            Object obj2 = null;
            int i2 = 0;
            StringBuffer stringBuffer = new StringBuffer();
            int i3 = 0;
            for (ScanResult scanResult : list) {
                if (i3 > 10) {
                    break;
                }
                if (i3 > 0) {
                    stringBuffer.append(",");
                }
                i3++;
                stringBuffer.append("\"").append(Jni.encode2(scanResult.BSSID.replace(":", ""))).append("\"");
            }
            Cursor cursor = null;
            Cursor rawQuery;
            try {
                rawQuery = sQLiteDatabase.rawQuery("select * from wof where id in (" + stringBuffer.toString() + ");", null);
                try {
                    if (rawQuery.moveToFirst()) {
                        while (!rawQuery.isAfterLast()) {
                            double d3 = rawQuery.getDouble(1) - 113.2349d;
                            double d4 = rawQuery.getDouble(2) - 432.1238d;
                            int i4 = rawQuery.getInt(4);
                            int i5 = rawQuery.getInt(5);
                            if (i5 <= 8 || i5 <= i4) {
                                int i6;
                                Object obj3;
                                float[] fArr;
                                if (!this.f579d) {
                                    if (obj == null) {
                                        int i7;
                                        if (obj2 != null) {
                                            int i8 = 0;
                                            while (i8 < i2) {
                                                Object obj4;
                                                double d5;
                                                double d6;
                                                fArr = new float[1];
                                                Location.distanceBetween(d4, d3, dArr[i8 + 1], dArr[i8], fArr);
                                                if (fArr[0] < 1000.0f) {
                                                    obj4 = 1;
                                                    d5 = d + dArr[i8];
                                                    d6 = dArr[i8 + 1] + d2;
                                                    i5 = i + 1;
                                                } else {
                                                    obj4 = obj;
                                                    i5 = i;
                                                    d6 = d2;
                                                    d5 = d;
                                                }
                                                i8 += 2;
                                                d2 = d6;
                                                d = d5;
                                                obj = obj4;
                                                i = i5;
                                            }
                                            if (obj == null) {
                                                if (i2 >= 8) {
                                                    break;
                                                }
                                                i4 = i2 + 1;
                                                dArr[i2] = d3;
                                                i7 = i4 + 1;
                                                dArr[i4] = d4;
                                                i6 = i7;
                                                obj3 = obj2;
                                            } else {
                                                d += d3;
                                                d2 += d4;
                                                i++;
                                                i6 = i2;
                                                obj3 = obj2;
                                            }
                                        } else {
                                            i4 = i2 + 1;
                                            dArr[i2] = d3;
                                            i7 = i4 + 1;
                                            dArr[i4] = d4;
                                            i3 = 1;
                                            i6 = i7;
                                        }
                                    } else {
                                        fArr = new float[1];
                                        Location.distanceBetween(d4, d3, d2 / ((double) i), d / ((double) i), fArr);
                                        if (fArr[0] > 1000.0f) {
                                            rawQuery.moveToNext();
                                        } else {
                                            i6 = i2;
                                            obj3 = obj2;
                                        }
                                    }
                                } else {
                                    fArr = new float[1];
                                    Location.distanceBetween(d4, d3, this.f583h, this.f582g, fArr);
                                    if (((double) fArr[0]) > this.f581f + 2000.0d) {
                                        rawQuery.moveToNext();
                                    } else {
                                        obj = 1;
                                        d += d3;
                                        d2 += d4;
                                        i++;
                                        i6 = i2;
                                        obj3 = obj2;
                                    }
                                }
                                if (i > 4) {
                                    break;
                                }
                                rawQuery.moveToNext();
                                i2 = i6;
                                obj2 = obj3;
                            } else {
                                rawQuery.moveToNext();
                            }
                        }
                        if (i > 0) {
                            this.f580e = true;
                            this.f584i = d / ((double) i);
                            this.f585j = d2 / ((double) i);
                        }
                    }
                    if (rawQuery != null) {
                        try {
                            rawQuery.close();
                        } catch (Exception e) {
                        }
                    }
                } catch (Exception e2) {
                    cursor = rawQuery;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e3) {
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Exception e4) {
                    }
                }
            } catch (Throwable th3) {
                rawQuery = null;
                th = th3;
                if (rawQuery != null) {
                    try {
                        rawQuery.close();
                    } catch (Exception e5) {
                    }
                }
                throw th;
            }
        }
    }

    private String m648b(boolean z) {
        double d;
        double d2;
        boolean z2;
        boolean z3;
        double d3 = 0.0d;
        if (this.f580e) {
            d = this.f584i;
            d2 = this.f585j;
            d3 = 246.4d;
            z2 = true;
            z3 = true;
        } else if (this.f579d) {
            d = this.f582g;
            d2 = this.f583h;
            d3 = this.f581f;
            z2 = C0351i.m280c().m304h();
            z3 = true;
        } else {
            z2 = false;
            z3 = false;
            d2 = 0.0d;
            d = 0.0d;
        }
        if (!z3) {
            return z ? "{\"result\":{\"time\":\"" + C0468j.m1012a() + "\",\"error\":\"67\"}}" : "{\"result\":{\"time\":\"" + C0468j.m1012a() + "\",\"error\":\"63\"}}";
        } else {
            if (z) {
                return String.format(Locale.CHINA, "{\"result\":{\"time\":\"" + C0468j.m1012a() + "\",\"error\":\"66\"},\"content\":{\"point\":{\"x\":" + "\"%f\",\"y\":\"%f\"},\"radius\":\"%f\",\"isCellChanged\":\"%b\"}}", new Object[]{Double.valueOf(d), Double.valueOf(d2), Double.valueOf(d3), Boolean.valueOf(true)});
            }
            return String.format(Locale.CHINA, "{\"result\":{\"time\":\"" + C0468j.m1012a() + "\",\"error\":\"66\"},\"content\":{\"point\":{\"x\":" + "\"%f\",\"y\":\"%f\"},\"radius\":\"%f\",\"isCellChanged\":\"%b\"}}", new Object[]{Double.valueOf(d), Double.valueOf(d2), Double.valueOf(d3), Boolean.valueOf(z2)});
        }
    }

    private void m652d() {
        try {
            File file = new File(f575l);
            File file2 = new File(f576m);
            if (!file.exists()) {
                file.mkdirs();
            }
            if (!file2.exists()) {
                file2.createNewFile();
            }
            if (file2.exists()) {
                SQLiteDatabase openOrCreateDatabase = SQLiteDatabase.openOrCreateDatabase(file2, null);
                openOrCreateDatabase.execSQL("CREATE TABLE IF NOT EXISTS bdcltb09(id CHAR(40) PRIMARY KEY,time DOUBLE,tag DOUBLE, type DOUBLE , ac INT);");
                openOrCreateDatabase.execSQL("CREATE TABLE IF NOT EXISTS wof(id CHAR(15) PRIMARY KEY,mktime DOUBLE,time DOUBLE, ac INT, bc INT, cc INT);");
                openOrCreateDatabase.setVersion(1);
                openOrCreateDatabase.close();
            }
            this.f577a = true;
        } catch (Exception e) {
        }
    }

    private void m653e() {
        SQLiteDatabase openOrCreateDatabase;
        SQLiteDatabase sQLiteDatabase = null;
        boolean z = true;
        try {
            openOrCreateDatabase = SQLiteDatabase.openOrCreateDatabase(f576m, null);
        } catch (Exception e) {
            openOrCreateDatabase = sQLiteDatabase;
        }
        if (openOrCreateDatabase != null) {
            try {
                long queryNumEntries = DatabaseUtils.queryNumEntries(openOrCreateDatabase, "wof");
                long queryNumEntries2 = DatabaseUtils.queryNumEntries(openOrCreateDatabase, "bdcltb09");
                boolean z2 = queryNumEntries > 10000;
                if (queryNumEntries2 <= 10000) {
                    z = false;
                }
                openOrCreateDatabase.close();
                if (z2 || z) {
                    new C0409a().execute(new Boolean[]{Boolean.valueOf(z2), Boolean.valueOf(z)});
                }
            } catch (Exception e2) {
            }
        }
    }

    public BDLocation m654a(String str, List<ScanResult> list, boolean z) {
        if (!this.f577a) {
            m652d();
        }
        String str2 = "{\"result\":\"null\"}";
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        String str3 = (FutureTask) newSingleThreadExecutor.submit(new C0413c(this, str, list));
        try {
            str3 = (String) str3.get(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            str3.cancel(true);
            str3 = str2;
            return new BDLocation(str3);
        } catch (ExecutionException e2) {
            str3.cancel(true);
            str3 = str2;
            return new BDLocation(str3);
        } catch (TimeoutException e3) {
            if (z) {
            }
            str3.cancel(true);
            str3 = str2;
            return new BDLocation(str3);
        } finally {
            newSingleThreadExecutor.shutdown();
        }
        return new BDLocation(str3);
    }

    public BDLocation m655a(boolean z) {
        BDLocation bDLocation = null;
        if (!this.f577a) {
            m652d();
        }
        C0441a f = C0443b.m855a().m873f();
        String g = f != null ? f.m848g() : null;
        C0451g l = C0454h.m948a().m963l();
        if (l != null) {
            bDLocation = m654a(g, l.f802a, true);
        }
        if (bDLocation != null && bDLocation.getLocType() == 66) {
            StringBuffer stringBuffer = new StringBuffer(1024);
            stringBuffer.append(String.format(Locale.CHINA, "&ofl=%f|%f|%f", new Object[]{Double.valueOf(bDLocation.getLatitude()), Double.valueOf(bDLocation.getLongitude()), Float.valueOf(bDLocation.getRadius())}));
            if (l != null && l.m933a() > 0) {
                stringBuffer.append("&wf=");
                stringBuffer.append(l.m938b(15));
            }
            if (f != null) {
                stringBuffer.append(f.m849h());
            }
            stringBuffer.append("&uptype=oldoff");
            stringBuffer.append(C0468j.m1027e(C0455f.getServiceContext()));
            stringBuffer.append(C0459b.m980a().m981a(false));
            stringBuffer.append(C0332a.m176a().m188d());
            stringBuffer.toString();
        }
        return bDLocation;
    }

    public void m656a(String str, C0441a c0441a, C0451g c0451g, BDLocation bDLocation) {
        if (!this.f577a) {
            m652d();
        }
        int i = (c0441a.m843b() && C0351i.m280c().m304h()) ? 0 : true;
        int i2 = (bDLocation == null || bDLocation.getLocType() != 161 || (!"wf".equals(bDLocation.getNetworkLocationType()) && bDLocation.getRadius() >= 300.0f)) ? true : 0;
        if (c0451g.f802a == null) {
            i2 = true;
        }
        if ((i == 0 || r0 == 0) && !this.f586k) {
            this.f586k = true;
            new C0410b().execute(new Object[]{str, c0441a, c0451g, bDLocation});
        }
    }

    public void m657b() {
        if (this.f587n == null) {
            this.f587n = new Handler();
        }
        this.f587n.postDelayed(new C0412b(this), 3000);
    }
}

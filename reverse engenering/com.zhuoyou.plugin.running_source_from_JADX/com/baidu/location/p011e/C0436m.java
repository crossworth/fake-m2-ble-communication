package com.baidu.location.p011e;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.baidu.android.bbalbs.common.p004a.C0303b;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.WeightedLatLng;
import com.tencent.stat.DeviceInfo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

final class C0436m {
    private static final double[] f721b = new double[]{45.0d, 135.0d, 225.0d, 315.0d};
    private final C0426h f722a;
    private final int f723c;
    private final SQLiteDatabase f724d;
    private int f725e = -1;
    private int f726f = -1;

    private static final class C0434a {
        private double f709a;
        private double f710b;

        private C0434a(double d, double d2) {
            this.f709a = d;
            this.f710b = d2;
        }
    }

    private enum C0435b {
        AREA("RGCAREA", "area", "addrv", 0, 1000),
        ROAD("RGCROAD", "road", "addrv", 1000, 10000),
        SITE("RGCSITE", "site", "addrv", 100, 50000),
        POI("RGCPOI", "poi", "poiv", 1000, 5000);
        
        private final int f716e;
        private final String f717f;
        private final String f718g;
        private final String f719h;
        private final int f720i;

        private C0435b(String str, String str2, String str3, int i, int i2) {
            this.f717f = str;
            this.f718g = str2;
            this.f719h = str3;
            this.f716e = i;
            this.f720i = i2;
        }

        private String m813a(int i, double d, double d2) {
            HashSet hashSet = new HashSet();
            hashSet.add(C0436m.m828b(i, d, d2));
            double d3 = ((double) this.f716e) * 1.414d;
            if (this.f716e > 0) {
                for (double a : C0436m.f721b) {
                    double[] a2 = C0436m.m829b(d2, d, d3, a);
                    hashSet.add(C0436m.m828b(i, a2[1], a2[0]));
                }
            }
            StringBuffer stringBuffer = new StringBuffer();
            Iterator it = hashSet.iterator();
            Object obj = 1;
            while (it.hasNext()) {
                String str = (String) it.next();
                if (obj != null) {
                    obj = null;
                } else {
                    stringBuffer.append(',');
                }
                stringBuffer.append("\"").append(str).append("\"");
            }
            return String.format("SELECT * FROM %s WHERE gridkey IN (%s);", new Object[]{this.f717f, stringBuffer.toString()});
        }

        private String m817a(JSONObject jSONObject) {
            Iterator keys = jSONObject.keys();
            StringBuffer stringBuffer = new StringBuffer();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                if (stringBuffer.length() != 0) {
                    stringBuffer.append(",");
                }
                stringBuffer.append("\"").append(str).append("\"");
            }
            return String.format(Locale.US, "DELETE FROM %s WHERE gridkey IN (%s)", new Object[]{this.f717f, stringBuffer});
        }

        private static void m820b(StringBuffer stringBuffer, String str, String str2, int i) {
            if (stringBuffer.length() > 0) {
                stringBuffer.append(",");
            }
            stringBuffer.append("(\"").append(str).append("\",\"").append(str2).append("\",").append(i).append(",").append(System.currentTimeMillis() / LogBuilder.MAX_INTERVAL).append(")");
        }

        abstract List<String> mo1750a(JSONObject jSONObject, String str, int i);
    }

    C0436m(C0426h c0426h, SQLiteDatabase sQLiteDatabase, int i) {
        this.f722a = c0426h;
        this.f724d = sQLiteDatabase;
        this.f723c = i;
        if (this.f724d != null && this.f724d.isOpen()) {
            try {
                this.f724d.execSQL("CREATE TABLE IF NOT EXISTS RGCAREA(gridkey VARCHAR(10) PRIMARY KEY, country VARCHAR(100),countrycode VARCHAR(100), province VARCHAR(100), city VARCHAR(100), citycode VARCHAR(100), district VARCHAR(100), timestamp INTEGER, version VARCHAR(50))");
                this.f724d.execSQL("CREATE TABLE IF NOT EXISTS RGCROAD(_id INTEGER PRIMARY KEY AUTOINCREMENT, gridkey VARCHAR(10), street VARCHAR(100), x1 DOUBLE, y1 DOUBLE, x2 DOUBLE, y2 DOUBLE)");
                this.f724d.execSQL("CREATE TABLE IF NOT EXISTS RGCSITE(_id INTEGER PRIMARY KEY AUTOINCREMENT, gridkey VARCHAR(10), street VARCHAR(100), streetnumber VARCHAR(100), x DOUBLE, y DOUBLE)");
                this.f724d.execSQL("CREATE TABLE IF NOT EXISTS RGCPOI(pid VARCHAR(50) PRIMARY KEY , gridkey VARCHAR(10), name VARCHAR(100), type VARCHAR(50), x DOUBLE, y DOUBLE, rank INTEGER)");
                this.f724d.execSQL("CREATE TABLE IF NOT EXISTS RGCUPDATE(gridkey VARCHAR(10), version VARCHAR(50), type INTEGER, timestamp INTEGER, PRIMARY KEY(gridkey, type))");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private double m824a(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = ((d5 - d3) * (d - d3)) + ((d6 - d4) * (d2 - d4));
        if (d7 <= 0.0d) {
            return Math.sqrt(((d - d3) * (d - d3)) + ((d2 - d4) * (d2 - d4)));
        }
        double d8 = ((d5 - d3) * (d5 - d3)) + ((d6 - d4) * (d6 - d4));
        if (d7 >= d8) {
            return Math.sqrt(((d - d5) * (d - d5)) + ((d2 - d6) * (d2 - d6)));
        }
        d7 /= d8;
        d8 = ((d5 - d3) * d7) + d3;
        d7 = (d7 * (d6 - d4)) + d4;
        return Math.sqrt(((d7 - d2) * (d7 - d2)) + ((d - d8) * (d - d8)));
    }

    private static int m825a(int i, int i2) {
        double d;
        int i3;
        if (100 > i2) {
            d = -0.1d;
            i3 = 60000;
        } else if (500 > i2) {
            d = -0.75d;
            i3 = 55500;
        } else {
            d = -0.5d;
            i3 = 0;
        }
        return ((int) (((double) i3) + (d * ((double) i2)))) + i;
    }

    private static String m828b(int i, double d, double d2) {
        int i2;
        int i3 = i * 5;
        char[] cArr = new char[(i + 1)];
        C0434a c0434a = new C0434a(90.0d, -90.0d);
        C0434a c0434a2 = new C0434a(180.0d, -180.0d);
        int i4 = 1;
        Object obj = 1;
        int i5 = 0;
        while (i4 <= i3) {
            C0434a c0434a3;
            double d3;
            int i6;
            if (obj != null) {
                c0434a3 = c0434a2;
                d3 = d;
            } else {
                c0434a3 = c0434a;
                d3 = d2;
            }
            double a = (c0434a3.f710b + c0434a3.f709a) / 2.0d;
            i5 <<= 1;
            if (((int) (d3 * 1000000.0d)) > ((int) (1000000.0d * a))) {
                c0434a3.f710b = a;
                i2 = i5 | 1;
            } else {
                c0434a3.f709a = a;
                i2 = i5;
            }
            if (i4 % 5 == 0) {
                cArr[(i4 / 5) - 1] = "0123456789bcdefghjkmnpqrstuvwxyz".charAt(i2);
                i6 = 0;
            } else {
                i6 = i2;
            }
            i4++;
            obj = obj == null ? 1 : null;
            i5 = i6;
        }
        cArr[i] = '\u0000';
        StringBuffer stringBuffer = new StringBuffer();
        for (i2 = 0; i2 < i; i2++) {
            stringBuffer.append(cArr[i2]);
        }
        return stringBuffer.toString();
    }

    private static double[] m829b(double d, double d2, double d3, double d4) {
        double[] dArr = new double[2];
        double toRadians = Math.toRadians(d);
        double toRadians2 = Math.toRadians(d2);
        double toRadians3 = Math.toRadians(d4);
        double asin = Math.asin((Math.sin(toRadians) * Math.cos(d3 / 6378137.0d)) + ((Math.cos(toRadians) * Math.sin(d3 / 6378137.0d)) * Math.cos(toRadians3)));
        toRadians = Math.atan2((Math.sin(toRadians3) * Math.sin(d3 / 6378137.0d)) * Math.cos(toRadians), Math.cos(d3 / 6378137.0d) - (Math.sin(toRadians) * Math.sin(asin))) + toRadians2;
        dArr[0] = Math.toDegrees(asin);
        dArr[1] = Math.toDegrees(toRadians);
        return dArr;
    }

    private double m830c(double d, double d2, double d3, double d4) {
        double d5 = d4 - d2;
        double d6 = d3 - d;
        double toRadians = Math.toRadians(d);
        Math.toRadians(d2);
        double toRadians2 = Math.toRadians(d3);
        Math.toRadians(d4);
        d5 = Math.toRadians(d5);
        d6 = Math.toRadians(d6);
        d5 = (Math.sin(d5 / 2.0d) * ((Math.cos(toRadians) * Math.cos(toRadians2)) * Math.sin(d5 / 2.0d))) + (Math.sin(d6 / 2.0d) * Math.sin(d6 / 2.0d));
        return (Math.atan2(Math.sqrt(d5), Math.sqrt(WeightedLatLng.DEFAULT_INTENSITY - d5)) * 2.0d) * 6378137.0d;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    com.baidu.location.Address m832a(double r32, double r34) {
        /*
        r31 = this;
        r24 = 0;
        r23 = 0;
        r22 = 0;
        r21 = 0;
        r20 = 0;
        r25 = 0;
        r12 = 0;
        r11 = 0;
        r10 = 0;
        r4 = com.baidu.location.p011e.C0436m.C0435b.SITE;	 Catch:{ Exception -> 0x011a, all -> 0x012e }
        r0 = r31;
        r5 = r0.f723c;	 Catch:{ Exception -> 0x011a, all -> 0x012e }
        r6 = r32;
        r8 = r34;
        r4 = r4.m813a(r5, r6, r8);	 Catch:{ Exception -> 0x011a, all -> 0x012e }
        r0 = r31;
        r5 = r0.f724d;	 Catch:{ Exception -> 0x011a, all -> 0x012e }
        r6 = 0;
        r4 = r5.rawQuery(r4, r6);	 Catch:{ Exception -> 0x011a, all -> 0x012e }
        r5 = r4.moveToFirst();	 Catch:{ Exception -> 0x02db, all -> 0x02d6 }
        if (r5 == 0) goto L_0x0316;
    L_0x002c:
        r6 = 9218868437227405311; // 0x7fefffffffffffff float:NaN double:1.7976931348623157E308;
        r16 = r6;
        r14 = r11;
        r15 = r12;
    L_0x0035:
        r5 = r4.isAfterLast();	 Catch:{ Exception -> 0x02e3, all -> 0x02d6 }
        if (r5 != 0) goto L_0x0074;
    L_0x003b:
        r5 = 2;
        r19 = r4.getString(r5);	 Catch:{ Exception -> 0x02e3, all -> 0x02d6 }
        r5 = 3;
        r18 = r4.getString(r5);	 Catch:{ Exception -> 0x02e3, all -> 0x02d6 }
        r5 = 5;
        r10 = r4.getDouble(r5);	 Catch:{ Exception -> 0x02e3, all -> 0x02d6 }
        r5 = 4;
        r12 = r4.getDouble(r5);	 Catch:{ Exception -> 0x02e3, all -> 0x02d6 }
        r5 = r31;
        r6 = r34;
        r8 = r32;
        r8 = r5.m830c(r6, r8, r10, r12);	 Catch:{ Exception -> 0x02e3, all -> 0x02d6 }
        r5 = (r8 > r16 ? 1 : (r8 == r16 ? 0 : -1));
        if (r5 >= 0) goto L_0x0310;
    L_0x005d:
        r5 = com.baidu.location.p011e.C0436m.C0435b.SITE;	 Catch:{ Exception -> 0x02e3, all -> 0x02d6 }
        r5 = r5.f716e;	 Catch:{ Exception -> 0x02e3, all -> 0x02d6 }
        r6 = (double) r5;
        r5 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r5 > 0) goto L_0x0310;
    L_0x0068:
        r5 = r18;
        r6 = r19;
    L_0x006c:
        r4.moveToNext();	 Catch:{ Exception -> 0x02e0, all -> 0x02d6 }
        r16 = r8;
        r14 = r5;
        r15 = r6;
        goto L_0x0035;
    L_0x0074:
        r5 = r14;
        r6 = r15;
    L_0x0076:
        if (r4 == 0) goto L_0x030b;
    L_0x0078:
        r4.close();	 Catch:{ Exception -> 0x0114 }
        r18 = r5;
        r10 = r6;
    L_0x007e:
        if (r18 != 0) goto L_0x013c;
    L_0x0080:
        r11 = 0;
        r4 = com.baidu.location.p011e.C0436m.C0435b.ROAD;	 Catch:{ Exception -> 0x0255, all -> 0x0264 }
        r0 = r31;
        r5 = r0.f723c;	 Catch:{ Exception -> 0x0255, all -> 0x0264 }
        r6 = r32;
        r8 = r34;
        r4 = r4.m813a(r5, r6, r8);	 Catch:{ Exception -> 0x0255, all -> 0x0264 }
        r0 = r31;
        r5 = r0.f724d;	 Catch:{ Exception -> 0x0255, all -> 0x0264 }
        r6 = 0;
        r19 = r5.rawQuery(r4, r6);	 Catch:{ Exception -> 0x0255, all -> 0x0264 }
        r4 = r19.moveToFirst();	 Catch:{ Exception -> 0x02cd, all -> 0x02cb }
        if (r4 == 0) goto L_0x0135;
    L_0x009e:
        r26 = 9218868437227405311; // 0x7fefffffffffffff float:NaN double:1.7976931348623157E308;
        r4 = "wgs842mc";
        r0 = r32;
        r2 = r34;
        r29 = com.baidu.location.Jni.coorEncrypt(r0, r2, r4);	 Catch:{ Exception -> 0x02cd, all -> 0x02cb }
        r4 = r10;
    L_0x00ae:
        r5 = r19.isAfterLast();	 Catch:{ Exception -> 0x02d2, all -> 0x02cb }
        if (r5 != 0) goto L_0x0136;
    L_0x00b4:
        r5 = 2;
        r0 = r19;
        r28 = r0.getString(r5);	 Catch:{ Exception -> 0x02d2, all -> 0x02cb }
        r5 = 3;
        r0 = r19;
        r6 = r0.getDouble(r5);	 Catch:{ Exception -> 0x02d2, all -> 0x02cb }
        r5 = 4;
        r0 = r19;
        r8 = r0.getDouble(r5);	 Catch:{ Exception -> 0x02d2, all -> 0x02cb }
        r5 = "wgs842mc";
        r5 = com.baidu.location.Jni.coorEncrypt(r6, r8, r5);	 Catch:{ Exception -> 0x02d2, all -> 0x02cb }
        r6 = 5;
        r0 = r19;
        r6 = r0.getDouble(r6);	 Catch:{ Exception -> 0x02d2, all -> 0x02cb }
        r8 = 6;
        r0 = r19;
        r8 = r0.getDouble(r8);	 Catch:{ Exception -> 0x02d2, all -> 0x02cb }
        r10 = "wgs842mc";
        r16 = com.baidu.location.Jni.coorEncrypt(r6, r8, r10);	 Catch:{ Exception -> 0x02d2, all -> 0x02cb }
        r6 = 0;
        r6 = r29[r6];	 Catch:{ Exception -> 0x02d2, all -> 0x02cb }
        r8 = 1;
        r8 = r29[r8];	 Catch:{ Exception -> 0x02d2, all -> 0x02cb }
        r10 = 0;
        r10 = r5[r10];	 Catch:{ Exception -> 0x02d2, all -> 0x02cb }
        r12 = 1;
        r12 = r5[r12];	 Catch:{ Exception -> 0x02d2, all -> 0x02cb }
        r5 = 0;
        r14 = r16[r5];	 Catch:{ Exception -> 0x02d2, all -> 0x02cb }
        r5 = 1;
        r16 = r16[r5];	 Catch:{ Exception -> 0x02d2, all -> 0x02cb }
        r5 = r31;
        r6 = r5.m824a(r6, r8, r10, r12, r14, r16);	 Catch:{ Exception -> 0x02d2, all -> 0x02cb }
        r5 = (r6 > r26 ? 1 : (r6 == r26 ? 0 : -1));
        if (r5 >= 0) goto L_0x0306;
    L_0x00ff:
        r5 = com.baidu.location.p011e.C0436m.C0435b.ROAD;	 Catch:{ Exception -> 0x02d2, all -> 0x02cb }
        r5 = r5.f716e;	 Catch:{ Exception -> 0x02d2, all -> 0x02cb }
        r8 = (double) r5;
        r5 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r5 > 0) goto L_0x0306;
    L_0x010a:
        r4 = r6;
        r10 = r28;
    L_0x010d:
        r19.moveToNext();	 Catch:{ Exception -> 0x02cd, all -> 0x02cb }
        r26 = r4;
        r4 = r10;
        goto L_0x00ae;
    L_0x0114:
        r4 = move-exception;
        r18 = r5;
        r10 = r6;
        goto L_0x007e;
    L_0x011a:
        r4 = move-exception;
        r4 = r10;
        r5 = r11;
        r6 = r12;
    L_0x011e:
        if (r4 == 0) goto L_0x030b;
    L_0x0120:
        r4.close();	 Catch:{ Exception -> 0x0128 }
        r18 = r5;
        r10 = r6;
        goto L_0x007e;
    L_0x0128:
        r4 = move-exception;
        r18 = r5;
        r10 = r6;
        goto L_0x007e;
    L_0x012e:
        r4 = move-exception;
    L_0x012f:
        if (r10 == 0) goto L_0x0134;
    L_0x0131:
        r10.close();	 Catch:{ Exception -> 0x029c }
    L_0x0134:
        throw r4;
    L_0x0135:
        r4 = r10;
    L_0x0136:
        if (r19 == 0) goto L_0x0303;
    L_0x0138:
        r19.close();	 Catch:{ Exception -> 0x0251 }
        r10 = r4;
    L_0x013c:
        r4 = com.baidu.location.p011e.C0436m.C0435b.AREA;
        r0 = r31;
        r5 = r0.f723c;
        r6 = r32;
        r8 = r34;
        r5 = r4.m813a(r5, r6, r8);
        r4 = 0;
        r0 = r31;
        r6 = r0.f724d;	 Catch:{ Exception -> 0x026d, all -> 0x0290 }
        r7 = 0;
        r4 = r6.rawQuery(r5, r7);	 Catch:{ Exception -> 0x026d, all -> 0x0290 }
        r5 = r4.moveToFirst();	 Catch:{ Exception -> 0x026d, all -> 0x02a6 }
        if (r5 == 0) goto L_0x02f5;
    L_0x015a:
        r5 = r4.isAfterLast();	 Catch:{ Exception -> 0x026d, all -> 0x02a6 }
        if (r5 != 0) goto L_0x02f5;
    L_0x0160:
        r5 = "country";
        r5 = r4.getColumnIndex(r5);	 Catch:{ Exception -> 0x026d, all -> 0x02a6 }
        r9 = r4.getString(r5);	 Catch:{ Exception -> 0x026d, all -> 0x02a6 }
        r5 = "countrycode";
        r5 = r4.getColumnIndex(r5);	 Catch:{ Exception -> 0x02ad, all -> 0x02a6 }
        r8 = r4.getString(r5);	 Catch:{ Exception -> 0x02ad, all -> 0x02a6 }
        r5 = "province";
        r5 = r4.getColumnIndex(r5);	 Catch:{ Exception -> 0x02b7, all -> 0x02a6 }
        r7 = r4.getString(r5);	 Catch:{ Exception -> 0x02b7, all -> 0x02a6 }
        r5 = "city";
        r5 = r4.getColumnIndex(r5);	 Catch:{ Exception -> 0x02bf, all -> 0x02a6 }
        r6 = r4.getString(r5);	 Catch:{ Exception -> 0x02bf, all -> 0x02a6 }
        r5 = "citycode";
        r5 = r4.getColumnIndex(r5);	 Catch:{ Exception -> 0x02c5, all -> 0x02a6 }
        r5 = r4.getString(r5);	 Catch:{ Exception -> 0x02c5, all -> 0x02a6 }
        r11 = "district";
        r11 = r4.getColumnIndex(r11);	 Catch:{ Exception -> 0x02c9, all -> 0x02a6 }
        r25 = r4.getString(r11);	 Catch:{ Exception -> 0x02c9, all -> 0x02a6 }
        r11 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r25;
    L_0x01a3:
        if (r4 == 0) goto L_0x01a8;
    L_0x01a5:
        r4.close();	 Catch:{ Exception -> 0x02a1 }
    L_0x01a8:
        if (r11 == 0) goto L_0x01b8;
    L_0x01aa:
        r4 = new java.lang.String;
        r11 = r11.getBytes();
        r11 = com.baidu.android.bbalbs.common.p004a.C0303b.m64a(r11);
        r4.<init>(r11);
        r11 = r4;
    L_0x01b8:
        if (r9 == 0) goto L_0x01c8;
    L_0x01ba:
        r4 = new java.lang.String;
        r9 = r9.getBytes();
        r9 = com.baidu.android.bbalbs.common.p004a.C0303b.m64a(r9);
        r4.<init>(r9);
        r9 = r4;
    L_0x01c8:
        if (r8 == 0) goto L_0x01d8;
    L_0x01ca:
        r4 = new java.lang.String;
        r8 = r8.getBytes();
        r8 = com.baidu.android.bbalbs.common.p004a.C0303b.m64a(r8);
        r4.<init>(r8);
        r8 = r4;
    L_0x01d8:
        if (r7 == 0) goto L_0x01e8;
    L_0x01da:
        r4 = new java.lang.String;
        r7 = r7.getBytes();
        r7 = com.baidu.android.bbalbs.common.p004a.C0303b.m64a(r7);
        r4.<init>(r7);
        r7 = r4;
    L_0x01e8:
        if (r6 == 0) goto L_0x01f8;
    L_0x01ea:
        r4 = new java.lang.String;
        r6 = r6.getBytes();
        r6 = com.baidu.android.bbalbs.common.p004a.C0303b.m64a(r6);
        r4.<init>(r6);
        r6 = r4;
    L_0x01f8:
        if (r5 == 0) goto L_0x0208;
    L_0x01fa:
        r4 = new java.lang.String;
        r5 = r5.getBytes();
        r5 = com.baidu.android.bbalbs.common.p004a.C0303b.m64a(r5);
        r4.<init>(r5);
        r5 = r4;
    L_0x0208:
        if (r10 == 0) goto L_0x0218;
    L_0x020a:
        r4 = new java.lang.String;
        r10 = r10.getBytes();
        r10 = com.baidu.android.bbalbs.common.p004a.C0303b.m64a(r10);
        r4.<init>(r10);
        r10 = r4;
    L_0x0218:
        if (r18 == 0) goto L_0x02e8;
    L_0x021a:
        r4 = new java.lang.String;
        r12 = r18.getBytes();
        r12 = com.baidu.android.bbalbs.common.p004a.C0303b.m64a(r12);
        r4.<init>(r12);
    L_0x0227:
        r12 = new com.baidu.location.Address$Builder;
        r12.<init>();
        r11 = r12.country(r11);
        r9 = r11.countryCode(r9);
        r8 = r9.province(r8);
        r7 = r8.city(r7);
        r6 = r7.cityCode(r6);
        r5 = r6.district(r5);
        r5 = r5.street(r10);
        r4 = r5.streetNumber(r4);
        r4 = r4.build();
        return r4;
    L_0x0251:
        r5 = move-exception;
        r10 = r4;
        goto L_0x013c;
    L_0x0255:
        r4 = move-exception;
        r5 = r11;
        r4 = r10;
    L_0x0258:
        if (r5 == 0) goto L_0x0303;
    L_0x025a:
        r5.close();	 Catch:{ Exception -> 0x0260 }
        r10 = r4;
        goto L_0x013c;
    L_0x0260:
        r5 = move-exception;
        r10 = r4;
        goto L_0x013c;
    L_0x0264:
        r4 = move-exception;
        r19 = r11;
    L_0x0267:
        if (r19 == 0) goto L_0x026c;
    L_0x0269:
        r19.close();	 Catch:{ Exception -> 0x029f }
    L_0x026c:
        throw r4;
    L_0x026d:
        r5 = move-exception;
        r5 = r20;
        r6 = r21;
        r7 = r22;
        r8 = r23;
        r9 = r24;
    L_0x0278:
        if (r4 == 0) goto L_0x02ec;
    L_0x027a:
        r4.close();	 Catch:{ Exception -> 0x0286 }
        r11 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r25;
        goto L_0x01a8;
    L_0x0286:
        r4 = move-exception;
        r11 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r25;
        goto L_0x01a8;
    L_0x0290:
        r5 = move-exception;
        r30 = r5;
        r5 = r4;
        r4 = r30;
    L_0x0296:
        if (r5 == 0) goto L_0x029b;
    L_0x0298:
        r5.close();	 Catch:{ Exception -> 0x02a4 }
    L_0x029b:
        throw r4;
    L_0x029c:
        r5 = move-exception;
        goto L_0x0134;
    L_0x029f:
        r5 = move-exception;
        goto L_0x026c;
    L_0x02a1:
        r4 = move-exception;
        goto L_0x01a8;
    L_0x02a4:
        r5 = move-exception;
        goto L_0x029b;
    L_0x02a6:
        r5 = move-exception;
        r30 = r5;
        r5 = r4;
        r4 = r30;
        goto L_0x0296;
    L_0x02ad:
        r5 = move-exception;
        r5 = r20;
        r6 = r21;
        r7 = r22;
        r8 = r23;
        goto L_0x0278;
    L_0x02b7:
        r5 = move-exception;
        r5 = r20;
        r6 = r21;
        r7 = r22;
        goto L_0x0278;
    L_0x02bf:
        r5 = move-exception;
        r5 = r20;
        r6 = r21;
        goto L_0x0278;
    L_0x02c5:
        r5 = move-exception;
        r5 = r20;
        goto L_0x0278;
    L_0x02c9:
        r11 = move-exception;
        goto L_0x0278;
    L_0x02cb:
        r4 = move-exception;
        goto L_0x0267;
    L_0x02cd:
        r4 = move-exception;
        r5 = r19;
        r4 = r10;
        goto L_0x0258;
    L_0x02d2:
        r5 = move-exception;
        r5 = r19;
        goto L_0x0258;
    L_0x02d6:
        r5 = move-exception;
        r10 = r4;
        r4 = r5;
        goto L_0x012f;
    L_0x02db:
        r5 = move-exception;
        r5 = r11;
        r6 = r12;
        goto L_0x011e;
    L_0x02e0:
        r7 = move-exception;
        goto L_0x011e;
    L_0x02e3:
        r5 = move-exception;
        r5 = r14;
        r6 = r15;
        goto L_0x011e;
    L_0x02e8:
        r4 = r18;
        goto L_0x0227;
    L_0x02ec:
        r11 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r25;
        goto L_0x01a8;
    L_0x02f5:
        r5 = r25;
        r6 = r20;
        r7 = r21;
        r8 = r22;
        r9 = r23;
        r11 = r24;
        goto L_0x01a3;
    L_0x0303:
        r10 = r4;
        goto L_0x013c;
    L_0x0306:
        r10 = r4;
        r4 = r26;
        goto L_0x010d;
    L_0x030b:
        r18 = r5;
        r10 = r6;
        goto L_0x007e;
    L_0x0310:
        r8 = r16;
        r5 = r14;
        r6 = r15;
        goto L_0x006c;
    L_0x0316:
        r5 = r11;
        r6 = r12;
        goto L_0x0076;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.e.m.a(double, double):com.baidu.location.Address");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void m833a(org.json.JSONObject r9) {
        /*
        r8 = this;
        r0 = r8.f724d;
        if (r0 == 0) goto L_0x0075;
    L_0x0004:
        r0 = r8.f724d;
        r0 = r0.isOpen();
        if (r0 == 0) goto L_0x0075;
    L_0x000c:
        r0 = r8.f724d;	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r0.beginTransaction();	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r2 = com.baidu.location.p011e.C0436m.C0435b.values();	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r3 = r2.length;	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r0 = 0;
        r1 = r0;
    L_0x0018:
        if (r1 >= r3) goto L_0x007a;
    L_0x001a:
        r4 = r2[r1];	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r0 = r4.f718g;	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r0 = r9.has(r0);	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        if (r0 == 0) goto L_0x0076;
    L_0x0026:
        r0 = "";
        r5 = r4.f719h;	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r5 = r9.has(r5);	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        if (r5 == 0) goto L_0x003a;
    L_0x0032:
        r0 = r4.f719h;	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r0 = r9.getString(r0);	 Catch:{ Exception -> 0x006f, all -> 0x008d }
    L_0x003a:
        r5 = new java.util.ArrayList;	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r5.<init>();	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r6 = r4.f718g;	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r6 = r9.getJSONObject(r6);	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r7 = r4.m817a(r6);	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r5.add(r7);	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r7 = r4.f720i;	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r0 = r4.mo1750a(r6, r0, r7);	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r5.addAll(r0);	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r4 = r5.iterator();	 Catch:{ Exception -> 0x006f, all -> 0x008d }
    L_0x005d:
        r0 = r4.hasNext();	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        if (r0 == 0) goto L_0x0076;
    L_0x0063:
        r0 = r4.next();	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r0 = (java.lang.String) r0;	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r5 = r8.f724d;	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r5.execSQL(r0);	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        goto L_0x005d;
    L_0x006f:
        r0 = move-exception;
        r0 = r8.f724d;	 Catch:{ Exception -> 0x0096 }
        r0.endTransaction();	 Catch:{ Exception -> 0x0096 }
    L_0x0075:
        return;
    L_0x0076:
        r0 = r1 + 1;
        r1 = r0;
        goto L_0x0018;
    L_0x007a:
        r0 = r8.f724d;	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r0.setTransactionSuccessful();	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r0 = -1;
        r8.f725e = r0;	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r0 = -1;
        r8.f726f = r0;	 Catch:{ Exception -> 0x006f, all -> 0x008d }
        r0 = r8.f724d;	 Catch:{ Exception -> 0x008b }
        r0.endTransaction();	 Catch:{ Exception -> 0x008b }
        goto L_0x0075;
    L_0x008b:
        r0 = move-exception;
        goto L_0x0075;
    L_0x008d:
        r0 = move-exception;
        r1 = r8.f724d;	 Catch:{ Exception -> 0x0094 }
        r1.endTransaction();	 Catch:{ Exception -> 0x0094 }
    L_0x0093:
        throw r0;
    L_0x0094:
        r1 = move-exception;
        goto L_0x0093;
    L_0x0096:
        r0 = move-exception;
        goto L_0x0075;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.e.m.a(org.json.JSONObject):void");
    }

    boolean m834a() {
        Cursor rawQuery;
        Throwable th;
        Cursor cursor = null;
        if (this.f722a.m787l().m759l() && this.f726f == -1 && this.f725e == -1 && this.f724d != null && this.f724d.isOpen()) {
            try {
                rawQuery = this.f724d.rawQuery("SELECT COUNT(*) FROM RGCSITE;", null);
                try {
                    rawQuery.moveToFirst();
                    this.f726f = rawQuery.getInt(0);
                    cursor = this.f724d.rawQuery("SELECT COUNT(*) FROM RGCAREA;", null);
                    cursor.moveToFirst();
                    this.f725e = cursor.getInt(0);
                    if (rawQuery != null) {
                        try {
                            rawQuery.close();
                        } catch (Exception e) {
                        }
                    }
                    if (cursor != null) {
                        try {
                            cursor.close();
                        } catch (Exception e2) {
                        }
                    }
                } catch (Exception e3) {
                    if (rawQuery != null) {
                        try {
                            rawQuery.close();
                        } catch (Exception e4) {
                        }
                    }
                    if (cursor != null) {
                        try {
                            cursor.close();
                        } catch (Exception e5) {
                        }
                    }
                    return this.f726f == 0 ? false : false;
                } catch (Throwable th2) {
                    th = th2;
                    if (rawQuery != null) {
                        try {
                            rawQuery.close();
                        } catch (Exception e6) {
                        }
                    }
                    if (cursor != null) {
                        try {
                            cursor.close();
                        } catch (Exception e7) {
                        }
                    }
                    throw th;
                }
            } catch (Exception e8) {
                rawQuery = null;
                if (rawQuery != null) {
                    rawQuery.close();
                }
                if (cursor != null) {
                    cursor.close();
                }
                if (this.f726f == 0) {
                }
            } catch (Throwable th3) {
                th = th3;
                rawQuery = null;
                if (rawQuery != null) {
                    rawQuery.close();
                }
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        }
        if (this.f726f == 0 && this.f725e == 0) {
            return true;
        }
    }

    List<Poi> m835b(double d, double d2) {
        Cursor cursor;
        Throwable th;
        List<Poi> arrayList = new ArrayList();
        Object obj = null;
        Cursor rawQuery;
        try {
            rawQuery = this.f724d.rawQuery(C0435b.POI.m813a(this.f723c, d, d2), null);
            try {
                if (rawQuery.moveToFirst()) {
                    int i = 0;
                    while (!rawQuery.isAfterLast()) {
                        int a;
                        String string = rawQuery.getString(0);
                        String string2 = rawQuery.getString(2);
                        double d3 = rawQuery.getDouble(4);
                        double d4 = rawQuery.getDouble(5);
                        int i2 = rawQuery.getInt(6);
                        double c = m830c(d2, d, d4, d3);
                        if (c < ((double) C0435b.POI.f716e)) {
                            Poi poi = new Poi(new String(C0303b.m64a(string.getBytes())), new String(C0303b.m64a(string2.getBytes())), WeightedLatLng.DEFAULT_INTENSITY);
                            a = C0436m.m825a(i2, Math.round((float) c));
                            if (a > i) {
                                obj = poi;
                                rawQuery.moveToNext();
                                i = a;
                            }
                        }
                        a = i;
                        rawQuery.moveToNext();
                        i = a;
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
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Exception e3) {
                    }
                }
                if (obj != null) {
                    arrayList.add(obj);
                }
                return arrayList;
            } catch (Throwable th2) {
                th = th2;
                if (rawQuery != null) {
                    try {
                        rawQuery.close();
                    } catch (Exception e4) {
                    }
                }
                throw th;
            }
        } catch (Exception e5) {
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            if (obj != null) {
                arrayList.add(obj);
            }
            return arrayList;
        } catch (Throwable th3) {
            th = th3;
            rawQuery = null;
            if (rawQuery != null) {
                rawQuery.close();
            }
            throw th;
        }
        if (obj != null) {
            arrayList.add(obj);
        }
        return arrayList;
    }

    JSONObject m836b() {
        Cursor cursor = null;
        Cursor cursor2 = null;
        JSONObject jSONObject = new JSONObject();
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        int currentTimeMillis = (int) (System.currentTimeMillis() / LogBuilder.MAX_INTERVAL);
        String str = "SELECT * FROM RGCUPDATE WHERE type=%d AND %d > timestamp+%d ORDER BY gridkey";
        String str2 = "UPDATE RGCUPDATE SET timestamp=timestamp+1 WHERE type = %d AND gridkey IN (%s)";
        try {
            if (this.f724d != null && this.f724d.isOpen()) {
                HashSet hashSet;
                String string;
                String[] strArr;
                JSONObject jSONObject2;
                JSONArray jSONArray = new JSONArray();
                JSONArray jSONArray2 = new JSONArray();
                JSONArray jSONArray3 = new JSONArray();
                JSONArray jSONArray4 = new JSONArray();
                cursor2 = this.f724d.rawQuery(String.format(str, new Object[]{Integer.valueOf(0), Integer.valueOf(currentTimeMillis), Integer.valueOf(this.f722a.m787l().m763p())}), null);
                cursor = this.f724d.rawQuery(String.format(str, new Object[]{Integer.valueOf(1), Integer.valueOf(currentTimeMillis), Integer.valueOf(this.f722a.m787l().m764q())}), null);
                if (cursor2.moveToFirst()) {
                    hashSet = new HashSet();
                    while (!cursor2.isAfterLast()) {
                        str = cursor2.getString(0);
                        string = cursor2.getString(1);
                        jSONArray3.put(str);
                        hashSet.add(string);
                        if (stringBuffer2.length() > 0) {
                            stringBuffer2.append(",");
                        }
                        stringBuffer2.append("\"").append(str).append("\"");
                        cursor2.moveToNext();
                    }
                    strArr = new String[hashSet.size()];
                    hashSet.toArray(strArr);
                    for (Object put : strArr) {
                        jSONArray4.put(put);
                    }
                }
                if (cursor.moveToFirst()) {
                    hashSet = new HashSet();
                    while (!cursor.isAfterLast()) {
                        str = cursor.getString(0);
                        string = cursor.getString(1);
                        jSONArray.put(str);
                        hashSet.add(string);
                        if (stringBuffer.length() > 0) {
                            stringBuffer.append(",");
                        }
                        stringBuffer.append("\"").append(str).append("\"");
                        cursor.moveToNext();
                    }
                    strArr = new String[hashSet.size()];
                    hashSet.toArray(strArr);
                    for (Object put2 : strArr) {
                        jSONArray2.put(put2);
                    }
                }
                if (jSONArray3.length() != 0) {
                    jSONObject2 = new JSONObject();
                    jSONObject2.put("gk", jSONArray3);
                    jSONObject2.put(DeviceInfo.TAG_VERSION, jSONArray4);
                    jSONObject.put("addr", jSONObject2);
                }
                if (jSONArray.length() != 0) {
                    jSONObject2 = new JSONObject();
                    jSONObject2.put("gk", jSONArray);
                    jSONObject2.put(DeviceInfo.TAG_VERSION, jSONArray2);
                    jSONObject.put("poi", jSONObject2);
                }
            }
            if (stringBuffer2.length() > 0) {
                this.f724d.execSQL(String.format(Locale.US, str2, new Object[]{Integer.valueOf(0), stringBuffer2.toString()}));
            }
            if (stringBuffer.length() > 0) {
                this.f724d.execSQL(String.format(Locale.US, str2, new Object[]{Integer.valueOf(1), stringBuffer.toString()}));
            }
            if (cursor2 != null) {
                try {
                    cursor2.close();
                } catch (Exception e) {
                }
            }
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e2) {
                }
            }
        } catch (Exception e3) {
            if (cursor2 != null) {
                try {
                    cursor2.close();
                } catch (Exception e4) {
                }
            }
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e5) {
                }
            }
        } catch (Throwable th) {
            if (cursor2 != null) {
                try {
                    cursor2.close();
                } catch (Exception e6) {
                }
            }
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e7) {
                }
            }
        }
        return (jSONObject.has("poi") || jSONObject.has("addr")) ? jSONObject : null;
    }
}

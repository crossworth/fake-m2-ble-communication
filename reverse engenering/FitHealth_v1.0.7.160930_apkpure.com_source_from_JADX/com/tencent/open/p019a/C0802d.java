package com.tencent.open.p019a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: ProGuard */
public class C0802d {
    private Context f2733a;
    private SQLiteDatabase f2734b;
    private C0801a f2735c;
    private int f2736d = m2565c().size();

    /* compiled from: ProGuard */
    private class C0801a extends SQLiteOpenHelper {
        final /* synthetic */ C0802d f2732a;

        public C0801a(C0802d c0802d, Context context, String str, CursorFactory cursorFactory, int i) {
            this.f2732a = c0802d;
            super(context, str, cursorFactory, i);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            try {
                Log.i("cgi_report_debug", "ReportDataModal onCreate sql1 = create table if not exists newdata(id integer primary key,apn text,frequency text,commandid text,resultcode text,tmcost text,reqsize text,rspsize text,deviceinfo text,detail text)");
                sQLiteDatabase.execSQL("create table if not exists newdata(id integer primary key,apn text,frequency text,commandid text,resultcode text,tmcost text,reqsize text,rspsize text,deviceinfo text,detail text)");
                Log.i("cgi_report_debug", "ReportDataModal onCreate sql2 = create table if not exists olddata(id integer primary key,apn text,frequency text,commandid text,resultcode text,tmcost text,reqsize text,rspsize text,deviceinfo text,detail text)");
                sQLiteDatabase.execSQL("create table if not exists olddata(id integer primary key,apn text,frequency text,commandid text,resultcode text,tmcost text,reqsize text,rspsize text,deviceinfo text,detail text)");
            } catch (Exception e) {
                Log.e("cgi_report_debug", "ReportDataModal onCreate failed");
                e.printStackTrace();
            }
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            Log.i("cgi_report_debug", "ReportDataModal onUpgrade oldVersion=" + i + "  newVersion=" + i2 + "");
            if (i != i2) {
                try {
                    sQLiteDatabase.execSQL("drop table if exists newdata");
                    sQLiteDatabase.execSQL("drop table if exists olddata");
                    onCreate(sQLiteDatabase);
                    Log.i("cgi_report_debug", "ReportDataModal onUpgrade success");
                } catch (Exception e) {
                    Log.e("cgi_report_debug", "ReportDataModal onUpgrade failed");
                }
            }
        }
    }

    public C0802d(Context context) {
        this.f2733a = context;
        this.f2735c = new C0801a(this, context, "sdk_cgi_report.db", null, 2);
    }

    public synchronized boolean m2563a(String str, String str2, String str3, int i, long j, long j2, long j3, String str4) {
        boolean z;
        if (str3.contains("?")) {
            str3 = str3.split("\\?")[0];
        }
        Log.i("cgi_report_debug", "ReportDataModal addNewItem apn=" + str + ",frequency=" + str2 + ",commandid=" + str3 + ",resultCode=" + i + ",costTime=" + j + ",reqSzie=" + j2 + ",rspSize=" + j3);
        ContentValues contentValues = new ContentValues();
        contentValues.put("apn", str + "");
        contentValues.put("frequency", str2 + "");
        contentValues.put("commandid", str3 + "");
        contentValues.put("resultcode", i + "");
        contentValues.put("tmcost", j + "");
        contentValues.put("reqsize", j2 + "");
        contentValues.put("rspsize", j3 + "");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("network=").append(str).append('&');
        stringBuilder.append("sdcard=").append(Environment.getExternalStorageState().equals("mounted") ? 1 : 0).append('&');
        stringBuilder.append("wifi=").append(C0800c.m2560c(this.f2733a));
        contentValues.put("deviceinfo", stringBuilder.toString());
        contentValues.put("detail", str4);
        try {
            this.f2734b = this.f2735c.getWritableDatabase();
            this.f2734b.insertOrThrow("newdata", null, contentValues);
            this.f2734b.close();
            Log.i("cgi_report_debug", "ReportDataModal addNewItem success");
            this.f2736d++;
            z = true;
        } catch (Exception e) {
            Log.e("cgi_report_debug", "ReportDataModal addNewItem failed");
            e.printStackTrace();
            z = false;
        }
        return z;
    }

    public synchronized int m2561a(ArrayList<C0797a> arrayList) {
        int i;
        Log.i("cgi_report_debug", "ReportDataModal backupOldItems count = " + arrayList.size());
        Iterator it = arrayList.iterator();
        i = 0;
        while (it.hasNext()) {
            C0797a c0797a = (C0797a) it.next();
            ContentValues contentValues = new ContentValues();
            contentValues.put("apn", c0797a.m2533a());
            contentValues.put("frequency", c0797a.m2534b());
            contentValues.put("commandid", c0797a.m2535c());
            contentValues.put("resultcode", c0797a.m2536d());
            contentValues.put("tmcost", c0797a.m2537e());
            contentValues.put("reqsize", c0797a.m2538f());
            contentValues.put("rspsize", c0797a.m2539g());
            contentValues.put("deviceinfo", c0797a.m2541i());
            contentValues.put("detail", c0797a.m2540h());
            try {
                this.f2734b = this.f2735c.getWritableDatabase();
                this.f2734b.insertOrThrow("olddata", null, contentValues);
                this.f2734b.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }
        Log.i("cgi_report_debug", "ReportDataModal backupOldItems succ_count = " + i);
        return i;
    }

    public synchronized boolean m2562a() {
        boolean z = false;
        synchronized (this) {
            Log.i("cgi_report_debug", "ReportDataModal deleteOldItems start");
            try {
                this.f2734b = this.f2735c.getWritableDatabase();
                try {
                    this.f2734b.execSQL("delete from olddata;");
                    this.f2734b.close();
                    Log.i("cgi_report_debug", "ReportDataModal deleteOldItems success");
                    z = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    this.f2734b.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return z;
    }

    public synchronized boolean m2564b() {
        boolean z = false;
        synchronized (this) {
            Log.i("cgi_report_debug", "ReportDataModal deleteNewItems start");
            try {
                this.f2734b = this.f2735c.getWritableDatabase();
                try {
                    this.f2734b.execSQL("delete from newdata;");
                    this.f2736d = 0;
                    this.f2734b.close();
                    Log.i("cgi_report_debug", "ReportDataModal deleteNewItems start");
                    z = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    this.f2734b.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return z;
    }

    public synchronized ArrayList<C0797a> m2565c() {
        ArrayList<C0797a> arrayList;
        Log.i("cgi_report_debug", "ReportDataModal getNewItems start");
        ArrayList<C0797a> arrayList2 = new ArrayList();
        try {
            this.f2734b = this.f2735c.getReadableDatabase();
            Cursor rawQuery = this.f2734b.rawQuery("select * from newdata", new String[0]);
            rawQuery.moveToFirst();
            while (!rawQuery.isAfterLast()) {
                arrayList2.add(new C0797a(rawQuery.getString(rawQuery.getColumnIndex("apn")), rawQuery.getString(rawQuery.getColumnIndex("frequency")), rawQuery.getString(rawQuery.getColumnIndex("commandid")), rawQuery.getString(rawQuery.getColumnIndex("resultcode")), rawQuery.getString(rawQuery.getColumnIndex("tmcost")), rawQuery.getString(rawQuery.getColumnIndex("reqsize")), rawQuery.getString(rawQuery.getColumnIndex("rspsize")), rawQuery.getString(rawQuery.getColumnIndex("deviceinfo")), rawQuery.getString(rawQuery.getColumnIndex("detail"))));
                rawQuery.moveToNext();
            }
            rawQuery.close();
            this.f2734b.close();
            Log.i("cgi_report_debug", "ReportDataModal getNewItems success, count = " + arrayList2.size());
            arrayList = arrayList2;
        } catch (Exception e) {
            e.printStackTrace();
            arrayList = arrayList2;
        }
        return arrayList;
    }

    public synchronized ArrayList<C0797a> m2566d() {
        ArrayList<C0797a> arrayList;
        Log.i("cgi_report_debug", "ReportDataModal getOldItems start");
        ArrayList<C0797a> arrayList2 = new ArrayList();
        try {
            this.f2734b = this.f2735c.getReadableDatabase();
            Cursor rawQuery = this.f2734b.rawQuery("select * from olddata", new String[0]);
            rawQuery.moveToFirst();
            while (!rawQuery.isAfterLast()) {
                arrayList2.add(new C0797a(rawQuery.getString(rawQuery.getColumnIndex("apn")), rawQuery.getString(rawQuery.getColumnIndex("frequency")), rawQuery.getString(rawQuery.getColumnIndex("commandid")), rawQuery.getString(rawQuery.getColumnIndex("resultcode")), rawQuery.getString(rawQuery.getColumnIndex("tmcost")), rawQuery.getString(rawQuery.getColumnIndex("reqsize")), rawQuery.getString(rawQuery.getColumnIndex("rspsize")), rawQuery.getString(rawQuery.getColumnIndex("deviceinfo")), rawQuery.getString(rawQuery.getColumnIndex("detail"))));
                rawQuery.moveToNext();
            }
            rawQuery.close();
            this.f2734b.close();
            Log.i("cgi_report_debug", "ReportDataModal getOldItems success, count = " + arrayList2.size());
            arrayList = arrayList2;
        } catch (Exception e) {
            e.printStackTrace();
            arrayList = arrayList2;
        }
        return arrayList;
    }

    public int m2567e() {
        Log.i("cgi_report_debug", "ReportDataModal getTotalCount count = " + this.f2736d);
        return this.f2736d;
    }
}

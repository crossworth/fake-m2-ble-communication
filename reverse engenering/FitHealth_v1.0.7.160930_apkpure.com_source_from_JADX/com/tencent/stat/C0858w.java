package com.tencent.stat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.tencent.stat.common.C0837k;
import java.util.ArrayList;
import java.util.List;

class C0858w extends SQLiteOpenHelper {
    public C0858w(Context context) {
        super(context, C0837k.m2749v(context), null, 3);
    }

    private void m2804a(SQLiteDatabase sQLiteDatabase) {
        Object th;
        Throwable th2;
        String str = null;
        Cursor query;
        try {
            query = sQLiteDatabase.query("user", null, null, null, null, null, null);
            try {
                ContentValues contentValues = new ContentValues();
                if (query.moveToNext()) {
                    str = query.getString(0);
                    query.getInt(1);
                    query.getString(2);
                    query.getLong(3);
                    contentValues.put("uid", C0837k.m2723c(str));
                }
                if (str != null) {
                    sQLiteDatabase.update("user", contentValues, "uid=?", new String[]{str});
                }
                if (query != null) {
                    query.close();
                }
            } catch (Throwable th3) {
                th = th3;
                try {
                    C0850n.f2933e.m2680e(th);
                    if (query != null) {
                        query.close();
                    }
                } catch (Throwable th4) {
                    th2 = th4;
                    if (query != null) {
                        query.close();
                    }
                    throw th2;
                }
            }
        } catch (Throwable th5) {
            th2 = th5;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th2;
        }
    }

    private void m2805b(SQLiteDatabase sQLiteDatabase) {
        Cursor query;
        Object th;
        Cursor cursor;
        Throwable th2;
        try {
            query = sQLiteDatabase.query("events", null, null, null, null, null, null);
            try {
                List<C0859x> arrayList = new ArrayList();
                while (query.moveToNext()) {
                    arrayList.add(new C0859x(query.getLong(0), query.getString(1), query.getInt(2), query.getInt(3)));
                }
                ContentValues contentValues = new ContentValues();
                for (C0859x c0859x : arrayList) {
                    contentValues.put("content", C0837k.m2723c(c0859x.f2955b));
                    sQLiteDatabase.update("events", contentValues, "event_id=?", new String[]{Long.toString(c0859x.f2954a)});
                }
                if (query != null) {
                    query.close();
                }
            } catch (Throwable th3) {
                th2 = th3;
                if (query != null) {
                    query.close();
                }
                throw th2;
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

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("create table if not exists events(event_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, content TEXT, status INTEGER, send_count INTEGER, timestamp LONG)");
        sQLiteDatabase.execSQL("create table if not exists user(uid TEXT PRIMARY KEY, user_type INTEGER, app_ver TEXT, ts INTEGER)");
        sQLiteDatabase.execSQL("create table if not exists config(type INTEGER PRIMARY KEY NOT NULL, content TEXT, md5sum TEXT, version INTEGER)");
        sQLiteDatabase.execSQL("create table if not exists keyvalues(key TEXT PRIMARY KEY NOT NULL, value TEXT)");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        C0850n.f2933e.debug("upgrade DB from oldVersion " + i + " to newVersion " + i2);
        if (i == 1) {
            sQLiteDatabase.execSQL("create table if not exists keyvalues(key TEXT PRIMARY KEY NOT NULL, value TEXT)");
            m2804a(sQLiteDatabase);
            m2805b(sQLiteDatabase);
        }
        if (i == 2) {
            m2804a(sQLiteDatabase);
            m2805b(sQLiteDatabase);
        }
    }
}

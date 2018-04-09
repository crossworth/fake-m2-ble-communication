package com.tencent.stat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.tencent.stat.common.C1389k;
import java.util.ArrayList;
import java.util.List;

class C1414w extends SQLiteOpenHelper {
    public C1414w(Context context) {
        super(context, C1389k.m4156v(context), null, 3);
    }

    private void m4217a(SQLiteDatabase sQLiteDatabase) {
        Cursor query;
        Object th;
        Throwable th2;
        String str = null;
        try {
            query = sQLiteDatabase.query("user", null, null, null, null, null, null);
            try {
                ContentValues contentValues = new ContentValues();
                if (query.moveToNext()) {
                    str = query.getString(0);
                    query.getInt(1);
                    query.getString(2);
                    query.getLong(3);
                    contentValues.put("uid", C1389k.m4130c(str));
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
                    C1405n.f4471e.m4085e(th);
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

    private void m4218b(SQLiteDatabase sQLiteDatabase) {
        Object th;
        Cursor cursor;
        Throwable th2;
        Cursor query;
        try {
            query = sQLiteDatabase.query("events", null, null, null, null, null, null);
            try {
                List<C1415x> arrayList = new ArrayList();
                while (query.moveToNext()) {
                    arrayList.add(new C1415x(query.getLong(0), query.getString(1), query.getInt(2), query.getInt(3)));
                }
                ContentValues contentValues = new ContentValues();
                for (C1415x c1415x : arrayList) {
                    contentValues.put("content", C1389k.m4130c(c1415x.f4496b));
                    sQLiteDatabase.update("events", contentValues, "event_id=?", new String[]{Long.toString(c1415x.f4495a)});
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
        C1405n.f4471e.debug("upgrade DB from oldVersion " + i + " to newVersion " + i2);
        if (i == 1) {
            sQLiteDatabase.execSQL("create table if not exists keyvalues(key TEXT PRIMARY KEY NOT NULL, value TEXT)");
            m4217a(sQLiteDatabase);
            m4218b(sQLiteDatabase);
        }
        if (i == 2) {
            m4217a(sQLiteDatabase);
            m4218b(sQLiteDatabase);
        }
    }
}

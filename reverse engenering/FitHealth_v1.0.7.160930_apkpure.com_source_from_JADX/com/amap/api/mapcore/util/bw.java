package com.amap.api.mapcore.util;

import android.database.sqlite.SQLiteDatabase;

/* compiled from: OfflineDBCreator */
public class bw implements ej {
    private static volatile bw f4060a;

    public static bw m4028a() {
        if (f4060a == null) {
            synchronized (bw.class) {
                if (f4060a == null) {
                    f4060a = new bw();
                }
            }
        }
        return f4060a;
    }

    private bw() {
    }

    public void mo1475a(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS update_item (_id integer primary key autoincrement, title  TEXT, url TEXT,mAdcode TEXT,fileName TEXT,version TEXT,lLocalLength INTEGER,lRemoteLength INTEGER,localPath TEXT,mIndex INTEGER,isProvince INTEGER NOT NULL,mCompleteCode INTEGER,mCityCode TEXT,mState INTEGER, UNIQUE(mAdcode));");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS update_item_file (_id integer primary key autoincrement,mAdcode TTEXT, file TEXT);");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS update_item_download_info (_id integer primary key autoincrement,mAdcode TEXT,fileLength integer,splitter integer,startPos integer,endPos integer, UNIQUE(mAdcode));");
        } catch (Throwable th) {
            ee.m4243a(th, "DB", "onCreate");
            th.printStackTrace();
        }
    }

    public String mo1477b() {
        return "offlineDbV4.db";
    }

    public int mo1478c() {
        return 1;
    }

    public void mo1476a(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}

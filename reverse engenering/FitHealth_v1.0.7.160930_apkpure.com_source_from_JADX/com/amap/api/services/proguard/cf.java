package com.amap.api.services.proguard;

import android.database.sqlite.SQLiteDatabase;

/* compiled from: DynamicFileDBCreator */
public class cf implements bm {
    private static cf f4374a;

    public static synchronized cf m4469c() {
        cf cfVar;
        synchronized (cf.class) {
            if (f4374a == null) {
                f4374a = new cf();
            }
            cfVar = f4374a;
        }
        return cfVar;
    }

    private cf() {
    }

    public String mo1767a() {
        return "dynamicamapfile.db";
    }

    public int mo1770b() {
        return 1;
    }

    public void mo1768a(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS file (_id integer primary key autoincrement, sdkname  varchar(20), filename varchar(100),md5 varchar(20),version varchar(20),dynamicversion varchar(20),status varchar(20),reservedfield varchar(20));");
        } catch (Throwable th) {
            be.m1340a(th, "DynamicFileDBCreator", "onCreate");
        }
    }

    public void mo1769a(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}

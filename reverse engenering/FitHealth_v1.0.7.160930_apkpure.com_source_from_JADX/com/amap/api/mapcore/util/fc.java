package com.amap.api.mapcore.util;

import android.database.sqlite.SQLiteDatabase;

/* compiled from: DynamicFileDBCreator */
public class fc implements ej {
    private static fc f4211a;

    public static synchronized fc m4276a() {
        fc fcVar;
        synchronized (fc.class) {
            if (f4211a == null) {
                f4211a = new fc();
            }
            fcVar = f4211a;
        }
        return fcVar;
    }

    private fc() {
    }

    public String mo1477b() {
        return "dynamicamapfile.db";
    }

    public int mo1478c() {
        return 1;
    }

    public void mo1475a(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS file (_id integer primary key autoincrement, sdkname  varchar(20), filename varchar(100),md5 varchar(20),version varchar(20),dynamicversion varchar(20),status varchar(20),reservedfield varchar(20));");
        } catch (Throwable th) {
            eb.m742a(th, "DynamicFileDBCreator", "onCreate");
        }
    }

    public void mo1476a(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}

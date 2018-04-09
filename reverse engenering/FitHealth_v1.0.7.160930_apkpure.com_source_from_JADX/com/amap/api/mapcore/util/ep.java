package com.amap.api.mapcore.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/* compiled from: DB */
public class ep extends SQLiteOpenHelper {
    private ej f561a;

    public ep(Context context, String str, CursorFactory cursorFactory, int i, ej ejVar) {
        super(context, str, cursorFactory, i);
        this.f561a = ejVar;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        this.f561a.mo1475a(sQLiteDatabase);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        this.f561a.mo1476a(sQLiteDatabase, i, i2);
    }
}

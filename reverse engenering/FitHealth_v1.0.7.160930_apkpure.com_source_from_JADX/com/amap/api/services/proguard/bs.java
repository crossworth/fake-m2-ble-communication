package com.amap.api.services.proguard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/* compiled from: DB */
public class bs extends SQLiteOpenHelper {
    private bm f1423a;

    public bs(Context context, String str, CursorFactory cursorFactory, int i, bm bmVar) {
        super(context, str, cursorFactory, i);
        this.f1423a = bmVar;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        this.f1423a.mo1768a(sQLiteDatabase);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        this.f1423a.mo1769a(sQLiteDatabase, i, i2);
    }
}

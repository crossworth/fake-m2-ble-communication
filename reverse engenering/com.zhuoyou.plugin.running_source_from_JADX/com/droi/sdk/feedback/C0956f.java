package com.droi.sdk.feedback;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class C0956f extends SQLiteOpenHelper {
    public C0956f(Context context) {
        super(context, "feedback", null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS feedbackInfo(id integer primary key autoincrement,contact varchar,content varchar,time integer,reply varchar,reply_time integer)");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}

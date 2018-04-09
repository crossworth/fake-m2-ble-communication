package com.droi.sdk.push.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.droi.sdk.push.DroiPush;
import com.droi.sdk.push.utils.C1008c;

public class C0989d extends SQLiteOpenHelper {
    private Context f3278a;

    public C0989d(Context context) {
        super(context, "push.db", null, 1);
        this.f3278a = context;
    }

    private void m3044a(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("create table if not exists appinfo (_id integer primary key autoincrement, appid text, secret text, package text); ");
        sQLiteDatabase.execSQL("create table if not exists message (_id integer primary key autoincrement, msg_id integer, appid text, tag text, content text, interval integer, time_1 integer, time_2 integer);");
        sQLiteDatabase.execSQL("create table if not exists history (_id integer primary key autoincrement, msg_id integer, appid text, createtime integer);");
        sQLiteDatabase.execSQL("create table if not exists sdk (_id integer primary key autoincrement, sdkver integer, reorgtime integer);");
        String appId = DroiPush.getAppId(this.f3278a);
        String secret = DroiPush.getSecret(this.f3278a);
        String packageName = this.f3278a.getPackageName();
        appId = C1008c.m3127b(appId);
        secret = C1008c.m3127b(secret);
        packageName = C1008c.m3127b(packageName);
        ContentValues contentValues = new ContentValues();
        contentValues.put("appid", appId);
        contentValues.put("secret", secret);
        contentValues.put("package", packageName);
        sQLiteDatabase.insert("appinfo", null, contentValues);
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("sdkver", Integer.valueOf(2));
        sQLiteDatabase.insert("sdk", null, contentValues2);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        m3044a(sQLiteDatabase);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}

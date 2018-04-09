package com.baidu.location;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class C0526i extends SQLiteOpenHelper {
    private static C0526i f2265a = null;
    private static final String f2266do = "bd_geofence.db";
    private static final int f2267if = 1;

    public C0526i(Context context) {
        super(context, f2266do, null, 1);
    }

    public static synchronized C0526i m2195a(Context context) {
        C0526i c0526i;
        synchronized (C0526i.class) {
            if (f2265a == null) {
                f2265a = new C0526i(context);
            }
            c0526i = f2265a;
        }
        return c0526i;
    }

    private void m2196a(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS geofence (_id INTEGER PRIMARY KEY AUTOINCREMENT,geofence_id NTEXT,longitude NTEXT,latitude NTEXT,radius_type INTEGER,radius NTEXT,valid_date INTEGER,duration_millis INTEGER,coord_type NTEXT,next_active_time INTEGER,is_lac INTEGER,is_cell INTEGER,is_wifi INTEGER);");
        sQLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS geofence_detail (_id INTEGER PRIMARY KEY AUTOINCREMENT,geofence_id NTEXT,ap NTEXT,ap_backup NTEXT);");
        sQLiteDatabase.execSQL("CREATE INDEX  IF NOT EXISTS ap_index ON geofence_detail (ap);");
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        m2196a(sQLiteDatabase);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}

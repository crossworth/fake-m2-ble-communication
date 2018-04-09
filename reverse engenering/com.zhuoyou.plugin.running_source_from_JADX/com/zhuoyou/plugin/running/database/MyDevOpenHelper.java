package com.zhuoyou.plugin.running.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
import com.droi.greendao.dao.DaoMaster;
import com.droi.greendao.dao.DaoMaster.DevOpenHelper;

public class MyDevOpenHelper extends DevOpenHelper {
    public MyDevOpenHelper(Context context, String name, CursorFactory factory) {
        super(context, name, factory);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        long time = System.currentTimeMillis();
        Log.i("zhuqichao", "Upgrading schema from version " + oldVersion + " to " + newVersion);
        DaoMaster.dropAllTables(db, true);
        onCreate(db);
        switch (oldVersion) {
        }
        Log.i("zhuqichao", "Upgrading finished, used time : " + (System.currentTimeMillis() - time));
    }
}

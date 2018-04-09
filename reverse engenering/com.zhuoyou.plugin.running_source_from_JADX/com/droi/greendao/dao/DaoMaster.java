package com.droi.greendao.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 5;

    public static abstract class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, 5);
        }

        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version 5");
            DaoMaster.createAllTables(db, false);
        }
    }

    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            DaoMaster.dropAllTables(db, true);
            onCreate(db);
        }
    }

    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        SleepBeanDao.createTable(db, ifNotExists);
        SportBeanDao.createTable(db, ifNotExists);
        GpsPointBeanDao.createTable(db, ifNotExists);
        GpsSportBeanDao.createTable(db, ifNotExists);
        WeightBeanDao.createTable(db, ifNotExists);
        HeartBeanDao.createTable(db, ifNotExists);
    }

    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        SleepBeanDao.dropTable(db, ifExists);
        SportBeanDao.dropTable(db, ifExists);
        GpsPointBeanDao.dropTable(db, ifExists);
        GpsSportBeanDao.dropTable(db, ifExists);
        WeightBeanDao.dropTable(db, ifExists);
        HeartBeanDao.dropTable(db, ifExists);
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, 5);
        registerDaoClass(SleepBeanDao.class);
        registerDaoClass(SportBeanDao.class);
        registerDaoClass(GpsPointBeanDao.class);
        registerDaoClass(GpsSportBeanDao.class);
        registerDaoClass(WeightBeanDao.class);
        registerDaoClass(HeartBeanDao.class);
    }

    public DaoSession newSession() {
        return new DaoSession(this.db, IdentityScopeType.Session, this.daoConfigMap);
    }

    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(this.db, type, this.daoConfigMap);
    }
}

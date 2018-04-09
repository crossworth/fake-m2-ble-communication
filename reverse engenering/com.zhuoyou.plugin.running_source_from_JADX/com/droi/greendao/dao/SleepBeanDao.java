package com.droi.greendao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.droi.greendao.bean.SleepBean;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class SleepBeanDao extends AbstractDao<SleepBean, String> {
    public static final String TABLENAME = "sleep_data";

    public static class Properties {
        public static final Property Account = new Property(0, String.class, "account", false, "ACCOUNT");
        public static final Property BTtype = new Property(4, Integer.TYPE, "BTtype", false, "BTTYPE");
        public static final Property EndTime = new Property(2, String.class, "endTime", false, "END_TIME");
        public static final Property SleepData = new Property(3, String.class, "sleepData", false, "SLEEP_DATA");
        public static final Property StartTime = new Property(1, String.class, "startTime", true, "START_TIME");
        public static final Property Sync = new Property(5, Integer.TYPE, "sync", false, "SYNC");
    }

    public SleepBeanDao(DaoConfig config) {
        super(config);
    }

    public SleepBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'sleep_data' (" + "'ACCOUNT' TEXT NOT NULL ," + "'START_TIME' TEXT PRIMARY KEY NOT NULL ," + "'END_TIME' TEXT NOT NULL ," + "'SLEEP_DATA' TEXT NOT NULL ," + "'BTTYPE' INTEGER NOT NULL ," + "'SYNC' INTEGER NOT NULL );");
        db.execSQL("CREATE INDEX " + constraint + "IDX_sleep_data_START_TIME ON sleep_data" + " (START_TIME);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        db.execSQL("DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'sleep_data'");
    }

    protected void bindValues(SQLiteStatement stmt, SleepBean entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getAccount());
        String startTime = entity.getStartTime();
        if (startTime != null) {
            stmt.bindString(2, startTime);
        }
        stmt.bindString(3, entity.getEndTime());
        stmt.bindString(4, entity.getSleepData());
        stmt.bindLong(5, (long) entity.getBTtype());
        stmt.bindLong(6, (long) entity.getSync());
    }

    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1);
    }

    public SleepBean readEntity(Cursor cursor, int offset) {
        return new SleepBean(cursor.getString(offset + 0), cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), cursor.getString(offset + 2), cursor.getString(offset + 3), cursor.getInt(offset + 4), cursor.getInt(offset + 5));
    }

    public void readEntity(Cursor cursor, SleepBean entity, int offset) {
        entity.setAccount(cursor.getString(offset + 0));
        entity.setStartTime(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setEndTime(cursor.getString(offset + 2));
        entity.setSleepData(cursor.getString(offset + 3));
        entity.setBTtype(cursor.getInt(offset + 4));
        entity.setSync(cursor.getInt(offset + 5));
    }

    protected String updateKeyAfterInsert(SleepBean entity, long rowId) {
        return entity.getStartTime();
    }

    public String getKey(SleepBean entity) {
        if (entity != null) {
            return entity.getStartTime();
        }
        return null;
    }

    protected boolean isEntityUpdateable() {
        return true;
    }
}

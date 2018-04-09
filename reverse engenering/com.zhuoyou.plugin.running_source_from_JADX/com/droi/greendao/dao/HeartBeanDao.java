package com.droi.greendao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.droi.btlib.connection.MapConstants;
import com.droi.greendao.bean.HeartBean;
import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class HeartBeanDao extends AbstractDao<HeartBean, String> {
    public static final String TABLENAME = "heart_data";

    public static class Properties {
        public static final Property Account = new Property(1, String.class, "account", false, "ACCOUNT");
        public static final Property Count = new Property(2, Integer.TYPE, ParamKey.COUNT, false, "COUNT");
        public static final Property Date = new Property(0, String.class, MapConstants.DATE, true, "DATE");
        public static final Property Sync = new Property(3, Integer.TYPE, "sync", false, "SYNC");
    }

    public HeartBeanDao(DaoConfig config) {
        super(config);
    }

    public HeartBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'heart_data' (" + "'DATE' TEXT PRIMARY KEY NOT NULL ," + "'ACCOUNT' TEXT NOT NULL ," + "'COUNT' INTEGER NOT NULL ," + "'SYNC' INTEGER NOT NULL );");
        db.execSQL("CREATE INDEX " + constraint + "IDX_heart_data_DATE ON heart_data" + " (DATE);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        db.execSQL("DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'heart_data'");
    }

    protected void bindValues(SQLiteStatement stmt, HeartBean entity) {
        stmt.clearBindings();
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(1, date);
        }
        stmt.bindString(2, entity.getAccount());
        stmt.bindLong(3, (long) entity.getCount());
        stmt.bindLong(4, (long) entity.getSync());
    }

    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }

    public HeartBean readEntity(Cursor cursor, int offset) {
        return new HeartBean(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), cursor.getString(offset + 1), cursor.getInt(offset + 2), cursor.getInt(offset + 3));
    }

    public void readEntity(Cursor cursor, HeartBean entity, int offset) {
        entity.setDate(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setAccount(cursor.getString(offset + 1));
        entity.setCount(cursor.getInt(offset + 2));
        entity.setSync(cursor.getInt(offset + 3));
    }

    protected String updateKeyAfterInsert(HeartBean entity, long rowId) {
        return entity.getDate();
    }

    public String getKey(HeartBean entity) {
        if (entity != null) {
            return entity.getDate();
        }
        return null;
    }

    protected boolean isEntityUpdateable() {
        return true;
    }
}

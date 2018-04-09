package com.droi.greendao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.droi.btlib.connection.MapConstants;
import com.droi.greendao.bean.WeightBean;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class WeightBeanDao extends AbstractDao<WeightBean, String> {
    public static final String TABLENAME = "weight_data";

    public static class Properties {
        public static final Property Account = new Property(1, String.class, "account", false, "ACCOUNT");
        public static final Property Date = new Property(0, String.class, MapConstants.DATE, true, "DATE");
        public static final Property Sync = new Property(3, Integer.TYPE, "sync", false, "SYNC");
        public static final Property Weight = new Property(2, Float.TYPE, "weight", false, "WEIGHT");
    }

    public WeightBeanDao(DaoConfig config) {
        super(config);
    }

    public WeightBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'weight_data' (" + "'DATE' TEXT PRIMARY KEY NOT NULL ," + "'ACCOUNT' TEXT NOT NULL ," + "'WEIGHT' REAL NOT NULL ," + "'SYNC' INTEGER NOT NULL );");
        db.execSQL("CREATE INDEX " + constraint + "IDX_weight_data_DATE ON weight_data" + " (DATE);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        db.execSQL("DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'weight_data'");
    }

    protected void bindValues(SQLiteStatement stmt, WeightBean entity) {
        stmt.clearBindings();
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(1, date);
        }
        stmt.bindString(2, entity.getAccount());
        stmt.bindDouble(3, (double) entity.getWeight());
        stmt.bindLong(4, (long) entity.getSync());
    }

    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }

    public WeightBean readEntity(Cursor cursor, int offset) {
        return new WeightBean(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), cursor.getString(offset + 1), cursor.getFloat(offset + 2), cursor.getInt(offset + 3));
    }

    public void readEntity(Cursor cursor, WeightBean entity, int offset) {
        entity.setDate(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setAccount(cursor.getString(offset + 1));
        entity.setWeight(cursor.getFloat(offset + 2));
        entity.setSync(cursor.getInt(offset + 3));
    }

    protected String updateKeyAfterInsert(WeightBean entity, long rowId) {
        return entity.getDate();
    }

    public String getKey(WeightBean entity) {
        if (entity != null) {
            return entity.getDate();
        }
        return null;
    }

    protected boolean isEntityUpdateable() {
        return true;
    }
}

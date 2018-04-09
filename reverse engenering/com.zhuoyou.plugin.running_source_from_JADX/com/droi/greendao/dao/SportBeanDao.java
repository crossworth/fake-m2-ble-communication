package com.droi.greendao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.droi.btlib.connection.MapConstants;
import com.droi.greendao.bean.SportBean;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class SportBeanDao extends AbstractDao<SportBean, String> {
    public static final String TABLENAME = "sport_data";

    public static class Properties {
        public static final Property Account = new Property(1, String.class, "account", false, "ACCOUNT");
        public static final Property BTtype = new Property(9, Integer.TYPE, "BTtype", false, "BTTYPE");
        public static final Property Cal = new Property(7, Float.TYPE, "cal", false, "CAL");
        public static final Property Complete = new Property(5, Integer.TYPE, "complete", false, "COMPLETE");
        public static final Property Date = new Property(0, String.class, MapConstants.DATE, true, "DATE");
        public static final Property Distance = new Property(6, Float.TYPE, "distance", false, "DISTANCE");
        public static final Property SportData = new Property(8, String.class, "sportData", false, "SPORT_DATA");
        public static final Property StepDev = new Property(3, Integer.TYPE, "stepDev", false, "STEP_DEV");
        public static final Property StepPhone = new Property(2, Integer.TYPE, "stepPhone", false, "STEP_PHONE");
        public static final Property StepTarget = new Property(4, Integer.TYPE, "stepTarget", false, "STEP_TARGET");
        public static final Property Sync = new Property(10, Integer.TYPE, "sync", false, "SYNC");
    }

    public SportBeanDao(DaoConfig config) {
        super(config);
    }

    public SportBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'sport_data' (" + "'DATE' TEXT PRIMARY KEY NOT NULL ," + "'ACCOUNT' TEXT NOT NULL ," + "'STEP_PHONE' INTEGER NOT NULL ," + "'STEP_DEV' INTEGER NOT NULL ," + "'STEP_TARGET' INTEGER NOT NULL ," + "'COMPLETE' INTEGER NOT NULL ," + "'DISTANCE' REAL NOT NULL ," + "'CAL' REAL NOT NULL ," + "'SPORT_DATA' TEXT NOT NULL ," + "'BTTYPE' INTEGER NOT NULL ," + "'SYNC' INTEGER NOT NULL );");
        db.execSQL("CREATE INDEX " + constraint + "IDX_sport_data_DATE ON sport_data" + " (DATE);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        db.execSQL("DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'sport_data'");
    }

    protected void bindValues(SQLiteStatement stmt, SportBean entity) {
        stmt.clearBindings();
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(1, date);
        }
        stmt.bindString(2, entity.getAccount());
        stmt.bindLong(3, (long) entity.getStepPhone());
        stmt.bindLong(4, (long) entity.getStepDev());
        stmt.bindLong(5, (long) entity.getStepTarget());
        stmt.bindLong(6, (long) entity.getComplete());
        stmt.bindDouble(7, (double) entity.getDistance());
        stmt.bindDouble(8, (double) entity.getCal());
        stmt.bindString(9, entity.getSportData());
        stmt.bindLong(10, (long) entity.getBTtype());
        stmt.bindLong(11, (long) entity.getSync());
    }

    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }

    public SportBean readEntity(Cursor cursor, int offset) {
        return new SportBean(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), cursor.getString(offset + 1), cursor.getInt(offset + 2), cursor.getInt(offset + 3), cursor.getInt(offset + 4), cursor.getInt(offset + 5), cursor.getFloat(offset + 6), cursor.getFloat(offset + 7), cursor.getString(offset + 8), cursor.getInt(offset + 9), cursor.getInt(offset + 10));
    }

    public void readEntity(Cursor cursor, SportBean entity, int offset) {
        entity.setDate(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setAccount(cursor.getString(offset + 1));
        entity.setStepPhone(cursor.getInt(offset + 2));
        entity.setStepDev(cursor.getInt(offset + 3));
        entity.setStepTarget(cursor.getInt(offset + 4));
        entity.setComplete(cursor.getInt(offset + 5));
        entity.setDistance(cursor.getFloat(offset + 6));
        entity.setCal(cursor.getFloat(offset + 7));
        entity.setSportData(cursor.getString(offset + 8));
        entity.setBTtype(cursor.getInt(offset + 9));
        entity.setSync(cursor.getInt(offset + 10));
    }

    protected String updateKeyAfterInsert(SportBean entity, long rowId) {
        return entity.getDate();
    }

    public String getKey(SportBean entity) {
        if (entity != null) {
            return entity.getDate();
        }
        return null;
    }

    protected boolean isEntityUpdateable() {
        return true;
    }
}

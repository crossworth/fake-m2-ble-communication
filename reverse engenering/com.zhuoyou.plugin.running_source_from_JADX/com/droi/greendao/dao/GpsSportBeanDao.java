package com.droi.greendao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.droi.greendao.bean.GpsSportBean;
import com.umeng.facebook.share.internal.ShareConstants;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class GpsSportBeanDao extends AbstractDao<GpsSportBean, String> {
    public static final String TABLENAME = "gps_sport_data";

    public static class Properties {
        public static final Property Account = new Property(1, String.class, "account", false, "ACCOUNT");
        public static final Property Cal = new Property(6, Float.TYPE, "cal", false, "CAL");
        public static final Property Distance = new Property(5, Float.TYPE, "distance", false, "DISTANCE");
        public static final Property Duration = new Property(4, Integer.TYPE, "duration", false, "DURATION");
        public static final Property Heart = new Property(8, Integer.TYPE, "heart", false, "HEART");
        public static final Property Id = new Property(0, String.class, ShareConstants.WEB_DIALOG_PARAM_ID, true, "ID");
        public static final Property StartTime = new Property(2, String.class, "startTime", false, "START_TIME");
        public static final Property Step = new Property(7, Integer.TYPE, "step", false, "STEP");
        public static final Property Stop = new Property(9, Integer.TYPE, "stop", false, "STOP");
        public static final Property StopTime = new Property(3, String.class, "stopTime", false, "STOP_TIME");
        public static final Property Sync = new Property(10, Integer.TYPE, "sync", false, "SYNC");
    }

    public GpsSportBeanDao(DaoConfig config) {
        super(config);
    }

    public GpsSportBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'gps_sport_data' (" + "'ID' TEXT PRIMARY KEY NOT NULL ," + "'ACCOUNT' TEXT NOT NULL ," + "'START_TIME' TEXT NOT NULL ," + "'STOP_TIME' TEXT NOT NULL ," + "'DURATION' INTEGER NOT NULL ," + "'DISTANCE' REAL NOT NULL ," + "'CAL' REAL NOT NULL ," + "'STEP' INTEGER NOT NULL ," + "'HEART' INTEGER NOT NULL ," + "'STOP' INTEGER NOT NULL ," + "'SYNC' INTEGER NOT NULL );");
        db.execSQL("CREATE INDEX " + constraint + "IDX_gps_sport_data_ID ON gps_sport_data" + " (ID);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        db.execSQL("DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'gps_sport_data'");
    }

    protected void bindValues(SQLiteStatement stmt, GpsSportBean entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getId());
        stmt.bindString(2, entity.getAccount());
        stmt.bindString(3, entity.getStartTime());
        stmt.bindString(4, entity.getStopTime());
        stmt.bindLong(5, (long) entity.getDuration());
        stmt.bindDouble(6, (double) entity.getDistance());
        stmt.bindDouble(7, (double) entity.getCal());
        stmt.bindLong(8, (long) entity.getStep());
        stmt.bindLong(9, (long) entity.getHeart());
        stmt.bindLong(10, (long) entity.getStop());
        stmt.bindLong(11, (long) entity.getSync());
    }

    public String readKey(Cursor cursor, int offset) {
        return cursor.getString(offset + 0);
    }

    public GpsSportBean readEntity(Cursor cursor, int offset) {
        return new GpsSportBean(cursor.getString(offset + 0), cursor.getString(offset + 1), cursor.getString(offset + 2), cursor.getString(offset + 3), cursor.getInt(offset + 4), cursor.getFloat(offset + 5), cursor.getFloat(offset + 6), cursor.getInt(offset + 7), cursor.getInt(offset + 8), cursor.getInt(offset + 9), cursor.getInt(offset + 10));
    }

    public void readEntity(Cursor cursor, GpsSportBean entity, int offset) {
        entity.setId(cursor.getString(offset + 0));
        entity.setAccount(cursor.getString(offset + 1));
        entity.setStartTime(cursor.getString(offset + 2));
        entity.setStopTime(cursor.getString(offset + 3));
        entity.setDuration(cursor.getInt(offset + 4));
        entity.setDistance(cursor.getFloat(offset + 5));
        entity.setCal(cursor.getFloat(offset + 6));
        entity.setStep(cursor.getInt(offset + 7));
        entity.setHeart(cursor.getInt(offset + 8));
        entity.setStop(cursor.getInt(offset + 9));
        entity.setSync(cursor.getInt(offset + 10));
    }

    protected String updateKeyAfterInsert(GpsSportBean entity, long rowId) {
        return entity.getId();
    }

    public String getKey(GpsSportBean entity) {
        if (entity != null) {
            return entity.getId();
        }
        return null;
    }

    protected boolean isEntityUpdateable() {
        return true;
    }
}

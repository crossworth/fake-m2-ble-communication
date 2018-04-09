package com.droi.greendao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.droi.btlib.connection.MapConstants;
import com.droi.greendao.bean.GpsPointBean;
import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;
import com.umeng.facebook.share.internal.ShareConstants;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class GpsPointBeanDao extends AbstractDao<GpsPointBean, Long> {
    public static final String TABLENAME = "gps_point_data";

    public static class Properties {
        public static final Property Account = new Property(2, String.class, "account", false, "ACCOUNT");
        public static final Property Address = new Property(6, String.class, MapConstants.ADDRESS, false, "ADDRESS");
        public static final Property Cadence = new Property(8, Integer.TYPE, "cadence", false, "CADENCE");
        public static final Property Heart = new Property(7, Integer.TYPE, "heart", false, "HEART");
        public static final Property Id = new Property(0, Long.class, ShareConstants.WEB_DIALOG_PARAM_ID, true, MapConstants._ID);
        public static final Property Latitude = new Property(3, Double.TYPE, ParamKey.LATITUDE, false, "LATITUDE");
        public static final Property Longitude = new Property(4, Double.TYPE, ParamKey.LONGITUDE, false, "LONGITUDE");
        public static final Property SportId = new Property(1, String.class, "sportId", false, "SPORT_ID");
        public static final Property Sync = new Property(9, Integer.TYPE, "sync", false, "SYNC");
        public static final Property Time = new Property(5, String.class, LogColumns.TIME, false, "TIME");
    }

    public GpsPointBeanDao(DaoConfig config) {
        super(config);
    }

    public GpsPointBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'gps_point_data' (" + "'_id' INTEGER PRIMARY KEY ," + "'SPORT_ID' TEXT NOT NULL ," + "'ACCOUNT' TEXT NOT NULL ," + "'LATITUDE' REAL NOT NULL ," + "'LONGITUDE' REAL NOT NULL ," + "'TIME' TEXT NOT NULL ," + "'ADDRESS' TEXT NOT NULL ," + "'HEART' INTEGER NOT NULL ," + "'CADENCE' INTEGER NOT NULL ," + "'SYNC' INTEGER NOT NULL );");
        db.execSQL("CREATE INDEX " + constraint + "IDX_gps_point_data__id ON gps_point_data" + " (_id);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        db.execSQL("DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'gps_point_data'");
    }

    protected void bindValues(SQLiteStatement stmt, GpsPointBean entity) {
        stmt.clearBindings();
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id.longValue());
        }
        stmt.bindString(2, entity.getSportId());
        stmt.bindString(3, entity.getAccount());
        stmt.bindDouble(4, entity.getLatitude());
        stmt.bindDouble(5, entity.getLongitude());
        stmt.bindString(6, entity.getTime());
        stmt.bindString(7, entity.getAddress());
        stmt.bindLong(8, (long) entity.getHeart());
        stmt.bindLong(9, (long) entity.getCadence());
        stmt.bindLong(10, (long) entity.getSync());
    }

    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : Long.valueOf(cursor.getLong(offset + 0));
    }

    public GpsPointBean readEntity(Cursor cursor, int offset) {
        return new GpsPointBean(cursor.isNull(offset + 0) ? null : Long.valueOf(cursor.getLong(offset + 0)), cursor.getString(offset + 1), cursor.getString(offset + 2), cursor.getDouble(offset + 3), cursor.getDouble(offset + 4), cursor.getString(offset + 5), cursor.getString(offset + 6), cursor.getInt(offset + 7), cursor.getInt(offset + 8), cursor.getInt(offset + 9));
    }

    public void readEntity(Cursor cursor, GpsPointBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : Long.valueOf(cursor.getLong(offset + 0)));
        entity.setSportId(cursor.getString(offset + 1));
        entity.setAccount(cursor.getString(offset + 2));
        entity.setLatitude(cursor.getDouble(offset + 3));
        entity.setLongitude(cursor.getDouble(offset + 4));
        entity.setTime(cursor.getString(offset + 5));
        entity.setAddress(cursor.getString(offset + 6));
        entity.setHeart(cursor.getInt(offset + 7));
        entity.setCadence(cursor.getInt(offset + 8));
        entity.setSync(cursor.getInt(offset + 9));
    }

    protected Long updateKeyAfterInsert(GpsPointBean entity, long rowId) {
        entity.setId(Long.valueOf(rowId));
        return Long.valueOf(rowId);
    }

    public Long getKey(GpsPointBean entity) {
        if (entity != null) {
            return entity.getId();
        }
        return null;
    }

    protected boolean isEntityUpdateable() {
        return true;
    }
}

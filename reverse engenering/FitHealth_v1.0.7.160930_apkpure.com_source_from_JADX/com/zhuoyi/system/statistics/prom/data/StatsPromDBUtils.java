package com.zhuoyi.system.statistics.prom.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import com.zhuoyi.system.network.object.AdLogInfo;
import com.zhuoyi.system.statistics.prom.model.MyDownloadResult;
import java.util.ArrayList;
import java.util.Locale;

public class StatsPromDBUtils {
    public static final String DATABASE_NAME = "ZySDK_statistics_prom";
    public static final int DATABASE_VERSION = 2;
    public static final String PROM_STATISTICS_DOWNLOAD_TABLE = "statistics_prom_download_table";
    public static final String PROM_STATISTICS_TABLE = "statistics_prom_table";
    public static final String PROM_STATS_ACTION = "prom_stats_action";
    public static final String PROM_STATS_APP_VER = "prom_stats_app_ver";
    public static final String PROM_STATS_DOWNLOAD_RESULT = "prom_stats_download_result";
    public static final String PROM_STATS_DOWNLOAD_SIZE = "prom_stats_dowload_size";
    public static final String PROM_STATS_ICONID = "prom_stats_iconid";
    public static final String PROM_STATS_NUM = "prom_stats_num";
    public static final String PROM_STATS_OFFSET = "prom_stats_offset";
    public static final String PROM_STATS_PACKNAME = "prom_stats_packname";
    public static final String PROM_STATS_SDK_VER = "prom_stats_sdk_ver";
    public static final String PROM_STATS_SOURCE1 = "prom_stats_source1";
    public static final String PROM_STATS_SOURCE2 = "prom_stats_source2";
    public static final String PROM_STATS_STAYTIME = "prom_stats_staytime";
    public static final String PROM_STATS_TOTAL_SIZE = "prom_stats_total_size";
    private static StatsPromDBUtils instance = null;
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase = null;
    private SQLiteOpenHelper mSqlOpenHelper;

    public class PromotionDataBaseHelper extends SQLiteOpenHelper {
        public PromotionDataBaseHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS statistics_prom_table (_id INTEGER PRIMARY KEY AUTOINCREMENT, prom_stats_action INTEGER, prom_stats_packname text, prom_stats_app_ver INTEGER, prom_stats_sdk_ver INTEGER, prom_stats_num INTEGER, prom_stats_source1 INTEGER, prom_stats_source2 INTEGER, prom_stats_iconid INTEGER, prom_stats_staytime INTEGER)");
            db.execSQL("CREATE TABLE IF NOT EXISTS statistics_prom_download_table (_id INTEGER PRIMARY KEY AUTOINCREMENT, prom_stats_packname text, prom_stats_app_ver INTEGER, prom_stats_total_size INTEGER, prom_stats_offset INTEGER, prom_stats_download_result INTEGER, prom_stats_dowload_size INTEGER, prom_stats_source1 INTEGER, prom_stats_source2 INTEGER)");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                onCreate(db);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private StatsPromDBUtils(Context context) {
        this.mContext = context;
    }

    public static synchronized StatsPromDBUtils getInstance(Context context) {
        StatsPromDBUtils statsPromDBUtils;
        synchronized (StatsPromDBUtils.class) {
            if (instance == null) {
                instance = new StatsPromDBUtils(context);
            }
            if (!instance.isOpen()) {
                instance.open();
            }
            statsPromDBUtils = instance;
        }
        return statsPromDBUtils;
    }

    private boolean isOpen() {
        return (this.mSqlOpenHelper == null || this.mSQLiteDatabase == null) ? false : true;
    }

    public void open() {
        try {
            this.mSqlOpenHelper = new PromotionDataBaseHelper(this.mContext, DATABASE_NAME, null, 2);
            this.mSQLiteDatabase = this.mSqlOpenHelper.getWritableDatabase();
            this.mSQLiteDatabase.setLocale(Locale.CHINESE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (this.mSqlOpenHelper != null) {
            this.mSqlOpenHelper.close();
        }
    }

    public void insertAdLogInfo(AdLogInfo info) {
        if (this.mSQLiteDatabase != null) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(PROM_STATS_ACTION, Integer.valueOf(info.getAction()));
            initialValues.put(PROM_STATS_PACKNAME, info.getPackageName());
            initialValues.put(PROM_STATS_APP_VER, Integer.valueOf(info.getAppVer()));
            initialValues.put(PROM_STATS_SDK_VER, Integer.valueOf(info.getSdkVer()));
            int key = ((((info.getAction() + info.getAppVer()) + info.getSdkVer()) + info.getIconId()) + info.getSource1()) + info.getSource2();
            initialValues.put(PROM_STATS_NUM, Integer.valueOf(info.getNum()));
            initialValues.put(PROM_STATS_SOURCE1, Short.valueOf(info.getSource1()));
            initialValues.put(PROM_STATS_SOURCE2, Short.valueOf(info.getSource2()));
            initialValues.put(PROM_STATS_ICONID, Integer.valueOf(info.getIconId()));
            initialValues.put(PROM_STATS_STAYTIME, Long.valueOf(info.getStayTime()));
            try {
                this.mSQLiteDatabase.insert(PROM_STATISTICS_TABLE, null, initialValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addAdLogInfo(AdLogInfo info) {
        if (this.mSQLiteDatabase != null) {
            AdLogInfo sta = queryAdLogInfo(info);
            if (sta != null) {
                sta.setNum(sta.getNum() + info.getNum());
                sta.setStayTime(sta.getStayTime() + info.getStayTime());
                updateAdLogInfo(sta);
                return;
            }
            insertAdLogInfo(info);
        }
    }

    public boolean deleteAdLogInfo(AdLogInfo info) {
        boolean ret = false;
        if (this.mSQLiteDatabase == null) {
            return 0;
        }
        try {
            ret = this.mSQLiteDatabase.delete(PROM_STATISTICS_TABLE, new StringBuilder("prom_stats_action='").append(info.getAction()).append("' AND ").append(PROM_STATS_PACKNAME).append("='").append(info.getPackageName()).append("' AND ").append(PROM_STATS_SDK_VER).append("='").append(info.getSdkVer()).append("' AND ").append(PROM_STATS_APP_VER).append("='").append(info.getAppVer()).append("' AND ").append(PROM_STATS_SOURCE1).append("='").append(info.getSource1()).append("' AND ").append(PROM_STATS_SOURCE2).append("='").append(info.getSource2()).append("' AND ").append(PROM_STATS_ICONID).append("='").append(info.getIconId()).append("'").toString(), null) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public AdLogInfo queryAdLogInfo(AdLogInfo info) {
        Cursor c = null;
        AdLogInfo r = null;
        if (this.mSQLiteDatabase == null) {
            return null;
        }
        try {
            c = this.mSQLiteDatabase.query(true, PROM_STATISTICS_TABLE, new String[]{PROM_STATS_ACTION, PROM_STATS_PACKNAME, PROM_STATS_APP_VER, PROM_STATS_SDK_VER, PROM_STATS_NUM, PROM_STATS_SOURCE1, PROM_STATS_SOURCE2, PROM_STATS_ICONID, PROM_STATS_STAYTIME}, "prom_stats_action='" + info.getAction() + "' AND " + PROM_STATS_ICONID + "='" + info.getIconId() + "' AND " + PROM_STATS_PACKNAME + "='" + info.getPackageName() + "' AND " + PROM_STATS_SOURCE1 + "='" + info.getSource1() + "' AND " + PROM_STATS_SOURCE2 + "='" + info.getSource2() + "' AND " + PROM_STATS_SDK_VER + "='" + info.getSdkVer() + "' AND " + PROM_STATS_APP_VER + "='" + info.getAppVer() + "'", null, null, null, null, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (c != null) {
            if (c.getCount() > 0) {
                c.moveToFirst();
                r = new AdLogInfo();
                r.setAction(c.getInt(c.getColumnIndex(PROM_STATS_ACTION)));
                r.setPackageName(c.getString(c.getColumnIndex(PROM_STATS_PACKNAME)));
                r.setAppVer(c.getInt(c.getColumnIndex(PROM_STATS_APP_VER)));
                r.setSdkVer(c.getInt(c.getColumnIndex(PROM_STATS_SDK_VER)));
                r.setNum(c.getInt(c.getColumnIndex(PROM_STATS_NUM)));
                r.setSource1((short) c.getInt(c.getColumnIndex(PROM_STATS_SOURCE1)));
                r.setSource2((short) c.getInt(c.getColumnIndex(PROM_STATS_SOURCE2)));
                r.setIconId(c.getInt(c.getColumnIndex(PROM_STATS_ICONID)));
                r.setStayTime((long) c.getInt(c.getColumnIndex(PROM_STATS_STAYTIME)));
            }
            c.close();
        }
        return r;
    }

    public Cursor fetchAllData() {
        Cursor ret = null;
        if (this.mSQLiteDatabase == null) {
            return ret;
        }
        try {
            ret = this.mSQLiteDatabase.query(PROM_STATISTICS_TABLE, new String[]{PROM_STATS_ACTION, PROM_STATS_PACKNAME, PROM_STATS_APP_VER, PROM_STATS_SDK_VER, PROM_STATS_NUM, PROM_STATS_SOURCE1, PROM_STATS_SOURCE2, PROM_STATS_ICONID, PROM_STATS_STAYTIME}, null, null, null, null, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public ArrayList<AdLogInfo> queryAdLogInfoList() {
        ArrayList<AdLogInfo> ret = new ArrayList();
        Cursor c = fetchAllData();
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                AdLogInfo r = new AdLogInfo();
                r.setAction(c.getInt(c.getColumnIndex(PROM_STATS_ACTION)));
                r.setPackageName(c.getString(c.getColumnIndex(PROM_STATS_PACKNAME)));
                r.setAppVer(c.getInt(c.getColumnIndex(PROM_STATS_APP_VER)));
                r.setSdkVer(c.getInt(c.getColumnIndex(PROM_STATS_SDK_VER)));
                r.setNum(c.getInt(c.getColumnIndex(PROM_STATS_NUM)));
                r.setSource1((short) c.getInt(c.getColumnIndex(PROM_STATS_SOURCE1)));
                r.setSource2((short) c.getInt(c.getColumnIndex(PROM_STATS_SOURCE2)));
                r.setIconId(c.getInt(c.getColumnIndex(PROM_STATS_ICONID)));
                r.setStayTime((long) c.getInt(c.getColumnIndex(PROM_STATS_STAYTIME)));
                ret.add(r);
                c.moveToNext();
            }
        }
        if (!(c == null || c.isClosed())) {
            c.close();
        }
        return ret;
    }

    public boolean updateAdLogInfo(AdLogInfo info) {
        ContentValues initialValues = new ContentValues();
        boolean ret = false;
        if (this.mSQLiteDatabase == null) {
            return 0;
        }
        initialValues.put(PROM_STATS_ACTION, Integer.valueOf(info.getAction()));
        initialValues.put(PROM_STATS_PACKNAME, info.getPackageName());
        initialValues.put(PROM_STATS_APP_VER, Integer.valueOf(info.getAppVer()));
        initialValues.put(PROM_STATS_SDK_VER, Integer.valueOf(info.getSdkVer()));
        int key = ((((info.getAction() + info.getAppVer()) + info.getSdkVer()) + info.getIconId()) + info.getSource1()) + info.getSource2();
        initialValues.put(PROM_STATS_NUM, Integer.valueOf(info.getNum()));
        initialValues.put(PROM_STATS_SOURCE1, Short.valueOf(info.getSource1()));
        initialValues.put(PROM_STATS_SOURCE2, Short.valueOf(info.getSource2()));
        initialValues.put(PROM_STATS_ICONID, Integer.valueOf(info.getIconId()));
        initialValues.put(PROM_STATS_STAYTIME, Long.valueOf(info.getStayTime()));
        try {
            ret = this.mSQLiteDatabase.update(PROM_STATISTICS_TABLE, initialValues, new StringBuilder("prom_stats_action='").append(info.getAction()).append("' AND ").append(PROM_STATS_PACKNAME).append("='").append(info.getPackageName()).append("' AND ").append(PROM_STATS_APP_VER).append("='").append(info.getAppVer()).append("' AND ").append(PROM_STATS_SDK_VER).append("='").append(info.getSdkVer()).append("' AND ").append(PROM_STATS_SOURCE1).append("='").append(info.getSource1()).append("' AND ").append(PROM_STATS_SOURCE2).append("='").append(info.getSource2()).append("' AND ").append(PROM_STATS_ICONID).append("='").append(info.getIconId()).append("'").toString(), null) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public int queryAdLogTableLastId() {
        int ret = 0;
        Cursor c = null;
        if (this.mSQLiteDatabase == null) {
            return 0;
        }
        try {
            c = this.mSQLiteDatabase.query(PROM_STATISTICS_TABLE, new String[]{"_id"}, null, null, null, null, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (c != null) {
            if (c.getCount() > 0) {
                c.moveToLast();
                ret = c.getInt(c.getColumnIndex("_id"));
            }
            c.close();
        }
        return ret;
    }

    public void deleteAllAdLogInfo() {
        if (this.mSQLiteDatabase != null) {
            int lastId = queryAdLogTableLastId();
            if (lastId > 0) {
                this.mSQLiteDatabase.delete(PROM_STATISTICS_TABLE, "_id > 0 and _id <= " + lastId, null);
            }
        }
    }

    public void insertDownloadResult(MyDownloadResult result) {
        if (this.mSQLiteDatabase != null) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(PROM_STATS_PACKNAME, result.getPackageName());
            initialValues.put(PROM_STATS_APP_VER, Integer.valueOf(result.getVersionCode()));
            initialValues.put(PROM_STATS_TOTAL_SIZE, Integer.valueOf(result.getTotalSize()));
            initialValues.put(PROM_STATS_SOURCE1, Integer.valueOf(result.getSource1()));
            initialValues.put(PROM_STATS_SOURCE2, Integer.valueOf(result.getSource2()));
            initialValues.put(PROM_STATS_DOWNLOAD_RESULT, Integer.valueOf(result.getDownloadResult()));
            initialValues.put(PROM_STATS_DOWNLOAD_SIZE, Integer.valueOf(result.getDownloadSize()));
            initialValues.put(PROM_STATS_OFFSET, Integer.valueOf(result.getOffset()));
            try {
                this.mSQLiteDatabase.insert(PROM_STATISTICS_DOWNLOAD_TABLE, null, initialValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<MyDownloadResult> queryDownloadResultList() {
        ArrayList<MyDownloadResult> ret = new ArrayList();
        if (this.mSQLiteDatabase != null) {
            Cursor c = null;
            try {
                c = this.mSQLiteDatabase.query(PROM_STATISTICS_DOWNLOAD_TABLE, new String[]{PROM_STATS_PACKNAME, PROM_STATS_APP_VER, PROM_STATS_OFFSET, PROM_STATS_TOTAL_SIZE, PROM_STATS_SOURCE1, PROM_STATS_SOURCE2, PROM_STATS_DOWNLOAD_RESULT, PROM_STATS_DOWNLOAD_SIZE}, null, null, null, null, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    while (!c.isAfterLast()) {
                        MyDownloadResult r = new MyDownloadResult();
                        r.setPackageName(c.getString(c.getColumnIndex(PROM_STATS_PACKNAME)));
                        r.setVersionCode(c.getInt(c.getColumnIndex(PROM_STATS_APP_VER)));
                        r.setTotalSize(c.getInt(c.getColumnIndex(PROM_STATS_TOTAL_SIZE)));
                        r.setOffset(c.getInt(c.getColumnIndex(PROM_STATS_OFFSET)));
                        r.setSource1((short) c.getInt(c.getColumnIndex(PROM_STATS_SOURCE1)));
                        r.setSource2((short) c.getInt(c.getColumnIndex(PROM_STATS_SOURCE2)));
                        r.setDownloadSize(c.getInt(c.getColumnIndex(PROM_STATS_DOWNLOAD_SIZE)));
                        r.setDownloadResult(c.getInt(c.getColumnIndex(PROM_STATS_DOWNLOAD_RESULT)));
                        ret.add(r);
                        c.moveToNext();
                    }
                }
                c.close();
            }
        }
        return ret;
    }

    public int queryDownloadTableLastId() {
        int ret = 0;
        if (this.mSQLiteDatabase == null) {
            return 0;
        }
        Cursor c = null;
        try {
            c = this.mSQLiteDatabase.query(PROM_STATISTICS_DOWNLOAD_TABLE, new String[]{"_id"}, null, null, null, null, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (c != null) {
            if (c.getCount() > 0) {
                c.moveToLast();
                ret = c.getInt(c.getColumnIndex("_id"));
            }
            c.close();
        }
        return ret;
    }

    public void deleteAllDownloadResultInfo() {
        if (this.mSQLiteDatabase != null) {
            int lastId = queryDownloadTableLastId();
            if (lastId > 0) {
                this.mSQLiteDatabase.delete(PROM_STATISTICS_DOWNLOAD_TABLE, "_id > 0 and _id <= " + lastId, null);
            }
        }
    }
}

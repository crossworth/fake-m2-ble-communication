package com.zhuoyi.system.statistics.sale.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Locale;

public class StatsSaleDBUtils {
    public static final String DATABASE_NAME = "ZySDK_statistics_sale";
    public static final int DATABASE_VERSION = 1;
    public static final String SALE_STATS_ACTIVE_STATE = "sale_stats_active_state";
    public static final String SALE_STATS_ACTIVE_TIME = "sale_stats_active_time";
    public static final String SALE_STATS_IMSI = "sale_stats_imsi";
    public static final String SALE_STATS_STAY_TIME = "sale_stats_stay_time";
    public static final String SALE_STATS_TABLE = "stats_sale_table";
    private static StatsSaleDBUtils instance = null;
    private Context mContext = null;
    private SQLiteDatabase mSQLiteDatabase = null;
    private SQLiteOpenHelper mSqlOpenHelper = null;

    public class SaleStatsDataBaseHelper extends SQLiteOpenHelper {
        public SaleStatsDataBaseHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL("CREATE TABLE stats_sale_table(sale_stats_imsi text PRIMARY KEY, sale_stats_active_time text, sale_stats_stay_time INTEGER, sale_stats_active_state INTEGER) ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL("drop table if exists stats_sale_table");
                onCreate(db);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private StatsSaleDBUtils(Context context) {
        this.mContext = context;
    }

    public static synchronized StatsSaleDBUtils getInstance(Context context) {
        StatsSaleDBUtils statsSaleDBUtils;
        synchronized (StatsSaleDBUtils.class) {
            if (instance == null) {
                instance = new StatsSaleDBUtils(context);
            }
            if (!instance.isOpen()) {
                instance.open();
            }
            statsSaleDBUtils = instance;
        }
        return statsSaleDBUtils;
    }

    private boolean isOpen() {
        return (this.mSqlOpenHelper == null || this.mSQLiteDatabase == null) ? false : true;
    }

    public void open() {
        try {
            this.mSqlOpenHelper = new SaleStatsDataBaseHelper(this.mContext, DATABASE_NAME, null, 1);
            this.mSQLiteDatabase = this.mSqlOpenHelper.getWritableDatabase();
            this.mSQLiteDatabase.setLocale(Locale.CHINESE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (this.mSQLiteDatabase != null) {
            this.mSQLiteDatabase.close();
        }
        if (this.mSqlOpenHelper != null) {
            this.mSqlOpenHelper.close();
        }
        instance = null;
    }

    private long insertInSaleStatsTable(ContentValues initialValues) {
        long ret = 0;
        if (this.mSQLiteDatabase == null) {
            return ret;
        }
        try {
            ret = this.mSQLiteDatabase.insert(SALE_STATS_TABLE, null, initialValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private int deleteInSaleStatsTable(String whereClause) {
        int ret = 0;
        if (this.mSQLiteDatabase == null) {
            return ret;
        }
        try {
            ret = this.mSQLiteDatabase.delete(SALE_STATS_TABLE, whereClause, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private Cursor queryInSaleStatsTable(String selection) {
        Cursor ret = null;
        if (this.mSQLiteDatabase == null) {
            return ret;
        }
        try {
            ret = this.mSQLiteDatabase.query(SALE_STATS_TABLE, new String[]{SALE_STATS_IMSI, SALE_STATS_ACTIVE_TIME, SALE_STATS_STAY_TIME, SALE_STATS_ACTIVE_STATE}, selection, null, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private int updateInSaleStatsTable(ContentValues initialValues, String whereClause) {
        int ret = 0;
        if (this.mSQLiteDatabase == null) {
            return ret;
        }
        try {
            ret = this.mSQLiteDatabase.update(SALE_STATS_TABLE, initialValues, whereClause, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private ContentValues saleStatsInfo2ContentValues(StatsSaleInfo info) {
        ContentValues initialValues = new ContentValues();
        long stayTime = info.getStayTime();
        initialValues.put(SALE_STATS_IMSI, info.getIMSI());
        initialValues.put(SALE_STATS_ACTIVE_TIME, info.getActiveTime());
        initialValues.put(SALE_STATS_STAY_TIME, Long.valueOf(stayTime));
        initialValues.put(SALE_STATS_ACTIVE_STATE, Integer.valueOf(info.getActiveState()));
        return initialValues;
    }

    private StatsSaleInfo cursor2SaleStatsInfo(Cursor c) {
        StatsSaleInfo info = new StatsSaleInfo();
        int stayTime = c.getInt(c.getColumnIndex(SALE_STATS_STAY_TIME));
        int activeState = c.getInt(c.getColumnIndex(SALE_STATS_ACTIVE_STATE));
        info.setIMSI(c.getString(c.getColumnIndex(SALE_STATS_IMSI)));
        info.setActiveTime(c.getString(c.getColumnIndex(SALE_STATS_ACTIVE_TIME)));
        info.setStayTime((long) stayTime);
        info.setActiveState(activeState);
        return info;
    }

    public void insertSaleStatsInfo(StatsSaleInfo info) {
        insertInSaleStatsTable(saleStatsInfo2ContentValues(info));
    }

    public boolean deleteInfoByIMSI(StatsSaleInfo info) {
        return deleteInSaleStatsTable(new StringBuilder("sale_stats_imsi='").append(info.getIMSI()).append("'").toString()) > 0;
    }

    public StatsSaleInfo queryInfoByIMSI(StatsSaleInfo info) {
        Cursor c = null;
        StatsSaleInfo ret = null;
        try {
            c = queryInSaleStatsTable("sale_stats_imsi='" + info.getIMSI() + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            ret = cursor2SaleStatsInfo(c);
        }
        if (c != null) {
            c.close();
        }
        return ret;
    }

    public Cursor fetchAllData() {
        Cursor ret = null;
        try {
            ret = queryInSaleStatsTable(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public ArrayList<StatsSaleInfo> querySaleStatsInfoList() {
        ArrayList<StatsSaleInfo> ret = new ArrayList();
        Cursor c = fetchAllData();
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                StatsSaleInfo info = cursor2SaleStatsInfo(c);
                if (info != null) {
                    ret.add(info);
                }
                c.moveToNext();
            }
        }
        if (c != null) {
            c.close();
        }
        return ret;
    }

    public boolean updateSaleStatsInfo(StatsSaleInfo info) {
        return updateInSaleStatsTable(saleStatsInfo2ContentValues(info), new StringBuilder("sale_stats_imsi='").append(info.getIMSI()).append("'").toString()) > 0;
    }

    public void saveSaleStatsToDB(StatsSaleInfo info) {
        ArrayList<StatsSaleInfo> infoList = getInstance(this.mContext).querySaleStatsInfoList();
        if (infoList.size() == 0) {
            getInstance(this.mContext).insertSaleStatsInfo(info);
            return;
        }
        info.setIMSI(((StatsSaleInfo) infoList.get(0)).getIMSI());
        getInstance(this.mContext).updateSaleStatsInfo(info);
    }
}

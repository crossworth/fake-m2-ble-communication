package com.zhuoyou.plugin.running.database;

import com.baidu.mapapi.model.LatLng;
import com.droi.greendao.bean.GpsPointBean;
import com.droi.greendao.dao.DaoSession;
import com.droi.greendao.dao.GpsPointBeanDao;
import com.droi.greendao.dao.GpsPointBeanDao.Properties;
import com.droi.sdk.core.DroiUser;
import com.zhuoyou.plugin.running.app.TheApp;
import de.greenrobot.dao.query.WhereCondition;
import java.util.List;

public class GpsPointHelper {
    private static GpsPointHelper mInstance;
    private DaoSession mDaoSession;
    private GpsPointBeanDao mGpsPointBeanDao;

    private GpsPointHelper() {
    }

    private static GpsPointHelper getInstance() {
        if (mInstance == null) {
            synchronized (GpsPointHelper.class) {
                if (mInstance == null) {
                    mInstance = new GpsPointHelper();
                    mInstance.mDaoSession = TheApp.getDaoSession();
                    mInstance.mGpsPointBeanDao = mInstance.mDaoSession.getGpsPointBeanDao();
                }
            }
        }
        return mInstance;
    }

    public static DaoSession getDaoSession() {
        return getInstance().mDaoSession;
    }

    public static GpsPointBeanDao getBeanDao() {
        return getInstance().mGpsPointBeanDao;
    }

    public static LatLng[] getBoundPoints(String sportId) {
        return getBoundPoints(getBeanDao().queryBuilder().where(Properties.SportId.eq(sportId), new WhereCondition[0]).list());
    }

    public static LatLng[] getBoundPoints(List<GpsPointBean> list) {
        if (list == null || list.size() <= 0) {
            return null;
        }
        LatLng[] value = new LatLng[4];
        GpsPointBean temp0 = (GpsPointBean) list.get(0);
        GpsPointBean temp1 = (GpsPointBean) list.get(0);
        GpsPointBean temp2 = (GpsPointBean) list.get(0);
        GpsPointBean temp3 = (GpsPointBean) list.get(0);
        for (GpsPointBean item : list) {
            if (item.getLatitude() < temp0.getLatitude()) {
                temp0 = item;
            }
            if (item.getLatitude() > temp1.getLatitude()) {
                temp1 = item;
            }
            if (item.getLongitude() < temp2.getLongitude()) {
                temp2 = item;
            }
            if (item.getLongitude() > temp3.getLongitude()) {
                temp3 = item;
            }
        }
        value[0] = new LatLng(temp0.getLatitude(), temp0.getLongitude());
        value[1] = new LatLng(temp1.getLatitude(), temp1.getLongitude());
        value[2] = new LatLng(temp2.getLatitude(), temp2.getLongitude());
        value[3] = new LatLng(temp3.getLatitude(), temp3.getLongitude());
        return value;
    }

    public static void deleteBySportId(String sportId) {
        getBeanDao().queryBuilder().where(Properties.SportId.eq(sportId), new WhereCondition[0]).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    public static List<GpsPointBean> loadAscBySportId(String sportId) {
        return getBeanDao().queryBuilder().where(Properties.SportId.eq(sportId), new WhereCondition[0]).orderAsc(Properties.Time).list();
    }

    public static List<GpsPointBean> getUploadList(String sportid) {
        GpsPointBeanDao dataDao = getBeanDao();
        WhereCondition condition1 = Properties.Sync.eq(Integer.valueOf(0));
        WhereCondition condition2 = Properties.Account.eq(DroiUser.getCurrentUser().getUserId());
        WhereCondition condition3 = Properties.SportId.eq(sportid);
        return dataDao.queryBuilder().where(condition1, condition2, condition3).list();
    }

    public static List<GpsPointBean> getUploadList() {
        GpsPointBeanDao dataDao = getBeanDao();
        WhereCondition condition1 = Properties.Sync.eq(Integer.valueOf(0));
        WhereCondition condition2 = Properties.Account.eq(DroiUser.getCurrentUser().getUserId());
        return dataDao.queryBuilder().where(condition1, condition2).list();
    }

    public static int getUpdateCount(String sportid) {
        GpsPointBeanDao dataDao = getBeanDao();
        WhereCondition condition1 = Properties.Sync.eq(Integer.valueOf(0));
        WhereCondition condition2 = Properties.Account.eq(DroiUser.getCurrentUser().getUserId());
        WhereCondition condition3 = Properties.SportId.eq(sportid);
        return (int) getBeanDao().queryBuilder().where(condition1, condition2, condition3).buildCount().count();
    }
}

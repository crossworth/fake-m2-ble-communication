package com.zhuoyou.plugin.running.database;

import com.droi.greendao.bean.GpsSportBean;
import com.droi.greendao.dao.DaoSession;
import com.droi.greendao.dao.GpsSportBeanDao;
import com.droi.greendao.dao.GpsSportBeanDao.Properties;
import com.zhuoyou.plugin.running.app.TheApp;
import de.greenrobot.dao.query.WhereCondition;
import java.util.List;

public class GpsSportHelper {
    private static GpsSportHelper mInstance;
    private DaoSession mDaoSession;
    private GpsSportBeanDao mGpsSportBeanDao;

    private GpsSportHelper() {
    }

    private static GpsSportHelper getInstance() {
        if (mInstance == null) {
            synchronized (GpsSportHelper.class) {
                if (mInstance == null) {
                    mInstance = new GpsSportHelper();
                    mInstance.mDaoSession = TheApp.getDaoSession();
                    mInstance.mGpsSportBeanDao = mInstance.mDaoSession.getGpsSportBeanDao();
                }
            }
        }
        return mInstance;
    }

    public static DaoSession getDaoSession() {
        return getInstance().mDaoSession;
    }

    public static GpsSportBeanDao getBeanDao() {
        return getInstance().mGpsSportBeanDao;
    }

    public static void deleteVoidData() {
        getBeanDao().queryBuilder().where(Properties.Duration.eq(Integer.valueOf(0)), new WhereCondition[0]).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    public static List<GpsSportBean> loadAllDesc() {
        deleteVoidData();
        return getBeanDao().queryBuilder().orderDesc(Properties.Id).list();
    }

    public static int getUploadCount(String id) {
        return (((GpsSportBean) getBeanDao().load(id)).getSync() == 0 ? 1 : 0) + GpsPointHelper.getUpdateCount(id);
    }
}

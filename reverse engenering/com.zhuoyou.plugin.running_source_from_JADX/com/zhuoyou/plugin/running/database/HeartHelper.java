package com.zhuoyou.plugin.running.database;

import com.droi.greendao.bean.HeartBean;
import com.droi.greendao.dao.DaoSession;
import com.droi.greendao.dao.HeartBeanDao;
import com.droi.greendao.dao.HeartBeanDao.Properties;
import com.droi.sdk.core.DroiUser;
import com.zhuoyou.plugin.running.app.TheApp;
import com.zhuoyou.plugin.running.baas.BaasHelper;
import de.greenrobot.dao.query.WhereCondition;
import java.util.List;

public class HeartHelper {
    private static HeartHelper mInstance;
    private DaoSession mDaoSession;
    private HeartBeanDao mHeartBeanDao;

    private HeartHelper() {
    }

    private static HeartHelper getInstance() {
        if (mInstance == null) {
            synchronized (HeartHelper.class) {
                if (mInstance == null) {
                    mInstance = new HeartHelper();
                    mInstance.mDaoSession = TheApp.getDaoSession();
                    mInstance.mHeartBeanDao = mInstance.mDaoSession.getHeartBeanDao();
                }
            }
        }
        return mInstance;
    }

    public static DaoSession getDaoSession() {
        return getInstance().mDaoSession;
    }

    public static HeartBeanDao getBeanDao() {
        return getInstance().mHeartBeanDao;
    }

    public static List<HeartBean> getHeartList() {
        List<HeartBean> list = getBeanDao().queryBuilder().orderDesc(Properties.Date).limit(20).list();
        if (list.size() <= 0) {
            BaasHelper.getHeartInBackground();
        }
        return list;
    }

    public static HeartBean getNewestHeart() {
        List<HeartBean> list = getBeanDao().queryBuilder().orderDesc(Properties.Date).limit(1).list();
        if (list.size() > 0) {
            return (HeartBean) list.get(0);
        }
        BaasHelper.getHeartInBackground();
        return null;
    }

    public static List<HeartBean> getUploadList() {
        HeartBeanDao dataDao = getBeanDao();
        WhereCondition condition1 = Properties.Sync.eq(Integer.valueOf(0));
        WhereCondition condition2 = Properties.Account.eq(DroiUser.getCurrentUser().getUserId());
        return dataDao.queryBuilder().where(condition1, condition2).orderAsc(Properties.Date).list();
    }
}

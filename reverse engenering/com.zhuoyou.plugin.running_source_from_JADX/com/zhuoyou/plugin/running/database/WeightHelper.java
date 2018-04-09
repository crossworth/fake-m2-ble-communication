package com.zhuoyou.plugin.running.database;

import com.droi.greendao.bean.WeightBean;
import com.droi.greendao.dao.DaoSession;
import com.droi.greendao.dao.WeightBeanDao;
import com.droi.greendao.dao.WeightBeanDao.Properties;
import com.droi.sdk.core.DroiUser;
import com.zhuoyou.plugin.running.app.TheApp;
import com.zhuoyou.plugin.running.baas.BaasHelper;
import com.zhuoyou.plugin.running.tools.Tools;
import de.greenrobot.dao.query.WhereCondition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WeightHelper {
    private static WeightHelper mInstance;
    private DaoSession mDaoSession;
    private WeightBeanDao mWeightBeanDao;

    static class C18781 implements Comparator<WeightBean> {
        C18781() {
        }

        public int compare(WeightBean lhs, WeightBean rhs) {
            return lhs.getDate().compareTo(rhs.getDate());
        }
    }

    private WeightHelper() {
    }

    private static WeightHelper getInstance() {
        if (mInstance == null) {
            synchronized (WeightHelper.class) {
                if (mInstance == null) {
                    mInstance = new WeightHelper();
                    mInstance.mDaoSession = TheApp.getDaoSession();
                    mInstance.mWeightBeanDao = mInstance.mDaoSession.getWeightBeanDao();
                }
            }
        }
        return mInstance;
    }

    public static DaoSession getDaoSession() {
        return getInstance().mDaoSession;
    }

    public static WeightBeanDao getBeanDao() {
        return getInstance().mWeightBeanDao;
    }

    public static WeightBean getNewestWeight() {
        List<WeightBean> list = getBeanDao().queryBuilder().orderDesc(Properties.Date).limit(1).list();
        if (list.size() > 0) {
            return (WeightBean) list.get(0);
        }
        BaasHelper.getWeightInBackground();
        return new WeightBean(Tools.getToday(), DroiUser.getCurrentUser().getUserId(), 65.0f, 0);
    }

    public static List<WeightBean> getLastWeekWeight() {
        List<WeightBean> list = getBeanDao().queryBuilder().orderDesc(Properties.Date).limit(7).list();
        if (list.size() <= 0) {
            BaasHelper.getWeightInBackground();
            WeightBean bean = new WeightBean(Tools.getToday(), DroiUser.getCurrentUser().getUserId(), 65.0f, 0);
            list = new ArrayList();
            list.add(bean);
            return list;
        }
        Collections.sort(list, new C18781());
        return list;
    }

    public static WeightBean getBeforeWeight(WeightBean weight) {
        List<WeightBean> list = getBeanDao().queryBuilder().where(Properties.Date.lt(weight.getDate()), new WhereCondition[0]).orderDesc(Properties.Date).limit(1).list();
        return list.size() > 0 ? (WeightBean) list.get(0) : weight;
    }

    public static List<WeightBean> getUploadList() {
        WeightBeanDao dataDao = getBeanDao();
        WhereCondition condition1 = Properties.Sync.eq(Integer.valueOf(0));
        WhereCondition condition2 = Properties.Account.eq(DroiUser.getCurrentUser().getUserId());
        return dataDao.queryBuilder().where(condition1, condition2).orderAsc(Properties.Date).list();
    }
}

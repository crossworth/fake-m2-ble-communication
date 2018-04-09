package com.droi.greendao.dao;

import android.database.sqlite.SQLiteDatabase;
import com.droi.greendao.bean.GpsPointBean;
import com.droi.greendao.bean.GpsSportBean;
import com.droi.greendao.bean.HeartBean;
import com.droi.greendao.bean.SleepBean;
import com.droi.greendao.bean.SportBean;
import com.droi.greendao.bean.WeightBean;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.Map;

public class DaoSession extends AbstractDaoSession {
    private final GpsPointBeanDao gpsPointBeanDao = new GpsPointBeanDao(this.gpsPointBeanDaoConfig, this);
    private final DaoConfig gpsPointBeanDaoConfig;
    private final GpsSportBeanDao gpsSportBeanDao = new GpsSportBeanDao(this.gpsSportBeanDaoConfig, this);
    private final DaoConfig gpsSportBeanDaoConfig;
    private final HeartBeanDao heartBeanDao = new HeartBeanDao(this.heartBeanDaoConfig, this);
    private final DaoConfig heartBeanDaoConfig;
    private final SleepBeanDao sleepBeanDao = new SleepBeanDao(this.sleepBeanDaoConfig, this);
    private final DaoConfig sleepBeanDaoConfig;
    private final SportBeanDao sportBeanDao = new SportBeanDao(this.sportBeanDaoConfig, this);
    private final DaoConfig sportBeanDaoConfig;
    private final WeightBeanDao weightBeanDao = new WeightBeanDao(this.weightBeanDaoConfig, this);
    private final DaoConfig weightBeanDaoConfig;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> daoConfigMap) {
        super(db);
        this.sleepBeanDaoConfig = ((DaoConfig) daoConfigMap.get(SleepBeanDao.class)).clone();
        this.sleepBeanDaoConfig.initIdentityScope(type);
        this.sportBeanDaoConfig = ((DaoConfig) daoConfigMap.get(SportBeanDao.class)).clone();
        this.sportBeanDaoConfig.initIdentityScope(type);
        this.gpsPointBeanDaoConfig = ((DaoConfig) daoConfigMap.get(GpsPointBeanDao.class)).clone();
        this.gpsPointBeanDaoConfig.initIdentityScope(type);
        this.gpsSportBeanDaoConfig = ((DaoConfig) daoConfigMap.get(GpsSportBeanDao.class)).clone();
        this.gpsSportBeanDaoConfig.initIdentityScope(type);
        this.weightBeanDaoConfig = ((DaoConfig) daoConfigMap.get(WeightBeanDao.class)).clone();
        this.weightBeanDaoConfig.initIdentityScope(type);
        this.heartBeanDaoConfig = ((DaoConfig) daoConfigMap.get(HeartBeanDao.class)).clone();
        this.heartBeanDaoConfig.initIdentityScope(type);
        registerDao(SleepBean.class, this.sleepBeanDao);
        registerDao(SportBean.class, this.sportBeanDao);
        registerDao(GpsPointBean.class, this.gpsPointBeanDao);
        registerDao(GpsSportBean.class, this.gpsSportBeanDao);
        registerDao(WeightBean.class, this.weightBeanDao);
        registerDao(HeartBean.class, this.heartBeanDao);
    }

    public void clear() {
        this.sleepBeanDaoConfig.getIdentityScope().clear();
        this.sportBeanDaoConfig.getIdentityScope().clear();
        this.gpsPointBeanDaoConfig.getIdentityScope().clear();
        this.gpsSportBeanDaoConfig.getIdentityScope().clear();
        this.weightBeanDaoConfig.getIdentityScope().clear();
        this.heartBeanDaoConfig.getIdentityScope().clear();
    }

    public SleepBeanDao getSleepBeanDao() {
        return this.sleepBeanDao;
    }

    public SportBeanDao getSportBeanDao() {
        return this.sportBeanDao;
    }

    public GpsPointBeanDao getGpsPointBeanDao() {
        return this.gpsPointBeanDao;
    }

    public GpsSportBeanDao getGpsSportBeanDao() {
        return this.gpsSportBeanDao;
    }

    public WeightBeanDao getWeightBeanDao() {
        return this.weightBeanDao;
    }

    public HeartBeanDao getHeartBeanDao() {
        return this.heartBeanDao;
    }
}

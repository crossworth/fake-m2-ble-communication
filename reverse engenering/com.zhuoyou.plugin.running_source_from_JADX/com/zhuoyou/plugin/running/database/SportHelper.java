package com.zhuoyou.plugin.running.database;

import android.database.Cursor;
import android.text.TextUtils;
import com.droi.btlib.service.SubStep;
import com.droi.greendao.bean.SportBean;
import com.droi.greendao.dao.DaoSession;
import com.droi.greendao.dao.SportBeanDao;
import com.droi.greendao.dao.SportBeanDao.Properties;
import com.droi.sdk.core.DroiUser;
import com.tencent.connect.common.Constants;
import com.zhuoyou.plugin.running.app.TheApp;
import com.zhuoyou.plugin.running.baas.User;
import com.zhuoyou.plugin.running.bean.EventStep;
import com.zhuoyou.plugin.running.tools.GpsUtils;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import de.greenrobot.dao.query.WhereCondition;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SportHelper {
    private static final float ONE_STEP_TIME = 1.4285f;
    private static SportHelper mInstance;
    private DaoSession mDaoSession;
    private SportBeanDao mSportBeanDao;

    private SportHelper() {
    }

    private static SportHelper getInstance() {
        if (mInstance == null) {
            synchronized (SportHelper.class) {
                if (mInstance == null) {
                    mInstance = new SportHelper();
                    mInstance.mDaoSession = TheApp.getDaoSession();
                    mInstance.mSportBeanDao = mInstance.mDaoSession.getSportBeanDao();
                }
            }
        }
        return mInstance;
    }

    public static DaoSession getDaoSession() {
        return getInstance().mDaoSession;
    }

    public static SportBeanDao getBeanDao() {
        return getInstance().mSportBeanDao;
    }

    public static SportBean getBeanByDate(String date) {
        SportBean bean = (SportBean) getBeanDao().load(date);
        if (bean != null) {
            return bean;
        }
        bean = new SportBean(date);
        bean.setSportData("");
        bean.setAccount(DroiUser.getCurrentUser().getUserId());
        bean.setStepTarget(SPUtils.getTargetStep());
        return bean;
    }

    public static void saveStepData(String date, int step, int btType) {
        if (Tools.judgeDate(date)) {
            SportBean bean = getBeanByDate(date);
            bean.setBTtype(btType);
            updateStep(bean, step, false);
        }
    }

    public static void updateTodayStep(int step) {
        updateStep(getBeanByDate(Tools.getToday()), step, false);
    }

    public static void updateTodayStepByPed(int step) {
        updateStep(getBeanByDate(Tools.getToday()), step, true);
        EventBus.getDefault().post(new EventStep());
    }

    private static void updateStep(SportBean bean, int step, boolean ifAdd) {
        if (step > 0 && step <= 99999) {
            int i;
            SportBean old = bean.getInstance();
            if (bean.getStepTarget() == 0 || bean.getDate().equals(Tools.getToday())) {
                bean.setStepTarget(SPUtils.getTargetStep());
            }
            if (ifAdd) {
                bean.setStepPhone(bean.getStepPhone() + step);
            } else {
                if (step <= bean.getStepPhone() && bean.getStepPhone() < 99999) {
                    step = bean.getStepPhone();
                }
                bean.setStepPhone(step);
            }
            if (bean.getStepPhone() > 99999) {
                bean.setStepPhone(99999);
            } else if (bean.getStepPhone() < 0) {
                bean.setStepPhone(0);
            }
            if (bean.getStepPhone() >= bean.getStepTarget()) {
                i = 1;
            } else {
                i = 0;
            }
            bean.setComplete(i);
            bean.setDistance(GpsUtils.getDistance(bean.getStepPhone(), ((User) DroiUser.getCurrentUser(User.class)).getHeight()));
            bean.setCal(GpsUtils.getCalories(bean.getDistance(), WeightHelper.getNewestWeight().getWeight()));
            if (!old.equals(bean)) {
                bean.setSync(0);
                getBeanDao().insertOrReplace(bean);
            }
        }
    }

    public static void saveSubData(String sub, int BTtype) {
        if (!TextUtils.isEmpty(sub)) {
            SportBean bean = getBeanByDate(Tools.getToday());
            if (!bean.getSportData().equals(sub)) {
                bean.setSportData(sub);
                bean.setBTtype(BTtype);
                bean.setSync(0);
                getBeanDao().insertOrReplace(bean);
            }
        }
    }

    public static String subStepToString(SubStep sub) {
        return sub.getStartTime() + "#" + sub.getEndTime() + "#" + sub.getStep();
    }

    public static ArrayList<SubStep> formatSubStep(String str) {
        ArrayList<SubStep> list = new ArrayList();
        if (!TextUtils.isEmpty(str)) {
            for (String item : str.split("&")) {
                String[] subs = item.split("#");
                SubStep sub = new SubStep();
                sub.setStartTime(subs[0]);
                sub.setEndTime(subs[1]);
                sub.setStep(Integer.parseInt(subs[2]));
                list.add(sub);
            }
        }
        return list;
    }

    public static int getAverageStep() {
        Cursor c = getBeanDao().getDatabase().rawQuery("select avg(" + Properties.StepPhone.columnName + ") from " + SportBeanDao.TABLENAME, null);
        int value = 0;
        if (c.moveToFirst()) {
            value = c.getInt(0);
        }
        c.close();
        return value;
    }

    public static float getTotalDis() {
        Cursor c = getBeanDao().getDatabase().rawQuery("select sum(" + Properties.Distance.columnName + ") from " + SportBeanDao.TABLENAME, null);
        float sum = 0.0f;
        if (c.moveToFirst()) {
            sum = c.getFloat(0);
        }
        c.close();
        return sum;
    }

    public static int getComplete() {
        return (int) getBeanDao().queryBuilder().where(Properties.Complete.eq(Integer.valueOf(1)), new WhereCondition[0]).buildCount().count();
    }

    public static int getCompleteSer() {
        String today = Tools.getToday();
        List<SportBean> list = getBeanDao().queryBuilder().where(Properties.Complete.eq(Integer.valueOf(1)), Properties.Date.lt(today)).orderDesc(Properties.Date).list();
        int count = 0;
        int i = 0;
        while (i < list.size() && Tools.getInterval(today, ((SportBean) list.get(i)).getDate()) <= ((long) (i + 1)) * LogBuilder.MAX_INTERVAL) {
            count++;
            i++;
        }
        if (getTodaySport().getComplete() == 1) {
            return count + 1;
        }
        return count;
    }

    public static SportBean getTodaySport() {
        return getBeanByDate(Tools.getToday());
    }

    public static List<SportBean> getUploadList() {
        SportBeanDao dataDao = getBeanDao();
        WhereCondition condition1 = Properties.Sync.eq(Integer.valueOf(0));
        WhereCondition condition2 = Properties.Account.eq(DroiUser.getCurrentUser().getUserId());
        return dataDao.queryBuilder().where(condition1, condition2).orderAsc(Properties.Date).list();
    }

    public static String getQQHealthData() {
        JSONObject JSON = new JSONObject();
        String userid = DroiUser.getCurrentUser().getUserId();
        try {
            JSON.put("access_token", SPUtils.getQQAccessToken(userid));
            JSON.put("oauth_consumer_key", TheApp.QQ_APPID);
            JSON.put("openid", SPUtils.getQQOpenId(userid));
            JSON.put(Constants.PARAM_PLATFORM_ID, Constants.SOURCE_QZONE);
            JSON.put("type", 1);
            JSONArray array = new JSONArray();
            JSONObject data = new JSONObject();
            SportBean bean = getTodaySport();
            data.put(LogColumns.TIME, (int) (System.currentTimeMillis() / 1000));
            data.put("distance", (int) bean.getDistance());
            data.put("steps", bean.getStepPhone());
            data.put("duration", (int) (((float) bean.getStepPhone()) * ONE_STEP_TIME));
            data.put("calories", (int) bean.getCal());
            data.put("achieve", bean.getStepPhone() / bean.getStepTarget());
            data.put("target", bean.getStepTarget());
            data.put("type", 1);
            JSON.put("data", array.put(data));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return JSON.toString();
    }
}

package com.zhuoyou.plugin.running.database;

import android.text.TextUtils;
import com.droi.btlib.service.SleepInfo;
import com.droi.greendao.bean.SleepBean;
import com.droi.greendao.dao.DaoSession;
import com.droi.greendao.dao.SleepBeanDao;
import com.droi.greendao.dao.SleepBeanDao.Properties;
import com.droi.sdk.core.DroiUser;
import com.lemon.cx.histogra.MiHistogramSleepData;
import com.lemon.cx.micolumnar.MiColumnarSleepData;
import com.zhuoyou.plugin.running.app.TheApp;
import com.zhuoyou.plugin.running.tools.Tools;
import de.greenrobot.dao.query.WhereCondition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SleepHelper {
    public static final int DEEP_COUNT = 34;
    public static final int ITEM_TIME = 1800000;
    public static final int SLEEP_START = 21;
    private static SleepHelper mInstance;
    private DaoSession mDaoSession;
    private SleepBeanDao mSleepBeanDao;

    private SleepHelper() {
    }

    private static SleepHelper getInstance() {
        if (mInstance == null) {
            synchronized (SleepHelper.class) {
                if (mInstance == null) {
                    mInstance = new SleepHelper();
                    mInstance.mDaoSession = TheApp.getDaoSession();
                    mInstance.mSleepBeanDao = mInstance.mDaoSession.getSleepBeanDao();
                }
            }
        }
        return mInstance;
    }

    public static DaoSession getDaoSession() {
        return getInstance().mDaoSession;
    }

    public static SleepBeanDao getBeanDao() {
        return getInstance().mSleepBeanDao;
    }

    public static SleepBean getBeanBySTime(String sTime) {
        SleepBean bean = (SleepBean) getBeanDao().load(sTime);
        if (bean != null) {
            return bean;
        }
        bean = new SleepBean(sTime);
        bean.setSleepData("");
        bean.setAccount(DroiUser.getCurrentUser().getUserId());
        return bean;
    }

    public static void saveData(SleepInfo info, int BTtype) {
        if (Tools.judgeDate(info.getStartTime() + "")) {
            SleepBean bean = getBeanBySTime(info.getStartTime() + "");
            SleepBean old = bean.getInstance();
            bean.setEndTime(info.getEndTime() + "");
            bean.setSleepData(info.getSleepDetail());
            bean.setBTtype(BTtype);
            if (!old.equals(bean)) {
                bean.setSync(0);
                getBeanDao().insertOrReplace(bean);
            }
        }
    }

    public static List<SleepBean> getSleepList(Calendar data) {
        Calendar sCal = (Calendar) data.clone();
        Calendar eCal = (Calendar) sCal.clone();
        sCal.add(6, -1);
        sCal.set(11, 0);
        sCal.set(12, 0);
        sCal.set(13, 0);
        eCal.add(6, 1);
        eCal.set(11, 0);
        eCal.set(12, 0);
        eCal.set(13, 0);
        WhereCondition condition1 = Properties.StartTime.ge(Tools.formatDefTime(sCal));
        WhereCondition condition2 = Properties.StartTime.lt(Tools.formatDefTime(eCal));
        List<SleepBean> list = getBeanDao().queryBuilder().where(condition1, condition2).orderAsc(Properties.StartTime).list();
        List<SleepBean> value = new ArrayList();
        for (SleepBean bean : list) {
            Calendar start = Tools.parseDefDate(bean.getStartTime());
            Calendar end = Tools.parseDefDate(bean.getEndTime());
            if (start.get(6) == data.get(6)) {
                if (start.get(11) < 21 && end.get(6) == data.get(6)) {
                    value.add(bean);
                }
            } else if (start.get(11) >= 21 || end.get(6) == data.get(6)) {
                value.add(bean);
            }
        }
        return value;
    }

    public static List<SleepBean> getTodaySleepList() {
        return getSleepList(Calendar.getInstance());
    }

    public static ArrayList<MiHistogramSleepData> getTodaySleepData() {
        return getSleepData(getTodaySleepList());
    }

    public static ArrayList<MiHistogramSleepData> getSleepData(List<SleepBean> src) {
        ArrayList<MiHistogramSleepData> list = new ArrayList();
        if (src.size() > 0) {
            Calendar basetime = Tools.parseDefDate(((SleepBean) src.get(0)).getStartTime());
            for (SleepBean bean : src) {
                if (bean.getBTtype() == 1) {
                    list.addAll(getSleepSubInfoClassic(bean, basetime));
                } else {
                    list.addAll(getSleepSubInfo(bean, basetime));
                }
            }
        }
        return list;
    }

    public static MiColumnarSleepData getSleepData(Calendar cal) {
        List src = getSleepList(cal);
        if (src.size() <= 0) {
            return null;
        }
        int[] time = getDeepTime(getSleepData(src));
        return new MiColumnarSleepData(cal, time[0] + time[1], time[0]);
    }

    public static int[] getDeepTime(List<MiHistogramSleepData> list) {
        int[] value = new int[2];
        for (MiHistogramSleepData item : list) {
            if (item.isDeep()) {
                value[0] = value[0] + (item.getEndTime() - item.getStartTime());
            } else {
                value[1] = value[1] + (item.getEndTime() - item.getStartTime());
            }
        }
        return value;
    }

    public static ArrayList<MiHistogramSleepData> getSleepSubInfo(SleepBean bean, Calendar basetime) {
        ArrayList<MiHistogramSleepData> list = new ArrayList();
        if (!TextUtils.isEmpty(bean.getSleepData())) {
            long stime = Tools.parseDefDate(bean.getStartTime()).getTimeInMillis();
            Calendar eCal = Tools.parseDefDate(bean.getEndTime());
            int count = (int) ((eCal.getTimeInMillis() - stime) / 1800000);
            int rest = (int) ((eCal.getTimeInMillis() - stime) % 1800000);
            String[] data = bean.getSleepData().split("\\|");
            int max = Math.min(count, data.length);
            long base = stime - basetime.getTimeInMillis();
            int i = 0;
            while (i <= max) {
                MiHistogramSleepData value = new MiHistogramSleepData();
                value.setStartTime(((int) (((long) (ITEM_TIME * i)) + base)) / 60000);
                value.setEndTime(((i == max ? rest : ITEM_TIME) / 60000) + value.getStartTime());
                boolean z = i != max && Integer.parseInt(data[i]) <= 34;
                value.setDeep(z);
                addDataToList(list, value);
                i++;
            }
        }
        return list;
    }

    public static ArrayList<MiHistogramSleepData> getSleepSubInfoClassic(SleepBean bean, Calendar basetime) {
        ArrayList<MiHistogramSleepData> list = new ArrayList();
        String[] data = bean.getSleepData().split("\\|");
        for (int i = 0; i < data.length - 3; i += 2) {
            MiHistogramSleepData value = new MiHistogramSleepData();
            value.setDeep(Integer.parseInt(data[i]) > 1);
            value.setStartTime(getItemTime(basetime, data[i + 1]));
            value.setEndTime(getItemTime(basetime, data[i + 3]));
            addDataToList(list, value);
        }
        return list;
    }

    private static int getItemTime(Calendar basetime, String str) {
        int hour = basetime.get(11);
        int minute = basetime.get(12);
        hour = Integer.parseInt(str.substring(0, 2)) - hour;
        if (hour < 0) {
            hour += 24;
        }
        return (hour * 60) + (Integer.parseInt(str.substring(2, 4)) - minute);
    }

    private static void addDataToList(List<MiHistogramSleepData> list, MiHistogramSleepData value) {
        if (list.size() > 0) {
            MiHistogramSleepData last = (MiHistogramSleepData) list.get(list.size() - 1);
            if (value.isDeep() == last.isDeep()) {
                last.setEndTime(value.getEndTime());
                return;
            } else {
                list.add(value);
                return;
            }
        }
        list.add(value);
    }

    public static List<SleepBean> getUploadList() {
        SleepBeanDao dataDao = getBeanDao();
        WhereCondition condition1 = Properties.Sync.eq(Integer.valueOf(0));
        WhereCondition condition2 = Properties.Account.eq(DroiUser.getCurrentUser().getUserId());
        return dataDao.queryBuilder().where(condition1, condition2).orderAsc(Properties.StartTime).list();
    }
}

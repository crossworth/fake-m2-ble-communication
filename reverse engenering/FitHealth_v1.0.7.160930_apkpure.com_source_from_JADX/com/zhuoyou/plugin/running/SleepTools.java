package com.zhuoyou.plugin.running;

import android.annotation.SuppressLint;
import android.util.Log;
import com.amap.api.services.core.AMapException;
import com.zhuoyou.plugin.download.Util_update.TimeManager;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SleepTools {
    public static int getDurationTime(long startTime, long endTime) {
        Calendar startCal = getCalendar(startTime);
        Calendar endCal = getCalendar(endTime);
        long duration = endCal.getTimeInMillis() - startCal.getTimeInMillis();
        Log.i("hepenghui", "duration=" + endCal.getTimeInMillis());
        Log.i("hepenghui", "duration=" + startCal.getTimeInMillis());
        return (int) (duration / 1000);
    }

    public static Calendar getCalendar(long time) {
        Calendar mCal = Calendar.getInstance();
        int year = (int) (time / 10000000000L);
        if (year >= 2015 && year <= 2029) {
            mCal.set(year, ((int) ((time % 10000000000L) / 100000000)) - 1, (int) ((time % 100000000) / 1000000), (int) ((time % 1000000) / 10000), (int) ((time % 10000) / 100), (int) (time % 100));
        }
        return mCal;
    }

    public static List<Integer> getData(String turnData, int index) throws NumberFormatException {
        int maxData;
        List<Integer> turnList = new ArrayList();
        String[] turnArray = turnData.split("\\|");
        if (turnArray.length < index) {
            maxData = turnArray.length;
        } else {
            maxData = index;
        }
        for (int i = 0; i < maxData; i++) {
            turnList.add(Integer.valueOf(turnArray[i]));
        }
        Log.i("hph1", "turnList=" + turnList);
        return turnList;
    }

    public static int getDeepSleep(List<Integer> turnList) {
        int deepSleep = 0;
        int i = 0;
        while (i < turnList.size()) {
            if (((Integer) turnList.get(i)).intValue() <= 34 && ((Integer) turnList.get(i)).intValue() >= 0) {
                deepSleep += AMapException.CODE_AMAP_CLIENT_ERRORCODE_MISSSING;
            }
            i++;
        }
        Log.i("hph1", "deepSleep=" + deepSleep);
        return deepSleep;
    }

    @SuppressLint({"SimpleDateFormat"})
    private static String getDate(int prev_index) {
        Calendar c = Calendar.getInstance();
        c.set(c.get(1), c.get(2), c.get(5) - prev_index);
        return new SimpleDateFormat("yyyyMMdd").format(c.getTime());
    }

    public static int getIntervalTime(String start, String end) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
        try {
            long diff = df.parse(end).getTime() - df.parse(start).getTime();
            long days = diff / 86400000;
            long hours = (diff - (86400000 * days)) / 3600000;
            return ((int) ((60 * hours) + (((diff - (86400000 * days)) - (3600000 * hours)) / TimeManager.UNIT_MINUTE))) * 60;
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getDeepSleep2(String[] details) {
        int length = details.length;
        int deepSleep = 0;
        int i = 0;
        while (i < details.length / 2) {
            if (details[i * 2].equals("2") && (i * 2) + 3 < length) {
                String start = getDate(0) + details[(i * 2) + 1];
                String end = "";
                if (Integer.parseInt(details[(i * 2) + 3]) >= AMapException.CODE_AMAP_SERVICE_INVALID_PARAMS) {
                    end = getDate(0) + details[(i * 2) + 3];
                } else {
                    end = getDate(-1) + details[(i * 2) + 3];
                }
                deepSleep += getIntervalTime(start, end);
            }
            i++;
        }
        return deepSleep;
    }

    public static int getlightSleep2(String[] details) {
        int length = details.length;
        int lightSleep = 0;
        int i = 0;
        while (i < details.length / 2) {
            if (details[i * 2].equals("1") && (i * 2) + 3 < length) {
                String start = getDate(0) + details[(i * 2) + 1];
                String end = "";
                if (Integer.parseInt(details[(i * 2) + 3]) >= AMapException.CODE_AMAP_SERVICE_INVALID_PARAMS) {
                    end = getDate(0) + details[(i * 2) + 3];
                } else {
                    end = getDate(-1) + details[(i * 2) + 3];
                }
                lightSleep += getIntervalTime(start, end);
            }
            i++;
        }
        return lightSleep;
    }

    public static ArrayList<SleepBean> getSleepBean(Calendar startTime, Calendar endTime, List<Integer> turnList) {
        Calendar startCal = (Calendar) startTime.clone();
        ArrayList<SleepBean> beansArray = new ArrayList();
        if (!(turnList == null || turnList.size() == 0)) {
            boolean isDeepSleep = false;
            DecimalFormat decFormat = new DecimalFormat("#00");
            SleepBean bean = new SleepBean();
            bean.setDeep(false);
            bean.setStartTime(decFormat.format((long) startCal.get(11)) + ":" + decFormat.format((long) startCal.get(12)));
            for (int i = 0; i < turnList.size(); i++) {
                boolean temp;
                if (((Integer) turnList.get(i)).intValue() > 22) {
                    temp = false;
                } else {
                    temp = true;
                }
                startCal.add(12, 30);
                if (isDeepSleep != temp) {
                    isDeepSleep = temp;
                    if (bean != null) {
                        bean.setEndTime(decFormat.format((long) startCal.get(11)) + ":" + decFormat.format((long) startCal.get(12)));
                        beansArray.add(bean);
                    }
                    bean = new SleepBean();
                    bean.setDeep(isDeepSleep);
                    bean.setStartTime(decFormat.format((long) startCal.get(11)) + ":" + decFormat.format((long) startCal.get(12)));
                }
                if (i == turnList.size() - 1) {
                    isDeepSleep = temp;
                    if (bean != null) {
                        bean.setEndTime(decFormat.format((long) endTime.get(11)) + ":" + decFormat.format((long) endTime.get(12)));
                        beansArray.add(bean);
                    }
                }
            }
            Log.i("hph1", "beansArray=" + beansArray);
        }
        return beansArray;
    }

    public static ArrayList<SleepBean> getSleepBean2(String[] details) {
        ArrayList<SleepBean> beansArray = new ArrayList();
        if (details.length != 0) {
            for (int i = 0; i < (details.length / 2) - 1; i++) {
                SleepBean bean = new SleepBean();
                if (details[i * 2].equals("1")) {
                    bean.setDeep(false);
                    bean.setStartTime(formatRemoteTime(details[(i * 2) + 1]));
                    bean.setEndTime(formatRemoteTime(details[(i * 2) + 3]));
                    beansArray.add(bean);
                } else if (details[i * 2].equals("2")) {
                    bean.setDeep(true);
                    bean.setStartTime(formatRemoteTime(details[(i * 2) + 1]));
                    bean.setEndTime(formatRemoteTime(details[(i * 2) + 3]));
                    beansArray.add(bean);
                }
            }
        }
        return beansArray;
    }

    public static String formatRemoteTime(String old_time) {
        return (old_time.substring(0, 2) + ":") + old_time.substring(2, 4);
    }
}

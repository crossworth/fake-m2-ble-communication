package com.zhuoyou.plugin.running.tools;

import android.net.Uri;
import com.baidu.mapapi.map.HeatMap;
import com.droi.greendao.bean.GpsSportBean;
import com.tencent.open.yyb.TitleBar;
import java.io.File;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.List;
import org.andengine.util.time.TimeConstants;

public class GpsUtils {
    private static Format decFormat = new DecimalFormat("#0.00");
    private static Format intformat = new DecimalFormat("00");

    public static String getGpsLocusPath(String path) {
        File file = new File(path + "gpssport" + File.separator);
        if (file.exists()) {
            return file.getAbsolutePath() + File.separator;
        }
        if (file.mkdirs()) {
            return file.getAbsolutePath() + File.separator;
        }
        return null;
    }

    public static Uri getLocusUri(String sportId) {
        File file = new File(getGpsLocusPath(Tools.CACHE_PATH) + sportId);
        if (!file.exists()) {
            file = new File(getGpsLocusPath(Tools.FILE_PATH) + sportId);
        }
        return Uri.fromFile(file);
    }

    public static String getDuration(int time) {
        int hours = (time % TimeConstants.SECONDS_PER_DAY) / TimeConstants.SECONDS_PER_HOUR;
        String hour = intformat.format(Integer.valueOf(hours));
        String minute = intformat.format(Integer.valueOf((time % TimeConstants.SECONDS_PER_HOUR) / 60));
        String second = intformat.format(Integer.valueOf(time % 60));
        if (hours <= 0) {
            return minute + ":" + second;
        }
        return hour + ":" + minute + ":" + second;
    }

    public static String getSleepTime(int time) {
        return (time / 60) + ":" + intformat.format(Integer.valueOf(time % 60));
    }

    public static String getSpeed(float speed) {
        int value = 0;
        if (speed > 0.0f) {
            value = (int) (1000.0f / speed);
        }
        return (value / 60) + "'" + intformat.format(Integer.valueOf(value % 60)) + "''";
    }

    public static String getDisStr(float distance) {
        return getDisStr(distance, SPUtils.getUnitDis());
    }

    public static String getDisStrNoUnit(float distance) {
        return getDisStrNoUnit(distance, SPUtils.getUnitDis());
    }

    public static String getDisStr(float distance, int unit) {
        if (unit == 1) {
            return decFormat.format(Float.valueOf(((float) Math.round(distance / TitleBar.SHAREBTN_RIGHT_MARGIN)) / 100.0f)) + " km";
        }
        return decFormat.format(Float.valueOf(((float) Math.round((0.6213712f * distance) / TitleBar.SHAREBTN_RIGHT_MARGIN)) / 100.0f)) + " mi";
    }

    public static String getDisStrNoUnit(float distance, int unit) {
        if (unit == 1) {
            return decFormat.format(Float.valueOf(((float) Math.round(distance / TitleBar.SHAREBTN_RIGHT_MARGIN)) / 100.0f));
        }
        return decFormat.format(Float.valueOf(((float) Math.round((0.6213712f * distance) / TitleBar.SHAREBTN_RIGHT_MARGIN)) / 100.0f));
    }

    public static float getDistance(float distance) {
        return getDistance(distance, SPUtils.getUnitDis());
    }

    public static float getDistance(float distance, int unit) {
        if (unit == 1) {
            return distance / 1000.0f;
        }
        return (distance / 1000.0f) * 0.6213712f;
    }

    public static float getCalories(float meter, float weight) {
        return (float) (((double) ((weight * meter) / 1000.0f)) * 1.175d);
    }

    public static String getCalStr(float value) {
        return getCalStrNoUnit(value) + " Kcal";
    }

    public static String getCalStrNoUnit(float value) {
        return String.valueOf(Math.round(value));
    }

    public static float getDistance(int step, int height) {
        double meterperstep;
        if (height < 155) {
            meterperstep = 0.55d;
        } else if (height <= 165) {
            meterperstep = HeatMap.DEFAULT_OPACITY;
        } else if (height <= 175) {
            meterperstep = 0.65d;
        } else if (height <= 185) {
            meterperstep = 0.7d;
        } else if (height <= 195) {
            meterperstep = 0.75d;
        } else {
            meterperstep = 0.8d;
        }
        return (float) ((int) (((double) step) * meterperstep));
    }

    public static String[] getTatalData(List<GpsSportBean> list) {
        float dis = 0.0f;
        float cal = 0.0f;
        for (GpsSportBean item : list) {
            dis += item.getDistance();
            cal += item.getCal();
        }
        return new String[]{getDisStrNoUnit(dis), getCalStrNoUnit(cal)};
    }

    public static int getGpsStatus(int satellites) {
        if (satellites <= 0) {
            return 0;
        }
        if (satellites < 3) {
            return 1;
        }
        if (satellites < 6) {
            return 2;
        }
        return 3;
    }
}

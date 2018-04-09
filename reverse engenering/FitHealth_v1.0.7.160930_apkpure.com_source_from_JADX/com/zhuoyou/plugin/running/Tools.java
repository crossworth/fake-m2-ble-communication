package com.zhuoyou.plugin.running;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;
import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.WeightedLatLng;
import com.fithealth.running.R;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyou.plugin.database.DBOpenHelper;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.info.ImageAsynTask;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@SuppressLint({"SimpleDateFormat"})
public class Tools {
    public static final String AUTO_SYNC_TIME = "auto_sync_time";
    public static final String DEVICE_INFO = "device_info";
    public static final String M2_SWITCH_REMIND = "m2.switch.remind";
    public static final String MARS5_SWITCH_REMIND = "mars5_switch_remind";
    public static final String PED_STATE = "phone_pedometer_state";
    public static final String PHONE_PED = "phone_pedometer";
    public static final String PHONE_SEDENTARY = "phone_sedentary";
    public static final String SEDENTARY_STATE = "phone_sedentary_state";
    private static final String SP_CONFIG_FILENAME = "personal_config";
    private static final String SP_CONFIG_KEY_HEIGHT = "height";
    private static final String SP_CONFIG_KEY_SEX = "sex";
    private static final String SP_CONFIG_KEY_WIDTH = "width";
    private static final String SP_CONFIG_KEY_WIGHT = "wight";
    private static final String SP_CONFIG_KEY_YEAR = "year";
    private static final String SP_GOAL_FILENAME = "personal_goal";
    private static final String SP_GOAL_KEY_CAL = "cal";
    private static final String SP_GOAL_KEY_STEPS = "steps";
    public static final String SP_PM25_FILENAME = "weather";
    public static final String SP_SPP_FLAG_FILENAME = "spp_flag";
    public static final String SP_SPP_FLAG_KEY_FLAG = "flag";
    public static final String SP_SPP_FLAG_KEY_SHOWDIALOG = "show_dialog";
    public static final String SP_SPP_FLAG_KEY_SLEEP_VIEWINDEX = "sleep_page_index";
    public static final String SP_SPP_FLAG_KEY_SYNCNOW = "syncnow";
    public static final String SP_SPP_FLAG_KEY_VIEWINDEX = "page_index";
    public static final String USR_INFO = "usr_info";
    public static boolean dbStateChange = false;
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    public static int[] headIcon = new int[]{R.drawable.mengmeizi, R.drawable.mm, R.drawable.dama, R.drawable.xiaozhengtai, R.drawable.gg, R.drawable.dashu, R.drawable.head1_1, R.drawable.head1_2, R.drawable.head1_3, R.drawable.head1_4, R.drawable.head1_5, R.drawable.head1_6, R.drawable.head1_7, R.drawable.head1_8, R.drawable.head2_1, R.drawable.head2_2, R.drawable.head2_3, R.drawable.head2_4, R.drawable.head2_5, R.drawable.head2_6, R.drawable.head2_7, R.drawable.head2_8, R.drawable.head2_9, R.drawable.head2_10, R.drawable.head2_11, R.drawable.head3_1, R.drawable.head3_2, R.drawable.head3_3, R.drawable.head3_4, R.drawable.head3_5, R.drawable.head3_6, R.drawable.head3_7, R.drawable.head3_8, R.drawable.head3_9, R.drawable.head3_10, R.drawable.head3_11, R.drawable.head3_12, R.drawable.head3_13, R.drawable.head3_14, R.drawable.head3_15};
    public static int[] headIcon1 = new int[]{R.drawable.mengmeizi, R.drawable.mm, R.drawable.dama, R.drawable.xiaozhengtai, R.drawable.gg, R.drawable.dashu};
    public static int[] headIcon2 = new int[]{R.drawable.head1_1, R.drawable.head1_2, R.drawable.head1_3, R.drawable.head1_4, R.drawable.head1_5, R.drawable.head1_6, R.drawable.head1_7, R.drawable.head1_8};
    public static int[] headIcon3 = new int[]{R.drawable.head2_1, R.drawable.head2_2, R.drawable.head2_3, R.drawable.head2_4, R.drawable.head2_5, R.drawable.head2_6, R.drawable.head2_7, R.drawable.head2_8, R.drawable.head2_9, R.drawable.head2_10, R.drawable.head2_11};
    public static int[] headIcon4 = new int[]{R.drawable.head3_1, R.drawable.head3_2, R.drawable.head3_3, R.drawable.head3_4, R.drawable.head3_5, R.drawable.head3_6, R.drawable.head3_7, R.drawable.head3_8, R.drawable.head3_9, R.drawable.head3_10, R.drawable.head3_11, R.drawable.head3_12, R.drawable.head3_13, R.drawable.head3_14, R.drawable.head3_15};
    private static int li = 0;
    public static boolean loginStateChange = true;
    private static long[] ls = new long[3000];
    private static Context mCont = RunningApp.getInstance().getApplicationContext();
    private static DBOpenHelper mDBOpenHelper;
    public static int[] sportType = new int[]{R.drawable.chonglang, R.drawable.menqiu, R.drawable.feipan, R.drawable.tiaoshui, R.drawable.feibiao, R.drawable.huachuan, R.drawable.baolingqiu, R.drawable.binghu, R.drawable.yujia, R.drawable.pingpang, R.drawable.buxing, R.drawable.danbanhuaxue, R.drawable.banqiu, R.drawable.bangqiu, R.drawable.gaoerfu, R.drawable.tiaowu, R.drawable.yumaoqiu, R.drawable.jijian, R.drawable.huaxue, R.drawable.paiqiu, R.drawable.lanqiu, R.drawable.palouti, R.drawable.jianshencao, R.drawable.wangqiu, R.drawable.zuqiu, R.drawable.pashan, R.drawable.qixing, R.drawable.ganlanqiu, R.drawable.paobu, R.drawable.quanji, R.drawable.tiaosheng, R.drawable.youyong, R.drawable.biqiu, R.drawable.lunhua};

    public static int dip2px(Context context, float dipValue) {
        return (int) ((dipValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int progress2degree(int progress) {
        return (int) (((double) (((float) progress) * 3.6f)) + 0.5d);
    }

    public static boolean checkIsFirstEntry(Context ctx) {
        return ctx.getSharedPreferences("app_config", 0).getBoolean("is_first_enter", true);
    }

    public static void setFirstEntry(Context ctx) {
        Editor et = ctx.getSharedPreferences("app_config", 0).edit();
        et.putBoolean("is_first_enter", false);
        et.commit();
    }

    public static String getUuid(Context ctx) {
        return ctx.getSharedPreferences("app_config", 0).getString("phone_uuid", "");
    }

    public static void setUuid(Context ctx, String uuid) {
        Editor et = ctx.getSharedPreferences("app_config", 0).edit();
        et.putString("phone_uuid", uuid);
        et.commit();
    }

    public static List<String> getDateFromDb(Context ctx) {
        List<String> date = new ArrayList();
        Cursor c = ctx.getContentResolver().query(DataBaseContants.CONTENT_URI, new String[]{"_id", "date"}, null, null, "date ASC");
        c.moveToFirst();
        int count = c.getCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                String temp = c.getString(c.getColumnIndex("date"));
                if (date.indexOf(temp) == -1) {
                    date.add(temp);
                }
                c.moveToNext();
            }
        }
        c.close();
        return date;
    }

    @SuppressLint({"SimpleDateFormat"})
    public static String getDate(int prev_index) {
        Calendar c = Calendar.getInstance();
        c.set(c.get(1), c.get(2), c.get(5) - prev_index);
        return formatter.format(c.getTime());
    }

    public static void saveAccountRegistDay(int count) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).edit();
        et.putInt("AccountRegistDay", count);
        et.commit();
    }

    public static int getAccountRegistDay() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).getInt("AccountRegistDay", 1);
    }

    @SuppressLint({"SimpleDateFormat"})
    public static String getDate(String day, int prev_index) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(formatter.parse(day));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.set(c.get(1), c.get(2), c.get(5) - prev_index);
        return formatter.format(c.getTime());
    }

    @SuppressLint({"SimpleDateFormat"})
    public static int getDayCount(String enterDay, String today, String format) {
        int count = 0;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            Date date1 = formatter.parse(enterDay);
            Date date2 = formatter.parse(today);
            c1.setTime(date1);
            c2.setTime(date2);
            long l = c2.getTimeInMillis() - c1.getTimeInMillis();
            count = (int) Math.ceil(((double) l) / 8.64E7d);
            if (l <= 0 || count == 0) {
                count = 1;
            } else {
                count++;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("gchk", "day count = " + count);
        return count;
    }

    @SuppressLint({"SimpleDateFormat"})
    public static String dateFormat(String date, String paramString) {
        if (paramString == null) {
            paramString = "yyyy-MM-dd HH:mm:ss";
        }
        Calendar localCalendar = Calendar.getInstance();
        try {
            localCalendar.setTime(formatter.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat(paramString).format(localCalendar.getTime());
    }

    @SuppressLint({"SimpleDateFormat"})
    public static Boolean isSameWeek(String date1, String date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            Date d1 = formatter.parse(date1);
            Date d2 = formatter.parse(date2);
            c1.setTime(d1);
            c2.setTime(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int subYear = c1.get(1) - c2.get(1);
        if (subYear == 0) {
            if (c1.get(3) == c2.get(3)) {
                return Boolean.valueOf(true);
            }
        } else if (subYear == 1 && c2.get(2) == 11) {
            if (c1.get(3) == c2.get(3)) {
                return Boolean.valueOf(true);
            }
        } else if (subYear == -1 && c1.get(2) == 11 && c1.get(3) == c2.get(3)) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    @SuppressLint({"SimpleDateFormat"})
    public static Boolean isSameMonth(String date1, String date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            Date d1 = formatter.parse(date1);
            Date d2 = formatter.parse(date2);
            c1.setTime(d1);
            c2.setTime(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c1.get(1) - c2.get(1) == 0 && c1.get(2) == c2.get(2)) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public static String getScreenShot(String fileName) {
        String filePath = getSDPath() + "/Running/share/" + fileName;
        return new File(filePath).exists() ? filePath : null;
    }

    public static void saveBitmapToFile(Bitmap bitmap, String fileName) {
        String filePath = getSDPath() + "/Running/share/";
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File f = new File(filePath + fileName);
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(CompressFormat.JPEG, 80, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
    }

    public static void saveGpsBitmapToFile(Bitmap bitmap, String fileName) {
        String filePath = getSDPath() + "/Running/gps/";
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File f = new File(filePath + fileName);
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
    }

    public static Bitmap convertFileToBitmap(String name) {
        Bitmap myBitmap = null;
        String sdPath = getSDPath();
        if (TextUtils.isEmpty(sdPath)) {
            return null;
        }
        String fileString = sdPath + name;
        if (new File(fileString).exists()) {
            try {
                myBitmap = BitmapFactory.decodeFile(fileString);
            } catch (OutOfMemoryError e) {
                System.gc();
                myBitmap = null;
                e.printStackTrace();
            }
        }
        return myBitmap;
    }

    public static String getSDPath() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    public static PersonalGoal getPersonalGoal() {
        Context sContext = RunningApp.getInstance().getApplicationContext();
        PersonalGoal goal = new PersonalGoal();
        SharedPreferences sp = sContext.getSharedPreferences(SP_GOAL_FILENAME, 0);
        goal.mGoalCalories = sp.getInt(SP_GOAL_KEY_CAL, 200);
        goal.mGoalSteps = sp.getInt("steps", 7000);
        return goal;
    }

    public static void updatePersonalGoal(PersonalGoal goal) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_GOAL_FILENAME, 0).edit();
        et.putInt(SP_GOAL_KEY_CAL, goal.mGoalCalories);
        et.putInt("steps", goal.mGoalSteps);
        et.commit();
    }

    public static void updatePersonalGoalStep(PersonalGoal goal) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_GOAL_FILENAME, 0).edit();
        et.putInt("steps", goal.mGoalSteps);
        et.commit();
    }

    public static PersonalConfig getPersonalConfig() {
        String wight;
        Context sContext = RunningApp.getInstance().getApplicationContext();
        PersonalConfig config = new PersonalConfig();
        SharedPreferences sp = sContext.getSharedPreferences(SP_CONFIG_FILENAME, 0);
        config.setSex(sp.getInt("sex", 0));
        if (sp.contains(SP_CONFIG_KEY_WIGHT)) {
            wight = sp.getString(SP_CONFIG_KEY_WIGHT, "175");
        } else {
            wight = sp.getInt(SP_CONFIG_KEY_WIDTH, 65) + ".0";
        }
        config.setWeight(wight);
        config.setHeight(sp.getInt("height", 180));
        config.setYear(sp.getInt("year", 1991));
        return config;
    }

    public static void updatePersonalConfig(PersonalConfig config) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_CONFIG_FILENAME, 0).edit();
        et.putInt("sex", config.getSex());
        et.putString(SP_CONFIG_KEY_WIGHT, config.getWeight());
        et.putInt("height", config.getHeight());
        et.putInt("year", config.getYear());
        et.commit();
    }

    public static void saveConsigneeInfo(String name, String phone, String address) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_CONFIG_FILENAME, 0).edit();
        et.putString("consigneeName", name);
        et.putString("consigneePhone", phone);
        et.putString("consigneeLocation", address);
        et.commit();
    }

    public static String getConsigneeName(Context sContext) {
        return sContext.getSharedPreferences(SP_CONFIG_FILENAME, 0).getString("consigneeName", "");
    }

    public static String getConsigneePhone(Context sContext) {
        return sContext.getSharedPreferences(SP_CONFIG_FILENAME, 0).getString("consigneePhone", "");
    }

    public static String getConsigneeAddress(Context sContext) {
        return sContext.getSharedPreferences(SP_CONFIG_FILENAME, 0).getString("consigneeLocation", "");
    }

    public static int getPm25(String date) {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences("weather", 0).getInt(date, 0);
    }

    public static void updatePm25(String date, int pm25) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences("weather", 0).edit();
        et.putInt(date, pm25);
        et.commit();
    }

    public static String getDeviceVersion() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(USR_INFO, 0).getString("hardware_version", "");
    }

    public static void setDeviceVersion(String hardwareVersion) {
        Editor editor = RunningApp.getInstance().getApplicationContext().getSharedPreferences(USR_INFO, 0).edit();
        editor.putString("hardware_version", hardwareVersion);
        editor.commit();
    }

    public static String getHardwareVersion() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(USR_INFO, 0).getString("hardware_version", "");
    }

    public static void setHardwareVersion(String hardwareVersion) {
        Editor editor = RunningApp.getInstance().getApplicationContext().getSharedPreferences(USR_INFO, 0).edit();
        editor.putString("hardware_version", hardwareVersion);
        editor.commit();
    }

    public static String getCurrentDeviceName() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(USR_INFO, 0).getString("device_name", "");
    }

    public static void setCurrentDeviceName(String deviceName) {
        Editor editor = RunningApp.getInstance().getApplicationContext().getSharedPreferences(USR_INFO, 0).edit();
        editor.putString("device_name", deviceName);
        editor.commit();
    }

    public static void setSppConnectedFlag(boolean flag) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).edit();
        et.putBoolean(SP_SPP_FLAG_KEY_FLAG, flag);
        et.commit();
    }

    public static boolean getSppConnectedFlag() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).getBoolean(SP_SPP_FLAG_KEY_FLAG, false);
    }

    public static void setSyncRnningDataNow(boolean flag) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).edit();
        et.putBoolean(SP_SPP_FLAG_KEY_SYNCNOW, flag);
        et.commit();
    }

    public static boolean getSyncRunningDataNow() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).getBoolean(SP_SPP_FLAG_KEY_SYNCNOW, false);
    }

    public static void setShowCreateScDialog(boolean show) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).edit();
        et.putBoolean(SP_SPP_FLAG_KEY_SHOWDIALOG, show);
        et.commit();
    }

    public static boolean getShowCreateScDialog() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).getBoolean(SP_SPP_FLAG_KEY_SHOWDIALOG, true);
    }

    public static void setCurrPageIndex(int index) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).edit();
        et.putInt(SP_SPP_FLAG_KEY_VIEWINDEX, index);
        et.commit();
    }

    public static int getCurrPageIndex() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).getInt(SP_SPP_FLAG_KEY_VIEWINDEX, 0);
    }

    public static void setSleepCurrPageIndex(int index) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).edit();
        et.putInt(SP_SPP_FLAG_KEY_SLEEP_VIEWINDEX, index);
        et.commit();
    }

    public static boolean getElectricityRemind() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(MARS5_SWITCH_REMIND, 32768).getBoolean("electricity_remind", false);
    }

    public static void setElectricityRemind(boolean electricityRemind) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(MARS5_SWITCH_REMIND, 32768).edit();
        et.putBoolean("electricity_remind", electricityRemind);
        et.commit();
    }

    public static boolean getWholePointRemind() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(MARS5_SWITCH_REMIND, 32768).getBoolean("whole_point_remind", false);
    }

    public static void setWholePointRemind(boolean wholePointRemind) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(MARS5_SWITCH_REMIND, 32768).edit();
        et.putBoolean("whole_point_remind", wholePointRemind);
        et.commit();
    }

    public static boolean getMileageRemind() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(MARS5_SWITCH_REMIND, 32768).getBoolean("mileage_remind", false);
    }

    public static void setMileageRemind(boolean mileageRemind) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(MARS5_SWITCH_REMIND, 32768).edit();
        et.putBoolean("mileage_remind", mileageRemind);
        et.commit();
    }

    public static boolean getSaveElectricity() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(MARS5_SWITCH_REMIND, 32768).getBoolean("save_electricity", false);
    }

    public static void setSaveElectricity(boolean saveElectricity) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(MARS5_SWITCH_REMIND, 32768).edit();
        et.putBoolean("save_electricity", saveElectricity);
        et.commit();
    }

    public static boolean getSportTargetReminderSwitch() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).getBoolean("sport_target_remind", false);
    }

    public static void setSportTargetReminderSwitch(boolean isRemind) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).edit();
        et.putBoolean("sport_target_remind", isRemind);
        et.commit();
    }

    public static boolean getDisturbanceModleSwitch() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).getBoolean("disturbance_modle", false);
    }

    public static void setDisturbanceModleSwitch(boolean isOpen) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).edit();
        et.putBoolean("disturbance_modle", isOpen);
        et.commit();
    }

    public static boolean getSedentaryRemindSwitch() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).getBoolean("sedentary_remind_m2", false);
    }

    public static void setSedentaryRemindSwitch(boolean isOpen) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).edit();
        et.putBoolean("sedentary_remind_m2", isOpen);
        et.commit();
    }

    public static boolean getLunchNotBreakSwitch() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).getBoolean("lunch_not_break", false);
    }

    public static void setLunchNotBreakSwitch(boolean isOpen) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).edit();
        et.putBoolean("lunch_not_break", isOpen);
        et.commit();
    }

    public static String getDisturbanceModleTime() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).getString("disturbance_mode_time", "");
    }

    public static void setDisturbanceModleTime(String time) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).edit();
        et.putString("disturbance_mode_time", time);
        et.commit();
    }

    public static String getSedentaryRemindTime() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).getString("sedentary_remind_time", "");
    }

    public static void setSedentaryRemindTime(String time) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).edit();
        et.putString("sedentary_remind_time", time);
        et.commit();
    }

    public static String getHeightWeightData() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).getString("height_weight_data", "3|175|0750|||||");
    }

    public static void setHeightWeightData(String time) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).edit();
        et.putString("height_weight_data", time);
        et.commit();
    }

    public static String getM2UIDisplay() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).getString("m2_ui_display", "");
    }

    public static void setM2UIDisplay(String open) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).edit();
        et.putString("m2_ui_display", open);
        et.commit();
    }

    public static boolean getM2MileageSwitch() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).getBoolean("m2_mileage_switch", true);
    }

    public static void setM2MileageSwitch(boolean isOpen) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).edit();
        et.putBoolean("m2_mileage_switch", isOpen);
        et.commit();
    }

    public static boolean getM2CaloriesSwitch() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).getBoolean("m2_calories_switch", true);
    }

    public static void setM2CaloriesSwitch(boolean isOpen) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).edit();
        et.putBoolean("m2_calories_switch", isOpen);
        et.commit();
    }

    public static boolean getM2ElectricitySwitch() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).getBoolean("m2_electricity_switch", true);
    }

    public static void setM2ElectricitySwitch(boolean isOpen) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).edit();
        et.putBoolean("m2_electricity_switch", isOpen);
        et.commit();
    }

    public static boolean getM2WristBrightScreenSwitch() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).getBoolean("m2_wrist_birght_screen", false);
    }

    public static void setM2WristBrightScreenSwitch(boolean isOpen) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).edit();
        et.putBoolean("m2_wrist_birght_screen", isOpen);
        et.commit();
    }

    public static int getDeviceConnectState() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(USR_INFO, 0).getInt("device_connect_state", 1);
    }

    public static void setDeviceConnectState(int connectState) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(USR_INFO, 0).edit();
        et.putInt("device_connect_state", connectState);
        et.commit();
    }

    public static String getClickConnectDeviceName() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(USR_INFO, 0).getString("click_connect_device_name", "");
    }

    public static void setClickConnectDeviceName(String deviceName) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(USR_INFO, 0).edit();
        et.putString("click_connect_device_name", deviceName);
        et.commit();
    }

    public static boolean getIsSendBindingDevice() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).getBoolean("binding_device_m2", true);
    }

    public static void setIsSendBindingDevice(boolean isOpen) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(M2_SWITCH_REMIND, 0).edit();
        et.putBoolean("binding_device_m2", isOpen);
        et.commit();
    }

    public static int getSleepCurrPageIndex() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).getInt(SP_SPP_FLAG_KEY_SLEEP_VIEWINDEX, 0);
    }

    public static int calcDistance(int step, int height) {
        double meterperstep;
        if (height < 155) {
            meterperstep = 0.55d;
        } else if (height <= 165) {
            meterperstep = HeatmapTileProvider.DEFAULT_OPACITY;
        } else if (height <= 175) {
            meterperstep = 0.65d;
        } else if (height <= 185) {
            meterperstep = 0.7d;
        } else if (height <= 195) {
            meterperstep = 0.75d;
        } else {
            meterperstep = 0.8d;
        }
        return (int) (((double) step) * meterperstep);
    }

    public static int calcCalories(int meter, float weight) {
        int temp = (int) (weight / 1.0f);
        return (int) (((double) ((((float) meter) * weight) / 1000.0f)) * 1.175d);
    }

    public static String getStartTime() {
        StringBuilder stringBuilder = new StringBuilder();
        DateFormat dateFormat = new DateFormat();
        return stringBuilder.append(DateFormat.format("kk:mm", Calendar.getInstance(Locale.CHINA))).append("").toString();
    }

    public static String getCurData() {
        StringBuilder stringBuilder = new StringBuilder();
        DateFormat dateFormat = new DateFormat();
        return stringBuilder.append(DateFormat.format("yyyy-MM-dd", Calendar.getInstance(Locale.CHINA))).append("").toString();
    }

    public static int getSportKll(int sportIndex, int lastTime) {
        switch (sportIndex) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return lastTime * 3;
            case 7:
            case 8:
            case 9:
            case 10:
                return lastTime * 4;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                return lastTime * 5;
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                return lastTime * 6;
            case 22:
                return lastTime * 7;
            case 23:
            case 24:
            case 25:
            case 26:
                return lastTime * 8;
            case 27:
            case 28:
                return lastTime * 9;
            case 29:
                return lastTime * 10;
            case 30:
            case 31:
                return lastTime * 11;
            case 32:
            case 33:
                return lastTime * 13;
            default:
                return 0;
        }
    }

    public static int getSportIndex(String[] arry, String sportName) {
        for (int i = 0; i < arry.length; i++) {
            if (sportName.equals(arry[i])) {
                return i;
            }
        }
        return -1;
    }

    public static double getBMI(PersonalConfig mPersonalConfig) {
        float weight = mPersonalConfig.getWeightNum();
        int height = mPersonalConfig.getHeight();
        return (((double) weight) * 10000.0d) / ((double) (height * height));
    }

    public static int getHead(Context ctx) {
        return ctx.getSharedPreferences(USR_INFO, 0).getInt("edit_head", 6);
    }

    public static void setHead(Context ctx, int position) {
        Editor et = ctx.getSharedPreferences(USR_INFO, 0).edit();
        et.putInt("edit_head", position);
        et.commit();
    }

    public static String getUsrName(Context ctx) {
        return ctx.getSharedPreferences(USR_INFO, 0).getString("edit_usr_name", "");
    }

    public static void setUsrName(Context ctx, String name) {
        Editor et = ctx.getSharedPreferences(USR_INFO, 0).edit();
        et.putString("edit_usr_name", name);
        et.commit();
    }

    public static String getSignature(Context ctx) {
        return ctx.getSharedPreferences(USR_INFO, 0).getString("edit_setSignature", "");
    }

    public static void setSignature(Context ctx, String setSignature) {
        Editor et = ctx.getSharedPreferences(USR_INFO, 0).edit();
        et.putString("edit_setSignature", setSignature);
        et.commit();
    }

    public static String getLikeSportsIndex(Context ctx) {
        return ctx.getSharedPreferences(USR_INFO, 0).getString("edit_like_sports_index", "");
    }

    public static void setLikeSportsIndex(Context ctx, String setLikeIndex) {
        Editor et = ctx.getSharedPreferences(USR_INFO, 0).edit();
        et.putString("edit_like_sports_index", setLikeIndex);
        et.commit();
    }

    public static int getUserAge(Context ctx) {
        return ctx.getSharedPreferences(USR_INFO, 0).getInt("edit_user_age", 0);
    }

    public static void setUserAge(Context ctx, int setUserAge) {
        Editor et = ctx.getSharedPreferences(USR_INFO, 0).edit();
        et.putInt("edit_user_age", setUserAge);
        et.commit();
    }

    public static String getPhoneNum(Context ctx) {
        return ctx.getSharedPreferences(USR_INFO, 0).getString("phone_num", "");
    }

    public static void setPhoneNum(Context ctx, String string) {
        Editor et = ctx.getSharedPreferences(USR_INFO, 0).edit();
        et.putString("phone_num", string);
        et.commit();
    }

    public static String getEmail(Context ctx) {
        return ctx.getSharedPreferences(USR_INFO, 0).getString("email_info", "");
    }

    public static void setEmail(Context ctx, String email) {
        Editor et = ctx.getSharedPreferences(USR_INFO, 0).edit();
        et.putString("email_info", email);
        et.commit();
    }

    public static int getProviceIndex(Context ctx) {
        return ctx.getSharedPreferences(USR_INFO, 0).getInt("provice_index", 10000);
    }

    public static void setProviceIndex(Context ctx, int index) {
        Editor et = ctx.getSharedPreferences(USR_INFO, 0).edit();
        et.putInt("provice_index", index);
        et.commit();
    }

    public static int getCityIndex(Context ctx) {
        return ctx.getSharedPreferences(USR_INFO, 0).getInt("city_index", 10000);
    }

    public static void setCityIndex(Context ctx, int index) {
        Editor et = ctx.getSharedPreferences(USR_INFO, 0).edit();
        et.putInt("city_index", index);
        et.commit();
    }

    public static String getHeadType(Context ctx) {
        return ctx.getSharedPreferences(USR_INFO, 0).getString("regType", "");
    }

    public static String getHeadURI(Context ctx) {
        return ctx.getSharedPreferences(USR_INFO, 0).getString("logoUrl", "");
    }

    public static boolean getLogin(Context ctx) {
        return ctx.getSharedPreferences(USR_INFO, 0).getBoolean("usr_login", false);
    }

    public static void setLogin(Context ctx, boolean login) {
        Editor et = ctx.getSharedPreferences(USR_INFO, 0).edit();
        et.putBoolean("usr_login", login);
        loginStateChange = true;
        dbStateChange = true;
        et.commit();
    }

    public static String getLoginName(Context ctx) {
        return ctx.getSharedPreferences(USR_INFO, 0).getString("login_name", "");
    }

    public static String getOpenId(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(USR_INFO, 0);
        Log.i("hph", "openid = " + sp.getString("openid", ""));
        return sp.getString("openid", "");
    }

    public static void saveInfoToSharePreference(Context ctx, String userInfo) {
        Editor meditor = ctx.getSharedPreferences(USR_INFO, 0).edit();
        if (userInfo.equals("")) {
            meditor.clear();
        } else {
            String str = userInfo;
            String subStr1 = str.substring(userInfo.indexOf("username"), userInfo.length());
            meditor.putString("login_name", subStr1.substring(subStr1.indexOf(58) + 2, subStr1.indexOf(44) - 1));
            str = userInfo;
            String subStr2 = str.substring(userInfo.indexOf("openid"), userInfo.length());
            String openId = subStr2.substring(subStr2.indexOf(58) + 2, subStr2.indexOf(44) - 1);
            Log.d("txhlog", "openId:" + openId);
            meditor.putString("openid", openId);
            if (userInfo.indexOf("regtype") != -1) {
                str = userInfo;
                String subStr3 = str.substring(userInfo.indexOf("regtype"), userInfo.length());
                String regType = subStr3.substring(subStr3.indexOf(58) + 2, subStr3.indexOf(44) - 1);
                Log.d("txhlog", "regType:" + regType);
                meditor.putString("regType", regType);
            }
            str = userInfo;
            String subStr4 = str.substring(userInfo.indexOf("logoUrl"), userInfo.length());
            String logoUrl = subStr4.substring(subStr4.indexOf(58) + 2, subStr4.indexOf(125) - 1);
            if (logoUrl.startsWith("http:")) {
                while (logoUrl.contains("\\")) {
                    String tmpstr1 = logoUrl.substring(0, logoUrl.indexOf("\\"));
                    logoUrl = tmpstr1 + "" + logoUrl.substring(logoUrl.indexOf("\\") + 1, logoUrl.length());
                }
            } else {
                logoUrl = "";
            }
            meditor.putString("logoUrl", logoUrl);
            if (!logoUrl.equals("")) {
                new ImageAsynTask().execute(new String[]{logoUrl, "logo"});
            }
        }
        meditor.commit();
    }

    public static long getAutoSyncTime(Context ctx) {
        return ctx.getSharedPreferences(AUTO_SYNC_TIME, 0).getLong(AUTO_SYNC_TIME, 0);
    }

    public static void setAutoSyncTime(Context ctx, long time) {
        Editor et = ctx.getSharedPreferences(AUTO_SYNC_TIME, 0).edit();
        et.putLong(AUTO_SYNC_TIME, time);
        et.commit();
    }

    public static int getInfoResult(Context ctx) {
        return ctx.getSharedPreferences(USR_INFO, 0).getInt("personal_info_result", -1);
    }

    public static void setInfoResult(Context ctx, int result) {
        Editor et = ctx.getSharedPreferences(USR_INFO, 0).edit();
        et.putInt("personal_info_result", result);
        et.commit();
    }

    public static HashMap<String, String> getBleBindDevice(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(DEVICE_INFO, 0);
        HashMap<String, String> bondDevicesMap = new HashMap();
        String bindInfo = sp.getString("ble_bind_info", "");
        if (!TextUtils.isEmpty(bindInfo)) {
            String[] bindInfoArr = bindInfo.split(";");
            for (String deviceInfo : bindInfoArr) {
                bondDevicesMap.put(deviceInfo.substring(0, deviceInfo.lastIndexOf("|")), deviceInfo.substring(deviceInfo.lastIndexOf("|") + 1, deviceInfo.length()));
            }
        }
        return bondDevicesMap;
    }

    public static void updateBleBindInfo(Context ctx, String deviceName, String deviceAddress) {
        if (!TextUtils.isEmpty(deviceName) && !TextUtils.isEmpty(deviceAddress)) {
            SharedPreferences sp = ctx.getSharedPreferences(DEVICE_INFO, 0);
            String bindInfo = sp.getString("ble_bind_info", "");
            String addInfo = deviceName + "|" + deviceAddress + ";";
            if (!bindInfo.contains(addInfo)) {
                Editor et = sp.edit();
                et.putString("ble_bind_info", bindInfo + addInfo);
                et.commit();
                Log.d("yangyang", "setBleBindAddress sucess:" + deviceName + ",add:" + deviceAddress);
            }
        }
    }

    public static void removeBleBindInfo(Context ctx, String deviceName, String deviceAddress) {
        if (!TextUtils.isEmpty(deviceName) && !TextUtils.isEmpty(deviceAddress)) {
            SharedPreferences sp = ctx.getSharedPreferences(DEVICE_INFO, 0);
            String bindInfo = sp.getString("ble_bind_info", "");
            String removeInfo = deviceName + "|" + deviceAddress + ";";
            Log.i("yangyang", "removeInfo:" + removeInfo);
            Log.i("yangyang", "bindInfo:" + bindInfo);
            if (bindInfo.contains(removeInfo)) {
                Editor et = sp.edit();
                et.putString("ble_bind_info", bindInfo.replace(removeInfo, ""));
                et.commit();
                Log.d("yangyang", "removeBleBindInfo sucess");
            }
        }
    }

    public static synchronized long getPKL() {
        long lo;
        synchronized (Tools.class) {
            String a = String.valueOf((System.currentTimeMillis() / 10) % 100000000000L);
            lo = Long.parseLong(a + String.valueOf((WeightedLatLng.DEFAULT_INTENSITY + Math.random()) * 100000.0d).substring(1, 6));
            for (int i = 0; i < 3000; i++) {
                if (ls[i] == lo) {
                    lo = getPKL();
                    break;
                }
            }
            ls[li] = lo;
            li++;
            if (li == 3000) {
                li = 0;
            }
        }
        return lo;
    }

    public static String setAppVersionInfo(Context context) {
        String info = "";
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            info = pi.applicationInfo.loadLabel(context.getPackageManager()).toString() + " V" + pi.versionName;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return info;
    }

    public static Integer DataToInteger(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return Integer.valueOf(((c.get(1) * 10000) + ((c.get(2) + 1) * 100)) + c.get(5));
    }

    public static Date stringToDate(String date) {
        Calendar c = Calendar.getInstance();
        String[] a = date.split(SocializeConstants.OP_DIVIDER_MINUS);
        c.set(Integer.valueOf(a[0]).intValue(), Integer.valueOf(a[1]).intValue() - 1, Integer.valueOf(a[2]).intValue());
        return c.getTime();
    }

    public static void deleteSDCardFolder(File dir) {
        File to = new File(dir.getAbsolutePath() + System.currentTimeMillis());
        dir.renameTo(to);
        if (to.isDirectory()) {
            String[] children = to.list();
            for (String file : children) {
                File temp = new File(to, file);
                if (temp.isDirectory()) {
                    deleteSDCardFolder(temp);
                } else if (!temp.delete()) {
                    Log.d("deleteSDCardFolder", "DELETE FAIL");
                }
            }
            to.delete();
        }
    }

    public static void clearFeedTable(String name, Context sContext) {
        mDBOpenHelper = new DBOpenHelper(sContext);
        mDBOpenHelper.getWritableDatabase().execSQL("DELETE FROM " + name + ";");
        revertSeq(name);
        mDBOpenHelper.close();
    }

    private static void revertSeq(String name) {
        mDBOpenHelper.getWritableDatabase().execSQL("update sqlite_sequence set seq=0 where name='" + name + "'");
        mDBOpenHelper.close();
    }

    public static void setFirmwear(boolean flag) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).edit();
        et.putBoolean("firm_wear", flag);
        et.commit();
    }

    public static boolean getFirmwear() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).getBoolean("firm_wear", false);
    }

    public static boolean getMsgState(Context ctx) {
        return ctx.getSharedPreferences(USR_INFO, 0).getBoolean("msg_state", false);
    }

    public static void setMsgState(Context ctx, boolean state) {
        Editor et = ctx.getSharedPreferences(USR_INFO, 0).edit();
        et.putBoolean("msg_state", state);
        et.commit();
    }

    public static boolean getActState(Context ctx) {
        return ctx.getSharedPreferences(USR_INFO, 0).getBoolean("act_state", false);
    }

    public static void setActState(Context ctx, boolean state) {
        Editor et = ctx.getSharedPreferences(USR_INFO, 0).edit();
        et.putBoolean("act_state", state);
        et.commit();
    }

    public static int selectByIndex(int headIndex) {
        int length1 = headIcon1.length;
        int length2 = headIcon2.length;
        int length3 = headIcon3.length;
        int length4 = headIcon4.length;
        if (headIndex < length1) {
            return headIcon1[headIndex];
        }
        if (headIndex / 100 == 1 && headIndex - 100 < length2) {
            return headIcon2[headIndex - 100];
        }
        if (headIndex / 100 == 2 && headIndex - 200 < length3) {
            return headIcon3[headIndex - 200];
        }
        if (headIndex / 100 != 3 || headIndex - 300 >= length4) {
            return R.drawable.logo_default;
        }
        return headIcon4[headIndex - 300];
    }

    public static String getTimer(int time) {
        return ((time % 86400) / 3600) + mCont.getString(R.string.sleep_hour) + mCont.getString(R.string.time_point) + ((time % 3600) / 60) + mCont.getString(R.string.sleep_minute);
    }

    public static boolean fileIsExists(String path) {
        try {
            if (new File(path).exists()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static String keyString(HashMap<String, String> map, String value) {
        for (String keyString : map.keySet()) {
            if (((String) map.get(keyString)).equals(value)) {
                return keyString;
            }
        }
        return null;
    }

    public static String transformLongTime2StringFormat(long timestamp) {
        String time = "";
        try {
            time = new SimpleDateFormat("yyyy-MM-dd HHmmss").format(new Date(1000 * timestamp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String transformLongTime2StringFormat2(long timestamp) {
        String time = "";
        try {
            time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static long transformUTCTime2LongFormat(long timestamp) {
        String sSime = "";
        long time = 0;
        try {
            time = Long.valueOf(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(1000 * timestamp))).longValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static void saveAlarmBrain(String alarm) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).edit();
        et.putString("alarm_brain", alarm);
        et.commit();
    }

    public static String getAlarmBrain() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).getString("alarm_brain", "");
    }

    public static void saveUpdateAlarmBrain(String alarm, int id) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).edit();
        et.putString("alarm_brain" + id, alarm);
        et.commit();
    }

    public static String getUpdateAlarmBrain(int id) {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).getString("alarm_brain" + id, "");
    }

    public static void saveBatteryLevel(int level) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).edit();
        et.putInt("Battery_Level", level);
        et.commit();
    }

    public static int getBatteryLevel() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).getInt("Battery_Level", 100);
    }

    public static void setPhonePedState(boolean state) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(PHONE_PED, 0).edit();
        et.putBoolean(PED_STATE, state);
        et.commit();
    }

    public static boolean getPhonePedState() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(PHONE_PED, 0).getBoolean(PED_STATE, false);
    }

    public static void setPhoneSedentaryState(boolean state) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(PHONE_SEDENTARY, 0).edit();
        et.putBoolean(SEDENTARY_STATE, state);
        et.commit();
    }

    public static boolean getPhoneSedentaryState() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(PHONE_SEDENTARY, 0).getBoolean(SEDENTARY_STATE, false);
    }

    public static void setOpenDialogState(boolean state) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(PHONE_SEDENTARY, 0).edit();
        et.putBoolean(SEDENTARY_STATE, state);
        et.commit();
    }

    public static boolean getOpenDialogState() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(PHONE_SEDENTARY, 0).getBoolean(SEDENTARY_STATE, false);
    }

    public static void setConnectNotVibtation(boolean state) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(PHONE_PED, 0).edit();
        et.putBoolean(SP_SPP_FLAG_FILENAME, state);
        et.commit();
    }

    public static boolean getConnectNotVibtation() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(PHONE_PED, 0).getBoolean(SP_SPP_FLAG_FILENAME, false);
    }

    public static void saveSedentaryRemind(String sedentary) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).edit();
        et.putString("sedentary_remind", sedentary);
        et.commit();
    }

    public static String getSedentaryRemind() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_SPP_FLAG_FILENAME, 0).getString("sedentary_remind", "");
    }

    public static InputFilter newInputFilter(int maxLength, final String hintmsg) {
        final int maxLen = maxLength;
        return new InputFilter() {
            public CharSequence filter(CharSequence src, int start, int end, Spanned dest, int dstart, int dend) {
                int dindex = 0;
                int count = 0;
                while (count <= maxLen && dindex < dest.length()) {
                    int dindex2 = dindex + 1;
                    if (dest.charAt(dindex) < '') {
                        count++;
                    } else {
                        count += 2;
                    }
                    dindex = dindex2;
                }
                if (count > maxLen) {
                    Tools.makeToast(hintmsg);
                    return dest.subSequence(0, dindex - 1);
                }
                int sindex = 0;
                while (count <= maxLen && sindex < src.length()) {
                    int sindex2 = sindex + 1;
                    if (src.charAt(sindex) < '') {
                        count++;
                    } else {
                        count += 2;
                    }
                    sindex = sindex2;
                }
                if (count > maxLen) {
                    sindex--;
                    Tools.makeToast(hintmsg);
                }
                return src.subSequence(0, sindex);
            }
        };
    }

    public static void makeToast(String msg) {
        Toast.makeText(mCont, msg, 0).show();
    }
}

package com.zhuoyou.plugin.running;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;
import com.amap.api.services.core.AMapException;
import com.fithealth.running.R;
import com.mcube.lib.ped.PedometerService;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.bluetooth.connection.CustomCmd;
import com.zhuoyou.plugin.cloud.AlarmUtils;
import com.zhuoyou.plugin.cloud.CloudSync;
import com.zhuoyou.plugin.custom.CustomAlertDialog;
import com.zhuoyou.plugin.custom.CustomAlertDialog.Builder;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.database.DataBaseUtil;
import com.zhuoyou.plugin.mainFrame.MineFragment;
import com.zhuoyou.plugin.resideMenu.EquipManagerListActivity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainService extends Service {
    private static final int HANDLE_MSG_CANCEL_MSG = 1008;
    private static final int HANDLE_MSG_FINISH_MSG = 1111;
    private static final int HANDLE_MSG_NO_SLEEP_MSG = 1112;
    private static final int HANDLE_MSG_STORE_MSG = 1024;
    private static MainService mInstance;
    private static final Context sContext = RunningApp.getInstance().getApplicationContext();
    public static Boolean syncnow = Boolean.valueOf(false);
    private ContentResolver mContentResolver;
    private BroadcastReceiver mGetBatteryReceiver = new C13823();
    private BroadcastReceiver mGetDataReceiver = new C13834();
    private BroadcastReceiver mGetGPSDateReceiver = new C13812();
    private UpdateHandler mHandler = new UpdateHandler(this);
    BroadcastReceiver mScreenBroadcastReceiver = new C13801();
    private BroadcastReceiver mSyncPersionalReceiver = new C13885();
    private List<RunningItem> mTempLists = new ArrayList();

    class C13801 extends BroadcastReceiver {
        C13801() {
        }

        public void onReceive(Context context, Intent intent) {
            String screenAction = intent.getAction();
            if ("android.intent.action.SCREEN_ON".equals(screenAction)) {
                Log.i("hph", "ACTION_SCREEN_ON");
                if (Tools.getPhonePedState()) {
                    MainService.this.startService(new Intent(MainService.this, PedometerService.class));
                    PedometerService.ifmove = true;
                }
                Log.i("hph", "startService(stepServiceIntent");
            } else if ("android.intent.action.USER_PRESENT".equals(screenAction)) {
                Log.i("hph", "ACTION_USER_PRESENT");
            } else if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(screenAction)) {
                Log.i("hph", "ACTION_CLOSE_SYSTEM_DIALOGS");
            }
        }
    }

    class C13812 extends BroadcastReceiver {
        C13812() {
        }

        public void onReceive(Context context, Intent intent) {
            ArrayList<ContentProviderOperation> operations;
            if (intent.getAction().equals("ACTION_GPS_INFO")) {
                RunningItem gps_info = (RunningItem) intent.getSerializableExtra("gps_info");
                try {
                    operations = new ArrayList();
                    ContentProviderOperation op1 = ContentProviderOperation.newInsert(DataBaseContants.CONTENT_URI).withValues(gps_info.toContentValues()).withValue("_id", Long.valueOf(Tools.getPKL())).withValue(DataBaseContants.CONF_WEIGHT, gps_info.getmWeight()).withValue(DataBaseContants.BMI, gps_info.getmBmi()).withValue(DataBaseContants.IMG_URI, gps_info.getmImgUri()).withValue(DataBaseContants.EXPLAIN, gps_info.getmExplain()).withValue("type", Integer.valueOf(5)).withValue(DataBaseContants.STATISTICS, Integer.valueOf(0)).withValue(DataBaseContants.DATA_FROM, "GPS").withYieldAllowed(true).build();
                    Log.i("lsj", "heart_count:" + gps_info.getHeart_rate_count());
                    operations.add(op1);
                    MainService.this.mContentResolver.applyBatch(DataBaseContants.AUTHORITY, operations);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (OperationApplicationException e2) {
                    e2.printStackTrace();
                }
            } else if (intent.getAction().endsWith("ACTION_PHONE_STEPS")) {
                Log.i("111", "save 5555555555555");
                RunningItem item = (RunningItem) intent.getSerializableExtra("phone_steps");
                total_step = intent.getIntExtra("total_step", 0);
                config = Tools.getPersonalConfig();
                calories = Tools.calcCalories(Tools.calcDistance(total_step, config.getHeight()), config.getWeightNum());
                day = item.getDate();
                try {
                    operations = new ArrayList();
                    operations.add(ContentProviderOperation.newInsert(DataBaseContants.CONTENT_URI).withValues(item.toContentValues()).withValue("_id", Long.valueOf(Tools.getPKL())).withValue(DataBaseContants.DATA_FROM, "phone").withYieldAllowed(true).build());
                    MainService.this.mContentResolver.applyBatch(DataBaseContants.AUTHORITY, operations);
                } catch (RemoteException e3) {
                    e3.printStackTrace();
                } catch (OperationApplicationException e22) {
                    e22.printStackTrace();
                }
            } else if (intent.getAction().endsWith("ACTION_PHONE_TOTAL_STEPS")) {
                total_step = intent.getIntExtra("total_step", 0);
                config = Tools.getPersonalConfig();
                int meter = Tools.calcDistance(total_step, config.getHeight());
                calories = Tools.calcCalories(meter, config.getWeightNum());
                day = Tools.getDate(0);
                try {
                    operations = new ArrayList();
                    Cursor c = MainService.this.mContentResolver.query(DataBaseContants.CONTENT_URI, new String[]{"_id", "steps", DataBaseContants.KILOMETER, DataBaseContants.CALORIES, DataBaseContants.SYNC_STATE}, "date  = ? AND statistics = ? AND data_from = ? ", new String[]{day, "2", "phone"}, null);
                    if (c.getCount() <= 0 || !c.moveToFirst() || c.getLong(c.getColumnIndex("_id")) <= 0) {
                        operations.add(ContentProviderOperation.newInsert(DataBaseContants.CONTENT_URI).withValue("_id", Long.valueOf(Tools.getPKL())).withValue("date", day).withValue("steps", Integer.valueOf(total_step)).withValue(DataBaseContants.CALORIES, Integer.valueOf(calories)).withValue(DataBaseContants.KILOMETER, Integer.valueOf(meter)).withValue("type", Integer.valueOf(6)).withValue(DataBaseContants.STATISTICS, Integer.valueOf(2)).withValue(DataBaseContants.DATA_FROM, "phone").withYieldAllowed(true).build());
                    } else {
                        ContentProviderOperation op2;
                        if (c.getInt(c.getColumnIndex(DataBaseContants.SYNC_STATE)) == 0) {
                            op2 = ContentProviderOperation.newUpdate(DataBaseContants.CONTENT_URI).withSelection("date = ? AND statistics = ? AND data_from = ? ", new String[]{day, "2", "phone"}).withValue("steps", Integer.valueOf(total_step)).withValue(DataBaseContants.CALORIES, Integer.valueOf(calories)).withValue(DataBaseContants.KILOMETER, Integer.valueOf(meter)).withYieldAllowed(true).build();
                        } else {
                            op2 = ContentProviderOperation.newUpdate(DataBaseContants.CONTENT_URI).withSelection("date = ? AND statistics = ? AND data_from = ? ", new String[]{day, "2", "phone"}).withValue("steps", Integer.valueOf(total_step)).withValue(DataBaseContants.CALORIES, Integer.valueOf(calories)).withValue(DataBaseContants.KILOMETER, Integer.valueOf(meter)).withValue(DataBaseContants.SYNC_STATE, Integer.valueOf(2)).withYieldAllowed(true).build();
                        }
                        operations.add(op2);
                    }
                    c.close();
                    MainService.this.mContentResolver.applyBatch(DataBaseContants.AUTHORITY, operations);
                } catch (RemoteException e32) {
                    e32.printStackTrace();
                } catch (OperationApplicationException e222) {
                    e222.printStackTrace();
                }
            }
            MainService.this.checkDataBase();
        }
    }

    class C13823 extends BroadcastReceiver {
        C13823() {
        }

        public void onReceive(Context context, Intent intent) {
            char[] c_tag = intent.getCharArrayExtra("tag");
            if (MineFragment.mHandler != null) {
                Bundle bundle = new Bundle();
                bundle.putInt("total_number", c_tag[2] - 32);
                bundle.putInt("battery_number", c_tag[3] - 32);
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = c_tag[0] - 32;
                msg.arg2 = c_tag[1];
                msg.setData(bundle);
                MineFragment.mHandler.sendMessage(msg);
            }
            if (EquipManagerListActivity.mHandler != null) {
                bundle = new Bundle();
                bundle.putInt("total_number", c_tag[2] - 32);
                bundle.putInt("battery_number", c_tag[3] - 32);
                msg = new Message();
                msg.what = 3;
                msg.arg1 = c_tag[0] - 32;
                msg.arg2 = c_tag[1];
                msg.setData(bundle);
                EquipManagerListActivity.mHandler.sendMessage(msg);
            }
        }
    }

    class C13834 extends BroadcastReceiver {
        C13834() {
        }

        public void onReceive(Context context, Intent intent) {
            if (!CloudSync.isSync) {
                char[] c_tag;
                String content;
                String data_from;
                int curr_index;
                int totle_number;
                String[] s;
                PersonalConfig config;
                int number;
                int i;
                int steps;
                int meter;
                int calories;
                RunningItem item;
                Message msg;
                if (intent.getAction().equals("com.zhuoyou.plugin.running.get")) {
                    c_tag = intent.getCharArrayExtra("tag");
                    content = intent.getStringExtra("content");
                    data_from = intent.getStringExtra("from");
                    if (data_from == null || data_from.equals("")) {
                        data_from = "phone";
                    }
                    Log.i("gchk", "mGetDataReceiver 0X71 TAG[0] =" + c_tag[0] + "TAG[1]= " + c_tag[1] + "||| c= " + content + "||| from= " + data_from);
                    curr_index = c_tag[0] - 32;
                    totle_number = c_tag[1] - 32;
                    Log.i("gchk", "curr_index = " + curr_index + " |||totle_number =" + totle_number);
                    s = content.split("\\|");
                    config = Tools.getPersonalConfig();
                    PersonalGoal Goal = Tools.getPersonalGoal();
                    if (curr_index == totle_number) {
                        number = s.length / 2;
                        for (i = 0; i < number; i++) {
                            steps = Integer.parseInt(s[(i * 2) + 0]);
                            meter = Tools.calcDistance(steps, config.getHeight());
                            calories = Tools.calcCalories(meter, config.getWeightNum());
                            item = new RunningItem();
                            item.setSteps(steps);
                            item.setCalories(calories);
                            item.setKilometer(meter);
                            item.setDate(MainService.this.formatRemoteDate(s[(i * 2) + 1]));
                            item.setStartTime("");
                            item.setEndTime("");
                            item.setDuration("");
                            item.setSportsType(0);
                            item.setmType(0);
                            item.setisComplete(0);
                            item.setisStatistics(2);
                            item.setDataFrom(data_from);
                            Log.i("gchk", item.toString());
                            MainService.this.mTempLists.add(item);
                        }
                        MainService.this.mHandler.removeMessages(1008);
                        msg = new Message();
                        msg.what = 1024;
                        MainService.this.mHandler.sendMessage(msg);
                        MainService.this.sendSyncRunningDataCallBack();
                        return;
                    }
                    number = s.length / 4;
                    for (i = 0; i < number; i++) {
                        steps = Integer.parseInt(s[(i * 4) + 0]);
                        meter = Tools.calcDistance(steps, config.getHeight());
                        calories = Tools.calcCalories(meter, config.getWeightNum());
                        item = new RunningItem();
                        item.setSteps(steps);
                        item.setCalories(calories);
                        item.setKilometer(meter);
                        item.setDate(MainService.this.formatRemoteDate(s[(i * 4) + 1]));
                        item.setStartTime(MainService.this.formatRemoteTime(s[(i * 4) + 2]));
                        item.setEndTime(MainService.this.formatRemoteTime(s[(i * 4) + 3]));
                        item.setDuration(MainService.this.getDuration(s[(i * 4) + 2], s[(i * 4) + 3]));
                        item.setSportsType(0);
                        item.setmType(2);
                        item.setisComplete(0);
                        item.setisStatistics(0);
                        item.setDataFrom(data_from);
                        Log.i("gchk", item.toString());
                        MainService.this.mTempLists.add(item);
                    }
                    MainService.this.mHandler.removeMessages(1008);
                    msg = new Message();
                    msg.what = 1008;
                    MainService.this.mHandler.sendMessageDelayed(msg, 10000);
                } else if (intent.getAction().equals("com.zhuoyou.plugin.running.get.gatt")) {
                    int statistics = intent.getIntExtra(DataBaseContants.STATISTICS, -1);
                    data_from = intent.getStringExtra("from");
                    config = Tools.getPersonalConfig();
                    if (statistics == 2) {
                        Message message;
                        s = intent.getStringExtra("content").split("\\|");
                        number = s.length / 2;
                        for (i = 0; i < number; i++) {
                            steps = Integer.parseInt(s[(i * 2) + 0]);
                            meter = Tools.calcDistance(steps, config.getHeight());
                            calories = Tools.calcCalories(meter, config.getWeightNum());
                            item = new RunningItem();
                            item.setSteps(steps);
                            item.setCalories(calories);
                            item.setKilometer(meter);
                            item.setDate(s[(i * 2) + 1]);
                            item.setStartTime("");
                            item.setEndTime("");
                            item.setDuration("");
                            item.setSportsType(0);
                            item.setmType(0);
                            item.setisComplete(0);
                            item.setisStatistics(statistics);
                            item.setDataFrom(data_from);
                            Log.i("gchk", item.toString());
                            MainService.this.mTempLists.add(item);
                        }
                        MainService.this.mHandler.removeMessages(1008);
                        MainService.this.insertToDatabase();
                        MainService.syncnow = Boolean.valueOf(false);
                        Toast.makeText(MainService.sContext, R.string.get_complete, 0).show();
                        if (HomePageItemFragment.mHandler != null) {
                            message = new Message();
                            message.what = 2;
                            HomePageItemFragment.mHandler.sendMessage(message);
                        }
                        if (HomePageFragment.mHandler != null) {
                            message = new Message();
                            message.what = 2;
                            message.arg1 = 1;
                            HomePageFragment.mHandler.sendMessage(message);
                        }
                        if (SleepPageItemFragment.mHandler != null) {
                            message = new Message();
                            message.what = 2;
                            SleepPageItemFragment.mHandler.sendMessage(message);
                        }
                    } else if (statistics == 0) {
                        steps = intent.getIntExtra("step", -1);
                        date = intent.getStringExtra("date");
                        String startTime = intent.getStringExtra("start");
                        String endTime = intent.getStringExtra("end");
                        meter = Tools.calcDistance(steps, config.getHeight());
                        calories = Tools.calcCalories(meter, config.getWeightNum());
                        item = new RunningItem();
                        item.setSteps(steps);
                        item.setCalories(calories);
                        item.setKilometer(meter);
                        item.setDate(date);
                        item.setStartTime(MainService.this.formatRemoteTime(startTime));
                        item.setEndTime(MainService.this.formatRemoteTime(endTime));
                        item.setDuration(MainService.this.getDuration(startTime, endTime));
                        item.setSportsType(0);
                        item.setmType(2);
                        item.setisComplete(0);
                        item.setisStatistics(statistics);
                        item.setDataFrom(data_from);
                        Log.i("gchk", item.toString());
                        MainService.this.mTempLists.add(item);
                        MainService.this.mHandler.removeMessages(1008);
                        msg = new Message();
                        msg.what = 1008;
                        MainService.this.mHandler.sendMessageDelayed(msg, 10000);
                    }
                } else if (intent.getAction().equals("com.zhuoyou.plugin.running.sleep")) {
                    c_tag = intent.getCharArrayExtra("tag");
                    content = intent.getStringExtra("content");
                    Log.i("caixinxin", "get sleep data 0X81 TAG[0] =" + c_tag[0] + "TAG[1]= " + c_tag[1] + "||| c= " + content);
                    curr_index = c_tag[0] - 32;
                    totle_number = c_tag[1] - 32;
                    Log.i("caixinxin", "curr_index = " + curr_index + " |||totle_number =" + totle_number);
                    s = content.split("\\|");
                    if (totle_number > 0) {
                        date = MainService.this.formatRemoteDate(s[0]);
                        if (Integer.parseInt(s[2]) >= 2100) {
                            date = Tools.getDate(date, -1);
                        }
                        DataBaseUtil.insertClassicSleep(MainService.sContext, date, content.substring(s[0].length() + 1));
                        MainService.this.mHandler.removeMessages(MainService.HANDLE_MSG_NO_SLEEP_MSG);
                        msg = new Message();
                        msg.what = MainService.HANDLE_MSG_NO_SLEEP_MSG;
                        MainService.this.mHandler.sendMessageDelayed(msg, 10000);
                    }
                    if (totle_number == curr_index) {
                        MainService.this.mHandler.removeMessages(MainService.HANDLE_MSG_NO_SLEEP_MSG);
                        msg = new Message();
                        msg.what = 1111;
                        MainService.this.mHandler.sendMessage(msg);
                    }
                }
            }
        }
    }

    class C13885 extends BroadcastReceiver {
        C13885() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.zhuoyou.plugin.running.sync.personal")) {
                char[] c_tag = intent.getCharArrayExtra("tag");
                String content = intent.getStringExtra("content");
                Log.i("gchk", "mSyncPersionalReceiver 0X73 TAG[0] =" + c_tag[0] + "TAG[1]= " + c_tag[1] + "||| c= " + content);
                int totle_number = c_tag[1] - 32;
                Log.i("gchk", "curr_index = " + (c_tag[0] - 32) + " |||totle_number =" + totle_number);
                String[] s = content.split("\\|");
                if (s.length < 4) {
                    char[] tag = new char[]{'!', 'ÿ', 'ÿ', 'ÿ'};
                    Intent intent1 = new Intent("com.tyd.plugin.receiver.sendmsg");
                    intent1.putExtra("plugin_cmd", 117);
                    intent1.putExtra("plugin_content", "himan");
                    intent1.putExtra("plugin_tag", tag);
                    MainService.this.sendBroadcast(intent1);
                    return;
                }
                final PersonalConfig config = new PersonalConfig();
                final PersonalGoal goal = new PersonalGoal();
                int sex = Integer.parseInt(s[0]);
                if (sex == 0) {
                    config.setSex(1);
                } else if (sex == 1) {
                    config.setSex(0);
                }
                int year = Integer.parseInt(s[3]);
                if (year < 200) {
                    year = Calendar.getInstance().get(1) - year;
                }
                config.setYear(year);
                config.setHeight(Integer.parseInt(s[1]));
                config.setWeight(s[2] + ".0");
                if (s.length > 4 && !s[4].equals("$")) {
                    goal.setStep(Integer.parseInt(s[4]));
                }
                final PersonalConfig config2 = Tools.getPersonalConfig();
                final PersonalGoal goal2 = Tools.getPersonalGoal();
                Builder builder;
                final Context context2;
                CustomAlertDialog dialog;
                if (s.length < 5 || s[4].equals("$")) {
                    if (config.isEquals(config2)) {
                        MainService.this.sendSyncPeronalConfigCallBack();
                        MainService.this.syncRunningData();
                    } else {
                        builder = new Builder(context);
                        builder.setTitle(R.string.syncs_persion_setting);
                        builder.setMessage(R.string.syncs_persion_setting_message);
                        context2 = context;
                        builder.setPositiveButton(R.string.upload, (OnClickListener) new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String s = config2.toString() + "|$|$|";
                                Log.i("gchk", "send to remote personal:" + s);
                                Intent intent = new Intent("com.tyd.plugin.receiver.sendmsg");
                                intent.putExtra("plugin_cmd", CustomCmd.CMD_SYNC_PERSONAL_DATA);
                                intent.putExtra("plugin_content", s);
                                context2.sendBroadcast(intent);
                                dialog.dismiss();
                                MainService.this.syncRunningData();
                            }
                        });
                        builder.setNegativeButton(R.string.download, (OnClickListener) new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                MainService.this.sendSyncPeronalConfigCallBack();
                                if (!(config.getHeight() == 0 || config.getWeightNum() == 0.0f)) {
                                    Tools.updatePersonalConfig(config);
                                }
                                dialog.dismiss();
                                MainService.this.syncRunningData();
                            }
                        });
                        builder.setCancelable(Boolean.valueOf(false));
                        dialog = builder.create();
                        dialog.getWindow().setType(AMapException.CODE_AMAP_ENGINE_TABLEID_NOT_EXIST);
                        dialog.show();
                    }
                } else if (config.isEquals(config2) && goal.getStep() == goal2.getStep()) {
                    MainService.this.sendSyncPeronalConfigCallBack();
                    MainService.this.syncRunningData();
                } else {
                    builder = new Builder(context);
                    builder.setTitle(R.string.syncs_persion_setting);
                    builder.setMessage(R.string.syncs_persion_setting_message);
                    context2 = context;
                    builder.setPositiveButton(R.string.upload, (OnClickListener) new OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String s = config2.toString() + goal2.toString();
                            Log.i("gchk", "send to remote personal:" + s);
                            Intent intent = new Intent("com.tyd.plugin.receiver.sendmsg");
                            intent.putExtra("plugin_cmd", CustomCmd.CMD_SYNC_PERSONAL_DATA);
                            intent.putExtra("plugin_content", s);
                            context2.sendBroadcast(intent);
                            dialog.dismiss();
                            MainService.this.syncRunningData();
                        }
                    });
                    builder.setNegativeButton(R.string.download, (OnClickListener) new OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            MainService.this.sendSyncPeronalConfigCallBack();
                            if (!(config.getHeight() == 0 || config.getWeightNum() == 0.0f)) {
                                Tools.updatePersonalConfig(config);
                            }
                            Tools.updatePersonalGoalStep(goal);
                            dialog.dismiss();
                            MainService.this.syncRunningData();
                        }
                    });
                    builder.setCancelable(Boolean.valueOf(false));
                    dialog = builder.create();
                    dialog.getWindow().setType(AMapException.CODE_AMAP_ENGINE_TABLEID_NOT_EXIST);
                    dialog.show();
                }
                MainService.this.mHandler.removeMessages(1008);
            }
        }
    }

    public static class UpdateHandler extends Handler {
        WeakReference<MainService> mMyFragment;

        public UpdateHandler(MainService f) {
            this.mMyFragment = new WeakReference(f);
        }

        public void handleMessage(Message msg) {
            if (this.mMyFragment != null) {
                MainService home = (MainService) this.mMyFragment.get();
                if (home != null) {
                    Message message;
                    switch (msg.what) {
                        case 1008:
                            Log.i("1111", "HANDLE_MSG_CANCEL_MSG");
                            Toast.makeText(home, R.string.connect_time_out, 0).show();
                            MainService.syncnow = Boolean.valueOf(false);
                            if (HomePageItemFragment.mHandler != null) {
                                message = new Message();
                                message.what = 1;
                                HomePageItemFragment.mHandler.sendMessage(message);
                            }
                            if (HomePageFragment.mHandler != null) {
                                message = new Message();
                                message.what = 3;
                                message.arg1 = 1;
                                HomePageFragment.mHandler.sendMessage(message);
                            }
                            if (SleepPageItemFragment.mHandler != null) {
                                message = new Message();
                                message.what = 1;
                                SleepPageItemFragment.mHandler.sendMessage(message);
                                return;
                            }
                            return;
                        case 1024:
                            Log.i("1111", "HANDLE_MSG_STORE_MSG");
                            home.insertToDatabase();
                            home.syncSleepData();
                            return;
                        case 1111:
                            Log.i("1111", "HANDLE_MSG_FINISH_MSG");
                            MainService.syncnow = Boolean.valueOf(false);
                            Toast.makeText(MainService.sContext, R.string.get_complete, 0).show();
                            if (HomePageItemFragment.mHandler != null) {
                                message = new Message();
                                message.what = 2;
                                HomePageItemFragment.mHandler.sendMessage(message);
                            }
                            if (HomePageFragment.mHandler != null) {
                                message = new Message();
                                message.what = 2;
                                message.arg1 = 1;
                                HomePageFragment.mHandler.sendMessage(message);
                            }
                            if (SleepPageItemFragment.mHandler != null) {
                                message = new Message();
                                message.what = 2;
                                SleepPageItemFragment.mHandler.sendMessage(message);
                                return;
                            }
                            return;
                        case MainService.HANDLE_MSG_NO_SLEEP_MSG /*1112*/:
                            Log.i("1111", "HANDLE_MSG_NO_SLEEP_MSG");
                            MainService.syncnow = Boolean.valueOf(false);
                            Toast.makeText(MainService.sContext, R.string.get_complete, 0).show();
                            if (HomePageItemFragment.mHandler != null) {
                                message = new Message();
                                message.what = 2;
                                HomePageItemFragment.mHandler.sendMessage(message);
                            }
                            if (HomePageFragment.mHandler != null) {
                                message = new Message();
                                message.what = 2;
                                message.arg1 = 1;
                                HomePageFragment.mHandler.sendMessage(message);
                            }
                            if (SleepPageItemFragment.mHandler != null) {
                                message = new Message();
                                message.what = 2;
                                SleepPageItemFragment.mHandler.sendMessage(message);
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }
            }
        }
    }

    public static MainService getInstance() {
        if (mInstance == null) {
            Log.i("gchk", "getInstance(), Main service is null.");
            startMainService();
        }
        return mInstance;
    }

    private static void startMainService() {
        Log.i("gchk", "startMainService()");
        sContext.startService(new Intent(sContext, MainService.class));
    }

    public void onCreate() {
        super.onCreate();
        Log.i("gchk", "onCreate()");
        mInstance = this;
        this.mContentResolver = getContentResolver();
        registerBc();
        initScreenBroadcast();
        syncnow = Boolean.valueOf(false);
        if (Tools.getLogin(sContext)) {
            AlarmUtils.setAutoSyncAlarm(sContext);
            CloudSync.autoSyncType = 1;
        }
        isJudgePhoneStepOpen();
    }

    private void isJudgePhoneStepOpen() {
        DayPedometerActivity.isOpen = Boolean.valueOf(Tools.getPhonePedState());
        if (DayPedometerActivity.isOpen.booleanValue()) {
            startService(new Intent(getApplicationContext(), PedometerService.class));
        }
    }

    private void initScreenBroadcast() {
        IntentFilter screenFilter = new IntentFilter();
        screenFilter.addAction("android.intent.action.SCREEN_ON");
        screenFilter.addAction("android.intent.action.SCREEN_OFF");
        screenFilter.addAction("android.intent.action.USER_PRESENT");
        screenFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        registerReceiver(this.mScreenBroadcastReceiver, screenFilter);
    }

    public void onDestroy() {
        super.onDestroy();
        unRegisterBc();
        unregisterReceiver(this.mScreenBroadcastReceiver);
        startMainService();
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    private void registerBc() {
        IntentFilter intentF = new IntentFilter();
        intentF.addAction("com.zhuoyou.plugin.running.sync.personal");
        registerReceiver(this.mSyncPersionalReceiver, intentF);
        IntentFilter intentF1 = new IntentFilter();
        intentF1.addAction("com.zhuoyou.plugin.running.get");
        intentF1.addAction("com.zhuoyou.plugin.running.get.gatt");
        intentF1.addAction("com.zhuoyou.plugin.running.sleep");
        registerReceiver(this.mGetDataReceiver, intentF1);
        IntentFilter intentF2 = new IntentFilter();
        intentF2.addAction("com.tyd.bt.device.battery");
        registerReceiver(this.mGetBatteryReceiver, intentF2);
        IntentFilter intentF3 = new IntentFilter();
        intentF3.addAction("ACTION_GPS_INFO");
        intentF3.addAction("ACTION_PHONE_STEPS");
        intentF3.addAction("ACTION_PHONE_TOTAL_STEPS");
        registerReceiver(this.mGetGPSDateReceiver, intentF3);
    }

    private void unRegisterBc() {
        unregisterReceiver(this.mSyncPersionalReceiver);
        unregisterReceiver(this.mGetDataReceiver);
        unregisterReceiver(this.mGetBatteryReceiver);
        unregisterReceiver(this.mGetGPSDateReceiver);
    }

    public void sendSyncPeronalConfigCallBack() {
        Intent intent1 = new Intent("com.tyd.plugin.receiver.sendmsg");
        intent1.putExtra("plugin_cmd", 117);
        intent1.putExtra("plugin_content", "himan");
        sendBroadcast(intent1);
    }

    public void sendSyncRunningDataCallBack() {
        Intent intent1 = new Intent("com.tyd.plugin.receiver.sendmsg");
        intent1.putExtra("plugin_cmd", 114);
        intent1.putExtra("plugin_content", "himan");
        sendBroadcast(intent1);
    }

    public void syncWithDevice() {
        syncnow = Boolean.valueOf(true);
        if (this.mTempLists.size() > 0) {
            this.mTempLists.clear();
        }
        if (RunningApp.isBLESupport && BleManagerService.getInstance().GetBleConnectState()) {
            sendBroadcast(new Intent(BleManagerService.ACTION_STEP_DATA_READ));
        } else {
            Intent intent = new Intent("com.tyd.plugin.receiver.sendmsg");
            intent.putExtra("plugin_cmd", 115);
            intent.putExtra("plugin_content", "himan");
            sendBroadcast(intent);
            Log.i("hph", "classic 0x73");
        }
        Message msg = new Message();
        msg.what = 1008;
        this.mHandler.sendMessageDelayed(msg, 10000);
    }

    public void syncRunningData() {
        Intent intent = new Intent("com.tyd.plugin.receiver.sendmsg");
        Log.i(DataBaseContants.TABLE_SLEEP, "send 0x70 running to BlueTooth");
        intent.putExtra("plugin_cmd", 112);
        intent.putExtra("plugin_content", "himan");
        sendBroadcast(intent);
        Message msg = new Message();
        msg.what = 1008;
        this.mHandler.sendMessageDelayed(msg, 10000);
    }

    public void syncSleepData() {
        Intent intent = new Intent("com.tyd.plugin.receiver.sendmsg");
        intent.putExtra("plugin_cmd", 128);
        intent.putExtra("plugin_content", "himan");
        sendBroadcast(intent);
        Message msg = new Message();
        msg.what = HANDLE_MSG_NO_SLEEP_MSG;
        this.mHandler.sendMessageDelayed(msg, 2000);
        Log.i(DataBaseContants.TABLE_SLEEP, "send 0x80 sleep to BlueTooth");
    }

    private String getDuration(String start, String end) {
        String sh = start.substring(0, 2);
        String sm = start.substring(2, 4);
        String eh = end.substring(0, 2);
        String em = end.substring(2, 4);
        Date d1 = new Date();
        d1.setHours(Integer.parseInt(sh));
        d1.setMinutes(Integer.parseInt(sm));
        Date d2 = new Date();
        d2.setHours(Integer.parseInt(eh));
        d2.setMinutes(Integer.parseInt(em));
        long min = ((d2.getTime() - d1.getTime()) / 1000) / 60;
        Log.i("gchk", min + "");
        return min + "";
    }

    private String formatRemoteTime(String old_time) {
        return (old_time.substring(0, 2) + ":") + old_time.substring(2, 4);
    }

    private String formatRemoteDate(String old_date) {
        return (((old_date.substring(0, 4) + SocializeConstants.OP_DIVIDER_MINUS) + old_date.substring(4, 6)) + SocializeConstants.OP_DIVIDER_MINUS) + old_date.substring(6, 8);
    }

    private void insertToDatabase() {
        try {
            ArrayList<ContentProviderOperation> operations = new ArrayList();
            Log.i("gchk", "mTempLists size = " + this.mTempLists.size() + System.currentTimeMillis() + "mTempLists = " + this.mTempLists);
            for (int i = 0; i < this.mTempLists.size(); i++) {
                RunningItem item = (RunningItem) this.mTempLists.get(i);
                String data_from = item.getDataFrom();
                String day;
                int steps;
                Cursor c;
                int sync;
                ContentProviderOperation op1;
                if (item.getIsStatistics() == 2) {
                    day = item.getDate();
                    steps = item.getSteps();
                    c = this.mContentResolver.query(DataBaseContants.CONTENT_URI, new String[]{"_id", "steps", DataBaseContants.SYNC_STATE}, "date  = ? AND statistics = ? AND data_from = ? ", new String[]{day, "2", data_from}, null);
                    if (c.getCount() <= 0 || !c.moveToFirst() || c.getLong(c.getColumnIndex("_id")) <= 0) {
                        operations.add(ContentProviderOperation.newInsert(DataBaseContants.CONTENT_URI).withValues(item.toContentValues()).withValue("_id", Long.valueOf(Tools.getPKL())).withYieldAllowed(true).build());
                    } else {
                        sync = c.getInt(c.getColumnIndex(DataBaseContants.SYNC_STATE));
                        if (steps == c.getInt(c.getColumnIndex("steps")) && sync == 1) {
                            operations.add(ContentProviderOperation.newUpdate(DataBaseContants.CONTENT_URI).withSelection("date = ? AND statistics = ? AND data_from = ? ", new String[]{day, "2", data_from}).withValues(item.toContentValues()).withYieldAllowed(true).build());
                        } else {
                            if (sync == 0) {
                                op1 = ContentProviderOperation.newUpdate(DataBaseContants.CONTENT_URI).withSelection("date = ? AND statistics = ? AND data_from = ? ", new String[]{day, "2", data_from}).withValues(item.toContentValues()).withValue(DataBaseContants.SYNC_STATE, Integer.valueOf(0)).withYieldAllowed(true).build();
                            } else {
                                op1 = ContentProviderOperation.newUpdate(DataBaseContants.CONTENT_URI).withSelection("date = ? AND statistics = ? AND data_from = ? ", new String[]{day, "2", data_from}).withValues(item.toContentValues()).withValue(DataBaseContants.SYNC_STATE, Integer.valueOf(2)).withYieldAllowed(true).build();
                            }
                            operations.add(op1);
                        }
                    }
                    c.close();
                } else {
                    day = item.getDate();
                    String start = item.getStartTime();
                    steps = item.getSteps();
                    c = this.mContentResolver.query(DataBaseContants.CONTENT_URI, new String[]{"_id", "steps", DataBaseContants.SYNC_STATE}, "date  = ? AND time_start = ? AND sports_type = ? AND statistics = ? AND data_from = ? ", new String[]{day, start, "0", "0", data_from}, null);
                    if (c.getCount() <= 0 || !c.moveToFirst() || c.getLong(c.getColumnIndex("_id")) <= 0) {
                        operations.add(ContentProviderOperation.newInsert(DataBaseContants.CONTENT_URI).withValues(item.toContentValues()).withValue("_id", Long.valueOf(Tools.getPKL())).withYieldAllowed(true).build());
                    } else {
                        sync = c.getInt(c.getColumnIndex(DataBaseContants.SYNC_STATE));
                        if (steps == c.getInt(c.getColumnIndex("steps")) && sync == 1) {
                            operations.add(ContentProviderOperation.newUpdate(DataBaseContants.CONTENT_URI).withSelection("date = ? AND time_start = ? AND sports_type = ? AND statistics = ? AND data_from = ? ", new String[]{day, start, "0", "0", data_from}).withValues(item.toContentValues()).withYieldAllowed(true).build());
                        } else {
                            if (sync == 0) {
                                op1 = ContentProviderOperation.newUpdate(DataBaseContants.CONTENT_URI).withSelection("date = ? AND time_start = ? AND sports_type = ? AND statistics = ? AND data_from = ? ", new String[]{day, start, "0", "0", data_from}).withValues(item.toContentValues()).withValue(DataBaseContants.SYNC_STATE, Integer.valueOf(0)).withYieldAllowed(true).build();
                            } else {
                                op1 = ContentProviderOperation.newUpdate(DataBaseContants.CONTENT_URI).withSelection("date = ? AND time_start = ? AND sports_type = ? AND statistics = ? AND data_from = ? ", new String[]{day, start, "0", "0", data_from}).withValues(item.toContentValues()).withValue(DataBaseContants.SYNC_STATE, Integer.valueOf(2)).withYieldAllowed(true).build();
                            }
                            operations.add(op1);
                        }
                    }
                    c.close();
                }
            }
            this.mContentResolver.applyBatch(DataBaseContants.AUTHORITY, operations);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e2) {
            e2.printStackTrace();
        }
        checkDataBase();
    }

    public void checkDataBase() {
        try {
            ArrayList<ContentProviderOperation> operations = new ArrayList();
            PersonalConfig config = Tools.getPersonalConfig();
            PersonalGoal goal = Tools.getPersonalGoal();
            String today = Tools.getDate(0);
            String enter_day = today;
            List<String> date_list = Tools.getDateFromDb(sContext);
            if (date_list != null && date_list.size() > 0) {
                enter_day = (String) date_list.get(0);
            }
            int count = Tools.getDayCount(enter_day, today, "yyyy-MM-dd");
            for (int i = 0; i < count; i++) {
                String day = Tools.getDate(enter_day, 0 - i);
                if (!(date_list == null || date_list.size() <= 0 || date_list.indexOf(day) == -1)) {
                    Cursor c = this.mContentResolver.query(DataBaseContants.CONTENT_URI, new String[]{"_id", "steps", DataBaseContants.SYNC_STATE, DataBaseContants.STATISTICS}, "date  = ? ", new String[]{day}, null);
                    if (c.getCount() > 0 && c.moveToFirst()) {
                        int effective_step = 0;
                        int total_step = 0;
                        int statistics_step = 0;
                        Boolean has_statistics = Boolean.valueOf(false);
                        int sync = 0;
                        for (int j = 0; j < c.getCount(); j++) {
                            int step = c.getInt(c.getColumnIndex("steps"));
                            switch (c.getInt(c.getColumnIndex(DataBaseContants.STATISTICS))) {
                                case 0:
                                    effective_step += step;
                                    break;
                                case 1:
                                    has_statistics = Boolean.valueOf(true);
                                    sync = c.getInt(c.getColumnIndex(DataBaseContants.SYNC_STATE));
                                    statistics_step = step;
                                    break;
                                case 2:
                                    total_step += step;
                                    break;
                                default:
                                    break;
                            }
                            c.moveToNext();
                        }
                        Log.i("caixinxin", "day = " + day);
                        Log.i("caixinxin", "effective_step = " + effective_step);
                        Log.i("caixinxin", "statistics_step = " + statistics_step);
                        Log.i("caixinxin", "total_step = " + total_step);
                        Log.i("caixinxin", "has_statistics = " + has_statistics);
                        Log.i("caixinxin", "sync = " + sync);
                        if (effective_step >= total_step) {
                            total_step = effective_step;
                        }
                        if (statistics_step > total_step) {
                            total_step = statistics_step;
                        }
                        int meter = Tools.calcDistance(effective_step + (total_step - effective_step), config.getHeight());
                        int calories = Tools.calcCalories(meter, config.getWeightNum());
                        if (has_statistics.booleanValue()) {
                            if (total_step == statistics_step && sync == 1) {
                                operations.add(ContentProviderOperation.newUpdate(DataBaseContants.CONTENT_URI).withSelection("date = ? AND statistics = ? ", new String[]{day, "1"}).withValue("steps", Integer.valueOf(total_step)).withValue(DataBaseContants.KILOMETER, Integer.valueOf(meter)).withValue(DataBaseContants.CALORIES, Integer.valueOf(calories)).withValue(DataBaseContants.COMPLETE, Integer.valueOf(total_step >= goal.mGoalSteps ? 1 : 0)).withYieldAllowed(true).build());
                            } else {
                                ContentProviderOperation op1;
                                if (sync == 0) {
                                    op1 = ContentProviderOperation.newUpdate(DataBaseContants.CONTENT_URI).withSelection("date = ? AND statistics = ? ", new String[]{day, "1"}).withValue("steps", Integer.valueOf(total_step)).withValue(DataBaseContants.KILOMETER, Integer.valueOf(meter)).withValue(DataBaseContants.CALORIES, Integer.valueOf(calories)).withValue(DataBaseContants.COMPLETE, Integer.valueOf(total_step >= goal.mGoalSteps ? 1 : 0)).withValue(DataBaseContants.SYNC_STATE, Integer.valueOf(0)).withYieldAllowed(true).build();
                                } else {
                                    op1 = ContentProviderOperation.newUpdate(DataBaseContants.CONTENT_URI).withSelection("date = ? AND statistics = ? ", new String[]{day, "1"}).withValue("steps", Integer.valueOf(total_step)).withValue(DataBaseContants.KILOMETER, Integer.valueOf(meter)).withValue(DataBaseContants.CALORIES, Integer.valueOf(calories)).withValue(DataBaseContants.COMPLETE, Integer.valueOf(total_step >= goal.mGoalSteps ? 1 : 0)).withValue(DataBaseContants.SYNC_STATE, Integer.valueOf(2)).withYieldAllowed(true).build();
                                }
                                operations.add(op1);
                            }
                        } else if (total_step > 0) {
                            operations.add(ContentProviderOperation.newInsert(DataBaseContants.CONTENT_URI).withValue("_id", Long.valueOf(Tools.getPKL())).withValue("steps", Integer.valueOf(total_step)).withValue(DataBaseContants.KILOMETER, Integer.valueOf(meter)).withValue(DataBaseContants.CALORIES, Integer.valueOf(calories)).withValue("date", day).withValue(DataBaseContants.SPORTS_TYPE, Integer.valueOf(0)).withValue("type", Integer.valueOf(0)).withValue(DataBaseContants.COMPLETE, Integer.valueOf(total_step >= goal.mGoalSteps ? 1 : 0)).withValue(DataBaseContants.STATISTICS, Integer.valueOf(1)).withValue(DataBaseContants.BMI, Integer.valueOf(goal.mGoalSteps)).withYieldAllowed(true).build());
                        }
                    }
                    c.close();
                }
            }
            this.mContentResolver.applyBatch(DataBaseContants.AUTHORITY, operations);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e2) {
            e2.printStackTrace();
        }
    }
}

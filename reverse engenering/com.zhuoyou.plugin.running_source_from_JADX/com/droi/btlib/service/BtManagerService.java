package com.droi.btlib.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.XmlResourceParser;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat.Builder;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.volley.DefaultRetryPolicy;
import com.droi.btlib.C0687R;
import com.droi.btlib.connection.MapConstants;
import com.droi.btlib.connection.MessageHeader;
import com.droi.btlib.connection.MessageObj;
import com.droi.btlib.connection.NoDataException;
import com.droi.btlib.connection.SmsMessageBody;
import com.droi.btlib.device.DeviceDetail;
import com.droi.btlib.device.GattInfo;
import com.droi.btlib.plugin.CallService;
import com.droi.btlib.plugin.FindPhoneDialog;
import com.droi.btlib.plugin.ReadContactname;
import com.droi.btlib.plugin.RemoteCamera;
import com.droi.btlib.plugin.SmsContentObserver;
import com.droi.btlib.service.BtDevice.ConnectCallback;
import com.mtk.btconnection.LoadJniFunction;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.pixart.alg.PXIALGMOTION;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import no.nordicsemi.android.dfu.DfuBaseService;
import org.xmlpull.v1.XmlPullParserException;

public class BtManagerService extends Service {
    private static boolean BLE_AUTIO_HAND = false;
    public static final int BLE_AUTO_CONNECT_DELAY = 256;
    public static final int BLE_AUTO_CONNECT_OPERATION = 257;
    public static final int CLASSIC_BASE = 32;
    public static final int CLASSIC_CAMERA_CAPTURE = 80;
    public static final int CLASSIC_CLOSE_CAMERA = 83;
    public static final int CLASSIC_CMD_ANTILOST_DIALOG_OFF = 21;
    public static final int CLASSIC_CMD_ANTILOST_DIALOG_ON = 20;
    public static final int CLASSIC_CMD_ANTILOST_OFF = 19;
    public static final int CLASSIC_CMD_ANTILOST_ON = 18;
    public static final int CLASSIC_CMD_DELETE_MUSIC_LIST = 161;
    public static final int CLASSIC_CMD_FIND_PHONE_CLOSE = 17;
    public static final int CLASSIC_CMD_FIND_PHONE_START = 16;
    public static final int CLASSIC_CMD_GET_ALARM_REMIND = 98;
    public static final int CLASSIC_CMD_GET_BATTERY = 3;
    public static final int CLASSIC_CMD_GET_FIRMWARE_VERSION = 5;
    public static final int CLASSIC_CMD_GET_HARDWARE_VERSION = 97;
    public static final int CLASSIC_CMD_GET_MUSIC_LIST = 160;
    public static final int CLASSIC_CMD_GET_SLEEP_INFO = 128;
    public static final int CLASSIC_CMD_GET_STEP = 112;
    public static final int CLASSIC_CMD_SEND_RELEASE_PAIR = 101;
    public static final int CLASSIC_CMD_SET_ALARM = 149;
    public static final int CLASSIC_CMD_SET_ALARM_REMIND = 96;
    public static final int CLASSIC_CMD_SET_MUSIC_LIST = 163;
    public static final int CLASSIC_CMD_SET_PLAY_MODE = 162;
    public static final int CLASSIC_DELETE_MUSIC_LIST = 161;
    public static final int CLASSIC_GET_ALARM_REMIND = 98;
    public static final int CLASSIC_GET_BATTERY = 3;
    public static final int CLASSIC_GET_FIRMWARE_VERSION = 5;
    public static final int CLASSIC_GET_MUSIC_LIST = 160;
    public static final int CLASSIC_HARDWARE_VERSION = 97;
    public static final int CLASSIC_INCOMING_NAME = 32;
    public static final int CLASSIC_OPEN_CAMERA = 82;
    public static final int CLASSIC_REMOTE_READ_SMS = 64;
    public static final int CLASSIC_SYNC_PERSONAL_DATA = 116;
    public static final int CLASSIC_SYNC_SLEEP_MSG = 129;
    public static final int CLASSIC_SYNC_SPORT_DATA = 113;
    public static final int CLASSIC_SYNC_TIME = 2;
    public static final int CLASSIC_VISITING_CARD = 48;
    private static final Handler CMD_HANDLER = new Handler() {

        class C07181 implements Runnable {
            C07181() {
            }

            public void run() {
                if (BtManagerService.mConnectCallbackList.size() != 0) {
                    Iterator it = BtManagerService.mConnectCallbackList.iterator();
                    while (it.hasNext()) {
                        ConnectCallback callback = (ConnectCallback) it.next();
                        if (callback != null) {
                            callback.battery(BtManagerService.battery);
                        }
                    }
                }
            }
        }

        class C07192 implements Runnable {
            C07192() {
            }

            public void run() {
                if (BtManagerService.mGetTotalStepCallback != null) {
                    BtManagerService.isNotifyTimeOut = false;
                    BtManagerService.mGetTotalStepCallback.end(END_STATE.EMPTY);
                    BtManagerService.gotoNextTask();
                }
            }
        }

        class C07203 implements Runnable {
            C07203() {
            }

            public void run() {
                if (BtManagerService.mGetSubStepsCallback != null) {
                    BtManagerService.isNotifyTimeOut = false;
                    BtManagerService.mGetSubStepsCallback.end(END_STATE.EMPTY);
                    BtManagerService.gotoNextTask();
                }
            }
        }

        class C07225 implements Runnable {
            C07225() {
            }

            public void run() {
                BtManagerService.isNotifyTimeOut = false;
                if (BtManagerService.mGetTotalStepCallback != null && BtManagerService.runningTask.getTaskCode() == BtManagerService.TASK_CLASSIC_GET_TOTAL_STEP) {
                    BtManagerService.mGetTotalStepCallback.end(END_STATE.COMPLETE);
                }
                if (BtManagerService.mGetSubStepsCallback != null && BtManagerService.runningTask.getTaskCode() == BtManagerService.TASK_CLASSIC_GET_SUB_STEP) {
                    BtManagerService.mGetSubStepsCallback.end(END_STATE.COMPLETE);
                }
                BtManagerService.gotoNextTask();
            }
        }

        class C07236 implements Runnable {
            C07236() {
            }

            public void run() {
                BtManagerService.isNotifyTimeOut = true;
                BtManagerService.mGetSleepInfoCallback.end(END_STATE.EMPTY);
                BtManagerService.gotoNextTask();
            }
        }

        class C07247 implements Runnable {
            C07247() {
            }

            public void run() {
                BtManagerService.isNotifyTimeOut = true;
                BtManagerService.mGetSleepInfoCallback.end(END_STATE.COMPLETE);
                BtManagerService.gotoNextTask();
            }
        }

        class C07258 implements Runnable {
            C07258() {
            }

            public void run() {
                BtManagerService.mGetMusicListCallback.success(BtManagerService.musicArray);
            }
        }

        public void handleMessage(Message msg) {
            int index;
            char[] c_ucs2 = (char[]) msg.obj;
            char[] c_platform = new char[20];
            int i = 0;
            int index2 = 1;
            while (i < 20) {
                index = index2 + 1;
                c_platform[i] = c_ucs2[index2];
                i++;
                index2 = index;
            }
            char[] c_verno = new char[8];
            i = 0;
            while (i < 8) {
                index = index2 + 1;
                c_verno[i] = c_ucs2[index2];
                i++;
                index2 = index;
            }
            c_tag = new char[4];
            index = index2 + 1;
            c_tag[0] = c_ucs2[index2];
            index2 = index + 1;
            c_tag[1] = c_ucs2[index];
            index = index2 + 1;
            c_tag[2] = c_ucs2[index2];
            index2 = index + 1;
            c_tag[3] = c_ucs2[index];
            char[] c_msg = new char[(c_ucs2.length - index2)];
            System.arraycopy(c_ucs2, index2, c_msg, 0, c_ucs2.length - index2);
            String str = new String(c_msg);
            Log.i("chenxin1", "CMD_HANDLER:" + msg.what + " tag:" + c_tag[0] + " " + c_tag[1] + " " + c_tag[2] + " " + c_tag[3] + " s_utf8:" + str);
            Intent it = new Intent();
            switch (msg.what) {
                case 3:
                    BtManagerService.gotoNextTask();
                    int total_number = c_tag[2] - 32;
                    int battery_number = c_tag[3] - 32;
                    int battery_lv = c_tag[1] - 32;
                    int state = c_tag[0] - 32;
                    BtManagerService.battery = 0;
                    if (state == 1) {
                        BtManagerService.battery = 101;
                    } else if (state == 2) {
                        BtManagerService.battery = 102;
                    } else if (battery_lv == 255 || battery_number == 0) {
                        BtManagerService.battery = 0;
                    } else if (total_number == 3) {
                        switch (battery_number) {
                            case 1:
                                BtManagerService.battery = 33;
                                break;
                            case 2:
                                BtManagerService.battery = 66;
                                break;
                            case 3:
                                BtManagerService.battery = 100;
                                break;
                            default:
                                break;
                        }
                    } else {
                        BtManagerService.battery = battery_lv;
                    }
                    Log.i(BtManagerService.TAG, "battery:" + BtManagerService.battery + " state:" + state + " total:" + total_number + " batLv:" + battery_lv + " batNum:" + battery_number);
                    Util.saveBattery(BtManagerService.battery);
                    BtManagerService.mHandler.post(new C07181());
                    return;
                case 5:
                    BtManagerService.gotoNextTask();
                    String[] version = str.split("\\|");
                    Util.saveVersion(version[0] + "_" + version[1]);
                    return;
                case 16:
                    if (!FindPhoneDialog.ifDialogShow()) {
                        it.setClass(BtManagerService.mContext, FindPhoneDialog.class);
                        it.setFlags(268435456);
                        it.putExtra("phone_lost_message", BtManagerService.mContext.getString(C0687R.string.bt_phone_found));
                        BtManagerService.mContext.startActivity(it);
                        return;
                    }
                    return;
                case 17:
                    FindPhoneDialog.clostSysDialog();
                    return;
                case 18:
                    BtManagerService.saveAntiLost(true);
                    return;
                case 19:
                    BtManagerService.saveAntiLost(false);
                    return;
                case 20:
                    if (!FindPhoneDialog.ifDialogShow() && BtManagerService.getDisconnectRemind()) {
                        it.setClass(BtManagerService.mContext, FindPhoneDialog.class);
                        it.setFlags(268435456);
                        it.putExtra("phone_lost_message", BtManagerService.mContext.getString(C0687R.string.phone_lost));
                        BtManagerService.mContext.startActivity(it);
                        return;
                    }
                    return;
                case 21:
                    FindPhoneDialog.clostSysDialog();
                    return;
                case 80:
                    it.setAction(RemoteCamera.mActionCapture);
                    BtManagerService.mContext.sendBroadcast(it);
                    return;
                case 82:
                    it.setClass(BtManagerService.mContext, RemoteCamera.class);
                    it.setFlags(268435456);
                    it.putExtra("isCmd", true);
                    BtManagerService.mContext.startActivity(it);
                    return;
                case 83:
                    it.setAction(RemoteCamera.mActionExit);
                    BtManagerService.mContext.sendBroadcast(it);
                    return;
                case 97:
                    BtManagerService.gotoNextTask();
                    Util.saveHardWare(str);
                    return;
                case 98:
                    BtManagerService.gotoNextTask();
                    if (BtManagerService.mGetAlarmRemindCallback != null) {
                        BtManagerService.isNotifyTimeOut = false;
                        String[] result = str.split("\\|");
                        boolean[] reminds = new boolean[result.length];
                        for (i = 0; i < result.length; i++) {
                            reminds[i] = result[i].equals("1");
                        }
                        BtManagerService.mGetAlarmRemindCallback.success(reminds);
                        BtManagerService.mGetAlarmRemindCallback.end(END_STATE.COMPLETE);
                        return;
                    }
                    return;
                case BtManagerService.CLASSIC_SYNC_SPORT_DATA /*113*/:
                    BtManagerService.refreshTaskLoop();
                    int curr_index = c_tag[0] - 32;
                    int total_index = c_tag[1] - 32;
                    String[] s = str.split("\\|");
                    Log.i("chenxinyx", "classic get sport:" + str + " curr_index:" + curr_index + " total_index:" + total_index);
                    if (TextUtils.isEmpty(str) && BtManagerService.mGetTotalStepCallback != null && BtManagerService.runningTask.getTaskCode() == BtManagerService.TASK_CLASSIC_GET_TOTAL_STEP) {
                        BtManagerService.mHandler.post(new C07192());
                        return;
                    } else if (TextUtils.isEmpty(str) && BtManagerService.mGetSubStepsCallback != null && BtManagerService.runningTask.getTaskCode() == BtManagerService.TASK_CLASSIC_GET_SUB_STEP) {
                        BtManagerService.mHandler.post(new C07203());
                        return;
                    } else if (curr_index == total_index) {
                        BtManagerService.isNotifyTimeOut = false;
                        number = s.length / 2;
                        for (i = 0; i < number; i++) {
                            final int classic_step = Integer.parseInt(s[(i * 2) + 0]);
                            final String classic_date = Util.formatRemoteDate(s[(i * 2) + 1]);
                            Log.i("chenxinyx", "classic_step:" + classic_step + " classic_date:" + classic_date);
                            BtManagerService.mHandler.post(new Runnable() {
                                public void run() {
                                    if (BtManagerService.mGetTotalStepCallback != null && BtManagerService.runningTask.getTaskCode() == BtManagerService.TASK_CLASSIC_GET_TOTAL_STEP) {
                                        BtManagerService.mGetTotalStepCallback.success(classic_step, classic_date);
                                    }
                                }
                            });
                        }
                        BtManagerService.mHandler.post(new C07225());
                        return;
                    } else {
                        number = s.length / 4;
                        for (i = 0; i < number; i++) {
                            if (BtManagerService.mGetSubStepsCallback != null && BtManagerService.runningTask.getTaskCode() == BtManagerService.TASK_CLASSIC_GET_SUB_STEP) {
                                SubStep subStep = new SubStep();
                                subStep.setStep(Integer.parseInt(s[(i * 4) + 0]));
                                subStep.setStartTime(Util.formatRemoteDate(s[(i * 4) + 1]) + " " + s[(i * 4) + 2] + "00");
                                subStep.setEndTime(Util.formatRemoteDate(s[(i * 4) + 1]) + " " + s[(i * 4) + 3] + "00");
                                Log.i("chenxinyz", "subStep:" + subStep);
                                BtManagerService.mGetSubStepsCallback.success(subStep);
                            }
                        }
                        return;
                    }
                case BtManagerService.CLASSIC_SYNC_SLEEP_MSG /*129*/:
                    BtManagerService.refreshTaskLoop();
                    int curr = c_tag[0] - 32;
                    int total = c_tag[1] - 32;
                    String[] str2 = str.split("\\|");
                    if (total > 0) {
                        String endTime;
                        String date = str2[0];
                        String startTime = date + str2[2] + "00";
                        if (Integer.parseInt(str2[2]) <= Integer.parseInt(str2[str2.length - 1])) {
                            endTime = date + str2[str2.length - 1] + "00";
                        } else {
                            endTime = Util.dealSleepDateAdd(date, 1) + str2[str2.length - 1] + "00";
                        }
                        String details = str.substring(str2[0].length() + 1);
                        SleepInfo info = new SleepInfo();
                        info.setStartTime(Long.parseLong(startTime));
                        info.setEndTime(Long.parseLong(endTime));
                        info.setSleepDetail(details);
                        Log.i(BtManagerService.TAG, "sleepInfo start:" + startTime + " end:" + endTime + " details:" + details);
                        if (BtManagerService.mGetSleepInfoCallback != null) {
                            BtManagerService.mGetSleepInfoCallback.success(info);
                        }
                        if (total == curr && BtManagerService.mGetSleepInfoCallback != null) {
                            BtManagerService.mHandler.post(new C07247());
                            return;
                        }
                        return;
                    } else if (BtManagerService.mGetSleepInfoCallback != null) {
                        BtManagerService.mHandler.post(new C07236());
                        return;
                    } else {
                        return;
                    }
                case 160:
                    if (c_tag[0] == 1025) {
                        BtManagerService.isNotifyTimeOut = false;
                        BtManagerService.mGetMusicListCallback.success(null);
                        BtManagerService.gotoNextTask();
                        return;
                    }
                    BtManagerService.refreshTaskLoop();
                    if (c_tag[2] == c_tag[3]) {
                        BtManagerService.isNotifyTimeOut = false;
                        BtManagerService.musicStr = BtManagerService.musicStr + str;
                        String[] musicList = BtManagerService.musicStr.split("\n");
                        BtManagerService.musicStr = new String();
                        BtManagerService.musicArray = new ArrayList();
                        Log.i(BtManagerService.TAG, "music num:" + BtManagerService.musicArray.size());
                        for (String musicName : musicList) {
                            if (!TextUtils.isEmpty(musicName)) {
                                Log.i(BtManagerService.TAG, "music name:" + musicName);
                                BtManagerService.musicArray.add(musicName);
                            }
                        }
                        if (BtManagerService.mGetMusicListCallback != null) {
                            BtManagerService.mHandler.post(new C07258());
                            return;
                        }
                        return;
                    }
                    BtManagerService.musicStr = BtManagerService.musicStr + str;
                    return;
                case 161:
                    boolean deleteResult = c_tag[0] == '!';
                    if (BtManagerService.mDeleteMusicListCallback != null) {
                        final boolean z = deleteResult;
                        BtManagerService.mHandler.post(new Runnable() {
                            public void run() {
                                if (z) {
                                    BtManagerService.mDeleteMusicListCallback.success();
                                } else {
                                    BtManagerService.mDeleteMusicListCallback.fail();
                                }
                            }
                        });
                        BtManagerService.isNotifyTimeOut = false;
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    private static final int END_CALL = 3;
    private static final int FIND_PHONE_END = 2;
    private static final int FIND_PHONE_START = 1;
    protected static final int NO_TASK = -1;
    public static int PLAY_MODE_NORMAL = 0;
    public static int PLAY_MODE_RANDOM = 1;
    private static final int REQUEST_ENABLE_BT = 87;
    private static final String TAG = "chenxin";
    private static final String TAG_ALARM = "alarm";
    private static final String TAG_ANTILOST = "antilost";
    private static final String TAG_CALL_REMIND = "callremind";
    private static final String TAG_DEVICE = "device";
    private static final String TAG_DISPLAY_SETTING = "displaysetting";
    private static final String TAG_DISTURBANCE_MODE = "disturbancemode";
    private static final String TAG_FIND_BRACELET = "findbracelet";
    private static final String TAG_FIRMWARE = "firmware";
    private static final String TAG_HARDWARE = "hardware";
    private static final String TAG_HEART = "heart";
    private static final String TAG_NAME = "name";
    private static final String TAG_PUSH_REMIND = "pushremind";
    private static final String TAG_SEDENTARY_REMIND = "sedentaryremind";
    private static final String TAG_SLEEP = "sleep";
    private static final String TAG_SMS_REMIND = "smsremind";
    private static final String TAG_SPORT_TARGET = "sporttarget";
    private static final String TAG_TYPE = "type";
    private static final String TAG_UPDATE_TYPE = "updatetype";
    private static final String TAG_WRIST_LIFT = "wristlift";
    public static final int TASK_ADD = 61440;
    private static final int TASK_BATTERY_NOTIFY = 4102;
    public static final int TASK_CALL_END = 4113;
    private static final int TASK_CLASSIC_CLOSE_MASK = 12290;
    private static final int TASK_CLASSIC_DELETE_MUSICLIST = 12433;
    private static final int TASK_CLASSIC_DELETE_MUSICLIST_CLOSE = 12434;
    private static final int TASK_CLASSIC_GET_ALARM_REMIND = 12401;
    private static final int TASK_CLASSIC_GET_ALARM_REMIND_CLOSE = 12402;
    private static final int TASK_CLASSIC_GET_BATTERY = 12337;
    private static final int TASK_CLASSIC_GET_BATTERY_CLOSE = 12338;
    private static final int TASK_CLASSIC_GET_FIRMWARE_VERSION = 12369;
    private static final int TASK_CLASSIC_GET_HARDWARE_VERSION = 12353;
    private static final int TASK_CLASSIC_GET_HARDWARE_VERSION_CLOSE = 12354;
    private static final int TASK_CLASSIC_GET_MUSICLIST = 12417;
    private static final int TASK_CLASSIC_GET_MUSICLIST_CLOSE = 12418;
    private static final int TASK_CLASSIC_GET_SLEEP_INFO = 12545;
    private static final int TASK_CLASSIC_GET_SLEEP_INFO_CLOSE = 12546;
    private static final int TASK_CLASSIC_GET_SUB_STEP = 12321;
    private static final int TASK_CLASSIC_GET_SUB_STEP_CLOSE = 12322;
    private static final int TASK_CLASSIC_GET_TOTAL_STEP = 12305;
    private static final int TASK_CLASSIC_GET_TOTAL_STEP_CLOSE = 12306;
    private static final int TASK_CLASSIC_MASK = 12289;
    private static final int TASK_CLASSIC_SET_ALARM_REMIND = 12385;
    private static int TASK_CLASSIC_WAIT = 5000;
    private static final int TASK_CONNECT_SUCCESS = 4101;
    private static final int TASK_FIND_PHONE_NOTIFY = 4104;
    private static final int TASK_GET_BATTERY = 4097;
    private static final int TASK_GET_HARDWARE = 4100;
    private static final int TASK_GET_HEART_INFO = 8257;
    private static final int TASK_GET_HEART_INFO_CLOSE = 8258;
    private static final int TASK_GET_MASK = 4096;
    private static final int TASK_GET_NAME = 4098;
    private static final int TASK_GET_SLEEP_INFO = 8225;
    private static final int TASK_GET_SLEEP_INFO_CLOSE = 8226;
    private static final int TASK_GET_SUB_STEPS = 8241;
    private static final int TASK_GET_SUB_STEPS_CLOSE = 8242;
    private static final int TASK_GET_TOTAL_STEP = 8209;
    private static final int TASK_GET_TOTAL_STEP_CLOSE = 8210;
    private static final int TASK_GET_VERSION = 4099;
    public static final int TASK_MISS_CALL = 4112;
    public static final int TASK_NEW_CALL = 4114;
    public static final int TASK_NEW_MESSAGE = 4117;
    public static final int TASK_NEW_SMS = 4116;
    private static final int TASK_NEXT = 61441;
    private static final int TASK_NOTIFY_CLOSE_MASK = 8194;
    private static final int TASK_NOTIFY_MASK = 8193;
    private static int TASK_NOTIFY_WAIT = DefaultRetryPolicy.DEFAULT_TIMEOUT_MS;
    private static final int TASK_SET_DEVICE_TIME = 4103;
    private static final int TASK_SET_NOT_VIBRATION = 4115;
    private static final int TASK_TAKE_PICTURE_NOTIFY = 4105;
    private static int TASK_WAIT = 1000;
    private static int TIME_GET_HEART = BaseImageDownloader.DEFAULT_HTTP_READ_TIMEOUT;
    private static int TIME_OUT = 60000;
    public static final String TYD_NUMBER = "99999999999";
    private static int battery = 0;
    private static int batteryLevel;
    private static String binFilePath;
    private static int bleUpdateTest = 0;
    private static int curr_index;
    private static String datFilePath;
    private static boolean[] deleteList;
    private static int heartDate = 0;
    private static boolean ifinHeart = false;
    protected static boolean isConnecting = false;
    private static boolean isNotifyTimeOut = true;
    private static boolean isScanning = false;
    public static ArrayList<DeviceDetail> mAllDeviceDetailList = new ArrayList();
    public static ArrayList<DeviceDetail> mBleDeviceDetailList = new ArrayList();
    private static List<BluetoothGattService> mBleServiceList = new ArrayList();
    private static BluetoothAdapter mBluetoothAdapter = null;
    private static BluetoothGatt mBluetoothGatt = null;
    public static BluetoothManager mBluetoothManager;
    private static BtDevice mBtDevice = null;
    private static BroadcastReceiver mBtReceiver = new BroadcastReceiver() {

        class C07061 implements Runnable {
            C07061() {
            }

            public void run() {
                BtManagerService.mScanCallback.scan(BtManagerService.mScanBleDeviceList);
            }
        }

        class C07072 implements Runnable {
            C07072() {
            }

            public void run() {
                BtManagerService.mBtDevice.setConnectState(CONNECT_STATE.DISCONNECTED);
                BtManagerService.mBluetoothGatt.disconnect();
            }
        }

        class C07083 implements Runnable {
            C07083() {
            }

            public void run() {
                Intent service = new Intent(BtManagerService.mContext, DfuBaseService.class);
                service.putExtra(DfuBaseService.EXTRA_DEVICE_ADDRESS, BtManagerService.mBtDevice.getMacAddress());
                service.putExtra(DfuBaseService.EXTRA_FILE_MIME_TYPE, DfuBaseService.MIME_TYPE_OCTET_STREAM);
                service.putExtra(DfuBaseService.EXTRA_FILE_TYPE, 4);
                service.putExtra(DfuBaseService.EXTRA_FILE_PATH, BtManagerService.binFilePath);
                if (!TextUtils.isEmpty(BtManagerService.datFilePath)) {
                    service.putExtra(DfuBaseService.EXTRA_INIT_FILE_PATH, BtManagerService.datFilePath);
                }
                BtManagerService.mContext.startService(service);
            }
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.bluetooth.device.action.FOUND")) {
                BluetoothDevice btDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (btDevice != null) {
                    Log.i(BtManagerService.TAG, "Found device:" + btDevice.getName() + " mac:" + btDevice.getAddress() + " type:" + btDevice.getType());
                    Iterator it = BtManagerService.mAllDeviceDetailList.iterator();
                    while (it.hasNext()) {
                        DeviceDetail detail = (DeviceDetail) it.next();
                        if (btDevice.getName() != null && btDevice.getName().equals(detail.getName()) && btDevice.getType() == detail.getBtType() && BtManagerService.mScanCallback != null) {
                            DeviceInfo deviceInfo = new DeviceInfo(btDevice);
                            short rssi = (short) 0;
                            if (btDevice.getBondState() != 12) {
                                rssi = intent.getExtras().getShort("android.bluetooth.device.extra.RSSI");
                            }
                            deviceInfo.setRssi(rssi);
                            deviceInfo.setDetail(detail);
                            Iterator it2 = BtManagerService.mScanBleDeviceList.iterator();
                            while (it2.hasNext()) {
                                if (((DeviceInfo) it2.next()).getMacAddress().equals(deviceInfo.getMacAddress())) {
                                    return;
                                }
                            }
                            BtManagerService.mScanBleDeviceList.add(deviceInfo);
                            BtManagerService.mHandler.post(new C07061());
                        }
                    }
                }
            } else if (intent.getAction().equals("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED")) {
                int connState = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            } else if (intent.getAction().equals(DfuBaseService.BROADCAST_PROGRESS)) {
                int progress = intent.getIntExtra(DfuBaseService.EXTRA_DATA, 0);
                Log.i(BtManagerService.TAG, "BROADCAST_PROGRESS:" + progress);
                if (BtManagerService.mUpdateBleCallback == null) {
                    return;
                }
                if (progress == -6) {
                    BtManagerService.mUpdateBleCallback.progress(100);
                    BtManagerService.mUpdateBleCallback.success();
                    Util.saveVersion(null);
                    Util.saveHardWare(null);
                    BtManagerService.mContext.stopService(new Intent(BtManagerService.mContext, DfuBaseService.class));
                    if (BtManagerService.mBluetoothGatt != null) {
                        BtManagerService.refreshDeviceCache(BtManagerService.mBluetoothGatt, true);
                    }
                } else if (progress >= 0) {
                    BtManagerService.mUpdateBleCallback.progress(progress);
                }
            } else if (intent.getAction().equals(DfuBaseService.BROADCAST_ERROR)) {
                Log.i(BtManagerService.TAG, "BROADCAST_ERROR:");
                if (BtManagerService.bleUpdateTest == 0) {
                    BtManagerService.access$3308();
                    BtManagerService.mContext.stopService(new Intent(BtManagerService.mContext, DfuBaseService.class));
                    BtManagerService.mHandler.postDelayed(new C07072(), 1000);
                    BtManagerService.mHandler.postDelayed(new C07083(), 10000);
                    return;
                }
                BtManagerService.mUpdateBleCallback.fail(3);
                BtManagerService.mContext.stopService(new Intent(BtManagerService.mContext, DfuBaseService.class));
            } else if (intent.getAction().equals(FindPhoneDialog.ACTION_FIND_PHONE) && BtManagerService.mBluetoothManager != null) {
                BtManagerService.mBluetoothManager.sendCustomCmd(17, "found");
            }
        }
    };
    private static CallService mCallService;
    public static ArrayList<DeviceDetail> mClassicDeviceDetailList = new ArrayList();
    private static CopyOnWriteArrayList<ConnectCallback> mConnectCallbackList = new CopyOnWriteArrayList();
    private static Context mContext = null;
    private static DeleteMusicListCallback mDeleteMusicListCallback = null;
    private static BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        private Runnable findphoneRunnable = new Runnable() {
            public void run() {
                if (!FindPhoneDialog.ifDialogShow()) {
                    Intent it = new Intent();
                    it.setClass(BtManagerService.mContext, FindPhoneDialog.class);
                    it.setFlags(268435456);
                    it.putExtra("phone_lost_message", BtManagerService.mContext.getString(C0687R.string.bt_phone_found));
                    BtManagerService.mContext.startActivity(it);
                    Log.i("hph", "startActivity FindPhoneDialog");
                }
            }
        };

        class C07102 implements Runnable {
            C07102() {
            }

            public void run() {
                if (BtManagerService.mGetTotalStepCallback != null) {
                    BtManagerService.mGetTotalStepCallback.end(END_STATE.COMPLETE);
                }
            }
        }

        class C07113 implements Runnable {
            C07113() {
            }

            public void run() {
                if (BtManagerService.mConnectCallbackList.size() != 0) {
                    Iterator it = BtManagerService.mConnectCallbackList.iterator();
                    while (it.hasNext()) {
                        ConnectCallback callback = (ConnectCallback) it.next();
                        if (!(callback == null || BtManagerService.mBtDevice == null)) {
                            callback.battery(BtManagerService.batteryLevel);
                        }
                    }
                }
            }
        }

        class C07124 implements Runnable {
            C07124() {
            }

            public void run() {
                if (BtManagerService.mGetSleepInfoCallback != null) {
                    BtManagerService.mGetSleepInfoCallback.end(END_STATE.EMPTY);
                }
            }
        }

        class C07146 implements Runnable {
            C07146() {
            }

            public void run() {
                if (BtManagerService.mGetSleepInfoCallback != null) {
                    BtManagerService.mGetSleepInfoCallback.end(END_STATE.COMPLETE);
                }
            }
        }

        class C07168 implements Runnable {
            C07168() {
            }

            public void run() {
                if (BtManagerService.mGetSubStepsCallback != null) {
                    BtManagerService.mGetSubStepsCallback.end(END_STATE.EMPTY);
                }
            }
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            Log.i(BtManagerService.TAG, "onCharacteristicChanged:" + characteristic.getUuid());
            byte[] data;
            int curr_index;
            int totle_number;
            int step;
            final int i;
            if (GattInfo.TOTAL_STEPS_MEASUREMENT.equals(characteristic.getUuid())) {
                BtManagerService.refreshTaskLoop();
                data = characteristic.getValue();
                StringBuilder content = new StringBuilder();
                if (data != null) {
                    curr_index = data[0] & 255;
                    totle_number = data[1] & 255;
                    step = ((((data[2] & 255) << 24) | ((data[3] & 255) << 16)) | ((data[4] & 255) << 8)) | (data[5] & 255);
                    final String date = Util.transformLongTime2StringFormat(((long) (((((data[6] & 255) << 24) | ((data[7] & 255) << 16)) | ((data[8] & 255) << 8)) | (data[9] & 255))) + 1262275200).substring(0, 10);
                    Log.i(BtManagerService.TAG, MapConstants.DATE + date);
                    Log.i(BtManagerService.TAG, "step" + step);
                    i = step;
                    BtManagerService.mHandler.post(new Runnable() {
                        public void run() {
                            if (BtManagerService.mGetTotalStepCallback != null) {
                                BtManagerService.mGetTotalStepCallback.success(i, date);
                            }
                        }
                    });
                    if (curr_index == totle_number) {
                        BtManagerService.isNotifyTimeOut = false;
                        BtManagerService.gotoNextTask();
                        BtManagerService.mHandler.post(new C07102());
                    }
                }
            } else if (GattInfo.BATTERY_LEVEL.equals(characteristic.getUuid())) {
                BtManagerService.batteryLevel = characteristic.getValue()[0];
                Util.saveBattery(BtManagerService.batteryLevel);
                Log.i(BtManagerService.TAG, "onNotify get battery:" + BtManagerService.batteryLevel);
                BtManagerService.mHandler.post(new C07113());
                BtManagerService.gotoNextTask();
            } else if (GattInfo.SLEEP_INFO_CHAR.equals(characteristic.getUuid())) {
                BtManagerService.refreshTaskLoop();
                byte[] sleepData = characteristic.getValue();
                if (sleepData != null && sleepData.length == 20) {
                    if (BtManagerService.mSleepInfoPart == 1) {
                        BtManagerService.curr_index = sleepData[0] & 255;
                        BtManagerService.totle_number = sleepData[1] & 255;
                        BtManagerService.mSleepInfoPart = 2;
                        for (i = 0; i < 20; i++) {
                            BtManagerService.sleepInfoByte[i] = sleepData[i];
                        }
                    } else if (BtManagerService.mSleepInfoPart == 2) {
                        BtManagerService.mSleepInfoPart = 1;
                        for (i = 0; i < 20; i++) {
                            BtManagerService.sleepInfoByte[i + 20] = sleepData[i];
                        }
                        Log.i(BtManagerService.TAG, "sleep chekc: curr_index:" + BtManagerService.curr_index + " totle_number:" + BtManagerService.totle_number);
                        if (BtManagerService.sleepInfoByte[0] == (byte) -1) {
                            BtManagerService.isNotifyTimeOut = false;
                            BtManagerService.gotoNextTask();
                            Log.i(BtManagerService.TAG, "sleep data empty");
                            BtManagerService.mHandler.post(new C07124());
                            return;
                        }
                        final SleepInfo dealSleepBytes = Util.dealSleepBytes(BtManagerService.sleepInfoByte);
                        BtManagerService.mHandler.post(new Runnable() {
                            public void run() {
                                if (BtManagerService.mGetSleepInfoCallback != null) {
                                    BtManagerService.mGetSleepInfoCallback.success(dealSleepBytes);
                                }
                            }
                        });
                        if (BtManagerService.curr_index == BtManagerService.totle_number) {
                            BtManagerService.isNotifyTimeOut = false;
                            BtManagerService.gotoNextTask();
                            BtManagerService.mHandler.post(new C07146());
                        }
                    }
                }
            } else if (GattInfo.FIND_PHONE_MEASUREMENT.equals(characteristic.getUuid())) {
                BtManagerService.refreshTaskLoop();
                type = characteristic.getValue()[0] & 255;
                Log.i(BtManagerService.TAG, "heart type=" + type);
                if (type == 1 && !FindPhoneDialog.ifDialogShow()) {
                    BtManagerService.mHandler.removeCallbacks(this.findphoneRunnable);
                    BtManagerService.mHandler.postDelayed(this.findphoneRunnable, 200);
                } else if (type == 2) {
                    FindPhoneDialog.clostSysDialog();
                } else if (type == 3) {
                    Util.endPhone();
                } else if (type > 50 && BtManagerService.ifinHeart) {
                    BtManagerService.isNotifyTimeOut = false;
                    BtManagerService.ifinHeart = false;
                    i = type;
                    BtManagerService.mHandler.post(new Runnable() {
                        public void run() {
                            if (BtManagerService.mGetHeartCallback != null) {
                                BtManagerService.mGetHeartCallback.success(i);
                            }
                        }
                    });
                    BtManagerService.gotoNextTask();
                }
            } else if (GattInfo.SEGMENT_STEPS_MEASUREMENT.equals(characteristic.getUuid())) {
                BtManagerService.refreshTaskLoop();
                Log.i(BtManagerService.TAG, "get sub step");
                data = characteristic.getValue();
                boolean isEmptyMsg = false;
                if (data != null) {
                    if (data[0] == (byte) -1 && data[13] == (byte) -1) {
                        isEmptyMsg = true;
                        for (i = 0; i < 12; i++) {
                            if (data[i + 1] != (byte) 0) {
                                isEmptyMsg = false;
                                break;
                            }
                        }
                        if (isEmptyMsg) {
                            Log.i(BtManagerService.TAG, "sub step empty");
                            BtManagerService.isNotifyTimeOut = false;
                            BtManagerService.gotoNextTask();
                            BtManagerService.mHandler.post(new C07168());
                            return;
                        }
                    }
                    if (!isEmptyMsg) {
                        curr_index = data[0] & 255;
                        totle_number = data[1] & 255;
                        step = ((((data[2] & 255) << 24) | ((data[3] & 255) << 16)) | ((data[4] & 255) << 8)) | (data[5] & 255);
                        long start = ((long) (((((data[6] & 255) << 24) | ((data[7] & 255) << 16)) | ((data[8] & 255) << 8)) | (data[9] & 255))) + 1262275200;
                        long end = ((long) (((((data[10] & 255) << 24) | ((data[11] & 255) << 16)) | ((data[12] & 255) << 8)) | (data[13] & 255))) + 1262275200;
                        SubStep subStep = new SubStep();
                        String startTime = Util.transformLongTime2StringFormat(start);
                        String endTime = Util.transformLongTime2StringFormat(end);
                        subStep.setStartTime(startTime);
                        subStep.setEndTime(endTime);
                        subStep.setStep(step);
                        Log.i(BtManagerService.TAG, "step:" + step);
                        Log.i(BtManagerService.TAG, "startTime:" + startTime);
                        final SubStep subStep2 = subStep;
                        BtManagerService.mHandler.post(new Runnable() {
                            public void run() {
                                if (BtManagerService.mGetSubStepsCallback != null) {
                                    BtManagerService.mGetSubStepsCallback.success(subStep2);
                                }
                            }
                        });
                        if (curr_index == totle_number) {
                            BtManagerService.isNotifyTimeOut = false;
                            BtManagerService.gotoNextTask();
                            BtManagerService.mHandler.post(new Runnable() {
                                public void run() {
                                    if (BtManagerService.mGetSubStepsCallback != null) {
                                        BtManagerService.mGetSubStepsCallback.end(END_STATE.COMPLETE);
                                    }
                                }
                            });
                        }
                    }
                }
            } else if (GattInfo.HEART_MEASUREMENT.equals(characteristic.getUuid())) {
                BtManagerService.isNotifyTimeOut = false;
                byte[] txValue = characteristic.getValue();
                type = txValue[0] & 255;
                if (!false) {
                    if (txValue[0] == (byte) 6) {
                        float[] fArr = new float[3];
                        fArr = new float[]{0.0f, 0.0f, 0.0f};
                        Object ppg_data = new char[20];
                        for (i = 0; i < txValue.length - 1; i++) {
                            ppg_data[i] = (char) txValue[i + 1];
                        }
                        int[] heart_dataArr = new int[13];
                        Log.i(BtManagerService.TAG, "heart_data=[" + (txValue[1] & 255) + ", " + (txValue[2] & 255) + " ," + (txValue[3] & 255) + ", " + (txValue[4] & 255) + ", " + (txValue[5] & 255) + " ," + (txValue[6] & 255) + " ," + (txValue[7] & 255) + " ," + (txValue[8] & 255) + " ," + (txValue[9] & 255) + ", " + (txValue[10] & 255) + " ," + (txValue[11] & 255) + " ," + (txValue[12] & 255) + ", " + (txValue[13] & 255) + "] ");
                        Log.i(BtManagerService.TAG, "txValue.length=" + txValue.length);
                        if (txValue.length == 20) {
                            for (i = 0; i < fArr.length; i++) {
                                fArr[i] = (float) ((txValue[(i * 2) + 15] << 8) + (txValue[(i * 2) + 14] & 255));
                                Log.i(BtManagerService.TAG, "mems_data=" + fArr[i]);
                            }
                        }
                        Log.i(BtManagerService.TAG, "ppg_data=" + ppg_data);
                        PXIALGMOTION.Process(ppg_data, fArr);
                        final int hrData = PXIALGMOTION.GetHR();
                        Log.i(BtManagerService.TAG, "heart data:" + hrData);
                        BtManagerService.mHandler.post(new Runnable() {
                            public void run() {
                                if (BtManagerService.mGetHeartCallback != null) {
                                    BtManagerService.mGetHeartCallback.success(hrData);
                                }
                            }
                        });
                    } else if (type > 50 && BtManagerService.ifinHeart) {
                        BtManagerService.isNotifyTimeOut = false;
                        BtManagerService.ifinHeart = false;
                        i = type;
                        BtManagerService.mHandler.post(new Runnable() {
                            public void run() {
                                if (BtManagerService.mGetHeartCallback != null) {
                                    BtManagerService.mGetHeartCallback.success(i);
                                }
                            }
                        });
                        BtManagerService.gotoNextTask();
                    }
                }
            }
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            Log.i(BtManagerService.TAG, "onCharacteristicRead");
            if (GattInfo.BATTERY_LEVEL.equals(characteristic.getUuid())) {
                BtManagerService.gotoNextTask();
                int batteryLevel = characteristic.getValue()[0];
                Log.i(BtManagerService.TAG, "get battery:" + batteryLevel);
                Util.saveBattery(batteryLevel);
            } else if (GattInfo.DEVICEINFO_MEASUMENT.equals(characteristic.getUuid())) {
                BtManagerService.gotoNextTask();
                String version = new String(characteristic.getValue());
                Log.i(BtManagerService.TAG, "get name:" + version);
                Util.saveVersion(version);
            } else if (GattInfo.DEVICEINFO_HARDWARE.equals(characteristic.getUuid())) {
                BtManagerService.gotoNextTask();
                String hardware = new String(characteristic.getValue());
                Log.i(BtManagerService.TAG, "get hardware:" + hardware);
                Util.saveHardWare(hardware);
            }
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            Log.i(BtManagerService.TAG, "onCharacteristicRead");
        }

        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            switch (newState) {
                case 0:
                    Log.i(BtManagerService.TAG, "BLE STATE_DISCONNECTED:" + gatt.getDevice().getName() + " state:" + status + " new:" + newState);
                    if (!BtManagerService.isBluetoothOpen() || BtManagerService.testConnectTimes < 0) {
                        BtManagerService.mHandler.removeMessages(256);
                        BtManagerService.mHandler.removeMessages(257);
                    } else {
                        BtManagerService.mHandler.sendEmptyMessage(256);
                    }
                    if (BtManagerService.mBtDevice != null && BtManagerService.mBtDevice.getType() == 2 && BtManagerService.mBtDevice.getConnectState() != CONNECT_STATE.DISCONNECTED) {
                        Log.i(BtManagerService.TAG, "BLE STATE_DISCONNECTED CALL CALLBACK");
                        if (gatt != null) {
                            gatt.disconnect();
                            gatt.close();
                        }
                        BtManagerService.mBtDevice.setConnectState(CONNECT_STATE.DISCONNECTED);
                        BtManagerService.mHandler.post(new Runnable() {
                            public void run() {
                                if (BtManagerService.mConnectCallbackList.size() != 0) {
                                    Iterator it = BtManagerService.mConnectCallbackList.iterator();
                                    while (it.hasNext()) {
                                        ConnectCallback callback = (ConnectCallback) it.next();
                                        if (callback != null) {
                                            callback.disconnect(BtManagerService.mBtDevice);
                                        }
                                    }
                                }
                            }
                        });
                        return;
                    }
                    return;
                case 1:
                    if (BtManagerService.mBtDevice != null && BtManagerService.mBtDevice.getType() == 2 && BtManagerService.mBtDevice.getName().equals(gatt.getDevice().getName())) {
                        BtManagerService.mBtDevice.setConnectState(CONNECT_STATE.CONNECTING);
                        BtManagerService.mHandler.post(new Runnable() {
                            public void run() {
                                if (BtManagerService.mConnectCallbackList.size() != 0) {
                                    Iterator it = BtManagerService.mConnectCallbackList.iterator();
                                    while (it.hasNext()) {
                                        ConnectCallback callback = (ConnectCallback) it.next();
                                        if (callback != null) {
                                            callback.connecting(BtManagerService.mBtDevice);
                                        }
                                    }
                                }
                            }
                        });
                        return;
                    }
                    return;
                case 2:
                    Log.i(BtManagerService.TAG, "BLE STATE_CONNECTED");
                    if (BtManagerService.isConnecting) {
                        Log.i(BtManagerService.TAG, "BLE STATE_CONNECTED:" + BtManagerService.isConnecting);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        BtManagerService.mHandler.post(new Runnable() {
                            public void run() {
                                BtManagerService.mBluetoothGatt.discoverServices();
                            }
                        });
                    }
                    BtManagerService.mHandler.removeMessages(256);
                    BtManagerService.mHandler.removeMessages(257);
                    BtManagerService.testConnectTimes = 8;
                    return;
                default:
                    return;
            }
        }

        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
        }

        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        public void onServicesDiscovered(final BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            Log.i(BtManagerService.TAG, "onServicesDiscovered");
            ArrayList<BluetoothGattService> arr_tmp = (ArrayList) gatt.getServices();
            Iterator it = arr_tmp.iterator();
            while (it.hasNext()) {
                Log.i(BtManagerService.TAG, "service:" + ((BluetoothGattService) it.next()).getUuid().toString());
            }
            BtManagerService.mHandler.removeCallbacks(BtManagerService.rConnectTimeOut);
            BtManagerService.mHandler.postDelayed(BtManagerService.rConnectTimeOut, (long) (BtManagerService.TIME_OUT / 2));
            if (BtManagerService.isConnecting && arr_tmp.size() > 0) {
                BtManagerService.mHandler.removeCallbacks(BtManagerService.rGetService);
                BtManagerService.mHandler.removeCallbacks(BtManagerService.rConnectTimeOut);
                BtManagerService.isConnecting = false;
                BtManagerService.mBleServiceList = gatt.getServices();
                if (BtManagerService.mConnectCallbackList.size() != 0) {
                    BtManagerService.mHandler.post(new Runnable() {
                        public void run() {
                            BtManagerService.mBtDevice = new BtDevice(gatt);
                            Util.saveMacAddress(gatt.getDevice().getAddress());
                            Message msg = new Message();
                            Log.i("hph", "isBindingDevice=" + Util.getIsBindingDevice());
                            if (!TextUtils.isEmpty(gatt.getDevice().getName()) && ((gatt.getDevice().getName().equals("M2") || gatt.getDevice().getName().equals("U3") || gatt.getDevice().getName().equals("TERRA HR2")) && Util.getIsBindingDevice())) {
                                BtManagerService.setBindingDevice();
                                Util.setIsBindingDevice(false);
                            }
                            Log.i("hph", "BLE_AUTIO_HAND=" + BtManagerService.BLE_AUTIO_HAND);
                            if (BtManagerService.BLE_AUTIO_HAND) {
                                BtManagerService.sendAddTaskMessage(4115);
                            }
                            BtManagerService.sendAddTaskMessage(4097);
                            BtManagerService.sendAddTaskMessage(4099);
                            BtManagerService.sendAddTaskMessage(4100);
                            BtManagerService.sendAddTaskMessage(4102);
                            BtManagerService.sendAddTaskMessage(4104);
                            BtManagerService.sendAddTaskMessage(4105);
                            BtManagerService.sendAddTaskMessage(4103);
                            BtManagerService.sendAddTaskMessage(4101, 1000);
                        }
                    });
                }
            }
        }
    };
    private static GetAlarmRemindCallback mGetAlarmRemindCallback = null;
    private static GetHeartCallback mGetHeartCallback = null;
    private static GetMusicListCallback mGetMusicListCallback = null;
    private static GetSleepInfoCallback mGetSleepInfoCallback = null;
    private static GetSubStepsCallback mGetSubStepsCallback = null;
    private static GetTotalStepCallback mGetTotalStepCallback = null;
    public static Handler mHandler = new C07041();
    private static BtManagerService mInstance = null;
    private static LeScanCallback mLeScanCallback = new LeScanCallback() {

        class C07051 implements Runnable {
            C07051() {
            }

            public void run() {
                BtManagerService.mScanCallback.scan(BtManagerService.mScanBleDeviceList);
            }
        }

        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            Log.i(BtManagerService.TAG, "onLeScan:" + device.getName() + " type:" + device.getType());
            Iterator it = BtManagerService.mBleDeviceDetailList.iterator();
            while (it.hasNext()) {
                DeviceDetail detail = (DeviceDetail) it.next();
                if (device.getName() != null && device.getName().equals(detail.getName()) && device.getType() == detail.getBtType() && device.getType() == 2 && BtManagerService.mScanCallback != null) {
                    DeviceInfo deviceInfo = new DeviceInfo(device);
                    deviceInfo.setRssi(rssi);
                    deviceInfo.setDetail(detail);
                    Iterator it2 = BtManagerService.mScanBleDeviceList.iterator();
                    while (it2.hasNext()) {
                        if (((DeviceInfo) it2.next()).getMacAddress().equals(deviceInfo.getMacAddress())) {
                            return;
                        }
                    }
                    BtManagerService.mScanBleDeviceList.add(deviceInfo);
                    BtManagerService.mHandler.post(new C07051());
                }
            }
        }
    };
    private static LoadJniFunction mLoadJniFunction = new LoadJniFunction();
    private static ArrayList<DeviceInfo> mScanBleDeviceList = null;
    private static ScanCallback mScanCallback = null;
    private static int mSleepInfoPart = 1;
    private static updateBleCallback mUpdateBleCallback = null;
    private static ArrayList<String> musicArray;
    private static String musicStr = new String();
    private static Runnable rConnectClassicSuccess = new Runnable() {
        public void run() {
            BtManagerService.sendAddTaskMessage(BtManagerService.TASK_CLASSIC_GET_BATTERY);
            BtManagerService.sendAddTaskMessage(BtManagerService.TASK_CLASSIC_GET_HARDWARE_VERSION);
            BtManagerService.sendAddTaskMessage(BtManagerService.TASK_CLASSIC_GET_FIRMWARE_VERSION);
            BtManagerService.sendAddTaskMessage(BtManagerService.TASK_CLASSIC_GET_BATTERY);
            BtManagerService.sendAddTaskMessage(4101);
        }
    };
    private static Runnable rConnectTimeOut = new Runnable() {
        public void run() {
            BtManagerService.isConnecting = false;
            BtManagerService.access$2610();
            Log.i(BtManagerService.TAG, "rConnectTimeOut run");
            if (BtManagerService.mBluetoothGatt != null) {
                BtManagerService.mBluetoothGatt.disconnect();
            }
            if (BtManagerService.mConnectCallbackList.size() != 0) {
                Iterator it = BtManagerService.mConnectCallbackList.iterator();
                while (it.hasNext()) {
                    ConnectCallback callback = (ConnectCallback) it.next();
                    if (callback != null) {
                        callback.fail(0);
                    }
                }
            }
            if (BtManagerService.mBtDevice != null) {
                BtManagerService.mBtDevice.setConnectState(CONNECT_STATE.DISCONNECTED);
            }
        }
    };
    private static Runnable rGetService = new Runnable() {
        public void run() {
            if (BtManagerService.mBluetoothGatt != null) {
                BtManagerService.mBluetoothGatt.discoverServices();
            }
        }
    };
    private static Runnable rStartScanClassic = new Runnable() {
        public void run() {
            BtManagerService.getBluetoothAdapter().stopLeScan(BtManagerService.mLeScanCallback);
            BtManagerService.getBluetoothAdapter().startDiscovery();
        }
    };
    private static Runnable rStopM2Heart = new Runnable() {
        public void run() {
        }
    };
    private static Runnable rStopScanBle = new Runnable() {
        public void run() {
            BtManagerService.stopScan();
            if (BtManagerService.mScanCallback != null) {
                BtManagerService.mScanCallback.end(END_STATE.TIMEOUT);
            }
        }
    };
    private static Runnable rTaskLoop = new Runnable() {
        public void run() {
            BtManagerService.mHandler.sendEmptyMessage(BtManagerService.TASK_NEXT);
        }
    };
    private static Task runningTask = new Task(-1, 0, null);
    private static byte[] sleepInfoByte = new byte[40];
    private static ContentObserver smsContentObserver;
    private static ArrayList<Task> taskQueue = null;
    private static int testConnectTimes = 8;
    private static int totle_number;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.e(BtManagerService.TAG, "onReceive---------");
            String action = intent.getAction();
            int i = -1;
            switch (action.hashCode()) {
                case -1530327060:
                    if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                        i = 0;
                        break;
                    }
                    break;
            }
            switch (i) {
                case 0:
                    switch (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 0)) {
                        case 10:
                            Log.e(BtManagerService.TAG, "onReceive---------STATE_OFF");
                            if (BtManagerService.mBtDevice != null) {
                                BtManagerService.mBtDevice.setConnectState(CONNECT_STATE.DISCONNECTED);
                                return;
                            }
                            return;
                        case 11:
                            Log.e(BtManagerService.TAG, "onReceive---------STATE_TURNING_ON");
                            return;
                        case 12:
                            Log.e(BtManagerService.TAG, "onReceive---------STATE_ON");
                            return;
                        case 13:
                            Log.e(BtManagerService.TAG, "onReceive---------STATE_TURNING_OFF");
                            return;
                        default:
                            return;
                    }
                default:
                    return;
            }
        }
    };

    static class C07041 extends Handler {

        class C07031 implements ConnectCallback {
            C07031() {
            }

            public void success(BtDevice device) {
            }

            public void fail(int state) {
            }

            public void disconnect(BtDevice device) {
            }

            public void connecting(BtDevice device) {
            }

            public void battery(int battery) {
            }
        }

        C07041() {
        }

        public void handleMessage(Message msg) {
            Task task = new Task(-1, 0, null);
            super.handleMessage(msg);
            switch (msg.what) {
                case BluetoothManager.TYPE_BT_CONNECTED /*241*/:
                    if (BtManagerService.mBtDevice != null && BtManagerService.mBtDevice.getType() == 1 && BtManagerService.isBluetoothOpen() && BtManagerService.mConnectCallbackList.size() != 0) {
                        BtManagerService.mHandler.removeCallbacks(BtManagerService.rConnectTimeOut);
                        BtManagerService.mHandler.removeMessages(256);
                        BtManagerService.mHandler.removeMessages(257);
                        BtManagerService.mHandler.postDelayed(BtManagerService.rConnectClassicSuccess, 2000);
                        return;
                    }
                    return;
                case BluetoothManager.TYPE_BT_CONNECTION_LOST /*242*/:
                    if (BtManagerService.mBtDevice != null && BtManagerService.mBtDevice.getType() == 1 && !BtManagerService.isConnecting) {
                        BtManagerService.mBtDevice.setConnectState(CONNECT_STATE.DISCONNECTED);
                        if (BtManagerService.mConnectCallbackList.size() != 0) {
                            Iterator it = BtManagerService.mConnectCallbackList.iterator();
                            while (it.hasNext()) {
                                ConnectCallback callback = (ConnectCallback) it.next();
                                if (callback != null) {
                                    callback.disconnect(BtManagerService.mBtDevice);
                                }
                            }
                            return;
                        }
                        return;
                    }
                    return;
                case BluetoothManager.TYPE_DATA_ARRIVE /*244*/:
                    byte[] dataBuffer = msg.getData().getByteArray("dataBuffer");
                    if (dataBuffer != null && dataBuffer.length != 0) {
                        Log.i(BtManagerService.TAG, "data arrive:" + dataBuffer.length);
                        try {
                            BtManagerService.parseReadBuffer(dataBuffer);
                            return;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    return;
                case 256:
                    BtManagerService.mHandler.sendEmptyMessageDelayed(257, 10000);
                    BtManagerService.mHandler.sendEmptyMessageDelayed(257, StatisticConfig.MIN_UPLOAD_INTERVAL);
                    BtManagerService.mHandler.sendEmptyMessageDelayed(257, 60000);
                    BtManagerService.mHandler.sendEmptyMessageDelayed(257, 90000);
                    return;
                case 257:
                    ConnectCallback connectCallback = new C07031();
                    if (BtManagerService.mBtDevice != null) {
                        BtManagerService.connectDevice(BtManagerService.mBtDevice.getType(), BtManagerService.mBtDevice.getMacAddress(), connectCallback, false);
                    }
                    BtManagerService.mHandler.sendEmptyMessageDelayed(257, 300000);
                    return;
                case BtManagerService.TASK_ADD /*61440*/:
                    if (msg.arg1 != 0) {
                        task.setTaskCode(msg.arg1);
                        task.setTaskSingal(msg.arg2);
                        task.setTaskDetail(msg.obj);
                        BtManagerService.taskQueue.add(task);
                        if ((task.getTaskCode() & 8193) == 8193 || (task.getTaskCode() & 12289) == 12289) {
                            BtManagerService.taskQueue.add(new Task(task.getTaskCode() + 1, task.getTaskSingal(), Integer.valueOf(task.getTaskSingal())));
                            return;
                        }
                        return;
                    }
                    return;
                case BtManagerService.TASK_NEXT /*61441*/:
                    BtManagerService.mHandler.removeCallbacks(BtManagerService.rTaskLoop);
                    if (BtManagerService.taskQueue == null || BtManagerService.taskQueue.size() == 0) {
                        task.setTaskCode(-1);
                    } else {
                        task = (Task) BtManagerService.taskQueue.get(0);
                        BtManagerService.taskQueue.remove(0);
                    }
                    long delay = (long) BtManagerService.TASK_WAIT;
                    if (task.getTaskCode() != -1) {
                        if (task.getTaskCode() == BtManagerService.TASK_CLASSIC_GET_MUSICLIST) {
                            delay = (long) (BtManagerService.TASK_CLASSIC_WAIT * 8);
                        } else if (task.getTaskCode() == BtManagerService.TASK_GET_HEART_INFO) {
                            delay = (long) BtManagerService.TIME_GET_HEART;
                        } else if (task.getTaskCode() == BtManagerService.TASK_CLASSIC_GET_BATTERY) {
                            delay = (long) BtManagerService.TASK_NOTIFY_WAIT;
                        } else if (task.getTaskCode() == BtManagerService.TASK_GET_HEART_INFO_CLOSE) {
                            delay = (long) BtManagerService.TASK_NOTIFY_WAIT;
                            Log.i(BtManagerService.TAG, "TASK_GET_HEART_INFO_CLOSE delay:" + delay);
                        } else if ((task.getTaskCode() & 8193) == 8193) {
                            delay = (long) BtManagerService.TASK_NOTIFY_WAIT;
                        } else if ((task.getTaskCode() & 12289) == 12289) {
                            delay = (long) BtManagerService.TASK_CLASSIC_WAIT;
                        }
                        try {
                            BtManagerService.runTaskmethod(task);
                        } catch (Exception e2) {
                            Log.i(BtManagerService.TAG, "runTaskmethod in crash:" + e2.getMessage());
                        }
                    }
                    BtManagerService.mHandler.postDelayed(BtManagerService.rTaskLoop, delay);
                    return;
                default:
                    Log.i(BtManagerService.TAG, "msg.what:" + msg.what);
                    return;
            }
        }
    }

    static class C07272 implements Runnable {
        C07272() {
        }

        public void run() {
            BtManagerService.mGetTotalStepCallback.end(END_STATE.TIMEOUT);
        }
    }

    static class C07283 implements Runnable {
        C07283() {
        }

        public void run() {
            BtManagerService.mGetSleepInfoCallback.end(END_STATE.TIMEOUT);
        }
    }

    static class C07294 implements Runnable {
        C07294() {
        }

        public void run() {
            BtManagerService.openHeartService();
        }
    }

    static class C07305 implements Runnable {
        C07305() {
        }

        public void run() {
            BtManagerService.notifyHeart(true);
        }
    }

    static class C07316 implements Runnable {
        C07316() {
        }

        public void run() {
            BtManagerService.closeHeartService();
        }
    }

    static class C07327 implements Runnable {
        C07327() {
        }

        public void run() {
            BtManagerService.notifyHeart(false);
        }
    }

    static class C07338 implements Runnable {
        C07338() {
        }

        public void run() {
            BtManagerService.mGetHeartCallback.end(END_STATE.COMPLETE);
        }
    }

    static class C07349 implements Runnable {
        C07349() {
        }

        public void run() {
            BtManagerService.mGetHeartCallback.end(END_STATE.COMPLETE);
        }
    }

    public enum CONNECT_STATE {
        NOT_CONNECT,
        DISCONNECTED,
        CONNECTED,
        CONNECTING
    }

    public interface ConnectStateCallback {
        void disconnected();
    }

    public interface DeleteMusicListCallback {
        void fail();

        void success();
    }

    public enum END_STATE {
        COMPLETE,
        TIMEOUT,
        BT_UNSUPPORT,
        EMPTY
    }

    public interface GetAlarmRemindCallback {
        void end(END_STATE end_state);

        void success(boolean[] zArr);
    }

    public interface GetHeartCallback {
        void end(END_STATE end_state);

        void success(int i);
    }

    public interface GetMusicListCallback {
        void fail();

        void success(ArrayList<String> arrayList);
    }

    public interface GetSleepInfoCallback {
        void end(END_STATE end_state);

        void success(SleepInfo sleepInfo);
    }

    public interface GetSubStepsCallback {
        void end(END_STATE end_state);

        void success(SubStep subStep);
    }

    public interface GetTotalStepCallback {
        void end(END_STATE end_state);

        void success(int i, String str);
    }

    public interface ScanCallback {
        void end(END_STATE end_state);

        void scan(ArrayList<DeviceInfo> arrayList);
    }

    public interface updateBleCallback {
        public static final int FAIL_ERROR_FILE_NOT_EXIST = 2;
        public static final int FAIL_ERROR_NOT_CONNECT = 1;
        public static final int FAIL_ERROR_UPDATE_FAIL = 3;

        void fail(int i);

        void progress(int i);

        void success();
    }

    static /* synthetic */ int access$2610() {
        int i = testConnectTimes;
        testConnectTimes = i - 1;
        return i;
    }

    static /* synthetic */ int access$3308() {
        int i = bleUpdateTest;
        bleUpdateTest = i + 1;
        return i;
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void init(Context context) {
        mContext = context;
        taskQueue = new ArrayList();
        Util.init(context);
        Intent intent = new Intent();
        intent.setClass(context, BtManagerService.class);
        context.startService(intent);
    }

    public static boolean isScanning() {
        return isScanning;
    }

    private static void runTaskmethod(Task task) throws Exception {
        runningTask = task;
        switch (task.getTaskCode()) {
            case 4097:
                Log.i(TAG, "TASK_GET_BATTERY");
                getDeviceBatteryLevel();
                return;
            case 4098:
                Log.i(TAG, "TASK_GET_NAME");
                getDeviceName();
                return;
            case 4099:
                Log.i(TAG, "TASK_GET_VERSION");
                getDeviceVersion();
                return;
            case 4100:
                Log.i(TAG, "TASK_GET_HARDWARE");
                getDeviceHardWare();
                return;
            case 4101:
                Log.i(TAG, "TASK_CONNECT_SUCCESS");
                isConnecting = false;
                if (mConnectCallbackList.size() != 0 && mBtDevice != null) {
                    Iterator it;
                    ConnectCallback callback;
                    if (mBtDevice.getBattery() == -1) {
                        mBtDevice.setConnectState(CONNECT_STATE.DISCONNECTED);
                        mBluetoothManager.disconnectRemoteDevice();
                        it = mConnectCallbackList.iterator();
                        while (it.hasNext()) {
                            callback = (ConnectCallback) it.next();
                            if (callback != null) {
                                callback.fail(0);
                            }
                        }
                        return;
                    }
                    mBtDevice.setConnectState(CONNECT_STATE.CONNECTED);
                    it = mConnectCallbackList.iterator();
                    while (it.hasNext()) {
                        callback = (ConnectCallback) it.next();
                        if (callback != null) {
                            callback.success(mBtDevice);
                        }
                    }
                    return;
                }
                return;
            case 4102:
                Log.i(TAG, "TASK_GET_TOTAL_STEP_CLOSE");
                notifyBattery();
                return;
            case 4103:
                Log.i(TAG, "TASK_SET_DEVICE_TIME");
                setDeviceTime();
                return;
            case 4104:
                Log.i(TAG, "TASK_FIND_PHONE_NOTIFY");
                notifyFindPhone();
                return;
            case 4105:
                Log.i(TAG, "TASK_TAKE_PICTURE_NOTIFY");
                notifyTakePicture();
                return;
            case 4112:
                Log.i(TAG, "TASK_MISS_CALL");
                if (getCallRemind() && mBtDevice != null && mBtDevice.supportCallRemind()) {
                    switch (mBtDevice.getType()) {
                        case 1:
                            if (mBluetoothManager != null) {
                                mBluetoothManager.sendData((byte[]) task.getTaskDetail());
                                return;
                            }
                            return;
                        case 2:
                            switch (mBtDevice.getDetail().getCallRemindCmd() % 10) {
                                case 0:
                                    return;
                                case 1:
                                    noticeMissCall();
                                    return;
                                default:
                                    return;
                            }
                        default:
                            return;
                    }
                }
                return;
            case 4113:
                Log.i(TAG, "TASK_CALL_END");
                if (getCallRemind() && mBtDevice != null && mBtDevice.getType() == 2 && mBtDevice.supportCallRemind()) {
                    noticeCallEnd();
                    return;
                }
                return;
            case 4114:
                Log.i(TAG, "TASK_NEW_CALL===");
                if (getCallRemind() && mBtDevice != null && mBtDevice.getType() == 2) {
                    Log.i("hph", "mBtDevice.getDetail().getCallRemindCmd()=" + mBtDevice.getDetail().getCallRemindCmd());
                    Log.i("hph", "mBtDevice.getDetail()./10=" + (mBtDevice.getDetail().getCallRemindCmd() % 10));
                    if (mBtDevice.supportCallRemind()) {
                        switch (mBtDevice.getDetail().getCallRemindCmd() % 10) {
                            case 0:
                                noticeNewCall();
                                return;
                            case 1:
                                noticeNewCall();
                                return;
                            case 2:
                                noticeNewCallPro((String) task.getTaskDetail());
                                return;
                            case 3:
                                noticeNewCallNameOrNum((String) task.getTaskDetail());
                                return;
                            case 4:
                                noticeNewCallM2();
                                return;
                            case 5:
                                String[] versionStr = mBtDevice.getVersion().split("_");
                                int fileVersion = Integer.parseInt(versionStr[versionStr.length - 1]);
                                String versionName = mBtDevice.getVersion();
                                if (versionName.startsWith("M2_2_")) {
                                    noticeNewCallPro((String) task.getTaskDetail());
                                    return;
                                } else if (versionName.startsWith("M2_3_")) {
                                    noticeNewCallNameOrNum((String) task.getTaskDetail());
                                    return;
                                } else if (Util.getName().equals("U3")) {
                                    noticeNewCallPro((String) task.getTaskDetail());
                                    return;
                                } else if (fileVersion <= 103) {
                                    noticeNewCallM2();
                                    return;
                                } else if (fileVersion >= 104) {
                                    noticeNewCallPro((String) task.getTaskDetail());
                                    return;
                                } else {
                                    return;
                                }
                            case 6:
                                noticeCallWithContent((String) task.getTaskDetail());
                                return;
                            default:
                                return;
                        }
                    }
                    return;
                }
                return;
            case 4115:
                setDeviceNotVibration();
                return;
            case 4116:
                Log.i(TAG, "GET_SMS:" + task.getTaskDetail() + " version:" + Util.getVersion());
                String[] smsData = task.getTaskDetail().toString().split("\\|");
                String number = smsData[0];
                String body = smsData[1];
                String id = smsData[2];
                String name = new ReadContactname(mContext).getContactNameFromPhoneBook(number);
                if (!TextUtils.isEmpty(name)) {
                    number = name;
                }
                if (getSMSRemind() && mBtDevice != null && mBtDevice.getConnectState() == CONNECT_STATE.CONNECTED) {
                    switch (mBtDevice.getType()) {
                        case 1:
                            sendSmsMessage(id, body, number);
                            return;
                        case 2:
                            if (Util.getVersion().startsWith("M2_3_")) {
                                noticeSmsPro(number);
                                return;
                            } else if (Util.getName().equals("U3")) {
                                noticeSmsProWithContent(number + ":" + body);
                                return;
                            } else {
                                noticeNewSMS();
                                return;
                            }
                        default:
                            return;
                    }
                }
                return;
            case 4117:
                String str = (String) task.getTaskDetail();
                if (Util.getVersion().startsWith("M2_3_")) {
                    noticeAppNotifyPro(str);
                    return;
                } else if (Util.getName().equals("U3")) {
                    noticeAppProWithContent(str);
                    return;
                } else {
                    noticeNewWeChatMsg();
                    return;
                }
            case TASK_GET_TOTAL_STEP /*8209*/:
                Log.i(TAG, "TASK_GET_TOTAL_STEP");
                notifyTotalStep(true);
                return;
            case TASK_GET_TOTAL_STEP_CLOSE /*8210*/:
                Log.i(TAG, "TASK_GET_TOTAL_STEP_CLOSE");
                notifyTotalStep(false);
                if (isNotifyTimeOut && mGetTotalStepCallback != null) {
                    mHandler.post(new C07272());
                }
                isNotifyTimeOut = true;
                return;
            case TASK_GET_SLEEP_INFO /*8225*/:
                Log.i(TAG, "TASK_GET_SLEEP_INFO");
                notifySleepInfo(true);
                return;
            case TASK_GET_SLEEP_INFO_CLOSE /*8226*/:
                Log.i(TAG, "TASK_GET_SLEEP_INFO_CLOSE");
                notifySleepInfo(false);
                if (isNotifyTimeOut && mGetSleepInfoCallback != null) {
                    mHandler.post(new C07283());
                }
                isNotifyTimeOut = true;
                return;
            case TASK_GET_SUB_STEPS /*8241*/:
                Log.i(TAG, "TASK_GET_SUB_STEPS");
                notifyGetSubSteps(true);
                return;
            case TASK_GET_SUB_STEPS_CLOSE /*8242*/:
                Log.i(TAG, "TASK_GET_SUB_STEPS_CLOSE");
                notifyGetSubSteps(false);
                if (isNotifyTimeOut && mGetSubStepsCallback != null) {
                    mHandler.post(new Runnable() {
                        public void run() {
                            BtManagerService.mGetSubStepsCallback.end(END_STATE.TIMEOUT);
                        }
                    });
                }
                isNotifyTimeOut = true;
                return;
            case TASK_GET_HEART_INFO /*8257*/:
                Log.i(TAG, "TASK_GET_HEART_INFO");
                ifinHeart = true;
                if (mBtDevice.getName().equals("M2") || mBtDevice.getName().equals("U3") || mBtDevice.getName().equals("TERRA HR2")) {
                    setM2HeartRate();
                }
                notifyGsensor(true);
                mHandler.postDelayed(new C07294(), 1000);
                mHandler.postDelayed(new C07305(), 1500);
                return;
            case TASK_GET_HEART_INFO_CLOSE /*8258*/:
                Log.i(TAG, "TASK_GET_HEART_INFO_CLOSE");
                notifyGsensor(false);
                mHandler.postDelayed(new C07316(), 1000);
                mHandler.postDelayed(new C07327(), 1500);
                PXIALGMOTION.Close();
                if (isNotifyTimeOut) {
                    if (mGetHeartCallback != null) {
                        mGetHeartCallback.end(END_STATE.TIMEOUT);
                    }
                } else if (mGetHeartCallback != null) {
                    if (mBtDevice.getName().equals("M2") || mBtDevice.getName().equals("TERRA HR2")) {
                        mHandler.postDelayed(new C07338(), 15000);
                    } else if (mBtDevice.getName().equals("U3")) {
                        mHandler.postDelayed(new C07349(), 4000);
                    } else {
                        mGetHeartCallback.end(END_STATE.COMPLETE);
                    }
                }
                isNotifyTimeOut = true;
                return;
            case TASK_CLASSIC_GET_TOTAL_STEP /*12305*/:
                Log.i(TAG, "TASK_CLASSIC_GET_TOTAL_STEP");
                mBluetoothManager.sendCustomCmd(CLASSIC_CMD_GET_STEP, "himan");
                return;
            case TASK_CLASSIC_GET_TOTAL_STEP_CLOSE /*12306*/:
                Log.i(TAG, "TASK_CLASSIC_GET_TOTAL_STEP_CLOSE");
                gotoNextTask();
                if (isNotifyTimeOut && mGetTotalStepCallback != null) {
                    mHandler.post(new Runnable() {
                        public void run() {
                            BtManagerService.mGetTotalStepCallback.end(END_STATE.TIMEOUT);
                        }
                    });
                }
                isNotifyTimeOut = true;
                return;
            case TASK_CLASSIC_GET_SUB_STEP /*12321*/:
                Log.i(TAG, "TASK_CLASSIC_GET_SUB_STEP");
                mBluetoothManager.sendCustomCmd(CLASSIC_CMD_GET_STEP, "himan");
                return;
            case TASK_CLASSIC_GET_BATTERY /*12337*/:
                Log.i(TAG, "TASK_CLASSIC_GET_BATTERY");
                getDeviceBatteryLevel();
                return;
            case TASK_CLASSIC_GET_HARDWARE_VERSION /*12353*/:
                Log.i(TAG, "TASK_CLASSIC_GET_HARDWARE_VERSION");
                getDeviceHardWare();
                return;
            case TASK_CLASSIC_GET_FIRMWARE_VERSION /*12369*/:
                Log.i(TAG, "TASK_CLASSIC_GET_FIRMWARE_VERSION");
                getDeviceVersionClassic();
                return;
            case TASK_CLASSIC_GET_ALARM_REMIND /*12401*/:
                Log.i(TAG, "TASK_CLASSIC_GET_ALARM_REMIND");
                mBluetoothManager.sendCustomCmd(98, "");
                return;
            case TASK_CLASSIC_GET_ALARM_REMIND_CLOSE /*12402*/:
                Log.i(TAG, "TASK_CLASSIC_GET_ALARM_REMIND_CLOSE");
                if (isNotifyTimeOut && mGetAlarmRemindCallback != null) {
                    mHandler.post(new Runnable() {
                        public void run() {
                            BtManagerService.mGetAlarmRemindCallback.end(END_STATE.TIMEOUT);
                            BtManagerService.mGetAlarmRemindCallback = null;
                        }
                    });
                }
                isNotifyTimeOut = true;
                return;
            case TASK_CLASSIC_GET_MUSICLIST /*12417*/:
                char[] c_tag = new char[4];
                if (((Boolean) task.getTaskDetail()).booleanValue()) {
                    c_tag[0] = '1';
                } else {
                    c_tag[0] = '0';
                }
                Log.i(TAG, "TASK_CLASSIC_GET_MUSICLIST:" + c_tag[0]);
                c_tag[1] = 'ÿ';
                c_tag[2] = 'ÿ';
                c_tag[3] = 'ÿ';
                mBluetoothManager.sendCustomCmd(160, "", c_tag);
                return;
            case TASK_CLASSIC_GET_MUSICLIST_CLOSE /*12418*/:
                Log.i(TAG, "TASK_CLASSIC_GET_MUSICLIST_CLOSE");
                if (isNotifyTimeOut && mGetMusicListCallback != null) {
                    mHandler.post(new Runnable() {
                        public void run() {
                            BtManagerService.mGetMusicListCallback.fail();
                            BtManagerService.mGetMusicListCallback = null;
                        }
                    });
                }
                isNotifyTimeOut = true;
                return;
            case TASK_CLASSIC_DELETE_MUSICLIST /*12433*/:
                Log.i(TAG, "TASK_CLASSIC_DELETE_MUSICLIST");
                mBluetoothManager.sendCustomCmd(161, new String(Util.boolsToChar(deleteList)));
                return;
            case TASK_CLASSIC_DELETE_MUSICLIST_CLOSE /*12434*/:
                Log.i(TAG, "TASK_CLASSIC_DELETE_MUSICLIST_CLOSE");
                if (isNotifyTimeOut && mDeleteMusicListCallback != null) {
                    mHandler.post(new Runnable() {
                        public void run() {
                            BtManagerService.mDeleteMusicListCallback.fail();
                            BtManagerService.mDeleteMusicListCallback = null;
                        }
                    });
                }
                isNotifyTimeOut = true;
                return;
            case TASK_CLASSIC_GET_SLEEP_INFO /*12545*/:
                Log.i(TAG, "TASK_CLASSIC_GET_SLEEP_INFO");
                mBluetoothManager.sendCustomCmd(128, "himan");
                return;
            default:
                return;
        }
    }

    public static void scanBleDevice(long scanTime) {
        Log.i(TAG, "scanBleDevice isBluetoothOpen:" + isBluetoothOpen());
        BluetoothAdapter.getDefaultAdapter().startLeScan(mLeScanCallback);
    }

    public static void scanDevice(long scanTime, ScanCallback scanCallback) {
        Log.i(TAG, "scanDevice isBluetoothOpen:" + isBluetoothOpen());
        mHandler.removeCallbacks(rStartScanClassic);
        mHandler.removeCallbacks(rStopScanBle);
        if (isBluetoothOpen()) {
            stopScan();
            isScanning = true;
            mScanCallback = scanCallback;
            mScanBleDeviceList = new ArrayList();
            boolean ifadd = false;
            for (BluetoothDevice device : getBluetoothAdapter().getBondedDevices()) {
                Log.i(TAG, "bonddevice:" + device.getName() + " mac:" + device.getAddress());
                Iterator it = mClassicDeviceDetailList.iterator();
                while (it.hasNext()) {
                    DeviceDetail detail = (DeviceDetail) it.next();
                    if (detail.getName().equals(device.getName())) {
                        Iterator it2 = mScanBleDeviceList.iterator();
                        while (it2.hasNext()) {
                            if (device.getAddress().equals(((DeviceInfo) it2.next()).getMacAddress())) {
                                ifadd = true;
                            }
                        }
                        if (!ifadd) {
                            DeviceInfo deviceInfo = new DeviceInfo(device);
                            deviceInfo.setDetail(detail);
                            mScanBleDeviceList.add(deviceInfo);
                        }
                        ifadd = false;
                    }
                }
            }
            mScanCallback.scan(mScanBleDeviceList);
            scanBleDevice(scanTime);
            mHandler.postDelayed(rStartScanClassic, scanTime / 2);
            mHandler.postDelayed(rStopScanBle, scanTime);
        } else if (scanCallback != null) {
            scanCallback.end(END_STATE.BT_UNSUPPORT);
        }
    }

    public static void scanDeviceOnlyBle(long scanTime, ScanCallback scanCallback) {
        Log.i(TAG, "scanDevice isBluetoothOpen:" + isBluetoothOpen());
        if (isBluetoothOpen()) {
            mHandler.removeCallbacks(rStopScanBle);
            stopScan();
            isScanning = true;
            mScanCallback = scanCallback;
            mScanBleDeviceList = new ArrayList();
            scanBleDevice(scanTime);
            mHandler.postDelayed(rStopScanBle, scanTime);
        } else if (scanCallback != null) {
            scanCallback.end(END_STATE.BT_UNSUPPORT);
        }
    }

    public static void scanDeviceOnlyClassic(long scanTime, ScanCallback scanCallback) {
        Log.i(TAG, "scanDevice isBluetoothOpen:" + isBluetoothOpen());
        if (isBluetoothOpen()) {
            mHandler.removeCallbacks(rStopScanBle);
            stopScan();
            isScanning = true;
            mScanCallback = scanCallback;
            mScanBleDeviceList = new ArrayList();
            boolean ifadd = false;
            for (BluetoothDevice device : getBluetoothAdapter().getBondedDevices()) {
                Log.i(TAG, "bonddevice:" + device.getName() + " mac:" + device.getAddress());
                Iterator it = mClassicDeviceDetailList.iterator();
                while (it.hasNext()) {
                    DeviceDetail detail = (DeviceDetail) it.next();
                    if (detail.getName().equals(device.getName())) {
                        Iterator it2 = mScanBleDeviceList.iterator();
                        while (it2.hasNext()) {
                            if (device.getAddress().equals(((DeviceInfo) it2.next()).getMacAddress())) {
                                ifadd = true;
                            }
                        }
                        if (!ifadd) {
                            DeviceInfo deviceInfo = new DeviceInfo(device);
                            deviceInfo.setDetail(detail);
                            mScanBleDeviceList.add(deviceInfo);
                        }
                        ifadd = false;
                    }
                }
            }
            mScanCallback.scan(mScanBleDeviceList);
            getBluetoothAdapter().startDiscovery();
            mHandler.postDelayed(rStopScanBle, scanTime);
        } else if (scanCallback != null) {
            scanCallback.end(END_STATE.BT_UNSUPPORT);
        }
    }

    public static void setBtDevice(BluetoothDevice device, int type) {
        mBtDevice = new BtDevice(device, type);
    }

    public static void stopScan() {
        isScanning = false;
        mHandler.removeCallbacks(rStartScanClassic);
        getBluetoothAdapter().stopLeScan(mLeScanCallback);
        getBluetoothAdapter().cancelDiscovery();
    }

    public static void deleteDevice() {
        taskQueue.clear();
        for (BluetoothDevice device : getBluetoothAdapter().getBondedDevices()) {
            Iterator it = mClassicDeviceDetailList.iterator();
            while (it.hasNext()) {
                if (((DeviceDetail) it.next()).getName().equals(device.getName())) {
                    Util.unpairDevice(device);
                }
            }
        }
        if (!(TextUtils.isEmpty(Util.getMacAddress()) || mBluetoothAdapter == null)) {
            Util.unpairDevice(mBluetoothAdapter.getRemoteDevice(Util.getMacAddress()));
        }
        switch (Util.getType()) {
            case 1:
                if (mBtDevice == null || !mBtDevice.getName().contains("Primo 5")) {
                    if (mBluetoothManager != null) {
                        mBluetoothManager.disconnectRemoteDevice();
                        break;
                    }
                }
                setReleasePair();
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        if (BtManagerService.mBluetoothManager != null) {
                            BtManagerService.mBluetoothManager.disconnectRemoteDevice();
                            Log.i("hph", "setReleasePair disconnectRemoteDevice");
                        }
                    }
                }, 300);
                break;
                break;
            case 2:
                if (mBluetoothGatt != null) {
                    Util.unpairDevice(mBluetoothGatt.getDevice());
                    break;
                }
                break;
        }
        Util.emptySave();
        isScanning = false;
        mBtDevice = null;
        isConnecting = false;
        mSleepInfoPart = 1;
        Util.setIsBindingDevice(true);
    }

    public static BtDevice getConnectDevice(ConnectCallback callback) {
        boolean z = true;
        String str = TAG;
        StringBuilder append = new StringBuilder().append("check connect classic:");
        if (Util.getType() != 1) {
            z = false;
        }
        Log.i(str, append.append(z).append(" ").append(TextUtils.isEmpty(Util.getMacAddress())).toString());
        if (mBtDevice != null) {
            Log.i(TAG, "has connect device");
            if (!mConnectCallbackList.contains(callback)) {
                mConnectCallbackList.add(callback);
            }
            return mBtDevice;
        } else if (TextUtils.isEmpty(Util.getMacAddress())) {
            return null;
        } else {
            Log.i(TAG, "have mac address");
            if (!mConnectCallbackList.contains(callback)) {
                mConnectCallbackList.add(callback);
            }
            mBtDevice = new BtDevice();
            return mBtDevice;
        }
    }

    protected static void connectDevice(int type, String deviceName, final BluetoothDevice device, ConnectCallback callback, boolean isHand) {
        stopScan();
        if (mScanCallback != null) {
            mScanCallback.end(END_STATE.COMPLETE);
        }
        if (!(mConnectCallbackList.contains(callback) || callback == null)) {
            mConnectCallbackList.add(callback);
        }
        Iterator it;
        ConnectCallback callbackTmp;
        if (isBluetoothOpen()) {
            Log.i(TAG, "isConnecting:" + isConnecting + " type:" + type);
            if (isConnecting) {
                if (mBtDevice != null) {
                    mBtDevice.setConnectState(CONNECT_STATE.CONNECTING);
                }
                if (mConnectCallbackList.size() != 0) {
                    it = mConnectCallbackList.iterator();
                    while (it.hasNext()) {
                        callbackTmp = (ConnectCallback) it.next();
                        if (callbackTmp != null) {
                            callbackTmp.fail(1);
                        }
                    }
                    return;
                }
                return;
            }
            if (mBtDevice != null) {
                mBtDevice.setConnectState(CONNECT_STATE.CONNECTING);
            }
            if (!(TextUtils.isEmpty(device.getName()) || getDetail(deviceName, type) == null)) {
                Util.saveName(device.getName());
            }
            isConnecting = true;
            switch (type) {
                case 1:
                    Log.i(TAG, "connectDevice classic");
                    if (mBluetoothManager != null) {
                        mBluetoothManager.connectToAppointedDevice(device);
                    }
                    mHandler.postDelayed(rConnectTimeOut, (long) TIME_OUT);
                    return;
                case 2:
                    Log.i(TAG, "connectDevice ble");
                    if (mBluetoothGatt != null) {
                        mBluetoothGatt.close();
                    }
                    BLE_AUTIO_HAND = isHand;
                    mSleepInfoPart = 1;
                    mHandler.post(new Runnable() {
                        public void run() {
                            BtManagerService.mBluetoothGatt = device.connectGatt(BtManagerService.mContext, false, BtManagerService.mGattCallback);
                        }
                    });
                    mHandler.postDelayed(rConnectTimeOut, (long) TIME_OUT);
                    return;
                default:
                    return;
            }
        }
        it = mConnectCallbackList.iterator();
        while (it.hasNext()) {
            callbackTmp = (ConnectCallback) it.next();
            if (callbackTmp != null) {
                callbackTmp.fail(2);
            }
        }
        if (mBtDevice != null) {
            mBtDevice.setConnectState(CONNECT_STATE.DISCONNECTED);
        }
    }

    protected static void connectDevice(int type, String macAddress, ConnectCallback callback) {
        connectDevice(type, macAddress, callback, true);
    }

    protected static void connectDevice(int type, String macAddress, ConnectCallback callback, boolean isHand) {
        if (!TextUtils.isEmpty(macAddress)) {
            BluetoothDevice device = getBluetoothAdapter().getRemoteDevice(macAddress);
            connectDevice(type, device.getName(), device, callback, isHand);
        }
    }

    protected static void getTotalStep(GetTotalStepCallback callback) {
        mGetTotalStepCallback = callback;
        if (callback != null) {
            switch (mBtDevice.getType()) {
                case 1:
                    sendAddTaskMessage(TASK_CLASSIC_GET_TOTAL_STEP);
                    return;
                case 2:
                    sendAddTaskMessage(TASK_GET_TOTAL_STEP);
                    return;
                default:
                    return;
            }
        }
    }

    protected static void getSleepInfo(GetSleepInfoCallback callback) {
        mGetSleepInfoCallback = callback;
        if (callback != null) {
            switch (mBtDevice.getType()) {
                case 1:
                    sendAddTaskMessage(TASK_CLASSIC_GET_SLEEP_INFO);
                    return;
                case 2:
                    sendAddTaskMessage(TASK_GET_SLEEP_INFO);
                    return;
                default:
                    return;
            }
        }
    }

    public static void refreshClassicBattery() {
        sendAddTaskMessage(TASK_CLASSIC_GET_BATTERY);
    }

    protected static void getSubSteps(GetSubStepsCallback callback) {
        mGetSubStepsCallback = callback;
        if (callback != null && mBtDevice != null) {
            switch (mBtDevice.getType()) {
                case 1:
                    sendAddTaskMessage(TASK_CLASSIC_GET_SUB_STEP);
                    return;
                case 2:
                    sendAddTaskMessage(TASK_GET_SUB_STEPS);
                    return;
                default:
                    return;
            }
        }
    }

    protected static void getHeartInfo(int time, GetHeartCallback callback) {
        if (callback != null && mBtDevice != null && mBtDevice.supportHeart()) {
            TIME_GET_HEART = time * 1000;
            mGetHeartCallback = callback;
            switch (mBtDevice.getType()) {
                case 1:
                    sendAddTaskMessage(TASK_CLASSIC_GET_SUB_STEP);
                    return;
                case 2:
                    sendAddTaskMessage(TASK_GET_HEART_INFO);
                    return;
                default:
                    return;
            }
        }
    }

    private static void sendAddTaskMessage(int taskCode) {
        sendAddTaskMessage(taskCode, null, 0);
    }

    private static void sendAddTaskMessage(int taskCode, long delay) {
        sendAddTaskMessage(taskCode, null, delay);
    }

    private static void sendAddTaskMessage(int taskCode, Object detail, long delay) {
        sendTaskMessage(TASK_ADD, taskCode, detail, delay);
    }

    private static void sendTaskMessage(int taskMehtod, int taskCode, Object detal, long delay) {
        if (mHandler != null) {
            Message msg = new Message();
            msg.what = taskMehtod;
            msg.arg1 = taskCode;
            msg.obj = detal;
            mHandler.sendMessageDelayed(msg, delay);
        }
    }

    private static void gotoNextTask() {
        if (mHandler != null && rTaskLoop != null) {
            mHandler.removeCallbacks(rTaskLoop);
            mHandler.sendEmptyMessage(TASK_NEXT);
        }
    }

    private static void refreshTaskLoop() {
        if (mHandler != null && rTaskLoop != null) {
            mHandler.removeCallbacks(rTaskLoop);
            mHandler.postDelayed(rTaskLoop, (long) TASK_NOTIFY_WAIT);
        }
    }

    public static boolean isBluetoothOpen() {
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Log.i("hph", "mBluetoothAdapter.isEnabled=" + mBluetoothAdapter.isEnabled());
            Log.i("hph", "mBluetoothAdapter.getState=" + mBluetoothAdapter.getState());
        }
        if (!mBluetoothAdapter.isEnabled() || mBluetoothAdapter.getState() == 10) {
            return false;
        }
        return true;
    }

    public static void openBluetoothSetting() {
        Intent it = new Intent();
        it.setAction("android.settings.BLUETOOTH_SETTINGS");
        it.setFlags(268435456);
        mContext.startActivity(it);
    }

    public static void openBluetoothSetting(Context context) {
        Intent it = new Intent();
        it.setAction("android.settings.BLUETOOTH_SETTINGS");
        context.startActivity(it);
    }

    private static BluetoothAdapter getBluetoothAdapter() {
        if (mBluetoothAdapter != null) {
            return mBluetoothAdapter;
        }
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return mBluetoothAdapter;
    }

    private static boolean isServiceStarted() {
        return mInstance != null;
    }

    private static void parseXml(Context context) {
        XmlResourceParser xml = context.getResources().getXml(C0687R.xml.device_info);
        try {
            DeviceDetail detail = null;
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                switch (eventType) {
                    case 0:
                        mBleDeviceDetailList = new ArrayList();
                        mClassicDeviceDetailList = new ArrayList();
                        mAllDeviceDetailList = new ArrayList();
                        break;
                    case 2:
                        String tag = xml.getName();
                        if (!tag.equalsIgnoreCase(TAG_DEVICE)) {
                            if (!tag.equalsIgnoreCase("name")) {
                                if (!tag.equalsIgnoreCase("type")) {
                                    if (!tag.equalsIgnoreCase(TAG_FIRMWARE)) {
                                        if (!tag.equalsIgnoreCase(TAG_HARDWARE)) {
                                            if (!tag.equalsIgnoreCase(TAG_CALL_REMIND)) {
                                                if (!tag.equalsIgnoreCase(TAG_SMS_REMIND)) {
                                                    if (!tag.equalsIgnoreCase(TAG_PUSH_REMIND)) {
                                                        if (!tag.equalsIgnoreCase(TAG_ANTILOST)) {
                                                            if (!tag.equalsIgnoreCase("alarm")) {
                                                                if (!tag.equalsIgnoreCase(TAG_SLEEP)) {
                                                                    if (!tag.equalsIgnoreCase(TAG_HEART)) {
                                                                        if (!tag.equalsIgnoreCase(TAG_UPDATE_TYPE)) {
                                                                            if (!tag.equalsIgnoreCase(TAG_SPORT_TARGET)) {
                                                                                if (!tag.equalsIgnoreCase(TAG_DISTURBANCE_MODE)) {
                                                                                    if (!tag.equalsIgnoreCase(TAG_SEDENTARY_REMIND)) {
                                                                                        if (!tag.equalsIgnoreCase(TAG_DISPLAY_SETTING)) {
                                                                                            if (!tag.equalsIgnoreCase(TAG_WRIST_LIFT)) {
                                                                                                if (!tag.equalsIgnoreCase(TAG_FIND_BRACELET)) {
                                                                                                    break;
                                                                                                }
                                                                                                detail.setFindBraceletCmd(new Integer(xml.nextText()).intValue());
                                                                                                break;
                                                                                            }
                                                                                            detail.setWristLiftCmd(new Integer(xml.nextText()).intValue());
                                                                                            break;
                                                                                        }
                                                                                        detail.setDisplaySettingCmd(new Integer(xml.nextText()).intValue());
                                                                                        break;
                                                                                    }
                                                                                    detail.setSedentaryRemindCmd(new Integer(xml.nextText()).intValue());
                                                                                    break;
                                                                                }
                                                                                detail.setDisturbanceModeCmd(new Integer(xml.nextText()).intValue());
                                                                                break;
                                                                            }
                                                                            detail.setSportTargetCmd(new Integer(xml.nextText()).intValue());
                                                                            break;
                                                                        }
                                                                        detail.setUpdateCmd(new Integer(xml.nextText()).intValue());
                                                                        break;
                                                                    }
                                                                    detail.setHeartCmd(new Integer(xml.nextText()).intValue());
                                                                    break;
                                                                }
                                                                detail.setSleepCmd(new Integer(xml.nextText()).intValue());
                                                                break;
                                                            }
                                                            detail.setAlarmCmd(new Integer(xml.nextText()).intValue());
                                                            break;
                                                        }
                                                        detail.setAntiLostCmd(new Integer(xml.nextText()).intValue());
                                                        break;
                                                    }
                                                    detail.setPushRemindCmd(new Integer(xml.nextText()).intValue());
                                                    break;
                                                }
                                                detail.setSmsRemindCmd(new Integer(xml.nextText()).intValue());
                                                break;
                                            }
                                            detail.setCallRemindCmd(new Integer(xml.nextText()).intValue());
                                            break;
                                        }
                                        detail.setHardwareCmd(new Integer(xml.nextText()).intValue());
                                        break;
                                    }
                                    detail.setFirmwareCmd(new Integer(xml.nextText()).intValue());
                                    break;
                                }
                                detail.setBtType(new Integer(xml.nextText()).intValue());
                                break;
                            }
                            detail.setName(xml.nextText());
                            break;
                        }
                        detail = new DeviceDetail();
                        break;
                    case 3:
                        if (xml.getName().equalsIgnoreCase(TAG_DEVICE) && detail != null) {
                            if (detail.getBtType() == 2) {
                                mBleDeviceDetailList.add(detail);
                            } else if (detail.getBtType() == 1) {
                                mClassicDeviceDetailList.add(detail);
                            }
                            mAllDeviceDetailList.add(detail);
                            detail = null;
                            break;
                        }
                    default:
                        break;
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void onCreate() {
        super.onCreate();
        mInstance = this;
        parseXml(mContext);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.bluetooth.device.action.FOUND");
        filter.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
        filter.addAction(DfuBaseService.BROADCAST_PROGRESS);
        filter.addAction(DfuBaseService.BROADCAST_ERROR);
        filter.addAction(FindPhoneDialog.ACTION_FIND_PHONE);
        registerReceiver(mBtReceiver, filter);
        registerReceiver(this.mReceiver, makeFilter());
        mHandler.sendEmptyMessage(TASK_NEXT);
        isNotifyTimeOut = true;
        mBluetoothManager = new BluetoothManager(mContext, mHandler);
        mBluetoothManager.setupConnection();
        smsContentObserver = new SmsContentObserver(this, mHandler);
        registerSmsContentObservers();
        startCallService();
        startForeground(this);
        startService(new Intent(this, BootstrapService.class));
        Log.i(TAG, "onCreate");
    }

    public static void startForeground(Service context) {
        NotificationManager nm = (NotificationManager) context.getSystemService(MessageObj.CATEGORY_NOTI);
        Builder builder = new Builder(context);
        builder.setContentTitle("Droi Bluetooth Service").setContentText("Thanks for using Droi.").setWhen(System.currentTimeMillis()).setPriority(-2).setSmallIcon(C0687R.drawable.logo).setAutoCancel(true);
        context.startForeground(87, builder.build());
    }

    public void onDestroy() {
        super.onDestroy();
        mInstance = null;
        mBluetoothAdapter = null;
        isScanning = false;
        unregisterReceiver(mBtReceiver);
        unregisterReceiver(this.mReceiver);
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
        }
        mBluetoothManager.disconnectRemoteDevice();
        unRegisterSmsContentObservers();
        stopCallService();
        stopForeground(true);
        Log.i(TAG, "onDestroy");
    }

    public static void getDeviceBatteryLevel() {
        Log.d(TAG, "getRemoteDeviceBatteryInfo");
        if (mBtDevice != null) {
            switch (mBtDevice.getType()) {
                case 1:
                    mBluetoothManager.sendCustomCmd(3, "");
                    return;
                case 2:
                    bleReadCharacteristic(GattInfo.BATTERY_LEVEL);
                    return;
                default:
                    return;
            }
        }
    }

    private static void getDeviceName() {
        Log.d(TAG, "getDeviceName");
        bleReadCharacteristic(GattInfo.DEVICE_NAME_SERVICE, GattInfo.DEVICE_NAME_CHAR);
    }

    private static void getDeviceVersion() {
        Log.d(TAG, "getDeviceVersion");
        bleReadCharacteristic(GattInfo.DEVICEINFO_SERVICE, GattInfo.DEVICEINFO_MEASUMENT);
    }

    private static void getDeviceVersionClassic() {
        Log.d(TAG, "getDeviceVersionClassic");
        mBluetoothManager.sendCustomCmd(5, "1");
    }

    private static void getDeviceHardWare() {
        Log.d(TAG, "getDeviceHardWare");
        if (mBtDevice != null) {
            switch (mBtDevice.getType()) {
                case 1:
                    mBluetoothManager.sendCustomCmd(97, "");
                    return;
                case 2:
                    bleReadCharacteristic(GattInfo.DEVICEINFO_SERVICE, GattInfo.DEVICEINFO_HARDWARE);
                    return;
                default:
                    return;
            }
        }
    }

    protected static void setAlarmRemin(String remind) {
        if (mBtDevice != null && mBtDevice.getName().contains("Primo 5")) {
            Log.i(TAG, "setAlarmRemin:" + remind);
            mBluetoothManager.sendCustomCmd(96, remind);
        }
    }

    protected static void getAlarmRemin(GetAlarmRemindCallback callback) {
        if (mBtDevice != null && callback != null && mBtDevice.getName().contains("Primo 5")) {
            Log.i(TAG, "getAlarmRemin");
            mGetAlarmRemindCallback = callback;
            sendAddTaskMessage(TASK_CLASSIC_GET_ALARM_REMIND, (long) TASK_CLASSIC_WAIT);
        }
    }

    protected static void setReleasePair() {
        if (mBtDevice != null && mBtDevice.getName().contains("Primo 5")) {
            mBluetoothManager.sendCustomCmd(101, "");
        }
    }

    protected static void setAlarm(String alarm) {
        if (mBtDevice != null) {
            switch (mBtDevice.getType()) {
                case 1:
                    Log.i(TAG, "setAlarm:" + alarm);
                    mBluetoothManager.sendCustomCmd(CLASSIC_CMD_SET_ALARM, alarm);
                    return;
                case 2:
                    setBleAlarmTime(alarm);
                    return;
                default:
                    return;
            }
        }
    }

    protected static void setSedentaryInfo(String sedentaryInfo) {
        if (mBtDevice != null) {
            switch (mBtDevice.getType()) {
                case 2:
                    setSedentaryRemind(sedentaryInfo);
                    return;
                default:
                    return;
            }
        }
    }

    protected static void getMusicList(GetMusicListCallback callback, boolean ifForce) {
        if (mBtDevice != null && callback != null && mBtDevice.getName().contains("Primo 5")) {
            Log.i(TAG, "getMusicList");
            mGetMusicListCallback = callback;
            if (mHandler != null) {
                Message msg = new Message();
                msg.what = TASK_ADD;
                msg.arg1 = TASK_CLASSIC_GET_MUSICLIST;
                msg.obj = Boolean.valueOf(ifForce);
                mHandler.sendMessage(msg);
            }
        }
    }

    protected static void deleteMusicList(boolean[] list, DeleteMusicListCallback callback) {
        if (mBtDevice != null && callback != null && mBtDevice.getName().contains("Primo 5")) {
            Log.i(TAG, "deleteMusicList");
            mDeleteMusicListCallback = callback;
            deleteList = list;
            sendAddTaskMessage(TASK_CLASSIC_DELETE_MUSICLIST);
        }
    }

    protected static void setPlayMode(int mode) {
        if (mBtDevice != null && mBtDevice.getName().contains("Primo 5")) {
            Log.i(TAG, "setPlayMode");
            char[] tag = new char[4];
            if (mode != PLAY_MODE_NORMAL) {
                tag[0] = '1';
            } else {
                tag[0] = '0';
            }
            tag[1] = 'ÿ';
            tag[2] = 'ÿ';
            tag[3] = 'ÿ';
            mBluetoothManager.sendCustomCmd(162, "", tag);
        }
    }

    protected static void setMusicList(boolean[] bools) {
        if (mBtDevice == null || !mBtDevice.getName().contains("Primo 5")) {
            Log.i(TAG, "setMusicList");
            mBluetoothManager.sendCustomCmd(CLASSIC_CMD_SET_MUSIC_LIST, new String(Util.boolsToChar(bools)));
        } else {
            Log.i(TAG, "setMusicList");
            mBluetoothManager.sendCustomCmd(CLASSIC_CMD_SET_MUSIC_LIST, new String(Util.boolsToChar(bools)));
        }
    }

    private static void notifyBattery() {
        bleSetNotify(true, GattInfo.BATTERY_SERVICE, GattInfo.BATTERY_LEVEL, GattInfo.STEPS_NOTICEFATION_ENABLE);
    }

    private static void notifyFindPhone() {
        bleSetNotify(true, GattInfo.FIND_PHONE_SERVICE, GattInfo.FIND_PHONE_MEASUREMENT, GattInfo.FIND_PHONE_NOTIFY_ENABLE);
    }

    private static void notifyTakePicture() {
        bleSetNotify(true, GattInfo.STEPS_SERVICE, GattInfo.TAKE_PICTURE, GattInfo.STEPS_NOTICEFATION_ENABLE);
    }

    private static void setDeviceTime() {
        Log.d(TAG, "setDeviceTime");
        long curr_system_time = System.currentTimeMillis();
        bleWriteCharacteristic(GattInfo.TIME_SYNC, Util.long2Byte(Long.valueOf(Long.parseLong(String.valueOf(((Util.getUtcTimeZone(curr_system_time) - 28800000) / 1000) + Util.getUtcTime(curr_system_time)))).longValue()));
    }

    private static void notifyTotalStep(boolean enable) {
        bleSetNotify(enable, GattInfo.STEPS_SERVICE, GattInfo.TOTAL_STEPS_MEASUREMENT, GattInfo.STEPS_NOTICEFATION_ENABLE);
    }

    private static void notifyHeart(boolean enable) {
        bleSetNotify(enable, GattInfo.STEPS_SERVICE, GattInfo.HEART_MEASUREMENT, GattInfo.STEPS_NOTICEFATION_ENABLE);
    }

    private static void notifyGsensor(boolean enable) {
        bleSetNotify(enable, GattInfo.STEPS_SERVICE, GattInfo.GSENOR_MEASUREMENT, GattInfo.STEPS_NOTICEFATION_ENABLE);
    }

    private static void notifySleepInfo(boolean enable) {
        bleSetNotify(enable, GattInfo.STEPS_SERVICE, GattInfo.SLEEP_INFO_CHAR, GattInfo.STEPS_NOTICEFATION_ENABLE);
    }

    private static void notifyGetSubSteps(boolean enable) {
        bleSetNotify(enable, GattInfo.STEPS_SERVICE, GattInfo.SEGMENT_STEPS_MEASUREMENT, GattInfo.STEPS_NOTICEFATION_ENABLE);
    }

    private static void bleSetNotify(boolean enable, UUID serviceUUID, UUID characterUUID, UUID descriptorUUID) {
        if (mBluetoothGatt != null) {
            BluetoothGattCharacteristic find_charac = getGattCharacteristic(serviceUUID, characterUUID);
            if (find_charac != null) {
                boolean result = mBluetoothGatt.setCharacteristicNotification(find_charac, enable);
                BluetoothGattDescriptor descriptor = find_charac.getDescriptor(descriptorUUID);
                if (descriptor != null) {
                    if (enable) {
                        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    } else {
                        descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
                    }
                    mBluetoothGatt.writeDescriptor(descriptor);
                }
            }
        }
    }

    private static void noticeNewSMS() {
        Log.d(TAG, "noticeNewSMS");
        bleWriteCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA, new byte[]{(byte) 4});
    }

    private static void setDeviceNotVibration() {
        Log.d(TAG, "setDeviceNotVibration");
        bleWriteCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA, new byte[]{(byte) 2});
    }

    private void noticeReadSMS() {
        Log.d(TAG, "noticeReadSMS");
        bleWriteCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA, new byte[]{(byte) 8});
    }

    private static boolean bleWriteCharacteristic(UUID serviceUUID, UUID characterUUID, byte[] data) {
        if (mBluetoothGatt != null) {
            BluetoothGattCharacteristic charac;
            if (serviceUUID == null) {
                charac = getGattCharacteristic(characterUUID);
            } else {
                charac = getGattCharacteristic(serviceUUID, characterUUID);
            }
            if (!(charac == null || mBluetoothGatt == null)) {
                if (data != null) {
                    charac.setValue(data);
                }
                charac.setWriteType(1);
                return mBluetoothGatt.writeCharacteristic(charac);
            }
        }
        return false;
    }

    private static boolean bleWriteCharacteristic(UUID serviceUUID, UUID characterUUID) {
        return bleWriteCharacteristic(serviceUUID, characterUUID, null);
    }

    private static boolean bleWriteCharacteristic(UUID characterUUID) {
        return bleWriteCharacteristic(null, characterUUID, null);
    }

    private static boolean bleWriteCharacteristic(UUID characterUUID, byte[] data) {
        return bleWriteCharacteristic(null, characterUUID, data);
    }

    private static boolean bleReadCharacteristic(UUID characterUUID) {
        if (mBluetoothGatt != null) {
            BluetoothGattCharacteristic charac = getGattCharacteristic(characterUUID);
            if (!(charac == null || mBluetoothGatt == null)) {
                return mBluetoothGatt.readCharacteristic(charac);
            }
        }
        return false;
    }

    private static boolean bleReadCharacteristic(UUID serviceUUID, UUID characterUUID) {
        if (mBluetoothGatt != null) {
            BluetoothGattCharacteristic charac = getGattCharacteristic(serviceUUID, characterUUID);
            if (!(charac == null || mBluetoothGatt == null)) {
                return mBluetoothGatt.readCharacteristic(charac);
            }
        }
        return false;
    }

    private static BluetoothGattCharacteristic getGattCharacteristic(UUID characteristicUUid) {
        if (mBleServiceList.size() != 0) {
            for (BluetoothGattService gattService : mBleServiceList) {
                for (BluetoothGattCharacteristic gattCharac : gattService.getCharacteristics()) {
                    if (gattCharac.getUuid().equals(characteristicUUid)) {
                        return gattCharac;
                    }
                }
            }
        }
        return null;
    }

    private static BluetoothGattCharacteristic getGattCharacteristic(UUID serviceUUid, UUID characteristicUUid) {
        BluetoothGattCharacteristic characteristic = null;
        if (mBleServiceList.size() != 0) {
            for (BluetoothGattService gattService : mBleServiceList) {
                Log.d("gatt", "::services uuid =" + gattService.getUuid().toString());
                if (gattService.getUuid().equals(serviceUUid)) {
                    List<BluetoothGattCharacteristic> gattCharacs = gattService.getCharacteristics();
                    Log.d("gatt", "right::services uuid =" + gattService.getUuid().toString());
                    for (BluetoothGattCharacteristic gattCharac : gattCharacs) {
                        UUID uuid = gattCharac.getUuid();
                        Log.d("gatt", "::gattCharacteristic uuid:" + uuid.toString());
                        if (uuid.equals(characteristicUUid)) {
                            Log.d("gatt", "right::gattCharacteristic uuid:" + uuid.toString());
                            characteristic = gattCharac;
                            break;
                        }
                    }
                }
            }
        }
        return characteristic;
    }

    public static boolean isConnected() {
        if (mBtDevice == null || mBtDevice.getConnectState() != CONNECT_STATE.CONNECTED) {
            return false;
        }
        return true;
    }

    private static void parseReadBuffer(byte[] mIncomingMessageBuffer) throws IOException {
        MessageObj mIncomingMessage = new MessageObj();
        MessageHeader mIncomingMessageHeader = new MessageHeader();
        try {
            mIncomingMessage = mIncomingMessage.parseXml(mIncomingMessageBuffer);
            String messageSubType = mIncomingMessage.getDataHeader().getSubType();
            Log.i(TAG, "parseReadBuffer start:" + messageSubType);
            if (messageSubType.equals(MessageObj.SUBTYPE_SMS)) {
                String address = ((SmsMessageBody) mIncomingMessage.getDataBody()).getNumber();
                String message = mIncomingMessage.getDataBody().getContent();
                if (message == null) {
                    message = "\n";
                }
                if (message.equals("")) {
                    message = "\n";
                }
                if (!praser(address, message)) {
                }
            } else if (!messageSubType.equals(MessageObj.SUBTYPE_MISSED_CALL)) {
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static boolean praser(String address, String message) {
        Log.i("chenxin1", "address:" + address + " message:" + message);
        if (!address.equals(TYD_NUMBER)) {
            return false;
        }
        char[] c_ucs2 = message.toCharArray();
        char c_cmd = c_ucs2[0];
        if (c_cmd > ' ') {
            Log.i(TAG, "CMD:" + (c_cmd - 32));
            CMD_HANDLER.obtainMessage(c_cmd - 32, c_ucs2).sendToTarget();
        } else {
            Log.i(TAG, "小于0X20的命令暂不被支持CMD =" + c_cmd);
        }
        return true;
    }

    public static void sendFileByBluetooth(Context context, String path) {
        if (path != null && !path.equals("") && BluetoothAdapter.getDefaultAdapter() != null) {
            PackageManager localPackageManager = context.getPackageManager();
            Intent localIntent = null;
            HashMap<String, ActivityInfo> localHashMap = null;
            try {
                Intent localIntent2 = new Intent();
                try {
                    localIntent2.setAction("android.intent.action.SEND");
                    File file = new File(path);
                    Log.i("chenxin2", "send file:" + path + " exist:" + file.exists());
                    localIntent2.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
                    localIntent2.setType("*/*");
                    List<ResolveInfo> localList = localPackageManager.queryIntentActivities(localIntent2, 0);
                    HashMap<String, ActivityInfo> localHashMap2 = new HashMap();
                    try {
                        for (ResolveInfo resolveInfo : localList) {
                            ActivityInfo localActivityInfo2 = resolveInfo.activityInfo;
                            String str = localActivityInfo2.applicationInfo.processName;
                            if (str.contains("bluetooth")) {
                                localHashMap2.put(str, localActivityInfo2);
                            }
                        }
                        localHashMap = localHashMap2;
                        localIntent = localIntent2;
                    } catch (Exception e) {
                        localHashMap = localHashMap2;
                        localIntent = localIntent2;
                    }
                } catch (Exception e2) {
                    localIntent = localIntent2;
                }
            } catch (Exception e3) {
            }
            ActivityInfo activityInfo = (ActivityInfo) localHashMap.get("com.android.bluetooth");
            if (activityInfo == null) {
                activityInfo = (ActivityInfo) localHashMap.get("com.mediatek.bluetooth");
            }
            if (activityInfo == null) {
                Iterator<ActivityInfo> localIterator2 = localHashMap.values().iterator();
                if (localIterator2.hasNext()) {
                    activityInfo = (ActivityInfo) localIterator2.next();
                }
            }
            if (activityInfo != null) {
                localIntent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
                context.startActivity(localIntent);
            }
        }
    }

    public static void removeConnectCallback(ConnectCallback callback) {
        mConnectCallbackList.remove(callback);
        Iterator it = mConnectCallbackList.iterator();
        while (it.hasNext()) {
            ConnectCallback callback_tmp = (ConnectCallback) it.next();
            if (callback_tmp == null) {
                mConnectCallbackList.remove(callback_tmp);
            }
        }
    }

    private void registerSmsContentObservers() {
        getContentResolver().registerContentObserver(Uri.parse("content://sms"), true, smsContentObserver);
    }

    private void unRegisterSmsContentObservers() {
        getContentResolver().unregisterContentObserver(smsContentObserver);
    }

    public static void openBluetooth() {
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.enable();
        }
    }

    public static void setCallRemind(boolean open) {
        Util.setCallRemind(open);
    }

    public static boolean getCallRemind() {
        return Util.getCallRemind();
    }

    public static void setSMSRemind(boolean open) {
        Util.setSMSRemind(open);
    }

    public static boolean getSMSRemind() {
        return Util.getSMSRemind();
    }

    public static void setDisconnectRemind(boolean open) {
        Util.setDisconnectRemind(open);
    }

    public static boolean getDisconnectRemind() {
        return Util.getDisconnectRemind();
    }

    public static DeviceDetail getDetail(String deviceName, int btType) {
        Iterator it;
        DeviceDetail detail;
        if (btType == 2) {
            if (!(mBleDeviceDetailList == null || mBleDeviceDetailList.size() == 0)) {
                it = mBleDeviceDetailList.iterator();
                while (it.hasNext()) {
                    detail = (DeviceDetail) it.next();
                    if (detail.getName().equals(deviceName)) {
                        return detail;
                    }
                }
            }
        } else if (!(btType != 1 || mClassicDeviceDetailList == null || mClassicDeviceDetailList.size() == 0)) {
            it = mClassicDeviceDetailList.iterator();
            while (it.hasNext()) {
                detail = (DeviceDetail) it.next();
                if (detail.getName().equals(deviceName)) {
                    return detail;
                }
            }
        }
        return null;
    }

    private static void sendSmsMessage(String id, String msgbody, String address) {
        Log.i(TAG, "sendSmsMessage:" + address + " body:" + msgbody);
        MessageObj smsMessageData = new MessageObj();
        smsMessageData.setDataHeader(createSmsHeader());
        smsMessageData.setDataBody(createSmsBody(id, address, msgbody));
        if (smsMessageData != null) {
            Log.i(TAG, "sendSmsMessage:" + address + " body:" + msgbody);
            mBluetoothManager.sendData(genBytesFromObject(smsMessageData));
        }
    }

    private static MessageHeader createSmsHeader() {
        MessageHeader header = new MessageHeader();
        header.setCategory(MessageObj.CATEGORY_NOTI);
        header.setSubType(MessageObj.SUBTYPE_SMS);
        header.setMsgId(Util.genMessageId());
        header.setAction(MessageObj.ACTION_ADD);
        return header;
    }

    private static SmsMessageBody createSmsBody(String id, String address, String msgbody) {
        String phoneNum = address;
        String sender = Util.getContactName(mContext, phoneNum);
        String content = msgbody;
        int timestamp = Util.getUtcTime(System.currentTimeMillis());
        SmsMessageBody body = new SmsMessageBody();
        body.setSender(sender);
        body.setNumber(phoneNum);
        body.setContent(content);
        body.setTimestamp(timestamp);
        body.setID(id);
        return body;
    }

    public static byte[] genBytesFromObject(MessageObj dataObj) {
        if (dataObj == null) {
            return null;
        }
        byte[] bArr = null;
        try {
            return dataObj.genXmlBuff();
        } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
            return bArr;
        } catch (IllegalStateException e12) {
            e12.printStackTrace();
            return bArr;
        } catch (IOException e13) {
            e13.printStackTrace();
            return bArr;
        } catch (XmlPullParserException e14) {
            e14.printStackTrace();
            return bArr;
        } catch (NoDataException e) {
            e.printStackTrace();
            return bArr;
        }
    }

    private static void noticeMissCall() {
        Log.d(TAG, "noticeMissCall");
        bleWriteCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA, new byte[]{(byte) 5});
    }

    protected static void startCallService() {
        Log.i(TAG, "startCallService()");
        if (mCallService == null) {
            mCallService = new CallService(mContext, mHandler);
        }
        ((TelephonyManager) mContext.getSystemService("phone")).listen(mCallService, 32);
    }

    protected static void stopCallService() {
        Log.i(TAG, "stopCallService()");
        if (mCallService != null) {
            ((TelephonyManager) mContext.getSystemService("phone")).listen(mCallService, 0);
            mCallService = null;
        }
    }

    private static void noticeNewCall() {
        Log.d(TAG, "noticeNewCall");
        bleWriteCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA, new byte[]{(byte) 5});
    }

    private static void noticeNewCallM2() {
        Log.d(TAG, "noticeNewCallM2");
        bleWriteCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA, new byte[]{(byte) 40});
    }

    private static void noticeNewCallPro(String phoneNum) {
        Log.d(TAG, "noticeNewCallPro:" + phoneNum);
        byte[] byteArr = new byte[]{(byte) 7, (byte) -1};
        if (phoneNum == null) {
            phoneNum = "";
        }
        byte[] phoneArr = phoneNum.getBytes();
        byte[] resArray = new byte[(byteArr.length + phoneArr.length)];
        System.arraycopy(byteArr, 0, resArray, 0, 1);
        System.arraycopy(phoneArr, 0, resArray, 1, phoneArr.length);
        System.arraycopy(byteArr, 1, resArray, resArray.length - 1, 1);
        bleWriteCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA, resArray);
    }

    private static void noticeNewCallNameOrNum(String nameornum) {
        Log.d(TAG, "noticeNewCallNameOrNum:" + nameornum);
        byte[] byteArr = new byte[]{(byte) 7, (byte) -1};
        if (nameornum == null) {
            nameornum = "";
        }
        byte[] phoneArr = nameornum.getBytes();
        byte[] resArray = new byte[(byteArr.length + phoneArr.length)];
        Log.i("hph1", "nameornum = " + nameornum);
        Log.i("hph1", "phoneArr = " + phoneArr);
        Log.i("hph1", "resArray = " + resArray);
        System.arraycopy(byteArr, 0, resArray, 0, 1);
        System.arraycopy(phoneArr, 0, resArray, 1, phoneArr.length);
        System.arraycopy(byteArr, 1, resArray, resArray.length - 1, 1);
        bleWriteCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA, resArray);
    }

    private static void noticeSmsPro(String nameornum) {
        Log.i(TAG, "noticeSmsPro:" + nameornum);
        byte[] byteArr = new byte[]{(byte) 4, (byte) -1};
        if (nameornum == null) {
            nameornum = "";
        }
        byte[] phoneArr = nameornum.getBytes();
        byte[] resArray = new byte[(byteArr.length + phoneArr.length)];
        Log.i("hph1", "nameornum = " + nameornum);
        Log.i("hph1", "phoneArr = " + phoneArr);
        Log.i("hph1", "resArray = " + resArray);
        System.arraycopy(byteArr, 0, resArray, 0, 1);
        System.arraycopy(phoneArr, 0, resArray, 1, phoneArr.length);
        System.arraycopy(byteArr, 1, resArray, resArray.length - 1, 1);
        bleWriteCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA, resArray);
    }

    private static void noticeSmsProWithContent(String nameornum) {
        noticeWithContent((byte) 4, nameornum);
    }

    private static void noticeAppProWithContent(String nameornum) {
        noticeWithContent((byte) 6, nameornum);
    }

    private static void noticeCallWithContent(String nameornum) {
        noticeWithContent((byte) 7, nameornum);
    }

    private static void noticeWithContent(byte cmd, String nameornum) {
        int i;
        Log.i(TAG, "noticeWithContent:" + nameornum);
        String strTmp = "";
        for (i = 0; i < nameornum.length(); i++) {
            strTmp = strTmp + nameornum.substring(i, i + 1);
            if (strTmp.getBytes().length >= 90) {
                break;
            }
        }
        nameornum = strTmp;
        int page = 0;
        String sendContent = "";
        for (i = 0; i < nameornum.length(); i++) {
            sendContent = sendContent + nameornum.substring(i, i + 1);
            if (i == nameornum.length() - 1) {
                noticContent(cmd, sendContent, page, true);
                sendContent = "";
            } else if (sendContent.getBytes().length >= 15) {
                noticContent(cmd, sendContent, page, false);
                page++;
                sendContent = "";
            }
        }
    }

    private static void noticContent(byte cmd, String content, int page, boolean isEnd) {
        Log.i(TAG, "noticSmsContent:" + content + " ---isEnd:" + isEnd + " bytes:" + content.getBytes().length);
        byte[] byteArr = isEnd ? new byte[]{cmd, (byte) -1} : new byte[]{cmd, (byte) -2};
        if (content == null) {
            content = "";
        }
        byte[] phoneArr = new byte[18];
        byte[] contentBytes = content.getBytes();
        for (int i = 0; i < phoneArr.length; i++) {
            if (i < contentBytes.length) {
                phoneArr[i] = contentBytes[i];
            } else {
                phoneArr[i] = (byte) -2;
            }
        }
        final byte[] resArray = new byte[(byteArr.length + phoneArr.length)];
        Log.i("hph1", "nameornum = " + content);
        Log.i("hph1", "phoneArr = " + phoneArr);
        Log.i("hph1", "resArray = " + resArray);
        System.arraycopy(byteArr, 0, resArray, 0, 1);
        System.arraycopy(phoneArr, 0, resArray, 1, phoneArr.length);
        System.arraycopy(byteArr, 1, resArray, resArray.length - 1, 1);
        mHandler.postDelayed(new Runnable() {
            public void run() {
                BtManagerService.bleWriteCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA, resArray);
            }
        }, (long) (page * 200));
    }

    private static void noticeAppNotifyPro(String nameornum) {
        Log.d(TAG, "noticeAppNotifyPro:" + nameornum);
        byte[] byteArr = new byte[]{(byte) 6, (byte) -1};
        if (nameornum == null) {
            nameornum = "";
        }
        byte[] phoneArr = nameornum.getBytes();
        byte[] resArray = new byte[(byteArr.length + phoneArr.length)];
        Log.i("hph1", "nameornum = " + nameornum);
        Log.i("hph1", "phoneArr = " + phoneArr);
        Log.i("hph1", "resArray = " + resArray);
        System.arraycopy(byteArr, 0, resArray, 0, 1);
        System.arraycopy(phoneArr, 0, resArray, 1, phoneArr.length);
        System.arraycopy(byteArr, 1, resArray, resArray.length - 1, 1);
        bleWriteCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA, resArray);
    }

    private static void noticeCallEnd() {
        Log.d(TAG, "noticeCallEnd");
        bleWriteCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA, new byte[]{(byte) 17});
    }

    private static void openHeartService() {
        Log.d(TAG, "openHeartService");
        bleWriteCharacteristic(GattInfo.FIRMWARE_READY_SERVICE, GattInfo.FIRMWARE_READY_MEASUMENT, new byte[]{(byte) 34});
    }

    private static void closeHeartService() {
        Log.d(TAG, "closeHeartService");
        bleWriteCharacteristic(GattInfo.FIRMWARE_READY_SERVICE, GattInfo.FIRMWARE_READY_MEASUMENT, new byte[]{(byte) 35});
    }

    private static void setBleAlarmTime(String alarmMsg) {
        Log.i(TAG, "setBleAlarmTime:" + alarmMsg);
        bleWriteCharacteristic(GattInfo.ALARM_SERVICE, GattInfo.ALARM_MEASUREMENT, alarmMsg.getBytes());
    }

    private static void setSedentaryRemind(String sedentaryMsg) {
        Log.i(TAG, "setSedentaryRemind:" + sedentaryMsg);
        bleWriteCharacteristic(GattInfo.SEDENTARY_SERVICE, GattInfo.SEDENTARY_MEASUREMENT, sedentaryMsg.getBytes());
    }

    public static void updateBleDevice(final String BinFilePath, final String datFilePath, updateBleCallback callback) {
        bleUpdateTest = 0;
        binFilePath = BinFilePath;
        datFilePath = datFilePath;
        if (callback != null) {
            mUpdateBleCallback = callback;
            File binFile = new File(BinFilePath);
            File datFile = new File(datFilePath);
            if (!binFile.exists() || !datFile.exists()) {
                callback.fail(2);
            } else if (mBtDevice == null || !(mBtDevice == null || mBtDevice.getConnectState() == CONNECT_STATE.CONNECTED)) {
                callback.fail(1);
            } else {
                setDeviceUpdateReady();
                setDeviceUpdateReady();
                setDeviceUpdateReady();
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        BtManagerService.mBtDevice.setConnectState(CONNECT_STATE.DISCONNECTED);
                        BtManagerService.mBluetoothGatt.disconnect();
                    }
                }, 2000);
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        Intent service = new Intent(BtManagerService.mContext, DfuBaseService.class);
                        service.putExtra(DfuBaseService.EXTRA_DEVICE_ADDRESS, BtManagerService.mBtDevice.getMacAddress());
                        service.putExtra(DfuBaseService.EXTRA_FILE_MIME_TYPE, DfuBaseService.MIME_TYPE_OCTET_STREAM);
                        service.putExtra(DfuBaseService.EXTRA_FILE_TYPE, 4);
                        service.putExtra(DfuBaseService.EXTRA_FILE_PATH, BinFilePath);
                        if (!TextUtils.isEmpty(datFilePath)) {
                            service.putExtra(DfuBaseService.EXTRA_INIT_FILE_PATH, datFilePath);
                        }
                        BtManagerService.mContext.startService(service);
                    }
                }, 10000);
                Log.i(TAG, "start dfu service");
            }
        }
    }

    private static void setDeviceUpdateReady() {
        Log.d(TAG, "setDeviceUpdateReady");
        bleWriteCharacteristic(GattInfo.FIRMWARE_READY_SERVICE, GattInfo.FIRMWARE_READY_MEASUMENT, new byte[]{(byte) 1});
    }

    protected static void sendNotifaction(String appName, String pkgName, Notification notification) {
        if (mBtDevice != null && mBtDevice.getConnectState() == CONNECT_STATE.CONNECTED) {
            switch (mBtDevice.getType()) {
                case 1:
                    try {
                        MessageObj notificationMessage = new MessageObj();
                        notificationMessage.setDataHeader(Util.createNotificationHeader());
                        notificationMessage.setDataBody(Util.createNotificationBody(mContext, pkgName, notification));
                        Log.i("chenxinx", "msgContent=" + notificationMessage.getDataBody().getContent());
                        byte[] data = genBytesFromObject(notificationMessage);
                        if (data != null) {
                            mBluetoothManager.sendData(data);
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        if (e != null) {
                            e.printStackTrace();
                            Log.w("Exception during write", e);
                            return;
                        }
                        return;
                    }
                case 2:
                    if (Util.getVersion().startsWith("M2_3_")) {
                        sendAddTaskMessage(4117, appName, 0);
                        return;
                    } else if (Util.getName().equals("U3")) {
                        sendAddTaskMessage(4117, appName + ":" + notification.tickerText, 0);
                        return;
                    } else {
                        sendAddTaskMessage(4117, appName, 0);
                        return;
                    }
                default:
                    return;
            }
        }
    }

    private static void noticeNewWeChatMsg() {
        Log.d(TAG, "noticeReadWeChatMsg");
        bleWriteCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA, new byte[]{(byte) 6});
    }

    private static void setBindingDevice() {
        Log.i(TAG, "setBindingDevice");
        bleWriteCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA, new byte[]{(byte) 41});
    }

    private static void setM2HeartRate() {
        Log.i(TAG, "setM2HeartRate");
        bleWriteCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA, new byte[]{(byte) 49});
    }

    protected static void setOpenFindDevice() {
        Log.d(TAG, "setOpenFindDevice");
        bleWriteCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA, new byte[]{(byte) 38});
    }

    protected static void setCloseFindDevice() {
        Log.d(TAG, "setCloseFindDevice");
        bleWriteCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA, new byte[]{(byte) 39});
    }

    protected static void setDeviceReboot() {
        Log.i(TAG, "setDeviceReboot");
        bleWriteCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA, new byte[]{(byte) 50});
    }

    public static boolean getAntiLost() {
        return Util.getAntiLost() != 0;
    }

    public static void saveAntiLost(boolean ifopen) {
        Util.saveAntiLost(ifopen ? 1 : 0);
    }

    private static void refreshDeviceCache(BluetoothGatt gatt, boolean force) {
        if (force || gatt.getDevice().getBondState() == 10) {
            try {
                Method refresh = gatt.getClass().getMethod("refresh", new Class[0]);
                if (refresh != null) {
                    ((Boolean) refresh.invoke(gatt, new Object[0])).booleanValue();
                }
            } catch (Exception e) {
            }
        }
    }

    private IntentFilter makeFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        return filter;
    }
}

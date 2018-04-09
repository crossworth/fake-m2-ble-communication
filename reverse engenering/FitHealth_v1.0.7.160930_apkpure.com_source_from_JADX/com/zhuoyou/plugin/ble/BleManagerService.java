package com.zhuoyou.plugin.ble;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import com.amap.api.services.core.AMapException;
import com.fithealth.running.R;
import com.mcube.lib.ped.PedometerService;
import com.zhuoyou.plugin.bluetooth.data.Util;
import com.zhuoyou.plugin.custom.CustomAlertDialog;
import com.zhuoyou.plugin.custom.CustomAlertDialog.Builder;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import java.util.List;
import java.util.UUID;
import p031u.aly.cv;

@SuppressLint({"NewApi"})
public class BleManagerService extends Service {
    public static final String ACTION_BATTERY_READ = "com.zhuoyou.plugin.batteryread";
    public static final String ACTION_BINDING_DEVICE = "com.zhuoyou.running.binding.device";
    public static final String ACTION_CLOSE_BLE_PHONE_STEPS = "com.zhuoyou.running.close.ble";
    public static final String ACTION_CLOSE_FIND_DEVICE = "com.zhuoyou.running.close.find.device";
    public static final String ACTION_CLOSE_FIND_PHONE_DIALOG = "com.zhuoyou.running.close.find.phone.dialog";
    public static final String ACTION_CONNECT_BINDED_DEVICE = "com.zhuoyou.plugin.connect.binded.device";
    public static final String ACTION_DISABLE_GSENOR_INFO = "com.zhuoyou.running.disable.gsenor.info";
    public static final String ACTION_DISABLE_HEART_INFO = "com.zhuoyou.running.disable.heart.info";
    public static final String ACTION_DISABLE_SLEEP_INFO = "com.zhuoyou.running.disable.sleep.info";
    public static final String ACTION_DISCONNECT_BINDED_DEVICE = "com.zhuoyou.plugin.disconnect.binded.device";
    public static final String ACTION_FIND_PHONE_REMIND = "com.zhuoyou.running.find.phone.remind";
    public static final String ACTION_GET_DEVICE_NAME = "com.zhuoyou.plugin.get.device.name";
    public static final String ACTION_GET_FIRMWARE_VERSION = "com.zhuoyou.running.get.firmware.version";
    public static final String ACTION_GET_SLEEP_INFO = "com.zhuoyou.running.get.sleep.info";
    public static final String ACTION_GSENOR_DATA_READ = "com.zhuoyou.running.gsensor.data";
    public static final String ACTION_HEART_DATA_READ = "com.zhuoyou.running.heart.data";
    public static final String ACTION_LOW_BATTERY_REMIND = "com.zhuoyou.running.low.battery.remind";
    public static final String ACTION_M2_HEART_RATE = "com.zhuoyou.running.m2.heart.rate";
    public static final String ACTION_NOTICE_CALL_END = "com.zhuoyou.running.notice.call.end";
    public static final String ACTION_NOTICE_MISS_CALL = "com.zhuoyou.running.notice.miss.call";
    public static final String ACTION_NOTICE_NEW_CALL = "com.zhuoyou.running.notice.new.call";
    public static final String ACTION_NOTICE_NEW_SMS = "com.zhuoyou.running.notice.new.sms";
    public static final String ACTION_NOTICE_NEW_WECHAT_MSG = "com.zhuoyou.running.notice.new.wechatMsg";
    public static final String ACTION_NOTICE_READ_SMS = "com.zhuoyou.running.notice.read.sms";
    public static final String ACTION_NOTICE_READ_WECHAT_MSG = "com.zhuoyou.running.notice.read.wechatMsg";
    public static final String ACTION_OPEN_FIND_DEVICE = "com.zhuoyou.running.open.find.device";
    public static final String ACTION_READY_FIRMWARE_UPDATE = "com.zhuoyou.running.update.firmware.ready";
    public static final String ACTION_REMIND_DEVICE_CLOSE_FIND_PHONE = "com.zhuoyou.running.remind.devic  e.close.find.phone";
    public static final String ACTION_STATISTICS_STEP_READ = "com.zhuoyou.running.statistics.step.read";
    public static final String ACTION_STEP_DATA_READ = "com.zhuoyou.running.step.dataread";
    public static final String ACTION_STEP_TOTAL_DATA = "com.zhuoyou.running.total.step";
    public static final String ACTION_TOTALSTEP_DISABLE_DATA_READ = "com.zhuoyou.running.disable.total.step";
    public static final String ACTION_UNBINDING_DEVICE = "com.zhuoyou.running.unbinding.device";
    public static final String ACTION_UPDATE_ALARM_INFO = "com.zhuoyou.running.update.alarm.info";
    public static final String ACTION_UPDATE_SEDENTARY_INFO = "com.zhuoyou.running.update.sedentary.info";
    public static final String ACTION_VIBRATION_REMIND = "com.zhuoyou.plugin.vibration.remind";
    private static final int CLOSE_FIND_DEVICE = 42;
    private static final int CLOSE_HEART_SERVICE = 33;
    private static final int CONNECT_BINDED_DEVICE = 7;
    private static final int DESCRIPTOR_FIND_PHONE = 22;
    private static final int DISABLE_GSENSOR_NOTIFATION = 36;
    private static final int DISABLE_HEART_NOTIFATION = 35;
    private static final int DISABLE_SLEEP_INFO = 18;
    private static final int DISCONNECT_BINDED_DEVICE = 8;
    private static final int FIRMWARE_UPDATE_READY = 24;
    private static final int FIRMWARE_VERSION = 23;
    private static final int GATT_TIMEOUT = 100;
    private static final int GET_BATTERYLV_FROM_REMOTE = 5;
    private static final int GET_DEVICE_NAME = 20;
    private static final int GET_SLEEP_INFO = 17;
    private static final int GET_TIME_FROM_REMOTE = 3;
    private static final int GSENSOR_ENABLE_NOTIFATION = 31;
    private static final int HANDWARE_VERSION = 40;
    private static final int HEART_ENABLE_NOTIFATION = 34;
    private static final int M2_DEVICE_BINDING = 43;
    private static final int M2_DEVICE_UNBINDING = 44;
    private static final int M2_GET_HEART_RATE = 45;
    private static final int MSG_TEST = 99;
    private static final int NOTICE_CALL_END = 13;
    private static final int NOTICE_MISS_CALL = 14;
    private static final int NOTICE_NEW_CALL = 12;
    private static final int NOTICE_NEW_SMS = 10;
    private static final int NOTICE_NEW_WECHAT_MSG = 15;
    private static final int NOTICE_READ_SMS = 11;
    private static final int NOTICE_READ_WECHAT_MSG = 16;
    private static final int NOTIFY_FIND_PHONE = 25;
    private static final int OPEN_FIND_DEVICE = 41;
    private static final int OPEN_HEART_SERVICE = 32;
    private static final int REMIND_DEVICE_CLOSE_FIND_PHONE = 37;
    private static final int SEND_BACK_CONNECT_NOT_VIBRATION = 27;
    private static final int SEND_BATTERYLY_NOTIFY_TO_REMOTE = 28;
    private static final int SEND_TAKE_PICTURE_NOTIFIY_TO_REMOTE = 38;
    private static final int SET_ALARM_TIME = 9;
    private static final int SET_SEDENTARY_TIME = 39;
    private static final int SET_TIME_TO_REMOTE = 4;
    private static final int SET_VIBRATION_REMIND = 6;
    private static final int STEP_DISABLE_NOTIFATION = 2;
    private static final int STEP_ENABLE_NOTIFATION = 1;
    private static final int STEP_GETTOTAL_READY = 21;
    private static final int STEP_TOTAL_READ = 19;
    private static final int STEP_TOTAL_READ_CLOSE = 26;
    private static final int SYNC_TIME_TO_REMTOE = 30;
    private static final String TAG = "BleManagerService";
    private static final int UPDATE_ALARM_TIME = 29;
    public static int low_battery_remind = 1;
    private static BluetoothManager mBluetoothManager;
    private static MediaPlayer media = null;
    private static final Context sContext = RunningApp.getInstance().getApplicationContext();
    protected static BleManagerService sInstance = null;
    Runnable AutoConnectRunnable = new C11575();
    private final IBinder binder = new LocalBinder();
    private boolean connect_status = false;
    private CustomAlertDialog dialog;
    private boolean isPlayer = false;
    private boolean mBTSupport = false;
    private String mBindedDeviceAddress = null;
    private boolean mBleSupport = true;
    private BluetoothLeService mBluetoothLeService = null;
    private BluetoothAdapter mBtAdapter = null;
    private boolean mCloseThread = true;
    private String mDeviceType = null;
    private IntentFilter mFilter;
    private IntentFilter mGATTFilter;
    private final BroadcastReceiver mGattUpdateReceiver = new C11553();
    @SuppressLint({"HandlerLeak"})
    private Handler mHandler = new C11501();
    private boolean mIsConnecting = false;
    private boolean mIsUnbinding = false;
    private BroadcastReceiver mReceiver = new C11512();
    private final ServiceConnection mServiceConnection = new C11564();
    private List<BluetoothGattService> mServiceList = null;
    private Object msgObj = null;
    private SharedPreferences msharepreference;
    private byte[] remindInfo = null;

    class C11501 extends Handler {
        C11501() {
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r11) {
            /*
            r10 = this;
            r6 = r11.what;
            switch(r6) {
                case 1: goto L_0x0009;
                case 2: goto L_0x0042;
                case 3: goto L_0x00cf;
                case 4: goto L_0x00ed;
                case 5: goto L_0x013b;
                case 6: goto L_0x014a;
                case 7: goto L_0x0159;
                case 8: goto L_0x01ad;
                case 9: goto L_0x01d5;
                case 10: goto L_0x0207;
                case 11: goto L_0x02bf;
                case 12: goto L_0x0362;
                case 13: goto L_0x0426;
                case 14: goto L_0x04c9;
                case 15: goto L_0x0518;
                case 16: goto L_0x05bb;
                case 17: goto L_0x065e;
                case 18: goto L_0x0675;
                case 19: goto L_0x0082;
                case 20: goto L_0x069a;
                case 21: goto L_0x0065;
                case 22: goto L_0x06c5;
                case 23: goto L_0x06d5;
                case 24: goto L_0x0707;
                case 25: goto L_0x06a9;
                case 26: goto L_0x00a1;
                case 27: goto L_0x0125;
                case 28: goto L_0x00de;
                case 29: goto L_0x01e8;
                case 30: goto L_0x0108;
                case 31: goto L_0x001f;
                case 32: goto L_0x0716;
                case 33: goto L_0x0732;
                case 34: goto L_0x074e;
                case 35: goto L_0x075e;
                case 36: goto L_0x076e;
                case 37: goto L_0x078b;
                case 38: goto L_0x079a;
                case 39: goto L_0x07a9;
                case 40: goto L_0x06f1;
                case 41: goto L_0x07bc;
                case 42: goto L_0x07cb;
                case 43: goto L_0x07da;
                case 44: goto L_0x07e9;
                case 45: goto L_0x07f8;
                case 46: goto L_0x0005;
                case 47: goto L_0x0005;
                case 48: goto L_0x0005;
                case 49: goto L_0x0005;
                case 50: goto L_0x0005;
                case 51: goto L_0x0005;
                case 52: goto L_0x0005;
                case 53: goto L_0x0005;
                case 54: goto L_0x0005;
                case 55: goto L_0x0005;
                case 56: goto L_0x0005;
                case 57: goto L_0x0005;
                case 58: goto L_0x0005;
                case 59: goto L_0x0005;
                case 60: goto L_0x0005;
                case 61: goto L_0x0005;
                case 62: goto L_0x0005;
                case 63: goto L_0x0005;
                case 64: goto L_0x0005;
                case 65: goto L_0x0005;
                case 66: goto L_0x0005;
                case 67: goto L_0x0005;
                case 68: goto L_0x0005;
                case 69: goto L_0x0005;
                case 70: goto L_0x0005;
                case 71: goto L_0x0005;
                case 72: goto L_0x0005;
                case 73: goto L_0x0005;
                case 74: goto L_0x0005;
                case 75: goto L_0x0005;
                case 76: goto L_0x0005;
                case 77: goto L_0x0005;
                case 78: goto L_0x0005;
                case 79: goto L_0x0005;
                case 80: goto L_0x0005;
                case 81: goto L_0x0005;
                case 82: goto L_0x0005;
                case 83: goto L_0x0005;
                case 84: goto L_0x0005;
                case 85: goto L_0x0005;
                case 86: goto L_0x0005;
                case 87: goto L_0x0005;
                case 88: goto L_0x0005;
                case 89: goto L_0x0005;
                case 90: goto L_0x0005;
                case 91: goto L_0x0005;
                case 92: goto L_0x0005;
                case 93: goto L_0x0005;
                case 94: goto L_0x0005;
                case 95: goto L_0x0005;
                case 96: goto L_0x0005;
                case 97: goto L_0x0005;
                case 98: goto L_0x0005;
                case 99: goto L_0x00c0;
                default: goto L_0x0005;
            };
        L_0x0005:
            super.handleMessage(r11);
            return;
        L_0x0009:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x0011:
            r6 = "BleManagerService";
            r7 = "STEP_ENABLE_NOTIFATION";
            android.util.Log.d(r6, r7);
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = 1;
            r6.getRemoteDeviceSteps(r7);
            goto L_0x0005;
        L_0x001f:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x0027:
            r6 = "BleManagerService";
            r7 = "GSENSOR_ENABLE_NOTIFATION";
            android.util.Log.d(r6, r7);
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = 1;
            r6.getRemoteDeviceGsensor(r7);
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mHandler;
            r7 = 32;
            r8 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
            r6.sendEmptyMessageDelayed(r7, r8);
            goto L_0x0005;
        L_0x0042:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x004a:
            r6 = "BleManagerService";
            r7 = "STEP_DISABLE_NOTIFATION";
            android.util.Log.d(r6, r7);
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = 0;
            r6.getRemoteDeviceSteps(r7);
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mHandler;
            r7 = 17;
            r8 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
            r6.sendEmptyMessageDelayed(r7, r8);
            goto L_0x0005;
        L_0x0065:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x006d:
            r6 = "BleManagerService";
            r7 = "STEP_GETTOTAL_READY";
            android.util.Log.d(r6, r7);
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mHandler;
            r7 = 19;
            r8 = 2000; // 0x7d0 float:2.803E-42 double:9.88E-321;
            r6.sendEmptyMessageDelayed(r7, r8);
            goto L_0x0005;
        L_0x0082:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x008a:
            r6 = "BleManagerService";
            r7 = "STEP_TOTAL_READ";
            android.util.Log.d(r6, r7);
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.getRemoteStepsDescriptor();
            if (r6 != 0) goto L_0x0005;
        L_0x0099:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = 1;
            r6.getRemoteDeviceTotalSteps(r7);
            goto L_0x0005;
        L_0x00a1:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x00a9:
            r6 = "BleManagerService";
            r7 = "STEP_TOTAL_READ_CLOSE";
            android.util.Log.d(r6, r7);
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.getRemoteStepsDescriptor();
            if (r6 != 0) goto L_0x0005;
        L_0x00b8:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = 0;
            r6.getRemoteDeviceTotalSteps(r7);
            goto L_0x0005;
        L_0x00c0:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mHandler;
            r7 = 99;
            r8 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
            r6.sendEmptyMessageDelayed(r7, r8);
            goto L_0x0005;
        L_0x00cf:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x00d7:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.getRemoteDeviceTimeAndAlarm();
            goto L_0x0005;
        L_0x00de:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x00e6:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.openBatteryNotify();
            goto L_0x0005;
        L_0x00ed:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x00f5:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.setRemoteDeviceTime();
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mHandler;
            r7 = 3;
            r8 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
            r6.sendEmptyMessageDelayed(r7, r8);
            goto L_0x0005;
        L_0x0108:
            r6 = 1;
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.connect_status;
            if (r6 != r7) goto L_0x0125;
        L_0x0111:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.IsSupportedServicesListEmpty();
            if (r6 != 0) goto L_0x0125;
        L_0x0119:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.setRemoteDeviceTime();
            r6 = "hph";
            r7 = "setRemoteDeviceTime==";
            android.util.Log.i(r6, r7);
        L_0x0125:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x012d:
            r6 = "BleManagerService";
            r7 = "SEND_BACK_CONNECT_NOT_VIBRATION";
            android.util.Log.d(r6, r7);
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.controlRemoteDeviceVibration();
            goto L_0x0005;
        L_0x013b:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x0143:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.getRemoteDeviceBatteryInfo();
            goto L_0x0005;
        L_0x014a:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x0152:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.SetVibrationRemind();
            goto L_0x0005;
        L_0x0159:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x0161:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.sInstance;
            r0 = com.zhuoyou.plugin.bluetooth.data.Util.getLatestConnectDeviceAddress(r6);
            r4 = r11.getData();
            if (r4 == 0) goto L_0x017a;
        L_0x016d:
            r6 = "address";
            r5 = r4.getString(r6);
            r6 = android.text.TextUtils.isEmpty(r5);
            if (r6 != 0) goto L_0x017a;
        L_0x0179:
            r0 = r5;
        L_0x017a:
            r6 = android.text.TextUtils.isEmpty(r0);
            if (r6 != 0) goto L_0x019f;
        L_0x0180:
            r6 = "BleManagerService";
            r7 = new java.lang.StringBuilder;
            r7.<init>();
            r8 = "CONNECT_BINDED_DEVICE,Address:";
            r7 = r7.append(r8);
            r7 = r7.append(r0);
            r7 = r7.toString();
            android.util.Log.i(r6, r7);
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.ConnectToDeviceByAddress(r0);
            goto L_0x0005;
        L_0x019f:
            r3 = new android.content.Intent;
            r6 = "com.zhuoyou.running.connect.failed";
            r3.<init>(r6);
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.sendBroadcast(r3);
            goto L_0x0005;
        L_0x01ad:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x01b5:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mBindedDeviceAddress;
            r6.disConnectDeviceByAddress(r7);
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = 0;
            r6.mBindedDeviceAddress = r7;
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = 0;
            r6.mIsUnbinding = r7;
            r6 = "BleManagerService";
            r7 = "disConnectDeviceByAddress(mBindedDeviceAddress)";
            android.util.Log.i(r6, r7);
            goto L_0x0005;
        L_0x01d5:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x01dd:
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r11.obj;
            r6 = (java.lang.String) r6;
            r7.setAlarmTime(r6);
            goto L_0x0005;
        L_0x01e8:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0207;
        L_0x01f0:
            r2 = 0;
        L_0x01f1:
            r6 = 3;
            if (r2 >= r6) goto L_0x0207;
        L_0x01f4:
            r1 = com.zhuoyou.plugin.running.Tools.getUpdateAlarmBrain(r2);
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.setAlarmTime(r1);
            r6 = "BleManagerService";
            r7 = "setAlarmTime";
            android.util.Log.i(r6, r7);
            r2 = r2 + 1;
            goto L_0x01f1;
        L_0x0207:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x020f:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mDeviceType;
            if (r6 == 0) goto L_0x0005;
        L_0x0217:
            r6 = "Unik 1";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x022c;
        L_0x0225:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeNewSMS();
            goto L_0x0005;
        L_0x022c:
            r6 = "Unik 2";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0241;
        L_0x023a:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeNewSMS();
            goto L_0x0005;
        L_0x0241:
            r6 = "LEO";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0256;
        L_0x024f:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeNewSMS();
            goto L_0x0005;
        L_0x0256:
            r6 = "A7";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x026b;
        L_0x0264:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeNewSMS();
            goto L_0x0005;
        L_0x026b:
            r6 = "T-Band";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0280;
        L_0x0279:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeNewSMS();
            goto L_0x0005;
        L_0x0280:
            r6 = "Rumor-1";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0295;
        L_0x028e:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeNewSMS();
            goto L_0x0005;
        L_0x0295:
            r6 = "Rumor-2";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x02aa;
        L_0x02a3:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeNewSMS();
            goto L_0x0005;
        L_0x02aa:
            r6 = "M2";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0005;
        L_0x02b8:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeNewSMS();
            goto L_0x0005;
        L_0x02bf:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x02c7:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mDeviceType;
            if (r6 == 0) goto L_0x0005;
        L_0x02cf:
            r6 = "Unik 2";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x02e4;
        L_0x02dd:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeReadSMS();
            goto L_0x0005;
        L_0x02e4:
            r6 = "LEO";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x02f9;
        L_0x02f2:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeReadSMS();
            goto L_0x0005;
        L_0x02f9:
            r6 = "A7";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x030e;
        L_0x0307:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeReadSMS();
            goto L_0x0005;
        L_0x030e:
            r6 = "T-Band";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0323;
        L_0x031c:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeReadSMS();
            goto L_0x0005;
        L_0x0323:
            r6 = "Rumor-1";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0338;
        L_0x0331:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeReadSMS();
            goto L_0x0005;
        L_0x0338:
            r6 = "Rumor-2";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x034d;
        L_0x0346:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeReadSMS();
            goto L_0x0005;
        L_0x034d:
            r6 = "M2";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0005;
        L_0x035b:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeReadSMS();
            goto L_0x0005;
        L_0x0362:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x036a:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mDeviceType;
            if (r6 == 0) goto L_0x0005;
        L_0x0372:
            r6 = "Unik 1";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0387;
        L_0x0380:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeNewCall();
            goto L_0x0005;
        L_0x0387:
            r6 = "Unik 2";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x03a0;
        L_0x0395:
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r11.obj;
            r6 = (java.lang.String) r6;
            r7.noticeNewCallPro(r6);
            goto L_0x0005;
        L_0x03a0:
            r6 = "LEO";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x03b9;
        L_0x03ae:
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r11.obj;
            r6 = (java.lang.String) r6;
            r7.noticeNewCallNameOrNum(r6);
            goto L_0x0005;
        L_0x03b9:
            r6 = "A7";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x03d2;
        L_0x03c7:
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r11.obj;
            r6 = (java.lang.String) r6;
            r7.noticeNewCallPro(r6);
            goto L_0x0005;
        L_0x03d2:
            r6 = "T-Band";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x03e7;
        L_0x03e0:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeNewCall();
            goto L_0x0005;
        L_0x03e7:
            r6 = "Rumor-1";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x03fc;
        L_0x03f5:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeNewCall();
            goto L_0x0005;
        L_0x03fc:
            r6 = "Rumor-2";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0411;
        L_0x040a:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeNewCall();
            goto L_0x0005;
        L_0x0411:
            r6 = "M2";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0005;
        L_0x041f:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeNewCallM2();
            goto L_0x0005;
        L_0x0426:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x042e:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mDeviceType;
            if (r6 == 0) goto L_0x0005;
        L_0x0436:
            r6 = "Unik 2";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x044b;
        L_0x0444:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeCallEnd();
            goto L_0x0005;
        L_0x044b:
            r6 = "LEO";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0460;
        L_0x0459:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeCallEnd();
            goto L_0x0005;
        L_0x0460:
            r6 = "A7";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0475;
        L_0x046e:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeCallEnd();
            goto L_0x0005;
        L_0x0475:
            r6 = "T-Band";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x048a;
        L_0x0483:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeCallEnd();
            goto L_0x0005;
        L_0x048a:
            r6 = "Rumor-1";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x049f;
        L_0x0498:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeCallEnd();
            goto L_0x0005;
        L_0x049f:
            r6 = "Rumor-2";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x04b4;
        L_0x04ad:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeCallEnd();
            goto L_0x0005;
        L_0x04b4:
            r6 = "M2";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0005;
        L_0x04c2:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeCallEnd();
            goto L_0x0005;
        L_0x04c9:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x04d1:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mDeviceType;
            if (r6 == 0) goto L_0x0005;
        L_0x04d9:
            r6 = "Unik 2";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x04ee;
        L_0x04e7:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeMissCall();
            goto L_0x0005;
        L_0x04ee:
            r6 = "LEO";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0503;
        L_0x04fc:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeMissCall();
            goto L_0x0005;
        L_0x0503:
            r6 = "A7";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0005;
        L_0x0511:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeMissCall();
            goto L_0x0005;
        L_0x0518:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x0520:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mDeviceType;
            if (r6 == 0) goto L_0x0005;
        L_0x0528:
            r6 = "Unik 2";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x053d;
        L_0x0536:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeNewWeChatMsg();
            goto L_0x0005;
        L_0x053d:
            r6 = "LEO";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0552;
        L_0x054b:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeNewWeChatMsg();
            goto L_0x0005;
        L_0x0552:
            r6 = "A7";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0567;
        L_0x0560:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeNewWeChatMsg();
            goto L_0x0005;
        L_0x0567:
            r6 = "T-Band";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x057c;
        L_0x0575:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeNewWeChatMsg();
            goto L_0x0005;
        L_0x057c:
            r6 = "Rumor-1";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0591;
        L_0x058a:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeNewWeChatMsg();
            goto L_0x0005;
        L_0x0591:
            r6 = "Rumor-2";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x05a6;
        L_0x059f:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeNewWeChatMsg();
            goto L_0x0005;
        L_0x05a6:
            r6 = "M2";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0005;
        L_0x05b4:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeNewWeChatMsg();
            goto L_0x0005;
        L_0x05bb:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x05c3:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mDeviceType;
            if (r6 == 0) goto L_0x0005;
        L_0x05cb:
            r6 = "Unik 2";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x05e0;
        L_0x05d9:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeReadWeChatMsg();
            goto L_0x0005;
        L_0x05e0:
            r6 = "LEO";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x05f5;
        L_0x05ee:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeReadWeChatMsg();
            goto L_0x0005;
        L_0x05f5:
            r6 = "A7";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x060a;
        L_0x0603:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeReadWeChatMsg();
            goto L_0x0005;
        L_0x060a:
            r6 = "T-Band";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x061f;
        L_0x0618:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeReadWeChatMsg();
            goto L_0x0005;
        L_0x061f:
            r6 = "Rumor-1";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0634;
        L_0x062d:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeReadWeChatMsg();
            goto L_0x0005;
        L_0x0634:
            r6 = "Rumor-2";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0649;
        L_0x0642:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeReadWeChatMsg();
            goto L_0x0005;
        L_0x0649:
            r6 = "M2";
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = r7.mDeviceType;
            r6 = r6.equals(r7);
            if (r6 == 0) goto L_0x0005;
        L_0x0657:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeReadWeChatMsg();
            goto L_0x0005;
        L_0x065e:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x0666:
            r6 = "BleManagerService";
            r7 = "GET_SLEEP_INFO";
            android.util.Log.i(r6, r7);
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = 1;
            r6.getSleepInfoFromRemote(r7);
            goto L_0x0005;
        L_0x0675:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x067d:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.getSleepInfoDescriptor();
            if (r6 == 0) goto L_0x0005;
        L_0x0685:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = 0;
            r6.getSleepInfoFromRemote(r7);
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mHandler;
            r7 = 19;
            r8 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
            r6.sendEmptyMessageDelayed(r7, r8);
            goto L_0x0005;
        L_0x069a:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x06a2:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.getDeviceName();
            goto L_0x0005;
        L_0x06a9:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x06b1:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeFindPhone();
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mHandler;
            r7 = 22;
            r8 = 600; // 0x258 float:8.41E-43 double:2.964E-321;
            r6.sendEmptyMessageDelayed(r7, r8);
            goto L_0x0005;
        L_0x06c5:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x06cd:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = 1;
            r6.desciptorFindPhone(r7);
            goto L_0x0005;
        L_0x06d5:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x06dd:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.getDeviceVersion();
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mHandler;
            r7 = 40;
            r8 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
            r6.sendEmptyMessageDelayed(r7, r8);
            goto L_0x0005;
        L_0x06f1:
            r6 = "BleManagerService";
            r7 = "HANDWARE_VERSION=getDeviceHardware";
            android.util.Log.i(r6, r7);
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x0700:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.getDeviceHardware();
            goto L_0x0005;
        L_0x0707:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x070f:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.setDeviceUpdateReady();
            goto L_0x0005;
        L_0x0716:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x071e:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.openHeartService();
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mHandler;
            r7 = 34;
            r8 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
            r6.sendEmptyMessageDelayed(r7, r8);
            goto L_0x0005;
        L_0x0732:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x073a:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.closeHeartService();
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mHandler;
            r7 = 35;
            r8 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
            r6.sendEmptyMessageDelayed(r7, r8);
            goto L_0x0005;
        L_0x074e:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x0756:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = 1;
            r6.getRemoteDeviceHeart(r7);
            goto L_0x0005;
        L_0x075e:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x0766:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = 0;
            r6.getRemoteDeviceHeart(r7);
            goto L_0x0005;
        L_0x076e:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x077c;
        L_0x0776:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r7 = 0;
            r6.getRemoteDeviceGsensor(r7);
        L_0x077c:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mHandler;
            r7 = 33;
            r8 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
            r6.sendEmptyMessageDelayed(r7, r8);
            goto L_0x0005;
        L_0x078b:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x0793:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.noticeDoneFindPhone();
            goto L_0x0005;
        L_0x079a:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x07a2:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.openTakePictureNotifiy();
            goto L_0x0005;
        L_0x07a9:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x07b1:
            r7 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r11.obj;
            r6 = (java.lang.String) r6;
            r7.setSedentaryTime(r6);
            goto L_0x0005;
        L_0x07bc:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x07c4:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.setOpenFindDevice();
            goto L_0x0005;
        L_0x07cb:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x07d3:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.setCloseFindDevice();
            goto L_0x0005;
        L_0x07da:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x07e2:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.setBindingDevice();
            goto L_0x0005;
        L_0x07e9:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x07f1:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.setUnbindingDevice();
            goto L_0x0005;
        L_0x07f8:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6 = r6.mBluetoothLeService;
            if (r6 == 0) goto L_0x0005;
        L_0x0800:
            r6 = com.zhuoyou.plugin.ble.BleManagerService.this;
            r6.setM2HeartRate();
            goto L_0x0005;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zhuoyou.plugin.ble.BleManagerService.1.handleMessage(android.os.Message):void");
        }
    }

    class C11512 extends BroadcastReceiver {
        C11512() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(action)) {
                switch (BleManagerService.this.mBtAdapter.getState()) {
                    case 10:
                        BleManagerService.this.mBTSupport = false;
                        return;
                    case 12:
                        BleManagerService.this.mBTSupport = true;
                        BleManagerService.this.startBluetoothLeService();
                        return;
                    default:
                        Log.w(BleManagerService.TAG, "Action STATE CHANGED not processed ");
                        return;
                }
            } else if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_GATT_CONNECTED");
                BleManagerService.this.connect_status = true;
                BleManagerService.this.setIsConnecting(true);
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_GATT_DISCONNECTED");
                BleManagerService.this.connect_status = false;
                BleManagerService.this.setIsConnecting(false);
                BleManagerService.this.mDeviceType = null;
                if (BleManagerService.this.mBluetoothLeService != null) {
                    BleManagerService.this.mBluetoothLeService.close();
                }
            }
        }
    }

    class C11553 extends BroadcastReceiver {

        class C11521 implements OnClickListener {
            C11521() {
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }

        class C11532 implements OnClickListener {
            C11532() {
            }

            public void onClick(DialogInterface dialog, int which) {
                BleManagerService.this.stopService(new Intent(BleManagerService.this.getApplicationContext(), PedometerService.class));
                Tools.setPhonePedState(false);
                dialog.dismiss();
            }
        }

        class C11543 implements OnClickListener {
            C11543() {
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Tools.setOpenDialogState(true);
            }
        }

        C11553() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int status = intent.getIntExtra(BluetoothLeService.EXTRA_STATUS, 0);
            if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                if (status == 0) {
                    BleManagerService.this.setIsConnecting(false);
                    Log.d(BleManagerService.TAG, "mGattUpdateReceiver--find device services");
                    BleManagerService.this.getSupportedrServices();
                    if (!Tools.getConnectNotVibtation()) {
                        Log.d(BleManagerService.TAG, "Tools.getConnectNotVibtation()= " + Tools.getConnectNotVibtation());
                        BleManagerService.this.mHandler.sendEmptyMessageDelayed(27, 1000);
                        Tools.setConnectNotVibtation(true);
                    }
                    if (Tools.getIsSendBindingDevice()) {
                        BleManagerService.this.mHandler.sendEmptyMessageDelayed(43, 1800);
                        Tools.setIsSendBindingDevice(false);
                    }
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(4, 2800);
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(5, 4800);
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(25, 5800);
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(28, 7000);
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(20, 8000);
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(38, 9000);
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(23, 10000);
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(99, 12000);
                } else {
                    Log.d(BleManagerService.TAG, "gatt Service discovery failed");
                    return;
                }
            }
            if (!(!BleManagerService.ACTION_CONNECT_BINDED_DEVICE.equals(action) || BleManagerService.this.connect_status || BleManagerService.this.IsConnecting())) {
                BleManagerService.this.mDeviceType = intent.getStringExtra("deviceName");
                String address = intent.getStringExtra("deviceAddress");
                Log.d(BleManagerService.TAG, "ACTION_CONNECT_BINDED_DEVICE,name:" + BleManagerService.this.mDeviceType + ",address:" + address);
                Bundle mBundle = new Bundle();
                mBundle.putString("name", BleManagerService.this.mDeviceType);
                mBundle.putString("address", address);
                Message msg = BleManagerService.this.mHandler.obtainMessage();
                msg.what = 7;
                msg.setData(mBundle);
                BleManagerService.this.mHandler.sendMessageDelayed(msg, 500);
            }
            if (BleManagerService.ACTION_DISCONNECT_BINDED_DEVICE.equals(action) && true == BleManagerService.this.connect_status) {
                Log.d(BleManagerService.TAG, "ACTION_DISCONNECT_BINDED_DEVICE");
                Util.handUnlink(BleManagerService.this, true);
                BleManagerService.this.mIsUnbinding = true;
                BleManagerService.this.mBindedDeviceAddress = intent.getStringExtra("BINDED_DEVICE_ADDRESS");
                Log.d(BleManagerService.TAG, "mBindedDeviceAddress111" + BleManagerService.this.mBindedDeviceAddress);
                if (!(BleManagerService.this.mBindedDeviceAddress == null || BleManagerService.this.mBindedDeviceAddress.equals("0"))) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(8, 0);
                }
                Log.i(BleManagerService.TAG, "mHandler.sendEmptyMessageDelayed(DISCONNECT_BINDED_DEVICE, 0)");
            }
            if (BleManagerService.ACTION_UPDATE_SEDENTARY_INFO.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_UPDATE_SEDENTARY_INFO");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    msg = BleManagerService.this.mHandler.obtainMessage();
                    msg.what = 39;
                    msg.obj = intent.getStringExtra("sedentary_info");
                    BleManagerService.this.mHandler.sendMessageDelayed(msg, 1000);
                    Log.i(BleManagerService.TAG, "msg.obj SET_SEDENTARY_TIME=" + msg.obj);
                }
            }
            if (BleManagerService.ACTION_BATTERY_READ.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_BATTERY_READ");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(5, 2000);
                }
            }
            if (BleManagerService.ACTION_STEP_DATA_READ.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_STEP_DATA_READ");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(1, 500);
                }
            }
            if (BleManagerService.ACTION_GSENOR_DATA_READ.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_GSENOR_DATA_READ");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(31, 500);
                }
            }
            if (BleManagerService.ACTION_HEART_DATA_READ.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_HEART_DATA_READ");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(32, 500);
                }
            }
            if (BleManagerService.ACTION_TOTALSTEP_DISABLE_DATA_READ.equals(action) && status == 0) {
                Log.i(BleManagerService.TAG, "receiver broadcast ACTION_TOTALSTEP_DISABLE_DATA_READ");
                BleManagerService.this.mHandler.sendEmptyMessageDelayed(26, 500);
                BleManagerService.this.mHandler.sendEmptyMessageDelayed(30, 1000);
                Log.i("hph", "(SYNC_TIME_TO_REMTOE, 1000);");
            }
            if (BleManagerService.ACTION_STATISTICS_STEP_READ.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_STATISTICS_STEP_READ");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(2, 500);
                }
            }
            if (BleManagerService.ACTION_STEP_TOTAL_DATA.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_TOTAL_STEP");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(21, 1000);
                }
            }
            if (BleManagerService.ACTION_GET_DEVICE_NAME.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_GET_DEVICE_NAME");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(20, 100);
                }
            }
            if (BleManagerService.ACTION_CLOSE_FIND_PHONE_DIALOG.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_CLOSE_FIND_PHONE_DIALOG");
                if (!(true != BleManagerService.this.connect_status || BleManagerService.this.IsSupportedServicesListEmpty() || BleManagerService.this.dialog == null)) {
                    BleManagerService.this.dialog.dismiss();
                }
            }
            if (BleManagerService.ACTION_UPDATE_ALARM_INFO.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_UPDATE_ALARM_INFO");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    msg = BleManagerService.this.mHandler.obtainMessage();
                    msg.what = 9;
                    msg.obj = intent.getStringExtra("alarmInfo");
                    BleManagerService.this.mHandler.sendMessageDelayed(msg, 1000);
                }
            }
            if (BleManagerService.ACTION_NOTICE_NEW_SMS.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_NOTICE_NEW_SMS");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(10, 100);
                }
            }
            if (BleManagerService.ACTION_NOTICE_READ_SMS.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_NOTICE_READ_SMS");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(11, 100);
                }
            }
            if (BleManagerService.ACTION_NOTICE_NEW_CALL.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_NOTICE_NEW_CALL");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    msg = BleManagerService.this.mHandler.obtainMessage();
                    msg.what = 12;
                    msg.obj = intent.getStringExtra("incomingNumber");
                    Log.i("zhangweinan", "msg.obj = " + msg.obj);
                    BleManagerService.this.mHandler.sendMessageDelayed(msg, 100);
                }
            }
            if (BleManagerService.ACTION_NOTICE_CALL_END.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_NOTICE_CALL_END");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(13, 100);
                }
            }
            if (BleManagerService.ACTION_NOTICE_MISS_CALL.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_NOTICE_MISS_CALL");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(14, 100);
                }
            }
            if (BleManagerService.ACTION_NOTICE_NEW_WECHAT_MSG.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_NOTICE_NEW_WECHAT_MSG");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(15, 100);
                }
            }
            if (BleManagerService.ACTION_NOTICE_READ_WECHAT_MSG.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_NOTICE_READ_WECHAT_MSG");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(16, 100);
                }
            }
            if (BleManagerService.ACTION_GET_SLEEP_INFO.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_GET_SLEEP_INFO");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(17, 1000);
                }
            }
            if (BleManagerService.ACTION_DISABLE_SLEEP_INFO.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_DISABLE_SLEEP_INFO");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(18, 500);
                }
            }
            if (BleManagerService.ACTION_DISABLE_HEART_INFO.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_DISABLE_HEART_INFO");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(33, 500);
                }
            }
            if (BleManagerService.ACTION_GET_FIRMWARE_VERSION.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_GET_FIRMWARE_VERSION");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(23, 100);
                }
            }
            if (BleManagerService.ACTION_READY_FIRMWARE_UPDATE.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_READY_FIRMWARE_UPDATE");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    Util.handUnlink(BleManagerService.this, true);
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(24, 100);
                }
            }
            if (BleManagerService.ACTION_REMIND_DEVICE_CLOSE_FIND_PHONE.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_REMIND_DEVICE_CLOSE_FIND_PHONE");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(37, 100);
                }
            }
            if (BleManagerService.ACTION_OPEN_FIND_DEVICE.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_OPEN_FIND_DEVICE");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(41, 100);
                }
            }
            if (BleManagerService.ACTION_OPEN_FIND_DEVICE.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_OPEN_FIND_DEVICE");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(42, 100);
                }
            }
            if (BleManagerService.ACTION_BINDING_DEVICE.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_BINDING_DEVICE");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(43, 100);
                }
            }
            if (BleManagerService.ACTION_UNBINDING_DEVICE.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_UNBINDING_DEVICE");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(44, 100);
                }
            }
            if (BleManagerService.ACTION_M2_HEART_RATE.equals(action)) {
                Log.d(BleManagerService.TAG, "ACTION_M2_HEART_RATE");
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.mHandler.sendEmptyMessageDelayed(45, 100);
                }
            }
            if (BleManagerService.ACTION_LOW_BATTERY_REMIND.equals(action)) {
                Log.d(BleManagerService.TAG, BleManagerService.ACTION_LOW_BATTERY_REMIND);
                if (BleManagerService.low_battery_remind == 1 && true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    Builder builder = new Builder(BleManagerService.sContext);
                    builder.setTitle((int) R.string.alert_title);
                    builder.setMessage((int) R.string.low_battery);
                    builder.setPositiveButton((int) R.string.ok, new C11521());
                    CustomAlertDialog dialog = builder.create();
                    dialog.getWindow().setType(AMapException.CODE_AMAP_ENGINE_TABLEID_NOT_EXIST);
                    dialog.show();
                    BleManagerService.low_battery_remind = 2;
                }
            }
            if (BleManagerService.ACTION_CLOSE_BLE_PHONE_STEPS.equals(action)) {
                Log.d(BleManagerService.TAG, BleManagerService.ACTION_CLOSE_BLE_PHONE_STEPS);
                if (true == Tools.getPhonePedState() && !Tools.getOpenDialogState()) {
                    builder = new Builder(BleManagerService.sContext);
                    builder.setTitle((int) R.string.alert_title);
                    builder.setMessage((int) R.string.close_phone_steps);
                    builder.setPositiveButton((int) R.string.ok, new C11532());
                    builder.setNegativeButton((int) R.string.cancle, new C11543());
                    builder.setCancelable(Boolean.valueOf(false));
                    dialog = builder.create();
                    dialog.getWindow().setType(AMapException.CODE_AMAP_ENGINE_TABLEID_NOT_EXIST);
                    dialog.show();
                }
            }
            if (BleManagerService.ACTION_FIND_PHONE_REMIND.equals(action)) {
                Log.d(BleManagerService.TAG, BleManagerService.ACTION_FIND_PHONE_REMIND);
                if (true == BleManagerService.this.connect_status && !BleManagerService.this.IsSupportedServicesListEmpty()) {
                    BleManagerService.this.showFindPhoneDialog();
                    BleManagerService.this.isPlayer = true;
                    if (BleManagerService.this.isPlayer) {
                        new MusicThread().start();
                    }
                }
            }
            if (BluetoothLeService.ACTION_DATA_READ.equals(action) && status == 0) {
                Log.d(BleManagerService.TAG, "ACTION_DATA_READ");
            }
            if (BluetoothLeService.ACTION_DATA_WRITE.equals(action) && status == 0) {
                Log.d(BleManagerService.TAG, "ACTION_DATA_WRITE");
            }
            if (BluetoothLeService.ACTION_DATA_NOTIFY.equals(action) && status == 0) {
                Log.d(BleManagerService.TAG, "ACTION_DATA_NOTIFY");
            }
        }
    }

    class C11564 implements ServiceConnection {
        C11564() {
        }

        public void onServiceConnected(ComponentName componentName, IBinder service) {
            BleManagerService.this.mBluetoothLeService = ((com.zhuoyou.plugin.ble.BluetoothLeService.LocalBinder) service).getService();
            if (!BleManagerService.this.mBluetoothLeService.initialize()) {
                Log.e(BleManagerService.TAG, "Unable to initialize BluetoothLeService");
                BleManagerService.this.stopSelf();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            BleManagerService.this.mBluetoothLeService = null;
            Log.i(BleManagerService.TAG, "BluetoothLeService disconnected");
        }
    }

    class C11575 implements Runnable {
        C11575() {
        }

        public void run() {
            while (BleManagerService.this.mBleSupport && BleManagerService.this.mCloseThread) {
                Log.d(BleManagerService.TAG, "AutoConnectRunnable:" + System.currentTimeMillis());
                Log.d(BleManagerService.TAG, "mBTSupport:" + BleManagerService.this.mBTSupport);
                if (BleManagerService.this.mBTSupport) {
                    Log.d(BleManagerService.TAG, "mIsConnecting:" + BleManagerService.this.mIsConnecting);
                    Log.d(BleManagerService.TAG, "mIsUnbinding:" + BleManagerService.this.mIsUnbinding);
                    Log.d(BleManagerService.TAG, "isConnected():" + BleManagerService.this.isConnected());
                    Log.d(BleManagerService.TAG, "isBinded():" + BleManagerService.this.isBinded());
                    if (BleManagerService.this.mIsConnecting || BleManagerService.this.mIsUnbinding || BleManagerService.this.isConnected() || !BleManagerService.this.isBinded()) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            if (BleManagerService.this.mBluetoothLeService != null) {
                                String deviceAddress = BleManagerService.this.msharepreference.getString("BLE_BIND_STATE", "0");
                                Log.d(BleManagerService.TAG, "deviceAddress" + deviceAddress);
                                if (!deviceAddress.equals("0")) {
                                    BleManagerService.this.ConnectToDeviceByAddress(deviceAddress);
                                    BleManagerService.this.setIsConnecting(true);
                                }
                            }
                            Thread.sleep(5000);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                    }
                } else {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e22) {
                        e22.printStackTrace();
                    }
                }
            }
        }
    }

    class C11586 implements OnClickListener {
        C11586() {
        }

        public void onClick(DialogInterface dialog, int which) {
            BleManagerService.stopMusic();
            BleManagerService.this.isPlayer = false;
            BleManagerService.this.noticeDoneFindPhone();
            dialog.dismiss();
        }
    }

    public class LocalBinder extends Binder {
        public BleManagerService getService() {
            return BleManagerService.this;
        }
    }

    public static class MusicThread extends Thread {
        public void run() {
            if (BleManagerService.media == null) {
                BleManagerService.media = MediaPlayer.create(BleManagerService.sContext, R.raw.find_phone);
            }
            if (BleManagerService.media != null && !BleManagerService.media.isPlaying()) {
                BleManagerService.media.start();
                BleManagerService.media.setLooping(true);
            }
        }
    }

    public boolean initialize() {
        Log.d(TAG, "BleManagerService initialize");
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService("bluetooth");
            if (mBluetoothManager == null) {
                this.mBTSupport = false;
                return false;
            }
        }
        this.mBtAdapter = mBluetoothManager.getAdapter();
        if (this.mBtAdapter == null) {
            this.mBTSupport = false;
            return false;
        }
        if (this.mBtAdapter.isEnabled()) {
            this.mBTSupport = true;
        } else {
            this.mBTSupport = false;
        }
        sInstance = this;
        startBluetoothLeService();
        this.mFilter = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
        this.mFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        this.mFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        this.mGATTFilter = new IntentFilter();
        this.mGATTFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        this.mGATTFilter.addAction(BluetoothLeService.ACTION_DATA_NOTIFY);
        this.mGATTFilter.addAction(BluetoothLeService.ACTION_DATA_WRITE);
        this.mGATTFilter.addAction(BluetoothLeService.ACTION_DATA_READ);
        this.mGATTFilter.addAction(ACTION_CONNECT_BINDED_DEVICE);
        this.mGATTFilter.addAction(ACTION_DISCONNECT_BINDED_DEVICE);
        this.mGATTFilter.addAction(ACTION_BATTERY_READ);
        this.mGATTFilter.addAction(ACTION_STEP_DATA_READ);
        this.mGATTFilter.addAction(ACTION_STEP_TOTAL_DATA);
        this.mGATTFilter.addAction(ACTION_STATISTICS_STEP_READ);
        this.mGATTFilter.addAction(ACTION_UPDATE_ALARM_INFO);
        this.mGATTFilter.addAction(ACTION_NOTICE_NEW_SMS);
        this.mGATTFilter.addAction(ACTION_NOTICE_READ_SMS);
        this.mGATTFilter.addAction(ACTION_NOTICE_NEW_CALL);
        this.mGATTFilter.addAction(ACTION_NOTICE_CALL_END);
        this.mGATTFilter.addAction(ACTION_NOTICE_MISS_CALL);
        this.mGATTFilter.addAction(ACTION_NOTICE_NEW_WECHAT_MSG);
        this.mGATTFilter.addAction(ACTION_NOTICE_READ_WECHAT_MSG);
        this.mGATTFilter.addAction(ACTION_GET_SLEEP_INFO);
        this.mGATTFilter.addAction(ACTION_DISABLE_SLEEP_INFO);
        this.mGATTFilter.addAction(ACTION_GET_FIRMWARE_VERSION);
        this.mGATTFilter.addAction(ACTION_READY_FIRMWARE_UPDATE);
        this.mGATTFilter.addAction(ACTION_TOTALSTEP_DISABLE_DATA_READ);
        this.mGATTFilter.addAction(ACTION_GET_DEVICE_NAME);
        this.mGATTFilter.addAction(ACTION_LOW_BATTERY_REMIND);
        this.mGATTFilter.addAction(ACTION_CLOSE_BLE_PHONE_STEPS);
        this.mGATTFilter.addAction(ACTION_FIND_PHONE_REMIND);
        this.mGATTFilter.addAction(ACTION_DISABLE_GSENOR_INFO);
        this.mGATTFilter.addAction(ACTION_DISABLE_HEART_INFO);
        this.mGATTFilter.addAction(ACTION_REMIND_DEVICE_CLOSE_FIND_PHONE);
        this.mGATTFilter.addAction(ACTION_UPDATE_SEDENTARY_INFO);
        this.mGATTFilter.addAction(ACTION_GSENOR_DATA_READ);
        this.mGATTFilter.addAction(ACTION_HEART_DATA_READ);
        this.mGATTFilter.addAction(ACTION_CLOSE_FIND_PHONE_DIALOG);
        this.mGATTFilter.addAction(ACTION_OPEN_FIND_DEVICE);
        this.mGATTFilter.addAction(ACTION_CLOSE_FIND_DEVICE);
        this.mGATTFilter.addAction(ACTION_BINDING_DEVICE);
        this.mGATTFilter.addAction(ACTION_UNBINDING_DEVICE);
        this.mGATTFilter.addAction(ACTION_M2_HEART_RATE);
        return true;
    }

    public void onCreate() {
        Log.d(TAG, "BleManagerService onCreate");
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "BleManagerService onStartCommand");
        if (!initialize()) {
            stopSelf();
            Log.d(TAG, "BleManagerService onStartCommand-stop self!!");
        }
        registerReceiver();
        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent intent) {
        Log.d(TAG, "BleManagerService onbind service");
        return this.binder;
    }

    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "BleManagerService onUnbind service");
        return super.onUnbind(intent);
    }

    public void onDestroy() {
        Log.d(TAG, "BleDeviceManagerService onDestroy");
        super.onDestroy();
        this.mHandler.removeCallbacksAndMessages(null);
        if (this.mBluetoothLeService != null) {
            this.mCloseThread = false;
            this.mBluetoothLeService.close();
            unregisterReceiver();
            unbindService(this.mServiceConnection);
            this.mBluetoothLeService = null;
        }
    }

    public boolean ConnectToDevice(BleDeviceInfo mbledev) {
        boolean result = false;
        if (!(mbledev == null || mbledev.getBluetoothDevice().getAddress() == null)) {
            result = this.mBluetoothLeService.connect(mbledev.getBluetoothDevice().getAddress());
            if (result && mbledev.getBluetoothDevice().getName() != null) {
                this.mDeviceType = mbledev.getBluetoothDevice().getName();
            }
        }
        return result;
    }

    public boolean ConnectToDeviceByAddress(String address) {
        if (address == null || this.mBluetoothLeService == null) {
            return false;
        }
        return this.mBluetoothLeService.connect(address);
    }

    public void disConnectDevice(BleDeviceInfo device) {
        if (mBluetoothManager.getConnectionState(device.getBluetoothDevice(), 7) == 2) {
            this.mBluetoothLeService.disconnect(device.getBluetoothDevice().getAddress());
        }
    }

    private void disConnectDeviceByAddress(String address) {
        if (address != null && this.mBluetoothLeService != null) {
            this.mBluetoothLeService.disconnect(address);
        }
    }

    public int getGattConnectState(BluetoothDevice device) {
        if (mBluetoothManager == null) {
            return 2;
        }
        int connectionState = mBluetoothManager.getConnectionState(device, 7);
        if (connectionState == 2) {
            return 3;
        }
        if (connectionState == 0) {
            return 2;
        }
        return connectionState;
    }

    public List<BluetoothDevice> getGattCurrentDevice() {
        return BluetoothLeService.getInstance().ConnectedDevicesList();
    }

    private void getSupportedrServices() {
        try {
            if (this.mBluetoothLeService != null) {
                this.mServiceList = this.mBluetoothLeService.getSupportedGattServices();
            }
            for (int i = 0; i < this.mServiceList.size() && this.mServiceList.size() > 1; i++) {
                Log.d(TAG, "mServiceList" + i + ":" + ((BluetoothGattService) this.mServiceList.get(i)).getUuid().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BluetoothGattCharacteristic getGattCharacteristic(UUID srcUuid) {
        BluetoothGattCharacteristic characteristic = null;
        if (!(IsSupportedServicesListEmpty() || this.mServiceList.size() == 0)) {
            for (BluetoothGattService gattService : this.mServiceList) {
                for (BluetoothGattCharacteristic gattCharac : gattService.getCharacteristics()) {
                    UUID uuid = gattCharac.getUuid();
                    Log.d("gatt", "gattCharacteristic.getUuid():" + uuid.toString());
                    if (uuid.equals(srcUuid)) {
                        characteristic = gattCharac;
                        break;
                    }
                }
            }
        }
        return characteristic;
    }

    private BluetoothGattCharacteristic getGattCharacteristic(UUID serviceUUid, UUID characteristicUUid) {
        BluetoothGattCharacteristic characteristic = null;
        if (!(IsSupportedServicesListEmpty() || this.mServiceList.size() == 0)) {
            for (BluetoothGattService gattService : this.mServiceList) {
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

    private void openBatteryNotify() {
        Log.d(TAG, "openBatteryNotify");
        if (this.mBluetoothLeService != null) {
            BluetoothGattCharacteristic find_charac = getGattCharacteristic(GattInfo.BATTERY_SERVICE, GattInfo.BATTERY_LEVEL);
            if (find_charac != null) {
                Log.d(TAG, "openBatteryNotify setCharacteristicNotification result = " + this.mBluetoothLeService.setCharacteristicNotification(find_charac, true));
                BluetoothGattDescriptor descriptor = find_charac.getDescriptor(GattInfo.STEPS_NOTICEFATION_ENABLE);
                if (descriptor != null) {
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                }
                this.mBluetoothLeService.writeDescriptor(descriptor);
            }
        }
    }

    private void getRemoteDeviceBatteryInfo() {
        Log.d(TAG, "getRemoteDeviceBatteryInfo");
        if (this.mBluetoothLeService != null) {
            BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.BATTERY_LEVEL);
            if (charac != null) {
                this.mBluetoothLeService.readCharacteristic(charac);
            }
            this.mBluetoothLeService.waitIdle(100);
        }
    }

    private void controlRemoteDeviceVibration() {
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.FIRMWARE_READY_MEASUMENT);
        byte[] byteArr = new byte[]{(byte) 2};
        if (charac != null) {
            charac.setValue(byteArr);
            Log.d(TAG, "sendControlRemoteDeviceVibration result= " + this.mBluetoothLeService.writeCharacteristic(charac));
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    private void getRemoteDeviceTimeAndAlarm() {
        Log.d(TAG, "getRemoteDeviceTimeAndAlarm");
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.TIME_AND_ALARM_INFO);
        if (charac != null) {
            this.mBluetoothLeService.readCharacteristic(charac);
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    private void setRemoteDeviceTime() {
        Log.d(TAG, "setRemoteDeviceTime");
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.TIME_SYNC);
        long curr_system_time = System.currentTimeMillis();
        String snycTime = String.valueOf(((Util.getUtcTimeZone(curr_system_time) - 28800000) / 1000) + Util.getUtcTime(curr_system_time));
        Long deviceTime = Long.valueOf(Long.parseLong(snycTime));
        byte[] LocalTimemsByte = long2Byte(deviceTime.longValue());
        String res = new String(LocalTimemsByte);
        Log.i("hph", "snycTime byte=" + snycTime);
        Log.i("hph", "LocalTimemsByte=" + LocalTimemsByte);
        Log.i("hph", "res=" + res);
        Log.i("hph", "deviceTime=" + deviceTime);
        if (charac != null) {
            charac.setValue(LocalTimemsByte);
            Log.d(TAG, "setRemoteDeviceTime result" + this.mBluetoothLeService.writeCharacteristic(charac));
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    private void setAlarmTime(String alarmMsg) {
        Log.i(TAG, "setAlarmTime:" + alarmMsg);
        byte[] alarmMsgByte = alarmMsg.getBytes();
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.ALARM_SERVICE, GattInfo.ALARM_MEASUREMENT);
        if (charac != null) {
            charac.setValue(alarmMsgByte);
            Log.d(TAG, "setAlarmTime result= " + this.mBluetoothLeService.writeCharacteristic(charac));
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    private void noticeNewSMS() {
        Log.d(TAG, "noticeNewSMS");
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA);
        byte[] byteArr = new byte[]{(byte) 4};
        if (charac != null) {
            charac.setValue(byteArr);
            Log.d(TAG, "noticeNewSMS result= " + this.mBluetoothLeService.writeCharacteristic(charac));
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    private void noticeReadSMS() {
        Log.d(TAG, "noticeReadSMS");
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA);
        byte[] byteArr = new byte[]{(byte) 8};
        if (charac != null) {
            charac.setValue(byteArr);
            Log.d(TAG, "noticeReadSMS result= " + this.mBluetoothLeService.writeCharacteristic(charac));
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    private void noticeNewCall() {
        Log.d(TAG, "noticeNewCall");
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA);
        byte[] byteArr = new byte[]{(byte) 5};
        if (charac != null) {
            charac.setValue(byteArr);
            Log.d(TAG, "noticeNewCall result= " + this.mBluetoothLeService.writeCharacteristic(charac));
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    private void noticeNewCallM2() {
        Log.d(TAG, "noticeNewCallM2");
        BluetoothGattCharacteristic chara = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA);
        byte[] byteArr = new byte[]{(byte) 40};
        if (chara != null) {
            chara.setValue(byteArr);
            Log.d(TAG, "noticeNewCallM2 result= " + this.mBluetoothLeService.writeCharacteristic(chara));
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    private void noticeNewCallPro(String phoneNum) {
        Log.d(TAG, "noticeNewCallPro:" + phoneNum);
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA);
        byte[] byteArr = new byte[]{(byte) 7, (byte) -1};
        if (phoneNum == null) {
            phoneNum = "";
        }
        byte[] phoneArr = phoneNum.getBytes();
        byte[] resArray = new byte[(byteArr.length + phoneArr.length)];
        System.arraycopy(byteArr, 0, resArray, 0, 1);
        System.arraycopy(phoneArr, 0, resArray, 1, phoneArr.length);
        System.arraycopy(byteArr, 1, resArray, resArray.length - 1, 1);
        if (charac != null) {
            charac.setValue(resArray);
            Log.d(TAG, "noticeNewCallPro result= " + this.mBluetoothLeService.writeCharacteristic(charac));
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    private void noticeNewCallNameOrNum(String nameornum) {
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA);
        byte[] byteArr = new byte[]{(byte) 7, (byte) -1};
        if (nameornum == null) {
            nameornum = "";
        }
        byte[] phoneArr = nameornum.getBytes();
        byte[] resArray = new byte[(byteArr.length + phoneArr.length)];
        Log.i("zhangweinan", "phoneArr = " + phoneArr);
        Log.i("zhangweinan", "resArray = " + resArray);
        System.arraycopy(byteArr, 0, resArray, 0, 1);
        System.arraycopy(phoneArr, 0, resArray, 1, phoneArr.length);
        System.arraycopy(byteArr, 1, resArray, resArray.length - 1, 1);
        if (charac != null) {
            charac.setValue(resArray);
            Log.d(TAG, "noticeNewCallPro result= " + this.mBluetoothLeService.writeCharacteristic(charac));
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    private void noticeCallEnd() {
        Log.d(TAG, "noticeCallEnd");
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA);
        byte[] byteArr = new byte[]{(byte) 17};
        if (charac != null) {
            charac.setValue(byteArr);
            Log.d(TAG, "noticeCallEnd result= " + this.mBluetoothLeService.writeCharacteristic(charac));
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    private void noticeMissCall() {
        Log.d(TAG, "noticeMissCall");
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA);
        byte[] byteArr = new byte[]{(byte) 5};
        if (charac != null) {
            charac.setValue(byteArr);
            Log.d(TAG, "noticeMissCall result= " + this.mBluetoothLeService.writeCharacteristic(charac));
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    private void noticeDoneFindPhone() {
        Log.d(TAG, "noticeDoneFindPhone");
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA);
        byte[] byteArr = new byte[]{(byte) 19};
        if (charac != null) {
            charac.setValue(byteArr);
            Log.d(TAG, "noticeDoneFindPhone result= " + this.mBluetoothLeService.writeCharacteristic(charac));
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    private void setOpenFindDevice() {
        Log.d(TAG, "setOpenFindDevice");
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA);
        byte[] byteArr = new byte[]{(byte) 38};
        if (charac != null) {
            charac.setValue(byteArr);
            Log.d(TAG, "setOpenFindDevice result= " + this.mBluetoothLeService.writeCharacteristic(charac));
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    private void setCloseFindDevice() {
        Log.d(TAG, "setCloseFindDevice");
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA);
        byte[] byteArr = new byte[]{(byte) 39};
        if (charac != null) {
            charac.setValue(byteArr);
            Log.d(TAG, "setCloseFindDevice result= " + this.mBluetoothLeService.writeCharacteristic(charac));
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    private void setBindingDevice() {
        Log.i(TAG, "setBindingDevice");
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA);
        byte[] byteArr = new byte[]{(byte) 41};
        if (charac != null) {
            charac.setValue(byteArr);
            Log.i(TAG, "setBindingDevice result=" + this.mBluetoothLeService.writeCharacteristic(charac));
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    private void setUnbindingDevice() {
        Log.i(TAG, "setUnbindingDevice");
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA);
        byte[] byteArr = new byte[]{(byte) 48};
        if (charac != null) {
            charac.setValue(byteArr);
            Log.i(TAG, "setUnbindingDevice result=" + this.mBluetoothLeService.writeCharacteristic(charac));
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    private void setM2HeartRate() {
        Log.i(TAG, "setM2HeartRate");
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA);
        byte[] byteArr = new byte[]{(byte) 49};
        if (charac != null) {
            charac.setValue(byteArr);
            Log.i(TAG, "setM2HeartRate result=" + this.mBluetoothLeService.writeCharacteristic(charac));
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    private void noticeNewWeChatMsg() {
        Log.d(TAG, "noticeNewWeChatMsg");
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA);
        byte[] byteArr = new byte[]{(byte) 6};
        if (charac != null) {
            charac.setValue(byteArr);
            Log.d(TAG, "noticeNewWeChatMsg result= " + this.mBluetoothLeService.writeCharacteristic(charac));
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    private void noticeReadWeChatMsg() {
        Log.d(TAG, "noticeReadWeChatMsg");
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.OTA);
        byte[] byteArr = new byte[]{cv.f3784n};
        if (charac != null) {
            charac.setValue(byteArr);
            Log.d(TAG, "noticeReadWeChatMsg result= " + this.mBluetoothLeService.writeCharacteristic(charac));
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    private boolean noticeFindPhone() {
        Log.d(TAG, "noticeFindPhone");
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.FIND_PHONE_SERVICE, GattInfo.FIND_PHONE_MEASUREMENT);
        boolean result = false;
        if (charac != null) {
            result = this.mBluetoothLeService.setCharacteristicNotification(charac, true);
            Log.d(TAG, "noticeFindPhone result= " + result);
        }
        this.mBluetoothLeService.waitIdle(100);
        return result;
    }

    private void desciptorFindPhone(boolean enable) {
        Log.d(TAG, "desciptorFindPhone:" + enable);
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.FIND_PHONE_SERVICE, GattInfo.FIND_PHONE_MEASUREMENT);
        if (charac != null) {
            BluetoothGattDescriptor descriptor = charac.getDescriptor(GattInfo.FIND_PHONE_NOTIFY_ENABLE);
            boolean result = false;
            if (descriptor != null) {
                if (enable) {
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                } else {
                    descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
                }
                result = this.mBluetoothLeService.writeDescriptor(descriptor);
            }
            Log.d(TAG, "desciptorFindPhone result= " + result);
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    private void getSleepInfoFromRemote(boolean enable) {
        Log.d(TAG, "getSleepInfoFromRemote:" + enable);
        if (this.mBluetoothLeService != null) {
            UUID service_Uuid = GattInfo.STEPS_SERVICE;
            BluetoothGattCharacteristic charac = getGattCharacteristic(service_Uuid, GattInfo.SLEEP_INFO_CHAR);
            if (charac != null) {
                boolean result = this.mBluetoothLeService.setCharacteristicNotification(service_Uuid, charac, enable);
                Log.d(TAG, "getSleepInfoFromRemote setCharacteristicNotification result = " + result);
                if (!result) {
                    sendBroadcast(new Intent(ACTION_DISABLE_SLEEP_INFO));
                }
            }
            this.mBluetoothLeService.waitIdle(100);
        }
    }

    private void getDeviceName() {
        Log.d(TAG, "getDeviceName");
        if (this.mBluetoothLeService != null) {
            BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.DEVICE_NAME_SERVICE, GattInfo.DEVICE_NAME_CHAR);
            if (charac != null) {
                this.mBluetoothLeService.readCharacteristic(charac);
            }
            this.mBluetoothLeService.waitIdle(100);
        }
    }

    private void getDeviceVersion() {
        Log.d(TAG, "getDeviceVersion");
        if (this.mBluetoothLeService != null) {
            BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.DEVICEINFO_SERVICE, GattInfo.DEVICEINFO_MEASUMENT);
            if (charac != null) {
                this.mBluetoothLeService.readCharacteristic(charac);
            }
            this.mBluetoothLeService.waitIdle(100);
        }
    }

    private void getDeviceHardware() {
        Log.d(TAG, "getDeviceHardware");
        if (this.mBluetoothLeService != null) {
            BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.DEVICEINFO_SERVICE, GattInfo.DEVICEINFO_HARDWARE);
            if (charac != null) {
                this.mBluetoothLeService.readCharacteristic(charac);
            }
            this.mBluetoothLeService.waitIdle(100);
        }
    }

    private void setDeviceUpdateReady() {
        Log.d(TAG, "setDeviceUpdateReady");
        if (this.mBluetoothLeService != null) {
            BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.FIRMWARE_READY_SERVICE, GattInfo.FIRMWARE_READY_MEASUMENT);
            if (charac != null) {
                charac.setValue(new byte[]{(byte) 1});
                this.mBluetoothLeService.writeCharacteristic(charac);
            }
            this.mBluetoothLeService.waitIdle(100);
        }
    }

    private void openHeartService() {
        Log.d(TAG, "openHeartService");
        if (this.mBluetoothLeService != null) {
            BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.FIRMWARE_READY_SERVICE, GattInfo.FIRMWARE_READY_MEASUMENT);
            if (charac != null) {
                charac.setValue(new byte[]{(byte) 34});
                this.mBluetoothLeService.writeCharacteristic(charac);
            }
            this.mBluetoothLeService.waitIdle(100);
        }
    }

    private void closeHeartService() {
        Log.d(TAG, "openHeartService");
        if (this.mBluetoothLeService != null) {
            BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.FIRMWARE_READY_SERVICE, GattInfo.FIRMWARE_READY_MEASUMENT);
            if (charac != null) {
                charac.setValue(new byte[]{(byte) 35});
                this.mBluetoothLeService.writeCharacteristic(charac);
            }
            this.mBluetoothLeService.waitIdle(100);
        }
    }

    private void setRemoteDeviceStepsReady() {
    }

    private void SetVibrationRemind() {
        Log.d(TAG, "SetVibrationRemind");
        if (this.mBluetoothLeService != null && getRemoteStepsDescriptor()) {
            BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.VIBRATION_REMIND);
            if (this.remindInfo != null) {
                charac.setValue(this.remindInfo);
            }
            if (charac != null) {
                this.mBluetoothLeService.writeCharacteristic(charac);
            }
            this.mBluetoothLeService.waitIdle(100);
        }
    }

    private void getRemoteDeviceTotalSteps(boolean enable) {
        Log.d(TAG, "getRemoteDeviceTotalSteps :" + enable);
        if (this.mBluetoothLeService != null) {
            BluetoothGattCharacteristic find_charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.TOTAL_STEPS_MEASUREMENT);
            Log.d(TAG, "getRemoteDeviceTotalSteps setCharacteristicNotification result = " + this.mBluetoothLeService.setCharacteristicNotification(find_charac, enable));
            BluetoothGattDescriptor descriptor = find_charac.getDescriptor(GattInfo.STEPS_NOTICEFATION_ENABLE);
            if (descriptor != null) {
                if (enable) {
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                } else {
                    descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
                }
                this.mBluetoothLeService.writeDescriptor(descriptor);
            }
        }
    }

    private void getRemoteDeviceSteps(boolean enable) {
        Log.d(TAG, "getRemoteDeviceSteps :" + enable);
        if (this.mBluetoothLeService != null) {
            UUID step_service_Uuid = GattInfo.STEPS_SERVICE;
            BluetoothGattCharacteristic charac = getGattCharacteristic(step_service_Uuid, GattInfo.SEGMENT_STEPS_MEASUREMENT);
            if (charac != null) {
                Log.d(TAG, "getRemoteDeviceSteps setCharacteristicNotification result = " + this.mBluetoothLeService.setCharacteristicNotification(step_service_Uuid, charac, enable));
            }
            this.mBluetoothLeService.waitIdle(100);
        }
    }

    private void getRemoteDeviceGsensor(boolean enable) {
        Log.d(TAG, "getRemoteDeviceGsensor :" + enable);
        if (this.mBluetoothLeService != null) {
            BluetoothGattCharacteristic find_charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.GSENOR_MEASUREMENT);
            Log.d(TAG, "getRemoteDeviceGsensor setCharacteristicNotification result = " + this.mBluetoothLeService.setCharacteristicNotification(find_charac, enable));
            BluetoothGattDescriptor descriptor = find_charac.getDescriptor(GattInfo.STEPS_NOTICEFATION_ENABLE);
            if (descriptor != null) {
                if (enable) {
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                } else {
                    descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
                }
                this.mBluetoothLeService.writeDescriptor(descriptor);
            }
        }
    }

    private void getRemoteDeviceHeart(boolean enable) {
        Log.d(TAG, "getRemoteDeviceGsensor :" + enable);
        if (this.mBluetoothLeService != null) {
            BluetoothGattCharacteristic find_charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.HEART_MEASUREMENT);
            Log.d(TAG, "getRemoteDeviceGsensor setCharacteristicNotification result = " + this.mBluetoothLeService.setCharacteristicNotification(find_charac, enable));
            BluetoothGattDescriptor descriptor = find_charac.getDescriptor(GattInfo.STEPS_NOTICEFATION_ENABLE);
            if (descriptor != null) {
                if (enable) {
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                } else {
                    descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
                }
                this.mBluetoothLeService.writeDescriptor(descriptor);
            }
        }
    }

    private boolean getRemoteStepsDescriptor() {
        Log.d(TAG, "getRemoteStepsDescriptor");
        if (this.mBluetoothLeService != null) {
            BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.SEGMENT_STEPS_MEASUREMENT);
            if (charac != null) {
                BluetoothGattDescriptor descriptor = charac.getDescriptor(GattInfo.STEPS_NOTICEFATION_ENABLE);
                if (descriptor != null && BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE == descriptor.getValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean getSleepInfoDescriptor() {
        Log.d(TAG, "getSleepInfoDescriptor");
        if (this.mBluetoothLeService != null) {
            BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.SLEEP_INFO_CHAR);
            if (charac != null) {
                BluetoothGattDescriptor descriptor = charac.getDescriptor(GattInfo.STEPS_NOTICEFATION_ENABLE);
                if (descriptor != null && BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE == descriptor.getValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void openTakePictureNotifiy() {
        Log.d(TAG, "openTakePictureNotifiy");
        if (this.mBluetoothLeService != null) {
            BluetoothGattCharacteristic find_charac = getGattCharacteristic(GattInfo.STEPS_SERVICE, GattInfo.TAKE_PICTURE);
            if (find_charac != null) {
                Log.d(TAG, "openTakePictureNotifiy setCharacteristicNotification result = " + this.mBluetoothLeService.setCharacteristicNotification(find_charac, true));
                BluetoothGattDescriptor descriptor = find_charac.getDescriptor(GattInfo.STEPS_NOTICEFATION_ENABLE);
                if (descriptor != null) {
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                }
                this.mBluetoothLeService.writeDescriptor(descriptor);
            }
        }
    }

    private void startBluetoothLeService() {
        if (bindService(new Intent(this, BluetoothLeService.class), this.mServiceConnection, 1)) {
            Log.d(TAG, "BluetoothLeService - success");
        } else {
            Log.d(TAG, "BluetoothLeService - failed");
        }
    }

    private void setSedentaryTime(String sedentaryMsg) {
        Log.i(TAG, "setSedentaryTime:" + sedentaryMsg);
        byte[] sedentaryMsgByte = sedentaryMsg.getBytes();
        BluetoothGattCharacteristic charac = getGattCharacteristic(GattInfo.SEDENTARY_SERVICE, GattInfo.SEDENTARY_MEASUREMENT);
        Log.i(TAG, "sedentaryMsgByte=" + sedentaryMsgByte);
        if (charac != null) {
            charac.setValue(sedentaryMsgByte);
            Log.d(TAG, "setSedentaryTime result= " + this.mBluetoothLeService.writeCharacteristic(charac));
        }
        this.mBluetoothLeService.waitIdle(100);
    }

    public static BleManagerService getInstance() {
        if (sInstance == null) {
            Log.d(TAG, "getInstance(), BleManagerService is null.");
            startBleManagerService();
        }
        return sInstance;
    }

    public void setTimeToRemote(long time) {
        if (this.mHandler != null) {
            this.mHandler.sendEmptyMessageDelayed(4, time);
        }
    }

    private static void startBleManagerService() {
        sContext.startService(new Intent(sContext, BleManagerService.class));
    }

    private void registerReceiver() {
        registerReceiver(this.mReceiver, this.mFilter);
        registerReceiver(this.mGattUpdateReceiver, this.mGATTFilter);
    }

    private void unregisterReceiver() {
        unregisterReceiver(this.mReceiver);
        unregisterReceiver(this.mGattUpdateReceiver);
    }

    public boolean GetBleConnectState() {
        return this.connect_status;
    }

    public boolean IsSupportedServicesListEmpty() {
        return this.mServiceList == null;
    }

    private boolean isBinded() {
        return !this.msharepreference.getString("BLE_BIND_STATE", "0").equals("0");
    }

    private boolean isConnected() {
        return this.msharepreference.getBoolean("BLE_CONNECT_STATE", false);
    }

    private synchronized void setIsConnecting(boolean val) {
        this.mIsConnecting = val;
    }

    private synchronized boolean IsConnecting() {
        return this.mIsConnecting;
    }

    private byte[] long2Byte(long src) {
        return new byte[]{(byte) ((int) (src >> 24)), (byte) ((int) (src >> 16)), (byte) ((int) (src >> 8)), (byte) ((int) (src >> 0))};
    }

    private void showFindPhoneDialog() {
        Builder builder = new Builder(this);
        builder.setTitle((int) R.string.alert_title);
        builder.setMessage(getResources().getString(R.string.phone_found));
        builder.setPositiveButton((int) R.string.ok, new C11586());
        this.dialog = builder.create();
        this.dialog.getWindow().setType(AMapException.CODE_AMAP_ENGINE_TABLEID_NOT_EXIST);
        this.dialog.show();
    }

    public static void stopMusic() {
        if (media != null) {
            if (media.isPlaying()) {
                media.stop();
            }
            media.release();
            media = null;
        }
    }
}

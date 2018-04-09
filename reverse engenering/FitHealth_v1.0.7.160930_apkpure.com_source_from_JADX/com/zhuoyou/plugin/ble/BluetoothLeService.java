package com.zhuoyou.plugin.ble;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import com.zhuoyou.plugin.bluetooth.data.Util;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.database.DataBaseUtil;
import com.zhuoyou.plugin.download.Util_update.TimeManager;
import com.zhuoyou.plugin.firmware.FirmwareService;
import com.zhuoyou.plugin.mainFrame.MineFragment;
import com.zhuoyou.plugin.resideMenu.EquipManagerListActivity;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@SuppressLint({"SimpleDateFormat", "NewApi"})
public class BluetoothLeService extends Service {
    public static final String ACTION_BACK_CONNECT = "com.zhuoyou.running.ble.ACTION_BACK_CONNECT";
    public static final String ACTION_DATABASE_CHANGE = "com.zhuoyou.running.ble.ACTION_DATABASE_CHANGE";
    public static final String ACTION_DATA_NOTIFY = "com.zhuoyou.running.ble.ACTION_DATA_NOTIFY";
    public static final String ACTION_DATA_READ = "com.zhuoyou.running.ble.ACTION_DATA_READ";
    public static final String ACTION_DATA_WRITE = "com.zhuoyou.running.ble.ACTION_DATA_WRITE";
    public static final String ACTION_GATT_CONNECTED = "com.zhuoyou.running.ble.ACTION_GATT_CONNECTED";
    public static final String ACTION_GATT_DISCONNECTED = "com.zhuoyou.running.ble.ACTION_GATT_DISCONNECTED";
    public static final String ACTION_GATT_SERVICES_DISCOVERED = "com.zhuoyou.running.ble.ACTION_GATT_SERVICES_DISCOVERED";
    public static final int BACK_CONNECT_DELAY = 1;
    public static final int BACK_CONNECT_OPERATION = 2;
    public static final int BACK_CONNECT_STATE_NOTIFY = 9;
    public static final String EXTRA_ADDRESS = "com.zhuoyou.running.ble.EXTRA_ADDRESS";
    public static final String EXTRA_DATA = "com.zhuoyou.running.ble.EXTRA_DATA";
    public static final String EXTRA_STATUS = "com.zhuoyou.running.ble.EXTRA_STATUS";
    public static final String EXTRA_UUID = "com.zhuoyou.running.ble.EXTRA_UUID";
    public static final String LEADOFF_STATUS = "com.zhuoyou.running.ble.LEADOFF_STATUS";
    static final String TAG = "BluetoothLeService";
    public static final UUID UUID_BATTERY_MEASUREMENT = GattInfo.BATTERY_LEVEL;
    public static final UUID UUID_GSENSOR_DATA_MEASUREMENT = GattInfo.GSENSOR_DATA_MEASUREMENT;
    public static final UUID UUID_GSENSOR_HDATA_MEASUREMENT = GattInfo.GSENOR_MEASUREMENT;
    public static final UUID UUID_HEART_DATA_MEASUREMENT = GattInfo.HEART_MEASUREMENT;
    public static final UUID UUID_HEART_RATE_MEASUREMENT = GattInfo.HEART_RATE_MEASUREMENT;
    public static final UUID UUID_SEGMENT_STEPS_MEASUREMENT = GattInfo.SEGMENT_STEPS_MEASUREMENT;
    public static final UUID UUID_SLEEP_INFO = GattInfo.SLEEP_INFO_CHAR;
    public static final UUID UUID_STEPS_SERVICE = GattInfo.STEPS_SERVICE;
    public static final UUID UUID_SYNC_TIME_INFO = GattInfo.TIME_SYNC;
    public static final UUID UUID_TAKE_PICTURE = GattInfo.TAKE_PICTURE;
    public static final UUID UUID_TIME_INFO = GattInfo.TIME_AND_ALARM_INFO;
    public static final UUID UUID_TOTAL_STEPS_MEASUREMENT = GattInfo.TOTAL_STEPS_MEASUREMENT;
    private static BluetoothLeService mThis = null;
    public static int relinkCount = 0;
    private static final Context sContext = RunningApp.getInstance().getApplicationContext();
    private static final SharedPreferences sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(sContext);
    private final IBinder binder = new LocalBinder();
    private HashMap<String, String> bleBindMap;
    private int countTimeTotal = 0;
    private int curr_index;
    private CustomHandler customHandler = null;
    private String deviceAddress = "";
    private String deviceName = "";
    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt = null;
    private BluetoothManager mBluetoothManager = null;
    private BluetoothAdapter mBtAdapter = null;
    private volatile boolean mBusy = false;
    private IntentFilter mFilter;
    private BluetoothGattCallback mGattCallbacks = new C11591();
    private int mSleepInfoPart = 1;
    private Handler relinkHandler = new C11602();
    private byte[] sleepInfoByte = new byte[40];
    private Runnable startDialog = new C11624();
    private Runnable takePictureRunnable = new C11613();
    private int totle_number;

    class C11591 extends BluetoothGattCallback {
        C11591() {
        }

        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (BluetoothLeService.this.mBluetoothGatt == null) {
                Log.e(BluetoothLeService.TAG, "mBluetoothGatt not created!");
                return;
            }
            BluetoothDevice device = gatt.getDevice();
            BluetoothLeService.this.deviceName = device.getName();
            Log.i("aaa", "deviceName =" + BluetoothLeService.this.deviceName);
            BluetoothLeService.this.deviceAddress = device.getAddress();
            Log.i(BluetoothLeService.TAG, "onConnectionStateChange (" + BluetoothLeService.this.deviceAddress + ") " + newState + " status: " + status);
            switch (newState) {
                case 0:
                    BluetoothLeService.this.updateConnectInfo(false, BluetoothLeService.this.deviceName, BluetoothLeService.this.deviceAddress);
                    Util.updateLLatestConnectDeviceAddress(BluetoothLeService.this.getApplicationContext(), "");
                    BluetoothLeService.this.broadcastUpdate(BluetoothLeService.ACTION_GATT_DISCONNECTED, BluetoothLeService.this.deviceAddress, status);
                    BluetoothLeService.this.relinkHandler.sendEmptyMessage(1);
                    if (MineFragment.mHandler != null) {
                        Message msg = MineFragment.mHandler.obtainMessage();
                        msg.what = 5;
                        MineFragment.mHandler.sendMessage(msg);
                        return;
                    }
                    return;
                case 2:
                    BluetoothLeService.this.relinkHandler.removeMessages(1);
                    BluetoothLeService.this.relinkHandler.removeMessages(2);
                    Util.handUnlink(BluetoothLeService.this, false);
                    BleManagerService.low_battery_remind = 1;
                    BluetoothLeService.this.updateConnectInfo(true, BluetoothLeService.this.deviceName, BluetoothLeService.this.deviceAddress);
                    Util.setDeviceName(BluetoothLeService.this.deviceName);
                    Util.setLatestDeviceType(BluetoothLeService.this.getApplicationContext(), true);
                    Util.updateLatestConnectDeviceAddress(BluetoothLeService.this.getApplicationContext(), BluetoothLeService.this.deviceAddress);
                    Util.updateLLatestConnectDeviceAddress(BluetoothLeService.this.getApplicationContext(), BluetoothLeService.this.deviceAddress);
                    RunningApp.setCurrentConnectDeviceType(BluetoothLeService.this.deviceAddress);
                    BluetoothLeService.this.broadcastUpdate(BluetoothLeService.ACTION_GATT_CONNECTED, BluetoothLeService.this.deviceAddress, status);
                    BluetoothLeService.this.mBluetoothGatt.discoverServices();
                    Intent intent = new Intent();
                    intent.setAction("com.zhuoyou.running.connect.success");
                    BluetoothLeService.this.sendBroadcast(intent);
                    BluetoothLeService.this.sendBroadcast(new Intent(BleManagerService.ACTION_CLOSE_BLE_PHONE_STEPS));
                    return;
                default:
                    try {
                        Log.e(BluetoothLeService.TAG, "New state not processed: " + newState);
                        return;
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
            }
            e.printStackTrace();
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == 0) {
                BluetoothLeService.this.broadcastUpdate(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED, gatt.getDevice().getAddress(), status);
                return;
            }
            Log.w(BluetoothLeService.TAG, "onServicesDiscovered received: " + status);
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            Log.i(BluetoothLeService.TAG, "onCharacteristicChanged");
            BluetoothLeService.this.broadcastUpdate(BluetoothLeService.ACTION_DATA_NOTIFY, characteristic, 0);
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == 0) {
                BluetoothLeService.this.broadcastUpdate(BluetoothLeService.ACTION_DATA_READ, characteristic, status);
            }
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            BluetoothLeService.this.broadcastUpdate(BluetoothLeService.ACTION_DATA_WRITE, characteristic, status);
        }

        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            BluetoothLeService.this.mBusy = false;
            Log.i(BluetoothLeService.TAG, "onDescriptorRead");
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            BluetoothLeService.this.mBusy = false;
            Log.i(BluetoothLeService.TAG, "onDescriptorWrite");
        }
    }

    class C11602 extends Handler {
        C11602() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    BluetoothLeService.this.relinkHandler.sendEmptyMessageDelayed(2, 10000);
                    BluetoothLeService.this.relinkHandler.sendEmptyMessageDelayed(2, 30000);
                    BluetoothLeService.this.relinkHandler.sendEmptyMessageDelayed(2, TimeManager.UNIT_MINUTE);
                    BluetoothLeService.this.relinkHandler.sendEmptyMessageDelayed(2, 90000);
                    return;
                case 2:
                    BluetoothLeService.this.relinkDevice();
                    BluetoothLeService.this.relinkHandler.sendEmptyMessageDelayed(2, 600000);
                    return;
                default:
                    return;
            }
        }
    }

    class C11613 implements Runnable {
        C11613() {
        }

        public void run() {
            BluetoothLeService.this.relinkHandler.removeCallbacks(BluetoothLeService.this.takePictureRunnable);
            BluetoothLeService.this.sendBroadcast(new Intent("com.zhoyou.plugin.autocamera.capture"));
            Log.i("hph", "take picture sendbroadcast");
        }
    }

    class C11624 implements Runnable {
        C11624() {
        }

        public void run() {
            BluetoothLeService.this.relinkHandler.removeCallbacks(BluetoothLeService.this.startDialog);
            BluetoothLeService.this.sendBroadcast(new Intent(BleManagerService.ACTION_FIND_PHONE_REMIND));
        }
    }

    private class CustomHandler extends Handler {
        public static final int MSG_SLEEP_INFO = 0;
        public static final int MSG_STEP_INFO = 1;

        public CustomHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Log.i("yangyang", "MSG_SLEEP_INFO");
                    byte[] sleepInfoByte = msg.getData().getByteArray("sleepInfoByte");
                    if (sleepInfoByte != null) {
                        int i;
                        boolean isEmptyMsg = false;
                        if (sleepInfoByte[0] == (byte) -1 && sleepInfoByte[39] == (byte) -1) {
                            isEmptyMsg = true;
                            i = 0;
                            while (i < 38) {
                                if (sleepInfoByte[i + 1] != (byte) 0) {
                                    isEmptyMsg = false;
                                    if (isEmptyMsg) {
                                        Log.i(BluetoothLeService.TAG, "isEmptyMsg" + isEmptyMsg);
                                        BluetoothLeService.this.sendBroadcast(new Intent(BleManagerService.ACTION_DISABLE_SLEEP_INFO));
                                    }
                                } else {
                                    i++;
                                }
                            }
                            if (isEmptyMsg) {
                                Log.i(BluetoothLeService.TAG, "isEmptyMsg" + isEmptyMsg);
                                BluetoothLeService.this.sendBroadcast(new Intent(BleManagerService.ACTION_DISABLE_SLEEP_INFO));
                            }
                        }
                        if (!isEmptyMsg) {
                            BluetoothLeService.this.curr_index = sleepInfoByte[0] & 255;
                            BluetoothLeService.this.totle_number = sleepInfoByte[1] & 255;
                            int type = sleepInfoByte[2] & 255;
                            long endTime = ((long) (((((sleepInfoByte[7] & 255) << 24) | ((sleepInfoByte[8] & 255) << 16)) | ((sleepInfoByte[9] & 255) << 8)) | (sleepInfoByte[10] & 255))) + 1262275200;
                            long startTime = Tools.transformUTCTime2LongFormat(((long) (((((sleepInfoByte[3] & 255) << 24) | ((sleepInfoByte[4] & 255) << 16)) | ((sleepInfoByte[5] & 255) << 8)) | (sleepInfoByte[6] & 255))) + 1262275200);
                            endTime = Tools.transformUTCTime2LongFormat(endTime);
                            StringBuilder sb = new StringBuilder("");
                            for (i = 0; i < 29; i++) {
                                sb.append(sleepInfoByte[i + 11] & 255);
                                sb.append("|");
                            }
                            Log.i(BluetoothLeService.TAG, "type = " + type);
                            Log.i(BluetoothLeService.TAG, "startTime = " + startTime);
                            Log.i(BluetoothLeService.TAG, "endTime = " + endTime);
                            Log.i(BluetoothLeService.TAG, "sb = " + sb.toString());
                            DataBaseUtil.insertSleep(BluetoothLeService.mThis.getBaseContext(), type, startTime, endTime, sb.toString());
                            break;
                        }
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class LocalBinder extends Binder {
        public BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }

    @SuppressLint({"SimpleDateFormat"})
    public void onCreate() {
        super.onCreate();
        this.deviceAddress = Util.getLatestConnectDeviceAddress(sContext);
        this.bleBindMap = Tools.getBleBindDevice(sContext);
        this.deviceName = Tools.keyString(this.bleBindMap, this.deviceAddress);
        Message msg = new Message();
        msg.what = 1;
        this.relinkHandler.sendMessage(msg);
        HandlerThread handlerThread = new HandlerThread("handler_thread");
        handlerThread.start();
        this.customHandler = new CustomHandler(handlerThread.getLooper());
    }

    private void broadcastUpdate(String action, String address, int status) {
        Intent intent = new Intent(action);
        intent.putExtra(EXTRA_ADDRESS, address);
        intent.putExtra(EXTRA_STATUS, status);
        sendBroadcast(intent);
        this.mBusy = false;
    }

    private void broadcastUpdate(String action, BluetoothGattCharacteristic characteristic, int status) {
        int i;
        Intent intent;
        Message msg;
        int batteryLevel;
        if (ACTION_DATA_NOTIFY.equals(action)) {
            byte[] data;
            StringBuilder content;
            String time;
            int step;
            if (UUID_GSENSOR_DATA_MEASUREMENT.equals(characteristic.getUuid())) {
                Log.i(TAG, "UUID_GSENSOR_DATA_MEASUREMENT===");
                data = characteristic.getValue();
                byte[] gsensor_x = new byte[20];
                byte[] gsensor_y = new byte[20];
                byte[] gsensor_z = new byte[20];
                int j = 0;
                content = new StringBuilder();
                time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
                if (data != null) {
                    if (!false) {
                        for (i = 0; i < 5; i++) {
                            Log.i(TAG, "i =" + i);
                            Log.i(TAG, "data[i] =" + data[i]);
                            if (data[i] != (byte) -1) {
                                break;
                            }
                            j++;
                        }
                        i = 9;
                        while (i < 20 && data[i] == (byte) -1) {
                            j++;
                            i++;
                        }
                        Log.i(TAG, "j=" + j);
                        if (j != 16) {
                            int count = ((data[1] & 255) << 8) | (data[0] & 255);
                            int countTime = this.countTimeTotal;
                            for (i = 2; i <= 17; i += 3) {
                                gsensor_x[i] = (byte) (data[i] & 255);
                                gsensor_y[i] = (byte) (data[i + 1] & 255);
                                gsensor_z[i] = (byte) (data[i + 2] & 255);
                                content.append(countTime);
                                content.append(" ,");
                                content.append(gsensor_x[i]);
                                content.append(" ,");
                                content.append(gsensor_y[i]);
                                content.append(" ,");
                                content.append(gsensor_z[i] + "\n");
                                countTime += 40;
                                this.countTimeTotal = countTime;
                            }
                            Log.i(TAG, "content===" + content);
                        } else {
                            step = ((((data[8] & 255) << 24) | ((data[7] & 255) << 16)) | ((data[6] & 255) << 8)) | (data[5] & 255);
                            content.append("step=");
                            content.append(step);
                            Log.i(TAG, "content===" + content);
                        }
                    }
                    File file = new File(Environment.getExternalStorageDirectory() + File.separator + "aaa.txt");
                    try {
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                        FileWriter fileWriter = new FileWriter(file, true);
                        fileWriter.write(content.toString() + "\n");
                        fileWriter.flush();
                        fileWriter.close();
                        Log.i(TAG, "content tostring===" + content.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (UUID_HEART_DATA_MEASUREMENT.equals(characteristic.getUuid())) {
                byte[] txValue = characteristic.getValue();
                if (!false && txValue[0] == (byte) 6) {
                    float[] fArr = new float[3];
                    fArr = new float[]{0.0f, 0.0f, 0.0f};
                    Object ppg_data = new char[20];
                    for (i = 0; i < txValue.length - 1; i++) {
                        ppg_data[i] = (char) txValue[i + 1];
                    }
                    int[] heart_dataArr = new int[13];
                    Log.i(TAG, "heart_data=[" + (txValue[1] & 255) + ", " + (txValue[2] & 255) + " ," + (txValue[3] & 255) + ", " + (txValue[4] & 255) + ", " + (txValue[5] & 255) + " ," + (txValue[6] & 255) + " ," + (txValue[7] & 255) + " ," + (txValue[8] & 255) + " ," + (txValue[9] & 255) + ", " + (txValue[10] & 255) + " ," + (txValue[11] & 255) + " ," + (txValue[12] & 255) + ", " + (txValue[13] & 255) + "] ");
                    Log.i(TAG, "txValue.length=" + txValue.length);
                    if (txValue.length == 20) {
                        for (i = 0; i < fArr.length; i++) {
                            fArr[i] = (float) ((txValue[(i * 2) + 15] << 8) + (txValue[(i * 2) + 14] & 255));
                            Log.i(TAG, "mems_data=" + fArr[i]);
                        }
                    }
                    Log.i(TAG, "ppg_data=" + ppg_data);
                    intent = new Intent("com.zhuoyou.plugin.running.heart.data");
                    intent.putExtra("gsensor_data", fArr);
                    intent.putExtra("heart_data", ppg_data);
                    sendBroadcast(intent);
                }
            } else if (UUID_GSENSOR_HDATA_MEASUREMENT.equals(characteristic.getUuid())) {
                data = characteristic.getValue();
                Log.i(TAG, "gsenor_data=" + data);
                if (!false) {
                    Object gsensor_data = new float[]{getFloat(data, 0), getFloat(data, 4), getFloat(data, 8)};
                    Log.i(TAG, "gsensor_data x=" + getFloat(data, 0));
                    Log.i(TAG, "gsensor_data y=" + y);
                    Log.i(TAG, "gsensor_data z=" + z);
                    Log.i(TAG, "gsensor_data=" + gsensor_data);
                    intent = new Intent("com.zhuoyou.plugin.running.gsensor.data");
                    intent.putExtra("gsensor_data", gsensor_data);
                    sendBroadcast(intent);
                }
            }
            int curr_index;
            int totle_number;
            String data_from;
            if (UUID_TOTAL_STEPS_MEASUREMENT.equals(characteristic.getUuid())) {
                data = characteristic.getValue();
                content = new StringBuilder();
                if (!(data == null || false)) {
                    curr_index = data[0] & 255;
                    totle_number = data[1] & 255;
                    step = ((((data[2] & 255) << 24) | ((data[3] & 255) << 16)) | ((data[4] & 255) << 8)) | (data[5] & 255);
                    Date date = Tools.transformLongTime2StringFormat(((long) (((((data[6] & 255) << 24) | ((data[7] & 255) << 16)) | ((data[8] & 255) << 8)) | (data[9] & 255))) + 1262275200).substring(0, 10);
                    Log.i(TAG, "date" + date);
                    Log.i(TAG, "step" + step);
                    content.append(step);
                    content.append("|");
                    content.append(date);
                    content.append("|");
                    data_from = this.deviceName + "|" + this.deviceAddress;
                    Log.i(TAG, "GATT get data = " + content + "||| from= " + data_from);
                    intent = new Intent("com.zhuoyou.plugin.running.get.gatt");
                    intent.putExtra("content", content.toString());
                    intent.putExtra(DataBaseContants.STATISTICS, 2);
                    intent.putExtra("from", data_from);
                    sendBroadcast(intent);
                    if (curr_index == totle_number) {
                        Log.i(TAG, "sendBroadcast ACTION_STEP_DATA_READ");
                        sendBroadcast(new Intent(BleManagerService.ACTION_TOTALSTEP_DISABLE_DATA_READ));
                    }
                }
            } else if (UUID_SEGMENT_STEPS_MEASUREMENT.equals(characteristic.getUuid())) {
                data = characteristic.getValue();
                Log.i(TAG, "data=" + characteristic.getValue());
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
                            sendBroadcast(new Intent(BleManagerService.ACTION_STATISTICS_STEP_READ));
                        }
                    }
                    if (!isEmptyMsg) {
                        curr_index = data[0] & 255;
                        totle_number = data[1] & 255;
                        step = ((((data[2] & 255) << 24) | ((data[3] & 255) << 16)) | ((data[4] & 255) << 8)) | (data[5] & 255);
                        long endTime = ((long) (((((data[10] & 255) << 24) | ((data[11] & 255) << 16)) | ((data[12] & 255) << 8)) | (data[13] & 255))) + 1262275200;
                        time = Tools.transformLongTime2StringFormat(((long) (((((data[6] & 255) << 24) | ((data[7] & 255) << 16)) | ((data[8] & 255) << 8)) | (data[9] & 255))) + 1262275200);
                        String date2 = time.substring(0, 10);
                        String start = time.substring(11, 15);
                        String end = Tools.transformLongTime2StringFormat(endTime).substring(11, 15);
                        Log.i(TAG, "curr_index" + curr_index);
                        Log.i(TAG, "totle_number" + totle_number);
                        Log.i(TAG, "start" + start);
                        Log.i(TAG, "end" + end);
                        Log.i(TAG, "step" + step);
                        data_from = this.deviceName + "|" + this.deviceAddress;
                        intent = new Intent("com.zhuoyou.plugin.running.get.gatt");
                        intent.putExtra("step", step);
                        intent.putExtra("date", date2);
                        intent.putExtra("start", start);
                        intent.putExtra("end", end);
                        intent.putExtra(DataBaseContants.STATISTICS, 0);
                        intent.putExtra("from", data_from);
                        sendBroadcast(intent);
                        if (curr_index == totle_number) {
                            Log.i(TAG, "ACTION_STATISTICS_STEP_READ");
                            sendBroadcast(new Intent(BleManagerService.ACTION_STATISTICS_STEP_READ));
                        }
                    }
                }
            } else if (UUID_SLEEP_INFO.equals(characteristic.getUuid())) {
                Log.i(TAG, "recevice sleep info mSleepInfoPart " + this.mSleepInfoPart);
                byte[] sleepData = characteristic.getValue();
                Log.i(TAG, "sleepData=" + characteristic.getValue());
                if (sleepData != null && sleepData.length == 20) {
                    if (this.mSleepInfoPart == 1) {
                        this.curr_index = sleepData[0] & 255;
                        this.totle_number = sleepData[1] & 255;
                        this.mSleepInfoPart = 2;
                        Log.i(TAG, "recevice sleep info curr_index " + this.curr_index);
                        Log.i(TAG, "recevice sleep info totle_number " + this.totle_number);
                        for (i = 0; i < 20; i++) {
                            this.sleepInfoByte[i] = sleepData[i];
                        }
                    } else if (this.mSleepInfoPart == 2) {
                        this.mSleepInfoPart = 1;
                        for (i = 0; i < 20; i++) {
                            this.sleepInfoByte[i + 20] = sleepData[i];
                        }
                        msg = this.customHandler.obtainMessage();
                        msg.what = 0;
                        Bundle bundle = new Bundle();
                        bundle.putByteArray("sleepInfoByte", this.sleepInfoByte);
                        msg.setData(bundle);
                        msg.sendToTarget();
                        if (this.curr_index == this.totle_number) {
                            sendBroadcast(new Intent(BleManagerService.ACTION_DISABLE_SLEEP_INFO));
                        }
                    }
                }
            } else if (GattInfo.FIND_PHONE_MEASUREMENT.equals(characteristic.getUuid())) {
                Log.i(TAG, "recevice find phone info ");
                if (characteristic != null) {
                    Object values = characteristic.getValue();
                    Log.i(TAG, "recevice find phone info values:" + values);
                    type = values[0] & 255;
                    Log.i(TAG, "recevice find phone info value:" + type);
                    if (type == 1) {
                        this.relinkHandler.removeCallbacks(this.startDialog);
                        this.relinkHandler.postDelayed(this.startDialog, 300);
                    } else if (type == 2) {
                        BleManagerService.stopMusic();
                        sendBroadcast(new Intent(BleManagerService.ACTION_CLOSE_FIND_PHONE_DIALOG));
                    } else if (type == 5) {
                        Log.i(TAG, "type == ");
                        endCall();
                    } else if (type == 6) {
                        Log.i("hph", "type=6 ACTION_BINDING_DEVICE_SUCCESS");
                    } else if (type == 7) {
                        sendBroadcast(new Intent(EquipManagerListActivity.ACTION_UNBINDING_DEVICE_SUCCESS));
                        Log.i("hph", "type=7 ACTION_UNBINDING_DEVICE_SUCCESS");
                    } else if (type > 68 && type < 92) {
                        intent = new Intent("com.zhuoyou.plugin.running.m2.heart.data");
                        intent.putExtra("m2_heart_data", type);
                        sendBroadcast(intent);
                    }
                }
            } else if (UUID_BATTERY_MEASUREMENT.equals(characteristic.getUuid())) {
                batteryLevel = characteristic.getValue()[0];
                Log.i(TAG, "battery info::value[0] = " + batteryLevel);
                Tools.saveBatteryLevel(batteryLevel);
                if (batteryLevel <= 10) {
                    Log.i(TAG, "batteryLevel = " + batteryLevel);
                    sendBroadcast(new Intent(BleManagerService.ACTION_LOW_BATTERY_REMIND));
                }
                Log.i(TAG, "updateLatestDeviceBatteryValue==");
                if (MineFragment.mHandler != null) {
                    msg = MineFragment.mHandler.obtainMessage();
                    msg.what = 2;
                    msg.arg1 = batteryLevel;
                    MineFragment.mHandler.sendMessage(msg);
                    Log.i("TAG", "MineFragment.mHandler.obtainMessage();=" + batteryLevel);
                }
                if (EquipManagerListActivity.mHandler != null) {
                    msg = EquipManagerListActivity.mHandler.obtainMessage();
                    msg.what = 4;
                    msg.arg1 = batteryLevel;
                    EquipManagerListActivity.mHandler.sendMessage(msg);
                    Log.i("TAG", "EquipManagerListActivity.UPDATE_BATTERY=" + batteryLevel);
                }
            } else if (UUID_TAKE_PICTURE.equals(characteristic.getUuid())) {
                Object value = characteristic.getValue();
                type = value[0] & 255;
                Log.i("hph", "value=" + value);
                Log.i("hph", "type=" + type);
                if (type == 18) {
                    Log.i("hph", "ACTION_TAKE_PICTURE");
                    this.relinkHandler.removeCallbacks(this.takePictureRunnable);
                    this.relinkHandler.postDelayed(this.takePictureRunnable, 1000);
                }
            }
        }
        if (ACTION_DATA_READ.equals(action)) {
            if (UUID_BATTERY_MEASUREMENT.equals(characteristic.getUuid())) {
                batteryLevel = characteristic.getValue()[0];
                Log.i(TAG, "battery info::value[0] = " + batteryLevel);
                Tools.saveBatteryLevel(batteryLevel);
                Log.i(TAG, "updateLatestDeviceBatteryValue==");
                if (MineFragment.mHandler != null) {
                    msg = new Message();
                    msg.what = 2;
                    msg.arg1 = batteryLevel;
                    MineFragment.mHandler.sendMessage(msg);
                }
                if (EquipManagerListActivity.mHandler != null) {
                    msg = EquipManagerListActivity.mHandler.obtainMessage();
                    msg.what = 4;
                    msg.arg1 = batteryLevel;
                    EquipManagerListActivity.mHandler.sendMessage(msg);
                }
            }
            if (UUID_TIME_INFO.equals(characteristic.getUuid())) {
                for (byte b : characteristic.getValue()) {
                    Log.i(TAG, "characteristic = " + b);
                }
            }
            if (GattInfo.DEVICE_NAME_CHAR.equals(characteristic.getUuid())) {
                String dName = new String(characteristic.getValue());
                this.deviceName = dName;
                Tools.updateBleBindInfo(this, dName, this.deviceAddress);
            } else if (GattInfo.DEVICEINFO_MEASUMENT.equals(characteristic.getUuid())) {
                r0 = new String(characteristic.getValue());
                intent = new Intent(FirmwareService.ACTION_RECEIVER_DEVICE_INFO);
                intent.putExtra("device_version", r0);
                sendBroadcast(intent);
                Tools.setDeviceVersion(r0);
                Log.i(TAG, "deviceVersion:" + r0);
            } else if (GattInfo.DEVICEINFO_HARDWARE.equals(characteristic.getUuid())) {
                r0 = new String(characteristic.getValue());
                Tools.setHardwareVersion(r0);
                sendHeighWeightData();
                Log.i(TAG, "handware=" + r0);
                Log.i(TAG, "getIsSendBindingDevice=" + Tools.getIsSendBindingDevice());
            }
        }
        this.mBusy = false;
        Log.i(TAG, "broadcastUpdate reset mBusy to false");
    }

    private void sendHeighWeightData() {
        Intent intent = new Intent(BleManagerService.ACTION_UPDATE_SEDENTARY_INFO);
        intent.putExtra("sedentary_info", Tools.getHeightWeightData());
        sendBroadcast(intent);
    }

    private void toFloat(byte[] data) {
    }

    private boolean checkGatt() {
        if (this.mBtAdapter == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            this.mBusy = false;
            return false;
        } else if (this.mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothGatt not initialized");
            this.mBusy = false;
            return false;
        } else {
            Log.i(TAG, "checkGatt mBusy =" + this.mBusy);
            if (!this.mBusy) {
                return true;
            }
            Log.w(TAG, "LeService busy");
            this.mBusy = false;
            return false;
        }
    }

    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "bleservices onunbind!!");
        close();
        return super.onUnbind(intent);
    }

    public boolean initialize() {
        mThis = this;
        if (this.mBluetoothManager == null) {
            this.mBluetoothManager = (BluetoothManager) getSystemService("bluetooth");
            if (this.mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }
        this.mBtAdapter = this.mBluetoothManager.getAdapter();
        if (this.mBtAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }
        BluetoothAdapter bluetoothAdapter = this.mBtAdapter;
        this.mFilter = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
        this.mFilter.addAction(ACTION_GATT_CONNECTED);
        this.mFilter.addAction(ACTION_GATT_DISCONNECTED);
        return true;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Received start id " + startId + ": " + intent);
        return 1;
    }

    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy() called");
        if (this.mBluetoothGatt != null) {
            this.mBluetoothGatt.close();
            this.mBluetoothGatt = null;
        }
    }

    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (checkGatt()) {
            this.mBusy = true;
            this.mBluetoothGatt.readCharacteristic(characteristic);
        }
    }

    public boolean writeCharacteristic(BluetoothGattCharacteristic characteristic, byte b) {
        if (!checkGatt()) {
            return false;
        }
        characteristic.setValue(new byte[]{b});
        this.mBusy = true;
        return this.mBluetoothGatt.writeCharacteristic(characteristic);
    }

    public boolean writeCharacteristic(BluetoothGattCharacteristic characteristic, boolean b) {
        if (!checkGatt()) {
            return false;
        }
        int i;
        byte[] val = new byte[1];
        if (b) {
            i = 1;
        } else {
            i = 0;
        }
        val[0] = (byte) i;
        characteristic.setValue(val);
        this.mBusy = true;
        return this.mBluetoothGatt.writeCharacteristic(characteristic);
    }

    public boolean writeCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (!checkGatt()) {
            return false;
        }
        this.mBusy = true;
        return this.mBluetoothGatt.writeCharacteristic(characteristic);
    }

    public boolean writeDescriptor(BluetoothGattDescriptor descriptor) {
        if (!checkGatt()) {
            return false;
        }
        this.mBusy = true;
        return this.mBluetoothGatt.writeDescriptor(descriptor);
    }

    public int getNumServices() {
        if (this.mBluetoothGatt == null) {
            return 0;
        }
        return this.mBluetoothGatt.getServices().size();
    }

    public List<BluetoothGattService> getSupportedGattServices() {
        if (this.mBluetoothGatt == null) {
            return null;
        }
        return this.mBluetoothGatt.getServices();
    }

    public boolean setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enable) {
        if (!checkGatt()) {
            return false;
        }
        if (this.mBluetoothGatt.setCharacteristicNotification(characteristic, enable)) {
            return this.mBluetoothGatt.setCharacteristicNotification(characteristic, enable);
        }
        Log.w(TAG, "setCharacteristicNotification failed");
        return false;
    }

    public boolean setCharacteristicNotification(UUID service_UUid, BluetoothGattCharacteristic characteristic, boolean enable) {
        if (!checkGatt()) {
            return false;
        }
        if (this.mBluetoothGatt.setCharacteristicNotification(characteristic, enable)) {
            BluetoothGattDescriptor descriptor = null;
            if (service_UUid.equals(GattInfo.HEART_RATE_SERVICE)) {
                descriptor = characteristic.getDescriptor(GattInfo.HEART_RATE_NOTICEFATION_ENABLE);
            } else if (service_UUid.equals(GattInfo.STEPS_SERVICE)) {
                descriptor = characteristic.getDescriptor(GattInfo.STEPS_NOTICEFATION_ENABLE);
            } else if (service_UUid.equals(GattInfo.OAD_SERVICE_UUID)) {
                descriptor = characteristic.getDescriptor(GattInfo.OAD_ENABLE_UUID);
            } else if (service_UUid.equals(GattInfo.OAD_ENABLE_UUID)) {
                descriptor = characteristic.getDescriptor(GattInfo.OAD_ENABLE_UUID);
            }
            if (descriptor == null) {
                return false;
            }
            if (enable) {
                Log.i(TAG, "enable notification");
                descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            } else {
                Log.i(TAG, "disable notification");
                descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
            }
            this.mBusy = true;
            return this.mBluetoothGatt.writeDescriptor(descriptor);
        }
        Log.w(TAG, "setCharacteristicNotification failed");
        return false;
    }

    public boolean isNotificationEnabled(BluetoothGattCharacteristic characteristic) {
        if (!checkGatt()) {
            return false;
        }
        BluetoothGattDescriptor clientConfig = characteristic.getDescriptor(GattInfo.HEART_RATE_NOTICEFATION_ENABLE);
        if (clientConfig == null || clientConfig.getValue() != BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE) {
            return false;
        }
        return true;
    }

    public boolean connect(String address) {
        if (this.mBtAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }
        BluetoothDevice device = this.mBtAdapter.getRemoteDevice(address);
        Log.i(TAG, "connect-connectionState = " + this.mBluetoothManager.getConnectionState(device, 7));
        Log.i(TAG, "mBluetoothDeviceAddress == " + this.mBluetoothDeviceAddress);
        Log.i(TAG, "address == " + address);
        Log.i(TAG, "mBluetoothGatt == " + this.mBluetoothGatt);
        if (this.mBluetoothDeviceAddress != null && address.equals(this.mBluetoothDeviceAddress) && this.mBluetoothGatt != null) {
            Log.i(TAG, "Re-use GATT connection");
            if (this.mBluetoothGatt.connect()) {
                return true;
            }
            Log.w(TAG, "GATT re-connect failed.");
            return false;
        } else if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        } else {
            Log.i(TAG, "Create a new GATT connection.");
            this.mBluetoothGatt = device.connectGatt(this, false, this.mGattCallbacks);
            this.mBluetoothDeviceAddress = address;
            return true;
        }
    }

    public void disconnect(String address) {
        if (this.mBtAdapter == null) {
            Log.w(TAG, "disconnect: BluetoothAdapter not initialized");
        } else if (address != null && !TextUtils.isEmpty(address)) {
            int connectionState = this.mBluetoothManager.getConnectionState(this.mBtAdapter.getRemoteDevice(address), 7);
            if (this.mBluetoothGatt != null) {
                Log.i(TAG, "disconnect");
                if (connectionState != 0) {
                    this.mBluetoothGatt.disconnect();
                } else {
                    Log.w(TAG, "Attempt to disconnect in state: " + connectionState);
                }
            }
        }
    }

    public void close() {
        if (this.mBluetoothGatt != null) {
            Log.i(TAG, "close");
            this.mBluetoothGatt.close();
            this.mBluetoothGatt = null;
        }
    }

    public int numConnectedDevices() {
        if (this.mBluetoothGatt != null) {
            return this.mBluetoothManager.getConnectedDevices(7).size();
        }
        return 0;
    }

    public List<BluetoothDevice> ConnectedDevicesList() {
        if (this.mBluetoothGatt != null) {
            return this.mBluetoothManager.getConnectedDevices(7);
        }
        return null;
    }

    public static BluetoothGatt getBtGatt() {
        return mThis.mBluetoothGatt;
    }

    public static BluetoothManager getBtManager() {
        return mThis.mBluetoothManager;
    }

    public static BluetoothLeService getInstance() {
        return mThis;
    }

    public boolean waitIdle(int i) {
        i /= 10;
        while (true) {
            i--;
            if (i <= 0 || !this.mBusy) {
                return i <= 0;
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (i <= 0) {
        }
    }

    private void updateConnectInfo(boolean is_connect, String name, String address) {
        this.mSleepInfoPart = 1;
        if (name == null && is_connect) {
            sendBroadcast(new Intent(BleManagerService.ACTION_GET_DEVICE_NAME));
        }
        if (is_connect) {
            Tools.updateBleBindInfo(this, name, address);
        }
    }

    private void relinkDevice() {
        Log.i(TAG, "IS going relinkDevice name:" + this.deviceName + ",address:" + this.deviceAddress);
        boolean isHand = Util.isHandUnlink(this);
        relinkCount++;
        int state = this.mBtAdapter.getState();
        BluetoothAdapter bluetoothAdapter = this.mBtAdapter;
        if (state == 12 && !isHand && this.deviceName != null) {
            Intent relinkDeviceIntent = new Intent(BleManagerService.ACTION_CONNECT_BINDED_DEVICE);
            relinkDeviceIntent.putExtra("deviceName", this.deviceName);
            relinkDeviceIntent.putExtra("deviceAddress", this.deviceAddress);
            sendBroadcast(relinkDeviceIntent);
            if ((Util.isBleDevice(this.deviceName) || this.deviceName.equals("")) && this.deviceAddress != "" && this.deviceAddress != null) {
                Message msg = new Message();
                if (msg != null && MineFragment.mHandler != null) {
                    msg.what = 9;
                    MineFragment.mHandler.sendMessage(msg);
                }
            }
        }
    }

    private void endCall() {
        try {
            IBinder iBinder = (IBinder) getClassLoader().loadClass("android.os.ServiceManager").getDeclaredMethod("getService", new Class[]{String.class}).invoke(null, new Object[]{"phone"});
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "拦截电话异常");
        }
    }

    private char[] getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        Log.i(TAG, "cb=" + cb);
        Log.i(TAG, "cb.array=" + cb.array());
        return cb.array();
    }

    public static float getFloat(byte[] b, int i) {
        return Float.intBitsToFloat((((0 | ((b[i] & 255) << 0)) | ((b[i + 1] & 255) << 8)) | ((b[i + 2] & 255) << 16)) | ((b[i + 3] & 255) << 24));
    }

    private int bytesToInt(byte[] b) {
        return Integer.parseInt(new String(b));
    }
}

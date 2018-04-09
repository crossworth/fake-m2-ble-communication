package com.droi.btlib.service;

import android.app.Notification;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.text.TextUtils;
import android.util.Log;
import com.droi.btlib.device.DeviceDetail;
import com.droi.btlib.service.BtManagerService.CONNECT_STATE;
import com.droi.btlib.service.BtManagerService.DeleteMusicListCallback;
import com.droi.btlib.service.BtManagerService.GetAlarmRemindCallback;
import com.droi.btlib.service.BtManagerService.GetHeartCallback;
import com.droi.btlib.service.BtManagerService.GetMusicListCallback;
import com.droi.btlib.service.BtManagerService.GetSleepInfoCallback;
import com.droi.btlib.service.BtManagerService.GetSubStepsCallback;
import com.droi.btlib.service.BtManagerService.GetTotalStepCallback;

public class BtDevice {
    public static final int BATTERY_CHARGE_COMPLETE = 102;
    public static final int BATTERY_CHARGING = 101;
    private static final String TAG = "chenxin";
    public static final int TYPE_BLE = 2;
    public static final int TYPE_BLE_AND_CLASSIC = 3;
    public static final int TYPE_CLASSIC = 1;
    public static final int TYPE_UNKNOW = 0;
    private CONNECT_STATE connectState = CONNECT_STATE.DISCONNECTED;
    private DeviceDetail detail;
    private BluetoothGatt mBluetoothGatt;

    public interface ConnectCallback {
        void battery(int i);

        void connecting(BtDevice btDevice);

        void disconnect(BtDevice btDevice);

        void fail(int i);

        void success(BtDevice btDevice);
    }

    public BtDevice(BluetoothGatt bluetoothGatt) {
        this.mBluetoothGatt = bluetoothGatt;
        this.connectState = CONNECT_STATE.CONNECTED;
        Util.saveType(2);
    }

    public BtDevice(BluetoothDevice device, int type) {
        Util.saveName(device.getName());
        Util.saveMacAddress(device.getAddress());
        Util.saveType(type);
    }

    public CONNECT_STATE getConnectState() {
        return this.connectState;
    }

    protected void setConnectState(CONNECT_STATE connectState) {
        Log.i(TAG, "setConnectState:" + connectState);
        this.connectState = connectState;
    }

    public int getBattery() {
        return Util.getBattery();
    }

    public void getBatteryCMD() {
        BtManagerService.getDeviceBatteryLevel();
    }

    public String getVersion() {
        return Util.getVersion();
    }

    public int getVersionCode() {
        if (TextUtils.isEmpty(getVersion())) {
            return 0;
        }
        String[] versionStr = getVersion().split("_");
        return Integer.parseInt(versionStr[versionStr.length - 1]);
    }

    public String getName() {
        return Util.getName();
    }

    public String getHardWare() {
        return Util.getHardWare();
    }

    public String getMacAddress() {
        return Util.getMacAddress();
    }

    public int getType() {
        return Util.getType();
    }

    public void connect(ConnectCallback callback) {
        if (getConnectState() != CONNECT_STATE.CONNECTED) {
            if (getConnectState() == CONNECT_STATE.DISCONNECTED) {
                setConnectState(CONNECT_STATE.CONNECTING);
            }
            if (getType() == 2) {
                BtManagerService.connectDevice(2, getMacAddress(), callback);
            } else if (getType() == 1) {
                BtManagerService.connectDevice(1, getMacAddress(), callback);
            }
        }
    }

    public void getTotalStep(GetTotalStepCallback callback) {
        BtManagerService.getTotalStep(callback);
    }

    public void getSleepInfo(GetSleepInfoCallback callback) {
        BtManagerService.getSleepInfo(callback);
    }

    public void getSubSteps(GetSubStepsCallback callback) {
        BtManagerService.getSubSteps(callback);
    }

    public BluetoothGatt testGetGatt() {
        return this.mBluetoothGatt;
    }

    public void setAlarmRemind(boolean electricity, boolean wholePoint, boolean mileage, boolean saveElectricityMode, boolean cadenceSong) {
        int i;
        int i2 = 1;
        StringBuilder stringBuilder = new StringBuilder();
        if (electricity) {
            i = 1;
        } else {
            i = 0;
        }
        StringBuilder append = stringBuilder.append(i).append("|").append(wholePoint ? 1 : 0).append("|").append(mileage ? 1 : 0).append("|").append(saveElectricityMode ? 1 : 0).append("|");
        if (!cadenceSong) {
            i2 = 0;
        }
        String remnd = append.append(i2).append("|").toString();
        Log.i("hph", "remnd=" + remnd);
        BtManagerService.setAlarmRemin(remnd);
    }

    public void getAlarmRemind(GetAlarmRemindCallback callback) {
        BtManagerService.getAlarmRemin(callback);
    }

    public void getMusicList(GetMusicListCallback callback, boolean ifForce) {
        BtManagerService.getMusicList(callback, ifForce);
    }

    public void setAlarm(String alarm) {
        BtManagerService.setAlarm(alarm);
    }

    public void setSedentary(String sedentaryInfo) {
        BtManagerService.setSedentaryInfo(sedentaryInfo);
    }

    protected DeviceDetail getDetail() {
        return BtManagerService.getDetail(getName(), getType());
    }

    public boolean supportFirmwareVersion() {
        return getDetail().getFirmwareCmd() != 0;
    }

    public boolean supportCallRemind() {
        return getDetail().getCallRemindCmd() != 0;
    }

    public boolean supportSmsRemind() {
        return getDetail().getSmsRemindCmd() != 0;
    }

    public boolean supportPushRemind() {
        return getDetail().getPushRemindCmd() != 0;
    }

    public boolean supportAntilost() {
        return getDetail().getAntiLostCmd() != 0;
    }

    public boolean supportAlarm() {
        return getDetail().getAlarmCmd() != 0;
    }

    public boolean supportSleep() {
        return getDetail().getSleepCmd() != 0;
    }

    public boolean supportHeart() {
        return getDetail().getHeartCmd() != 0;
    }

    public boolean supportFindBracelet() {
        return getDetail().getFindBraceletCmd() != 0;
    }

    public boolean supportSportTarget() {
        return getDetail().getSportTargetCmd() != 0;
    }

    public boolean supportDisturbanceMode() {
        return getDetail().getDisturbanceModeCmd() != 0;
    }

    public boolean supportSedentaryRemind() {
        return getDetail().getSedentaryRemindCmd() != 0;
    }

    public boolean supportDisplaySetting() {
        return getDetail().getDisplaySettingCmd() != 0;
    }

    public boolean supportWristLift() {
        return getDetail().getWristLiftCmd() != 0;
    }

    public void deleteMusicList(boolean[] bools, DeleteMusicListCallback callback) {
        BtManagerService.deleteMusicList(bools, callback);
    }

    public boolean supportReboot() {
        return !TextUtils.isEmpty(getVersion()) && (getVersion().startsWith("M2_2_") || getVersion().startsWith("M2_3_") || getName().equals("U3") || getName().equals("TERRA HR2") || (getVersion().startsWith("M2_") && getVersionCode() > 106));
    }

    public void setPlayMode(int mode) {
        BtManagerService.setPlayMode(mode);
    }

    public void setMusicList(boolean[] bools) {
        BtManagerService.setMusicList(bools);
    }

    public void getHeartInfo(int time, GetHeartCallback callback) {
        BtManagerService.getHeartInfo(time, callback);
    }

    public void refreshClassicBattery() {
        BtManagerService.refreshClassicBattery();
    }

    public int getUpdateType() {
        return getDetail().getUpdateCmd();
    }

    public void sendNotifaction(String appName, String pkgName, Notification mNotification) {
        BtManagerService.sendNotifaction(appName, pkgName, mNotification);
    }

    public void startFindBracelet() {
        if (supportFindBracelet()) {
            BtManagerService.setOpenFindDevice();
        }
    }

    public void endFindBracelet() {
        if (supportFindBracelet()) {
            BtManagerService.setCloseFindDevice();
        }
    }

    public void rebootDevice() {
        BtManagerService.setDeviceReboot();
    }
}

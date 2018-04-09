package com.droi.btlib.service;

import android.bluetooth.BluetoothDevice;
import android.os.Build.VERSION;
import android.util.Log;
import com.droi.btlib.device.DeviceDetail;
import com.droi.btlib.service.BtDevice.ConnectCallback;
import com.droi.btlib.service.Util.GetConnectClassicCallback;

public class DeviceInfo {
    public static final int FAIL_BT_NOT_SUPPORT = 2;
    public static final int FAIL_CONNECT_TIMEOUT = 0;
    public static final int FAIL_IN_CONNECTING = 1;
    private String address;
    private DeviceDetail detail;
    private String name;
    private int rssi;

    DeviceInfo(BluetoothDevice device) {
        this.name = device.getName();
        this.address = device.getAddress();
    }

    public void setDetail(DeviceDetail detail) {
        this.detail = detail;
    }

    public int getType() {
        if (this.detail != null) {
            return this.detail.getBtType();
        }
        return 0;
    }

    public int getRssi() {
        return this.rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public String getName() {
        return this.name;
    }

    public String getMacAddress() {
        return this.address;
    }

    public void connectDevice(final ConnectCallback callback) {
        Util.getBTProxy(this, new GetConnectClassicCallback() {
            public void success(BluetoothDevice device) {
                Log.i("chenxin", "connectDevice:success");
                BtManagerService.connectDevice(DeviceInfo.this.detail.getBtType(), DeviceInfo.this.address, callback, true);
                if (VERSION.SDK_INT >= 19) {
                    device.createBond();
                }
            }

            public void fail() {
                Log.i("chenxin", "fail");
                BtManagerService.deleteDevice();
                BtManagerService.connectDevice(DeviceInfo.this.detail.getBtType(), DeviceInfo.this.address, callback, true);
            }
        });
    }
}

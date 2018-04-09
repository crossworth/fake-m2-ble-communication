package com.zhuoyou.plugin.bluetooth.connection;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.Timer;
import java.util.TimerTask;

public class BtProfileReceiver extends BroadcastReceiver {
    public static final String NEED_CONNECT_ACTION_STRING = "com.mtk.btconnection.needconnected";
    private static BluetoothDevice currRemoteDevice = null;
    private static int mConnectTime = 0;
    public static Timer mTimer = new Timer(true);
    private final int LESS_CONNECT_TIME = 3;
    private Context mContext = null;

    class C11961 extends TimerTask {
        C11961() {
        }

        public void run() {
            cancel();
            BtProfileReceiver.mTimer = null;
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(BtProfileReceiver.NEED_CONNECT_ACTION_STRING);
            if (!(BtProfileReceiver.currRemoteDevice == null || BluetoothManager.GetSppConnectState() == 2 || BluetoothManager.GetSppConnectState() == 3)) {
                BtProfileReceiver.this.mContext.sendBroadcast(broadcastIntent);
            }
            BtProfileReceiver.access$210();
            if (BtProfileReceiver.mConnectTime > 0) {
                BtProfileReceiver.this.runningSyncTimer();
            }
        }
    }

    static /* synthetic */ int access$210() {
        int i = mConnectTime;
        mConnectTime = i - 1;
        return i;
    }

    public BtProfileReceiver(Context context) {
        this.mContext = context;
    }

    public static BluetoothDevice getRemoteDevice() {
        return currRemoteDevice;
    }

    public static void stopAutoConnect() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    private void runningSyncTimer() {
        TimerTask task = new C11961();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mTimer = new Timer();
        mTimer.schedule(task, 3000);
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int connState;
        if (action.equals("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED")) {
            connState = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
            if (connState == 1 || connState == 2) {
                if (currRemoteDevice == null || !(BluetoothManager.GetSppConnectState() == 2 || BluetoothManager.GetSppConnectState() == 3)) {
                    currRemoteDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                }
                new Intent().setAction(NEED_CONNECT_ACTION_STRING);
                if (currRemoteDevice != null && BluetoothManager.GetSppConnectState() != 2 && BluetoothManager.GetSppConnectState() != 3) {
                    mConnectTime = 3;
                    runningSyncTimer();
                }
            }
        } else if (action.equals("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED")) {
            connState = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
            if (connState == 1 || connState == 2) {
                if (currRemoteDevice == null || !(BluetoothManager.GetSppConnectState() == 2 || BluetoothManager.GetSppConnectState() == 3)) {
                    currRemoteDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                }
                new Intent().setAction(NEED_CONNECT_ACTION_STRING);
                if (currRemoteDevice != null && BluetoothManager.GetSppConnectState() != 2 && BluetoothManager.GetSppConnectState() != 3) {
                    mConnectTime = 3;
                    runningSyncTimer();
                }
            }
        }
    }

    public BluetoothDevice getCurrRemoteDevice() {
        return currRemoteDevice;
    }

    public String getRemoteDeviceName() {
        return currRemoteDevice.getName();
    }

    public void setRemoteDevice(BluetoothDevice remoteDevice) {
        currRemoteDevice = remoteDevice;
    }

    public static int getLessTime() {
        return mConnectTime;
    }
}

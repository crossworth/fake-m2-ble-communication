package com.droi.btlib.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.droi.btlib.connection.BluetoothConnection;
import com.droi.btlib.connection.MessageDataList;
import com.droi.btlib.connection.MessageHeader;
import com.droi.btlib.connection.MessageObj;
import com.droi.btlib.connection.NoDataException;
import com.droi.btlib.connection.SmsMessageBody;
import com.mtk.btconnection.LoadJniFunction;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.xmlpull.v1.XmlPullParserException;

public class BluetoothManager {
    public static final int BLOCKED = 1;
    public static final int BLUETOOTH_CONNECT_SUCCESS = 0;
    public static final int BLUETOOTH_NOT_CONNECT = -3;
    public static final int BLUETOOTH_NOT_ENABLE = -2;
    public static final int BLUETOOTH_NOT_SUPPORT = -1;
    public static final String BT_BROADCAST_ACTION = "com.mtk.connection.BT_CONNECTION_CHANGED";
    public static final String DEVICE_NAME = "device_name";
    public static final String EXTRA_DATA = "EXTRA_DATA";
    public static final String EXTRA_TYPE = "EXTRA_TYPE";
    public static final int FAILED = -1;
    private static final String LOG_TAG = "BluetoothManager";
    public static final int MESSAGE_CONNECT_FAIL = 6;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_TOAST = 5;
    public static final int MESSAGE_WRITE = 3;
    public static final int NOTIFYMINIHEADERLENTH = 8;
    public static final int NOTIFYSYNCLENTH = 4;
    public static final int READ_CMD = 2;
    public static final int READ_DATA = 3;
    public static final int READ_IDLE = 0;
    public static final int READ_PRE = 1;
    public static final int SUCCESS = 0;
    public static final String TOAST = "toast";
    public static final int TYPE_BT_CONNECTED = 241;
    public static final int TYPE_BT_CONNECTION_FAIL = 246;
    public static final int TYPE_BT_CONNECTION_LOST = 242;
    public static final int TYPE_DATA_ARRIVE = 244;
    public static final int TYPE_DATA_SENT = 243;
    public static final int TYPE_MAPCMD_ARRIVE = 245;
    public static int cmdBufferLenth = 0;
    public static byte[] commandBuffer = null;
    public static byte[] dataBuffer = null;
    public static int dataBufferLenth = 0;
    private static boolean isHandshake = false;
    private static boolean isOlderThanVersionTow = true;
    private static WeakReference<BluetoothManager> mBluetoothManager;
    public static final byte[] reciveBuffer = new byte[51200];
    public static int reciveBufferLenth = 0;
    private static int sppConnectState = 0;
    public int CMD_TYPE = 1;
    public int READBUFFERSTATE = 0;
    private final BroadcastReceiver mBTReceiver = new C07023();
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothConnection mBluetoothConnection = null;
    private BluetoothDevice mConnectedDeviceName = null;
    private Context mContext = null;
    private MessageHandler mHandler = null;
    private LoadJniFunction mLoadJniFunction = null;
    private MessageDataList mMessageDataList = null;
    public Timer mTimer = new Timer(true);
    private Handler serviceHandler;

    class C07012 extends TimerTask {
        C07012() {
        }

        public void run() {
            Log.i(BluetoothManager.LOG_TAG, "Timer Task Run ... isHandshake = " + BluetoothManager.isHandshake);
            cancel();
            BluetoothManager.this.mTimer = null;
            BluetoothManager.this.handShakeDone();
        }
    }

    class C07023 extends BroadcastReceiver {
        C07023() {
        }

        public void onReceive(Context context, Intent intent) {
            BluetoothManager bluetoothManager = (BluetoothManager) BluetoothManager.mBluetoothManager.get();
            if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(intent.getAction())) {
                int connectionState = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
                Log.i(BluetoothManager.LOG_TAG, "onReceive(), action=" + intent.getAction());
                if (connectionState == 12) {
                    BluetoothManager.this.setupConnection();
                } else if (connectionState == 10) {
                    BluetoothManager.this.mBluetoothConnection.disconnectRemoteDevice();
                    BluetoothManager.this.removeConnection();
                    BluetoothManager.this.serviceHandler.sendEmptyMessage(BluetoothManager.TYPE_BT_CONNECTION_LOST);
                }
            }
        }
    }

    private class MessageHandler extends Handler {
        private Context mContext = null;

        public MessageHandler(BluetoothManager bluetoothManager, Context context) {
            BluetoothManager.mBluetoothManager = new WeakReference(bluetoothManager);
            this.mContext = context;
        }

        public void handleMessage(Message msg) {
            Log.i(BluetoothManager.LOG_TAG, "handleMessage(), msg.what=" + msg.what);
            BluetoothManager bluetoothManager = (BluetoothManager) BluetoothManager.mBluetoothManager.get();
            switch (msg.what) {
                case 1:
                    Log.i(BluetoothManager.LOG_TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    switch (msg.arg1) {
                        case 0:
                            BluetoothManager.sppConnectState = 0;
                            BluetoothManager.this.serviceHandler.sendEmptyMessage(BluetoothManager.TYPE_BT_CONNECTION_LOST);
                            return;
                        case 1:
                            BluetoothManager.sppConnectState = 0;
                            return;
                        case 2:
                            BluetoothManager.sppConnectState = 2;
                            return;
                        case 3:
                            bluetoothManager.runningSyncTimer();
                            BluetoothManager.sppConnectState = 3;
                            return;
                        case 4:
                            bluetoothManager.sendBroadcast(BluetoothManager.TYPE_BT_CONNECTION_LOST, null);
                            BluetoothManager.this.serviceHandler.sendEmptyMessage(BluetoothManager.TYPE_BT_CONNECTION_LOST);
                            BluetoothManager.cmdBufferLenth = 0;
                            BluetoothManager.reciveBufferLenth = 0;
                            BluetoothManager.dataBufferLenth = 0;
                            BluetoothManager.isHandshake = false;
                            BluetoothManager.isOlderThanVersionTow = true;
                            BluetoothManager.sppConnectState = 0;
                            return;
                        default:
                            return;
                    }
                case 2:
                    byte[] readBuf = (byte[]) msg.obj;
                    int bytes = msg.arg1;
                    try {
                        System.arraycopy(readBuf, 0, BluetoothManager.reciveBuffer, BluetoothManager.reciveBufferLenth, bytes);
                        BluetoothManager.reciveBufferLenth += bytes;
                        Log.i(BluetoothManager.LOG_TAG, "reciveBufferLenth is " + BluetoothManager.reciveBufferLenth);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    bluetoothManager.runningReadFSM();
                    return;
                case 3:
                    bluetoothManager.sendBroadcast(BluetoothManager.TYPE_DATA_SENT, null);
                    Log.i(BluetoothManager.LOG_TAG, "MESSAGE_WRITE TYPE_DATA_SENT");
                    return;
                case 4:
                    bluetoothManager.setConnectedDeviceName((BluetoothDevice) msg.getData().getParcelable(BluetoothManager.DEVICE_NAME));
                    return;
                case 6:
                    bluetoothManager.sendBroadcast(BluetoothManager.TYPE_BT_CONNECTION_FAIL, null);
                    BluetoothManager.cmdBufferLenth = 0;
                    BluetoothManager.reciveBufferLenth = 0;
                    BluetoothManager.dataBufferLenth = 0;
                    BluetoothManager.isHandshake = false;
                    BluetoothManager.isOlderThanVersionTow = true;
                    BluetoothManager.sppConnectState = 0;
                    bluetoothManager.setConnectedDeviceName(null);
                    return;
                default:
                    return;
            }
        }
    }

    public BluetoothManager(Context context, Handler handler) {
        Log.i(LOG_TAG, "BluetoothManager(), BluetoothManager created!");
        this.mHandler = new MessageHandler(this, context);
        this.mContext = context;
        this.mLoadJniFunction = new LoadJniFunction();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        this.mContext.registerReceiver(this.mBTReceiver, filter);
        this.mMessageDataList = new MessageDataList(this.mContext);
        this.serviceHandler = handler;
    }

    public static int GetSppConnectState() {
        return sppConnectState;
    }

    public int setupConnection() {
        Log.d(LOG_TAG, "setupConnection()");
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (this.mBluetoothAdapter == null) {
            return -1;
        }
        if (!this.mBluetoothAdapter.isEnabled()) {
            return -2;
        }
        this.mBluetoothConnection = new BluetoothConnection(this.mHandler);
        Log.d(LOG_TAG, "setupConnection(), setupConnection successfully!");
        return 0;
    }

    public int removeConnection() {
        Log.i(LOG_TAG, "removeConnection(), Bluetooth connection is removed!");
        if (this.mBluetoothConnection != null) {
            this.mBluetoothConnection.stop();
        }
        return 0;
    }

    public String getConnectedDeviceName() {
        Log.i(LOG_TAG, "getConnectedDeviceName(), mConnectedDeviceName=" + this.mConnectedDeviceName);
        return this.mConnectedDeviceName.getName();
    }

    public void setConnectedDeviceName(BluetoothDevice connectedDeviceName) {
        Log.i(LOG_TAG, "setConnectedDeviceName(), deviceName=" + this.mConnectedDeviceName);
        this.mConnectedDeviceName = connectedDeviceName;
    }

    public void connectToRemoteDevice() {
        if (this.mBluetoothConnection != null) {
            this.mBluetoothConnection.connectRemoteDevice(this.mBluetoothAdapter.getRemoteDevice(Util.getMacAddress()));
        }
    }

    public void connectToAppointedDevice(BluetoothDevice remoteDevice) {
        if (this.mBluetoothConnection != null) {
            Log.i(LOG_TAG, "connectToAppointedDevice");
            this.mBluetoothConnection.connectRemoteDevice(remoteDevice);
        }
    }

    public void disconnectRemoteDevice() {
        if (this.mBluetoothConnection != null) {
            this.mBluetoothConnection.disconnectRemoteDevice();
        }
    }

    public void saveData() {
        Log.i(LOG_TAG, "saveData()");
        this.mMessageDataList.saveMessageDataList();
    }

    public void sendData(final byte[] data) {
        Log.i(LOG_TAG, "sendData:" + (data != null) + " " + isBTConnected());
        if (this.mBluetoothConnection == null) {
            return;
        }
        if (data == null || !isBTConnected()) {
            if (!(BtManagerService.isConnected() || TextUtils.isEmpty(Util.getMacAddress()))) {
                this.mBluetoothConnection.connectRemoteDevice(this.mBluetoothAdapter.getRemoteDevice(Util.getMacAddress()));
            }
            new Thread() {
                public void run() {
                    Looper.prepare();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (data == null || !BluetoothManager.this.isBTConnected()) {
                        BluetoothManager.this.mMessageDataList.saveMessageData(data);
                    } else {
                        BluetoothManager.this.sendDataToRemote(1, data);
                        if (BluetoothManager.this.mMessageDataList.getMessageDataList().size() > 0) {
                            BluetoothManager.this.sendDataFromFile();
                        }
                    }
                    Looper.loop();
                }
            }.start();
            return;
        }
        sendDataToRemote(1, data);
        if (this.mMessageDataList.getMessageDataList().size() > 0) {
            sendDataFromFile();
        }
    }

    public boolean isBTConnected() {
        boolean isConnected = this.mBluetoothConnection != null && isHandshake && this.mBluetoothConnection.getState() == 3;
        Log.i(LOG_TAG, "isBTConnected(), isConnected=" + isConnected);
        return isConnected;
    }

    public boolean isEnable() {
        return this.mBluetoothConnection.isEnable();
    }

    public void sendMapResult(String result) {
        if (isBTConnected()) {
            sendCommandToRemote(5, result);
        }
    }

    public void sendMapDResult(String result) {
        if (isBTConnected()) {
            sendCommandToRemote(6, result);
        }
    }

    public boolean sendMAPData(byte[] data) {
        if (isBTConnected()) {
            this.mBluetoothConnection.write(data);
            Log.i(LOG_TAG, "sendMAPData(), isDataSent=true");
            return true;
        }
        Log.i(LOG_TAG, "sendMAPData(), isDataSent=false");
        return false;
    }

    public boolean sendDataFromFile() {
        List<byte[]> messageList = this.mMessageDataList.getMessageDataList();
        Log.i(LOG_TAG, "sendDataFromFile(), message count=" + messageList.size());
        if (messageList.size() > 0) {
            int messageCount = messageList.size();
            for (int index = 0; index < messageCount && messageList.get(0) != null && isBTConnected(); index++) {
                sendDataToRemote(1, (byte[]) messageList.get(0));
                messageList.remove(0);
                Log.i(LOG_TAG, "sendDataFromFile(), message index=" + index);
            }
        }
        return false;
    }

    private void sendDataToRemote(int i, byte[] data) {
        Log.i(LOG_TAG, "sendDataToRemote cmd and data()" + getCmdBuffer(i, String.valueOf(data.length)));
        this.mBluetoothConnection.write(getCmdBuffer(i, String.valueOf(data.length)));
        this.mBluetoothConnection.write(data);
    }

    private void sendCommandToRemote(int i, String command) {
        Log.i(LOG_TAG, "Send Command to Remote: " + command);
        if (getCmdBuffer(i, command) != null) {
            this.mBluetoothConnection.write(getCmdBuffer(i, command));
        }
    }

    private byte[] getCmdBuffer(int i, String bufferString) {
        return this.mLoadJniFunction.getDataCmd(i, bufferString);
    }

    private void sendPureDatToRemote(byte[] data) {
        Log.i(LOG_TAG, "sendPureDatToRemote() " + String.valueOf(data));
        if (isBTConnected()) {
            this.mBluetoothConnection.write(data);
        }
    }

    private void sendBroadcast(int extraType, byte[] extraData) {
        Log.i(LOG_TAG, "sendBroadcast(), extraType=" + extraType);
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(BT_BROADCAST_ACTION);
        broadcastIntent.putExtra(EXTRA_TYPE, extraType);
        if (extraData != null) {
            broadcastIntent.putExtra("EXTRA_DATA", extraData);
        }
        this.mContext.sendBroadcast(broadcastIntent);
    }

    public void _sendSyncTime() {
        try {
            sendSyncTime(false);
        } catch (IOException e) {
            Log.e("gchk", "need sync time , _sendSyncTime e=" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendSyncTime(boolean useNewFormat) throws IOException {
        long curr_system_time = System.currentTimeMillis();
        int timestamp = Util.getUtcTime(curr_system_time);
        int timezone = Util.getUtcTimeZone(curr_system_time);
        Log.i("hph", "curr_system_time=" + curr_system_time);
        Log.i("hph", "timestamp=" + timestamp);
        Log.i("hph", "timezone=" + timezone);
        if (useNewFormat) {
            String snycTime_header = "bnsrv_time mtk_bnapk 0 0 " + String.valueOf((String.valueOf(timestamp).length() + 1) + String.valueOf(timezone).length()) + " ";
            String snycTime_data = String.valueOf(timestamp) + " " + String.valueOf(timezone);
            sendCommandToRemote(9, snycTime_header);
            sendPureDatToRemote(snycTime_data.getBytes());
            Log.i("hph", "snycTime_header=" + snycTime_header);
            Log.i("hph", "snycTime_data=" + snycTime_data);
        } else {
            String snycTime = String.valueOf(((timezone - 28800000) / 1000) + timestamp) + " " + String.valueOf(timezone);
            sendCommandToRemote(2, snycTime);
            Log.i("hph", "snycTime=" + snycTime);
        }
        Log.i(LOG_TAG, "sendSyncTime()");
    }

    public void sendAlarmTime() throws IOException {
        Date date = Util.getAlarmTime(this.mContext);
        if (date != null) {
            String alarmTime_header = "bnsrv_alarm mtk_bnapk 0 0 " + String.valueOf(((((String.valueOf(date.getDay()).length() + 2) + 1) + String.valueOf(date.getHours()).length()) + 1) + String.valueOf(date.getMinutes()).length()) + " ";
            String alarmTime_data = "0 " + String.valueOf(date.getDay()) + " " + String.valueOf(date.getHours()) + " " + String.valueOf(date.getMinutes());
            sendCommandToRemote(9, alarmTime_header);
            sendPureDatToRemote(alarmTime_data.getBytes());
            Log.i(LOG_TAG, "sendAlarmTime()");
        }
    }

    private void handShakeDone() {
        if (isOlderThanVersionTow) {
            isHandshake = true;
            Context context = this.mContext;
            this.mContext.getSharedPreferences("installprefs", 0).edit().putBoolean("isConnected", true);
            this.serviceHandler.sendEmptyMessage(TYPE_BT_CONNECTED);
            sendDataFromFile();
            Log.i(LOG_TAG, "mTimer is canceled verstion is old");
            return;
        }
        try {
            sendSyncTime(false);
            Log.i(LOG_TAG, "mTimer is canceled verstion is new");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private void runningSyncTimer() {
        TimerTask task = new C07012();
        if (this.mTimer == null) {
            this.mTimer = new Timer();
        }
        this.mTimer.schedule(task, 6000);
    }

    private void runningReadFSM() {
        Log.i(LOG_TAG, "runningReadFSM() READBUFFERSTATE = " + this.READBUFFERSTATE);
        switch (this.READBUFFERSTATE) {
            case 0:
                getCommandLenth();
                return;
            case 1:
                getCmdAndDataLenth();
                return;
            case 2:
                getData();
                return;
            default:
                return;
        }
    }

    private void getCommandLenth() {
        if (this.READBUFFERSTATE == 0) {
            int cmdpos = -1;
            if (reciveBufferLenth < 8) {
                Log.i(LOG_TAG, "getCommandLenth(): reciveBufferLenth < Constants.NOTIFYMINIHEADERLENTH");
                return;
            }
            int i = 0;
            while (i < reciveBufferLenth - 4) {
                if (reciveBuffer[i] == (byte) -16 && reciveBuffer[i + 1] == (byte) -16 && reciveBuffer[i + 2] == (byte) -16 && reciveBuffer[i + 3] == (byte) -15) {
                    cmdpos = i;
                    Log.i(LOG_TAG, "getCommandLenth(): Get F0F0F0F1 Success");
                    break;
                }
                i++;
            }
            if (cmdpos != -1) {
                cmdBufferLenth = (((reciveBuffer[i + 4] << 24) | (reciveBuffer[i + 5] << 16)) | (reciveBuffer[i + 6] << 8)) | reciveBuffer[i + 7];
                System.arraycopy(reciveBuffer, 8, reciveBuffer, 0, reciveBufferLenth - 8);
                reciveBufferLenth -= 8;
                this.READBUFFERSTATE = 1;
                Log.i(LOG_TAG, "getCommandLenth(): Get cmdBufferLenth Success cmdBufferLenth is " + cmdBufferLenth + "reciveBufferLenth is " + reciveBufferLenth);
                runningReadFSM();
                return;
            }
            System.arraycopy(reciveBuffer, 8, reciveBuffer, 0, reciveBufferLenth - 8);
            reciveBufferLenth -= 8;
            this.READBUFFERSTATE = 0;
            Log.i(LOG_TAG, "getCommandLenth(): Get cmdBufferLenth Success cmdBufferLenth is " + cmdBufferLenth + "reciveBufferLenth is " + reciveBufferLenth);
            runningReadFSM();
        }
    }

    private void getCmdAndDataLenth() {
        if (reciveBufferLenth < cmdBufferLenth) {
            Log.i(LOG_TAG, "getDataLenth():reciveBufferLenth < cmdBufferLenth");
            return;
        }
        commandBuffer = new byte[cmdBufferLenth];
        System.arraycopy(reciveBuffer, 0, commandBuffer, 0, cmdBufferLenth);
        System.arraycopy(reciveBuffer, cmdBufferLenth, reciveBuffer, 0, reciveBufferLenth - cmdBufferLenth);
        reciveBuffer[reciveBufferLenth - cmdBufferLenth] = (byte) 0;
        reciveBufferLenth -= cmdBufferLenth;
        if (reciveBufferLenth == 39) {
            reciveBufferLenth = (reciveBufferLenth + 1) - 1;
        }
        Log.i(LOG_TAG, "getDataLenth() :Get cmdBuffer Success cmdBufferLenth is " + cmdBufferLenth + "reciveBufferLenth is " + reciveBufferLenth);
        this.CMD_TYPE = this.mLoadJniFunction.getCmdType(commandBuffer, cmdBufferLenth);
        Log.i(LOG_TAG, "Get data Success and the CMD_TYPE is " + this.CMD_TYPE);
        if (isBTConnected()) {
            if (this.CMD_TYPE == 1 || this.CMD_TYPE == 5 || this.CMD_TYPE == 6 || this.CMD_TYPE == 7 || this.CMD_TYPE == 8 || this.CMD_TYPE == 9) {
                dataBufferLenth = this.mLoadJniFunction.getDataLenth(commandBuffer, cmdBufferLenth);
                Log.i(LOG_TAG, "getDataLenth():Get dataBufferLenth Success dataBufferLenth is " + dataBufferLenth);
                if (dataBufferLenth == -1) {
                    this.READBUFFERSTATE = 0;
                    return;
                }
                this.READBUFFERSTATE = 2;
                runningReadFSM();
                return;
            }
            this.READBUFFERSTATE = 0;
        } else if (this.mLoadJniFunction.getCmdType(commandBuffer, cmdBufferLenth) == 3) {
            isHandshake = true;
            Log.i(LOG_TAG, "isHandshake = true");
            reciveBufferLenth = 0;
            for (int i = 0; i < reciveBuffer.length; i++) {
                reciveBuffer[i] = (byte) 0;
            }
            this.serviceHandler.sendEmptyMessage(TYPE_BT_CONNECTED);
            sendDataFromFile();
            this.READBUFFERSTATE = 0;
            runningReadFSM();
        } else if (this.mLoadJniFunction.getCmdType(commandBuffer, cmdBufferLenth) == 4) {
            reciveBuffer[0] = (byte) 0;
            reciveBufferLenth = 0;
            isOlderThanVersionTow = false;
            this.READBUFFERSTATE = 0;
            this.mTimer.cancel();
            this.mTimer = null;
            handShakeDone();
            Log.i(LOG_TAG, "getDataLenth():Get the Version Success");
        } else {
            this.READBUFFERSTATE = 0;
        }
    }

    private void getData() {
        if (dataBufferLenth <= reciveBufferLenth) {
            dataBuffer = new byte[dataBufferLenth];
            System.arraycopy(reciveBuffer, 0, dataBuffer, 0, dataBufferLenth);
            System.arraycopy(reciveBuffer, dataBufferLenth, reciveBuffer, 0, reciveBufferLenth - dataBufferLenth);
            reciveBuffer[reciveBufferLenth - dataBufferLenth] = (byte) 0;
            reciveBufferLenth -= dataBufferLenth;
            this.READBUFFERSTATE = 0;
            cmdBufferLenth = 0;
            dataBufferLenth = 0;
            if (this.CMD_TYPE == 1) {
                Message msg = new Message();
                msg.what = TYPE_DATA_ARRIVE;
                Bundle bundle = new Bundle();
                bundle.putByteArray("dataBuffer", dataBuffer);
                msg.setData(bundle);
                this.serviceHandler.sendMessage(msg);
            } else if (this.CMD_TYPE == 5 || this.CMD_TYPE == 6) {
                Log.i(LOG_TAG, "sendBroadcast of MAPX OR MAPD :" + this.CMD_TYPE);
            } else if (this.CMD_TYPE == 9) {
                String[] commands = new String(dataBuffer).split(" ");
                try {
                    if (commands[1].equals("mtk_bnapk")) {
                        if (commands[0].equals("bnsrv_time")) {
                            sendSyncTime(true);
                        } else if (commands[0].equals("bnsrv_alarm")) {
                            sendAlarmTime();
                        } else {
                            Intent broadcastIntent = new Intent();
                            broadcastIntent.setAction(commands[1]);
                            if (dataBuffer != null) {
                                broadcastIntent.putExtra("EXTRA_DATA", dataBuffer);
                            }
                            this.mContext.sendBroadcast(broadcastIntent);
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            Log.i(LOG_TAG, "reciveBufferLenth is " + reciveBufferLenth);
            if (reciveBufferLenth != 0) {
                runningReadFSM();
            }
        }
    }

    public void sendBroadcasetToMapService(byte[] dataBuffer) {
    }

    public void sendCustomCmd(int cmd, String s) {
        sendCustomCmd(cmd, s, new char[]{'ÿ', 'ÿ', 'ÿ', 'ÿ'});
    }

    public void sendCustomCmd(int cmd, String s, char[] tag) {
        String content = buildMsgContent(cmd + 32, s, tag);
        Log.i(LOG_TAG, "sendCustomCmd cmd= " + cmd + " ||s = " + s + " content:" + content);
        MessageHeader header = new MessageHeader();
        header.setCategory(MessageObj.CATEGORY_NOTI);
        header.setSubType(MessageObj.SUBTYPE_SMS);
        header.setMsgId(Util.genMessageId());
        header.setAction(MessageObj.ACTION_ADD);
        String phoneNum = BtManagerService.TYD_NUMBER;
        int timestamp = Util.getUtcTime(Calendar.getInstance().getTimeInMillis());
        SmsMessageBody body = new SmsMessageBody();
        body.setSender("TYD");
        body.setNumber(phoneNum);
        body.setContent(content);
        body.setTimestamp(timestamp);
        MessageObj smsMessageData = new MessageObj();
        smsMessageData.setDataHeader(header);
        smsMessageData.setDataBody(body);
        sendData(genBytesFromObject(smsMessageData));
    }

    private byte[] genBytesFromObject(MessageObj dataObj) {
        Log.i(LOG_TAG, "genBytesFromObject(), dataObj=" + dataObj);
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

    private String buildMsgContent(int cmd, String s, char[] c_tag) {
        String s_utf8 = "";
        char[] c_cmd = new char[]{(char) cmd};
        char[] c_msg = s.toCharArray();
        char[] c_ucs2 = new char[((c_cmd.length + c_tag.length) + c_msg.length)];
        System.arraycopy(c_cmd, 0, c_ucs2, 0, c_cmd.length);
        System.arraycopy(c_tag, 0, c_ucs2, c_cmd.length, c_tag.length);
        System.arraycopy(c_msg, 0, c_ucs2, c_cmd.length + c_tag.length, c_msg.length);
        return new String(c_ucs2);
    }
}

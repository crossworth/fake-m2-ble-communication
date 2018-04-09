package com.droi.btlib.connection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.droi.btlib.connection.BluetoothConnector.BluetoothSocketWrapper;
import com.droi.btlib.device.DeviceDetail;
import com.droi.btlib.service.BluetoothManager;
import com.droi.btlib.service.BtManagerService;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class BluetoothConnection {
    public static final int MESSAGE_CONNECT_FAIL = 6;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_TOAST = 5;
    public static final int MESSAGE_WRITE = 3;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String NAME = "BTNotification";
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECT_LOST = 4;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_NONE = 0;
    public static final String TAG = "BluetoothConnection";
    private final BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    private ClientThread mClientThread;
    private int mConnectState = 0;
    private final Handler mMessageHandler;
    private ServerThread mServerThread;
    private WorkThread mWorkThread;

    private class ClientThread extends Thread {
        private BluetoothSocketWrapper mClientSocket;
        private BluetoothConnector mConnector;
        private final BluetoothDevice mRemoteDevice;

        public ClientThread(BluetoothDevice remoteDevice) {
            this.mRemoteDevice = remoteDevice;
            ArrayList<UUID> list = new ArrayList();
            list.add(BluetoothConnection.MY_UUID);
            this.mConnector = new BluetoothConnector(remoteDevice, true, BluetoothConnection.this.mAdapter, list);
        }

        public void run() {
            Log.i(BluetoothConnection.TAG, "ClientThread BEGIN");
            setName("ClientThread");
            BluetoothConnection.this.mAdapter.cancelDiscovery();
            try {
                this.mClientSocket = this.mConnector.connect();
                synchronized (BluetoothConnection.this) {
                    BluetoothConnection.this.mClientThread = null;
                }
                BluetoothConnection.this.connected(this.mClientSocket.getUnderlyingSocket(), this.mRemoteDevice);
            } catch (Exception e) {
                String errorString = e.toString();
                if (errorString == null) {
                    errorString = "connect error";
                }
                Log.w(BluetoothConnection.TAG, errorString);
                BluetoothConnection.this.connectionFailed();
                try {
                    this.mClientSocket.close();
                } catch (Exception e2) {
                    e.printStackTrace();
                }
            }
        }

        public void cancel() {
            Log.i(BluetoothConnection.TAG, "cancel(), ClientThread is canceled");
            try {
                if (this.mClientSocket != null) {
                    this.mClientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                Log.e(BluetoothConnection.TAG, e2.toString());
            }
        }
    }

    private class ServerThread extends Thread {
        private final BluetoothServerSocket mServerSocket;

        public ServerThread() {
            BluetoothServerSocket tmp = null;
            try {
                tmp = BluetoothConnection.this.mAdapter.listenUsingRfcommWithServiceRecord(BluetoothConnection.NAME, BluetoothConnection.MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.mServerSocket = tmp;
        }

        public void run() {
            Log.i(BluetoothConnection.TAG, "ServerThread BEGIN" + this);
            setName("ServerThread");
            while (BluetoothConnection.this.mConnectState != 3) {
                try {
                    BluetoothSocket socket = this.mServerSocket.accept();
                    if (socket != null) {
                        synchronized (BluetoothConnection.this) {
                            switch (BluetoothConnection.this.mConnectState) {
                                case 0:
                                case 3:
                                    try {
                                        socket.close();
                                        break;
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        break;
                                    }
                                case 1:
                                case 2:
                                    BluetoothConnection.this.connected(socket, socket.getRemoteDevice());
                                    break;
                            }
                        }
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            Log.i(BluetoothConnection.TAG, "ServerThread END");
            return;
        }

        public void cancel() {
            Log.i(BluetoothConnection.TAG, "cancel(),  ServerThread is canceled");
            try {
                if (this.mServerSocket != null) {
                    this.mServerSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private class WorkThread extends Thread {
        private final InputStream mInStream;
        private final OutputStream mOutStream;
        private final BluetoothSocket mSocket;

        public WorkThread(BluetoothSocket socket) {
            Log.i(BluetoothConnection.TAG, "WorkThread(), create WorkThread");
            this.mSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.mInStream = tmpIn;
            this.mOutStream = tmpOut;
        }

        public void run() {
            Log.i(BluetoothConnection.TAG, "WorkThread BEGIN");
            while (true) {
                try {
                    byte[] buffer = new byte[5120];
                    int bytes = this.mInStream.read(buffer);
                    Log.i(BluetoothConnection.TAG, "read data frome smart client, the lenth is " + bytes);
                    if (bytes > 0) {
                        BluetoothConnection.this.mMessageHandler.obtainMessage(2, bytes, -1, buffer).sendToTarget();
                    }
                } catch (IOException e) {
                    Log.w(BluetoothConnection.TAG, "IOException:" + e);
                    BluetoothConnection.this.connectionLost();
                    return;
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                this.mOutStream.write(buffer);
                Log.i(BluetoothConnection.TAG, "Write to Feature Phone SPP" + buffer.length);
                BluetoothConnection.this.mMessageHandler.obtainMessage(3, -1, -1, buffer).sendToTarget();
            } catch (Exception e) {
                e.printStackTrace();
                Log.w(BluetoothConnection.TAG, "Exception during write:" + e);
                BluetoothConnection.this.connectionLost();
            }
        }

        public void cancel() {
            Log.i(BluetoothConnection.TAG, "cancel(),  WorkThread is canceled");
            try {
                this.mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public BluetoothConnection(Handler handler) {
        Log.i(TAG, "BluetoothConnection(), BluetoothConnection created!");
        this.mMessageHandler = handler;
    }

    private synchronized void setState(int state) {
        Log.i(TAG, "setState(), state=" + state);
        this.mConnectState = state;
        this.mMessageHandler.obtainMessage(1, state, -1).sendToTarget();
    }

    public synchronized int getState() {
        Log.i(TAG, "getState(), mConnectState=" + this.mConnectState);
        return this.mConnectState;
    }

    public synchronized void startAccept() {
        Log.i(TAG, "startAccept()");
        if (this.mClientThread != null) {
            this.mClientThread.cancel();
            this.mClientThread = null;
        }
        if (this.mWorkThread != null) {
            this.mWorkThread.cancel();
            this.mWorkThread = null;
        }
        if (this.mServerThread == null) {
            this.mServerThread = new ServerThread();
            this.mServerThread.start();
        }
        setState(1);
    }

    public synchronized void disconnectRemoteDevice() {
        if (this.mConnectState == 2 && this.mClientThread != null) {
            this.mClientThread.cancel();
            this.mClientThread = null;
        }
        if (this.mWorkThread != null) {
            this.mWorkThread.cancel();
            this.mWorkThread = null;
        }
    }

    public synchronized void connectRemoteDevice(BluetoothDevice remoteDevice) {
        Log.i(TAG, "connectRemoteDevice(), device=" + remoteDevice);
        if (this.mConnectState == 2 && this.mClientThread != null) {
            this.mClientThread.cancel();
            this.mClientThread = null;
        }
        if (this.mWorkThread != null) {
            this.mWorkThread.cancel();
            this.mWorkThread = null;
        }
        this.mClientThread = new ClientThread(remoteDevice);
        this.mClientThread.start();
        setState(2);
    }

    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        Log.i(TAG, "connected(), socket=" + socket + ", device=" + device);
        String deviceName = device.getName();
        if (!(deviceName == null || deviceName.equals(""))) {
            Iterator it = BtManagerService.mClassicDeviceDetailList.iterator();
            while (it.hasNext()) {
                DeviceDetail detail = (DeviceDetail) it.next();
                if (device.getName() != null && device.getName().equals(detail.getName())) {
                    if (this.mClientThread != null) {
                        this.mClientThread.cancel();
                        this.mClientThread = null;
                    }
                    if (this.mWorkThread != null) {
                        this.mWorkThread.cancel();
                        this.mWorkThread = null;
                    }
                    if (this.mServerThread != null) {
                        this.mServerThread.cancel();
                        this.mServerThread = null;
                    }
                    this.mWorkThread = new WorkThread(socket);
                    this.mWorkThread.start();
                    BtManagerService.setBtDevice(device, 1);
                    setState(3);
                    write("mtk".getBytes());
                }
            }
        }
    }

    public synchronized void stop() {
        Log.i(TAG, "stop()");
        if (this.mClientThread != null) {
            this.mClientThread.cancel();
            this.mClientThread = null;
        }
        if (this.mWorkThread != null) {
            this.mWorkThread.cancel();
            this.mWorkThread = null;
        }
        if (this.mServerThread != null) {
            this.mServerThread.cancel();
            this.mServerThread = null;
        }
        setState(0);
    }

    public void write(byte[] out) {
        synchronized (this) {
            if (this.mConnectState != 3) {
                return;
            }
            WorkThread r = this.mWorkThread;
            r.write(out);
        }
    }

    private void connectionFailed() {
        Log.i(TAG, "connectionFailed()");
        setState(1);
        Message msg = this.mMessageHandler.obtainMessage(6);
        Bundle bundle = new Bundle();
        bundle.putString(BluetoothManager.TOAST, "Unable to connect device");
        msg.setData(bundle);
        this.mMessageHandler.sendMessage(msg);
        startAccept();
    }

    private void connectionLost() {
        Log.i(TAG, "connectionLost()");
        setState(4);
        startAccept();
        Log.i(TAG, "connectionLost(), ServerThread restart!");
    }

    public boolean isEnable() {
        return this.mAdapter.isEnabled();
    }
}

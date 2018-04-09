package com.zhuoyou.plugin.bluetooth.connection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.zhuoyi.system.network.util.NetworkConstants;
import com.zhuoyou.plugin.bluetooth.data.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothConnection {
    public static final String LOG_TAG = "BluetoothConnection";
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String NAME = "BTNotification";
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECT_LOST = 4;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_NONE = 0;
    private final BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    private ClientThread mClientThread;
    private int mConnectState = 0;
    private final Handler mMessageHandler;
    private ServerThread mServerThread;
    private WorkThread mWorkThread;

    private class ClientThread extends Thread {
        private final BluetoothSocket mClientSocket;
        private final BluetoothDevice mRemoteDevice;

        public ClientThread(BluetoothDevice remoteDevice) {
            this.mRemoteDevice = remoteDevice;
            BluetoothSocket tmp = null;
            if (remoteDevice != null) {
                try {
                    tmp = remoteDevice.createRfcommSocketToServiceRecord(BluetoothConnection.MY_UUID);
                } catch (IOException e) {
                    Log.w("create client socket failed", e);
                }
            }
            this.mClientSocket = tmp;
        }

        public void run() {
            Log.i(BluetoothConnection.LOG_TAG, "ClientThread BEGIN");
            setName("ClientThread");
            BluetoothConnection.this.mAdapter.cancelDiscovery();
            try {
                this.mClientSocket.connect();
                synchronized (BluetoothConnection.this) {
                    BluetoothConnection.this.mClientThread = null;
                }
                BluetoothConnection.this.connected(this.mClientSocket, this.mRemoteDevice);
            } catch (Exception e) {
                String errorString = e.toString();
                if (errorString == null) {
                    errorString = "connect error";
                }
                Log.w(BluetoothConnection.LOG_TAG, errorString);
                BluetoothConnection.this.connectionFailed();
                try {
                    this.mClientSocket.close();
                } catch (Exception e2) {
                    Log.w("unable to close socket during connection failure", e2);
                }
            }
        }

        public void cancel() {
            Log.i(BluetoothConnection.LOG_TAG, "cancel(), ClientThread is canceled");
            try {
                if (this.mClientSocket != null) {
                    this.mClientSocket.close();
                }
            } catch (IOException e) {
                Log.w("close connect socket failed", e);
            } catch (Exception e2) {
                Log.e(BluetoothConnection.LOG_TAG, e2.toString());
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
                Log.w("ServerThread listen() failed", e);
            }
            this.mServerSocket = tmp;
        }

        public void run() {
            Log.i(BluetoothConnection.LOG_TAG, "ServerThread BEGIN" + this);
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
                                        Log.w("ServerThread Could not close unwanted socket", e);
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
                    Log.w("ServerThread accept() failed", e2);
                } catch (Exception e3) {
                    Log.w("mServerSocket is exception", e3);
                }
            }
            Log.i(BluetoothConnection.LOG_TAG, "ServerThread END");
            return;
        }

        public void cancel() {
            Log.i(BluetoothConnection.LOG_TAG, "cancel(),  ServerThread is canceled");
            try {
                if (this.mServerSocket != null) {
                    this.mServerSocket.close();
                }
            } catch (IOException e) {
                Log.w("close server socket failed", e);
            } catch (Exception e2) {
                Log.w("mServerSocket is exception", e2);
            }
        }
    }

    private class WorkThread extends Thread {
        private final InputStream mInStream;
        private final OutputStream mOutStream;
        private final BluetoothSocket mSocket;

        public WorkThread(BluetoothSocket socket) {
            Log.i(BluetoothConnection.LOG_TAG, "WorkThread(), create WorkThread");
            this.mSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.w("temp sockets not created", e);
            }
            this.mInStream = tmpIn;
            this.mOutStream = tmpOut;
        }

        public void run() {
            Log.i(BluetoothConnection.LOG_TAG, "WorkThread BEGIN");
            while (true) {
                try {
                    byte[] buffer = new byte[NetworkConstants.CONNECTION_BUFFER_SIZE];
                    int bytes = this.mInStream.read(buffer);
                    Log.i(BluetoothConnection.LOG_TAG, "read data frome smart client, the lenth is " + bytes);
                    if (bytes > 0) {
                        BluetoothConnection.this.mMessageHandler.obtainMessage(2, bytes, -1, buffer).sendToTarget();
                    }
                } catch (IOException e) {
                    Log.w("disconnected", e);
                    BluetoothConnection.this.connectionLost();
                    return;
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                this.mOutStream.write(buffer);
                Log.i(BluetoothConnection.LOG_TAG, "Write to Feature Phone SPP" + buffer.length);
                BluetoothConnection.this.mMessageHandler.obtainMessage(3, -1, -1, buffer).sendToTarget();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(BluetoothConnection.LOG_TAG, "Exception during write", e);
                BluetoothConnection.this.connectionLost();
            }
        }

        public void cancel() {
            Log.i(BluetoothConnection.LOG_TAG, "cancel(),  WorkThread is canceled");
            try {
                this.mSocket.close();
            } catch (IOException e) {
                Log.w("close connected socket failed", e);
            }
        }
    }

    public BluetoothConnection(Handler handler) {
        Log.i(LOG_TAG, "BluetoothConnection(), BluetoothConnection created!");
        this.mMessageHandler = handler;
    }

    private synchronized void setState(int state) {
        Log.i(LOG_TAG, "setState(), state=" + state);
        this.mConnectState = state;
        this.mMessageHandler.obtainMessage(1, state, -1).sendToTarget();
    }

    public synchronized int getState() {
        Log.i(LOG_TAG, "getState(), mConnectState=" + this.mConnectState);
        return this.mConnectState;
    }

    public synchronized void startAccept() {
        Log.i(LOG_TAG, "startAccept()");
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
        Log.i(LOG_TAG, "connectRemoteDevice(), device=" + remoteDevice);
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
        Log.i(LOG_TAG, "connected(), socket=" + socket + ", device=" + device);
        String deviceName = device.getName();
        if (!(deviceName == null || deviceName.equals(""))) {
            int count = Util.filterNames.length;
            int i = 0;
            while (i < count) {
                if (Util.filterNames[i].equals(deviceName)) {
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
                    BtProfileReceiver.stopAutoConnect();
                    Message msg = this.mMessageHandler.obtainMessage(4);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("device_name", device);
                    msg.setData(bundle);
                    this.mMessageHandler.sendMessage(msg);
                    setState(3);
                    write("mtk".getBytes());
                } else {
                    i++;
                }
            }
        }
    }

    public synchronized void stop() {
        Log.i(LOG_TAG, "stop()");
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
        Log.i(LOG_TAG, "connectionFailed()");
        setState(1);
        Message msg = this.mMessageHandler.obtainMessage(6);
        Bundle bundle = new Bundle();
        bundle.putString(BluetoothManager.TOAST, "Unable to connect device");
        msg.setData(bundle);
        this.mMessageHandler.sendMessage(msg);
        startAccept();
    }

    private void connectionLost() {
        Log.i(LOG_TAG, "connectionLost()");
        setState(4);
        startAccept();
        Log.i(LOG_TAG, "connectionLost(), ServerThread restart!");
    }

    public boolean isEnable() {
        return this.mAdapter.isEnabled();
    }
}

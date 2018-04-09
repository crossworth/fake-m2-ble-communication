package com.droi.btlib.connection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BluetoothConnector {
    private BluetoothAdapter adapter;
    private BluetoothSocketWrapper bluetoothSocket;
    private int candidate;
    private BluetoothDevice device;
    private boolean secure;
    private List<UUID> uuidCandidates;

    public interface BluetoothSocketWrapper {
        void close() throws IOException;

        void connect() throws IOException;

        InputStream getInputStream() throws IOException;

        OutputStream getOutputStream() throws IOException;

        String getRemoteDeviceAddress();

        String getRemoteDeviceName();

        BluetoothSocket getUnderlyingSocket();
    }

    public static class NativeBluetoothSocket implements BluetoothSocketWrapper {
        private BluetoothSocket socket;

        public NativeBluetoothSocket(BluetoothSocket tmp) {
            this.socket = tmp;
        }

        public InputStream getInputStream() throws IOException {
            return this.socket.getInputStream();
        }

        public OutputStream getOutputStream() throws IOException {
            return this.socket.getOutputStream();
        }

        public String getRemoteDeviceName() {
            return this.socket.getRemoteDevice().getName();
        }

        public void connect() throws IOException {
            this.socket.connect();
        }

        public String getRemoteDeviceAddress() {
            return this.socket.getRemoteDevice().getAddress();
        }

        public void close() throws IOException {
            this.socket.close();
        }

        public BluetoothSocket getUnderlyingSocket() {
            return this.socket;
        }
    }

    public class FallbackBluetoothSocket extends NativeBluetoothSocket {
        private BluetoothSocket fallbackSocket;

        public FallbackBluetoothSocket(BluetoothSocket tmp) throws FallbackException {
            super(tmp);
            try {
                this.fallbackSocket = (BluetoothSocket) tmp.getRemoteDevice().getClass().getMethod("createRfcommSocket", new Class[]{Integer.TYPE}).invoke(tmp.getRemoteDevice(), new Object[]{Integer.valueOf(1)});
            } catch (Exception e) {
                throw new FallbackException(e);
            }
        }

        public InputStream getInputStream() throws IOException {
            return this.fallbackSocket.getInputStream();
        }

        public OutputStream getOutputStream() throws IOException {
            return this.fallbackSocket.getOutputStream();
        }

        public void connect() throws IOException {
            this.fallbackSocket.connect();
        }

        public void close() throws IOException {
            this.fallbackSocket.close();
        }
    }

    public static class FallbackException extends Exception {
        private static final long serialVersionUID = 1;

        public FallbackException(Exception e) {
            super(e);
        }
    }

    public BluetoothConnector(BluetoothDevice device, boolean secure, BluetoothAdapter adapter, List<UUID> uuidCandidates) {
        this.device = device;
        this.secure = secure;
        this.adapter = adapter;
        this.uuidCandidates = uuidCandidates;
        if (this.uuidCandidates == null || this.uuidCandidates.isEmpty()) {
            this.uuidCandidates = new ArrayList();
            this.uuidCandidates.add(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            this.uuidCandidates.add(UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66"));
        }
    }

    public BluetoothSocketWrapper connect() throws IOException {
        boolean success = false;
        while (selectSocket()) {
            this.adapter.cancelDiscovery();
            try {
                this.bluetoothSocket.connect();
                success = true;
                break;
            } catch (IOException e) {
                try {
                    this.bluetoothSocket = new FallbackBluetoothSocket(this.bluetoothSocket.getUnderlyingSocket());
                    Thread.sleep(500);
                    this.bluetoothSocket.connect();
                    success = true;
                    break;
                } catch (FallbackException e2) {
                    Log.w("chenxin", "Could not initialize FallbackBluetoothSocket classes.", e);
                } catch (InterruptedException e1) {
                    Log.w("chenxin", e1.getMessage(), e1);
                } catch (IOException e12) {
                    Log.w("chenxin", "Fallback failed. Cancelling.", e12);
                }
            }
        }
        if (success) {
            return this.bluetoothSocket;
        }
        throw new IOException("Could not connect to device: " + this.device.getAddress());
    }

    private boolean selectSocket() throws IOException {
        if (this.candidate >= this.uuidCandidates.size()) {
            return false;
        }
        BluetoothSocket tmp;
        List list = this.uuidCandidates;
        int i = this.candidate;
        this.candidate = i + 1;
        UUID uuid = (UUID) list.get(i);
        Log.i("chenxin", "Attempting to connect to Protocol: " + uuid + " secure:" + this.secure);
        if (this.secure) {
            tmp = this.device.createRfcommSocketToServiceRecord(uuid);
        } else {
            tmp = this.device.createInsecureRfcommSocketToServiceRecord(uuid);
        }
        this.bluetoothSocket = new NativeBluetoothSocket(tmp);
        return true;
    }
}

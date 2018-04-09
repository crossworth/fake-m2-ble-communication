package com.zhuoyi.system.network.connection;

import com.zhuoyi.system.network.callback.NetworkCallback;
import com.zhuoyi.system.network.object.NetworkAddr;
import com.zhuoyi.system.network.serializer.AttributeUitl;
import com.zhuoyi.system.network.serializer.MessageCodec;
import com.zhuoyi.system.network.serializer.SignalCode;
import com.zhuoyi.system.network.serializer.ZyCom_Message;
import com.zhuoyi.system.network.serializer.ZyCom_MessageHead;
import com.zhuoyi.system.network.util.NetworkConstants;
import com.zhuoyi.system.util.Logger;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;

public class HTTPConnection {
    private static HTTPConnection mConnection;
    ThreadPoolExecutor executor = null;
    private MessageCodec m_MessageCodec = new MessageCodec();

    class SendRunnable implements Runnable {
        private NetworkCallback mCallback;
        String m_HTTPServerAddress;
        private ZyCom_Message sendMessage;

        public SendRunnable(NetworkAddr networkAddr, ZyCom_Message msg, NetworkCallback callback) {
            this.m_HTTPServerAddress = networkAddr.getServerAddress();
            this.sendMessage = msg;
            this.mCallback = callback;
        }

        public void run() {
            do {
                try {
                    byte[] recvBuffer = new byte[NetworkConstants.CONNECTION_BUFFER_SIZE];
                    byte[] sendBuff = HTTPConnection.this.m_MessageCodec.serializeMessage(this.sendMessage);
                    HttpURLConnection connection = (HttpURLConnection) new URL(this.m_HTTPServerAddress).openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(NetworkConstants.CONNECTION_TIMEOUT);
                    connection.setReadTimeout(30000);
                    connection.setUseCaches(false);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setInstanceFollowRedirects(true);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.connect();
                    OutputStream outStream = connection.getOutputStream();
                    outStream.write(sendBuff);
                    outStream.flush();
                    outStream.close();
                    Logger.debug(NetworkConstants.TAG, "send " + this.sendMessage.message.getClass() + " to " + this.m_HTTPServerAddress);
                    int code = connection.getResponseCode();
                    if (code == 200 || code == 206) {
                        InputStream inputStream = connection.getInputStream();
                        ByteArrayOutputStream saveStream = new ByteArrayOutputStream();
                        while (true) {
                            int len = inputStream.read(recvBuffer);
                            if (len == -1) {
                                break;
                            }
                            saveStream.write(recvBuffer, 0, len);
                        }
                        byte[] responseBuff = saveStream.toByteArray();
                        saveStream.close();
                        inputStream.close();
                        connection.disconnect();
                        ZyCom_Message recvMessage = new ZyCom_Message();
                        recvMessage.head = HTTPConnection.this.m_MessageCodec.deserializeHead(responseBuff, 0);
                        if (responseBuff.length < recvMessage.head.length) {
                            throw new Exception("receive data fail, recv " + responseBuff.length + " bytes < message length " + recvMessage.head.length + ", ");
                        }
                        recvMessage.message = HTTPConnection.this.m_MessageCodec.deserializeBody(responseBuff, 28, responseBuff.length - 28, recvMessage.head.code);
                        Logger.debug(NetworkConstants.TAG, "recv " + recvMessage.message.getClass());
                        if (this.mCallback != null) {
                            this.mCallback.onResponse(Boolean.valueOf(true), recvMessage);
                            return;
                        }
                        return;
                    }
                    throw new Exception("get response fail , response code = " + code);
                } catch (Exception e) {
                    Logger.m3375p(e);
                    Logger.error(NetworkConstants.TAG, "Http connection throws exception ");
                    if (!checkRetrySend(this.sendMessage, e.getClass().getSimpleName())) {
                        if (this.mCallback == null) {
                            Logger.error(NetworkConstants.TAG, "mCallback is null");
                        } else {
                            this.mCallback.onResponse(Boolean.valueOf(false), this.sendMessage);
                        }
                        Logger.debug(NetworkConstants.TAG, "connection is closed");
                    }
                }
            } while (checkRetrySend(this.sendMessage, e.getClass().getSimpleName()));
            if (this.mCallback == null) {
                this.mCallback.onResponse(Boolean.valueOf(false), this.sendMessage);
            } else {
                Logger.error(NetworkConstants.TAG, "mCallback is null");
            }
            Logger.debug(NetworkConstants.TAG, "connection is closed");
        }

        protected boolean checkRetrySend(ZyCom_Message message, String errorMsg) {
            try {
                message.retryCount++;
                SignalCode attrib = AttributeUitl.getMessageAttribute(message.message);
                if (attrib == null || !attrib.autoRetry() || message.retryCount >= 3) {
                    Logger.error(NetworkConstants.TAG, "send " + message.message.getClass() + " fail(" + message.retryCount + "), cancel :" + errorMsg);
                    return false;
                }
                Logger.error(NetworkConstants.TAG, "send " + message.message.getClass() + " fail(" + message.retryCount + "), retry :" + errorMsg);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static synchronized HTTPConnection getInstance() {
        HTTPConnection hTTPConnection;
        synchronized (HTTPConnection.class) {
            if (mConnection == null) {
                mConnection = new HTTPConnection();
            }
            hTTPConnection = mConnection;
        }
        return hTTPConnection;
    }

    private HTTPConnection() {
        startRecvThread();
    }

    public void shutdown() {
        this.executor = null;
    }

    public void startRecvThread() {
        if (this.executor == null) {
            this.executor = new ThreadPoolExecutor(3, 6, 5, TimeUnit.SECONDS, new ArrayBlockingQueue(20), new DiscardOldestPolicy());
        }
    }

    public synchronized void sendRequest(NetworkAddr networkAddr, Object requestObject, NetworkCallback callback) {
        try {
            this.executor.execute(new SendRunnable(networkAddr, getMessage(requestObject), callback));
        } catch (Exception e) {
            callback.onResponse(Boolean.valueOf(false), null);
        }
    }

    private ZyCom_Message getMessage(Object requestObject) throws Exception {
        SignalCode code = AttributeUitl.getMessageAttribute(requestObject);
        if (code == null || code.messageCode() == 0) {
            throw new Exception("can't get message code");
        }
        ZyCom_Message message = new ZyCom_Message();
        message.head = new ZyCom_MessageHead();
        message.head.version = (byte) 1;
        message.head.length = 0;
        message.head.firstTransaction = 0;
        message.head.secondTransaction = 0;
        message.head.type = (byte) 1;
        message.head.reserved = (short) 0;
        message.head.code = code.messageCode();
        message.message = requestObject;
        return message;
    }
}

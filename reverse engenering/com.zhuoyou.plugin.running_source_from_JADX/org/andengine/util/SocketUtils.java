package org.andengine.util;

import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import org.andengine.util.debug.Debug;

public final class SocketUtils {
    public static final void closeSocket(DatagramSocket pDatagramSocket) {
        if (pDatagramSocket != null && !pDatagramSocket.isClosed()) {
            pDatagramSocket.close();
        }
    }

    public static final void closeSocket(Socket pSocket) {
        if (pSocket != null && !pSocket.isClosed()) {
            try {
                pSocket.close();
            } catch (Throwable e) {
                Debug.m4592e(e);
            }
        }
    }

    public static final void closeSocket(ServerSocket pServerSocket) {
        if (pServerSocket != null && !pServerSocket.isClosed()) {
            try {
                pServerSocket.close();
            } catch (Throwable e) {
                Debug.m4592e(e);
            }
        }
    }
}

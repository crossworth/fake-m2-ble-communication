package com.zhuoyi.system.network.util;

public class NetworkConstants {
    public static final int CONNECTION_BUFFER_SIZE = 5120;
    public static final int CONNECTION_MAX_RETRY = 3;
    public static final int CONNECTION_TIMEOUT = 25000;
    public static final int DOWNLOAD_BUFFER_SIZE = 10240;
    public static final int HANDLER_DOWNLOAD_FAIL = 1;
    public static final int HANDLER_DOWNLOAD_STEP = 2;
    public static final int HANDLER_DOWNLOAD_SUCCESS = 0;
    public static final int HANDLER_NETWORK_FAIL = 1;
    public static final int HANDLER_NETWORK_SUCCESS = 0;
    public static final int NERWORK_TYPE_2G = 1;
    public static final int NERWORK_TYPE_3G = 2;
    public static final int NERWORK_TYPE_FAIL = 0;
    public static final int NERWORK_TYPE_UNKNOWN = 4;
    public static final int NERWORK_TYPE_WIFI = 3;
    public static final byte PROTOCL_REQ = (byte) 1;
    public static final byte PROTOCL_RESP = (byte) 2;
    public static final byte PROTOCL_VERSION = (byte) 1;
    public static final int PROTOCOL_HEAD_LENGTH = 28;
    public static final int READWIRTE_TIMEOUT = 30000;
    public static final String TAG = "ZyNetwork";
}

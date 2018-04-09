package com.amap.api.location.core;

public class AMapLocException extends Exception {
    public static final int ERROR_CODE_CONNECTION = 30;
    public static final int ERROR_CODE_FAILURE_AUTH = 32;
    public static final int ERROR_CODE_INVALID_PARAMETER = 24;
    public static final int ERROR_CODE_IO = 21;
    public static final int ERROR_CODE_NULL_PARAMETER = 25;
    public static final int ERROR_CODE_PROTOCOL = 29;
    public static final int ERROR_CODE_SOCKET = 22;
    public static final int ERROR_CODE_SOCKE_TIME_OUT = 23;
    public static final int ERROR_CODE_UNKNOWN = 31;
    public static final int ERROR_CODE_UNKNOW_HOST = 27;
    public static final int ERROR_CODE_UNKNOW_SERVICE = 28;
    public static final int ERROR_CODE_URL = 26;
    public static final String ERROR_CONNECTION = "http连接失败 - ConnectionException";
    public static final String ERROR_FAILURE_AUTH = "key鉴权失败";
    public static final String ERROR_INVALID_PARAMETER = "无效的参数 - IllegalArgumentException";
    public static final String ERROR_IO = "IO 操作异常 - IOException";
    public static final String ERROR_NULL_PARAMETER = "空指针异常 - NullPointException";
    public static final String ERROR_PROTOCOL = "协议解析错误 - ProtocolException";
    public static final String ERROR_SOCKET = "socket 连接异常 - SocketException";
    public static final String ERROR_SOCKE_TIME_OUT = "socket 连接超时 - SocketTimeoutException";
    public static final String ERROR_UNKNOWN = "未知的错误";
    public static final String ERROR_UNKNOW_HOST = "未知主机 - UnKnowHostException";
    public static final String ERROR_UNKNOW_SERVICE = "服务器连接失败 - UnknownServiceException";
    public static final String ERROR_URL = "url异常 - MalformedURLException";
    private String f84a = "正常";
    private int f85b = 0;

    public AMapLocException(String str) {
        this.f84a = str;
        m75a(str);
    }

    public String getErrorMessage() {
        return this.f84a;
    }

    public int getErrorCode() {
        return this.f85b;
    }

    private void m75a(String str) {
        if ("IO 操作异常 - IOException".equals(str)) {
            this.f85b = 21;
        } else if ("socket 连接异常 - SocketException".equals(str)) {
            this.f85b = 22;
        } else if ("socket 连接超时 - SocketTimeoutException".equals(str)) {
            this.f85b = 23;
        } else if ("无效的参数 - IllegalArgumentException".equals(str)) {
            this.f85b = 24;
        } else if ("空指针异常 - NullPointException".equals(str)) {
            this.f85b = 25;
        } else if ("url异常 - MalformedURLException".equals(str)) {
            this.f85b = 26;
        } else if ("未知主机 - UnKnowHostException".equals(str)) {
            this.f85b = 27;
        } else if ("服务器连接失败 - UnknownServiceException".equals(str)) {
            this.f85b = 28;
        } else if ("协议解析错误 - ProtocolException".equals(str)) {
            this.f85b = 29;
        } else if ("http连接失败 - ConnectionException".equals(str)) {
            this.f85b = 30;
        } else if ("未知的错误".equals(str)) {
            this.f85b = 31;
        } else if ("key鉴权失败".equals(str)) {
            this.f85b = 32;
        }
    }
}

package com.amap.api.mapcore.util;

/* compiled from: AMapCoreException */
public class dk extends Exception {
    private String f458a = "未知的错误";
    private int f459b = -1;

    public dk(String str) {
        super(str);
        this.f458a = str;
        m598a(str);
    }

    public String m599a() {
        return this.f458a;
    }

    public int m600b() {
        return this.f459b;
    }

    private void m598a(String str) {
        if ("IO 操作异常 - IOException".equals(str)) {
            this.f459b = 21;
        } else if ("socket 连接异常 - SocketException".equals(str)) {
            this.f459b = 22;
        } else if ("socket 连接超时 - SocketTimeoutException".equals(str)) {
            this.f459b = 23;
        } else if ("无效的参数 - IllegalArgumentException".equals(str)) {
            this.f459b = 24;
        } else if ("空指针异常 - NullPointException".equals(str)) {
            this.f459b = 25;
        } else if ("url异常 - MalformedURLException".equals(str)) {
            this.f459b = 26;
        } else if ("未知主机 - UnKnowHostException".equals(str)) {
            this.f459b = 27;
        } else if ("服务器连接失败 - UnknownServiceException".equals(str)) {
            this.f459b = 28;
        } else if ("协议解析错误 - ProtocolException".equals(str)) {
            this.f459b = 29;
        } else if ("http连接失败 - ConnectionException".equals(str)) {
            this.f459b = 30;
        } else if ("未知的错误".equals(str)) {
            this.f459b = 31;
        } else if ("key鉴权失败".equals(str)) {
            this.f459b = 32;
        } else if ("requeust is null".equals(str)) {
            this.f459b = 1;
        } else if ("request url is empty".equals(str)) {
            this.f459b = 2;
        } else if ("response is null".equals(str)) {
            this.f459b = 3;
        } else if ("thread pool has exception".equals(str)) {
            this.f459b = 4;
        } else if ("sdk name is invalid".equals(str)) {
            this.f459b = 5;
        } else if ("sdk info is null".equals(str)) {
            this.f459b = 6;
        } else if ("sdk packages is null".equals(str)) {
            this.f459b = 7;
        } else if ("线程池为空".equals(str)) {
            this.f459b = 8;
        } else if ("获取对象错误".equals(str)) {
            this.f459b = 101;
        } else {
            this.f459b = -1;
        }
    }
}

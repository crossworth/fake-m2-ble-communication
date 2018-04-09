package com.amap.api.services.proguard;

/* compiled from: AMapCoreException */
public class ar extends Exception {
    private String f1315a = "未知的错误";
    private int f1316b = -1;

    public ar(String str) {
        super(str);
        this.f1315a = str;
        m1206a(str);
    }

    public String m1207a() {
        return this.f1315a;
    }

    public int m1208b() {
        return this.f1316b;
    }

    private void m1206a(String str) {
        if ("IO 操作异常 - IOException".equals(str)) {
            this.f1316b = 21;
        } else if ("socket 连接异常 - SocketException".equals(str)) {
            this.f1316b = 22;
        } else if ("socket 连接超时 - SocketTimeoutException".equals(str)) {
            this.f1316b = 23;
        } else if ("无效的参数 - IllegalArgumentException".equals(str)) {
            this.f1316b = 24;
        } else if ("空指针异常 - NullPointException".equals(str)) {
            this.f1316b = 25;
        } else if ("url异常 - MalformedURLException".equals(str)) {
            this.f1316b = 26;
        } else if ("未知主机 - UnKnowHostException".equals(str)) {
            this.f1316b = 27;
        } else if ("服务器连接失败 - UnknownServiceException".equals(str)) {
            this.f1316b = 28;
        } else if ("协议解析错误 - ProtocolException".equals(str)) {
            this.f1316b = 29;
        } else if ("http连接失败 - ConnectionException".equals(str)) {
            this.f1316b = 30;
        } else if ("未知的错误".equals(str)) {
            this.f1316b = 31;
        } else if ("key鉴权失败".equals(str)) {
            this.f1316b = 32;
        } else if ("requeust is null".equals(str)) {
            this.f1316b = 1;
        } else if ("request url is empty".equals(str)) {
            this.f1316b = 2;
        } else if ("response is null".equals(str)) {
            this.f1316b = 3;
        } else if ("thread pool has exception".equals(str)) {
            this.f1316b = 4;
        } else if ("sdk name is invalid".equals(str)) {
            this.f1316b = 5;
        } else if ("sdk info is null".equals(str)) {
            this.f1316b = 6;
        } else if ("sdk packages is null".equals(str)) {
            this.f1316b = 7;
        } else if ("线程池为空".equals(str)) {
            this.f1316b = 8;
        } else if ("获取对象错误".equals(str)) {
            this.f1316b = 101;
        } else {
            this.f1316b = -1;
        }
    }
}

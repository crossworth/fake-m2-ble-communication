package com.baidu.mapapi.http;

import com.baidu.mapapi.http.HttpClient.ProtoResultCallback;
import com.tencent.connect.common.Constants;

class C0473a implements Runnable {
    final /* synthetic */ ProtoResultCallback f973a;
    final /* synthetic */ String f974b;
    final /* synthetic */ AsyncHttpClient f975c;

    C0473a(AsyncHttpClient asyncHttpClient, ProtoResultCallback protoResultCallback, String str) {
        this.f975c = asyncHttpClient;
        this.f973a = protoResultCallback;
        this.f974b = str;
    }

    public void run() {
        HttpClient httpClient = new HttpClient(this.f975c.f962a, Constants.HTTP_GET, this.f973a);
        httpClient.setMaxTimeOut(this.f975c.f963b);
        httpClient.setReadTimeOut(this.f975c.f964c);
        httpClient.request(this.f974b);
    }
}

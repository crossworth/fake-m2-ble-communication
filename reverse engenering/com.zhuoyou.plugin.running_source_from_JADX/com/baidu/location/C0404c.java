package com.baidu.location;

import com.baidu.location.p005a.C0338b;

class C0404c extends Thread {
    final /* synthetic */ LocationClient f555a;

    C0404c(LocationClient locationClient) {
        this.f555a = locationClient;
    }

    public void run() {
        if (this.f555a.mloc == null) {
            this.f555a.mloc = new C0338b(this.f555a.mContext, this.f555a.mOption, this.f555a);
        }
        this.f555a.mloc.m218c();
    }
}

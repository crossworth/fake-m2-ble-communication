package com.baidu.location.p012f;

import android.location.Location;
import android.os.Handler;
import android.os.Message;
import com.baidu.location.C0455f;

class C0449e extends Handler {
    final /* synthetic */ C0448d f794a;

    C0449e(C0448d c0448d) {
        this.f794a = c0448d;
    }

    public void handleMessage(Message message) {
        if (C0455f.isServing) {
            switch (message.what) {
                case 1:
                    this.f794a.m913e((Location) message.obj);
                    return;
                case 2:
                    if (this.f794a.f779j != null) {
                        this.f794a.f779j.m877a((String) message.obj);
                        return;
                    }
                    return;
                case 3:
                    this.f794a.m896a("&og=1", (Location) message.obj);
                    return;
                case 4:
                    this.f794a.m896a("&og=2", (Location) message.obj);
                    return;
                default:
                    return;
            }
        }
    }
}

package com.aps;

import android.os.Handler;
import android.os.Message;

final class ar extends Handler {
    private /* synthetic */ aq f1760a;

    ar(aq aqVar) {
        this.f1760a = aqVar;
    }

    public final void handleMessage(Message message) {
        try {
            switch (message.what) {
                case 1:
                    if (this.f1760a.f1759a.f1988E != null) {
                        this.f1760a.f1759a.f1988E.m1813a((String) message.obj);
                        return;
                    }
                    return;
                default:
                    return;
            }
        } catch (Exception e) {
        }
    }
}

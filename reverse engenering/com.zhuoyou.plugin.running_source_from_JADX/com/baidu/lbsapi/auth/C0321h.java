package com.baidu.lbsapi.auth;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

class C0321h extends Handler {
    final /* synthetic */ LBSAuthManager f84a;

    C0321h(LBSAuthManager lBSAuthManager, Looper looper) {
        this.f84a = lBSAuthManager;
        super(looper);
    }

    public void handleMessage(Message message) {
        if (C0311a.f70a) {
            C0311a.m122a("handleMessage !!");
        }
        LBSAuthManagerListener lBSAuthManagerListener = (LBSAuthManagerListener) LBSAuthManager.f65f.get(message.getData().getString("listenerKey"));
        if (C0311a.f70a) {
            C0311a.m122a("handleMessage listener = " + lBSAuthManagerListener);
        }
        if (lBSAuthManagerListener != null) {
            lBSAuthManagerListener.onAuthResult(message.what, message.obj.toString());
        }
    }
}

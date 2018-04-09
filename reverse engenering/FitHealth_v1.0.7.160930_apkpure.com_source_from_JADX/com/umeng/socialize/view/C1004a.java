package com.umeng.socialize.view;

import android.os.Handler;
import android.os.Message;

/* compiled from: OauthDialog */
class C1004a extends Handler {
    final /* synthetic */ OauthDialog f3472a;

    C1004a(OauthDialog oauthDialog) {
        this.f3472a = oauthDialog;
    }

    public void handleMessage(Message message) {
        super.handleMessage(message);
        if (message.what == 1 && this.f3472a.f3369e != null) {
            this.f3472a.f3369e.setVisibility(8);
        }
        if (message.what != 2) {
        }
    }
}

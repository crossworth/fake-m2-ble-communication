package com.umeng.socialize.handler;

import android.os.Bundle;
import com.tencent.open.SocialConstants;
import com.umeng.socialize.handler.UMAPIShareHandler.C0966a;
import com.umeng.socialize.utils.Log;

/* compiled from: UMAPIShareHandler */
class C0968a implements Runnable {
    final /* synthetic */ C0966a f3315a;
    final /* synthetic */ Bundle f3316b;
    final /* synthetic */ UMAPIShareHandler f3317c;

    C0968a(UMAPIShareHandler uMAPIShareHandler, C0966a c0966a, Bundle bundle) {
        this.f3317c = uMAPIShareHandler;
        this.f3315a = c0966a;
        this.f3316b = bundle;
    }

    public void run() {
        this.f3317c.sendShareRequest(this.f3317c.getResult(this.f3315a.f3313a, this.f3316b), this.f3315a.f3314b);
        Log.m3248d(SocialConstants.PARAM_ACT, "sent share request");
    }
}

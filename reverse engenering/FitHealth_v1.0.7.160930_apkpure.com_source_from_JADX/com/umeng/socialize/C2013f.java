package com.umeng.socialize;

import android.app.Activity;
import android.content.Context;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.QueuedWork.DialogThread;

/* compiled from: UMShareAPI */
class C2013f extends DialogThread<Void> {
    final /* synthetic */ Activity f5452a;
    final /* synthetic */ SHARE_MEDIA f5453b;
    final /* synthetic */ UMAuthListener f5454c;
    final /* synthetic */ UMShareAPI f5455d;

    C2013f(UMShareAPI uMShareAPI, Context context, Activity activity, SHARE_MEDIA share_media, UMAuthListener uMAuthListener) {
        this.f5455d = uMShareAPI;
        this.f5452a = activity;
        this.f5453b = share_media;
        this.f5454c = uMAuthListener;
        super(context);
    }

    protected Object doInBackground() {
        if (this.f5455d.f3233a != null) {
            this.f5455d.f3233a.m3189a(this.f5452a, this.f5453b, this.f5454c);
        }
        return null;
    }
}

package com.umeng.socialize;

import android.app.Activity;
import android.content.Context;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.QueuedWork.DialogThread;

/* compiled from: UMShareAPI */
class C2014g extends DialogThread<Void> {
    final /* synthetic */ Activity f5456a;
    final /* synthetic */ SHARE_MEDIA f5457b;
    final /* synthetic */ UMAuthListener f5458c;
    final /* synthetic */ UMShareAPI f5459d;

    C2014g(UMShareAPI uMShareAPI, Context context, Activity activity, SHARE_MEDIA share_media, UMAuthListener uMAuthListener) {
        this.f5459d = uMShareAPI;
        this.f5456a = activity;
        this.f5457b = share_media;
        this.f5458c = uMAuthListener;
        super(context);
    }

    protected Object doInBackground() {
        if (this.f5459d.f3233a != null) {
            this.f5459d.f3233a.m3193b(this.f5456a, this.f5457b, this.f5458c);
        }
        return null;
    }
}

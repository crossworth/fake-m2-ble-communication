package com.umeng.socialize;

import android.app.Activity;
import android.content.Context;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.QueuedWork.DialogThread;
import com.umeng.socialize.p025b.C0947a;

/* compiled from: UMShareAPI */
class C2012e extends DialogThread<Void> {
    final /* synthetic */ Activity f5448a;
    final /* synthetic */ SHARE_MEDIA f5449b;
    final /* synthetic */ UMAuthListener f5450c;
    final /* synthetic */ UMShareAPI f5451d;

    C2012e(UMShareAPI uMShareAPI, Context context, Activity activity, SHARE_MEDIA share_media, UMAuthListener uMAuthListener) {
        this.f5451d = uMShareAPI;
        this.f5448a = activity;
        this.f5449b = share_media;
        this.f5450c = uMAuthListener;
        super(context);
    }

    protected /* synthetic */ Object doInBackground() {
        return m6106a();
    }

    protected Void m6106a() {
        if (this.f5451d.f3233a != null) {
            this.f5451d.f3233a.m3195c(this.f5448a, this.f5449b, this.f5450c);
        } else {
            C0947a c0947a = new C0947a(this.f5448a);
            this.f5451d.f3233a.m3195c(this.f5448a, this.f5449b, this.f5450c);
        }
        return null;
    }
}

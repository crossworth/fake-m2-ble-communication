package com.umeng.socialize;

import android.app.Activity;
import android.content.Context;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.QueuedWork.DialogThread;
import com.umeng.socialize.p025b.C0947a;
import com.umeng.socialize.view.UMFriendListener;

/* compiled from: UMShareAPI */
class C2015h extends DialogThread<Void> {
    final /* synthetic */ Activity f5460a;
    final /* synthetic */ SHARE_MEDIA f5461b;
    final /* synthetic */ UMFriendListener f5462c;
    final /* synthetic */ UMShareAPI f5463d;

    C2015h(UMShareAPI uMShareAPI, Context context, Activity activity, SHARE_MEDIA share_media, UMFriendListener uMFriendListener) {
        this.f5463d = uMShareAPI;
        this.f5460a = activity;
        this.f5461b = share_media;
        this.f5462c = uMFriendListener;
        super(context);
    }

    protected Object doInBackground() {
        if (this.f5463d.f3233a != null) {
            this.f5463d.f3233a.m3190a(this.f5460a, this.f5461b, this.f5462c);
        } else {
            this.f5463d.f3233a = new C0947a(this.f5460a);
            this.f5463d.f3233a.m3190a(this.f5460a, this.f5461b, this.f5462c);
        }
        return null;
    }
}

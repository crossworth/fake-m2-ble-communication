package com.umeng.socialize;

import android.app.Activity;
import android.content.Context;
import com.umeng.socialize.common.QueuedWork.DialogThread;
import com.umeng.socialize.p025b.C0947a;

/* compiled from: UMShareAPI */
class C2016i extends DialogThread<Void> {
    final /* synthetic */ Activity f5464a;
    final /* synthetic */ ShareAction f5465b;
    final /* synthetic */ UMShareListener f5466c;
    final /* synthetic */ UMShareAPI f5467d;

    C2016i(UMShareAPI uMShareAPI, Context context, Activity activity, ShareAction shareAction, UMShareListener uMShareListener) {
        this.f5467d = uMShareAPI;
        this.f5464a = activity;
        this.f5465b = shareAction;
        this.f5466c = uMShareListener;
        super(context);
    }

    protected /* synthetic */ Object doInBackground() {
        return m6107a();
    }

    protected Void m6107a() {
        if (this.f5467d.f3233a != null) {
            this.f5467d.f3233a.m3188a(this.f5464a, this.f5465b, this.f5466c);
        } else {
            this.f5467d.f3233a = new C0947a(this.f5464a);
            this.f5467d.f3233a.m3188a(this.f5464a, this.f5465b, this.f5466c);
        }
        return null;
    }
}

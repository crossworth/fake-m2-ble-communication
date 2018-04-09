package com.umeng.socialize.handler;

import android.app.Activity;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.QueuedWork;
import java.util.Map;

/* compiled from: UMAPIShareHandler */
class C1817b implements UMAuthListener {
    final /* synthetic */ Activity f4830a;
    final /* synthetic */ ShareContent f4831b;
    final /* synthetic */ UMShareListener f4832c;
    final /* synthetic */ UMAPIShareHandler f4833d;

    C1817b(UMAPIShareHandler uMAPIShareHandler, Activity activity, ShareContent shareContent, UMShareListener uMShareListener) {
        this.f4833d = uMAPIShareHandler;
        this.f4830a = activity;
        this.f4831b = shareContent;
        this.f4832c = uMShareListener;
    }

    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
        QueuedWork.runInBack(new C0969c(this));
    }

    public void onError(SHARE_MEDIA share_media, int i, Throwable th) {
        this.f4832c.onError(share_media, th);
    }

    public void onCancel(SHARE_MEDIA share_media, int i) {
        this.f4832c.onCancel(share_media);
    }
}

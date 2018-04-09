package com.droi.sdk.push.p020b;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.droi.btlib.connection.MessageObj;
import com.droi.sdk.DroiError;
import com.droi.sdk.push.ag;
import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1015j;
import com.droi.sdk.utility.DroiDownloadFile.DroiDownloadFileEventListener;
import com.umeng.facebook.share.internal.ShareConstants;

class C0980b implements DroiDownloadFileEventListener {
    final /* synthetic */ C0979a f3251a;

    C0980b(C0979a c0979a) {
        this.f3251a = c0979a;
    }

    public void onFailed(String str, DroiError droiError) {
        C1012g.m3138a("download onFailed: " + str);
        if (droiError != null) {
            C1012g.m3138a("code : message>>> " + droiError.getCode() + ":" + droiError.getAppendedMessage());
        }
        C0981c c0981c = (C0981c) this.f3251a.f3245d.get(str);
        if (c0981c != null) {
            ag.m3007a(this.f3251a.f3243b, c0981c.f3252a, "m01", 8, 1, -1, "DROIPUSH");
            Intent intent = new Intent();
            String packageName = this.f3251a.f3243b.getPackageName();
            String str2 = c0981c.f3254c;
            if (!TextUtils.isEmpty(packageName)) {
                intent.setAction(packageName + ".Action.START");
                intent.putExtra("type", 4);
                intent.putExtra(ShareConstants.WEB_DIALOG_PARAM_ID, c0981c.f3253b);
                intent.putExtra(MessageObj.MSGID, c0981c.f3252a);
                Bundle bundle = new Bundle();
                bundle.putString("package", packageName + "|" + str2);
                bundle.putString("durl", str);
                intent.putExtra("dinfo", bundle);
            }
            this.f3251a.m3013a(str, -1, PendingIntent.getBroadcast(this.f3251a.f3243b, c0981c.f3253b, intent, 134217728));
        }
        this.f3251a.f3245d.remove(str);
    }

    public void onFinished(String str, String str2) {
        C1012g.m3138a("download success: " + str);
        C0981c c0981c = (C0981c) this.f3251a.f3245d.get(str);
        if (c0981c != null) {
            boolean b;
            this.f3251a.m3014a(c0981c.f3255d, c0981c.f3252a);
            if (c0981c.f3252a > 0) {
                ag.m3007a(this.f3251a.f3243b, c0981c.f3252a, "m01", 7, 1, -1, "DROIPUSH");
            }
            try {
                b = this.f3251a.m3017b(this.f3251a.f3246e + "/" + C1015j.m3153a(str), c0981c.f3252a);
            } catch (Exception e) {
                C1012g.m3139b(e);
                b = false;
            }
            if (b) {
                this.f3251a.m3013a(str, 100, null);
            } else {
                this.f3251a.m3013a(str, -1, PendingIntent.getBroadcast(this.f3251a.f3243b, c0981c.f3253b, new Intent(), 134217728));
            }
        }
        this.f3251a.f3245d.remove(str);
    }

    public void onProgress(String str, float f) {
        int i = (f <= 0.0f || f >= 1.0f) ? 0 : (int) (100.0f * f);
        this.f3251a.m3013a(str, i, null);
    }

    public void onStart(String str, long j) {
        C1012g.m3138a("download onStart:" + str);
        this.f3251a.m3013a(str, 0, null);
    }
}

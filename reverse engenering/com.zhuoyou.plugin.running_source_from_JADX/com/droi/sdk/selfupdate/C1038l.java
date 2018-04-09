package com.droi.sdk.selfupdate;

import com.droi.sdk.DroiError;
import com.droi.sdk.internal.DroiLog;
import com.droi.sdk.selfupdate.util.C1049c.C1036b;
import com.droi.sdk.selfupdate.util.C1051e;

class C1038l implements C1036b {
    final /* synthetic */ DroiInappDownloadListener f3442a;
    final /* synthetic */ DroiInappUpdateResponse f3443b;
    final /* synthetic */ C1032g f3444c;

    C1038l(C1032g c1032g, DroiInappDownloadListener droiInappDownloadListener, DroiInappUpdateResponse droiInappUpdateResponse) {
        this.f3444c = c1032g;
        this.f3442a = droiInappDownloadListener;
        this.f3443b = droiInappUpdateResponse;
    }

    public void mo1942a(String str, long j) {
        DroiLog.m2871i("DroiUpdateImpl", "downloadInappUpdateFile:onStart");
        if (this.f3442a != null) {
            this.f3442a.onStart(j);
        }
    }

    public void mo1941a(String str, float f) {
        DroiLog.m2871i("DroiUpdateImpl", "downloadInappUpdateFile:onProgress:" + f);
        if (this.f3442a != null) {
            this.f3442a.onProgress(f);
        }
    }

    public void mo1944a(String str, String str2) {
        DroiLog.m2871i("DroiUpdateImpl", "downloadInappUpdateFile:onFinished");
        if (this.f3442a == null) {
            return;
        }
        if (C1051e.m3297a(str2).equals(this.f3443b.getFileMd5())) {
            this.f3442a.onFinished(str2);
        } else {
            this.f3442a.onFailed(3);
        }
    }

    public void mo1943a(String str, DroiError droiError) {
        DroiLog.m2871i("DroiUpdateImpl", "downloadInappUpdateFile:onFailed:DOWNLOAD_FAILED");
        if (this.f3442a != null) {
            this.f3442a.onFailed(2);
        }
    }
}

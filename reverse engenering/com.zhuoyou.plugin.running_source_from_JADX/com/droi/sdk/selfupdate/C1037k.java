package com.droi.sdk.selfupdate;

import android.content.Context;
import com.droi.sdk.DroiError;
import com.droi.sdk.internal.DroiLog;
import com.droi.sdk.selfupdate.util.C1046a;
import com.droi.sdk.selfupdate.util.C1047b;
import com.droi.sdk.selfupdate.util.C1049c.C1036b;
import com.droi.sdk.selfupdate.util.C1051e;
import com.droi.sdk.selfupdate.util.PatchUtils;
import java.io.File;

class C1037k implements C1036b {
    final /* synthetic */ Context f3438a;
    final /* synthetic */ DroiUpdateResponse f3439b;
    final /* synthetic */ DroiDownloadListener f3440c;
    final /* synthetic */ C1032g f3441d;

    C1037k(C1032g c1032g, Context context, DroiUpdateResponse droiUpdateResponse, DroiDownloadListener droiDownloadListener) {
        this.f3441d = c1032g;
        this.f3438a = context;
        this.f3439b = droiUpdateResponse;
        this.f3440c = droiDownloadListener;
    }

    public void mo1944a(String str, String str2) {
        DroiLog.m2871i("DroiUpdateImpl", "startDownload:onFinished");
        try {
            File a = C1032g.m3205b(this.f3438a, this.f3439b.getNewMd5());
            C1041o.m3235a("m01", this.f3439b.m3193a(), C1041o.f3457h, System.currentTimeMillis());
            if (this.f3439b.isDeltaUpdate()) {
                if (this.f3440c != null) {
                    this.f3440c.onPatching();
                }
                int patch = PatchUtils.patch(C1046a.m3253a(this.f3438a), a.getAbsolutePath(), str2);
                this.f3441d.f3428f = false;
                if (patch != 0) {
                    C1047b.m3261a(this.f3438a, 1);
                    C1041o.m3235a("m01", this.f3439b.m3193a(), C1041o.f3460k, System.currentTimeMillis());
                    if (this.f3440c != null) {
                        this.f3440c.onFailed(1);
                        return;
                    }
                    return;
                } else if (this.f3439b.getNewMd5().equalsIgnoreCase(C1051e.m3296a(a))) {
                    C1041o.m3235a("m01", this.f3439b.m3193a(), C1041o.f3459j, System.currentTimeMillis());
                    if (this.f3440c != null) {
                        this.f3440c.onFinished(a);
                        return;
                    }
                    return;
                } else {
                    C1047b.m3261a(this.f3438a, 1);
                    C1041o.m3235a("m01", this.f3439b.m3193a(), C1041o.f3462m, System.currentTimeMillis());
                    if (this.f3440c != null) {
                        this.f3440c.onFailed(3);
                        return;
                    }
                    return;
                }
            }
            this.f3441d.f3428f = false;
            if (!this.f3439b.getNewMd5().equalsIgnoreCase(C1051e.m3296a(a))) {
                C1041o.m3235a("m01", this.f3439b.m3193a(), C1041o.f3462m, System.currentTimeMillis());
                if (this.f3440c != null) {
                    this.f3440c.onFailed(3);
                }
            } else if (this.f3440c != null) {
                this.f3440c.onFinished(a);
            }
        } catch (Exception e) {
            DroiLog.m2869e("DroiUpdateImpl", e);
            this.f3441d.f3428f = false;
            C1041o.m3235a("m01", this.f3439b.m3193a(), C1041o.f3461l, System.currentTimeMillis());
            if (this.f3440c != null) {
                this.f3440c.onFailed(0);
            }
        }
    }

    public void mo1942a(String str, long j) {
        DroiLog.m2871i("DroiUpdateImpl", "startDownload:onStart");
        if (this.f3440c != null) {
            this.f3440c.onStart(j);
        }
        this.f3441d.f3428f = true;
    }

    public void mo1943a(String str, DroiError droiError) {
        DroiLog.m2871i("DroiUpdateImpl", "startDownload:onFailed");
        this.f3441d.f3428f = false;
        C1041o.m3235a("m01", this.f3439b.m3193a(), C1041o.f3458i, System.currentTimeMillis());
        if (this.f3440c != null) {
            this.f3440c.onFailed(2);
        }
    }

    public void mo1941a(String str, float f) {
        DroiLog.m2871i("DroiUpdateImpl", "startDownload:onProgress:" + f);
        if (this.f3440c != null) {
            this.f3440c.onProgress(f);
        }
    }
}

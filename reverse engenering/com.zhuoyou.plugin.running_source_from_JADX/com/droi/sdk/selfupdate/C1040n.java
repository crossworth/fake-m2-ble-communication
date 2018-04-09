package com.droi.sdk.selfupdate;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.droi.sdk.internal.DroiLog;
import com.droi.sdk.selfupdate.util.C1050d;
import java.io.File;

class C1040n implements DroiDownloadListener {
    final /* synthetic */ Context f3446a;
    final /* synthetic */ NotificationManager f3447b;
    final /* synthetic */ DroiUpdateResponse f3448c;
    final /* synthetic */ NotificationClickReceiver f3449d;

    C1040n(NotificationClickReceiver notificationClickReceiver, Context context, NotificationManager notificationManager, DroiUpdateResponse droiUpdateResponse) {
        this.f3449d = notificationClickReceiver;
        this.f3446a = context;
        this.f3447b = notificationManager;
        this.f3448c = droiUpdateResponse;
    }

    public void onStart(long j) {
    }

    public void onPatching() {
        this.f3447b.notify("update", 0, C1044q.m3245a(this.f3446a).m3237a(100).m3239a(this.f3446a.getString(C1050d.m3292a(this.f3446a).m3295c("droi_patching"))).m3236a());
    }

    public void onProgress(float f) {
        this.f3447b.notify("update", 0, C1044q.m3245a(this.f3446a).m3237a((int) (f * 100.0f)).m3239a(((int) (100.0f * f)) + "%").m3236a());
    }

    public void onFinished(File file) {
        DroiLog.m2871i("BroadcastReceiver", "onFinished");
        C1044q.m3247b(this.f3446a, this.f3448c, true, file);
        DroiUpdate.installApp(this.f3446a, file, this.f3448c.getUpdateType());
    }

    public void onFailed(int i) {
        Intent intent = new Intent(this.f3446a, NotificationClickReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("response", this.f3448c);
        intent.putExtras(bundle);
        PendingIntent broadcast = PendingIntent.getBroadcast(this.f3446a, 0, intent, 134217728);
        if (i == 1 || i == 3) {
            this.f3447b.notify("update", 0, C1044q.m3245a(this.f3446a).m3238a(broadcast).m3239a(this.f3446a.getString(C1050d.m3292a(this.f3446a).m3295c("droi_patch_failed_redownload"))).m3236a());
        } else if (i == 2) {
            this.f3447b.notify("update", 0, C1044q.m3245a(this.f3446a).m3238a(broadcast).m3239a(this.f3446a.getString(C1050d.m3292a(this.f3446a).m3295c("droi_download_failed_redownload"))).m3236a());
        } else {
            this.f3447b.notify("update", 0, C1044q.m3245a(this.f3446a).m3238a(broadcast).m3239a(this.f3446a.getString(C1050d.m3292a(this.f3446a).m3295c("droi_download_failed_permission"))).m3236a());
        }
    }
}

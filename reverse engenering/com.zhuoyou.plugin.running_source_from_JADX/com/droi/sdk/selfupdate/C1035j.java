package com.droi.sdk.selfupdate;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.droi.sdk.selfupdate.C1044q.C1043a;
import com.droi.sdk.selfupdate.util.C1050d;
import java.io.File;

class C1035j implements DroiDownloadListener {
    final /* synthetic */ Context f3433a;
    final /* synthetic */ NotificationManager f3434b;
    final /* synthetic */ C1043a f3435c;
    final /* synthetic */ DroiUpdateResponse f3436d;
    final /* synthetic */ C1032g f3437e;

    C1035j(C1032g c1032g, Context context, NotificationManager notificationManager, C1043a c1043a, DroiUpdateResponse droiUpdateResponse) {
        this.f3437e = c1032g;
        this.f3433a = context;
        this.f3434b = notificationManager;
        this.f3435c = c1043a;
        this.f3436d = droiUpdateResponse;
    }

    public void onStart(long j) {
    }

    public void onPatching() {
        this.f3434b.notify("update", 0, this.f3435c.m3237a(100).m3239a(this.f3433a.getString(C1050d.m3292a(this.f3433a).m3295c("droi_patching"))).m3236a());
    }

    public void onProgress(float f) {
        this.f3434b.notify("update", 0, this.f3435c.m3237a((int) (f * 100.0f)).m3239a(((int) (100.0f * f)) + "%").m3236a());
    }

    public void onFinished(File file) {
        C1044q.m3247b(this.f3433a, this.f3436d, true, file);
        C1032g.m3203a(this.f3433a, file, this.f3436d.getUpdateType());
    }

    public void onFailed(int i) {
        Intent intent = new Intent(this.f3433a, NotificationClickReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("response", this.f3436d);
        intent.putExtras(bundle);
        PendingIntent broadcast = PendingIntent.getBroadcast(this.f3433a, 0, intent, 134217728);
        if (i == 1 || i == 3) {
            this.f3434b.notify("update", 0, C1044q.m3245a(this.f3433a).m3238a(broadcast).m3239a(this.f3433a.getString(C1050d.m3292a(this.f3433a).m3295c("droi_patch_failed_redownload"))).m3236a());
        } else if (i == 2) {
            this.f3434b.notify("update", 0, C1044q.m3245a(this.f3433a).m3238a(broadcast).m3239a(this.f3433a.getString(C1050d.m3292a(this.f3433a).m3295c("droi_download_failed_redownload"))).m3236a());
        } else {
            this.f3434b.notify("update", 0, C1044q.m3245a(this.f3433a).m3238a(broadcast).m3239a(this.f3433a.getString(C1050d.m3292a(this.f3433a).m3295c("droi_download_failed_permission"))).m3236a());
        }
    }
}

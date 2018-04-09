package com.droi.sdk.selfupdate;

import android.os.Handler;
import android.os.Message;
import com.droi.sdk.selfupdate.util.C1050d;

class C1025b extends Handler {
    final /* synthetic */ DroiUpdateDialogActivity f3409a;

    C1025b(DroiUpdateDialogActivity droiUpdateDialogActivity) {
        this.f3409a = droiUpdateDialogActivity;
    }

    public void handleMessage(Message message) {
        super.handleMessage(message);
        int i;
        switch (message.what) {
            case 0:
                i = message.arg1;
                this.f3409a.f3385a.setProgress(i);
                this.f3409a.f3386b.setText(this.f3409a.getString(C1050d.m3292a(this.f3409a.f3394l).m3295c("droi_dialog_downloading")) + i + " %");
                return;
            case 1:
                this.f3409a.f3385a.setProgress(100);
                this.f3409a.f3386b.setText(C1050d.m3292a(this.f3409a.f3394l).m3295c("droi_downloaded"));
                this.f3409a.f3387c.setVisibility(0);
                this.f3409a.f3387c.setText(C1050d.m3292a(this.f3409a.f3394l).m3295c("droi_insatll"));
                DroiUpdateDialogActivity.f3383d = true;
                return;
            case 2:
                i = message.arg1;
                if (i == 1) {
                    this.f3409a.f3386b.setText(C1050d.m3292a(this.f3409a.f3394l).m3295c("droi_patch_failed"));
                } else if (i == 2) {
                    this.f3409a.f3386b.setText(C1050d.m3292a(this.f3409a.f3394l).m3295c("droi_download_failed"));
                }
                this.f3409a.f3387c.setVisibility(0);
                this.f3409a.f3387c.setText(C1050d.m3292a(this.f3409a.f3394l).m3295c("droi_redownload"));
                DroiUpdateDialogActivity.f3383d = false;
                return;
            case 3:
                this.f3409a.f3386b.setText(C1050d.m3292a(this.f3409a.f3394l).m3295c("droi_patching"));
                return;
            default:
                return;
        }
    }
}

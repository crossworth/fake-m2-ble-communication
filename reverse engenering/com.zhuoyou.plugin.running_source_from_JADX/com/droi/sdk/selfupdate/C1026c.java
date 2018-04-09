package com.droi.sdk.selfupdate;

import android.view.View;
import android.view.View.OnClickListener;
import com.droi.sdk.internal.DroiLog;

class C1026c implements OnClickListener {
    final /* synthetic */ int f3410a;
    final /* synthetic */ View f3411b;
    final /* synthetic */ View f3412c;
    final /* synthetic */ DroiUpdateDialogActivity f3413d;

    C1026c(DroiUpdateDialogActivity droiUpdateDialogActivity, int i, View view, View view2) {
        this.f3413d = droiUpdateDialogActivity;
        this.f3410a = i;
        this.f3411b = view;
        this.f3412c = view2;
    }

    public void onClick(View view) {
        DroiLog.m2871i(DroiUpdateDialogActivity.f3384f, "onClick");
        if (this.f3413d.f3390h.getUpdateType() == 2 && this.f3410a == view.getId()) {
            DroiLog.m2871i(DroiUpdateDialogActivity.f3384f, "FORCE");
            DroiDownloadListener c1027d = new C1027d(this);
            this.f3413d.f3387c.setOnClickListener(new C1028e(this, c1027d));
            if (this.f3413d.f3391i == null) {
                this.f3411b.setVisibility(8);
                this.f3412c.setVisibility(0);
                DroiUpdate.downloadApp(this.f3413d.f3394l, this.f3413d.f3390h, c1027d);
                return;
            }
            DroiUpdate.installApp(this.f3413d.f3394l, this.f3413d.f3391i, 2);
            return;
        }
        DroiLog.m2871i(DroiUpdateDialogActivity.f3384f, "NOT FORCE OK");
        if (this.f3410a == view.getId()) {
            this.f3413d.f3389g = 1;
        } else if (this.f3413d.f3392j) {
            this.f3413d.f3389g = 3;
        }
        this.f3413d.finish();
    }
}

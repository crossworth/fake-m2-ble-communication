package com.droi.sdk.selfupdate;

import android.view.View;
import android.view.View.OnClickListener;

class C1028e implements OnClickListener {
    final /* synthetic */ DroiDownloadListener f3415a;
    final /* synthetic */ C1026c f3416b;

    C1028e(C1026c c1026c, DroiDownloadListener droiDownloadListener) {
        this.f3416b = c1026c;
        this.f3415a = droiDownloadListener;
    }

    public void onClick(View view) {
        if (DroiUpdateDialogActivity.f3383d) {
            DroiUpdate.installApp(this.f3416b.f3413d.f3394l, this.f3416b.f3413d.f3391i, 2);
            return;
        }
        DroiUpdate.downloadApp(this.f3416b.f3413d.f3394l, this.f3416b.f3413d.f3390h, this.f3415a);
        this.f3416b.f3413d.f3387c.setVisibility(8);
    }
}

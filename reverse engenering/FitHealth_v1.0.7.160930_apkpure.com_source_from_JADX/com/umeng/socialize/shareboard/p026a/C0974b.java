package com.umeng.socialize.shareboard.p026a;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.DeviceConfig;

/* compiled from: SNSPlatformAdapter */
class C0974b implements OnClickListener {
    final /* synthetic */ SnsPlatform f3332a;
    final /* synthetic */ C1827a f3333b;

    C0974b(C1827a c1827a, SnsPlatform snsPlatform) {
        this.f3333b = c1827a;
        this.f3332a = snsPlatform;
    }

    public void onClick(View view) {
        this.f3333b.f4845c.dismiss();
        SHARE_MEDIA share_media = this.f3332a.mPlatform;
        if (DeviceConfig.isNetworkAvailable(this.f3333b.f4844b) || share_media == SHARE_MEDIA.SMS) {
            this.f3333b.m5001a(this.f3332a, share_media);
        } else {
            Toast.makeText(this.f3333b.f4844b, "您的网络不可用,请检查网络连接...", 0).show();
        }
    }
}

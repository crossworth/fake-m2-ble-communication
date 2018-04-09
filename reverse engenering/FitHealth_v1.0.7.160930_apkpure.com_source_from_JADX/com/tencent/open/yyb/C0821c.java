package com.tencent.open.yyb;

import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;
import com.tencent.connect.auth.QQToken;
import com.tencent.p004a.p005a.C1711d;

/* compiled from: ProGuard */
class C0821c implements DownloadListener {
    final /* synthetic */ AppbarActivity f2761a;

    C0821c(AppbarActivity appbarActivity) {
        this.f2761a = appbarActivity;
    }

    public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
        C1711d.m4638b("openSDK_LOG", "-->(AppbarActivity)onDownloadStart : url = " + str);
        try {
            this.f2761a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (Exception e) {
            C1711d.m4638b("openSDK_LOG", "-->(AppbarActivity)onDownloadStart : activity aciton_view not found.");
        }
        QQToken access$500 = this.f2761a.getToken();
        if (access$500 != null) {
            C0820b.m2605a(access$500.getAppId(), "200", "SDK.APPBAR.HOME ACTION");
        }
    }
}

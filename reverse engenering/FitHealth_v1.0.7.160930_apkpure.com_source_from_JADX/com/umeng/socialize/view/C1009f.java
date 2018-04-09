package com.umeng.socialize.view;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

/* compiled from: OauthDialog */
class C1009f extends WebChromeClient {
    final /* synthetic */ OauthDialog f3484a;

    C1009f(OauthDialog oauthDialog) {
        this.f3484a = oauthDialog;
    }

    public void onProgressChanged(WebView webView, int i) {
        super.onProgressChanged(webView, i);
        if (i < 90) {
            this.f3484a.f3369e.setVisibility(0);
        } else {
            this.f3484a.f3379o.sendEmptyMessage(1);
        }
    }
}

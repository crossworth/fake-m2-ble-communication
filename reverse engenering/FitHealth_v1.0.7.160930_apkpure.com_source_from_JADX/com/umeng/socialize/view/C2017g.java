package com.umeng.socialize.view;

import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import com.umeng.socialize.view.OauthDialog.C1828b;

/* compiled from: OauthDialog */
class C2017g extends C1828b {
    final /* synthetic */ OauthDialog f5544a;

    C2017g(OauthDialog oauthDialog) {
        this.f5544a = oauthDialog;
        super();
    }

    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        sslErrorHandler.cancel();
    }
}

package com.umeng.socialize.handler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import oauth.signpost.OAuth;

public class TwitterWebActivity extends Activity {
    private WebView wv;

    private class MyWebViewClient extends WebViewClient {
        private MyWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (url != null && url.startsWith("oauth://t4jsample")) {
                String oauthVerifer = Uri.parse(url).getQueryParameter(OAuth.OAUTH_VERIFIER);
                Intent i = new Intent();
                i.putExtra(OAuth.OAUTH_VERIFIER, oauthVerifer);
                TwitterWebActivity.this.setResult(0, i);
                TwitterWebActivity.this.finish();
            }
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.cancel();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.wv = new WebView(this);
        this.wv.setWebViewClient(new MyWebViewClient());
        if (getIntent() != null && getIntent().getStringExtra("auth_url") != null) {
            this.wv.loadUrl(getIntent().getStringExtra("auth_url"));
            setContentView(this.wv);
        }
    }
}

package com.weibo.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;

public class WeiboWebView {
    static final int MARGIN = 4;
    static final int PADDING = 2;
    private static final String TAG = "Weibo-WebView";
    private Context mContext;
    private WeiboDialogListener mListener;
    private ProgressDialog mSpinner;
    private String mUrl;
    private WebView mWebView;
    private final Weibo mWeibo;

    private class WeiboWebViewClient extends WebViewClient {
        private WeiboWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(WeiboWebView.TAG, "Redirect URL: " + url);
            if (url.startsWith(WeiboWebView.this.mWeibo.getRedirectUrl())) {
                WeiboWebView.this.handleRedirectUrl(view, url);
            } else {
                WeiboWebView.this.mContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
            }
            return true;
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            WeiboWebView.this.mListener.onError(new DialogError(description, errorCode, failingUrl));
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d(WeiboWebView.TAG, "onPageStarted URL: " + url);
            if (url.startsWith(WeiboWebView.this.mWeibo.getRedirectUrl())) {
                WeiboWebView.this.handleRedirectUrl(view, url);
                view.stopLoading();
                return;
            }
            super.onPageStarted(view, url, favicon);
            WeiboWebView.this.mSpinner.show();
        }

        public void onPageFinished(WebView view, String url) {
            Log.d(WeiboWebView.TAG, "onPageFinished URL: " + url);
            super.onPageFinished(view, url);
            WeiboWebView.this.mSpinner.dismiss();
        }

        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }

    public WeiboWebView(Weibo weibo, WebView webView, Context context, String url, WeiboDialogListener listener) {
        this.mWeibo = weibo;
        this.mUrl = url;
        this.mListener = listener;
        this.mContext = context;
        this.mWebView = webView;
        initUI();
    }

    public void initUI() {
        this.mSpinner = new ProgressDialog(this.mContext);
        this.mSpinner.requestWindowFeature(1);
        this.mSpinner.setMessage("正在加载，请稍候...");
        setUpWebView();
    }

    private void setUpWebView() {
        this.mWebView.setVerticalScrollBarEnabled(false);
        this.mWebView.setHorizontalScrollBarEnabled(false);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.setWebViewClient(new WeiboWebViewClient());
        this.mWebView.loadUrl(this.mUrl);
    }

    private void handleRedirectUrl(WebView view, String url) {
        Bundle values = Utility.parseUrl(url);
        String error = values.getString("error");
        String error_code = values.getString("error_code");
        if (error == null && error_code == null) {
            this.mListener.onComplete(values);
        } else if (error.equals("access_denied")) {
            this.mListener.onCancel();
        } else {
            this.mListener.onWeiboException(new WeiboException(error, Integer.parseInt(error_code)));
        }
    }

    private static String getHtml(String urlString) {
        try {
            StringBuffer html = new StringBuffer();
            InputStreamReader isr = new InputStreamReader(((HttpURLConnection) new URL(urlString).openConnection(new Proxy(Type.HTTP, new InetSocketAddress("10.75.0.103", 8093)))).getInputStream());
            BufferedReader br = new BufferedReader(isr);
            while (true) {
                String temp = br.readLine();
                if (temp == null) {
                    br.close();
                    isr.close();
                    return html.toString();
                }
                html.append(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

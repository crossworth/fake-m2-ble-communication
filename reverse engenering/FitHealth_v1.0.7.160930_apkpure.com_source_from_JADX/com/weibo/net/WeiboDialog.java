package com.weibo.net;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.weibo.android.R;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;

public class WeiboDialog extends Dialog {
    static final LayoutParams FILL = new LayoutParams(-1, -1);
    static final int MARGIN = 4;
    static final int PADDING = 2;
    private static final String TAG = "Weibo-WebView";
    private ImageView mBtnClose;
    private RelativeLayout mContent;
    private WeiboDialogListener mListener;
    private ProgressDialog mSpinner;
    private String mUrl;
    private WebView mWebView;
    private final Weibo mWeibo;
    private RelativeLayout webViewContainer;

    class C10181 implements OnClickListener {
        C10181() {
        }

        public void onClick(View v) {
            WeiboDialog.this.mListener.onCancel();
            WeiboDialog.this.dismiss();
        }
    }

    private class WeiboWebViewClient extends WebViewClient {
        private WeiboWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(WeiboDialog.TAG, "Redirect URL: " + url);
            if (url.startsWith(WeiboDialog.this.mWeibo.getRedirectUrl())) {
                WeiboDialog.this.handleRedirectUrl(view, url);
                WeiboDialog.this.dismiss();
            } else {
                WeiboDialog.this.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
            }
            return true;
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            WeiboDialog.this.mListener.onError(new DialogError(description, errorCode, failingUrl));
            WeiboDialog.this.dismiss();
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d(WeiboDialog.TAG, "onPageStarted URL: " + url);
            if (url.startsWith(WeiboDialog.this.mWeibo.getRedirectUrl())) {
                WeiboDialog.this.handleRedirectUrl(view, url);
                view.stopLoading();
                WeiboDialog.this.dismiss();
                return;
            }
            super.onPageStarted(view, url, favicon);
            WeiboDialog.this.mSpinner.show();
        }

        public void onPageFinished(WebView view, String url) {
            Log.d(WeiboDialog.TAG, "onPageFinished URL: " + url);
            super.onPageFinished(view, url);
            WeiboDialog.this.mSpinner.dismiss();
            WeiboDialog.this.mContent.setBackgroundColor(0);
            WeiboDialog.this.webViewContainer.setBackgroundResource(R.drawable.dialog_bg);
            WeiboDialog.this.mWebView.setVisibility(0);
        }

        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }

    public WeiboDialog(Weibo weibo, Context context, String url, WeiboDialogListener listener) {
        super(context, R.style.ContentOverlay);
        this.mWeibo = weibo;
        this.mUrl = url;
        this.mListener = listener;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSpinner = new ProgressDialog(getContext());
        this.mSpinner.requestWindowFeature(1);
        this.mSpinner.setMessage("Loading...");
        requestWindowFeature(1);
        this.mContent = new RelativeLayout(getContext());
        setUpWebView();
        addContentView(this.mContent, new ViewGroup.LayoutParams(-1, -1));
    }

    private void setUpWebView() {
        this.webViewContainer = new RelativeLayout(getContext());
        this.mWebView = new WebView(getContext());
        this.mWebView.setVerticalScrollBarEnabled(false);
        this.mWebView.setHorizontalScrollBarEnabled(false);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.setWebViewClient(new WeiboWebViewClient());
        this.mWebView.loadUrl(this.mUrl);
        this.mWebView.setLayoutParams(FILL);
        this.mWebView.setVisibility(4);
        this.webViewContainer.addView(this.mWebView);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-1, -1);
        Resources resources = getContext().getResources();
        lp.leftMargin = resources.getDimensionPixelSize(R.dimen.dialog_left_margin);
        lp.topMargin = resources.getDimensionPixelSize(R.dimen.dialog_top_margin);
        lp.rightMargin = resources.getDimensionPixelSize(R.dimen.dialog_right_margin);
        lp.bottomMargin = resources.getDimensionPixelSize(R.dimen.dialog_bottom_margin);
        this.mContent.addView(this.webViewContainer, lp);
    }

    private void setUpCloseBtn() {
        this.mBtnClose = new ImageView(getContext());
        this.mBtnClose.setClickable(true);
        this.mBtnClose.setOnClickListener(new C10181());
        this.mBtnClose.setImageResource(R.drawable.close_selector);
        this.mBtnClose.setVisibility(4);
        RelativeLayout.LayoutParams closeBtnRL = new RelativeLayout.LayoutParams(-2, -2);
        closeBtnRL.addRule(11);
        closeBtnRL.addRule(10);
        closeBtnRL.topMargin = getContext().getResources().getDimensionPixelSize(R.dimen.dialog_btn_close_right_margin);
        closeBtnRL.rightMargin = getContext().getResources().getDimensionPixelSize(R.dimen.dialog_btn_close_top_margin);
        this.webViewContainer.addView(this.mBtnClose, closeBtnRL);
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

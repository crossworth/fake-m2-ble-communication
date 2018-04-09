package com.sina.weibo.sdk.auth;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.WebDialog;
import com.sina.weibo.sdk.exception.WeiboAuthException;
import com.sina.weibo.sdk.exception.WeiboDialogException;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.utils.NetworkHelper;
import com.sina.weibo.sdk.utils.ResourceManager;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.open.yyb.TitleBar;

public class WeiboDialog extends Dialog {
    private static final String TAG = "WeiboDialog";
    private static final int WEBVIEW_CONTAINER_MARGIN_TOP = 25;
    private static final int WEBVIEW_MARGIN = 10;
    private static int theme = WebDialog.DEFAULT_THEME;
    private String mAuthUrl;
    private Context mContext;
    private boolean mIsDetached = false;
    private WeiboAuthListener mListener;
    private ProgressDialog mLoadingDlg;
    private RelativeLayout mRootContainer;
    private WebView mWebView;
    private RelativeLayout mWebViewContainer;
    private WeiboAuth mWeibo;

    class C06621 implements OnClickListener {
        C06621() {
        }

        public void onClick(View v) {
            WeiboDialog.this.dismiss();
            if (WeiboDialog.this.mListener != null) {
                WeiboDialog.this.mListener.onCancel();
            }
        }
    }

    private class WeiboWebViewClient extends WebViewClient {
        private boolean isCallBacked;

        private WeiboWebViewClient() {
            this.isCallBacked = false;
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtil.m2216i(WeiboDialog.TAG, "load URL: " + url);
            if (!url.startsWith("sms:")) {
                return super.shouldOverrideUrlLoading(view, url);
            }
            Intent sendIntent = new Intent("android.intent.action.VIEW");
            sendIntent.putExtra("address", url.replace("sms:", ""));
            sendIntent.setType("vnd.android-dir/mms-sms");
            WeiboDialog.this.getContext().startActivity(sendIntent);
            return true;
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            LogUtil.m2214d(WeiboDialog.TAG, "onReceivedError: errorCode = " + errorCode + ", description = " + description + ", failingUrl = " + failingUrl);
            super.onReceivedError(view, errorCode, description, failingUrl);
            if (WeiboDialog.this.mListener != null) {
                WeiboDialog.this.mListener.onWeiboException(new WeiboDialogException(description, errorCode, failingUrl));
            }
            WeiboDialog.this.dismiss();
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            LogUtil.m2214d(WeiboDialog.TAG, "onPageStarted URL: " + url);
            if (!url.startsWith(WeiboDialog.this.mWeibo.getAuthInfo().getRedirectUrl()) || this.isCallBacked) {
                super.onPageStarted(view, url, favicon);
                if (!WeiboDialog.this.mIsDetached && WeiboDialog.this.mLoadingDlg != null && !WeiboDialog.this.mLoadingDlg.isShowing()) {
                    WeiboDialog.this.mLoadingDlg.show();
                    return;
                }
                return;
            }
            this.isCallBacked = true;
            WeiboDialog.this.handleRedirectUrl(url);
            view.stopLoading();
            WeiboDialog.this.dismiss();
        }

        public void onPageFinished(WebView view, String url) {
            LogUtil.m2214d(WeiboDialog.TAG, "onPageFinished URL: " + url);
            super.onPageFinished(view, url);
            if (!(WeiboDialog.this.mIsDetached || WeiboDialog.this.mLoadingDlg == null)) {
                WeiboDialog.this.mLoadingDlg.dismiss();
            }
            if (WeiboDialog.this.mWebView != null) {
                WeiboDialog.this.mWebView.setVisibility(0);
            }
        }
    }

    public WeiboDialog(Context context, String authUrl, WeiboAuthListener listener, WeiboAuth weibo) {
        super(context, theme);
        this.mAuthUrl = authUrl;
        this.mListener = listener;
        this.mContext = context;
        this.mWeibo = weibo;
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (this.mListener != null) {
            this.mListener.onCancel();
        }
    }

    public void dismiss() {
        if (!this.mIsDetached) {
            if (this.mLoadingDlg != null && this.mLoadingDlg.isShowing()) {
                this.mLoadingDlg.dismiss();
            }
            super.dismiss();
        }
    }

    public void onAttachedToWindow() {
        this.mIsDetached = false;
        super.onAttachedToWindow();
    }

    public void onDetachedFromWindow() {
        if (this.mWebView != null) {
            this.mWebViewContainer.removeView(this.mWebView);
            this.mWebView.stopLoading();
            this.mWebView.removeAllViews();
            this.mWebView.destroy();
            this.mWebView = null;
        }
        this.mIsDetached = true;
        super.onDetachedFromWindow();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        initLoadingDlg();
        initWebView();
        initCloseButton();
    }

    private void initWindow() {
        requestWindowFeature(1);
        getWindow().setFeatureDrawableAlpha(0, 0);
        getWindow().setSoftInputMode(16);
        this.mRootContainer = new RelativeLayout(getContext());
        this.mRootContainer.setBackgroundColor(0);
        addContentView(this.mRootContainer, new LayoutParams(-1, -1));
    }

    private void initLoadingDlg() {
        this.mLoadingDlg = new ProgressDialog(getContext());
        this.mLoadingDlg.requestWindowFeature(1);
        this.mLoadingDlg.setMessage(ResourceManager.getString(this.mContext, 1));
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void initWebView() {
        this.mWebViewContainer = new RelativeLayout(getContext());
        this.mWebView = new WebView(getContext());
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.getSettings().setSavePassword(false);
        this.mWebView.setWebViewClient(new WeiboWebViewClient());
        this.mWebView.requestFocus();
        this.mWebView.setScrollBarStyle(0);
        this.mWebView.setVisibility(4);
        NetworkHelper.clearCookies(this.mContext, this.mAuthUrl);
        this.mWebView.loadUrl(this.mAuthUrl);
        RelativeLayout.LayoutParams webViewContainerLayout = new RelativeLayout.LayoutParams(-1, -1);
        RelativeLayout.LayoutParams webviewLayout = new RelativeLayout.LayoutParams(-1, -1);
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int margin = (int) (TitleBar.SHAREBTN_RIGHT_MARGIN * dm.density);
        webviewLayout.setMargins(margin, margin, margin, margin);
        this.mWebViewContainer.setBackgroundDrawable(ResourceManager.getNinePatchDrawable(this.mContext, 1));
        this.mWebViewContainer.addView(this.mWebView, webviewLayout);
        this.mWebViewContainer.setGravity(17);
        int width = (ResourceManager.getDrawable(this.mContext, 2).getIntrinsicWidth() / 2) + 1;
        webViewContainerLayout.setMargins(width, (int) (25.0f * dm.density), width, width);
        this.mRootContainer.addView(this.mWebViewContainer, webViewContainerLayout);
    }

    private void initCloseButton() {
        ImageView closeImage = new ImageView(this.mContext);
        Drawable drawable = ResourceManager.getDrawable(this.mContext, 2);
        closeImage.setImageDrawable(drawable);
        closeImage.setOnClickListener(new C06621());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) this.mWebViewContainer.getLayoutParams();
        layoutParams.leftMargin = (params.leftMargin - (drawable.getIntrinsicWidth() / 2)) + 5;
        layoutParams.topMargin = (params.topMargin - (drawable.getIntrinsicHeight() / 2)) + 5;
        this.mRootContainer.addView(closeImage, layoutParams);
    }

    private void handleRedirectUrl(String url) {
        Bundle values = Utility.parseUrl(url);
        String errorType = values.getString("error");
        String errorCode = values.getString("error_code");
        String errorDescription = values.getString(NativeProtocol.BRIDGE_ARG_ERROR_DESCRIPTION);
        if (errorType == null && errorCode == null) {
            this.mListener.onComplete(values);
        } else {
            this.mListener.onWeiboException(new WeiboAuthException(errorCode, errorType, errorDescription));
        }
    }
}

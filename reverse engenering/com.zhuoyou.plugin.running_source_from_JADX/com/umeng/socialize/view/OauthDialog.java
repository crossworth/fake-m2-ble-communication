package com.umeng.socialize.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.connect.common.Constants;
import com.umeng.socialize.Config;
import com.umeng.socialize.SocializeException;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.net.base.SocializeClient;
import com.umeng.socialize.net.utils.AesHelper;
import com.umeng.socialize.utils.DeviceConfig;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.utils.URLBuilder;
import java.lang.reflect.Method;

public class OauthDialog extends Dialog {
    private static final String TAG = "OauthDialog";
    private final ResContainer f5006R;
    private Activity mActivity;
    private View mContent;
    private Context mContext;
    private int mFlag = 0;
    private Handler mHandler = new C16701();
    private AuthListenerWrapper mListener;
    private SHARE_MEDIA mPlatform;
    private View mProgressbar;
    private Bundle mValues;
    private String mWaitUrl = "error";
    private WebView mWebView;

    class C16701 extends Handler {
        C16701() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1 && OauthDialog.this.mProgressbar != null) {
                OauthDialog.this.mProgressbar.setVisibility(8);
            }
            if (msg.what != 2) {
            }
        }
    }

    class C16712 implements OnClickListener {
        C16712() {
        }

        public void onClick(View v) {
            OauthDialog.this.dismiss();
        }
    }

    class C16754 extends WebChromeClient {
        C16754() {
        }

        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress < 90) {
                OauthDialog.this.mProgressbar.setVisibility(0);
            } else {
                OauthDialog.this.mHandler.sendEmptyMessage(1);
            }
        }
    }

    private class AuthWebViewClient extends WebViewClient {
        private AuthWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.m4552i(OauthDialog.TAG, "shouldOverrideUrlLoading current : " + url);
            if (DeviceConfig.isNetworkAvailable(OauthDialog.this.mContext)) {
                if (url.contains("?ud_get=")) {
                    url = OauthDialog.this.decrypt(url);
                }
                if (url.contains(OauthDialog.this.mWaitUrl)) {
                    doWaitUrl(url);
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
            Toast.makeText(OauthDialog.this.mContext, "抱歉,您的网络不可用...", 0).show();
            return true;
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.m4549e(OauthDialog.TAG, "onReceivedError: " + failingUrl + "\nerrCode: " + errorCode + " description:" + description);
            if (OauthDialog.this.mProgressbar.getVisibility() == 0) {
                OauthDialog.this.mProgressbar.setVisibility(8);
            }
            super.onReceivedError(view, errorCode, description, failingUrl);
            SocializeUtils.safeCloseDialog(OauthDialog.this);
        }

        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.cancel();
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (url.contains("?ud_get=")) {
                url = OauthDialog.this.decrypt(url);
            }
            if (!url.contains("access_key") || !url.contains("access_secret")) {
                super.onPageStarted(view, url, favicon);
            } else if (url.contains(OauthDialog.this.mWaitUrl)) {
                doWaitUrl(url);
            }
        }

        public void onPageFinished(WebView view, String url) {
            OauthDialog.this.mHandler.sendEmptyMessage(1);
            super.onPageFinished(view, url);
            if (OauthDialog.this.mFlag == 0 && url.contains(OauthDialog.this.mWaitUrl)) {
                doWaitUrl(url);
            }
        }

        private void doWaitUrl(String url) {
            Log.m4546d(OauthDialog.TAG, "OauthDialog " + url);
            OauthDialog.this.mFlag = 1;
            OauthDialog.this.mValues = SocializeUtils.parseUrl(url);
            if (OauthDialog.this.isShowing()) {
                SocializeUtils.safeCloseDialog(OauthDialog.this);
            }
        }
    }

    class C16765 extends AuthWebViewClient {
        C16765() {
            super();
        }

        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.cancel();
        }
    }

    static class AuthListenerWrapper {
        private int action;
        private UMAuthListener mListener = null;
        private SHARE_MEDIA f5005p;

        public AuthListenerWrapper(UMAuthListener listener, SHARE_MEDIA p) {
            this.mListener = listener;
            this.f5005p = p;
        }

        public void onError(Exception e) {
            if (this.mListener != null) {
                this.mListener.onError(this.f5005p, this.action, e);
            }
        }

        public void onComplete(Bundle bundle) {
            if (this.mListener != null) {
                this.mListener.onComplete(this.f5005p, this.action, SocializeUtils.bundleTomap(bundle));
            }
        }

        public void onCancel() {
            if (this.mListener != null) {
                this.mListener.onCancel(this.f5005p, this.action);
            }
        }
    }

    public OauthDialog(Activity activity, SHARE_MEDIA platform, UMAuthListener listener) {
        int animRes;
        super(activity, ResContainer.get(activity).style("umeng_socialize_popup_dialog"));
        this.mContext = activity.getApplicationContext();
        this.f5006R = ResContainer.get(this.mContext);
        this.mActivity = activity;
        this.mPlatform = platform;
        this.mListener = new AuthListenerWrapper(listener, platform);
        setOwnerActivity(activity);
        LayoutInflater inflater = (LayoutInflater) this.mActivity.getSystemService("layout_inflater");
        int layoutId = this.f5006R.layout("umeng_socialize_oauth_dialog");
        int followId = this.f5006R.id("umeng_socialize_follow");
        int checkId = this.f5006R.id("umeng_socialize_follow_check");
        this.mContent = inflater.inflate(layoutId, null);
        final View cuArea = this.mContent.findViewById(followId);
        if (platform == SHARE_MEDIA.SINA || platform == SHARE_MEDIA.TENCENT) {
        }
        cuArea.setVisibility(8);
        int progressbarId = this.f5006R.id("progress_bar_parent");
        int barleftfbtnId = this.f5006R.id("umeng_socialize_title_bar_leftBt");
        int barrightbtnId = this.f5006R.id("umeng_socialize_title_bar_rightBt");
        int barmiddletxtId = this.f5006R.id("umeng_socialize_title_bar_middleTv");
        int barId = this.f5006R.id("umeng_socialize_titlebar");
        this.mProgressbar = this.mContent.findViewById(progressbarId);
        this.mProgressbar.setVisibility(0);
        ((Button) this.mContent.findViewById(barleftfbtnId)).setOnClickListener(new C16712());
        this.mContent.findViewById(barrightbtnId).setVisibility(8);
        TextView titleMidTv = (TextView) this.mContent.findViewById(barmiddletxtId);
        String name = null;
        if (platform.toString().equals("SINA")) {
            name = "微博";
        } else if (platform.toString().equals("RENREN")) {
            name = "人人网";
        } else if (platform.toString().equals("DOUBAN")) {
            name = "豆瓣";
        } else if (platform.toString().equals("TENCENT")) {
            name = "腾讯微博";
        }
        titleMidTv.setText("授权" + name);
        setUpWebView();
        final View titleBar = this.mContent.findViewById(barId);
        final int minHeight = SocializeUtils.dip2Px(this.mContext, 200.0f);
        FrameLayout checkView = new FrameLayout(this.mContext) {
            protected void onSizeChanged(int w, int h, int oldw, int oldh) {
                super.onSizeChanged(w, h, oldw, oldh);
                if (!SocializeUtils.isFloatWindowStyle(OauthDialog.this.mContext)) {
                    notifyTitleChanged(cuArea, titleBar, minHeight, h);
                }
            }

            private void notifyTitleChanged(final View cuArea, final View titleBar, int minHeight, int h) {
                if (titleBar.getVisibility() == 0 && h < minHeight) {
                    OauthDialog.this.mHandler.post(new Runnable() {
                        public void run() {
                            titleBar.setVisibility(8);
                            if (cuArea.getVisibility() == 0) {
                                cuArea.setVisibility(8);
                            }
                            C16743.this.requestLayout();
                        }
                    });
                } else if (titleBar.getVisibility() != 0 && h >= minHeight) {
                    OauthDialog.this.mHandler.post(new Runnable() {
                        public void run() {
                            titleBar.setVisibility(0);
                            C16743.this.requestLayout();
                        }
                    });
                }
            }
        };
        checkView.addView(this.mContent, -1, -1);
        setContentView(checkView);
        LayoutParams lp = getWindow().getAttributes();
        if (SocializeUtils.isFloatWindowStyle(this.mContext)) {
            int[] windowSize = SocializeUtils.getFloatWindowSize(this.mContext);
            lp.width = windowSize[0];
            lp.height = windowSize[1];
            animRes = this.f5006R.style("umeng_socialize_dialog_anim_fade");
        } else {
            lp.height = -1;
            lp.width = -1;
            animRes = this.f5006R.style("umeng_socialize_dialog_animations");
        }
        lp.gravity = 17;
        getWindow().getAttributes().windowAnimations = animRes;
    }

    public void setWaitUrl(String url) {
        this.mWaitUrl = url;
    }

    private String getUrl(SHARE_MEDIA media) {
        URLBuilder builder = new URLBuilder(this.mContext);
        builder.setHost(SocializeClient.BASE_URL).setPath("share/auth/").setAppkey(SocializeUtils.getAppkey(this.mContext)).setEntityKey(Config.EntityKey).withMedia(media).withOpId(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ).withSessionId(Config.SessionId).withUID(Config.UID);
        return builder.toEncript();
    }

    private boolean setUpWebView() {
        this.mWebView = (WebView) this.mContent.findViewById(this.f5006R.id("webView"));
        this.mWebView.setWebViewClient(getAdapterWebViewClient());
        this.mWebView.setWebChromeClient(new C16754());
        this.mWebView.requestFocusFromTouch();
        this.mWebView.setVerticalScrollBarEnabled(false);
        this.mWebView.setHorizontalScrollBarEnabled(false);
        this.mWebView.setScrollBarStyle(0);
        this.mWebView.getSettings().setCacheMode(2);
        WebSettings settings = this.mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        if (VERSION.SDK_INT >= 8) {
            settings.setPluginState(PluginState.ON);
        }
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setAllowFileAccess(true);
        settings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
        settings.setUseWideViewPort(true);
        if (VERSION.SDK_INT >= 8) {
            settings.setLoadWithOverviewMode(true);
            settings.setDatabaseEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setGeolocationEnabled(true);
            settings.setAppCacheEnabled(true);
        }
        if (VERSION.SDK_INT >= 11) {
            try {
                Method method = WebSettings.class.getDeclaredMethod("setDisplayZoomControls", new Class[]{Boolean.TYPE});
                method.setAccessible(true);
                method.invoke(settings, new Object[]{Boolean.valueOf(false)});
            } catch (Exception e) {
            }
        }
        try {
            if (this.mPlatform == SHARE_MEDIA.RENREN) {
                CookieSyncManager.createInstance(this.mContext);
                CookieManager.getInstance().removeAllCookie();
            }
        } catch (Exception e2) {
        }
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    private WebViewClient getAdapterWebViewClient() {
        boolean hasSslMethod = false;
        try {
            if (WebViewClient.class.getMethod("onReceivedSslError", new Class[]{WebView.class, SslErrorHandler.class, SslError.class}) != null) {
                hasSslMethod = true;
            }
        } catch (NoSuchMethodException e) {
        } catch (IllegalArgumentException e2) {
        }
        if (hasSslMethod) {
            Log.m4552i(TAG, "has method onReceivedSslError : ");
            return new C16765();
        }
        Log.m4552i(TAG, "has no method onReceivedSslError : ");
        return new AuthWebViewClient();
    }

    private String decrypt(String url) {
        try {
            String[] urlStrings = url.split("ud_get=");
            urlStrings[1] = AesHelper.decryptNoPadding(urlStrings[1], "UTF-8").trim();
            url = urlStrings[0] + urlStrings[1];
        } catch (Exception e) {
            Log.m4549e(TAG, "### AuthWebViewClient解密失败");
            e.printStackTrace();
        }
        return url;
    }

    public void show() {
        super.show();
        this.mValues = null;
        this.mWebView.loadUrl(getUrl(this.mPlatform));
    }

    public void dismiss() {
        if (this.mValues == null) {
            this.mListener.onCancel();
        } else if (TextUtils.isEmpty(this.mValues.getString("uid"))) {
            this.mListener.onError(new SocializeException("unfetch usid..."));
        } else {
            Log.m4546d(TAG, "### dismiss ");
            this.mListener.onComplete(this.mValues);
        }
        super.dismiss();
    }
}

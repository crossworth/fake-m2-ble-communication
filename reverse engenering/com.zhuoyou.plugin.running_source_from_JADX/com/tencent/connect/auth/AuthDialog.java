package com.tencent.connect.auth;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.tencent.connect.auth.AuthMap.Auth;
import com.tencent.connect.common.Constants;
import com.tencent.open.p035c.C1334c;
import com.tencent.open.p036a.C1314f;
import com.tencent.open.p037b.C1331g;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.Util;
import com.tencent.open.web.security.C1347b;
import com.tencent.open.web.security.JniInterface;
import com.tencent.open.web.security.SecureJsInterface;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.umeng.facebook.internal.WebDialog;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class AuthDialog extends Dialog {
    private String f3562a;
    private OnTimeListener f3563b;
    private IUiListener f3564c;
    private Handler f3565d;
    private FrameLayout f3566e;
    private LinearLayout f3567f;
    private FrameLayout f3568g;
    private ProgressBar f3569h;
    private String f3570i;
    private C1334c f3571j;
    private Context f3572k;
    private C1347b f3573l;
    private boolean f3574m = false;
    private int f3575n;
    private String f3576o;
    private String f3577p;
    private long f3578q = 0;
    private long f3579r = StatisticConfig.MIN_UPLOAD_INTERVAL;
    private HashMap<String, Runnable> f3580s;

    /* compiled from: ProGuard */
    class C11541 implements OnLongClickListener {
        final /* synthetic */ AuthDialog f3548a;

        C11541(AuthDialog authDialog) {
            this.f3548a = authDialog;
        }

        public boolean onLongClick(View view) {
            return true;
        }
    }

    /* compiled from: ProGuard */
    class C11552 implements OnTouchListener {
        final /* synthetic */ AuthDialog f3549a;

        C11552(AuthDialog authDialog) {
            this.f3549a = authDialog;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case 0:
                case 1:
                    if (!view.hasFocus()) {
                        view.requestFocus();
                        break;
                    }
                    break;
            }
            return false;
        }
    }

    /* compiled from: ProGuard */
    class C11563 implements OnDismissListener {
        final /* synthetic */ AuthDialog f3550a;

        C11563(AuthDialog authDialog) {
            this.f3550a = authDialog;
        }

        public void onDismiss(DialogInterface dialogInterface) {
            try {
                JniInterface.clearAllPWD();
            } catch (Exception e) {
            }
        }
    }

    /* compiled from: ProGuard */
    private class LoginWebViewClient extends WebViewClient {
        final /* synthetic */ AuthDialog f3552a;

        /* compiled from: ProGuard */
        class C11571 implements Runnable {
            final /* synthetic */ LoginWebViewClient f3551a;

            C11571(LoginWebViewClient loginWebViewClient) {
                this.f3551a = loginWebViewClient;
            }

            public void run() {
                this.f3551a.f3552a.f3571j.loadUrl(this.f3551a.f3552a.f3576o);
            }
        }

        private LoginWebViewClient(AuthDialog authDialog) {
            this.f3552a = authDialog;
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            C1314f.m3864a("openSDK_LOG.AuthDialog", "-->Redirect URL: " + str);
            if (str.startsWith(AuthConstants.REDIRECT_BROWSER_URI)) {
                JSONObject parseUrlToJson = Util.parseUrlToJson(str);
                this.f3552a.f3574m = this.f3552a.m3385e();
                if (!this.f3552a.f3574m) {
                    if (parseUrlToJson.optString("fail_cb", null) != null) {
                        this.f3552a.callJs(parseUrlToJson.optString("fail_cb"), "");
                    } else if (parseUrlToJson.optInt("fall_to_wv") == 1) {
                        AuthDialog.m3370a(this.f3552a, this.f3552a.f3562a.indexOf("?") > -1 ? "&" : "?");
                        AuthDialog.m3370a(this.f3552a, (Object) "browser_error=1");
                        this.f3552a.f3571j.loadUrl(this.f3552a.f3562a);
                    } else {
                        String optString = parseUrlToJson.optString("redir", null);
                        if (optString != null) {
                            this.f3552a.f3571j.loadUrl(optString);
                        }
                    }
                }
                return true;
            } else if (str.startsWith(ServerSetting.DEFAULT_REDIRECT_URI)) {
                this.f3552a.f3563b.onComplete(Util.parseUrlToJson(str));
                this.f3552a.dismiss();
                return true;
            } else if (str.startsWith("auth://cancel")) {
                this.f3552a.f3563b.onCancel();
                this.f3552a.dismiss();
                return true;
            } else if (str.startsWith("auth://close")) {
                this.f3552a.dismiss();
                return true;
            } else if (str.startsWith("download://")) {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(Uri.decode(str.substring("download://".length()))));
                    intent.addFlags(268435456);
                    this.f3552a.f3572k.startActivity(intent);
                } catch (Throwable e) {
                    C1314f.m3868b("openSDK_LOG.AuthDialog", "-->start download activity exception, e: ", e);
                }
                return true;
            } else if (str.startsWith(AuthConstants.PROGRESS_URI)) {
                try {
                    r0 = Uri.parse(str).getPathSegments();
                    if (r0.isEmpty()) {
                        return true;
                    }
                    int intValue = Integer.valueOf((String) r0.get(0)).intValue();
                    if (intValue == 0) {
                        this.f3552a.f3568g.setVisibility(8);
                        this.f3552a.f3571j.setVisibility(0);
                    } else if (intValue == 1) {
                        this.f3552a.f3568g.setVisibility(0);
                    }
                    return true;
                } catch (Exception e2) {
                    return true;
                }
            } else if (str.startsWith(AuthConstants.ON_LOGIN_URI)) {
                try {
                    r0 = Uri.parse(str).getPathSegments();
                    if (!r0.isEmpty()) {
                        this.f3552a.f3577p = (String) r0.get(0);
                    }
                } catch (Exception e3) {
                }
                return true;
            } else if (this.f3552a.f3573l.mo2214a(this.f3552a.f3571j, str)) {
                return true;
            } else {
                C1314f.m3870c("openSDK_LOG.AuthDialog", "-->Redirect URL: return false");
                return false;
            }
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
            C1314f.m3870c("openSDK_LOG.AuthDialog", "-->onReceivedError, errorCode: " + i + " | description: " + str);
            if (!Util.checkNetWork(this.f3552a.f3572k)) {
                this.f3552a.f3563b.onError(new UiError(m_AppUI.MSG_CLICK_ITEM, "当前网络不可用，请稍后重试！", str2));
                this.f3552a.dismiss();
            } else if (this.f3552a.f3576o.startsWith(ServerSetting.DOWNLOAD_QQ_URL)) {
                this.f3552a.f3563b.onError(new UiError(i, str, str2));
                this.f3552a.dismiss();
            } else {
                long elapsedRealtime = SystemClock.elapsedRealtime() - this.f3552a.f3578q;
                if (this.f3552a.f3575n >= 1 || elapsedRealtime >= this.f3552a.f3579r) {
                    this.f3552a.f3571j.loadUrl(this.f3552a.m3369a());
                    return;
                }
                this.f3552a.f3575n = this.f3552a.f3575n + 1;
                this.f3552a.f3565d.postDelayed(new C11571(this), 500);
            }
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            C1314f.m3864a("openSDK_LOG.AuthDialog", "-->onPageStarted, url: " + str);
            super.onPageStarted(webView, str, bitmap);
            this.f3552a.f3568g.setVisibility(0);
            this.f3552a.f3578q = SystemClock.elapsedRealtime();
            if (!TextUtils.isEmpty(this.f3552a.f3576o)) {
                this.f3552a.f3565d.removeCallbacks((Runnable) this.f3552a.f3580s.remove(this.f3552a.f3576o));
            }
            this.f3552a.f3576o = str;
            Runnable timeOutRunable = new TimeOutRunable(this.f3552a, this.f3552a.f3576o);
            this.f3552a.f3580s.put(str, timeOutRunable);
            this.f3552a.f3565d.postDelayed(timeOutRunable, 120000);
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            C1314f.m3864a("openSDK_LOG.AuthDialog", "-->onPageFinished, url: " + str);
            this.f3552a.f3568g.setVisibility(8);
            if (this.f3552a.f3571j != null) {
                this.f3552a.f3571j.setVisibility(0);
            }
            if (!TextUtils.isEmpty(str)) {
                this.f3552a.f3565d.removeCallbacks((Runnable) this.f3552a.f3580s.remove(str));
            }
        }

        @TargetApi(8)
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.cancel();
            this.f3552a.f3563b.onError(new UiError(sslError.getPrimaryError(), "请求不合法，请检查手机安全设置，如系统时间、代理等。", "ssl error"));
            this.f3552a.dismiss();
        }
    }

    /* compiled from: ProGuard */
    private class OnTimeListener implements IUiListener {
        String f3553a;
        String f3554b;
        final /* synthetic */ AuthDialog f3555c;
        private String f3556d;
        private IUiListener f3557e;

        public OnTimeListener(AuthDialog authDialog, String str, String str2, String str3, IUiListener iUiListener) {
            this.f3555c = authDialog;
            this.f3556d = str;
            this.f3553a = str2;
            this.f3554b = str3;
            this.f3557e = iUiListener;
        }

        private void m3366a(String str) {
            try {
                onComplete(Util.parseJson(str));
            } catch (JSONException e) {
                e.printStackTrace();
                onError(new UiError(-4, Constants.MSG_JSON_ERROR, str));
            }
        }

        public void onComplete(Object obj) {
            JSONObject jSONObject = (JSONObject) obj;
            C1331g.m3907a().m3911a(this.f3556d + "_H5", SystemClock.elapsedRealtime(), 0, 0, jSONObject.optInt("ret", -6), this.f3553a, false);
            if (this.f3557e != null) {
                this.f3557e.onComplete(jSONObject);
                this.f3557e = null;
            }
        }

        public void onError(UiError uiError) {
            String str = uiError.errorMessage != null ? uiError.errorMessage + this.f3553a : this.f3553a;
            C1331g.m3907a().m3911a(this.f3556d + "_H5", SystemClock.elapsedRealtime(), 0, 0, uiError.errorCode, str, false);
            this.f3555c.m3372a(str);
            if (this.f3557e != null) {
                this.f3557e.onError(uiError);
                this.f3557e = null;
            }
        }

        public void onCancel() {
            if (this.f3557e != null) {
                this.f3557e.onCancel();
                this.f3557e = null;
            }
        }
    }

    /* compiled from: ProGuard */
    private class THandler extends Handler {
        final /* synthetic */ AuthDialog f3558a;
        private OnTimeListener f3559b;

        public THandler(AuthDialog authDialog, OnTimeListener onTimeListener, Looper looper) {
            this.f3558a = authDialog;
            super(looper);
            this.f3559b = onTimeListener;
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    this.f3559b.m3366a((String) message.obj);
                    return;
                case 2:
                    this.f3559b.onCancel();
                    return;
                case 3:
                    AuthDialog.m3377b(this.f3558a.f3572k, (String) message.obj);
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: ProGuard */
    class TimeOutRunable implements Runnable {
        String f3560a = "";
        final /* synthetic */ AuthDialog f3561b;

        public TimeOutRunable(AuthDialog authDialog, String str) {
            this.f3561b = authDialog;
            this.f3560a = str;
        }

        public void run() {
            C1314f.m3864a("openSDK_LOG.AuthDialog", "-->timeoutUrl: " + this.f3560a + " | mRetryUrl: " + this.f3561b.f3576o);
            if (this.f3560a.equals(this.f3561b.f3576o)) {
                this.f3561b.f3563b.onError(new UiError(9002, "请求页面超时，请稍后重试！", this.f3561b.f3576o));
                this.f3561b.dismiss();
            }
        }
    }

    static /* synthetic */ String m3370a(AuthDialog authDialog, Object obj) {
        String str = authDialog.f3562a + obj;
        authDialog.f3562a = str;
        return str;
    }

    static {
        try {
            Context context = Global.getContext();
            if (context == null) {
                C1314f.m3870c("openSDK_LOG.AuthDialog", "-->load lib fail, because context is null:" + AuthAgent.SECURE_LIB_NAME);
            } else if (new File(context.getFilesDir().toString() + "/" + AuthAgent.SECURE_LIB_NAME).exists()) {
                System.load(context.getFilesDir().toString() + "/" + AuthAgent.SECURE_LIB_NAME);
                C1314f.m3870c("openSDK_LOG.AuthDialog", "-->load lib success:" + AuthAgent.SECURE_LIB_NAME);
            } else {
                C1314f.m3870c("openSDK_LOG.AuthDialog", "-->fail, because so is not exists:" + AuthAgent.SECURE_LIB_NAME);
            }
        } catch (Throwable e) {
            C1314f.m3868b("openSDK_LOG.AuthDialog", "-->load lib error:" + AuthAgent.SECURE_LIB_NAME, e);
        }
    }

    public AuthDialog(Context context, String str, String str2, IUiListener iUiListener, QQToken qQToken) {
        super(context, WebDialog.DEFAULT_THEME);
        this.f3572k = context;
        this.f3562a = str2;
        this.f3563b = new OnTimeListener(this, str, str2, qQToken.getAppId(), iUiListener);
        this.f3565d = new THandler(this, this.f3563b, context.getMainLooper());
        this.f3564c = iUiListener;
        this.f3570i = str;
        this.f3573l = new C1347b();
        getWindow().setSoftInputMode(32);
    }

    protected void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        m3376b();
        m3383d();
        this.f3580s = new HashMap();
    }

    public void onBackPressed() {
        if (!this.f3574m) {
            this.f3563b.onCancel();
        }
        super.onBackPressed();
    }

    protected void onStop() {
        super.onStop();
    }

    private String m3372a(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        if (!TextUtils.isEmpty(this.f3577p) && this.f3577p.length() >= 4) {
            stringBuilder.append("_u_").append(this.f3577p.substring(this.f3577p.length() - 4));
        }
        return stringBuilder.toString();
    }

    private String m3369a() {
        String str = ServerSetting.DOWNLOAD_QQ_URL + this.f3562a.substring(this.f3562a.indexOf("?") + 1);
        C1314f.m3870c("openSDK_LOG.AuthDialog", "-->generateDownloadUrl, url: http://qzs.qq.com/open/mobile/login/qzsjump.html?");
        return str;
    }

    private void m3376b() {
        m3380c();
        LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        this.f3571j = new C1334c(this.f3572k);
        if (VERSION.SDK_INT >= 11) {
            this.f3571j.setLayerType(1, null);
        }
        this.f3571j.setLayoutParams(layoutParams);
        this.f3566e = new FrameLayout(this.f3572k);
        layoutParams.gravity = 17;
        this.f3566e.setLayoutParams(layoutParams);
        this.f3566e.addView(this.f3571j);
        this.f3566e.addView(this.f3568g);
        setContentView(this.f3566e);
    }

    private void m3380c() {
        LayoutParams layoutParams;
        this.f3569h = new ProgressBar(this.f3572k);
        this.f3569h.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        this.f3567f = new LinearLayout(this.f3572k);
        View view = null;
        if (this.f3570i.equals(SystemUtils.ACTION_LOGIN)) {
            layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.gravity = 16;
            layoutParams.leftMargin = 5;
            view = new TextView(this.f3572k);
            if (Locale.getDefault().getLanguage().equals("zh")) {
                view.setText("登录中...");
            } else {
                view.setText("Logging in...");
            }
            view.setTextColor(Color.rgb(255, 255, 255));
            view.setTextSize(18.0f);
            view.setLayoutParams(layoutParams);
        }
        layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        this.f3567f.setLayoutParams(layoutParams);
        this.f3567f.addView(this.f3569h);
        if (view != null) {
            this.f3567f.addView(view);
        }
        this.f3568g = new FrameLayout(this.f3572k);
        LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -2);
        layoutParams2.leftMargin = 80;
        layoutParams2.rightMargin = 80;
        layoutParams2.topMargin = 40;
        layoutParams2.bottomMargin = 40;
        layoutParams2.gravity = 17;
        this.f3568g.setLayoutParams(layoutParams2);
        this.f3568g.setBackgroundResource(17301504);
        this.f3568g.addView(this.f3567f);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void m3383d() {
        this.f3571j.setVerticalScrollBarEnabled(false);
        this.f3571j.setHorizontalScrollBarEnabled(false);
        this.f3571j.setWebViewClient(new LoginWebViewClient());
        this.f3571j.setWebChromeClient(new WebChromeClient());
        this.f3571j.clearFormData();
        this.f3571j.clearSslPreferences();
        this.f3571j.setOnLongClickListener(new C11541(this));
        this.f3571j.setOnTouchListener(new C11552(this));
        WebSettings settings = this.f3571j.getSettings();
        settings.setSavePassword(false);
        settings.setSaveFormData(false);
        settings.setCacheMode(-1);
        settings.setNeedInitialFocus(false);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setRenderPriority(RenderPriority.HIGH);
        settings.setJavaScriptEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDatabasePath(this.f3572k.getDir("databases", 0).getPath());
        settings.setDomStorageEnabled(true);
        C1314f.m3864a("openSDK_LOG.AuthDialog", "-->mUrl : " + this.f3562a);
        this.f3576o = this.f3562a;
        this.f3571j.loadUrl(this.f3562a);
        this.f3571j.setVisibility(4);
        this.f3571j.getSettings().setSavePassword(false);
        this.f3573l.m3881a(new SecureJsInterface(), "SecureJsInterface");
        SecureJsInterface.isPWDEdit = false;
        super.setOnDismissListener(new C11563(this));
    }

    private boolean m3385e() {
        AuthMap instance = AuthMap.getInstance();
        String makeKey = instance.makeKey();
        Auth auth = new Auth();
        auth.listener = this.f3564c;
        auth.dialog = this;
        auth.key = makeKey;
        String str = instance.set(auth);
        String substring = this.f3562a.substring(0, this.f3562a.indexOf("?"));
        Bundle parseUrl = Util.parseUrl(this.f3562a);
        parseUrl.putString("token_key", makeKey);
        parseUrl.putString("serial", str);
        parseUrl.putString("browser", "1");
        this.f3562a = substring + "?" + HttpUtils.encodeUrl(parseUrl);
        return Util.openBrowser(this.f3572k, this.f3562a);
    }

    private static void m3377b(Context context, String str) {
        try {
            JSONObject parseJson = Util.parseJson(str);
            int i = parseJson.getInt("type");
            Toast.makeText(context.getApplicationContext(), parseJson.getString("msg"), i).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void callJs(String str, String str2) {
        this.f3571j.loadUrl("javascript:" + str + "(" + str2 + ");void(" + System.currentTimeMillis() + ");");
    }

    public void dismiss() {
        this.f3580s.clear();
        this.f3565d.removeCallbacksAndMessages(null);
        if (isShowing()) {
            super.dismiss();
        }
        if (this.f3571j != null) {
            this.f3571j.destroy();
            this.f3571j = null;
        }
    }
}

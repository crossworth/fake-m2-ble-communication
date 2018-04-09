package com.tencent.connect.auth;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
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
import com.facebook.internal.WebDialog;
import com.tencent.connect.auth.AuthMap.Auth;
import com.tencent.connect.common.Constants;
import com.tencent.open.p019a.C0799b;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.tencent.utils.ServerSetting;
import com.tencent.utils.Util;
import com.umeng.socialize.common.SocializeConstants;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class AuthDialog extends Dialog {
    private static WeakReference<Context> f2361a;
    private static WeakReference<View> f2362l;
    private String f2363b;
    private OnTimeListener f2364c;
    private IUiListener f2365d;
    private Handler f2366e;
    private FrameLayout f2367f;
    private LinearLayout f2368g;
    private FrameLayout f2369h;
    private ProgressBar f2370i;
    private String f2371j;
    private WebView f2372k;
    private boolean f2373m = false;

    /* compiled from: ProGuard */
    private class JsListener {
        final /* synthetic */ AuthDialog f2359a;

        public void onCancelLogin() {
            onCancel(null);
        }

        public void onCancel(String str) {
            this.f2359a.f2366e.obtainMessage(2, str).sendToTarget();
            this.f2359a.dismiss();
        }

        public void showMsg(String str) {
            this.f2359a.f2366e.obtainMessage(3, str).sendToTarget();
        }

        public void onLoad(String str) {
            this.f2359a.f2366e.obtainMessage(4, str).sendToTarget();
        }
    }

    /* compiled from: ProGuard */
    private static class THandler extends Handler {
        private OnTimeListener f2360a;

        public THandler(OnTimeListener onTimeListener, Looper looper) {
            super(looper);
            this.f2360a = onTimeListener;
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    this.f2360a.m4665a((String) message.obj);
                    return;
                case 2:
                    this.f2360a.onCancel();
                    return;
                case 3:
                    if (AuthDialog.f2361a != null && AuthDialog.f2361a.get() != null) {
                        AuthDialog.m2317b((Context) AuthDialog.f2361a.get(), (String) message.obj);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: ProGuard */
    private class LoginWebViewClient extends WebViewClient {
        final /* synthetic */ AuthDialog f4530a;

        private LoginWebViewClient(AuthDialog authDialog) {
            this.f4530a = authDialog;
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            Util.logd("AuthDialog", "Redirect URL: " + str);
            if (str.startsWith(AuthConstants.REDIRECT_BROWSER_URI)) {
                JSONObject parseUrlToJson = Util.parseUrlToJson(str);
                this.f4530a.f2373m = this.f4530a.m2326f();
                if (!this.f4530a.f2373m) {
                    if (parseUrlToJson.optString("fail_cb", null) != null) {
                        this.f4530a.callJs(parseUrlToJson.optString("fail_cb"), "");
                    } else if (parseUrlToJson.optInt("fall_to_wv") == 1) {
                        AuthDialog.m2311a(this.f4530a, this.f4530a.f2363b.indexOf("?") > -1 ? "&" : "?");
                        AuthDialog.m2311a(this.f4530a, (Object) "browser_error=1");
                        this.f4530a.f2372k.loadUrl(this.f4530a.f2363b);
                    } else {
                        String optString = parseUrlToJson.optString("redir", null);
                        if (optString != null) {
                            this.f4530a.f2372k.loadUrl(optString);
                        }
                    }
                }
                return true;
            } else if (str.startsWith(ServerSetting.getInstance().getEnvUrl((Context) AuthDialog.f2361a.get(), ServerSetting.DEFAULT_REDIRECT_URI))) {
                this.f4530a.f2364c.onComplete(Util.parseUrlToJson(str));
                this.f4530a.dismiss();
                return true;
            } else if (str.startsWith("auth://cancel")) {
                this.f4530a.f2364c.onCancel();
                this.f4530a.dismiss();
                return true;
            } else if (str.startsWith("auth://close")) {
                this.f4530a.dismiss();
                return true;
            } else if (!str.startsWith("download://")) {
                return false;
            } else {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(Uri.decode(str.substring("download://".length()))));
                intent.addFlags(268435456);
                if (!(AuthDialog.f2361a == null || AuthDialog.f2361a.get() == null)) {
                    ((Context) AuthDialog.f2361a.get()).startActivity(intent);
                }
                return true;
            }
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
            this.f4530a.f2364c.onError(new UiError(i, str, str2));
            if (!(AuthDialog.f2361a == null || AuthDialog.f2361a.get() == null)) {
                Toast.makeText((Context) AuthDialog.f2361a.get(), "网络连接异常或系统错误", 0).show();
            }
            this.f4530a.dismiss();
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            Util.logd("AuthDialog", "Webview loading URL: " + str);
            super.onPageStarted(webView, str, bitmap);
            if (AuthDialog.f2362l != null && AuthDialog.f2362l.get() != null) {
                ((View) AuthDialog.f2362l.get()).setVisibility(0);
            }
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            if (!(AuthDialog.f2362l == null || AuthDialog.f2362l.get() == null)) {
                ((View) AuthDialog.f2362l.get()).setVisibility(8);
            }
            this.f4530a.f2372k.setVisibility(0);
        }
    }

    /* compiled from: ProGuard */
    private class OnTimeListener implements IUiListener {
        String f4531a;
        String f4532b;
        final /* synthetic */ AuthDialog f4533c;
        private String f4534d;
        private IUiListener f4535e;

        public OnTimeListener(AuthDialog authDialog, String str, String str2, String str3, IUiListener iUiListener) {
            this.f4533c = authDialog;
            this.f4534d = str;
            this.f4531a = str2;
            this.f4532b = str3;
            this.f4535e = iUiListener;
        }

        private void m4665a(String str) {
            try {
                onComplete(Util.parseJson(str));
            } catch (JSONException e) {
                e.printStackTrace();
                onError(new UiError(-4, Constants.MSG_JSON_ERROR, str));
            }
        }

        public void onComplete(Object obj) {
            JSONObject jSONObject = (JSONObject) obj;
            C0799b.m2544a().m2557a((Context) AuthDialog.f2361a.get(), this.f4534d + "_H5", SystemClock.elapsedRealtime(), 0, 0, jSONObject.optInt("ret", -6), this.f4532b, this.f4531a, "1000067");
            if (this.f4535e != null) {
                this.f4535e.onComplete(jSONObject);
                this.f4535e = null;
            }
        }

        public void onError(UiError uiError) {
            C0799b.m2544a().m2557a((Context) AuthDialog.f2361a.get(), this.f4534d + "_H5", SystemClock.elapsedRealtime(), 0, 0, uiError.errorCode, this.f4532b, uiError.errorMessage != null ? uiError.errorMessage + this.f4531a : this.f4531a, "1000067");
            if (this.f4535e != null) {
                this.f4535e.onError(uiError);
                this.f4535e = null;
            }
        }

        public void onCancel() {
            if (this.f4535e != null) {
                this.f4535e.onCancel();
                this.f4535e = null;
            }
        }
    }

    static /* synthetic */ String m2311a(AuthDialog authDialog, Object obj) {
        String str = authDialog.f2363b + obj;
        authDialog.f2363b = str;
        return str;
    }

    public AuthDialog(Context context, String str, String str2, IUiListener iUiListener, QQToken qQToken) {
        super(context, WebDialog.DEFAULT_THEME);
        f2361a = new WeakReference(context.getApplicationContext());
        this.f2363b = str2;
        this.f2364c = new OnTimeListener(this, str, str2, qQToken.getAppId(), iUiListener);
        this.f2366e = new THandler(this.f2364c, context.getMainLooper());
        this.f2365d = iUiListener;
        this.f2371j = str;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        m2320c();
        m2324e();
    }

    public void onBackPressed() {
        if (!this.f2373m) {
            this.f2364c.onCancel();
        }
        super.onBackPressed();
    }

    private void m2320c() {
        m2322d();
        LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        this.f2372k = new WebView((Context) f2361a.get());
        this.f2372k.setLayoutParams(layoutParams);
        try {
            Method method = this.f2372k.getSettings().getClass().getMethod("removeJavascriptInterface", new Class[]{String.class});
            if (method != null) {
                method.invoke(this.f2372k, new Object[]{"searchBoxJavaBridge_"});
            }
        } catch (Exception e) {
        }
        this.f2367f = new FrameLayout((Context) f2361a.get());
        layoutParams.gravity = 17;
        this.f2367f.setLayoutParams(layoutParams);
        this.f2367f.addView(this.f2372k);
        this.f2367f.addView(this.f2369h);
        f2362l = new WeakReference(this.f2369h);
        setContentView(this.f2367f);
    }

    private void m2322d() {
        this.f2370i = new ProgressBar((Context) f2361a.get());
        this.f2370i.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        this.f2368g = new LinearLayout((Context) f2361a.get());
        View view = null;
        if (this.f2371j.equals("action_login")) {
            LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.gravity = 16;
            layoutParams.leftMargin = 5;
            View textView = new TextView((Context) f2361a.get());
            if (Locale.getDefault().getLanguage().equals("zh")) {
                textView.setText("登录中...");
            } else {
                textView.setText("Logging in...");
            }
            textView.setTextColor(Color.rgb(255, 255, 255));
            textView.setTextSize(18.0f);
            textView.setLayoutParams(layoutParams);
            view = textView;
        }
        LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-2, -2);
        layoutParams2.gravity = 17;
        this.f2368g.setLayoutParams(layoutParams2);
        this.f2368g.addView(this.f2370i);
        if (view != null) {
            this.f2368g.addView(view);
        }
        this.f2369h = new FrameLayout((Context) f2361a.get());
        LayoutParams layoutParams3 = new FrameLayout.LayoutParams(-1, -2);
        layoutParams3.leftMargin = 80;
        layoutParams3.rightMargin = 80;
        layoutParams3.topMargin = 40;
        layoutParams3.bottomMargin = 40;
        layoutParams3.gravity = 17;
        this.f2369h.setLayoutParams(layoutParams3);
        this.f2369h.setBackgroundResource(17301504);
        this.f2369h.addView(this.f2368g);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void m2324e() {
        this.f2372k.setVerticalScrollBarEnabled(false);
        this.f2372k.setHorizontalScrollBarEnabled(false);
        this.f2372k.setWebViewClient(new LoginWebViewClient());
        this.f2372k.setWebChromeClient(new WebChromeClient());
        this.f2372k.clearFormData();
        WebSettings settings = this.f2372k.getSettings();
        settings.setSavePassword(false);
        settings.setSaveFormData(false);
        settings.setCacheMode(-1);
        settings.setNeedInitialFocus(false);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setRenderPriority(RenderPriority.HIGH);
        settings.setJavaScriptEnabled(true);
        if (!(f2361a == null || f2361a.get() == null)) {
            settings.setDatabaseEnabled(true);
            settings.setDatabasePath(((Context) f2361a.get()).getApplicationContext().getDir("databases", 0).getPath());
        }
        settings.setDomStorageEnabled(true);
        this.f2372k.loadUrl(this.f2363b);
        this.f2372k.setVisibility(4);
        this.f2372k.getSettings().setSavePassword(false);
    }

    private boolean m2326f() {
        AuthMap instance = AuthMap.getInstance();
        String makeKey = instance.makeKey();
        Auth auth = new Auth();
        auth.listener = this.f2365d;
        auth.dialog = this;
        auth.key = makeKey;
        String str = instance.set(auth);
        String substring = this.f2363b.substring(0, this.f2363b.indexOf("?"));
        Bundle parseUrl = Util.parseUrl(this.f2363b);
        parseUrl.putString("token_key", makeKey);
        parseUrl.putString("serial", str);
        parseUrl.putString("browser", "1");
        this.f2363b = substring + "?" + Util.encodeUrl(parseUrl);
        if (f2361a == null || f2361a.get() == null) {
            return false;
        }
        return Util.openBrowser((Context) f2361a.get(), this.f2363b);
    }

    private static void m2317b(Context context, String str) {
        try {
            JSONObject parseJson = Util.parseJson(str);
            int i = parseJson.getInt("type");
            Toast.makeText(context.getApplicationContext(), parseJson.getString("msg"), i).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void callJs(String str, String str2) {
        this.f2372k.loadUrl("javascript:" + str + SocializeConstants.OP_OPEN_PAREN + str2 + ");void(" + System.currentTimeMillis() + ");");
    }
}

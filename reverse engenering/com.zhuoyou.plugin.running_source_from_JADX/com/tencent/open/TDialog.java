package com.tencent.open;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.connect.auth.AuthConstants;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.open.C1317a.C1277b;
import com.tencent.open.p035c.C1333b;
import com.tencent.open.p036a.C1314f;
import com.tencent.open.p037b.C1331g;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.umeng.facebook.internal.WebDialog;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class TDialog extends C1278b {
    static final LayoutParams f3987a = new LayoutParams(-1, -1);
    static Toast f3988b = null;
    private static WeakReference<ProgressDialog> f3989d;
    private WeakReference<Context> f3990c;
    private String f3991e;
    private OnTimeListener f3992f;
    private IUiListener f3993g;
    private FrameLayout f3994h;
    private C1333b f3995i;
    private Handler f3996j;
    private boolean f3997k = false;
    private QQToken f3998l = null;

    /* compiled from: ProGuard */
    private class FbWebViewClient extends WebViewClient {
        private FbWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            C1314f.m3864a("openSDK_LOG.TDialog", "Redirect URL: " + str);
            if (str.startsWith(ServerSetting.getInstance().getEnvUrl((Context) TDialog.this.f3990c.get(), ServerSetting.DEFAULT_REDIRECT_URI))) {
                TDialog.this.f3992f.onComplete(Util.parseUrlToJson(str));
                if (TDialog.this.isShowing()) {
                    TDialog.this.dismiss();
                }
                return true;
            } else if (str.startsWith("auth://cancel")) {
                TDialog.this.f3992f.onCancel();
                if (TDialog.this.isShowing()) {
                    TDialog.this.dismiss();
                }
                return true;
            } else if (str.startsWith("auth://close")) {
                if (TDialog.this.isShowing()) {
                    TDialog.this.dismiss();
                }
                return true;
            } else if (str.startsWith("download://")) {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(Uri.decode(str.substring("download://".length()))));
                    intent.addFlags(268435456);
                    if (!(TDialog.this.f3990c == null || TDialog.this.f3990c.get() == null)) {
                        ((Context) TDialog.this.f3990c.get()).startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            } else if (str.startsWith(AuthConstants.PROGRESS_URI)) {
                return true;
            } else {
                return false;
            }
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
            TDialog.this.f3992f.onError(new UiError(i, str, str2));
            if (!(TDialog.this.f3990c == null || TDialog.this.f3990c.get() == null)) {
                Toast.makeText((Context) TDialog.this.f3990c.get(), "网络连接异常或系统错误", 0).show();
            }
            TDialog.this.dismiss();
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            C1314f.m3864a("openSDK_LOG.TDialog", "Webview loading URL: " + str);
            super.onPageStarted(webView, str, bitmap);
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            TDialog.this.f3995i.setVisibility(0);
        }
    }

    /* compiled from: ProGuard */
    private class JsListener extends C1277b {
        private JsListener() {
        }

        public void onAddShare(String str) {
            C1314f.m3867b("openSDK_LOG.TDialog", "JsListener onAddShare");
            onComplete(str);
        }

        public void onInvite(String str) {
            onComplete(str);
        }

        public void onCancelAddShare(String str) {
            C1314f.m3872e("openSDK_LOG.TDialog", "JsListener onCancelAddShare" + str);
            onCancel("cancel");
        }

        public void onCancelLogin() {
            onCancel("");
        }

        public void onCancelInvite() {
            C1314f.m3872e("openSDK_LOG.TDialog", "JsListener onCancelInvite");
            onCancel("");
        }

        public void onComplete(String str) {
            TDialog.this.f3996j.obtainMessage(1, str).sendToTarget();
            C1314f.m3872e("openSDK_LOG.TDialog", "JsListener onComplete" + str);
            TDialog.this.dismiss();
        }

        public void onCancel(String str) {
            C1314f.m3872e("openSDK_LOG.TDialog", "JsListener onCancel --msg = " + str);
            TDialog.this.f3996j.obtainMessage(2, str).sendToTarget();
            TDialog.this.dismiss();
        }

        public void showMsg(String str) {
            TDialog.this.f3996j.obtainMessage(3, str).sendToTarget();
        }

        public void onLoad(String str) {
            TDialog.this.f3996j.obtainMessage(4, str).sendToTarget();
        }
    }

    /* compiled from: ProGuard */
    private static class OnTimeListener implements IUiListener {
        private String mAction;
        String mAppid;
        String mUrl;
        private WeakReference<Context> mWeakCtx;
        private IUiListener mWeakL;

        public OnTimeListener(Context context, String str, String str2, String str3, IUiListener iUiListener) {
            this.mWeakCtx = new WeakReference(context);
            this.mAction = str;
            this.mUrl = str2;
            this.mAppid = str3;
            this.mWeakL = iUiListener;
        }

        private void onComplete(String str) {
            try {
                onComplete(Util.parseJson(str));
            } catch (JSONException e) {
                e.printStackTrace();
                onError(new UiError(-4, Constants.MSG_JSON_ERROR, str));
            }
        }

        public void onComplete(Object obj) {
            JSONObject jSONObject = (JSONObject) obj;
            C1331g.m3907a().m3911a(this.mAction + "_H5", SystemClock.elapsedRealtime(), 0, 0, jSONObject.optInt("ret", -6), this.mUrl, false);
            if (this.mWeakL != null) {
                this.mWeakL.onComplete(jSONObject);
                this.mWeakL = null;
            }
        }

        public void onError(UiError uiError) {
            C1331g.m3907a().m3911a(this.mAction + "_H5", SystemClock.elapsedRealtime(), 0, 0, uiError.errorCode, uiError.errorMessage != null ? uiError.errorMessage + this.mUrl : this.mUrl, false);
            if (this.mWeakL != null) {
                this.mWeakL.onError(uiError);
                this.mWeakL = null;
            }
        }

        public void onCancel() {
            if (this.mWeakL != null) {
                this.mWeakL.onCancel();
                this.mWeakL = null;
            }
        }
    }

    /* compiled from: ProGuard */
    private class THandler extends Handler {
        private OnTimeListener mL;

        public THandler(OnTimeListener onTimeListener, Looper looper) {
            super(looper);
            this.mL = onTimeListener;
        }

        public void handleMessage(Message message) {
            C1314f.m3867b("openSDK_LOG.TDialog", "--handleMessage--msg.WHAT = " + message.what);
            switch (message.what) {
                case 1:
                    this.mL.onComplete((String) message.obj);
                    return;
                case 2:
                    this.mL.onCancel();
                    return;
                case 3:
                    if (TDialog.this.f3990c != null && TDialog.this.f3990c.get() != null) {
                        TDialog.m3746c((Context) TDialog.this.f3990c.get(), (String) message.obj);
                        return;
                    }
                    return;
                case 5:
                    if (TDialog.this.f3990c != null && TDialog.this.f3990c.get() != null) {
                        TDialog.m3748d((Context) TDialog.this.f3990c.get(), (String) message.obj);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public TDialog(Context context, String str, String str2, IUiListener iUiListener, QQToken qQToken) {
        super(context, WebDialog.DEFAULT_THEME);
        this.f3990c = new WeakReference(context);
        this.f3991e = str2;
        this.f3992f = new OnTimeListener(context, str, str2, qQToken.getAppId(), iUiListener);
        this.f3996j = new THandler(this.f3992f, context.getMainLooper());
        this.f3993g = iUiListener;
        this.f3998l = qQToken;
    }

    protected void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        m3740a();
        m3743b();
    }

    public void onBackPressed() {
        if (this.f3992f != null) {
            this.f3992f.onCancel();
        }
        super.onBackPressed();
    }

    private void m3740a() {
        new TextView((Context) this.f3990c.get()).setText("test");
        ViewGroup.LayoutParams layoutParams = new LayoutParams(-1, -1);
        this.f3995i = new C1333b((Context) this.f3990c.get());
        this.f3995i.setLayoutParams(layoutParams);
        this.f3994h = new FrameLayout((Context) this.f3990c.get());
        layoutParams.gravity = 17;
        this.f3994h.setLayoutParams(layoutParams);
        this.f3994h.addView(this.f3995i);
        setContentView(this.f3994h);
    }

    protected void onConsoleMessage(String str) {
        C1314f.m3867b("openSDK_LOG.TDialog", "--onConsoleMessage--");
        try {
            this.jsBridge.mo2214a(this.f3995i, str);
        } catch (Exception e) {
        }
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void m3743b() {
        this.f3995i.setVerticalScrollBarEnabled(false);
        this.f3995i.setHorizontalScrollBarEnabled(false);
        this.f3995i.setWebViewClient(new FbWebViewClient());
        this.f3995i.setWebChromeClient(this.mChromeClient);
        this.f3995i.clearFormData();
        WebSettings settings = this.f3995i.getSettings();
        if (settings != null) {
            settings.setSavePassword(false);
            settings.setSaveFormData(false);
            settings.setCacheMode(-1);
            settings.setNeedInitialFocus(false);
            settings.setBuiltInZoomControls(true);
            settings.setSupportZoom(true);
            settings.setRenderPriority(RenderPriority.HIGH);
            settings.setJavaScriptEnabled(true);
            if (!(this.f3990c == null || this.f3990c.get() == null)) {
                settings.setDatabaseEnabled(true);
                settings.setDatabasePath(((Context) this.f3990c.get()).getApplicationContext().getDir("databases", 0).getPath());
            }
            settings.setDomStorageEnabled(true);
            this.jsBridge.m3881a(new JsListener(), "sdk_js_if");
            this.f3995i.loadUrl(this.f3991e);
            this.f3995i.setLayoutParams(f3987a);
            this.f3995i.setVisibility(4);
            this.f3995i.getSettings().setSavePassword(false);
        }
    }

    private static void m3746c(Context context, String str) {
        try {
            JSONObject parseJson = Util.parseJson(str);
            int i = parseJson.getInt("type");
            CharSequence string = parseJson.getString("msg");
            if (i == 0) {
                if (f3988b == null) {
                    f3988b = Toast.makeText(context, string, 0);
                } else {
                    f3988b.setView(f3988b.getView());
                    f3988b.setText(string);
                    f3988b.setDuration(0);
                }
                f3988b.show();
            } else if (i == 1) {
                if (f3988b == null) {
                    f3988b = Toast.makeText(context, string, 1);
                } else {
                    f3988b.setView(f3988b.getView());
                    f3988b.setText(string);
                    f3988b.setDuration(1);
                }
                f3988b.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void m3748d(Context context, String str) {
        if (context != null && str != null) {
            try {
                JSONObject parseJson = Util.parseJson(str);
                int i = parseJson.getInt("action");
                CharSequence string = parseJson.getString("msg");
                if (i == 1) {
                    if (f3989d == null || f3989d.get() == null) {
                        ProgressDialog progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage(string);
                        f3989d = new WeakReference(progressDialog);
                        progressDialog.show();
                        return;
                    }
                    ((ProgressDialog) f3989d.get()).setMessage(string);
                    if (!((ProgressDialog) f3989d.get()).isShowing()) {
                        ((ProgressDialog) f3989d.get()).show();
                    }
                } else if (i == 0 && f3989d != null && f3989d.get() != null && ((ProgressDialog) f3989d.get()).isShowing()) {
                    ((ProgressDialog) f3989d.get()).dismiss();
                    f3989d = null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

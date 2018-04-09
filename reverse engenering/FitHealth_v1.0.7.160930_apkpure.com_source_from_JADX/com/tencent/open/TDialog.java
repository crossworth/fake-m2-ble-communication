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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.internal.WebDialog;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.open.C0803a.C0795a;
import com.tencent.open.p019a.C0799b;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.tencent.utils.ServerSetting;
import com.tencent.utils.Util;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class TDialog extends C0812h {
    static final LayoutParams f4622a = new LayoutParams(-1, -1);
    static Toast f4623b = null;
    private static WeakReference<Context> f4624c;
    private static WeakReference<View> f4625d;
    private static WeakReference<ProgressDialog> f4626e;
    private String f4627f;
    private OnTimeListener f4628g;
    private IUiListener f4629h;
    private FrameLayout f4630i;
    private WebView f4631j;
    private FrameLayout f4632k;
    private ProgressBar f4633l;
    private Handler f4634m;
    private boolean f4635n = false;
    private QQToken f4636o = null;

    /* compiled from: ProGuard */
    private static class THandler extends Handler {
        private OnTimeListener mL;

        public THandler(OnTimeListener onTimeListener, Looper looper) {
            super(looper);
            this.mL = onTimeListener;
        }

        public void handleMessage(Message message) {
            Log.d("TAG", "--handleMessage--msg.WHAT = " + message.what);
            switch (message.what) {
                case 1:
                    this.mL.onComplete((String) message.obj);
                    return;
                case 2:
                    this.mL.onCancel();
                    return;
                case 3:
                    if (TDialog.f4624c != null && TDialog.f4624c.get() != null) {
                        TDialog.m4778c((Context) TDialog.f4624c.get(), (String) message.obj);
                        return;
                    }
                    return;
                case 4:
                    if (TDialog.f4625d != null && TDialog.f4625d.get() != null) {
                        ((View) TDialog.f4625d.get()).setVisibility(8);
                        return;
                    }
                    return;
                case 5:
                    if (TDialog.f4624c != null && TDialog.f4624c.get() != null) {
                        TDialog.m4780d((Context) TDialog.f4624c.get(), (String) message.obj);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: ProGuard */
    private class FbWebViewClient extends WebViewClient {
        private FbWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            Util.logd("TDialog", "Redirect URL: " + str);
            if (str.startsWith(ServerSetting.getInstance().getEnvUrl((Context) TDialog.f4624c.get(), ServerSetting.DEFAULT_REDIRECT_URI))) {
                TDialog.this.f4628g.onComplete(Util.parseUrlToJson(str));
                if (TDialog.this.isShowing()) {
                    TDialog.this.dismiss();
                }
                return true;
            } else if (str.startsWith("auth://cancel")) {
                TDialog.this.f4628g.onCancel();
                if (TDialog.this.isShowing()) {
                    TDialog.this.dismiss();
                }
                return true;
            } else if (str.startsWith("auth://close")) {
                if (TDialog.this.isShowing()) {
                    TDialog.this.dismiss();
                }
                return true;
            } else if (!str.startsWith("download://")) {
                return false;
            } else {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(Uri.decode(str.substring("download://".length()))));
                if (!(TDialog.f4624c == null || TDialog.f4624c.get() == null)) {
                    ((Context) TDialog.f4624c.get()).startActivity(intent);
                }
                return true;
            }
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
            TDialog.this.f4628g.onError(new UiError(i, str, str2));
            if (!(TDialog.f4624c == null || TDialog.f4624c.get() == null)) {
                Toast.makeText((Context) TDialog.f4624c.get(), "网络连接异常或系统错误", 0).show();
            }
            TDialog.this.dismiss();
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            Util.logd("TDialog", "Webview loading URL: " + str);
            super.onPageStarted(webView, str, bitmap);
            if (TDialog.f4625d != null && TDialog.f4625d.get() != null) {
                ((View) TDialog.f4625d.get()).setVisibility(0);
            }
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            if (!(TDialog.f4625d == null || TDialog.f4625d.get() == null)) {
                ((View) TDialog.f4625d.get()).setVisibility(8);
            }
            TDialog.this.f4631j.setVisibility(0);
        }
    }

    /* compiled from: ProGuard */
    private class JsListener extends C0795a {
        private JsListener() {
        }

        public void onAddShare(String str) {
            Log.d("TDialog", "onAddShare");
            onComplete(str);
        }

        public void onInvite(String str) {
            onComplete(str);
        }

        public void onCancelAddShare(String str) {
            Log.d("TDialog", "onCancelAddShare");
            onCancel("cancel");
        }

        public void onCancelLogin() {
            onCancel("");
        }

        public void onCancelInvite() {
            Log.d("TDialog", "onCancelInvite");
            onCancel("");
        }

        public void onComplete(String str) {
            TDialog.this.f4634m.obtainMessage(1, str).sendToTarget();
            Log.e("onComplete", str);
            TDialog.this.dismiss();
        }

        public void onCancel(String str) {
            Log.d("TDialog", "onCancel --msg = " + str);
            TDialog.this.f4634m.obtainMessage(2, str).sendToTarget();
            TDialog.this.dismiss();
        }

        public void showMsg(String str) {
            TDialog.this.f4634m.obtainMessage(3, str).sendToTarget();
        }

        public void onLoad(String str) {
            TDialog.this.f4634m.obtainMessage(4, str).sendToTarget();
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
            C0799b.m2544a().m2557a((Context) this.mWeakCtx.get(), this.mAction + "_H5", SystemClock.elapsedRealtime(), 0, 0, jSONObject.optInt("ret", -6), this.mAppid, this.mUrl, "1000067");
            if (this.mWeakL != null) {
                this.mWeakL.onComplete(jSONObject);
                this.mWeakL = null;
            }
        }

        public void onError(UiError uiError) {
            C0799b.m2544a().m2557a((Context) this.mWeakCtx.get(), this.mAction + "_H5", SystemClock.elapsedRealtime(), 0, 0, uiError.errorCode, this.mAppid, uiError.errorMessage != null ? uiError.errorMessage + this.mUrl : this.mUrl, "1000067");
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

    public TDialog(Context context, String str, String str2, IUiListener iUiListener, QQToken qQToken) {
        super(context, WebDialog.DEFAULT_THEME);
        f4624c = new WeakReference(context);
        this.f4627f = str2;
        this.f4628g = new OnTimeListener(context, str, str2, qQToken.getAppId(), iUiListener);
        this.f4634m = new THandler(this.f4628g, context.getMainLooper());
        this.f4629h = iUiListener;
        this.f4636o = qQToken;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        m4777c();
        m4779d();
    }

    public void onBackPressed() {
        if (this.f4628g != null) {
            this.f4628g.onCancel();
        }
        super.onBackPressed();
    }

    private void m4777c() {
        this.f4633l = new ProgressBar((Context) f4624c.get());
        ViewGroup.LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        this.f4633l.setLayoutParams(layoutParams);
        new TextView((Context) f4624c.get()).setText("test");
        this.f4632k = new FrameLayout((Context) f4624c.get());
        layoutParams = new LayoutParams(-1, -2);
        layoutParams.bottomMargin = 40;
        layoutParams.leftMargin = 80;
        layoutParams.rightMargin = 80;
        layoutParams.topMargin = 40;
        layoutParams.gravity = 17;
        this.f4632k.setLayoutParams(layoutParams);
        this.f4632k.setBackgroundResource(17301504);
        this.f4632k.addView(this.f4633l);
        ViewGroup.LayoutParams layoutParams2 = new LayoutParams(-1, -1);
        this.f4631j = new WebView((Context) f4624c.get());
        this.f4631j.setLayoutParams(layoutParams2);
        this.f4630i = new FrameLayout((Context) f4624c.get());
        layoutParams2.gravity = 17;
        this.f4630i.setLayoutParams(layoutParams2);
        this.f4630i.addView(this.f4631j);
        this.f4630i.addView(this.f4632k);
        f4625d = new WeakReference(this.f4632k);
        setContentView(this.f4630i);
    }

    protected void onConsoleMessage(String str) {
        Log.d("TDialog", "--onConsoleMessage--");
        try {
            this.jsBridge.m2570a(this.f4631j, str);
        } catch (Exception e) {
        }
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void m4779d() {
        this.f4631j.setVerticalScrollBarEnabled(false);
        this.f4631j.setHorizontalScrollBarEnabled(false);
        this.f4631j.setWebViewClient(new FbWebViewClient());
        this.f4631j.setWebChromeClient(this.mChromeClient);
        this.f4631j.clearFormData();
        WebSettings settings = this.f4631j.getSettings();
        settings.setSavePassword(false);
        settings.setSaveFormData(false);
        settings.setCacheMode(-1);
        settings.setNeedInitialFocus(false);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setRenderPriority(RenderPriority.HIGH);
        settings.setJavaScriptEnabled(true);
        try {
            Method method = settings.getClass().getMethod("removeJavascriptInterface", new Class[]{String.class});
            if (method != null) {
                method.invoke(this.f4631j, new Object[]{"searchBoxJavaBridge_"});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!(f4624c == null || f4624c.get() == null)) {
            settings.setDatabaseEnabled(true);
            settings.setDatabasePath(((Context) f4624c.get()).getApplicationContext().getDir("databases", 0).getPath());
        }
        settings.setDomStorageEnabled(true);
        this.jsBridge.m2568a(new JsListener(), "sdk_js_if");
        this.f4631j.loadUrl(this.f4627f);
        this.f4631j.setLayoutParams(f4622a);
        this.f4631j.setVisibility(4);
        this.f4631j.getSettings().setSavePassword(false);
    }

    private static void m4778c(Context context, String str) {
        try {
            JSONObject parseJson = Util.parseJson(str);
            int i = parseJson.getInt("type");
            CharSequence string = parseJson.getString("msg");
            if (i == 0) {
                if (f4623b == null) {
                    f4623b = Toast.makeText(context, string, 0);
                } else {
                    f4623b.setView(f4623b.getView());
                    f4623b.setText(string);
                    f4623b.setDuration(0);
                }
                f4623b.show();
            } else if (i == 1) {
                if (f4623b == null) {
                    f4623b = Toast.makeText(context, string, 1);
                } else {
                    f4623b.setView(f4623b.getView());
                    f4623b.setText(string);
                    f4623b.setDuration(1);
                }
                f4623b.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void m4780d(Context context, String str) {
        if (context != null && str != null) {
            try {
                JSONObject parseJson = Util.parseJson(str);
                int i = parseJson.getInt("action");
                CharSequence string = parseJson.getString("msg");
                if (i == 1) {
                    if (f4626e == null) {
                        ProgressDialog progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage(string);
                        f4626e = new WeakReference(progressDialog);
                        progressDialog.show();
                        return;
                    }
                    ((ProgressDialog) f4626e.get()).setMessage(string);
                    if (!((ProgressDialog) f4626e.get()).isShowing()) {
                        ((ProgressDialog) f4626e.get()).show();
                    }
                } else if (i == 0 && f4626e != null && f4626e.get() != null && ((ProgressDialog) f4626e.get()).isShowing()) {
                    ((ProgressDialog) f4626e.get()).dismiss();
                    f4626e = null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

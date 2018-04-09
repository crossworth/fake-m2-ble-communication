package com.tencent.open.yyb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ZoomButtonsController;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.open.p035c.C1333b;
import com.tencent.open.p036a.C1314f;
import com.tencent.open.utils.SystemUtils;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class AppbarActivity extends Activity implements OnClickListener {
    private static final int FLOATING_DIALOG_HEIGHT = 100;
    public static final String MYAPP_CACHE_PATH = "/tencent/tassistant";
    private static final String TAG = "openSDK_LOG.AppbarActivity";
    private static final String UA_PREFIX = "qqdownloader/";
    private static final String WEBVIEW_PATH = "/webview_cache";
    private static ArrayList<String> specialModel = new ArrayList();
    private String appid;
    private AppbarJsBridge jsBridge;
    private final DownloadListener mDownloadListener = new C13545(this);
    private MoreFloatingDialog mFloatingDialog;
    protected ProgressDialog mProgressDialog;
    private LinearLayout mRootView;
    private TitleBar mTitleBar;
    private QQToken mToken;
    private C1333b mWebView;
    private ShareModel model;
    private Tencent tencent;
    private int titlebarTop;
    private String url;

    /* compiled from: ProGuard */
    class C13491 implements IUiListener {
        final /* synthetic */ AppbarActivity f4242a;

        C13491(AppbarActivity appbarActivity) {
            this.f4242a = appbarActivity;
        }

        public void onError(UiError uiError) {
            C1314f.m3867b(AppbarActivity.TAG, "-->(AppbarJsBridge)openLoginActivity onError" + uiError.errorMessage);
            this.f4242a.jsBridge.responseFail(AppbarJsBridge.CALLBACK_LOGIN, 0, null, -5);
        }

        public void onComplete(Object obj) {
            C1314f.m3867b(AppbarActivity.TAG, "-->(AppbarJsBridge)openLoginActivity onComplete");
            JSONObject jSONObject = (JSONObject) obj;
            if (jSONObject.optInt("ret", -1) != 0) {
                this.f4242a.jsBridge.responseFail(AppbarJsBridge.CALLBACK_LOGIN, 0, null, -5);
                return;
            }
            try {
                String string = jSONObject.getString("openid");
                String string2 = jSONObject.getString("access_token");
                jSONObject.getString("expires_in");
                C1362a.m3988a(this.f4242a, this.f4242a.mWebView.getUrl(), string, string2, this.f4242a.getToken().getAppId());
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put("logintype", "SSO");
                    jSONObject2.put("openid", string);
                    jSONObject2.put("accesstoken", string2);
                    this.f4242a.jsBridge.response(AppbarJsBridge.CALLBACK_LOGIN, 0, null, jSONObject2.toString());
                    Intent intent = new Intent();
                    intent.putExtra(Constants.LOGIN_INFO, jSONObject.toString());
                    this.f4242a.setResult(-1, intent);
                } catch (JSONException e) {
                    this.f4242a.jsBridge.responseFail(AppbarJsBridge.CALLBACK_LOGIN, 0, null, -5);
                    C1314f.m3867b(AppbarActivity.TAG, "-->(AppbarJsBridge)openLoginActivity onComplete: put keys callback failed.");
                }
            } catch (JSONException e2) {
                this.f4242a.jsBridge.responseFail(AppbarJsBridge.CALLBACK_LOGIN, 0, null, -5);
                C1314f.m3867b(AppbarActivity.TAG, "-->(AppbarJsBridge)openLoginActivity onComplete: get keys failed.");
            }
        }

        public void onCancel() {
            C1314f.m3867b(AppbarActivity.TAG, "-->(AppbarJsBridge)openLoginActivity onCancel");
            this.f4242a.jsBridge.responseFail(AppbarJsBridge.CALLBACK_LOGIN, 0, null, -2);
        }
    }

    /* compiled from: ProGuard */
    private interface C1352a {
        void mo2218a(byte[] bArr);
    }

    /* compiled from: ProGuard */
    class C13534 implements C1352a {
        final /* synthetic */ AppbarActivity f4247a;

        C13534(AppbarActivity appbarActivity) {
            this.f4247a = appbarActivity;
        }

        public void mo2218a(byte[] bArr) {
            this.f4247a.mProgressDialog.dismiss();
        }
    }

    /* compiled from: ProGuard */
    class C13545 implements DownloadListener {
        final /* synthetic */ AppbarActivity f4248a;

        C13545(AppbarActivity appbarActivity) {
            this.f4248a = appbarActivity;
        }

        public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
            C1314f.m3864a(AppbarActivity.TAG, "-->(AppbarActivity)onDownloadStart : url = " + str);
            try {
                this.f4248a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
            } catch (Exception e) {
                C1314f.m3867b(AppbarActivity.TAG, "-->(AppbarActivity)onDownloadStart : activity aciton_view not found.");
            }
            QQToken access$500 = this.f4248a.getToken();
            if (access$500 != null) {
                C1362a.m3989a(access$500.getAppId(), "200", "SDK.APPBAR.HOME ACTION");
            }
        }
    }

    /* compiled from: ProGuard */
    private static class C1355b extends AsyncTask<String, Void, byte[]> {
        private C1352a f4249a;

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return m3972a((String[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            m3971a((byte[]) obj);
        }

        public C1355b(C1352a c1352a) {
            this.f4249a = c1352a;
        }

        protected byte[] m3972a(String... strArr) {
            try {
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(strArr[0]).openConnection();
                    httpURLConnection.setConnectTimeout(5000);
                    try {
                        httpURLConnection.setRequestMethod(Constants.HTTP_GET);
                        try {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            try {
                                if (httpURLConnection.getResponseCode() == 200) {
                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    byte[] bArr = new byte[1024];
                                    while (true) {
                                        int read = inputStream.read(bArr);
                                        if (read != -1) {
                                            byteArrayOutputStream.write(bArr, 0, read);
                                        } else {
                                            byteArrayOutputStream.close();
                                            inputStream.close();
                                            return byteArrayOutputStream.toByteArray();
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        } catch (IOException e2) {
                            e2.printStackTrace();
                            return null;
                        }
                    } catch (ProtocolException e3) {
                        e3.printStackTrace();
                        return null;
                    }
                } catch (IOException e22) {
                    e22.printStackTrace();
                    return null;
                }
            } catch (MalformedURLException e4) {
                e4.printStackTrace();
                return null;
            }
        }

        protected void m3971a(byte[] bArr) {
            super.onPostExecute(bArr);
            this.f4249a.mo2218a(bArr);
        }
    }

    /* compiled from: ProGuard */
    private class C1356c extends WebChromeClient {
        final /* synthetic */ AppbarActivity f4250a;

        private C1356c(AppbarActivity appbarActivity) {
            this.f4250a = appbarActivity;
        }

        public void onReceivedTitle(WebView webView, String str) {
            this.f4250a.mTitleBar.setTitle(str);
        }
    }

    /* compiled from: ProGuard */
    private class C1357d extends WebViewClient {
        final /* synthetic */ AppbarActivity f4251a;

        private C1357d(AppbarActivity appbarActivity) {
            this.f4251a = appbarActivity;
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            this.f4251a.setSupportZoom(true);
            this.f4251a.jsBridge.ready();
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
            this.f4251a.setSupportZoom(false);
            if (!str.startsWith("http") && !str.startsWith("https")) {
            }
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            boolean z = true;
            C1314f.m3864a(AppbarActivity.TAG, "-->(AppbarDialog)shouldOverrideUrlLoading : url = " + str);
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            if (str.startsWith("http") || str.startsWith("https")) {
                return super.shouldOverrideUrlLoading(webView, str);
            }
            if (str.startsWith(AppbarJsBridge.JS_BRIDGE_SCHEME)) {
                this.f4251a.jsBridge.invoke(str);
                return true;
            } else if (!str.equals("about:blank;") && !str.equals("about:blank")) {
                return false;
            } else {
                if (VERSION.SDK_INT >= 11) {
                    z = false;
                }
                return z;
            }
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.appid = getIntent().getStringExtra("appid");
        this.url = getIntent().getStringExtra("url");
        C1314f.m3864a(TAG, "-->(AppbarActivity)onCreate : appid = " + this.appid + " url = " + this.url);
        this.mWebView = new C1333b(this);
        this.jsBridge = new AppbarJsBridge(this, this.mWebView);
        createViews();
        initViews();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
        MoreFloatingDialog floatingDialg = getFloatingDialg();
        if (floatingDialg != null && floatingDialg.isShowing()) {
            floatingDialg.dismiss();
        }
    }

    public void onBackPressed() {
        MoreFloatingDialog floatingDialg = getFloatingDialg();
        if (floatingDialg == null || !floatingDialg.isShowing()) {
            super.onBackPressed();
        } else {
            floatingDialg.dismiss();
        }
    }

    private void createViews() {
        LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        this.mWebView.setLayoutParams(layoutParams);
        this.mRootView = new LinearLayout(this);
        layoutParams.gravity = 17;
        this.mRootView.setLayoutParams(layoutParams);
        this.mRootView.setOrientation(1);
        this.mTitleBar = new TitleBar(this);
        this.mTitleBar.getBackBtn().setOnClickListener(this);
        this.mTitleBar.getSharBtn().setOnClickListener(this);
        this.mRootView.addView(this.mTitleBar);
        this.mRootView.addView(this.mWebView);
        setContentView(this.mRootView);
    }

    private void initViews() {
        Method method;
        WebSettings settings = this.mWebView.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setUserAgentString(settings.getUserAgentString() + "/" + UA_PREFIX + this.jsBridge.getVersion() + "/sdk");
        settings.setJavaScriptEnabled(true);
        Class cls = settings.getClass();
        try {
            method = cls.getMethod("setPluginsEnabled", new Class[]{Boolean.TYPE});
            if (method != null) {
                method.invoke(settings, new Object[]{Boolean.valueOf(true)});
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        try {
            method = cls.getMethod("setDomStorageEnabled", new Class[]{Boolean.TYPE});
            if (method != null) {
                method.invoke(settings, new Object[]{Boolean.valueOf(true)});
            }
        } catch (SecurityException e2) {
            e2.printStackTrace();
        } catch (NoSuchMethodException e3) {
        } catch (IllegalArgumentException e4) {
        } catch (IllegalAccessException e5) {
        } catch (InvocationTargetException e6) {
        }
        settings.setAppCachePath(getWebViewCacheDir());
        settings.setDatabasePath(getWebViewCacheDir());
        settings.setDatabaseEnabled(true);
        settings.setAppCacheEnabled(true);
        if (supportWebViewFullScreen()) {
            settings.setUseWideViewPort(true);
            if (VERSION.SDK_INT >= 7) {
                try {
                    cls.getMethod("setLoadWithOverviewMode", new Class[]{Boolean.TYPE}).invoke(settings, new Object[]{Boolean.valueOf(true)});
                } catch (Exception e7) {
                }
            }
            if (SystemUtils.isSupportMultiTouch()) {
                if (SystemUtils.getAndroidSDKVersion() < 11) {
                    try {
                        Field declaredField = WebView.class.getDeclaredField("mZoomButtonsController");
                        declaredField.setAccessible(true);
                        ZoomButtonsController zoomButtonsController = new ZoomButtonsController(this.mWebView);
                        zoomButtonsController.getZoomControls().setVisibility(8);
                        declaredField.set(this.mWebView, zoomButtonsController);
                    } catch (Exception e8) {
                    }
                } else {
                    try {
                        this.mWebView.getSettings().getClass().getMethod("setDisplayZoomControls", new Class[]{Boolean.TYPE}).invoke(this.mWebView.getSettings(), new Object[]{Boolean.valueOf(false)});
                    } catch (Exception e9) {
                    }
                }
            }
        }
        this.mWebView.setWebViewClient(new C1357d());
        this.mWebView.setWebChromeClient(new C1356c());
        this.mWebView.setDownloadListener(this.mDownloadListener);
        this.mWebView.loadUrl(this.url);
    }

    static {
        specialModel.add("MT870");
        specialModel.add("XT910");
        specialModel.add("XT928");
        specialModel.add("MT917");
        specialModel.add("Lenovo A60");
    }

    private boolean supportWebViewFullScreen() {
        String str = Build.MODEL;
        return (str.contains("vivo") || specialModel.contains(str)) ? false : true;
    }

    private Tencent getTencent() {
        if (this.tencent == null) {
            this.tencent = Tencent.createInstance(this.appid, this);
        }
        return this.tencent;
    }

    private QQToken getToken() {
        if (this.mToken == null) {
            this.mToken = getTencent().getQQToken();
        }
        return this.mToken;
    }

    private String getWebViewCacheDir() {
        return getCommonPath(WEBVIEW_PATH);
    }

    private String getCommonPath(String str) {
        String commonRootDir = getCommonRootDir();
        if (!TextUtils.isEmpty(str)) {
            commonRootDir = commonRootDir + str;
        }
        return getPath(commonRootDir, false);
    }

    private MoreFloatingDialog getFloatingDialg() {
        if (this.mFloatingDialog == null) {
            this.mFloatingDialog = new MoreFloatingDialog(this);
            this.mFloatingDialog.setCanceledOnTouchOutside(true);
            this.mFloatingDialog.getQQItem().setOnClickListener(this);
            this.mFloatingDialog.getQzoneItem().setOnClickListener(this);
        }
        return this.mFloatingDialog;
    }

    private String getCommonRootDir() {
        String str;
        if (isSDCardExistAndCanWrite()) {
            str = Environment.getExternalStorageDirectory().getPath() + MYAPP_CACHE_PATH;
        } else {
            File filesDir = getFilesDir();
            if (filesDir == null) {
                return "";
            }
            str = filesDir.getAbsolutePath() + MYAPP_CACHE_PATH;
        }
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    private boolean isSDCardExistAndCanWrite() {
        try {
            if ("mounted".equals(Environment.getExternalStorageState()) && Environment.getExternalStorageDirectory().canWrite()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getPath(String str, boolean z) {
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
            if (z) {
                try {
                    new File(str + File.separator + ".nomedia").createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file.getAbsolutePath();
    }

    private void setSupportZoom(boolean z) {
        if (this.mWebView != null) {
            this.mWebView.getSettings().setSupportZoom(z);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.mWebView != null) {
            this.mWebView.removeAllViews();
            this.mWebView.setVisibility(8);
            this.mWebView.stopLoading();
            this.mWebView.clearHistory();
            this.mWebView.destroy();
        }
    }

    public void showFloatingDialog() {
        MoreFloatingDialog floatingDialg = getFloatingDialg();
        floatingDialg.show();
        Window window = floatingDialg.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 48;
        attributes.y = getTitbarTop() + this.mTitleBar.getHeight();
        Display defaultDisplay = floatingDialg.getWindow().getWindowManager().getDefaultDisplay();
        attributes.height = floatingDialg.dip2px(100.0f);
        attributes.width = ((int) (((double) defaultDisplay.getWidth()) * 0.95d)) / 2;
        attributes.x = attributes.width / 2;
        C1314f.m3867b(TAG, "-->(AppbarDialog)showFloatingDialog : params.x = " + attributes.x);
        window.setAttributes(attributes);
    }

    private int getTitbarTop() {
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.titlebarTop = displayMetrics.heightPixels - rect.height();
        return this.titlebarTop;
    }

    public void onClick(View view) {
        MoreFloatingDialog floatingDialg = getFloatingDialg();
        if (view == this.mTitleBar.getSharBtn()) {
            this.jsBridge.clickCallback();
        } else if (view == floatingDialg.getQQItem()) {
            shareToQQ();
        } else if (view == floatingDialg.getQzoneItem()) {
            shareToQzone();
        } else if (view == floatingDialg.getWXItem()) {
            shareToWX();
        } else if (view == floatingDialg.getTimelineItem()) {
            shareToTimeline();
        } else if (view == this.mTitleBar.getBackBtn()) {
            finish();
        }
    }

    public void login() {
        C1314f.m3867b(TAG, "-->login : activity~~~");
        getTencent().login((Activity) this, "all", new C13491(this));
    }

    public void shareToQQ() {
        final QQToken token = getToken();
        if (token != null) {
            QQShare qQShare = new QQShare(this, token);
            Bundle bundle = new Bundle();
            bundle.putString("title", this.model.f4256a);
            bundle.putString("targetUrl", this.model.f4259d);
            bundle.putString("summary", this.model.f4257b);
            bundle.putString("imageUrl", this.model.f4258c);
            C1314f.m3864a(TAG, "-->(AppbarActivity)shareToQQ : model.mTitle = " + this.model.f4256a);
            C1314f.m3864a(TAG, "-->(AppbarActivity)shareToQQ : model.mTargetUrl = " + this.model.f4259d);
            C1314f.m3864a(TAG, "-->(AppbarActivity)shareToQQ : model.mDescription = " + this.model.f4257b);
            C1314f.m3864a(TAG, "-->(AppbarActivity)shareToQQ : model.mIconUrl = " + this.model.f4258c);
            qQShare.shareToQQ(this, bundle, new IUiListener(this) {
                final /* synthetic */ AppbarActivity f4244b;

                public void onError(UiError uiError) {
                    C1314f.m3867b(AppbarActivity.TAG, "-->(AppbarActivity)shareToQQ onError" + uiError.errorMessage);
                    this.f4244b.jsBridge.responseShareFail(1);
                }

                public void onComplete(Object obj) {
                    C1314f.m3867b(AppbarActivity.TAG, "-->(AppbarActivity)shareToQQ onComplete");
                    this.f4244b.jsBridge.responseShare(1);
                    C1362a.m3989a(token.getAppId(), "400", "SDK.APPBAR.HOME.SHARE.QQ");
                }

                public void onCancel() {
                    C1314f.m3867b(AppbarActivity.TAG, "-->(AppbarActivity)shareToQQ onCancel");
                    this.f4244b.jsBridge.responseShareFail(1);
                }
            });
            C1362a.m3989a(token.getAppId(), "200", "SDK.APPBAR.HOME.SHARE.QQ");
        }
    }

    public void shareToQzone() {
        final QQToken token = getToken();
        if (token != null) {
            QzoneShare qzoneShare = new QzoneShare(this, token);
            Bundle bundle = new Bundle();
            bundle.putInt("req_type", 1);
            bundle.putString("title", this.model.f4256a);
            bundle.putString("summary", this.model.f4257b);
            bundle.putString("targetUrl", this.model.f4259d);
            ArrayList arrayList = new ArrayList();
            C1314f.m3864a(TAG, "-->shareToQzone : mIconUrl = " + this.model.f4258c);
            arrayList.add(this.model.f4258c);
            bundle.putStringArrayList("imageUrl", arrayList);
            qzoneShare.shareToQzone(this, bundle, new IUiListener(this) {
                final /* synthetic */ AppbarActivity f4246b;

                public void onError(UiError uiError) {
                    C1314f.m3867b(AppbarActivity.TAG, "-->(AppbarActivity)shareToQzone onError" + uiError.errorMessage);
                    this.f4246b.jsBridge.responseShareFail(2);
                }

                public void onComplete(Object obj) {
                    C1314f.m3867b(AppbarActivity.TAG, "-->(AppbarActivity)shareToQzone onComplete");
                    this.f4246b.jsBridge.responseShare(2);
                    C1362a.m3989a(token.getAppId(), "400", "SDK.APPBAR.HOME.SHARE.QZ");
                }

                public void onCancel() {
                    C1314f.m3867b(AppbarActivity.TAG, "-->(AppbarActivity)shareToQzone onCancel");
                    this.f4246b.jsBridge.responseShareFail(2);
                }
            });
            C1362a.m3989a(token.getAppId(), "200", "SDK.APPBAR.HOME.SHARE.QZ");
        }
    }

    public void shareToWX() {
        shareToWX(false);
    }

    public void shareToTimeline() {
        shareToWX(true);
    }

    private void shareToWX(boolean z) {
        C1314f.m3864a(TAG, "-->shareToWX : wx_appid = wx8e8dc60535c9cd93");
        if (!TextUtils.isEmpty(this.model.f4258c)) {
            showProgressDialog(this, "", "");
            new C1355b(new C13534(this)).execute(new String[]{this.model.f4258c});
        }
    }

    public void setShareVisibility(boolean z) {
        this.mTitleBar.getSharBtn().setVisibility(z ? 0 : 4);
    }

    public void setAppbarTitle(String str) {
        this.mTitleBar.setTitle(str);
    }

    public void setShareModel(ShareModel shareModel) {
        this.model = shareModel;
    }

    private String buildTransaction(String str) {
        return str == null ? String.valueOf(System.currentTimeMillis()) : str + System.currentTimeMillis();
    }

    protected void showProgressDialog(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            str = "请稍候";
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "正在加载...";
        }
        this.mProgressDialog = ProgressDialog.show(context, str, str2);
        this.mProgressDialog.setCancelable(true);
    }
}
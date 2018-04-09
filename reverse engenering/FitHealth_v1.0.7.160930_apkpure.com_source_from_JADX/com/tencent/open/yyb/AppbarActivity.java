package com.tencent.open.yyb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
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
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.p004a.p005a.C1711d;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.tencent.utils.SystemUtils;
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
import org.apache.http.HttpHost;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class AppbarActivity extends Activity implements OnClickListener {
    private static final int FLOATING_DIALOG_HEIGHT = 100;
    public static final String MYAPP_CACHE_PATH = "/tencent/tassistant";
    private static final String UA_PREFIX = "qqdownloader/";
    private static final String WEBVIEW_PATH = "/webview_cache";
    private static ArrayList<String> specialModel = new ArrayList();
    private String appid;
    private AppbarJsBridge jsBridge;
    private final DownloadListener mDownloadListener = new C0821c(this);
    private MoreFloatingDialog mFloatingDialog;
    protected ProgressDialog mProgressDialog;
    private LinearLayout mRootView;
    private TitleBar mTitleBar;
    private QQToken mToken;
    private WebView mWebView;
    private ShareModel model;
    private Tencent tencent;
    private int titlebarTop;
    private String url;

    /* compiled from: ProGuard */
    private static class C0813a extends AsyncTask<String, Void, byte[]> {
        private C0814b f2750a;

        protected /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            return m2595a((String[]) objArr);
        }

        protected /* bridge */ /* synthetic */ void onPostExecute(Object obj) {
            m2594a((byte[]) obj);
        }

        public C0813a(C0814b c0814b) {
            this.f2750a = c0814b;
        }

        protected byte[] m2595a(String... strArr) {
            try {
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(strArr[0]).openConnection();
                    httpURLConnection.setConnectTimeout(5000);
                    try {
                        httpURLConnection.setRequestMethod("GET");
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

        protected void m2594a(byte[] bArr) {
            super.onPostExecute(bArr);
            this.f2750a.mo2141a(bArr);
        }
    }

    /* compiled from: ProGuard */
    private interface C0814b {
        void mo2141a(byte[] bArr);
    }

    /* compiled from: ProGuard */
    private class C0815c extends WebChromeClient {
        final /* synthetic */ AppbarActivity f2751a;

        private C0815c(AppbarActivity appbarActivity) {
            this.f2751a = appbarActivity;
        }

        public void onReceivedTitle(WebView webView, String str) {
            this.f2751a.mTitleBar.setTitle(str);
        }
    }

    /* compiled from: ProGuard */
    class C17322 implements IUiListener {
        final /* synthetic */ AppbarActivity f4681a;

        C17322(AppbarActivity appbarActivity) {
            this.f4681a = appbarActivity;
        }

        public void onError(UiError uiError) {
            C1711d.m4638b("openSDK_LOG", "-->(AppbarJsBridge)openLoginActivity onError");
            this.f4681a.jsBridge.responseFail(AppbarJsBridge.CALLBACK_LOGIN, 0, null, -5);
        }

        public void onComplete(Object obj) {
            C1711d.m4638b("openSDK_LOG", "-->(AppbarJsBridge)openLoginActivity onComplete");
            JSONObject jSONObject = (JSONObject) obj;
            if (jSONObject.optInt("ret", -1) != 0) {
                this.f4681a.jsBridge.responseFail(AppbarJsBridge.CALLBACK_LOGIN, 0, null, -5);
                return;
            }
            try {
                String string = jSONObject.getString("openid");
                String string2 = jSONObject.getString("access_token");
                C0820b.m2604a(this.f4681a, this.f4681a.mWebView.getUrl(), string, string2, this.f4681a.getToken().getAppId());
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put("logintype", "SSO");
                    jSONObject2.put("openid", string);
                    jSONObject2.put("accesstoken", string2);
                    this.f4681a.jsBridge.response(AppbarJsBridge.CALLBACK_LOGIN, 0, null, jSONObject2.toString());
                } catch (JSONException e) {
                    this.f4681a.jsBridge.responseFail(AppbarJsBridge.CALLBACK_LOGIN, 0, null, -5);
                    C1711d.m4638b("openSDK_LOG", "-->(AppbarJsBridge)openLoginActivity onComplete: put keys callback failed.");
                }
            } catch (JSONException e2) {
                this.f4681a.jsBridge.responseFail(AppbarJsBridge.CALLBACK_LOGIN, 0, null, -5);
                C1711d.m4638b("openSDK_LOG", "-->(AppbarJsBridge)openLoginActivity onComplete: get keys failed.");
            }
        }

        public void onCancel() {
            C1711d.m4638b("openSDK_LOG", "-->(AppbarJsBridge)openLoginActivity onCancel");
            this.f4681a.jsBridge.responseFail(AppbarJsBridge.CALLBACK_LOGIN, 0, null, -2);
        }
    }

    /* compiled from: ProGuard */
    class C17333 implements C0814b {
        final /* synthetic */ AppbarActivity f4682a;

        C17333(AppbarActivity appbarActivity) {
            this.f4682a = appbarActivity;
        }

        public void mo2141a(byte[] bArr) {
            C1711d.m4638b("openSDK_LOG", "-->onImageDownloaded : result = " + bArr);
            this.f4682a.mProgressDialog.dismiss();
        }
    }

    /* compiled from: ProGuard */
    private class C1735d extends WebViewClient {
        final /* synthetic */ AppbarActivity f4685a;

        private C1735d(AppbarActivity appbarActivity) {
            this.f4685a = appbarActivity;
        }

        public void onPageFinished(WebView webView, String str) {
            this.f4685a.setSupportZoom(true);
            this.f4685a.jsBridge.ready();
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            this.f4685a.setSupportZoom(false);
            if (!str.startsWith(HttpHost.DEFAULT_SCHEME_NAME) && !str.startsWith("https")) {
            }
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            boolean z = true;
            C1711d.m4638b("openSDK_LOG", "-->(AppbarDialog)shouldOverrideUrlLoading : url = " + str);
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            if (str.startsWith(HttpHost.DEFAULT_SCHEME_NAME) || str.startsWith("https")) {
                return super.shouldOverrideUrlLoading(webView, str);
            }
            if (str.startsWith(AppbarJsBridge.JS_BRIDGE_SCHEME)) {
                this.f4685a.jsBridge.invoke(str);
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
        C1711d.m4638b("openSDK_LOG", "-->(AppbarActivity)onCreate : appid = " + this.appid + " url = " + this.url);
        this.mWebView = new WebView(this);
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
        try {
            method = cls.getMethod("removeJavascriptInterface", new Class[]{String.class});
            if (method != null) {
                method.invoke(this.mWebView, new Object[]{"searchBoxJavaBridge_"});
            }
        } catch (Exception e7) {
            e7.printStackTrace();
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
                } catch (Exception e8) {
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
                    } catch (Exception e9) {
                    }
                } else {
                    try {
                        this.mWebView.getSettings().getClass().getMethod("setDisplayZoomControls", new Class[]{Boolean.TYPE}).invoke(this.mWebView.getSettings(), new Object[]{Boolean.valueOf(false)});
                    } catch (Exception e10) {
                    }
                }
            }
        }
        this.mWebView.setWebViewClient(new C1735d());
        this.mWebView.setWebChromeClient(new C0815c());
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
            str = getFilesDir().getAbsolutePath() + MYAPP_CACHE_PATH;
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
        C1711d.m4638b("openSDK_LOG", "-->(AppbarDialog)showFloatingDialog : params.x = " + attributes.x);
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
        C1711d.m4638b("openSDK_LOG", "-->login : activity~~~");
        getTencent().login(this, "all", new C17322(this));
    }

    public void shareToQQ() {
        final QQToken token = getToken();
        if (token != null) {
            QQShare qQShare = new QQShare(this, token);
            Bundle bundle = new Bundle();
            bundle.putString("title", this.model.f2754a);
            bundle.putString("targetUrl", this.model.f2757d);
            bundle.putString("summary", this.model.f2755b);
            bundle.putString("imageUrl", this.model.f2756c);
            C1711d.m4638b("openSDK_LOG", "-->(AppbarActivity)shareToQQ : model.mTitle = " + this.model.f2754a);
            C1711d.m4638b("openSDK_LOG", "-->(AppbarActivity)shareToQQ : model.mTargetUrl = " + this.model.f2757d);
            C1711d.m4638b("openSDK_LOG", "-->(AppbarActivity)shareToQQ : model.mDescription = " + this.model.f2755b);
            C1711d.m4638b("openSDK_LOG", "-->(AppbarActivity)shareToQQ : model.mIconUrl = " + this.model.f2756c);
            qQShare.shareToQQ(this, bundle, new IUiListener(this) {
                final /* synthetic */ AppbarActivity f4680b;

                public void onError(UiError uiError) {
                    C1711d.m4638b("openSDK_LOG", "-->(AppbarActivity)shareToQQ onError");
                    this.f4680b.jsBridge.responseShareFail(1);
                }

                public void onComplete(Object obj) {
                    C1711d.m4638b("openSDK_LOG", "-->(AppbarActivity)shareToQQ onComplete");
                    this.f4680b.jsBridge.responseShare(1);
                    C0820b.m2605a(token.getAppId(), "400", "SDK.APPBAR.HOME.SHARE.QQ");
                }

                public void onCancel() {
                    C1711d.m4638b("openSDK_LOG", "-->(AppbarActivity)shareToQQ onCancel");
                    this.f4680b.jsBridge.responseShareFail(1);
                }
            });
            C0820b.m2605a(token.getAppId(), "200", "SDK.APPBAR.HOME.SHARE.QQ");
        }
    }

    public void shareToQzone() {
        final QQToken token = getToken();
        if (token != null) {
            QzoneShare qzoneShare = new QzoneShare(this, token);
            Bundle bundle = new Bundle();
            bundle.putInt("req_type", 1);
            bundle.putString("title", this.model.f2754a);
            bundle.putString("summary", this.model.f2755b);
            bundle.putString("targetUrl", this.model.f2757d);
            ArrayList arrayList = new ArrayList();
            C1711d.m4638b("openSDK_LOG", "-->shareToQzone : mIconUrl = " + this.model.f2756c);
            arrayList.add(this.model.f2756c);
            bundle.putStringArrayList("imageUrl", arrayList);
            qzoneShare.shareToQzone(this, bundle, new IUiListener(this) {
                final /* synthetic */ AppbarActivity f4684b;

                public void onError(UiError uiError) {
                    C1711d.m4638b("openSDK_LOG", "-->(AppbarActivity)shareToQzone onError");
                    this.f4684b.jsBridge.responseShareFail(2);
                }

                public void onComplete(Object obj) {
                    C1711d.m4638b("openSDK_LOG", "-->(AppbarActivity)shareToQzone onComplete");
                    this.f4684b.jsBridge.responseShare(2);
                    C0820b.m2605a(token.getAppId(), "400", "SDK.APPBAR.HOME.SHARE.QZ");
                }

                public void onCancel() {
                    C1711d.m4638b("openSDK_LOG", "-->(AppbarActivity)shareToQzone onCancel");
                    this.f4684b.jsBridge.responseShareFail(2);
                }
            });
            C0820b.m2605a(token.getAppId(), "200", "SDK.APPBAR.HOME.SHARE.QZ");
        }
    }

    public void shareToWX() {
        shareToWX(false);
    }

    public void shareToTimeline() {
        shareToWX(true);
    }

    private void shareToWX(boolean z) {
        C1711d.m4638b("openSDK_LOG", "-->shareToWX : wx_appid = " + AppbarAgent.wx_appid);
        if (!TextUtils.isEmpty(this.model.f2756c)) {
            showProgressDialog(this, "", "");
            new C0813a(new C17333(this)).execute(new String[]{this.model.f2756c});
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
